package com.university.university_system.service;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.dto.CourseDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.repository.CourseRepository;
import com.university.university_system.repository.ProfessorRepository;
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
  private final ProfessorRepository professorRepo;

  public CourseDto create(CourseDto courseDto) {
    if (courseDto == null) {
      throw new IllegalArgumentException("CourseDto can't be null");
    }
    Course course = courseMapper.toEntity(courseDto);
    Course savedCourse = courseRepo.save(course);
    return courseMapper.toDto(savedCourse);
  }

  public Optional<CourseDto> findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Course id can't be null");
    }
    return courseRepo.findById(id).map(courseMapper::toDto);
  }

  @Transactional
  public CourseDto updateCourseFields(CourseDto courseDto) {
    if (courseDto == null) {
      throw new IllegalArgumentException("CourseDto can't be null");
    }
    if (courseDto.getId() == null) {
      throw new IllegalArgumentException("Course id cannot be null");
    }
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
    if (courseDto == null) {
      throw new IllegalArgumentException("CourseDto can't be null");
    }
    if (courseDto.getId() == null) {
      throw new IllegalArgumentException("Id can't be null");
    }
    Course updatedCourse = courseRepo.findById(courseDto.getId())
        .map(existingCourse -> {
          if (courseDto.getProfessor() != null) {
            Professor professor = professorRepo.findById(courseDto.getProfessor().getId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));
            existingCourse.setProfessor(professor);
          }
          return courseRepo.save(existingCourse);
        })
        .orElseThrow();
    return courseMapper.toDto(updatedCourse);
  }

  @Transactional
  public CourseDto deleteById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Course id cannot be null");
    }
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
    if (ids == null || ids.isEmpty()) {
      throw new IllegalArgumentException();
    }
    List<Course> courses = courseRepo.findAllById(ids);
    for (Course course : courses) {
      deleteById(course.getId());
    }
  }
}
