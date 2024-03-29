package org.commonfarm;

/**
 * 
 * @author David Yang
 */
public interface Constant {
	/**
	 * Test case use Spring Context
	 */
	public static final String DEFAULT_CONTEXT = "classpath*:spring/*.xml";

	/**
	 * Test case use Spring test Context
	 */
	public static final String DEFAULT_TEST_CONTEXT = "classpath*:spring/test/*.xml";
	
//	~ Static fields/initializers =============================================

    /**
     * The application scoped attribute for persistence engine and class that
     * implements it
     */
    public static final String DAO_TYPE = "daoType";
    public static final String DAO_TYPE_HIBERNATE = "hibernate";

    /**
     * JNDI Name of Mail Session.  As configured in the appserver.
     */
    public static final String JNDI_MAIL = "mail/Session";

    /**
     * Default from address for e-mail messages. You should change
     * this to an e-mail address that your users can reply to.
     */
    public static final String DEFAULT_FROM = "commonfuse@commonpoint.org";

    /** Application scoped attribute for authentication url */
    public static final String AUTH_URL = "authURL";

    /** Application scoped attributes for SSL Switching */
    public static final String HTTP_PORT = "httpPort";
    public static final String HTTPS_PORT = "httpsPort";

    /** The application scoped attribute for indicating a secure login */
    public static final String SECURE_LOGIN = "secureLogin";

    /** The encryption algorithm key to be used for passwords */
    public static final String ENC_ALGORITHM = "algorithm";

    /** A flag to indicate if passwords should be encrypted */
    public static final String ENCRYPT_PASSWORD = "encryptPassword";

    /** File separator from System properties */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /** LoginUser home from System properties */
    public static final String USER_HOME =
        System.getProperty("user.home") + FILE_SEP;

    /**
     * The session scope attribute under which the breadcrumb ArrayStack is
     * stored
     */
    public static final String BREADCRUMB = "breadcrumbs";

    /**
     * The session scope attribute under which the LoginUser object for the
     * currently logged in user is stored.
     */
    public static final String USER_KEY = "ACEGI_SECURITY_LAST_USERNAME";//"currentUserForm";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_EDIT_KEY = "userFormEx";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "admin";

    /**
     * The name of the LoginUser role, as specified in web.xml
     */
    public static final String USER_ROLE = "tomcat";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * Name of cookie for "Remember Me" functionality.
     */
    public static final String LOGIN_COOKIE = "sessionId";

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";
    public static final String ID = "id";
    public static final String SEARCH_NAME = "searchName";
    public static final String CLASS_NAME = "className";
	public static final String BUNDLE_KEY = null;
    public static final String CONFIG_SEARCH = "searchConfig";
	public static final String ACTION_ERRORS = "actionErrors";
	public static final String ACTION_MESSAGES = "actionMessages";
	public static final String CONFIG_VALIDATE = "validateRule";
	public static final int DEFAULT_PAGE_SIZE = 1000;

	public static final String DEFAULT_ENCODING = "UTF-8";
}
