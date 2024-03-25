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

import com.example.demo.dtos.CourseRecordDto;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/create-course")
    public void create(@Valid @RequestBody CourseRecordDto courseRecordDto){
        var course = new Course();
        BeanUtils.copyProperties(courseRecordDto, course);
        courseService.create(course);
    }

    @GetMapping("/find-course/{name}")
    public Course findCourse(@PathVariable("name")String name){
        List<Course> course = courseService.findByName(name);
        if(course != null){
            return course.get(0);
        }
        return null;
    }

    @GetMapping("/find-all-course")
    public List<Course> findAllCourse(){
        List<Course> courses = courseService.findAll();
        if(courses != null){
            return courses;
        }
        return null;
    }

    @PutMapping("/update-course-by-put/{name}")
    public void putUpdateCourse(@PathVariable("name") String nameParam, @RequestBody Course course){
        courseService.putUpdate(nameParam, course);
    }

    @PatchMapping("/update-course-by-patch/{name}")
    public void patchUpdateCourse(@PathVariable("name") String nameParam, @RequestBody Course course){
        courseService.patchUpdate(nameParam, course);
    }

    @DeleteMapping("/delete-course")
    public void deleteCourse(@RequestParam("nameParam")String nameParam){
        courseService.deleteByName(nameParam);
    }
}
