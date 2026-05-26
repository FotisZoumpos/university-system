package com.university.university_system.controller;

import com.university.university_system.dto.CourseDto;
import com.university.university_system.service.CourseService;
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
@RequestMapping("/api/courses")
@RequiredArgsConstructor

public class CourseController {

  private final CourseService courseService;

  @PostMapping("/create")
  public CourseDto createCourse(@RequestBody CourseDto courseDto) {
    return courseService.create(courseDto);
  }

  @GetMapping("/{id}")
  public CourseDto getCourseById(@PathVariable Long id) {
    return courseService.findById(id)
        .orElseThrow(() -> new RuntimeException("Course not found"));
  }

  @PutMapping("/update")
  public CourseDto updateCourse(@RequestBody CourseDto courseDto) {
    return courseService.updateCourseFields(courseDto);
  }

  @PutMapping("/update-professor")
  public CourseDto updateCourseProfessor(@RequestBody CourseDto courseDto) {
    return courseService.updateCourseProfessor(courseDto);
  }

  @DeleteMapping("/{id}")
  public CourseDto deleteCourseById(@PathVariable Long id) {
    return courseService.deleteById(id);
  }

  @DeleteMapping("/list")
  public void deleteCoursesList(@RequestBody List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new IllegalArgumentException("At least 2 ids");
    }
    courseService.deleteAllById(ids);
  }

}
