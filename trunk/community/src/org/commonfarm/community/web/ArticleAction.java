package org.commonfarm.community.web;

import org.commonfarm.community.model.Article;
import org.commonfarm.service.BusinessException;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.StringUtil;
import org.commonfarm.web.StrutsAction;

import com.opensymphony.xwork2.Preparable;

public class ArticleAction extends StrutsAction implements Preparable {
//	private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Request Parameters Start */
	private Long content;
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

	public Long getContent() {
		return content;
	}

	public void setContent(Long content) {
		this.content = content;
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
}
