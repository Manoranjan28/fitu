package org.commonfarm.service;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;


/**
 * Base class about biz exception.
 * with error code and error message.
 *
 * @author calvin & David Yang
 */
public class BusinessException extends Exception {
	private static String ERROR_BUNDLE_KEY = "message";
    /**
     * error code, default unknow error
     */
    private String errorCode = "UNKNOW_ERROR";

    /**
     * args in error message
     */
    protected String[] errorArgs = null;

    /**
     * error message
     */
    private String errorMessage = null;

    /**
     * ResourceBundle of I18N error message.
     * default Properties file location.
     */
    static private ResourceBundle rb = ResourceBundle.getBundle(ERROR_BUNDLE_KEY, LocaleContextHolder.getLocale());

    public String getErrorCode() {
        return errorCode;
    }

    public BusinessException(String errorCode, String[] errorArgs) {
        this.errorCode = errorCode;
        this.errorArgs = errorArgs;
    }

    public BusinessException(String errorCode, String[] errorArgs, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorArgs = errorArgs;
    }

    public BusinessException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Getting error message.
     * Read i18N properties file about message of Error Code.
     */
    public String getMessage() {
        if (errorMessage != null) return errorMessage;

        String message;
        try {
            message = rb.getString(errorCode);
        }
        catch (MissingResourceException mse) {
            message = "Can't get the message of the Error Code";
        }

        message = MessageFormat.format(message, (Object[]) errorArgs);

        return errorCode + ": " + message;
    }
}