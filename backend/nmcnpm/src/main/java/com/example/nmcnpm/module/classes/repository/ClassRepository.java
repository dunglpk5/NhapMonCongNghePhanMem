package com.example.nmcnpm.module.classes.repository;

import com.example.nmcnpm.module.classes.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {

    List<ClassEntity> findByGradeLevel(Integer gradeLevel);

    List<ClassEntity> findByStatus(String status);
}
