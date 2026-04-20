package com.example.nmcnpm.module.core.enums;

/**
 * UserRole enum – ánh xạ trực tiếp từ class diagram (FR-06).
 * 5 vai trò: admin, teacher, office_staff, principal, student.
 */
public enum UserRole {
    ADMIN,
    TEACHER,
    OFFICE_STAFF,
    PRINCIPAL,
    STUDENT;

    /**
     * Trả về URL dashboard tương ứng với vai trò (FR-07).
     */
    public String getDashboardUrl() {
        return switch (this) {
            case ADMIN        -> "/dashboard/admin";
            case TEACHER      -> "/dashboard/teacher";
            case OFFICE_STAFF -> "/dashboard/office";
            case PRINCIPAL    -> "/dashboard/principal";
            case STUDENT      -> "/dashboard/student";
        };
    }
}
