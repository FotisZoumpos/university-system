package com.university.university_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.university.university_system.domain.Course;
import com.university.university_system.dto.CourseDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.repository.CourseRepository;
import com.university.university_system.repository.ProfessorRepository;
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
    verify(courseRepo,times(0)).save(any());

  }

}
