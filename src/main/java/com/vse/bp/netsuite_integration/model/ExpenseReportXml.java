package com.vse.bp.netsuite_integration.model;

import jakarta.xml.bind.annotation.*;

import java.util.Date;
import java.util.List;

@XmlRootElement(name = "ExpenseReport", namespace = "urn:employees_2021_2.transactions.webservices.netsuite.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExpenseReportXml {

    @XmlElement
    private String reportID;

    @XmlElement
    private String reportNumber;

    @XmlElement
    private String status;

    @XmlElement
    private Date createdDate;

    @XmlElement
    private String amount;

    @XmlElement
    private Date dueDate;

    @XmlElement
    private String department;

    @XmlElement
    private String account;

    @XmlElement
    private String location;

    @XmlElement
    private String employeeID;

    @XmlElement
    private String employeeName;

    @XmlElement
    private String currencyName;

    @XmlElement
    private Integer currency;

    @XmlElement
    private String approvalStatus;

    @XmlElement
    private String accountingApproval;

    @XmlElement
    private Double advance;

    @XmlElement
    private String description;

    @XmlElement
    private Double totalApproved;

    @XmlElement
    private Boolean isFull;

    @XmlElementWrapper(name = "expenseList")
    @XmlElement(name = "expense")
    private List<ExpenseLineXml> expenseList;

    // другие нужные поля...

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<ExpenseLineXml> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<ExpenseLineXml> expenseList) {
        this.expenseList = expenseList;
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

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public Double getTotalApproved() {
        return totalApproved;
    }

    public void setTotalApproved(Double totalApproved) {
        this.totalApproved = totalApproved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public String getAccountingApproval() {
        return accountingApproval;
    }

    public void setAccountingApproval(String accountingApproval) {
        this.accountingApproval = accountingApproval;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    // геттеры/сеттеры

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ExpenseLineXml {

        @XmlElement
        private Integer lineNumber;

        @XmlElement
        private String amount;

        @XmlElement
        private String category;

        @XmlElement
        private String status;
        @XmlElement
        private String account;
        @XmlElement
        private String allocatedDepartment;
        @XmlElement
        private String allocatedLocation;
        @XmlElement
        private String corporateCreditCard;
        @XmlElement
        private String description;
        @XmlElement
        private String expenseType;
        @XmlElement
        private String expenseDate;
        @XmlElement
        private String currency;
        @XmlElement
        private String exchangeRate;
        @XmlElement
        private String taxAmount;
        @XmlElement
        private String taxCode;
        @XmlElement
        private String taxRate;
        @XmlElement
        private String taxType;
        // другие поля

        public Integer getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(Integer lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExpenseType() {
            return expenseType;
        }

        public void setExpenseType(String expenseType) {
            this.expenseType = expenseType;
        }

        public String getExpenseDate() {
            return expenseDate;
        }

        public void setExpenseDate(String expenseDate) {
            this.expenseDate = expenseDate;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(String exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }

        public String getTaxCode() {
            return taxCode;
        }

        public void setTaxCode(String taxCode) {
            this.taxCode = taxCode;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public String getTaxType() {
            return taxType;
        }

        public void setTaxType(String taxType) {
            this.taxType = taxType;
        }

        // геттеры/сеттеры
    }
}