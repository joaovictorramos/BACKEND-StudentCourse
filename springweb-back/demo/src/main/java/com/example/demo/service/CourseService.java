package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public void save(Course course){
        courseRepository.save(course);
    }

    public void create(Course course){
        if(course != null){
            courseRepository.save(course);
        }
    }

    public Course findById(String idCourse){
        Optional<Course> course = null;
        if(idCourse.trim() != null && !idCourse.trim().isEmpty()){
            course = courseRepository.findById(UUID.fromString(idCourse));
        }
        return course.get();
    }

    public List<Course> findByName(String name){
        List<Course> course = null;
        if(name.trim().isEmpty() || name.trim() == null){
            return null;

        }else{
            course = courseRepository.findByName(name);
            if(course == null){
                return null;
            }
        }
        return course;
    }

    public void putUpdate(String nameParam, Course course){
        if(course != null){
            String nameCourse = course.getName().trim();
            String descriptionCourse = course.getDescription().trim();

            if(nameCourse != null && !nameCourse.isEmpty() && descriptionCourse != null && !descriptionCourse.isEmpty()){
                courseRepository.updateCourse(nameParam, nameCourse, descriptionCourse);
            }
        }
    }

    public void patchUpdate(String nameParam, Course course){
        if(course != null){
            String nameCourse = course.getName().trim();
            String descriptionCourse = course.getDescription().trim();

            // some name
            if((nameCourse != null && !nameCourse.isEmpty()) && (descriptionCourse == null || descriptionCourse.isEmpty())){
                courseRepository.updateByName(nameParam, nameCourse);

            // some description
            }else if((nameCourse == null || nameCourse.isEmpty()) && (descriptionCourse != null && !descriptionCourse.isEmpty())){
                courseRepository.updateByDescription(nameParam, descriptionCourse);

            // name and description
            }else if(nameCourse != null && !nameCourse.isEmpty() && descriptionCourse != null && !descriptionCourse.isEmpty()){
                courseRepository.updateCourse(nameParam, nameCourse, descriptionCourse);
            }
        }
    }

    public void deleteByName(String nameParam){
        if(!nameParam.trim().isEmpty() || nameParam.trim() != null){
            List<Course> course = courseRepository.findByName(nameParam);
            if(course != null){
                courseRepository.deleteByName(nameParam);
            }
        }
    }
}
