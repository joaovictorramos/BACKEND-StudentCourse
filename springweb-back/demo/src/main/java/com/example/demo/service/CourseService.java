package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;


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

    public List<Course> findAll(){
        List<Course> courses = courseRepository.findAllPersonalize();
        if(courses == null){
            return null;
        }
        return courses;
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
            List<Course> courses = courseRepository.findByName(nameParam);
            if(courses != null){
                Course course = courses.get(0);
                List<Student> students = courseRepository.findByEnrolledStudent(course.getName());
                if(students != null){
                    course.setStudent(null);
                    students.forEach(s ->{
                        s.getCourses().forEach(c ->{
                            if(c.getName().equals(course.getName())){
                                c = null;
                            }
                        });
                        studentRepository.save(s);
                    });
                    courseRepository.save(course);
                }
                courseRepository.deleteByName(nameParam);
            }
        }
    }
}
