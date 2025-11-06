package com.university.university_system.service;

import com.university.university_system.domain.Student;
import com.university.university_system.repository.StudentRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class StudentService {

  private final StudentRepository studentRepo;

  public Student create(Student student) {
    return studentRepo.save(student);
  }

  public Optional<Student> findById(Long id) {
    return studentRepo.findById(id);
  }

  @Transactional
  public Student updateStudent(Student student) {
    studentRepo.findById(student.getId()).ifPresent(
        existingStudent -> {

          if (student.getFirstName() != null) {
            existingStudent.setFirstName(student.getFirstName());
          }
          if (student.getLastName() != null) {
            existingStudent.setLastName(student.getLastName());
          }
          if (student.getEmail() != null) {
            existingStudent.setEmail(student.getEmail());
          }
          if (student.getBirthday() != null) {
            existingStudent.setBirthday(student.getBirthday());
          }
          if (student.getGender() != null) {
            existingStudent.setGender(student.getGender());
          }
          if (student.getPhone() != null) {
            existingStudent.setPhone(student.getPhone());
          }
          studentRepo.save(existingStudent);
        }
    );
    return student;
  }

  @Transactional
  public Student updateStudentCourse(Student student) {
    studentRepo.findById(student.getId()).ifPresent(
        existingStudent -> {
          if (student.getCourses() != null) {
            existingStudent.setCourses(student.getCourses());
          }
          studentRepo.save(existingStudent);
        }
    );
    return student;
  }

  public void deleteById(Long id) {
    Student foundStudent = studentRepo.findById(id).orElseThrow();
    studentRepo.delete(foundStudent);
  }

  @Transactional
  public void deleteAllByIds(List<Long> ids) {
    List<Student> students = studentRepo.findAllById(ids);
    for (Student student : students) {
      deleteById(student.getId());
    }
  }

}
