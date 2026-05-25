package com.university.university_system.service;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.dto.ProfessorDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.mapper.ProfessorMapper;
import com.university.university_system.repository.CourseRepository;
import com.university.university_system.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {

  private final ProfessorRepository professorRepo;
  private final ProfessorMapper professorMapper;
  private final CourseMapper courseMapper;
  private final CourseRepository courseRepo;

  public ProfessorDto create(ProfessorDto professorDto) {
    if (professorDto == null) {
      throw new IllegalArgumentException("Professor can't be null");
    }
    Professor professor = professorMapper.toEntity(professorDto);
    Professor savedProfessor = professorRepo.save(professor);
    return professorMapper.toDto(savedProfessor);
  }

  public Optional<ProfessorDto> findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Professor id can't be null");
    }
    return professorRepo.findById(id).map(professor -> {
      ProfessorDto dto = professorMapper.toDto(professor);
      dto.setCourses(professor.getCourses().stream()
          .map(courseMapper::toDto)
          .collect(Collectors.toList()));
      return dto;
    });
  }

  @Transactional
  public ProfessorDto updateProfessorFields(ProfessorDto professorDto) {
    if (professorDto == null) {
      throw new IllegalArgumentException("ProfessorDto can't be null");
    }
    if (professorDto.getId() == null) {
      throw new IllegalArgumentException("Professor id can't be null");

    }
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
    if (professorDto == null) {
      throw new IllegalArgumentException("ProfessorDto can't be null");
    }

    if (professorDto.getId() == null) {
      throw new IllegalArgumentException("Professor id can't be null");
    }

    Professor updatedProfessor = professorRepo.findById(professorDto.getId())
        .map(existingProfessor -> {
          if (professorDto.getCourses() != null) {
            List<Course> newCourses = professorDto.getCourses()
                .stream()
                .map(courseDto -> courseRepo.findById(courseDto.getId())
                    .orElseThrow(() -> new RuntimeException("Course not found.")))
                .collect(Collectors.toCollection(ArrayList::new));

            for (Course course : newCourses) {
              if (!existingProfessor.getCourses().contains(course)) {
                existingProfessor.getCourses().add(course);
                course.setProfessor(existingProfessor);
              }
            }
          }
          return professorRepo.save(existingProfessor);
        })
        .orElseThrow(() -> new RuntimeException("Professor not found."));

    return professorMapper.toDto(updatedProfessor);
  }

  @Transactional
  public ProfessorDto deleteById(Long id) {

    if (id == null) {
      throw new IllegalArgumentException("Professor id can't be null");
    }
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
    return professorMapper.toDto(foundProfessor);
  }

  @Transactional
  public void deleteAllById(List<Long> ids) {
    List<Professor> professors = professorRepo.findAllById(ids);
    for (Professor professor : professors) {
      deleteById(professor.getId());
    }
  }
}

