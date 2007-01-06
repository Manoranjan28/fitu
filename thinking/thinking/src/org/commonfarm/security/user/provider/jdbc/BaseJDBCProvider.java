/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user.provider.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.commonfarm.security.user.Entity;
import org.commonfarm.security.user.provider.UserProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.3 $
 */
public abstract class BaseJDBCProvider implements UserProvider {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(BaseJDBCProvider.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected DataSource dataSource;
    protected String roleName = "NAME";
    protected String roleTable = "SYS_O_ROLES";
    protected String userName = "USERNAME";
    protected String userPassword = "PASSWORD";
    protected String userTable = "SYS_O_USERS";
    protected boolean closeConnWhenDone = false;

    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean create(String name) {
        return true;
    }

    public void flushCaches() {
        // nothing for now
    }

    public boolean init(Properties props) {
        userTable = (String) props.get("user.table");
        roleTable = (String) props.get("role.table");
        userName = (String) props.get("user.name");
        userPassword = (String) props.get("user.password");
        roleName = (String) props.get("role.name");

        String jndi = (String) props.get("datasource");

        if (jndi != null) {
            try {
                dataSource = (DataSource) lookup(jndi);

                if (dataSource == null) {
                    dataSource = (DataSource) new javax.naming.InitialContext().lookup(jndi);
                }
            } catch (Exception e) {
                log.fatal("Could not look up DataSource using JNDI location: " + jndi, e);

                return false;
            }
        }

        return true;
    }

    public boolean load(String name, Entity.Accessor accessor) {
        accessor.setMutable(true);

        return true;
    }

    public boolean remove(String name) {
        return true;
    }

    public boolean store(String name, Entity.Accessor accessor) {
        return true;
    }

    protected Connection getConnection() throws SQLException {
        closeConnWhenDone = true;

        return dataSource.getConnection();
    }

    protected void cleanup(Connection connection, Statement statement, ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException ex) {
                log.error("Error closing resultset", ex);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                log.error("Error closing statement", ex);
            }
        }

        if ((connection != null) && closeConnWhenDone) {
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error("Error closing connection", ex);
            }
        }
    }

    private Object lookup(String location) throws NamingException {
        try {
            InitialContext context = new InitialContext();

            try {
                return context.lookup(location);
            } catch (NamingException e) {
                //ok, couldn't find it, look in env
                return context.lookup("java:comp/env/" + location);
            }
        } catch (NamingException e) {
            throw e;
        }
    }

	/**
	 * @return Returns the dataSource.
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource The dataSource to set.
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
