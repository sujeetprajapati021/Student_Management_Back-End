package com.example.demo.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum AdminStatus {

    New(201),
    Active(202),
    Inactive(203);

    private Integer lookupId;

    private AdminStatus(Integer lookupId) {
        this.lookupId = lookupId;
    }

    public Integer getLookupId() {
        return this.lookupId;
    }

    public static Integer findLookupIdByName(String name) {
        for (AdminStatus admin : AdminStatus.values()) {

            if (admin.name().equals(name)) {
                return admin.getLookupId();
            }
        }
        return null;
    }

    public static String findAdminTypeByLookupId(Integer lookupId) {
        for (AdminStatus admin : AdminStatus.values()) {
            if (admin.getLookupId().equals(lookupId)) {
                return admin.name();
            }
        }
        return null;
    }
}
