package com.university.university_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.dto.CourseDto;
import com.university.university_system.dto.ProfessorDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.repository.CourseRepository;
import com.university.university_system.repository.ProfessorRepository;
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
public class CourseServiceTest {

  @Mock
  private CourseRepository courseRepo;

  @Mock
  private CourseMapper courseMapper;

  @Mock
  private ProfessorRepository professorRepo;

  @InjectMocks
  private CourseService courseService;

  @Test
  void create_shouldReturnStudentDto() {

    CourseDto inputDto = CourseDto.builder()
        .description("algevra")
        .name("mathimatika")
        .build();

    Course course = Course.builder()
        .description("algevra")
        .name("mathimatika")
        .build();

    Course savedCourse = Course.builder()
        .id(1L)
        .description("algevra")
        .name("mathimatika")
        .build();

    CourseDto expectedDto = CourseDto.builder()
        .id(1L)
        .description("algevra")
        .name("mathimatika")
        .build();

    when(courseMapper.toEntity(inputDto)).thenReturn(course);
    when(courseRepo.save(course)).thenReturn(savedCourse);
    when(courseMapper.toDto(savedCourse)).thenReturn(expectedDto);

    CourseDto result = courseService.create(inputDto);

    assertEquals(1L, result.getId());
    assertEquals("algevra", result.getDescription());
    assertEquals("mathimatika", result.getName());

    verify(courseRepo, times(1)).save(course);
  }

  @Test
  void create_shouldThrowExceptionWhenCourseDtoIsNull() {

    assertThrows(IllegalArgumentException.class, () -> courseService.create(null));
    verify(courseRepo, times(0)).save(any());

  }

  @Test
  void findById_shouldFindCourseDto() {

    Course foundCourse = Course.builder()
        .id(1L)
        .name("fisiki")
        .description("simpantiki")
        .build();


    CourseDto expectedDto = CourseDto.builder()
        .id(1L)
        .name("fisiki")
        .description("simpantiki")
        .build();

    when(courseRepo.findById(1L)).thenReturn(Optional.of(foundCourse));
    when(courseMapper.toDto(foundCourse)).thenReturn(expectedDto);

    Optional<CourseDto> result = courseService.findById(1L);

    assertEquals(1L, result.get().getId());
    assertEquals("fisiki", result.get().getName());
    assertEquals("simpantiki", result.get().getDescription());

    verify(courseRepo, times(1)).findById(1L);
  }

  @Test
  void findById_courseDtoNotFound() {

    when(courseRepo.findById(1L)).thenReturn(Optional.empty());

    Optional<CourseDto> result = courseService.findById(1L);
    assertTrue(result.isEmpty());

    verify(courseRepo, times(1)).findById(1L);
  }

  @Test
  void findById_shouldThrowExceptionWhenIdIsNull() {

    assertThrows(IllegalArgumentException.class, () -> courseService.findById(null));

    verify(courseRepo, never()).findById(any());
  }

  @Test
  void updateCourseFields_shouldUpdateCourse() {

    CourseDto inputDto = CourseDto.builder()
        .id(1L)
        .name("mathimatika")
        .description("algevra")
        .build();

    Course foundCourse = Course.builder()
        .id(1L)
        .name("fisiki")
        .description("simpantiki")
        .build();

    Course updatedCourse = Course.builder()
        .id(1L)
        .name("mathimatika")
        .description("algevra")
        .build();

    CourseDto expectedDto = CourseDto.builder()
        .id(1L)
        .name("mathimatika")
        .description("algevra")
        .build();

    when(courseRepo.findById(1L)).thenReturn(Optional.of(foundCourse));
    when(courseRepo.save(foundCourse)).thenReturn(updatedCourse);
    when(courseMapper.toDto(updatedCourse)).thenReturn(expectedDto);

    CourseDto result = courseService.updateCourseFields(inputDto);

    assertEquals(expectedDto.getId(), result.getId());
    assertEquals(expectedDto.getName(), result.getName());
    assertEquals(expectedDto.getDescription(), result.getDescription());

    verify(courseRepo, times(1)).findById(1L);
    verify(courseRepo, times(1)).save(foundCourse);
  }

  @Test
  void updateCourseFields_shouldThrowExceptionWhenCourseNotFound() {

    when(courseRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> courseService.updateCourseFields(
            CourseDto.builder().id(1L).build()
        )
    );
    verify(courseRepo, times(1)).findById(1L);
    verify(courseRepo, never()).save(any());
  }

  @Test
  void updateCourseFields_shouldUpdateOnlyProvidedFields() {
    CourseDto inputDto = CourseDto.builder()
        .id(1L)
        .name("gimnastiki")
        .build();

    Course foundCourse = Course.builder()
        .id(1L)
        .name("fisiki")
        .description("simpantiki")
        .build();

    Course updatedCourse = Course.builder()
        .id(1L)
        .name("gimnastiki")
        .description("simpantiki")
        .build();

    CourseDto expectedDto = CourseDto.builder()
        .id(1L)
        .name("gimnastiki")
        .description("simpantiki")
        .build();

    when(courseRepo.findById(1L)).thenReturn(Optional.of(foundCourse));
    when(courseRepo.save(foundCourse)).thenReturn(updatedCourse);
    when((courseMapper.toDto(updatedCourse))).thenReturn(expectedDto);

    CourseDto result = courseService.updateCourseFields(inputDto);

    assertEquals("gimnastiki", result.getName());
    assertEquals("simpantiki", result.getDescription());

    assertEquals("gimnastiki", foundCourse.getName());
    assertEquals("simpantiki", foundCourse.getDescription());

    verify(courseRepo, times(1)).findById(1L);
    verify(courseRepo, times(1)).save(foundCourse);
  }

