package web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.dao.Test;
import org.commonfarm.web.StrutsAction;

import service.TestService;

public class TestAction extends StrutsAction {
	private static final Log logger = LogFactory.getLog(TestAction.class);
	
	private Test test;
	
	private TestService testService;
	
	public TestAction(TestService testService) {
		this.testService = testService;
	}
	
	public String list() throws Exception {
        this.items = getTestService().getObjects(test);
        if (logger.isDebugEnabled()) {
        	logger.debug("AbstractCRUDAction - [list]: " + (items != null ? "" + items.size() : "no" ) + " items found");
        }
        return SUCCESS;
    }

    public String delete() throws Exception {
        /*if (delItems != null) {
            int count=0;
            for (int i = 0, j = delItems.length; i < j; i++) {
                count = count + getTestService().removeObject(Test.class, delItems[i]);
            }
            if (logger.isDebugEnabled()) {
            	logger.debug("AbstractCRUDAction - [delete]: " + count + " items deleted.");
            }
        }*/
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
}
