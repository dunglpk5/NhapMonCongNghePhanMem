CREATE DATABASE SchoolManagementSystem;
GO

USE SchoolManagementSystem;
GO
CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) UNIQUE NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    password_hash NVARCHAR(255) NOT NULL, -- NFR-10
    full_name NVARCHAR(100) NOT NULL,
    role NVARCHAR(20) CHECK (role IN (N'admin', N'teacher', N'office_staff', N'principal', N'student')), -- FR-06
    status NVARCHAR(10) CHECK (status IN (N'active', N'locked')) DEFAULT N'active', -- FR-04
    failed_login_attempts INT DEFAULT 0, -- NFR-13
    last_login_at DATETIME,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);
GO
CREATE TABLE password_resets (
    reset_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT FOREIGN KEY REFERENCES users(user_id) ON DELETE CASCADE,
    token NVARCHAR(255) NOT NULL,
    expires_at DATETIME NOT NULL,
    used BIT DEFAULT 0
);
GO
CREATE TABLE academic_years (
    year_id INT IDENTITY(1,1) PRIMARY KEY,
    year_name NVARCHAR(20) NOT NULL UNIQUE, -- '2024-2025'
    is_current BIT DEFAULT 0, -- FR-32
    start_date DATE,
    end_date DATE,
    created_at DATETIME DEFAULT GETDATE()
);
GO
CREATE TABLE students (
    student_id NVARCHAR(20) PRIMARY KEY, -- 'HS' + STT
    full_name NVARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender bit,
    address NVARCHAR(255),
    ethnicity NVARCHAR(50),
    religion NVARCHAR(50),
    father_name NVARCHAR(100),
    mother_name NVARCHAR(100),
    phone NVARCHAR(15),
    user_id INT UNIQUE FOREIGN KEY REFERENCES users(user_id) ON DELETE SET NULL, -- FR-34
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);
GO
CREATE TABLE classes (
    class_id INT IDENTITY(1,1) PRIMARY KEY,
    class_name NVARCHAR(20) NOT NULL,
    grade_level INT CHECK (grade_level BETWEEN 1 AND 12),
    year_id INT FOREIGN KEY REFERENCES academic_years(year_id) ON DELETE CASCADE,
    user_id INT UNIQUE FOREIGN KEY REFERENCES users(user_id) ON DELETE SET NULL, -- FR-18, FR-19
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);
GO
CREATE TABLE student_class (
    student_id NVARCHAR(20) FOREIGN KEY REFERENCES students(student_id) ON DELETE CASCADE,
    class_id INT FOREIGN KEY REFERENCES classes(class_id) ON DELETE CASCADE,
    year_id INT FOREIGN KEY REFERENCES academic_years(year_id),
    enrolled_date DATE DEFAULT GETDATE(),
    PRIMARY KEY (student_id, class_id, year_id)
);
GO
CREATE TABLE subjects (
    subject_id NVARCHAR(10) PRIMARY KEY, -- mã môn
    subject_name NVARCHAR(100) NOT NULL,
    periods_per_week INT CHECK (periods_per_week > 0),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);
GO
CREATE TABLE teaching_assignments (
    assignment_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT FOREIGN KEY REFERENCES users(user_id) ON DELETE CASCADE,
    subject_id NVARCHAR(10) FOREIGN KEY REFERENCES subjects(subject_id) ON DELETE CASCADE,
    class_id INT FOREIGN KEY REFERENCES classes(class_id) ON DELETE CASCADE,
    year_id INT FOREIGN KEY REFERENCES academic_years(year_id),
    semester NVARCHAR(10) CHECK (semester IN (N'1', N'2')),
    approved BIT DEFAULT 0,
    approved_at DATETIME,
    created_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT UQ_teaching_assignment UNIQUE (user_id, class_id, subject_id, semester, year_id)
);
GO
CREATE TABLE scores (
    score_id INT IDENTITY(1,1) PRIMARY KEY,
    student_id NVARCHAR(20) FOREIGN KEY REFERENCES students(student_id) ON DELETE CASCADE,
    subject_id NVARCHAR(10) FOREIGN KEY REFERENCES subjects(subject_id) ON DELETE CASCADE,
    year_id INT FOREIGN KEY REFERENCES academic_years(year_id),
    semester NVARCHAR(10) CHECK (semester IN (N'1', N'2')),
    score_type NVARCHAR(20) CHECK (score_type IN (N'oral', N'15min', N'1period', N'final')),
    score DECIMAL(3,1) CHECK (score >= 0 AND score <= 10), -- FR-21
    user_id INT FOREIGN KEY REFERENCES users(user_id),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);
