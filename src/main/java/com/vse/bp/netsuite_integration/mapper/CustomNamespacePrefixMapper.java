package com.vse.bp.netsuite_integration.mapper;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

public class CustomNamespacePrefixMapper extends NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("urn:employees_2021_2.transactions.webservices.netsuite.com".equals(namespaceUri)) {
            return "tranEmp";
        }
        if ("urn:core_2021_2.platform.webservices.netsuite.com".equals(namespaceUri)) {
            return "platformCore";
        }
        return suggestion;
    }
}