  @Test
  void updateCourseFields_shouldThrowExceptionWhenCourseDtoIsNull() {

    assertThrows(IllegalArgumentException.class, () -> courseService.updateCourseFields(null));
  }

  @Test
  void updateCourseFields_shouldThrowExceptionWhenCourseIdIsNull() {

    CourseDto inputDto = CourseDto.builder()
        .id(null)
        .build();

    assertThrows(IllegalArgumentException.class, () -> courseService.updateCourseFields(inputDto));

  }

  @Test
  void updateCourseProfessor_shouldUpdateCourseProfessor(){

    Professor professor = Professor.builder()
        .id(1L)
        .build();

    ProfessorDto professorDto = ProfessorDto.builder()
        .id(1L)
        .build();

    Course course = Course.builder()
        .id(1L)
        .build();

    Course updatedCourse = Course.builder()
        .id(1L)
        .professor(professor)
        .build();

    CourseDto inputDto = CourseDto.builder()
        .id(1L)
        .professor(professorDto)
        .build();

    CourseDto expectedDto = CourseDto.builder()
        .id(1L)
        .professor(professorDto)
        .build();

    when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
    when(professorRepo.findById(1L)).thenReturn(Optional.of(professor));
    when(courseRepo.save(course)).thenReturn(updatedCourse);
    when(courseMapper.toDto(updatedCourse)).thenReturn(expectedDto);

    CourseDto result = courseService.updateCourseProfessor(inputDto);

    assertEquals(1L, result.getId());
    assertEquals(1L, result.getProfessor().getId());

    verify(courseRepo).findById(1L);
    verify(professorRepo).findById(1L);
    verify(courseRepo).save(course);
  }

  @Test
  void updateCourseProfessor_shouldThrowExceptionWhenCourseNotFound(){

    when(courseRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class,()->courseService.updateCourseProfessor(
        CourseDto.builder().id(1L).build()
    ));

    verify(courseRepo,times(1)).findById(1L);
  }

  @Test
  void updateCourseProfessor_shouldThrowExceptionWhenCourseDtoIsNull(){

    assertThrows(IllegalArgumentException.class,()->courseService.updateCourseProfessor(null));
  }

  @Test
  void updateCourseProfessor_shouldThrowExceptionWhenCourseIdIsNull(){

    CourseDto inputDto = CourseDto.builder()
            .id(null).build();

    assertThrows(IllegalArgumentException.class,()->courseService.updateCourseProfessor(inputDto));
  }

  @Test
  void deleteById_shouldDeleteCourseById(){

    Professor professor = Professor.builder()
        .id(1L)
        .courses(new ArrayList<>())
        .build();

    Student student = Student.builder()
        .id(1L)
        .courses(new ArrayList<>())
        .build();

    Course foundCourse = Course.builder()
        .id(1L)
        .professor(professor)
        .build();

    CourseDto expectedDto = CourseDto.builder()
        .id(1L)
        .build();

    when(courseRepo.findById(1L)).thenReturn(Optional.of(foundCourse));
    when(courseMapper.toDto(foundCourse)).thenReturn(expectedDto);

    CourseDto result = courseService.deleteById(1L);

    assertEquals(1L,result.getId());

    assertNull(foundCourse.getProfessor());
    assertTrue(professor.getCourses().isEmpty());

    assertTrue(foundCourse.getStudents().isEmpty());
    assertTrue(student.getCourses().isEmpty());

    verify(courseRepo).findById(1L);
    verify(courseRepo).delete(foundCourse);
    verify(courseMapper).toDto(foundCourse);
  }

  @Test
  void deleteById_shouldThrowExceptionWhenIdIsNull(){

    assertThrows(IllegalArgumentException.class,()->courseService.deleteById(null));
  }

  @Test
  void deleteById_shouldThrowExceptionWhenCourseNotFound(){

    when(courseRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> courseService.deleteById(1L));

    verify(courseRepo).findById(1L);
    verify(courseRepo,never()).delete(any());
  }

  @Test
  void deleteAllById_shouldSuccessfullyDeleteAllIds(){

    List<Long> ids = List.of(1L,2L);

    Course course1 = Course.builder().id(1L).build();
    Course course2 = Course.builder().id(2L).build();

    when(courseRepo.findAllById(ids)).thenReturn(List.of(course1,course2));
    when(courseRepo.findById(1L)).thenReturn(Optional.of(course1));
    when(courseRepo.findById(2L)).thenReturn(Optional.of(course2));

    courseService.deleteAllById(ids);

    verify(courseRepo).findAllById(ids);
    verify(courseRepo,times(2)).delete(any(Course.class));
  }

  @Test
  void deleteAllById_shouldThrowExceptionWhenIdsIsNull(){

    assertThrows(IllegalArgumentException.class,()->courseService.deleteAllById(null));

    verify(courseRepo, never()).findAllById(any());
    verify(courseRepo, never()).deleteAll(any());

  }

  @Test
  void deleteAllById_shouldThrowExceptionWhenIdsIsEmpty(){

    assertThrows(IllegalArgumentException.class,()->courseService.deleteAllById(List.of()));

    verify(courseRepo, never()).findAllById(any());
    verify(courseRepo, never()).deleteAll(any());
  }

}

