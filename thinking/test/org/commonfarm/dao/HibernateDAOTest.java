package org.commonfarm.dao;

public class HibernateDAOTest extends DAOTest {
	//this instance will be (automatically) dependency injected
	private HibernateDAO hibernateDAO;
	
	//a setter method to enable DI of the 'titleDao' instance variable
	public void setTitleDao(HibernateDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	
	public void testLoadTitle() throws Exception {
		Test test = (Test) hibernateDAO.getObject(Test.class, new Long(10));
		assertNotNull(test);
	}
}
