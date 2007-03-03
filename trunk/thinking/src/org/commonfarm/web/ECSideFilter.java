/*
 * Copyright 2006-2007 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonfarm.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.Constant;
import org.ecside.core.ECSideConstants;
import org.ecside.core.TableConstants;
import org.ecside.core.TableModelUtils;
import org.ecside.core.TableProperties;
import org.ecside.easyda.DataAccessModel;
import org.ecside.easyda.DataExportModel;
import org.ecside.easylist.AbstractEasyListModel;
import org.ecside.easylist.EasyDataAccessHandler;
import org.ecside.filter.ECSideAjaxResponseWrapper;
import org.ecside.filter.ExportResponseWrapper;
import org.ecside.log.LogHandler;
import org.ecside.resource.MimeUtils;
import org.ecside.util.ECSideUtils;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.Preferences;
import org.extremecomponents.table.filter.ExportFilterUtils;
import org.extremecomponents.table.filter.ViewResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Wei Zijun
 * 
 */

public class ECSideFilter implements Filter {

	protected static String encoding = Constant.DEFAULT_ENCODING;

	protected static boolean useEncoding = true;

	protected static boolean useEasyDataAccess = true;

	protected static boolean responseHeadersSetBeforeDoFilter = true;

	protected static Log logger = LogFactory.getLog(ECSideFilter.class);

	public String servletRealPath;

	public ServletContext servletContext;

	protected FilterConfig filterConfig = null;

	protected EasyDataAccessHandler easyDataAccessHandler;

	protected ApplicationContext appContext;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		servletContext = filterConfig.getServletContext();
		servletRealPath = servletContext.getRealPath("/");

		initEncoding();
		initEasyList();

