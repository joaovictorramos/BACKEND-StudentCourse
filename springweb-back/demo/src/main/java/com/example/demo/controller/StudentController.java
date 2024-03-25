package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.StudentRecordDto;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/create-student")
    public void create(@Valid @RequestBody StudentRecordDto studentRecordDto){
        var student = new Student();
        BeanUtils.copyProperties(studentRecordDto, student);
        studentService.create(student);
    }

    @GetMapping("/find-student/{registration}")
    public Student findStudent(@PathVariable("registration") String registration){
        List<Student> student = studentService.findByRegistration(registration);
        if(student != null){
            return student.get(0);
        }
        return null;
    }

    @GetMapping("/find-all-student")
    public List<Student> findAllStudent(){
        List<Student> students = studentService.findAll();
        if(students != null){
            return students;
        }
        return null;
    }
    
    @PutMapping("/update-student-by-put/{registration}")
    public void putUpdateStudent(@PathVariable("registration") String registration, @RequestBody Student student){
        System.out.println();
        System.out.println(student + registration);
        System.out.println();
        studentService.putUpdate(registration, student);
    }

    @PatchMapping("/update-student-by-patch/{registration}")
    public void patchUpdateStudent(@PathVariable("registration") String registration, @RequestBody Student student){
        System.out.println();
        System.out.println(student + registration);
        System.out.println();
        studentService.patchUpdate(registration, student);
    }
    
    @DeleteMapping("/delete-student")
    public void deleteStudent(@RequestParam(name="registration") String registration){
        studentService.deleteByRegistration(registration);
    }
}

 