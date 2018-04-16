package com.test.utils;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class PhaseListenerImpl implements PhaseListener {

    private static final long serialVersionUID = 1L;

    public void afterPhase(PhaseEvent event) {
        // System.out.println("******************After Executing " + event.getPhaseId() + "**********************************");
        event.getFacesContext().getMessageList();
    }

    public void beforePhase(PhaseEvent event) {
        // System.out.println("*******************Before Executing " + event.getPhaseId() + "**********************************");
    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
