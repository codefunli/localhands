package com.nineplus.localhand.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ConvertResponseUtils {

    private static HashMap<String, String> itemDocument = new HashMap<>();

    static {
        itemDocument.put("documentCode","DOCUMENT_CODE");
        itemDocument.put("department","DEPARTMENT");
        itemDocument.put("documentOwner","DOCUMENT_OWNER");
        itemDocument.put("documentReceiver","DOCUMENT_RECEIVER");
        itemDocument.put("templateId","ID");
        itemDocument.put("createdDate","CREATED_DATE");
        itemDocument.put("createdUser","CREATED_USER");
        itemDocument.put("templateName","TEMPLATE_NAME");
    }

    String convertResponseDocument (String item) {
        String columnMapped = "";
        if (itemDocument.containsKey(item)) {
            columnMapped = itemDocument.get(item);
        }
        return columnMapped;
    }
}
