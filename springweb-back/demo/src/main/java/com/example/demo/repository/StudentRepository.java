package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Student;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface StudentRepository extends CrudRepository<Student, UUID>{
    @Query(nativeQuery = true, value = """
        SELECT 
            s.id,
            s.name AS student_name,
            s.registration,
            c.name AS course_name,
            c.description
        FROM 
            Student AS s
        LEFT JOIN 
            StudentCourse AS u ON u.id_student = s.id
        LEFT JOIN 
            Course AS c ON c.id = u.id_course
        WHERE 
            s.registration = :registration
            """)
    List<Student> findByRegistrationWithCourse(String registration);

    List<Student> findByRegistration(String registration);

    @Query("""
        SELECT
            new com.example.demo.model.Student(s.id, s.name, s.registration)
        FROM Student s
        """)
    ArrayList<Student> findAllPersonalize();

    @Transactional
    @Modifying
    @Query(
        """
           UPDATE Student s SET s.name = :nameStudent,  s.registration = :registrationStudent WHERE s.registration = :registration
        """)
    void updateStudent(@Param("nameStudent") String nameStudent, @Param("registrationStudent") String registrationStudent, @Param("registration") String registration);

    @Transactional
    @Modifying
    @Query(
        "UPDATE Student s " +
        "SET s.name = :nameStudent " +
        "WHERE s.registration = :registration")
    void updateByName(@Param("registration") String registration, @Param("nameStudent") String nameStudent);

    @Transactional
    @Modifying
    @Query(
        "UPDATE Student s " +
        "SET s.registration =: registrationStudent " +
        "WHERE s.registration =: registration")
    void updateByRegistration(@Param("registration") String registration, @Param("registrationStudent") String registrationStudent);

    @Transactional
    @Modifying
    @Query(
        "DELETE FROM Student s WHERE s.registration = :registration")
    void deleteByRegistration(@Param("registration") String registration);
}
