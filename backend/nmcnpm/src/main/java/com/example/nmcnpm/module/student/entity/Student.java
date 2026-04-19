// package com.example.nmcnpm.module.student.entity;

// import java.time.LocalDate;

// /**
//  * Entity ánh xạ bảng Students trong DB.
//  * Chỉ dùng nội bộ trong Student Module —
//  * module khác không được dùng entity này trực tiếp.
//  */
// @Entity
// @Table(name = "Students")
// public class Student {

//     @Id
//     @Column(name = "student_id", length = 10)
//     private String studentId;

//     @Column(name = "full_name", nullable = false, length = 100)
//     private String fullName;

//     @Column(name = "dob")
//     private LocalDate dob;

//     @Column(name = "gender", length = 10)
//     private String gender;

//     @Column(name = "address", length = 255)
//     private String address;

//     @Column(name = "ethnicity", length = 50)
//     private String ethnicity;

//     @Column(name = "religion", length = 50)
//     private String religion;

//     @Column(name = "father_name", length = 100)
//     private String fatherName;

//     @Column(name = "mother_name", length = 100)
//     private String motherName;

//     @Column(name = "phone", length = 15)
//     private String phone;

//     @Column(name = "class_id")
//     private Integer classId;

//     // ── Getters / Setters ──────────────────────────────────────────────────

//     public String getStudentId()                        { return studentId; }
//     public void   setStudentId(String studentId)        { this.studentId = studentId; }

//     public String getFullName()                         { return fullName; }
//     public void   setFullName(String fullName)          { this.fullName = fullName; }

//     public LocalDate getDob()                           { return dob; }
//     public void      setDob(LocalDate dob)              { this.dob = dob; }

//     public String getGender()                           { return gender; }
//     public void   setGender(String gender)              { this.gender = gender; }

//     public String getAddress()                          { return address; }
//     public void   setAddress(String address)            { this.address = address; }

//     public String getEthnicity()                        { return ethnicity; }
//     public void   setEthnicity(String ethnicity)        { this.ethnicity = ethnicity; }

//     public String getReligion()                         { return religion; }
//     public void   setReligion(String religion)          { this.religion = religion; }

//     public String getFatherName()                       { return fatherName; }
//     public void   setFatherName(String fatherName)      { this.fatherName = fatherName; }

//     public String getMotherName()                       { return motherName; }
//     public void   setMotherName(String motherName)      { this.motherName = motherName; }

//     public String getPhone()                            { return phone; }
//     public void   setPhone(String phone)                { this.phone = phone; }

//     public Integer getClassId()                         { return classId; }
//     public void    setClassId(Integer classId)          { this.classId = classId; }
// }
