package com.example.nmcnpm.module.classes.repository;

import com.example.nmcnpm.module.classes.entity.ClassEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClassRepositoryTest {

    @Autowired
    private ClassRepository classRepository;

    @BeforeEach
    void setUp() {
        // Clean up repository before each test
        classRepository.deleteAll();
        // Insert sample data
        ClassEntity class1 = new ClassEntity();
        class1.setClassName("Math 101");
        class1.setGradeLevel(10);
        class1.setStatus("active");
        classRepository.save(class1);

        ClassEntity class2 = new ClassEntity();
        class2.setClassName("Science 101");
        class2.setGradeLevel(10);
        class2.setStatus("inactive");
        classRepository.save(class2);

        ClassEntity class3 = new ClassEntity();
        class3.setClassName("History 101");
        class3.setGradeLevel(11);
        class3.setStatus("active");
        classRepository.save(class3);
    }

    @Test
    @DisplayName("findByGradeLevel should return classes of given grade")
    void testFindByGradeLevel() {
        List<ClassEntity> grade10 = classRepository.findByGradeLevel(10);
        assertEquals(2, grade10.size(), "There should be two classes for grade 10");
        assertTrue(grade10.stream().anyMatch(c -> "Math 101".equals(c.getClassName())));
        assertTrue(grade10.stream().anyMatch(c -> "Science 101".equals(c.getClassName())));

        List<ClassEntity> grade11 = classRepository.findByGradeLevel(11);
        assertEquals(1, grade11.size());
        assertEquals("History 101", grade11.get(0).getClassName());
    }

    @Test
    @DisplayName("findByStatus should return classes with matching status")
    void testFindByStatus() {
        List<ClassEntity> active = classRepository.findByStatus("active");
        assertEquals(2, active.size());
        assertTrue(active.stream().anyMatch(c -> "Math 101".equals(c.getClassName())));
        assertTrue(active.stream().anyMatch(c -> "History 101".equals(c.getClassName())));

        List<ClassEntity> inactive = classRepository.findByStatus("inactive");
        assertEquals(1, inactive.size());
        assertEquals("Science 101", inactive.get(0).getClassName());
    }
}
