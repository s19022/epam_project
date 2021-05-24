package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.AccountRole;

public class FilterUtils {
    public static AccountRole getAccountRole(Object attribute) {
        try {
            if (attribute instanceof AccountRole) {
                return (AccountRole) attribute;
            }
            if (attribute instanceof String) {
                return AccountRole.valueOf((String) attribute);
            }
        } catch (RuntimeException ignore) {}
        return AccountRole.UNKNOWN;
    }
}
