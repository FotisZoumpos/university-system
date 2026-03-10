package com.university.university_system.service;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.dto.CourseDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.mapper.ProfessorMapper;
import com.university.university_system.repository.CourseRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

  private final CourseRepository courseRepo;
  private final CourseMapper courseMapper;
  private final ProfessorMapper professorMapper;

//  public Course create(Course course) {
//    return courseRepo.save(course);
//  }

  public CourseDto create(CourseDto courseDto) {
    Course course = courseMapper.toEntity(courseDto);
    Course savedCourse = courseRepo.save(course);
    return courseMapper.toDto(savedCourse);
  }

  public Optional<CourseDto> findById(Long id) {
    return courseRepo.findById(id).map(courseMapper::toDto);
  }

  @Transactional
  public CourseDto updateCourseFields(CourseDto courseDto) {
    Course updatedCourse = courseRepo.findById(courseDto.getId())
        .map(existingCourse -> {
          if (courseDto.getName() != null) {
            existingCourse.setName(courseDto.getName());
          }
          if (courseDto.getDescription() != null) {
            existingCourse.setDescription(courseDto.getDescription());
          }
          return courseRepo.save(existingCourse);
        })
        .orElseThrow();
    return courseMapper.toDto(updatedCourse);
  }

  @Transactional
  public CourseDto updateCourseProfessor(CourseDto courseDto) {
    Course updatedCourse = courseRepo.findById(courseDto.getId())
        .map(existingCourse -> {
          if (courseDto.getProfessor() != null) {
            existingCourse.setProfessor(professorMapper.toEntity(courseDto.getProfessor()));
          }
          return courseRepo.save(existingCourse);
        })
        .orElseThrow();
    return courseMapper.toDto(updatedCourse);
  }

  @Transactional
  public CourseDto deleteById(Long id) {
    Optional<Course> courseOpt = courseRepo.findById(id);
    if (courseOpt.isPresent()) {
      Course course = courseOpt.get();
      Professor professor = course.getProfessor();
      if (professor != null) {
        professor.getCourses().remove(course);
        course.setProfessor(null);
      }
      if (course.getStudents() != null) {
        for (Student student : course.getStudents()) {
          student.getCourses().remove(course);
        }
        course.getStudents().clear();
      }
      courseRepo.delete(course);
      return courseMapper.toDto(course);
    }
    throw new RuntimeException("Course not found");
  }

  @Transactional
  public void deleteAllById(List<Long> ids) {
    List<Course> courses = courseRepo.findAllById(ids);
    for (Course course : courses) {
      deleteById(course.getId());
    }
  }
}
