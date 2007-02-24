package generator;

import org.commonfarm.app.model.Role;
import org.commonfarm.app.model.User;
import org.commonfarm.app.model.UserGroup;
import org.commonfarm.util.DateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProjectInitor {
	public static void main(String[] args) {
    	Session session = HibernateUtil.getSession();
    	Transaction tx = session.beginTransaction();
    	Role role = new Role("normal");
		role.setDescn("normal");
		session.save(role);
		
		User user = new User();
		user.setUserId("test");
		user.setPassword("test");
		user.setAddress("YangGuang");
		user.setBornDate(DateUtil.convert("1978-11-23"));
		user.setEmail("zhuxiumei@gmail.com");
		user.setFax("0512-50507127");
		user.setTel("0512-50507127");
		user.setImid("david4zhu@hotmail.com");
		user.setZcode("215324");
		user.setSecondName("Xiumei");
		user.setFirstName("Zhu");
		session.save(user);
		
		UserGroup group = new UserGroup("C");
		group.setDescn("Cell FAB");
		group.addUser(user);
		group.setRole(role);
		session.save(group);
		tx.commit();
		session.close();
    }
}
