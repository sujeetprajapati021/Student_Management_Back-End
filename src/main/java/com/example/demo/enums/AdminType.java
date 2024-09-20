package com.example.demo.enums;

public enum AdminType {

    STUDENT(101),
    TEACHER(102),
    SUPER_ADMIN(103);

    private Integer lookupId;

    AdminType(Integer lookupId) {
        this.lookupId = lookupId;
    }

    public Integer getLookupId() {
        return this.lookupId;
    }

    public static Integer findLookupIdByName(String name) {
        for(AdminType admin : AdminType.values()) {

            if(admin.name().equals(name)) {
                return admin.getLookupId();
            }
        }
        return  null;
    }

    public static AdminType findAdminTypeByLookupId(Integer lookupId) {
        for(AdminType admin : AdminType.values()) {
            if(admin.getLookupId().equals(lookupId)) {
                return admin;
            }
        }
        return null;
    }
}