GO
CREATE TABLE semester_averages (
    avg_id INT IDENTITY(1,1) PRIMARY KEY,
    student_id NVARCHAR(20) FOREIGN KEY REFERENCES students(student_id) ON DELETE CASCADE,
    subject_id NVARCHAR(10) FOREIGN KEY REFERENCES subjects(subject_id),
    year_id INT FOREIGN KEY REFERENCES academic_years(year_id),
    semester NVARCHAR(10) CHECK (semester IN (N'1', N'2')),
    avg_score DECIMAL(5,2),
    academic_rank NVARCHAR(20), -- FR-26
    calculated_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT UQ_semester_averages UNIQUE (student_id, subject_id, year_id, semester)
);
GO
CREATE TABLE conduct (
    conduct_id INT IDENTITY(1,1) PRIMARY KEY,
    student_id NVARCHAR(20) FOREIGN KEY REFERENCES students(student_id) ON DELETE CASCADE,
    year_id INT FOREIGN KEY REFERENCES academic_years(year_id),
    semester NVARCHAR(10) CHECK (semester IN (N'1', N'2')),
    conduct_rank NVARCHAR(10) CHECK (conduct_rank IN (N'Tốt', N'Khá', N'Trung bình', N'Yếu')),
    user_id INT FOREIGN KEY REFERENCES users(user_id),
    updated_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT UQ_conduct UNIQUE (student_id, year_id, semester)
);
GO
CREATE TABLE timetable (
    schedule_id INT IDENTITY(1,1) PRIMARY KEY,
    class_id INT FOREIGN KEY REFERENCES classes(class_id) ON DELETE CASCADE,
    subject_id NVARCHAR(10) FOREIGN KEY REFERENCES subjects(subject_id),
    user_id INT FOREIGN KEY REFERENCES users(user_id),
    day_of_week INT CHECK (day_of_week BETWEEN 2 AND 8), -- 2=Thứ 2, 8=Chủ nhật
    period INT CHECK (period BETWEEN 1 AND 10),
    room NVARCHAR(20),
    year_id INT FOREIGN KEY REFERENCES academic_years(year_id),
    semester NVARCHAR(10) CHECK (semester IN (N'1', N'2')),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT UQ_timetable_teacher UNIQUE (user_id, day_of_week, period, semester, year_id), -- FR-40
    CONSTRAINT UQ_timetable_room UNIQUE (room, day_of_week, period, semester, year_id) -- FR-40
);
GO
CREATE TABLE audit_logs (
    log_id INT IDENTITY(1,1) PRIMARY KEY,
    table_name NVARCHAR(100),
    action NVARCHAR(10) CHECK (action IN (N'INSERT', N'UPDATE', N'DELETE')),
    user_id INT FOREIGN KEY REFERENCES users(user_id),
    action_time DATETIME DEFAULT GETDATE(),
    old_data NVARCHAR(MAX),
    new_data NVARCHAR(MAX)
);
GO
CREATE TABLE error_logs (
    error_id INT IDENTITY(1,1) PRIMARY KEY,
    error_message NVARCHAR(MAX),
    error_stack NVARCHAR(MAX),
    user_id INT FOREIGN KEY REFERENCES users(user_id),
    error_time DATETIME DEFAULT GETDATE()
);
GO
CREATE TABLE system_config (
    config_key NVARCHAR(50) PRIMARY KEY,
    config_value NVARCHAR(MAX),
    description NVARCHAR(255),
    updated_at DATETIME DEFAULT GETDATE()
);
GO
CREATE TABLE user_sessions (
    session_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT FOREIGN KEY REFERENCES users(user_id) ON DELETE CASCADE,
    token NVARCHAR(255) NOT NULL,
    ip_address NVARCHAR(45),
    user_agent NVARCHAR(255),
    logged_in_at DATETIME DEFAULT GETDATE(),
    last_activity_at DATETIME DEFAULT GETDATE(),
    expires_at DATETIME NOT NULL,
    revoked BIT DEFAULT 0
);
GO
CREATE TABLE backup_history (
    backup_id INT IDENTITY(1,1) PRIMARY KEY,
    backup_file NVARCHAR(255),
    backup_size_mb DECIMAL(10,2),
    backup_location NVARCHAR(500),
    backup_time DATETIME DEFAULT GETDATE(),
    restored BIT DEFAULT 0,
    restored_at DATETIME,
    restored_by INT FOREIGN KEY REFERENCES users(user_id)
);
GO
-- Users indexes
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);

