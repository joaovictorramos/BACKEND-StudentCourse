package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Course;

import jakarta.transaction.Transactional;

public interface CourseRepository extends CrudRepository<Course, UUID>{ 
    List<Course> findByName(String name);

    @Transactional
    @Modifying
    @Query(
        """
            UPDATE Course c 
            SET c.name = :nameCourse, c.description = :descriptionCourse
            WHERE c.name = :nameParam    
        """
    )
    void updateCourse(@Param("nameParam")String nameParam, @Param("nameCourse")String nameCourse, @Param("descriptionCourse")String descriptionCourse);

    @Transactional
    @Modifying
    @Query(
        """
            UPDATE Course c
            SET c.name = :nameCourse
            WHERE c.name = :nameParam
        """
    )
    void updateByName(@Param("nameParam")String nameParam, @Param("nameCourse")String nameCourse);

    @Transactional
    @Modifying
    @Query(
        """
            UPDATE Course c
            SET c.description = :descriptionCourse
            WHERE c.name = :nameParam        
        """
    )
    void updateByDescription(@Param("nameParam")String nameParam, @Param("descriptionCourse")String descriptionCourse);

    @Transactional
    @Modifying
    @Query(
        """
            DELETE FROM Course c
            WHERE c.name = :nameParam
        """
    )
    void deleteByName(@Param("nameParam")String nameParam);
}
