/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user.provider.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.commonfarm.security.user.LoginUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Credentials provider backed by a JDBC datastore.  Allows modification of
 * user data.
 *
 * @version $Revision: 1.4 $
 */
public class JDBCCredentialsProvider extends BaseJDBCProvider {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(JDBCCredentialsProvider.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Authenticate a user by checking to see if they exist in database
     * and if their password matches.
     */
    public boolean authenticate(String name, String password) {
        boolean authenticated = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT " + userPassword + " FROM " + userTable + " WHERE " + userName + " = ?");
            ps.setString(1, name);

            rs = ps.executeQuery();

            if (rs.next() && rs.getString(1).equals(password)) {
            	authenticated = true;
                //authenticated = compareHash(rs.getString(1), password);
            }
        } catch (SQLException e) {
            log.fatal("Could not authenticate user [" + name + "]", e);
        } finally {
            cleanup(conn, ps, rs);
        }

        return authenticated;
    }

    /**
     * Changes a users password to a new password.
     */
    public boolean changePassword(String name, String password) {
        boolean changedPassword = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("UPDATE " + userTable + " SET " + userPassword + " = ? WHERE " + userName + " = ?");
            //ps.setString(1, createHash(password));
            ps.setString(1, password);
            ps.setString(2, name);
            ps.executeUpdate();
            changedPassword = true;
        } catch (SQLException e) {
            log.fatal("Could not change password for user [" + name + "]", e);
        } finally {
            cleanup(conn, ps, null);
        }

        return changedPassword;
    }

    /**
     * Create a new user by inserting a record into the users table.
     */
    public boolean create(String name) {
        boolean created = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO " + userTable + " (" + userName + ") VALUES (?)");
            ps.setString(1, name);

            try {
                ps.executeUpdate();
                created = true;
            } catch (SQLException e) {
                log.warn("LoginUser [" + name + "] must already exist", e);
            }
        } catch (SQLException e) {
            log.fatal("Could not create user [" + name + "]", e);
        } finally {
            cleanup(conn, ps, null);
        }

        return created;
    }
    
    /**
     * Returns whether a user exists or not.
     */
    public boolean handles(LoginUser user) {
        boolean handles = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT * FROM " + userTable + " WHERE " + userName + " = ?");
            ps.setString(1, user.getName());

            rs = ps.executeQuery();
            handles = rs.next();
            user.setId(rs.getLong("ID"));
            user.setFullName(rs.getString("NAME"));
        } catch (SQLException e) {
            log.fatal("Could not see if [" + user.getName() + "] is handled", e);
        } finally {
            cleanup(conn, ps, rs);
        }

        return handles;
    }
    /**
     * Returns whether a user exists or not.
     */
    public boolean handles(String name) {
        boolean handles = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT * FROM " + userTable + " WHERE " + userName + " = ?");
            ps.setString(1, name);

            rs = ps.executeQuery();
            handles = rs.next();
            
        } catch (SQLException e) {
            log.fatal("Could not see if [" + name + "] is handled", e);
        } finally {
            cleanup(conn, ps, rs);
        }

        return handles;
    }
    public Set getRoles(String name) {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Set roles = new HashSet();
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT ROLES.NAME AS ROLE_NAME FROM " + roleTable + " AS ROLES JOIN SYS_R_USER_ROLE AS USER_ROLE ON ROLES.ID = USER_ROLE.ROLE_ID JOIN SYS_O_USERS AS USERS ON USER_ROLE.USER_ID = USERS.ID WHERE USERS.USERNAME = ?");
            ps.setString(1, name);
            
            rs = ps.executeQuery();
            while (rs.next()) {
        		String roleName = rs.getString("ROLE_NAME");
        		roles.add(roleName);
        	}
        } catch (SQLException e) {
            log.fatal("Could not get roles by [" + name + "]", e);
        } finally {
            cleanup(conn, ps, rs);
        }
        return roles;
    }
    /**
     * Return all the users ordered by their username.
     */
    public List list() {
        ArrayList users = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT " + userName + " FROM " + userTable + " ORDER BY " + userName);
            rs = ps.executeQuery();

            while (rs.next()) {
                users.add(rs.getString(1));
            }
        } catch (SQLException e) {
            log.fatal("Could not list users", e);
        } finally {
            cleanup(conn, ps, rs);
        }

        return users;
    }

    /*
     * Delete a user from their memberships and the user table.
     * @see com.opensymphony.user.provider.UserProvider#remove(java.lang.String)
     */
    public boolean remove(String name) {
        boolean removed = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();

            ps = conn.prepareStatement("DELETE FROM " + userTable + " WHERE " + userName + " = ?");
            ps.setString(1, name);

            int rows = ps.executeUpdate();

            if (rows == 1) {
                removed = true;
            }
        } catch (SQLException e) {
            log.fatal("Unable to remove user [" + name + "]", e);
        } finally {
            cleanup(conn, ps, null);
        }

        return removed;
    }
}