-- Students indexes
CREATE INDEX idx_students_name ON students(full_name);
CREATE INDEX idx_students_phone ON students(phone);
CREATE INDEX idx_students_user_id ON students(user_id);

-- Scores indexes
CREATE INDEX idx_scores_student ON scores(student_id, year_id, semester);
CREATE INDEX idx_scores_subject ON scores(subject_id);
CREATE INDEX idx_scores_created_at ON scores(created_at);

-- Timetable indexes
CREATE INDEX idx_timetable_class ON timetable(class_id);
CREATE INDEX idx_timetable_teacher ON timetable(user_id);
CREATE INDEX idx_timetable_day_period ON timetable(day_of_week, period);

-- Student_class indexes
CREATE INDEX idx_student_class_student ON student_class(student_id);
CREATE INDEX idx_student_class_class ON student_class(class_id);

-- Audit logs indexes
CREATE INDEX idx_audit_time ON audit_logs(action_time);
CREATE INDEX idx_audit_user ON audit_logs(user_id);

-- Teaching assignments indexes
CREATE INDEX idx_teaching_teacher ON teaching_assignments(user_id);
CREATE INDEX idx_teaching_class ON teaching_assignments(class_id);

-- Semester averages indexes
CREATE INDEX idx_semester_avg_student ON semester_averages(student_id);

go;

CREATE PROCEDURE sp_CreateStudent
    @full_name NVARCHAR(100),
    @date_of_birth DATE,
    @gender NVARCHAR(10),
    @address NVARCHAR(255),
    @ethnicity NVARCHAR(50),
    @religion NVARCHAR(50),
    @father_name NVARCHAR(100),
    @mother_name NVARCHAR(100),
    @phone NVARCHAR(15)
AS
BEGIN
    DECLARE @next_id INT;
    DECLARE @student_id NVARCHAR(20);
    
    -- Lấy số thứ tự tiếp theo
    SELECT @next_id = ISNULL(MAX(CAST(SUBSTRING(student_id, 3, LEN(student_id)-2) AS INT)), 0) + 1
    FROM students;
    
    -- Tạo mã HS + số thứ tự
    SET @student_id = N'HS' + FORMAT(@next_id, '000000');
    
    INSERT INTO students (
        student_id, full_name, date_of_birth, gender, address,
        ethnicity, religion, father_name, mother_name, phone
    ) VALUES (
        @student_id, @full_name, @date_of_birth, @gender, @address,
        @ethnicity, @religion, @father_name, @mother_name, @phone
    );
    
    SELECT @student_id AS student_id;
END;
GO

CREATE PROCEDURE sp_CalculateSubjectAverage
    @student_id NVARCHAR(20),
    @subject_id NVARCHAR(10),
    @year_id INT,
    @semester NVARCHAR(10)
AS
BEGIN
    DECLARE @avg_score DECIMAL(5,2);
    DECLARE @total_weight INT;
    DECLARE @weighted_sum DECIMAL(10,2);
    
    SELECT 
        @weighted_sum = SUM(
            CASE score_type
                WHEN N'oral' THEN score * 1
                WHEN N'15min' THEN score * 2
                WHEN N'1period' THEN score * 3
                WHEN N'final' THEN score * 4
            END
        ),
        @total_weight = SUM(
            CASE score_type
                WHEN N'oral' THEN 1
                WHEN N'15min' THEN 2
                WHEN N'1period' THEN 3
                WHEN N'final' THEN 4
            END
        )
    FROM scores
    WHERE student_id = @student_id
        AND subject_id = @subject_id
        AND year_id = @year_id
        AND semester = @semester;
    
    IF @total_weight > 0
        SET @avg_score = @weighted_sum / @total_weight;
    ELSE
        SET @avg_score = NULL;
    
    -- Cập nhật hoặc insert vào bảng semester_averages
    IF EXISTS (
        SELECT 1 FROM semester_averages 
        WHERE student_id = @student_id 
            AND subject_id = @subject_id 
            AND year_id = @year_id 
            AND semester = @semester
    )
    BEGIN
        UPDATE semester_averages 
        SET avg_score = @avg_score,
            calculated_at = GETDATE()
        WHERE student_id = @student_id 
            AND subject_id = @subject_id 
            AND year_id = @year_id 
            AND semester = @semester;
    END
    ELSE
    BEGIN
        INSERT INTO semester_averages (
            student_id, subject_id, year_id, semester, avg_score
        ) VALUES (
            @student_id, @subject_id, @year_id, @semester, @avg_score
        );
    END
