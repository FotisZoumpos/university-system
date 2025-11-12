package com.university.university_system.service;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.dto.ProfessorDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.mapper.ProfessorMapper;
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
  private final ProfessorMapper professorMapper;
  private final CourseMapper courseMapper;

//  public Professor create(Professor professor) {
//    return professorRepo.save(professor);
//  }

  public ProfessorDto create(ProfessorDto professorDto) {
    Professor professor = professorMapper.toEntity(professorDto);
    Professor savedProfessor = professorRepo.save(professor);
    return professorMapper.toDto(savedProfessor);
  }

  public Optional<ProfessorDto> findById(Long id) {
    return professorRepo.findById(id).map(professorMapper::toDto);
  }

  @Transactional
  public ProfessorDto updateProfessorFields(ProfessorDto professorDto) {
    Professor updatedProfessor = professorRepo.findById(professorDto.getId())
        .map(existingProfessor -> {

          if (professorDto.getFirstName() != null) {
            existingProfessor.setFirstName(professorDto.getFirstName());
          }
          if (professorDto.getLastName() != null) {
            existingProfessor.setLastName(professorDto.getLastName());
          }
          if (professorDto.getEmail() != null) {
            existingProfessor.setEmail(professorDto.getEmail());
          }
          if (professorDto.getBirthday() != null) {
            existingProfessor.setBirthday(professorDto.getBirthday());
          }
          if (professorDto.getGender() != null) {
            existingProfessor.setGender(professorDto.getGender());
          }
          if (professorDto.getPhone() != null) {
            existingProfessor.setPhone(professorDto.getPhone());
          }
          return professorRepo.save(existingProfessor);
        })
        .orElseThrow();
    return professorMapper.toDto(updatedProfessor);
  }

  @Transactional
  public ProfessorDto updateProfessorCourses(ProfessorDto professorDto) {
    Professor updatedProfessor = professorRepo.findById(professorDto.getId())
        .map(existingProfessor -> {
          if (professorDto.getCourses() != null) {
            existingProfessor.setCourses(
                professorDto.getCourses().stream()
                    .map(courseMapper::toEntity)
                    .toList());
          }
          return professorRepo.save(existingProfessor);
        })
        .orElseThrow();
    return professorMapper.toDto(updatedProfessor);
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

