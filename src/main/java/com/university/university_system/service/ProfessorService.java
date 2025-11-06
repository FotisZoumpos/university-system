package com.university.university_system.service;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {

  private final ProfessorRepository professorRepo;

  public Professor create(Professor professor) {
    return professorRepo.save(professor);
  }

  public Optional<Professor> findById(Long id) {
    return professorRepo.findById(id);
  }

  @Transactional
  public Professor updateProfessorFields(Professor professor) {
    professorRepo.findById(professor.getId()).ifPresent(existingProfessor -> {

      if (professor.getFirstName() != null) {
        existingProfessor.setFirstName(professor.getFirstName());
      }
      if (professor.getLastName() != null) {
        existingProfessor.setLastName(professor.getLastName());
      }
      if (professor.getEmail() != null) {
        existingProfessor.setEmail(professor.getEmail());
      }
      if (professor.getBirthday() != null) {
        existingProfessor.setBirthday(professor.getBirthday());
      }
      if (professor.getGender() != null) {
        existingProfessor.setGender(professor.getGender());
      }
      if (professor.getPhone() != null) {
        existingProfessor.setPhone(professor.getPhone());
      }
      professorRepo.save(existingProfessor);
    });
    return professor;
  }

  @Transactional
  public Professor updateProfessorCourses(Professor professor) {
    professorRepo.findById(professor.getId()).ifPresent(existingProfessor -> {
      if (professor.getCourses() != null) {
        existingProfessor.setCourses(professor.getCourses());
      }
      professorRepo.save(existingProfessor);
    });
    return professor;
  }

  @Transactional
  public void deleteById(Long id) {
    Professor foundProfessor = professorRepo.findById(id).orElseThrow();
    if (foundProfessor.getCourses() != null) {
      for (Course course : foundProfessor.getCourses()) {
        course.setProfessor(null);
        if (course.getStudents() != null) {
          for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
          }
        }
      }
    }
    professorRepo.delete(foundProfessor);
  }

  @Transactional
  public void deleteAllById(List<Long> ids) {
    List<Professor> professors = professorRepo.findAllById(ids);
    for (Professor professor : professors) {
      deleteById(professor.getId());
    }
  }
}

