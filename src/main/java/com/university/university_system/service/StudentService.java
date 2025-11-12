package com.university.university_system.service;

import com.university.university_system.domain.Student;
import com.university.university_system.dto.StudentDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.mapper.StudentMapper;
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
  private final StudentMapper studentMapper;
  private final CourseMapper courseMapper;

//  public Student create(Student student) {
//    return studentRepo.save(student);
//  }

  public StudentDto create(StudentDto studentDto) {
    Student student = studentMapper.toEntity(studentDto);
    Student savedStudent = studentRepo.save(student);
    return studentMapper.toDto(savedStudent);
  }

  public Optional<StudentDto> findById(Long id) {
    return studentRepo.findById(id).map(studentMapper::toDto);
  }

  @Transactional
  public StudentDto updateStudentFields(StudentDto studentDto) {
    Student updatedStudent = studentRepo.findById(studentDto.getId())
        .map(
            existingStudent -> {

              if (studentDto.getFirstName() != null) {
                existingStudent.setFirstName(studentDto.getFirstName());
              }
              if (studentDto.getLastName() != null) {
                existingStudent.setLastName(studentDto.getLastName());
              }
              if (studentDto.getEmail() != null) {
                existingStudent.setEmail(studentDto.getEmail());
              }
              if (studentDto.getBirthday() != null) {
                existingStudent.setBirthday(studentDto.getBirthday());
              }
              if (studentDto.getGender() != null) {
                existingStudent.setGender(studentDto.getGender());
              }
              if (studentDto.getPhone() != null) {
                existingStudent.setPhone(studentDto.getPhone());
              }
              return studentRepo.save(existingStudent);
            })
        .orElseThrow();
    return studentMapper.toDto(updatedStudent);
  }

  @Transactional
  public StudentDto updateStudentCourse(StudentDto studentDto) {
    Student updatedStudent = studentRepo.findById(studentDto.getId())
        .map(
            existingStudent -> {
              if (studentDto.getCourses() != null) {
                existingStudent.setCourses(studentDto.getCourses().stream()
                    .map(courseMapper::toEntity)
                    .toList());
              }
              return studentRepo.save(existingStudent);
            })
        .orElseThrow();
    return studentMapper.toDto(updatedStudent);
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
