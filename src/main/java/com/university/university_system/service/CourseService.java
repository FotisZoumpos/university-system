package com.university.university_system.service;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
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

  public Course create(Course course){
    return courseRepo.save(course);
  }

  public List<Course> findAllCourses(){
    return courseRepo.findAll();
  }

  public Optional<Course> findById(Long id){
    return courseRepo.findById(id);
  }

  public Course update(Course course) {
    courseRepo.findById(course.getId()).ifPresent(
        existingCourse -> {

          if(course.getName()!= null){
            existingCourse.setName(course.getName());
          }
          if(course.getDescription() != null){
            existingCourse.setDescription(course.getDescription());
          }
          if(course.getProfessor() != null){
            existingCourse.setProfessor(course.getProfessor());
          }
          courseRepo.save(existingCourse);
        }
    );
    return course;
  }

  public void deleteAllCourses() {
    List<Course> courses = courseRepo.findAll();

    for (Course course : courses) {
      deleteById(course.getId());
    }
  }

@Transactional
  public void deleteById(Long id) {
    Optional<Course> courseOpt = courseRepo.findById(id);
    if (courseOpt.isPresent()) {
      Course course = courseOpt.get();
      Professor professor = course.getProfessor();
      if(professor != null){
        professor.getCourses().remove(course);
        course.setProfessor(null);
      }
      if(course.getStudents() != null){
        for(Student student : course.getStudents()){
          student.getCourses().remove(course);
        }
        course.getStudents().clear();
      }
      courseRepo.delete(course);
    }
  }
}
