package org.commonfarm.dao;

public class HibernateDAOTest extends DAOTest {
	//this instance will be (automatically) dependency injected
	private HibernateDAO hibernateDAO;
	
	//a setter method to enable DI of the 'hibernateDAO' instance variable
	public void setHibernateDAO(HibernateDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	public void testSaveObject() throws Exception {
		Test test = new Test();
		test.setName("David Yang");
		test.setRemark("Honest, Initiative, Pleasant, Highly-motivated");
		hibernateDAO.saveObject(test);
	}
	
	public void testGetObject() throws Exception {
		Object test = hibernateDAO.getObject(Test.class, new Long(10));
		assertNotNull(test);
	}
}
