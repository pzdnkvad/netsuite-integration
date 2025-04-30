package com.vse.bp.netsuite_integration.util;

import com.vse.bp.netsuite_integration.model.ExpenseReportXml;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;

public class XmlMarshallerUtil {

    public static String marshalExpenseReport(ExpenseReportXml report) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ExpenseReportXml.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        marshaller.marshal(report, writer);

        return writer.toString();
    }
}
