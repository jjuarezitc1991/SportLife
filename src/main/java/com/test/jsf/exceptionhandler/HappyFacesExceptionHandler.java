package com.test.jsf.exceptionhandler;

import java.sql.SQLException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.test.utils.FacesUtils;
import org.hibernate.StaleObjectStateException;

public class HappyFacesExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public HappyFacesExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {

        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable t = context.getException();

            final FacesContext fc = FacesContext.getCurrentInstance();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();

            try {
                if (unwindException(t) instanceof StaleObjectStateException) {
                    FacesUtils.error("error.optimisticLocking");
                    nav.handleNavigation(fc, null, null);
                    fc.renderResponse();

                } else if (t instanceof ViewExpiredException) {

                    FacesUtils.error("error.sessionExpired");
                    nav.handleNavigation(fc, null, "/login.xhtml");
                    fc.renderResponse();

                }

            } finally {
                i.remove();
            }
        }
        getWrapped().handle();
    }

    private static Throwable unwindException(Throwable th) {
        if (th instanceof SQLException) {
            SQLException sql = (SQLException) th;
            if (sql.getNextException() != null) {
                return unwindException(sql.getNextException());
            }
        } else if (th.getCause() != null) {
            return unwindException(th.getCause());
        }

        return th;
    }
}
