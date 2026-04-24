package com.hr.leave;

public class LeaveRequest {
    private int id;
    private String username;
    private String startDate;
    private String endDate;
    private String type;
    private String reason;
    private String status;
    private String requestedAt;
    private String decisionBy;
    private String decisionAt;

    public LeaveRequest() { }

    public LeaveRequest(String username, String startDate, String endDate,
                        String type, String reason, String requestedAt) {
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.reason = reason;
        this.requestedAt = requestedAt;
        this.status = "PENDING";
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRequestedAt() { return requestedAt; }
    public void setRequestedAt(String requestedAt) { this.requestedAt = requestedAt; }

    public String getDecisionBy() { return decisionBy; }
    public void setDecisionBy(String decisionBy) { this.decisionBy = decisionBy; }

    public String getDecisionAt() { return decisionAt; }
    public void setDecisionAt(String decisionAt) { this.decisionAt = decisionAt; }
}
