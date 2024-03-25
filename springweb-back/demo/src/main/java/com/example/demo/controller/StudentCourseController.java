package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.StudentCourseRecordDto;
import com.example.demo.service.StudentCourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class StudentCourseController {
    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping("/create-bond")
    public void create(@Valid @RequestBody StudentCourseRecordDto studentCourseRecordDto){
        studentCourseService.create(studentCourseRecordDto.student(), studentCourseRecordDto.course());
    }

    @DeleteMapping("/delete-by-student")
    public void deleteByStudent(@RequestParam("id_student")String id_student){
        studentCourseService.deleteByStudent(id_student);
    }

    @DeleteMapping("/delete-by-course")
    public void deleteByCourse(@RequestParam("id_course")String id_course){
        studentCourseService.deleteByCourse(id_course);
    }
}