END;
GO

CREATE PROCEDURE sp_ClassifyAcademicRank
    @student_id NVARCHAR(20),
    @year_id INT,
    @semester NVARCHAR(10)
AS
BEGIN
    DECLARE @overall_avg DECIMAL(5,2);
    DECLARE @academic_rank NVARCHAR(20);
    
    -- Tính điểm trung bình học kỳ (tất cả các môn)
    SELECT @overall_avg = AVG(avg_score)
    FROM semester_averages
    WHERE student_id = @student_id
        AND year_id = @year_id
        AND semester = @semester
        AND avg_score IS NOT NULL;
    
    -- Xếp loại theo quy định Bộ GD&ĐT
    IF @overall_avg >= 9.0
        SET @academic_rank = N'Xuất sắc';
    ELSE IF @overall_avg >= 8.0
        SET @academic_rank = N'Giỏi';
    ELSE IF @overall_avg >= 6.5
        SET @academic_rank = N'Khá';
    ELSE IF @overall_avg >= 5.0
        SET @academic_rank = N'Trung bình';
    ELSE IF @overall_avg >= 3.5
        SET @academic_rank = N'Yếu';
    ELSE
        SET @academic_rank = N'Kém';
    
    -- Cập nhật xếp loại
    UPDATE semester_averages
    SET academic_rank = @academic_rank
    WHERE student_id = @student_id
        AND year_id = @year_id
        AND semester = @semester;
END;
GO

CREATE TRIGGER trg_Audit_Students
ON students
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @action NVARCHAR(10);
    DECLARE @user_id INT;
    
    -- Lấy user_id từ session (cần truyền từ ứng dụng)
    SET @user_id = ISNULL(CAST(SESSION_CONTEXT(N'user_id') AS INT), 0);
    
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
        SET @action = N'UPDATE';
    ELSE IF EXISTS (SELECT * FROM inserted)
        SET @action = N'INSERT';
    ELSE IF EXISTS (SELECT * FROM deleted)
        SET @action = N'DELETE';
    
    IF @action = N'INSERT'
    BEGIN
        INSERT INTO audit_logs (table_name, action, user_id, new_data)
        SELECT N'students', @action, @user_id, 
               (SELECT * FROM inserted FOR JSON AUTO);
    END
    ELSE IF @action = N'UPDATE'
    BEGIN
        INSERT INTO audit_logs (table_name, action, user_id, old_data, new_data)
        SELECT N'students', @action, @user_id,
               (SELECT * FROM deleted FOR JSON AUTO),
               (SELECT * FROM inserted FOR JSON AUTO);
    END
    ELSE IF @action = N'DELETE'
    BEGIN
        INSERT INTO audit_logs (table_name, action, user_id, old_data)
        SELECT N'students', @action, @user_id,
               (SELECT * FROM deleted FOR JSON AUTO);
    END
END;
GO

CREATE TRIGGER trg_Update_Students_Timestamp
ON students
AFTER UPDATE
AS
BEGIN
    UPDATE students
    SET updated_at = GETDATE()
    FROM students s
    INNER JOIN inserted i ON s.student_id = i.student_id;
END;
GO

-- Tương tự cho các bảng khác
CREATE TRIGGER trg_Update_Users_Timestamp
ON users
AFTER UPDATE
AS
BEGIN
    UPDATE users
    SET updated_at = GETDATE()
    FROM users u
    INNER JOIN inserted i ON u.user_id = i.user_id;
END;
GO

CREATE TRIGGER trg_Check_Homeroom_Teacher
ON classes
AFTER INSERT, UPDATE
AS
BEGIN
    IF EXISTS (
        SELECT 1
        FROM classes c1
        JOIN classes c2 ON c1.user_id = c2.user_id
            AND c1.year_id = c2.year_id
            AND c1.class_id != c2.class_id
        WHERE c1.user_id IS NOT NULL
    )
    BEGIN
        RAISERROR(N'Một giáo viên không thể làm chủ nhiệm nhiều lớp trong cùng năm học', 16, 1);
        ROLLBACK TRANSACTION;
    END
END;
GO

