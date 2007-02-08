package org.commonfarm.app.dao;

import org.commonfarm.app.model.Role;
import org.commonfarm.app.model.User;
import org.commonfarm.app.model.UserGroup;
import org.commonfarm.dao.DAOTest;
import org.commonfarm.dao.HibernateDAO;
import org.commonfarm.util.DateUtil;

public class UserDAOTest extends DAOTest {
	//this instance will be (automatically) dependency injected
	private HibernateDAO hibernateDAO;
	
	//a setter method to enable DI of the 'hibernateDAO' instance variable
	public void setHibernateDAO(HibernateDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	public void testSaveObject() throws Exception {
		Role role = new Role("normal");
		role.setDescn("normal");
		hibernateDAO.saveObject(role);
		
		UserGroup group = new UserGroup("C");
		group.setDescn("Cell FAB");
		hibernateDAO.saveObject(group);
		//group.setRole(role);
		//role.addGroup(group);
		
		User user = new User();
		user.setUserId("xiumei");
		user.setPassword("password");
		user.setAddress("YangGuang");
		user.setBornDate(DateUtil.convert("1978-11-23"));
		user.setEmail("zhuxiumei@gmail.com");
		user.setFax("0512-50507127");
		user.setTel("0512-50507127");
		user.setImid("david4zhu@hotmail.com");
		user.setZcode("215324");
		user.setSecondName("Xiumei");
		user.setFirstName("Zhu");

		hibernateDAO.saveObject(user);
		//setComplete();
	}
	
	public void testGetObject() throws Exception {
		Object user = hibernateDAO.getObject(new User("xiumei"), new String[] {"userId"});
		/*Object role = hibernateDAO.getObject(Role.class, new Long(26));
		assertNotNull(role);
		Object group = hibernateDAO.getObject(UserGroup.class, new Long(27));
		assertNotNull(group);
		Object user = hibernateDAO.getObject(User.class, new Long(28));
		assertNotNull(user);*/
		assertNotNull(user);
	}
	
	public void testUpdateObject() throws Exception {
		/*Role role = (Role) hibernateDAO.getObject(Role.class, new Long(26));
		UserGroup group = (UserGroup) hibernateDAO.getObject(UserGroup.class, new Long(27));
		User user = (User) hibernateDAO.getObject(User.class, new Long(28));

		group.setRole(role);//OR
		//group.setRole(null);
		group.addUser(user);
		//user.addGroup(group);
		hibernateDAO.saveObject(group);*/
	}
	
}
