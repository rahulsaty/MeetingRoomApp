package com.threepillar.meetingroomfinder.model;

import java.util.Date;

public class EventInfo {

    private Date dateEvent;
    private String titleEvent;
    private String emailEvent;

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getTitleEvent() {
        return titleEvent;
    }

    public void setTitleEvent(String titleEvent) {
        this.titleEvent = titleEvent;
    }

    public String getEmailEvent() {
        return emailEvent;
    }

    public void setEmailEvent(String emailEvent) {
        this.emailEvent = emailEvent;
    }
}
