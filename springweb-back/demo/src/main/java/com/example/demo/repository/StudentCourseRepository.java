package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.StudentCourse;

public interface StudentCourseRepository extends CrudRepository<StudentCourse, UUID>{
    @Query("""
            SELECT
                s.id,
                s.id_student,
                s.id_course 
            FROM StudentCourse s
            WHERE s.id_student = :id_student
            """)
    List<StudentCourse> findByStudent(@Param("id_student")String id_student);

    @Query("""
            SELECT
                s.id,
                s.id_student,
                s.id_course
            FROM StudentCourse s
            WHERE s.id_course = :id_course
            """)
    List<StudentCourse> findByCourse(@Param("id_course")String id_course);

    @Query("""
            DELETE FROM StudentCourse s
            WHERE s.id_student = :id_student
            """)
    void deleteByStudent(@Param("id_student")String id_student);

    @Query("""
            DELETE FROM StudentCourse s
            WHERE s.id_course = :id_course
            """)
    void deleteByCourse(@Param("id_course")String id_course);
}
