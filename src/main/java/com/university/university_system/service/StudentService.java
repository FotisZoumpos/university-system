package com.university.university_system.service;

import com.university.university_system.domain.Student;
import com.university.university_system.repo.StudentRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional

public class StudentService {
  
  private final StudentRepo studentRepo;

  public Student create(Student student) {
    return studentRepo.save(student);
  }

  public Optional<Student> findById(Long id) {
    return studentRepo.findById(id);
  }

  public List<Student> findAllStudents() {
    return studentRepo.findAll();
  }

  public Student findByFirstName(String name) {
    return studentRepo.findByFirstName(name);
  }

  public Student findByLastName(String name) {
    return studentRepo.findByLastName(name);
  }

  public Student findByEmail(String email) {
    return studentRepo.findByEmail(email);
  }

  public Student findByPhone(String phone) {
    return studentRepo.findByPhone(phone);
  }

  public Student updateStudent(Student student) {
    Optional<Student> existingStudentOpt = studentRepo.findById(student.getId());

    if (existingStudentOpt.isPresent()) {
      Student existingStudent = existingStudentOpt.get();

      if (student.getFirstName() != null) {
        existingStudent.setFirstName(student.getFirstName());
      }
      if (student.getLastName() != null){
        existingStudent.setLastName(student.getLastName());
      }
      if(student.getEmail()!= null){
        existingStudent.setEmail(student.getEmail());
      }
      if(student.getBirthday()!=null){
        existingStudent.setBirthday(student.getBirthday());
      }
      if(student.getGender()!=null){
        existingStudent.setGender(student.getGender());
      }
      if(student.getPhone()!=null){
        existingStudent.setPhone(student.getPhone());
      }
      if (student.getCourses() != null && !student.getCourses().isEmpty()) {
        existingStudent.setCourses(student.getCourses());
      }
      return studentRepo.save(existingStudent);
    }
    return student;

  }

  public void deleteById(Long id){
    Student foundStudent = studentRepo.findById(id).orElseThrow();
    studentRepo.delete(foundStudent);
  }

  public void deleteByFirstName(String name){
    studentRepo.deleteByFirstName(name);
  }
  public void deleteByLastName(String name){
    studentRepo.deleteByLastName(name);
  }
  public void deleteByEmail(String email){
    studentRepo.deleteByEmail(email);
  }
  
}
