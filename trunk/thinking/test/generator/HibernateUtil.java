/*
 * Created on 2004-11-24
 *
 */
package generator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.dao.Test;
import org.commonfarm.util.ResourceUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;


/**
 * A very simple Hibernate helper class that holds the SessionFactory as a
 * singleton.
 * <p>
 * The only job of this helper class is to give your application code easy
 * access to the <tt>SessionFactory</tt>. It initializes the
 * <tt>SessionFactory</tt> when it is loaded (static initializer) and you can
 * easily open new <tt>Session</tt>s. Only really useful for trivial
 * applications.
 * 
 * @author christian@hibernate.org
 */
public class HibernateUtil {

    private static Log log = LogFactory.getLog(HibernateUtil.class);

    private static final SessionFactory sessionFactory;

    private static final ThreadLocal sessionThread = new ThreadLocal();

    // Create the initial SessionFactory from the default configuration files
    static {
        try {
            Configuration cfg = new AnnotationConfiguration().configure(ResourceUtil.getURL("classpath:generator/hibernate.cfg.xml"));
            sessionFactory = cfg.buildSessionFactory();
            SchemaExport se = new SchemaExport(cfg);
            SchemaUpdate su = null;//new SchemaUpdate(cfg);
            if (su != null) su.execute(true, true);
            if (se != null) {
            	se.drop(true, true); 
            	se.create(true, true);
            }
        } catch (Throwable ex) {
            // We have to catch Throwable, otherwise we will miss
            // NoClassDefFoundError and other subclasses of Error
            log.error("Building SessionFactory failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
    	return sessionFactory;
    }
    public static Session getSession() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return session;
    }

    public static Session currentSession() {
        Session s = (Session) sessionThread.get();
        //open a new session if this thread has none yet
        try {
            if (s == null) {
                s = sessionFactory.openSession();
                sessionThread.set(s);
            }
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = (Session) sessionThread.get();
        sessionThread.set(null);
        if (s != null) {
            s.close();
        }
    }
    public static void main(String[] args) {
    	Session session = HibernateUtil.getSession();
    	Criteria criteria = session.createCriteria(Test.class);
    	criteria.setProjection(Projections.count("id"));
    	Integer count = (Integer)criteria.uniqueResult();
    	System.out.print("count----------------" + count);
    }
}