package com.vse.bp.netsuite_integration.model;

import java.util.Date;
import java.util.List;

public class ExpenseReportFlat {
    private String reportID;
    private String reportNumber;
    private String status;
    private Date createdDate;
    private Date dueDate;
    private String department;
    private String account;
    private String location;
    private String employeeID;
    private String employeeName;
    private String amount;
    private Double totalApproved;
    private Boolean isFull;
    private String currencyName;
    private Integer currency;
    private String approvalStatus;
    private String accountingApproval;
    private Double advance;
    private String description;
    private String corporateCreditCard;

    private List<ExpenseLineFlat> lines;

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Double getTotalApproved() {
        return totalApproved;
    }

    public void setTotalApproved(Double totalApproved) {
        this.totalApproved = totalApproved;
    }

    public Boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getAccountingApproval() {
        return accountingApproval;
    }

    public void setAccountingApproval(String accountingApproval) {
        this.accountingApproval = accountingApproval;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public List<ExpenseLineFlat> getLines() {
        return lines;
    }

    public void setLines(List<ExpenseLineFlat> lines) {
        this.lines = lines;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCorporateCreditCard() {
        return corporateCreditCard;
    }

    public void setCorporateCreditCard(String corporateCreditCard) {
        this.corporateCreditCard = corporateCreditCard;
    }

    // Вложенный класс
    public static class ExpenseLineFlat {
        private Integer lineNumber;
        private String account;
        private String amount;
        private String status;
        private String category;
        private String allocatedDepartment;
        private String allocatedLocation;
        private String corporateCreditCard;

        public Integer getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(Integer lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAllocatedDepartment() {
            return allocatedDepartment;
        }

        public void setAllocatedDepartment(String allocatedDepartment) {
            this.allocatedDepartment = allocatedDepartment;
        }

        public String getAllocatedLocation() {
            return allocatedLocation;
        }

        public void setAllocatedLocation(String allocatedLocation) {
            this.allocatedLocation = allocatedLocation;
        }

        public String getCorporateCreditCard() {
            return corporateCreditCard;
        }

        public void setCorporateCreditCard(String corporateCreditCard) {
            this.corporateCreditCard = corporateCreditCard;
        }

    }
}