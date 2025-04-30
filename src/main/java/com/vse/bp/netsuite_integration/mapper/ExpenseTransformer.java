package com.vse.bp.netsuite_integration.mapper;

import com.vse.bp.netsuite_integration.model.ExpenseReportFlat;
import com.vse.bp.netsuite_integration.model.ExpenseReportXml;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTransformer {
    public static ExpenseReportXml transformToXml(ExpenseReportFlat flat) {
        ExpenseReportXml xml = new ExpenseReportXml();

        // 💼 Базовые поля
        xml.setReportID(flat.getReportID());
        xml.setReportNumber(flat.getReportNumber());
        xml.setStatus(flat.getStatus());
        xml.setCreatedDate(flat.getCreatedDate());
        xml.setDueDate(flat.getCreatedDate());
        xml.setDepartment(flat.getDepartment());
        xml.setAccount(flat.getAccount());
        xml.setLocation(flat.getLocation());
        xml.setEmployeeID(flat.getEmployeeID());
        xml.setEmployeeName(flat.getEmployeeName());
        xml.setAmount(flat.getAmount());
        xml.setTotalApproved(flat.getTotalApproved());
        xml.setIsFull(flat.getIsFull());
        xml.setCurrencyName(flat.getCurrencyName());
        xml.setCurrency(flat.getCurrency());
        xml.setApprovalStatus(flat.getApprovalStatus());
        xml.setAccountingApproval(flat.getAccountingApproval());
        xml.setAdvance(flat.getAdvance());
        xml.setDescription(flat.getDescription());

        // 📋 Список строк расходов
        List<ExpenseReportXml.ExpenseLineXml> xmlLines = new ArrayList<>();
        if (flat.getLines() != null) {
            for (ExpenseReportFlat.ExpenseLineFlat line : flat.getLines()) {
                ExpenseReportXml.ExpenseLineXml xmlLine = new ExpenseReportXml.ExpenseLineXml();
                xmlLine.setLineNumber(line.getLineNumber());
                xmlLine.setAccount(line.getAccount());
                xmlLine.setAmount(line.getAmount());
                xmlLine.setStatus(line.getStatus());
                xmlLine.setCategory(line.getCategory());
                xmlLine.setAllocatedDepartment(line.getAllocatedDepartment());
                xmlLine.setAllocatedLocation(line.getAllocatedLocation());
                xmlLines.add(xmlLine);
            }
        }
        xml.setExpenseList(xmlLines);

        return xml;
    }
}
