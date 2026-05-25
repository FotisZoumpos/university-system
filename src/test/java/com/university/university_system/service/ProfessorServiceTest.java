package com.university.university_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import java.util.NoSuchElementException;
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

  @Test
  void findById_shouldFindProfessorDto() {

    Course foundCourse = Course.builder()
        .id(1L)
        .name("fisiki")
        .description("simpan")
        .build();

    CourseDto expectedCourseDto = CourseDto.builder()
        .id(1L)
        .name("fisiki")
        .description("simpan")
        .build();

    Professor foundProfessor = Professor.builder()
        .id(1L)
        .firstName("fotis")
        .lastName("zou")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("f@z.gr")
        .gender(Gender.MALE)
        .phone("210345555")
        .courses(List.of(foundCourse))
        .build();

    ProfessorDto expectedProfessorDto = ProfessorDto.builder()
        .id(1L)
        .firstName("fotis")
        .lastName("zou")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("f@z.gr")
        .gender(Gender.MALE)
        .phone("210345555")
        .courses(new ArrayList<>())
        .build();

    when(professorRepo.findById(1L)).thenReturn(Optional.of(foundProfessor));
    when(professorMapper.toDto(foundProfessor)).thenReturn(expectedProfessorDto);
    when(courseMapper.toDto(foundCourse)).thenReturn(expectedCourseDto);

    ProfessorDto result = professorService.findById(1L).orElseThrow();

    assertEquals(expectedProfessorDto.getId(), result.getId());
    assertEquals(expectedProfessorDto.getFirstName(), result.getFirstName());
    assertEquals(expectedProfessorDto.getLastName(), result.getLastName());
    assertEquals(expectedProfessorDto.getEmail(), result.getEmail());
    assertEquals(expectedProfessorDto.getPhone(), result.getPhone());
    assertEquals(expectedProfessorDto.getGender(), result.getGender());

    assertEquals(1L, result.getCourses().get(0).getId());
    assertEquals(1, result.getCourses().size());
    assertEquals(expectedCourseDto.getName(),
        result.getCourses().get(0).getName());
    assertEquals(expectedCourseDto.getDescription(),
        result.getCourses().get(0).getDescription());

    verify(professorRepo).findById(1L);
    verify(professorMapper).toDto(foundProfessor);
    verify(courseMapper).toDto(foundCourse);
  }

  @Test
  void findById_professorDtoNotFound() {

    when(professorRepo.findById(1L)).thenReturn(Optional.empty());

    Optional<ProfessorDto> result = professorService.findById(1L);

    assertTrue(result.isEmpty());
    verify(professorRepo).findById(1L);
  }

  @Test
  void findById_shouldThrowExceptionWhenIdIsNull() {

    assertThrows(IllegalArgumentException.class, () -> professorService.findById(null));

    verify(professorRepo, times(0)).findById(any());

  }

  @Test
  void updateProfessorFields_shouldUpdateProfessorDto() {

    ProfessorDto inputDto = ProfessorDto.builder()
        .id(1L)
        .firstName("f")
        .lastName("z")
        .email("f@z")
        .birthday(LocalDate.of(1993, 4, 4))
        .phone("54321")
        .gender(Gender.MALE)
        .build();

    Professor foundProfessor = Professor.builder()
        .id(1L)
        .firstName("f")
        .lastName("z")
        .email("f@z")
        .birthday(LocalDate.of(1993, 4, 4))
        .phone("54321")
        .gender(Gender.MALE)
        .build();

    Professor updatedProfessor = Professor.builder()
        .id(1L)
        .firstName("q")
        .lastName("w")
        .email("q@w")
        .birthday(LocalDate.of(2000, 4, 4))
        .phone("12345")
        .gender(Gender.FEMALE)
        .build();

    ProfessorDto expectedDto = ProfessorDto.builder()
        .id(1L)
        .firstName("q")
        .lastName("w")
        .email("q@w")
        .birthday(LocalDate.of(2000, 4, 4))
        .phone("12345")
        .gender(Gender.FEMALE)
        .build();

    when(professorRepo.findById(1L)).thenReturn(Optional.of(foundProfessor));
    when(professorRepo.save(foundProfessor)).thenReturn(updatedProfessor);
    when(professorMapper.toDto(updatedProfessor)).thenReturn(expectedDto);

    ProfessorDto result = professorService.updateProfessorFields(inputDto);

    assertEquals(expectedDto.getId(), result.getId());
    assertEquals(expectedDto.getFirstName(), result.getFirstName());
    assertEquals(expectedDto.getLastName(), result.getLastName());
    assertEquals(expectedDto.getBirthday(), result.getBirthday());
    assertEquals(expectedDto.getEmail(), result.getEmail());
    assertEquals(expectedDto.getPhone(), result.getPhone());
    assertEquals(expectedDto.getGender(), result.getGender());

    verify(professorRepo, times(1)).findById(1L);
    verify(professorRepo, times(1)).save(foundProfessor);

  }

  @Test
  void updateProfessorFields_shouldThrowExceptionWhenProfessorNotFound() {

    when(professorRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> professorService.updateProfessorFields(
        ProfessorDto.builder().id(1L).build())
    );

    verify(professorRepo, times(1)).findById(1L);
    verify(professorRepo, never()).save(any());
  }

  @Test
  void updateProfessorFields_shouldUpdateOnlyProvidedFields() {

    ProfessorDto inputDto = ProfessorDto.builder()
        .id(1L)
        .email("new@m.gr")
        .build();

    Professor foundProfessor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("old@email.com")
        .phone("123")
        .gender(Gender.MALE)
        .build();

    Professor updatedProfessor = Professor.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("new@m.com")
        .phone("123")
        .gender(Gender.MALE)
        .build();

    ProfessorDto expectedDto = ProfessorDto.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("new@m.com")
        .phone("123")
        .gender(Gender.MALE)
        .build();

    when(professorRepo.findById(1L)).thenReturn(Optional.of(foundProfessor));
    when(professorRepo.save(foundProfessor)).thenReturn(updatedProfessor);
    when(professorMapper.toDto(updatedProfessor)).thenReturn(expectedDto);

    ProfessorDto result = professorService.updateProfessorFields(inputDto);

    assertEquals("Fotis", result.getFirstName());
    assertEquals("Zoumpos", result.getLastName());
    assertEquals("new@m.com", result.getEmail());
    assertEquals("123", result.getPhone());

    verify(professorRepo).findById(1L);
    verify(professorRepo).save(foundProfessor);
  }

  @Test
  void updateProfessorFields_shouldThrowExceptionWhenProfessorDtoIsNull() {

    assertThrows(IllegalArgumentException.class, () -> professorService.updateProfessorFields(null));
  }

  @Test
  void updateProfessorFields_shouldThrowExceptionWhenProfessorIdIsNull() {

    ProfessorDto inputDto = ProfessorDto.builder()
        .id(null)
        .build();

    assertThrows(IllegalArgumentException.class, () -> professorService.updateProfessorFields(inputDto));
  }

  @Test
  void updateProfessorCourses_shouldUpdateProfessorCourses() {

    CourseDto courseDto = CourseDto.builder()
        .id(1L)
        .build();

    ProfessorDto inputDto = ProfessorDto.builder()
        .id(1L)
        .courses(List.of(courseDto))
        .build();

    Professor professor = Professor.builder()
        .id(1L)
        .courses(new ArrayList<>())
        .build();

    Course course = Course.builder()
        .id(1L)
        .build();

    ProfessorDto expectedDto = ProfessorDto.builder()
        .id(1L)
        .build();

    when(professorRepo.findById(1L)).thenReturn(Optional.of(professor));
    when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
    when(professorRepo.save(professor)).thenReturn(professor);
    when(professorMapper.toDto(professor)).thenReturn(expectedDto);

    ProfessorDto result = professorService.updateProfessorCourses(inputDto);

    assertNotNull(result);
    assertTrue(professor.getCourses().contains(course));
    assertEquals(professor, course.getProfessor());

    verify(professorRepo).findById(1L);
    verify(courseRepo).findById(1L);
    verify(professorRepo).save(professor);
  }

  @Test
  void updateProfessorCourses_shouldThrowExceptionWhenProfessorNotFound() {

    when(professorRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> professorService.updateProfessorCourses
        (ProfessorDto.builder()
            .id(1L)
            .build()));

    verify(professorRepo).findById(1L);
    verify(professorRepo, never()).save(any());
  }

  @Test
  void updateProfessorCourses_shouldThrowExceptionWhenCourseNotFound() {

    CourseDto courseDto = CourseDto.builder()
        .id(1L)
        .build();

    ProfessorDto inputDto = ProfessorDto.builder()
        .id(1L)
        .courses(List.of(courseDto))
        .build();

    Professor professor = Professor.builder()
        .id(1L)
        .courses(new ArrayList<>())
        .build();

    when(professorRepo.findById(1L)).thenReturn(Optional.of(professor));
    when(courseRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class,
        () -> professorService.updateProfessorCourses(inputDto)
    );

    verify(professorRepo).findById(1L);
    verify(courseRepo).findById(1L);
    verify(professorRepo, never()).save(any());
  }

  @Test
  void updateProfessorCourses_shouldNotAddDuplicateCourse() {

    Course course = Course.builder()
        .id(1L)
        .build();

    Professor professor = Professor.builder()
        .id(1L)
        .courses(new ArrayList<>(List.of(course)))
        .build();

    CourseDto courseDto = CourseDto.builder()
        .id(1L)
        .build();

    ProfessorDto inputDto = ProfessorDto.builder()
        .id(1L)
        .courses(List.of(courseDto))
        .build();

    ProfessorDto expectedDto = ProfessorDto.builder()
        .id(1L)
        .build();

    when(professorRepo.findById(1L)).thenReturn(Optional.of(professor));
    when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
    when(professorRepo.save(professor)).thenReturn(professor);
    when(professorMapper.toDto(professor)).thenReturn(expectedDto);

    professorService.updateProfessorCourses(inputDto);

    assertEquals(1, professor.getCourses().size());

    verify(professorRepo).save(professor);
  }

}
