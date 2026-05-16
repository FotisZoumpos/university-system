package com.university.university_system.controller;

import com.university.university_system.dto.StudentDto;
import com.university.university_system.service.StudentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor

public class StudentController {

  private final StudentService studentService;

  @PostMapping("/create")
  public StudentDto createStudent(@RequestBody StudentDto studentDto) {
    return studentService.create(studentDto);
  }

  @GetMapping("/{id}")
  public StudentDto getStudentById(@PathVariable Long id) {
    return studentService.findById(id)
        .orElseThrow(() -> new RuntimeException("Student not found"));
  }

  //@PatchMapping
  @PutMapping("/update")
  public StudentDto updateStudent(@RequestBody StudentDto studentDto) {
    return studentService.updateStudentFields(studentDto);
  }
  //@PatchMapping
  @PutMapping("/update-courses")
  public StudentDto updateStudentCourses(@RequestBody StudentDto studentDto) {
    return studentService.updateStudentCourse(studentDto);
  }

  @DeleteMapping("/{id}")
  public StudentDto deleteStudentById(@PathVariable Long id) {
    return studentService.deleteById(id);
  }

  @DeleteMapping("/list")
  public void deleteStudentsList(@RequestBody List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new IllegalArgumentException("At least 2 ids.");
    }
    studentService.deleteAllByIds(ids);
  }
}
