package com.university.university_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Gender;
import com.university.university_system.domain.Professor;
import com.university.university_system.dto.CourseDto;
import com.university.university_system.dto.ProfessorDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.mapper.ProfessorMapper;
import com.university.university_system.repository.CourseRepository;
import com.university.university_system.repository.ProfessorRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

  @Mock
  private ProfessorRepository professorRepo;

  @Mock
  private ProfessorMapper professorMapper;

  @Mock
  private CourseRepository courseRepo;

  @Mock
  private CourseMapper courseMapper;

  @InjectMocks
  private ProfessorService professorService;

  @Test
  void create_shouldReturnProfessorDto() {

    ProfessorDto inputDto = ProfessorDto.builder()
        .firstName("kostas")
        .lastName("pppp")
        .email("k@p")
        .birthday(LocalDate.of(1993, 4, 4))
        .gender(Gender.MALE)
        .phone("6999")
        .build();

    Professor professor = Professor.builder()
        .firstName("kostas")
        .lastName("pppp")
        .email("k@p")
        .birthday(LocalDate.of(1993, 4, 4))
        .gender(Gender.MALE)
        .phone("6999")
        .build();

    Professor savedProfessor = Professor.builder()
        .id(1L)
        .firstName("kostas")
        .lastName("pppp")
        .email("k@p")
        .birthday(LocalDate.of(1993, 4, 4))
        .gender(Gender.MALE)
        .phone("6999")
        .build();

    ProfessorDto expectedDto = ProfessorDto.builder()
        .id(1L)
        .firstName("kostas")
        .lastName("pppp")
        .email("k@p")
        .birthday(LocalDate.of(1993, 4, 4))
        .gender(Gender.MALE)
        .phone("6999")
        .build();

    when(professorMapper.toEntity(inputDto)).thenReturn(professor);
    when(professorRepo.save(professor)).thenReturn(savedProfessor);
    when(professorMapper.toDto(savedProfessor)).thenReturn(expectedDto);

    ProfessorDto result = professorService.create(inputDto);

    assertEquals(1L, result.getId());
    assertEquals("kostas", result.getFirstName());
    assertEquals("pppp", result.getLastName());
    assertEquals("k@p", result.getEmail());
    assertEquals(LocalDate.of(1993, 4, 4), result.getBirthday());
    assertEquals("6999", result.getPhone());
    assertEquals(Gender.MALE, result.getGender());

    verify(professorRepo, times(1)).save(professor);
  }

  @Test
  void create_shouldThrowExceptionWhenProfessorDtoIsNull() {
    assertThrows(IllegalArgumentException.class, () -> professorService.create(null));

    verify(professorRepo, times(0)).save(any());
  }

}
