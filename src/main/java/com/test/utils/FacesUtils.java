package com.test.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.test.beans.SessionPreferences;

public final class FacesUtils {

    private static final String MESSAGE_FILE_NAME = "messages";

    private static Logger log = Logger.getLogger(FacesUtils.class.getName());

    private FacesUtils() {
        super();
    }

    public static void info(String messageKey, String... params) {
        addFacesMessage(FacesMessage.SEVERITY_INFO, messageKey, params);
    }

    public static void warn(String messageKey, String... params) {
        addFacesMessage(FacesMessage.SEVERITY_WARN, messageKey, params);
    }

    public static void error(String messageKey, String... params) {
        addFacesMessage(FacesMessage.SEVERITY_ERROR, messageKey, params);
    }

    public static Locale getLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public static String getMessage(String key) {
        return ResourceBundle.getBundle(MESSAGE_FILE_NAME, SessionPreferences.getCurrentLocale()).getString(key);
    }

    public static String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(MESSAGE_FILE_NAME, locale).getString(key);
    }

    private static void addFacesMessage(Severity severity, String messageKey, String... params) {
        addMessageFromBundle(severity, messageKey, MESSAGE_FILE_NAME, params);
    }

    private static void addMessageFromBundle(Severity severity, String messageKey, String bundleName, String... params) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, FacesContext.getCurrentInstance().getViewRoot().getLocale());
        addFacesMessage(severity, messageKey, bundle, params);
    }

    private static void addFacesMessage(Severity severity, String messageKey, ResourceBundle bundle, String... params) {
        String message = messageKey;
        try {
            if (params.length == 0) {
                message = bundle.getString(messageKey);
            } else {
                message = bundle.getString(messageKey);
                MessageFormat mf = new MessageFormat(message, SessionPreferences.getCurrentLocale());
                message = mf.format(params, new StringBuffer(), null).toString();
            }
        } catch (MissingResourceException e) {
            log.warn(String.format("There was no resource in messages.properties for %s", messageKey));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
    }

    public static void addFacesMessageWithoutKey(Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
    }

    public static void renderResponse() {
        FacesContext.getCurrentInstance().renderResponse();
    }

    @SuppressWarnings("rawtypes")
    public static Object evaluateEL(String el, Class clazz) {
    	
        javax.el.ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        javax.el.ExpressionFactory expressionFactory =

        FacesContext.getCurrentInstance().getApplication().getExpressionFactory();

        javax.el.ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, el, clazz);
        return valueExpression.getValue(elContext);
    }
    
    public static HttpServletRequest getRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        return request;
    }

    public static HttpServletResponse getResponse() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        return response;
    }
}