		String responseHeadersSetBeforeDoFilter = filterConfig
				.getInitParameter("responseHeadersSetBeforeDoFilter");
		if (StringUtils.isNotBlank(responseHeadersSetBeforeDoFilter)) {
			ECSideFilter.responseHeadersSetBeforeDoFilter = Boolean.valueOf(
					responseHeadersSetBeforeDoFilter).booleanValue();
		}

	}

	public void initEasyList() throws ServletException {
		useEasyDataAccess = true;
		String useEasyDataAccessC = filterConfig
				.getInitParameter("useEasyDataAccess");
		if ("off".equalsIgnoreCase(useEasyDataAccessC)
				|| "false".equalsIgnoreCase(useEasyDataAccessC)
				|| "no".equals(useEasyDataAccessC)
				|| "0".equals(useEasyDataAccessC)) {
			useEasyDataAccess = false;
		}
		easyDataAccessHandler = new EasyDataAccessHandler();
	}

	public void initEncoding() throws ServletException {
		String encodingValue = filterConfig.getInitParameter("encoding");
		String useEncodingC = filterConfig.getInitParameter("useEncoding");

		if (encodingValue != null && encodingValue.length() > 0) {
			encoding = encodingValue;
		}
		if (useEncodingC == null || useEncodingC.equalsIgnoreCase("true")
				|| useEncodingC.equalsIgnoreCase("yes")
				|| useEncodingC.equalsIgnoreCase("on")
				|| useEncodingC.equalsIgnoreCase("1")) {
			useEncoding = true;
		} else {
			useEncoding = false;
		}

	}

	public void doEncoding(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		if (useEncoding || (request.getCharacterEncoding() == null)) {
			if (encoding != null) {
				// Set the same character encoding for the request and the
				// response
				request.setCharacterEncoding(encoding);
			} else {
				request.setCharacterEncoding(Constant.DEFAULT_ENCODING);
			}
		} else {
			request.setCharacterEncoding(Constant.DEFAULT_ENCODING);
		}
	}

	public boolean isAJAXRequest(ServletRequest servletRequest) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		return Boolean.TRUE.toString().equalsIgnoreCase(
				request.getHeader(TableConstants.IS_AJAX_REQUEST))
				|| Boolean.TRUE.toString().equalsIgnoreCase(
						request.getHeader(TableConstants.USE_AJAX_PREP));
	}

	public String getEasyList(ServletRequest servletRequest) {
		String easyDataAccess = null;
		if (useEasyDataAccess) {
			easyDataAccess = servletRequest.getParameter(ECSideConstants.EASY_DATA_LIST_FLAG);
		}
		return StringUtils.isNotBlank(easyDataAccess) ? easyDataAccess : null;
	}

	public String getEasyDataExport(ServletRequest servletRequest) {
		String easyDataAccess = null;
		if (useEasyDataAccess) {
			easyDataAccess = servletRequest.getParameter(ECSideConstants.EASY_DATA_EXPORT_FLAG);
		}
		if (StringUtils.isNotBlank(easyDataAccess) && easyDataAccess.indexOf('.') > 0) {
			easyDataAccess = easyDataAccess.substring(0, easyDataAccess.indexOf('.'));
		}
		return StringUtils.isNotBlank(easyDataAccess) ? easyDataAccess : null;
	}

	public String getEasyDataAccess(ServletRequest servletRequest) {
		String easyDataAccess = null;
		if (useEasyDataAccess) {
			easyDataAccess = servletRequest.getParameter(ECSideConstants.EASY_DATA_ACCESS_FLAG);
		}
		if (StringUtils.isNotBlank(easyDataAccess) && easyDataAccess.indexOf('.') > 0) {
			easyDataAccess = easyDataAccess.substring(0, easyDataAccess.indexOf('.'));
		}
		return StringUtils.isNotBlank(easyDataAccess) ? easyDataAccess : null;
	}

	public String getFileName(ServletRequest servletRequest) {
		String fileName = servletRequest.getParameter(ECSideConstants.EASY_DATA_EXPORT_FILENAME);
		if (StringUtils.isBlank(fileName)) {
			String eda = servletRequest.getParameter(ECSideConstants.EASY_DATA_EXPORT_FLAG);
			if (eda.indexOf('.') > 0) {
				fileName = eda.substring(eda.indexOf('.') + 1);
			}
		}
		return StringUtils.isNotBlank(fileName) ? fileName : null;
	}

	public String getSqlName(ServletRequest servletRequest) {
		String sqlName = servletRequest.getParameter(ECSideConstants.EASY_DATA_ACCESS_SQLNAME);
		if (StringUtils.isBlank(sqlName)) {
			String eda = servletRequest.getParameter(ECSideConstants.EASY_DATA_ACCESS_FLAG);
			if (StringUtils.isBlank(eda)) {
				eda = servletRequest.getParameter(ECSideConstants.EASY_DATA_EXPORT_FLAG);
			}
			if (StringUtils.isNotBlank(eda) && eda.indexOf('.') > 0) {
				sqlName = eda.substring(eda.indexOf('.') + 1);
			}
		}
		return StringUtils.isNotBlank(sqlName) ? sqlName : null;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (isAJAXRequest(request)) {
			doAjaxFilter(request, response, chain);
			return;
		}

		doEncoding(request, response);

		String easyListName = getEasyList(request);
		String easyDataAccessName = getEasyDataAccess(request);
		String easyDataExport = getEasyDataExport(request);

		Context context = new HttpServletRequestContext(request);
		boolean isExported = ExportFilterUtils.isExported(context);

		if (isExported) {
			doExportFilter(request, response, chain, context);
			return;
		}

		if (StringUtils.isNotBlank(easyDataExport)) {
			String sqlName = getSqlName(request);
			String fileName = getFileName(request);
			String type = fileName.substring(fileName.indexOf('.') + 1);
			try {
				setResponseHeaders(request, response, fileName);

				response.flushBuffer();
				response.getOutputStream().flush();
				// response.setBufferSize(8192);
				// System.out.println("======response buffer size :
				// "+response.getBufferSize());
				easyDataAccessHandler.exportList(getDataExportModelBean(request, easyDataExport), sqlName, type, request, response);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (Exception e) {
				// TODO :
			}
			return;
		}

		if (StringUtils.isNotBlank(easyListName)) {
			easyDataAccessHandler.easyList(request, response, getEasyListModelBean(request, easyListName));
		} else if (StringUtils.isNotBlank(easyDataAccessName)) {
			String sqlName = getSqlName(request);
			easyDataAccessHandler.dataAccess(getDataAccessModelBean(request, easyDataAccessName), sqlName, request, response);
		}
		chain.doFilter(request, response);

	}

	public void doAjaxFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");

		String easyListName = getEasyList(request);
		String easyDataAccessName = getEasyDataAccess(request);

		String findAjaxZoneAtClient = request.getParameter(TableConstants.AJAX_FINDZONE_CLIENT);
		if (findAjaxZoneAtClient != null
				&& ("false".equalsIgnoreCase(findAjaxZoneAtClient) || "0".equals(findAjaxZoneAtClient))) {
			ECSideAjaxResponseWrapper bufferResponseWrapper = new ECSideAjaxResponseWrapper(response);
			try {

				String ectableId = request.getParameter(TableConstants.EXTREME_COMPONENTS_INSTANCE);
				if (easyListName != null) {
					easyDataAccessHandler.easyList(request, bufferResponseWrapper, getEasyListModelBean(request, easyListName));
				} else if (easyDataAccessName != null) {
					String sqlName = getSqlName(request);
					easyDataAccessHandler.dataAccess(getDataAccessModelBean(request, easyDataAccessName), sqlName, request, response);
				}
				chain.doFilter(request, bufferResponseWrapper);
				String zone = bufferResponseWrapper.findSubstring(ECSideUtils.getAjaxBegin(ectableId), ECSideUtils.getAjaxEnd(ectableId));
				HttpServletResponse originalResponse = bufferResponseWrapper.getOriginalResponse();
				originalResponse.flushBuffer();
				originalResponse.getOutputStream().write(zone.getBytes("UTF-8"));
				originalResponse.flushBuffer();
				originalResponse.getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (easyListName != null) {
				easyDataAccessHandler.easyList(request, response, getEasyListModelBean(request, easyListName));
			} else if (easyDataAccessName != null) {
				String sqlName = getSqlName(request);
				easyDataAccessHandler.dataAccess(getDataAccessModelBean(request, easyDataAccessName), sqlName, request, response);
			}
			chain.doFilter(request, response);
		}
	}

	public void doExportFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Context context)
			throws IOException, ServletException {
		String easyListName = getEasyList(request);
		String exportFileName = ExportFilterUtils.getExportFileName(context);
		boolean isPrint = "_print_".equals(exportFileName);

		if (isPrint) {

			if (easyListName != null) {
				easyDataAccessHandler.easyList(request, response, getEasyListModelBean(request, easyListName));
			}
			chain.doFilter(request, response);
		} else {
			HttpServletResponseWrapper responseWrapper = new ExportResponseWrapper(response);
			if (responseHeadersSetBeforeDoFilter) {
				setResponseHeaders(request, response, exportFileName);
			}
			if (easyListName != null) {
				easyDataAccessHandler.easyList(request, responseWrapper, getEasyListModelBean(request, easyListName));
			}
			chain.doFilter(request, responseWrapper);
			if (!responseHeadersSetBeforeDoFilter) {
				setResponseHeaders(request, response, exportFileName);
			}
		}
		handleExport(request, response, context);
	}

	public void handleExport(HttpServletRequest request, HttpServletResponse response, Context context) {
		try {
			Object viewData = request.getAttribute(TableConstants.VIEW_DATA);
			if (viewData != null) {
				Preferences preferences = new TableProperties();
				preferences.init(null, TableModelUtils.getPreferencesLocation(context));
				String viewResolver = (String) request
						.getAttribute(TableConstants.VIEW_RESOLVER);
				Class classDefinition = Class.forName(viewResolver);
				ViewResolver handleExportViewResolver = (ViewResolver) classDefinition.newInstance();

				request.setAttribute(TableConstants.SERVLET_REAL_PATH, servletRealPath);
				handleExportViewResolver.resolveView(request, response, preferences, viewData);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public void setResponseHeaders(HttpServletRequest request, HttpServletResponse response, String exportFileName) {
		String mimeType = MimeUtils.getFileMimeType(exportFileName);

		// response.reset();

		if (StringUtils.isNotBlank(mimeType)) {
			response.setContentType(mimeType);
		}

		// response.setHeader("Content-Type",mimeType);
		try {
			exportFileName = encodeFileName(request, exportFileName);

		} catch (UnsupportedEncodingException e) {
			String errorMessage = "Unsupported response.getCharacterEncoding() [" + "UTF-8" + "].";
			logger.error("TDExportFilter.setResponseHeaders() - " + errorMessage);
		}
		response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		// response.setHeader("Cache-Control","private");
		// response.setHeader("Pragma", "private");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));

	}

	public static String encodeFileName(HttpServletRequest request,
			String fileName) throws UnsupportedEncodingException {
		String agent = request.getHeader("USER-AGENT");
		// exportFileName =
		// URLEncoder.encode(exportFileName,response.getCharacterEncoding());

		return ECSideUtils.encodeFileName(fileName, agent);

	}

	public AbstractEasyListModel getEasyListModelBean(HttpServletRequest request, String beanName) {
		return (AbstractEasyListModel) getBean(request, beanName);
	}

	public DataAccessModel getDataAccessModelBean(HttpServletRequest request, String beanName) {
		return (DataAccessModel) getBean(request, beanName);
	}

	public DataExportModel getDataExportModelBean(HttpServletRequest request, String beanName) {
		return (DataExportModel) getBean(request, beanName);
	}

	public Object getBean(HttpServletRequest request, String beanName) {
		Object bean = null;
		if (appContext == null) {
			appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		}
		if (appContext != null) {
			bean = appContext.getBean(beanName);
		}
		if (bean == null) {
			LogHandler.warnLog("Can't find DataAccess Bean named " + beanName, ECSideFilter.class.getName());
		}
		return bean;
	}

	public void destroy() {
		this.filterConfig = null;
	}

}
