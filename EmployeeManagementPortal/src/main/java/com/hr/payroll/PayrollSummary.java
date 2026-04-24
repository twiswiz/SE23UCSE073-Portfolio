package com.hr.payroll;

public class PayrollSummary {
    private String username;
    private double hoursWorked;
    private double monthlySalary;
    private double payableAmount; // prorated

    public PayrollSummary() {}

    public PayrollSummary(String username, double hoursWorked, double monthlySalary, double payableAmount) {
        this.username = username;
        this.hoursWorked = hoursWorked;
        this.monthlySalary = monthlySalary;
        this.payableAmount = payableAmount;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public double getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(double hoursWorked) { this.hoursWorked = hoursWorked; }

    public double getMonthlySalary() { return monthlySalary; }
    public void setMonthlySalary(double monthlySalary) { this.monthlySalary = monthlySalary; }

    public double getPayableAmount() { return payableAmount; }
    public void setPayableAmount(double payableAmount) { this.payableAmount = payableAmount; }
}
