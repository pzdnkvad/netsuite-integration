package com.vse.bp.netsuite_integration.mapper;

import com.vse.bp.netsuite_integration.model.ExpenseReportFlat;
import com.vse.bp.netsuite_integration.model.ExpenseReportXml;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTransformerTest {

    @Test
    public void testTransformToXml_WithAllFieldsPopulated_NoNullFields() {
        ExpenseReportFlat flat = new ExpenseReportFlat();
        flat.setReportID("123");
        flat.setReportNumber("RPT-001");
        flat.setStatus("Pending");
        flat.setCreatedDate(new Date());
        flat.setDueDate(new Date());
        flat.setDepartment("Finance");
        flat.setAccount("1234");
        flat.setLocation("Office");
        flat.setEmployeeID("EMP001");
        flat.setEmployeeName("John Doe");
        flat.setAmount("1000.00");
        flat.setTotalApproved(950.00);
        flat.setIsFull(true);
        flat.setCurrencyName("USD");
        flat.setCurrency(1);
        flat.setApprovalStatus("Approved");
        flat.setAccountingApproval("Yes");
        flat.setAdvance(500.00);
        flat.setDescription("Travel expenses");

        ExpenseReportFlat.ExpenseLineFlat lineFlat = new ExpenseReportFlat.ExpenseLineFlat();
        lineFlat.setLineNumber(1);
        lineFlat.setAccount("5678");
        lineFlat.setAmount("500.00");
        lineFlat.setStatus("Completed");
        lineFlat.setCategory("Transport");
        lineFlat.setAllocatedDepartment("Sales");
        lineFlat.setAllocatedLocation("Remote");
        List<ExpenseReportFlat.ExpenseLineFlat> lines = new ArrayList<>();
        lines.add(lineFlat);
        flat.setLines(lines);

        ExpenseReportXml xml = ExpenseTransformer.transformToXml(flat);

        assertEquals(flat.getReportID(), xml.getReportID());
        assertEquals(flat.getReportNumber(), xml.getReportNumber());
        assertEquals(flat.getStatus(), xml.getStatus());
        assertEquals(flat.getCreatedDate(), xml.getCreatedDate());
        assertEquals(flat.getCreatedDate(), xml.getDueDate());
        assertEquals(flat.getDepartment(), xml.getDepartment());
        assertEquals(flat.getAccount(), xml.getAccount());
        assertEquals(flat.getLocation(), xml.getLocation());
        assertEquals(flat.getEmployeeID(), xml.getEmployeeID());
        assertEquals(flat.getEmployeeName(), xml.getEmployeeName());
        assertEquals(flat.getAmount(), xml.getAmount());
        assertEquals(flat.getTotalApproved(), xml.getTotalApproved());
        assertEquals(flat.getIsFull(), xml.getFull());
        assertEquals(flat.getCurrencyName(), xml.getCurrencyName());
        assertEquals(flat.getCurrency(), xml.getCurrency());
        assertEquals(flat.getApprovalStatus(), xml.getApprovalStatus());
        assertEquals(flat.getAccountingApproval(), xml.getAccountingApproval());
        assertEquals(flat.getAdvance(), xml.getAdvance());
        assertEquals(flat.getDescription(), xml.getDescription());

        assertEquals(1, xml.getExpenseList().size());
        ExpenseReportXml.ExpenseLineXml xmlLine = xml.getExpenseList().get(0);
        assertEquals(lineFlat.getLineNumber(), xmlLine.getLineNumber());
        assertEquals(lineFlat.getAccount(), xmlLine.getAccount());
        assertEquals(lineFlat.getAmount(), xmlLine.getAmount());
        assertEquals(lineFlat.getStatus(), xmlLine.getStatus());
        assertEquals(lineFlat.getCategory(), xmlLine.getCategory());
        assertEquals(lineFlat.getAllocatedDepartment(), xmlLine.getAllocatedDepartment());
        assertEquals(lineFlat.getAllocatedLocation(), xmlLine.getAllocatedLocation());
    }

    @Test
    public void testTransformToXml_WithNullFields_ResultsInDefaultInitialization() {
        ExpenseReportFlat flat = new ExpenseReportFlat();
        flat.setLines(null); // No lines data

        ExpenseReportXml xml = ExpenseTransformer.transformToXml(flat);

        assertNull(xml.getReportID());
        assertNull(xml.getReportNumber());
        assertNull(xml.getStatus());
        assertNull(xml.getCreatedDate());
        assertNull(xml.getDueDate());
        assertNull(xml.getDepartment());
        assertNull(xml.getAccount());
        assertNull(xml.getLocation());
        assertNull(xml.getEmployeeID());
        assertNull(xml.getEmployeeName());
        assertNull(xml.getAmount());
        assertNull(xml.getTotalApproved());
        assertNull(xml.getFull());
        assertNull(xml.getCurrencyName());
        assertNull(xml.getCurrency());
        assertNull(xml.getApprovalStatus());
        assertNull(xml.getAccountingApproval());
        assertNull(xml.getAdvance());
        assertNull(xml.getDescription());

        assertTrue(xml.getExpenseList().isEmpty());
    }

    @Test
    public void testTransformToXml_WithEmptyLines_ResultsInEmptyXmlLines() {
        ExpenseReportFlat flat = new ExpenseReportFlat();
        flat.setLines(new ArrayList<>()); // Empty list for lines

        ExpenseReportXml xml = ExpenseTransformer.transformToXml(flat);

        assertTrue(xml.getExpenseList().isEmpty());
    }
}