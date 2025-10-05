package com.university.university_system.service;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.repo.CourseRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

  private final CourseRepo courseRepo;
  public Course create(Course course){
    return courseRepo.save(course);
  }

  public List<Course> findAllCourses(){
    return courseRepo.findAll();
  }

  public Optional<Course> findById(Long id){
    return courseRepo.findById(id);
  }

  public Optional<Course> findByDescription(String name){
    return courseRepo.findByDescription(name);
  }

  public List<Course> findAllByYear(int year){
    return courseRepo.findAllByYear(year);
  }
  public Course update(Course course){
    Optional<Course> existingCourseOpt = courseRepo.findById(course.getId());
    if (existingCourseOpt.isPresent()){
      Course existingCourse = existingCourseOpt.get();
      if(course.getDescription() != null){
        existingCourse.setDescription(course.getDescription());
      }
      if(course.getYear()!= 0){
        existingCourse.setYear(course.getYear());
      }
      if(course.getProfessor() != null){
        existingCourse.setProfessor(course.getProfessor());
      }
      return courseRepo.save(existingCourse);
    }
    return course;
  }

  public void deleteAllCourses(){
    List<Course> courses = courseRepo.findAll();
    for(Course course : courses){
      clearCourseRelations(course);
    }
    courseRepo.deleteAll();
  }
  public void deleteById(Long id) {
    Optional<Course> courseOpt = courseRepo.findById(id);
    if (courseOpt.isPresent()) {
      Course course = courseOpt.get();
      clearCourseRelations(course);
      courseRepo.delete(course);
    }
  }

  public void deleteByDescription(String description) {
    Optional<Course> courseOpt = courseRepo.findByDescription(description);
    if (courseOpt.isPresent()) {
      Course course = courseOpt.get();
      clearCourseRelations(course);
      courseRepo.delete(course);
    }
  }

  private void clearCourseRelations(Course course) {
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
  }
}
