package web;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.dao.Test;
import org.commonfarm.web.StrutsAction;

import service.TestService;

public class TestAction extends StrutsAction {
	private static final Log logger = LogFactory.getLog(TestAction.class);
	/** Available items */
	private Collection items;
	/** To delete items*/
	private String[] delItems;
	
	private Test test;
	
	private TestService testService;
	
	public String list() throws Exception {
        this.items = getTestService().getObjects(test);
        if (logger.isDebugEnabled()) {
        	logger.debug("AbstractCRUDAction - [list]: " + (items != null ? "" + items.size() : "no" ) + " items found");
        }
        return execute();
    }

    public String delete() throws Exception {
        if (delItems != null) {
            int count=0;
            for (int i = 0, j = delItems.length; i < j; i++) {
                count = count + getTestService().removeObject(Test.class, delItems[i]);
            }
            if (logger.isDebugEnabled()) {
            	logger.debug("AbstractCRUDAction - [delete]: " + count + " items deleted.");
            }
        }
        return SUCCESS;
    }
    public String save() throws Exception {
        if (getTest() != null) {
        	
        }
        return SUCCESS;
    }
	/**
	 * @return the test
	 */
	public Test getTest() {
		return test;
	}
	/**
	 * @param test the test to set
	 */
	public void setTest(Test test) {
		this.test = test;
	}
	/**
	 * @return the testService
	 */
	public TestService getTestService() {
		return testService;
	}
	/**
	 * @param testService the testService to set
	 */
	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	/**
	 * @return the delItems
	 */
	public String[] getDelItems() {
		return delItems;
	}

	/**
	 * @param delItems the delItems to set
	 */
	public void setDelItems(String[] delItems) {
		this.delItems = delItems;
	}

	/**
	 * @return the items
	 */
	public Collection getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Collection items) {
		this.items = items;
	}
}
