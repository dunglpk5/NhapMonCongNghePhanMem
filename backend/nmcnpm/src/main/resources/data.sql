-- ═══════════════════════════════════════════════════════════
-- Dữ liệu mẫu: Năm học & Lớp học
-- ═══════════════════════════════════════════════════════════

-- Năm học hiện tại
IF NOT EXISTS (SELECT 1 FROM academic_years WHERE year_id = 1)
BEGIN
    SET IDENTITY_INSERT academic_years ON;
    INSERT INTO academic_years (year_id, year_name, start_date, end_date, is_current)
    VALUES (1, N'2025-2026', '2025-09-05', '2026-05-31', 1);
    SET IDENTITY_INSERT academic_years OFF;
END

-- Lớp Khối 10
IF NOT EXISTS (SELECT 1 FROM classes WHERE class_name = N'10A1')
BEGIN
    INSERT INTO classes (class_name, grade_level, year_id, number_of_students, status)
    VALUES
        (N'10A1', 10, 1, 0, N'active'),
        (N'10A2', 10, 1, 0, N'active'),
        (N'10A3', 10, 1, 0, N'active'),
        (N'10A4', 10, 1, 0, N'active'),
        (N'10A5', 10, 1, 0, N'active');
END

-- Lớp Khối 11
IF NOT EXISTS (SELECT 1 FROM classes WHERE class_name = N'11A1')
BEGIN
    INSERT INTO classes (class_name, grade_level, year_id, number_of_students, status)
    VALUES
        (N'11A1', 11, 1, 0, N'active'),
        (N'11A2', 11, 1, 0, N'active'),
        (N'11A3', 11, 1, 0, N'active'),
        (N'11A4', 11, 1, 0, N'active'),
        (N'11A5', 11, 1, 0, N'active');
END

-- Lớp Khối 12
IF NOT EXISTS (SELECT 1 FROM classes WHERE class_name = N'12A1')
BEGIN
    INSERT INTO classes (class_name, grade_level, year_id, number_of_students, status)
    VALUES
        (N'12A1', 12, 1, 0, N'active'),
        (N'12A2', 12, 1, 0, N'active'),
        (N'12A3', 12, 1, 0, N'active'),
        (N'12A4', 12, 1, 0, N'active'),
        (N'12A5', 12, 1, 0, N'active');
END
