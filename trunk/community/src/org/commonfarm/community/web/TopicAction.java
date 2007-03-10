package org.commonfarm.community.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.commonfarm.app.model.UserGroup;
import org.commonfarm.community.model.Article;
import org.commonfarm.community.model.Topic;
import org.commonfarm.service.BusinessException;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.StringUtil;
import org.commonfarm.web.StrutsAction;

import com.opensymphony.xwork2.Preparable;

public class TopicAction extends StrutsAction implements Preparable {
	//private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Request Parameters Start */
	/** Request Parameters End */
	
	/** Search Criterias Start **/
	private String s_name;
	/** Search Criterias End **/
	
	public TopicAction(ThinkingService thinkingService) {
		super(thinkingService);
	}
	/**
	 * Select users for user group
	 * @return
	 */
	public String select() throws Exception {
		Topic topic = (Topic) thinkingService.getObject(Topic.class, new Long(modelId));
		
		String[] ids = request.getParameterValues("items");
		if (ids != null) {
			List selectArticles = new ArrayList();
			List cancelArticles = new ArrayList();
            for (int i = 0; i < ids.length; i++) {
                Article article = (Article) thinkingService.getObject(Article.class, new Long(ids[i].split("_")[0]));
                if (ids[i].split("_")[1].equals("false")) {
                	selectArticles.add(article);
                } else {
                	cancelArticles.add(article);
                }
            }
            thinkingService.selectObjects(topic.getArticles(), selectArticles, cancelArticles);
		}
		
		request.setAttribute("TOPIC", topic);
		
		search("article");
		List articles = (List) request.getAttribute("list");
        Set selectArticles = topic.getArticles();
        for (Iterator iter = articles.iterator(); iter.hasNext();) {
			Article article = (Article) iter.next();
			if (selectArticles.contains(article)) article.setSelected(true);
		}
		return SUCCESS;
	}
	
	/**
	 * Process Update Operation
	 * @return
	 */
	protected boolean processUpdate(Object model) throws BusinessException {
		Topic topic = (Topic) model;
		Topic oldTopic = (Topic) thinkingService.getObject(Topic.class, topic.getId());
		oldTopic.setName(topic.getName());
		oldTopic.setDescn(topic.getDescn());
		oldTopic.setSpace(topic.getSpace());
		try {
			thinkingService.updateObject(oldTopic);
		} catch (Exception e) {
			addActionError("Update failure! " + e.getMessage());
			return false;
		}
		return true;
	}
	/**
	 * init: set search name and initialize model object
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		if (StringUtil.isEmpty(getSearchName())) setSearchName("topic");
		if(actionId == 0) {
            model = new Topic();
        } else {
            model = thinkingService.getObject(Topic.class, new Long(actionId));
        }

	}

	/**
	 * @return the s_name
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * @param s_name the s_name to set
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
}
