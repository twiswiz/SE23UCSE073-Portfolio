package com.hr.attendance;

public class Attendance {
    private int id;
    private String username;
    private String eventType; // "IN" or "OUT"
    private String eventTime; // ISO timestamp string
    private String note;

    public Attendance() {}

    public Attendance(int id, String username, String eventType, String eventTime, String note) {
        this.id = id;
        this.username = username;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.note = note;
    }

    public Attendance(String username, String eventType, String eventTime, String note) {
        this.username = username;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.note = note;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