CREATE VIEW v_StudentTranscript AS
SELECT 
    s.student_id,
    s.full_name,
    ay.year_name,
    sub.subject_name,
    AVG(CASE WHEN sc.semester = N'1' THEN sc.avg_score END) AS semester_1_avg,
    AVG(CASE WHEN sc.semester = N'2' THEN sc.avg_score END) AS semester_2_avg,
    (ISNULL(AVG(CASE WHEN sc.semester = N'1' THEN sc.avg_score END), 0) + 
     ISNULL(AVG(CASE WHEN sc.semester = N'2' THEN sc.avg_score END), 0) * 2) / 3 AS year_avg,
    c.conduct_rank
FROM students s
CROSS JOIN academic_years ay
LEFT JOIN semester_averages sc ON s.student_id = sc.student_id AND ay.year_id = sc.year_id
LEFT JOIN subjects sub ON sc.subject_id = sub.subject_id
LEFT JOIN conduct c ON s.student_id = c.student_id AND ay.year_id = c.year_id AND c.semester = N'2'
GROUP BY s.student_id, s.full_name, ay.year_name, sub.subject_name, c.conduct_rank;
GO

CREATE VIEW v_AcademicRankStatistics AS
SELECT 
    ay.year_name,
    sc.semester,
    sc.academic_rank,
    COUNT(DISTINCT sc.student_id) AS student_count,
    CAST(COUNT(DISTINCT sc.student_id) * 100.0 / SUM(COUNT(DISTINCT sc.student_id)) OVER (PARTITION BY ay.year_name, sc.semester) AS DECIMAL(5,2)) AS percentage
FROM semester_averages sc
JOIN academic_years ay ON sc.year_id = ay.year_id
WHERE sc.academic_rank IS NOT NULL
GROUP BY ay.year_name, sc.semester, sc.academic_rank;
GO

INSERT INTO system_config (config_key, config_value, description) VALUES
(N'session_timeout_minutes', N'15', N'Thời gian timeout session (phút)'),
(N'max_failed_login_attempts', N'5', N'Số lần đăng nhập sai tối đa'),
(N'backup_time', N'02:00', N'Thời gian sao lưu tự động hàng ngày'),
(N'system_name', N'Hệ thống quản lý trường học', N'Tên hệ thống'),
(N'school_name', N'Trường THPT Mẫu giáo', N'Tên trường');
GO

-- Password: Admin@123 (đã hash bằng bcrypt, cần thay đổi theo thuật toán thực tế)
INSERT INTO users (username, email, password_hash, full_name, role, status) VALUES
(N'admin', N'admin@school.edu.vn', N'$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/Lewd5jYhF7qFZkK7K', N'Quản trị viên hệ thống', N'admin', N'active');
GO

CREATE PROCEDURE sp_ExportStudentTranscriptPDF
    @student_id NVARCHAR(20),
    @year_id INT
AS
BEGIN
    SELECT 
        s.student_id AS N'Mã học sinh',
        s.full_name AS N'Họ và tên',
        s.date_of_birth AS N'Ngày sinh',
        s.gender AS N'Giới tính',
        ay.year_name AS N'Năm học',
        sub.subject_name AS N'Môn học',
        AVG(CASE WHEN sc.semester = N'1' THEN sc.avg_score END) AS N'Điểm TB HKI',
        AVG(CASE WHEN sc.semester = N'2' THEN sc.avg_score END) AS N'Điểm TB HKII',
        (ISNULL(AVG(CASE WHEN sc.semester = N'1' THEN sc.avg_score END), 0) + 
         ISNULL(AVG(CASE WHEN sc.semester = N'2' THEN sc.avg_score END), 0) * 2) / 3 AS N'Điểm TB cả năm',
        c.conduct_rank AS N'Hạnh kiểm',
        sc2.academic_rank AS N'Xếp loại học lực'
    FROM students s
    JOIN academic_years ay ON ay.year_id = @year_id
    LEFT JOIN semester_averages sc ON s.student_id = sc.student_id AND ay.year_id = sc.year_id
    LEFT JOIN subjects sub ON sc.subject_id = sub.subject_id
    LEFT JOIN conduct c ON s.student_id = c.student_id AND ay.year_id = c.year_id AND c.semester = N'2'
    LEFT JOIN semester_averages sc2 ON s.student_id = sc2.student_id 
        AND ay.year_id = sc2.year_id 
        AND sc2.semester = N'2'
        AND sc2.subject_id = (SELECT MIN(subject_id) FROM subjects) -- Lấy rank từ bất kỳ môn nào
    WHERE s.student_id = @student_id
    GROUP BY s.student_id, s.full_name, s.date_of_birth, s.gender, ay.year_name, 
             sub.subject_name, c.conduct_rank, sc2.academic_rank
    ORDER BY sub.subject_name;
END;
GO