package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.model.StudentCourse;
import com.example.demo.repository.StudentCourseRepository;

@Service
public class StudentCourseService {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public void create(String idStudent, String idCourse){
        if(!idStudent.trim().isEmpty() && idStudent.trim() != null && !idCourse.trim().isEmpty() && idCourse.trim() != null){
            Student student = studentService.findById(idStudent);
            Course course = courseService.findById(idCourse);

            StudentCourse studentCourse = new StudentCourse(null, student, course);
            studentCourseRepository.save(studentCourse);
            
            student.getCourses().add(course);
            course.getStudent().add(student);

            studentService.save(student);
            courseService.save(course);
        }
    }

    // Não precisa?
    public void deleteByStudent(String id_student){
        if(!id_student.trim().isEmpty() || id_student.trim() != null){
            Student student = studentService.findById(id_student);

            if(student != null){
                studentCourseRepository.deleteByStudent(student);
            }
        }
    }

    // Não precisa?
    public void deleteByCourse(String id_course){
        if(!id_course.trim().isEmpty() || id_course.trim() != null){
            Course course = courseService.findById(id_course);

            if(course != null){
                studentCourseRepository.deleteByCourse(course);
            }
        }
    }
}
