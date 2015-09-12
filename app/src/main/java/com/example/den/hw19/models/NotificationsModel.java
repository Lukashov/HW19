package com.example.den.hw19.models;

/**
 * Created by Den on 11.09.15.
 */
public class NotificationsModel {
    private String _id;

    private String message;
    private String title;
    private String subtitle;
    private String ticketText;

    public NotificationsModel() {
    }

    public String getId() {
        return _id;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTicketText() {
        return ticketText;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTicketText(String ticketText) {
        this.ticketText = ticketText;
    }

}
