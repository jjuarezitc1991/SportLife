package com.test.beans;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import com.test.domain.User;
import com.test.services.UserService.HappyfacesUserDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

@ManagedBean(name = "sessionPreferences")
@SessionScoped
public class SessionPreferences implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int LOGOUT_POPUP_COUNTDOWN = 60;

    private static Logger log = Logger.getLogger(SessionPreferences.class.getName());

    private Locale locale;

    public SessionPreferences() {
        this.locale = new Locale("en");
        FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
    }

    public static Locale getCurrentLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public Locale getLocale() {
        if (locale == null) {
            changeLocale(SessionPreferences.getCurrentLocale().getLanguage());
        }
        return locale;
    }

    public void changeLocale(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public int getSessionDuration() {
        return ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getMaxInactiveInterval();
    }

    public int getSessionTimeoutAlertDuration() {
        return LOGOUT_POPUP_COUNTDOWN;
    }

    public void keepSessionAlive() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public boolean isEditAllowed() {
        return hasRole("ROLE_USER");
    }

    private boolean hasRole(String role) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(req, "");
        return sc.isUserInRole(role);
    }

    public static String getUserName() {
        try {
            UserDetails authenticatedUser = (UserDetails) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
            return authenticatedUser.getUsername();
        } catch (Throwable e) {
            log.error("Error getting logged in user", e);
            return "authentication error";
        }
    }

    public static User getLoggedInUser() {
        try {
            HappyfacesUserDetails authenticatedUser = (HappyfacesUserDetails) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication()
                    .getPrincipal();
            return authenticatedUser.getUser();
        } catch (Throwable e) {
            log.error("Error getting logged in user", e);
            return null;
        }
    }

}
