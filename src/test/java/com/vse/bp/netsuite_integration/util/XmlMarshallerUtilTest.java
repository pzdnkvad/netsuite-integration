package com.vse.bp.netsuite_integration.util;

import com.vse.bp.netsuite_integration.model.ExpenseReportXml;
import com.vse.bp.netsuite_integration.model.ExpenseReportXml.ExpenseLineXml;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlMarshallerUtilTest {

    /**
     * Tests for XmlMarshallerUtil's marshalExpenseReport method.
     * This method serializes an ExpenseReportXml object into an XML string.
     */

    @Test
    public void testMarshalExpenseReport_Success() throws JAXBException {
        // Arrange
        ExpenseReportXml report = new ExpenseReportXml();
        report.setReportID("12345");
        report.setEmployeeName("John Doe");
        report.setAmount("1000.00");
        report.setCreatedDate(new Date());

        // Act
        String result = XmlMarshallerUtil.marshalExpenseReport(report);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.contains("<ExpenseReport"));
        Assertions.assertTrue(result.contains("<reportID>12345</reportID>"));
        Assertions.assertTrue(result.contains("<employeeName>John Doe</employeeName>"));
        Assertions.assertTrue(result.contains("<amount>1000.00</amount>"));
    }

    @Test
    public void testMarshalExpenseReport_WithNestedObjects() throws JAXBException {
        // Arrange
        ExpenseReportXml report = new ExpenseReportXml();
        report.setReportID("12345");
        report.setEmployeeName("John Doe");

        ExpenseLineXml expenseLine = new ExpenseLineXml();
        expenseLine.setLineNumber(1);
        expenseLine.setAmount("500.00");
        expenseLine.setCategory("Travel");

        List<ExpenseLineXml> expenseList = new ArrayList<>();
        expenseList.add(expenseLine);
        report.setExpenseList(expenseList);

        // Act
        String result = XmlMarshallerUtil.marshalExpenseReport(report);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.contains("<expenseList>"));
        Assertions.assertTrue(result.contains("<expense>"));
        Assertions.assertTrue(result.contains("<lineNumber>1</lineNumber>"));
        Assertions.assertTrue(result.contains("<amount>500.00</amount>"));
        Assertions.assertTrue(result.contains("<category>Travel</category>"));
    }

    @Test
    public void testMarshalExpenseReport_EmptyObject() throws JAXBException {
        // Arrange
        ExpenseReportXml report = new ExpenseReportXml();

        // Act
        String result = XmlMarshallerUtil.marshalExpenseReport(report);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.contains("<ExpenseReport"));
    }

    @Test
    public void testMarshalExpenseReport_NullExpenseList() throws JAXBException {
        // Arrange
        ExpenseReportXml report = new ExpenseReportXml();
        report.setReportID("67890");
        report.setEmployeeName("Jane Smith");

        // Act
        String result = XmlMarshallerUtil.marshalExpenseReport(report);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.contains("<reportID>67890</reportID>"));
        Assertions.assertTrue(result.contains("<employeeName>Jane Smith</employeeName>"));
        Assertions.assertFalse(result.contains("<expenseList>"));
    }

    @Test
    public void testMarshalExpenseReport_NullObject() {
        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> {
            XmlMarshallerUtil.marshalExpenseReport(null);
        });
    }
}