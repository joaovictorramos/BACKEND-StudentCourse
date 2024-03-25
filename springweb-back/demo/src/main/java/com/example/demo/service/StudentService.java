package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void save(Student student){
        studentRepository.save(student);
    }

    public void create(Student student){
        if(student != null){
            studentRepository.save(student);
        }
    }

    public Student findById(String idStudent){
        Optional<Student> student = null;
        if(idStudent.trim() != null && !idStudent.trim().isEmpty()){
            student = studentRepository.findById(UUID.fromString(idStudent));
        }
        return student.get();
    }
    
    public List<Student> findAll(){
        List<Student> students = studentRepository.findAllPersonalize();
        if(students == null){
            return null;
        }
        return students;
    }
    
    public List<Student> findByRegistration(String registration){
        List<Student> student = null;
        if(registration.trim().isEmpty() || registration.trim() == null){
            return null;

        }else{
            student = studentRepository.findByRegistrationWithCourse(registration);
            if(student == null){
                return null;
            }
        }
        return student;
    }

    public void putUpdate(String registration, Student student){
        if(student != null){
            String nameStudent = student.getName();
            String registrationStudent = student.getRegistration();
    
            studentRepository.updateStudent(nameStudent, registrationStudent, registration);
        }
    }

    public void patchUpdate(String registration, Student student){
        if(student != null){
            String nameStudent = student.getName().trim();
            String registrationStudent = student.getRegistration().trim();

            // some name
            if((nameStudent != null && !nameStudent.isEmpty()) && (registrationStudent == null || registrationStudent.isEmpty())){
                studentRepository.updateByName(registration, nameStudent);

            // some registration
            }else if((nameStudent == null || nameStudent.isEmpty()) && (registrationStudent != null && !registrationStudent.isEmpty())){
                studentRepository.updateByRegistration(registration, registrationStudent);

            // name and registration
            }else if(nameStudent != null && !nameStudent.isEmpty() && registrationStudent != null && !registrationStudent.isEmpty()){
                studentRepository.updateStudent(nameStudent, registrationStudent, registration);
            }
        }
    }
    
    public void deleteByRegistration(String registration){
        if(!registration.trim().isEmpty() || registration.trim() != null){
            List<Student> student = studentRepository.findByRegistration(registration);
            if(student != null){
                studentRepository.deleteByRegistration(registration);
            }
        }
    }
}
