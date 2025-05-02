package com.vse.bp.netsuite_integration.model.xml;

import com.vse.bp.netsuite_integration.model.ExpenseReportXml;
import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory() {
    }

    public ExpenseReportXml createExpenseReportXml() {
        return new ExpenseReportXml();
    }

    public ExpenseReportXml.ExpenseLineXml createExpenseLineXml() {
        return new ExpenseReportXml.ExpenseLineXml();
    }
}
