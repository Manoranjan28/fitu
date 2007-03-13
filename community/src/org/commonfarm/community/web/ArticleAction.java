package org.commonfarm.community.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.commonfarm.community.model.Article;
import org.commonfarm.service.BusinessException;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.StringUtil;
import org.commonfarm.web.WebWorkAction;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Preparable;

public class ArticleAction extends WebWorkAction implements Preparable {
//	private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Request Parameters Start */
	private File[] uploadFiles;
	private String[] uploadFilesContentType;
	private String[] uploadFilesFileName;
	private String[] fileDescs;
	/** Request Parameters End */
	
	/** Search Criterias Start **/
	private String s_title;
	private String s_summary;
	private String s_level;
	private String s_topic$name;
	private String s_goodFlg;
	//select group criterias
	/** Search Criterias End **/
	
	public ArticleAction(ThinkingService thinkingService) {
		super(thinkingService);
	}
	
	/**
	 * Process Save Operation
	 * @return
	 */
	protected boolean processSave(Object model) {
		Article article = (Article) model;
		try {
			for (int i = 0; i < uploadFiles.length; i++) {
				upload(uploadFiles[i]);
			}
			if (getLoginUser() != null) {
				article.setCreateUser(getLoginUser().getUserId());
			} else {
				article.setCreateUser("test");
			}
			article.setCreateDate(new Date());
			
			thinkingService.saveObject(model);
		} catch (Exception e) {
			addActionError("Save failure! " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public void upload(File file) throws Exception {
		if (file == null || file.length() > 2097152) {
            addActionError(getText("maxLengthExceeded"));
        }
		String fileName = file.getName();
        // the directory to upload to
        String uploadDir = 
            ServletActionContext.getServletContext().getRealPath("/resources") +
            "/" + "user" + "/";

        // write the file to the file specified
        File dirPath = new File(uploadDir);
        
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        //retrieve the file data
        InputStream stream = new FileInputStream(file);

        //write the file to the file specified
        OutputStream bos = new FileOutputStream(uploadDir + fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];

        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        bos.close();        
        stream.close();

        
        // place the data into the request for retrieval on next page
        /*getRequest().setAttribute("location", dirPath.getAbsolutePath()
                + Constants.FILE_SEP + fileFileName);
        
        String link =
            getRequest().getContextPath() + "/resources" + "/" +
            getRequest().getRemoteUser() + "/";
        
        getRequest().setAttribute("link", link + fileFileName);*/
	}
	/**
	 * if you master this framework, you must not implement this method and you can use invention
	 */
	public void remove(String id) throws BusinessException {
		thinkingService.removeObject(model, new Long(id), new String[] {"groups", "users"});
	}
	
	public void prepare() throws Exception {
		if (StringUtil.isEmpty(getSearchName())) setSearchName("article");
		if(actionId == 0) {
            model = new Article();
        } else {
            model = thinkingService.getObject(Article.class, new Long(actionId));
        }

	}

	public String getS_goodFlg() {
		return s_goodFlg;
	}

	public void setS_goodFlg(String flg) {
		s_goodFlg = flg;
	}

	public String getS_level() {
		return s_level;
	}

	public void setS_level(String s_level) {
		this.s_level = s_level;
	}

	public String getS_summary() {
		return s_summary;
	}

	public void setS_summary(String s_summary) {
		this.s_summary = s_summary;
	}

	public String getS_title() {
		return s_title;
	}

	public void setS_title(String s_title) {
		this.s_title = s_title;
	}

	public String getS_topic$name() {
		return s_topic$name;
	}

	public void setS_topic$name(String s_topic$name) {
		this.s_topic$name = s_topic$name;
	}

	/**
	 * @return the uploadFiles
	 */
	public File[] getUploadFiles() {
		return uploadFiles;
	}

	/**
	 * @param uploadFiles the uploadFiles to set
	 */
	public void setUploadFiles(File[] uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	/**
	 * @return the fileDescs
	 */
	public String[] getFileDescs() {
		return fileDescs;
	}

	/**
	 * @param fileDescs the fileDescs to set
	 */
	public void setFileDescs(String[] fileDescs) {
		this.fileDescs = fileDescs;
	}

	/**
	 * @return the uploadFilesContentType
	 */
	public String[] getUploadFilesContentType() {
		return uploadFilesContentType;
	}

	/**
	 * @param uploadFilesContentType the uploadFilesContentType to set
	 */
	public void setUploadFilesContentType(String[] uploadFilesContentType) {
		this.uploadFilesContentType = uploadFilesContentType;
	}

	/**
	 * @return the uploadFilesFileName
	 */
	public String[] getUploadFilesFileName() {
		return uploadFilesFileName;
	}

	/**
	 * @param uploadFilesFileName the uploadFilesFileName to set
	 */
	public void setUploadFilesFileName(String[] uploadFilesFileName) {
		this.uploadFilesFileName = uploadFilesFileName;
	}
}
