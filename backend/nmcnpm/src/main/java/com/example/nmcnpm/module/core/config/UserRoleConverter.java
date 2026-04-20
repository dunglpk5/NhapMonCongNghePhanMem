package com.example.nmcnpm.module.core.config;

import com.example.nmcnpm.module.core.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA AttributeConverter để map giữa DB (lowercase: 'admin', 'teacher'...)
 * và Java enum (UPPERCASE: ADMIN, TEACHER...).
 *
 * DB schema dùng CHECK constraint với giá trị lowercase,
 * nhưng Java enum theo convention dùng UPPERCASE.
 */
@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        if (role == null) return null;
        return role.name().toLowerCase();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;
        return UserRole.valueOf(dbValue.toUpperCase());
    }
}
