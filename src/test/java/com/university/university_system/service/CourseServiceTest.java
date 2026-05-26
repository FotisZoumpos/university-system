package com.university.university_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.university.university_system.domain.Course;
import com.university.university_system.dto.CourseDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.repository.CourseRepository;
import com.university.university_system.repository.ProfessorRepository;
import java.util.Optional;
import lombok.ToString;
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

  @Test
  void findById_shouldFindCourseDto(){

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

    assertEquals(1L,result.get().getId());
    assertEquals("fisiki",result.get().getName());
    assertEquals("simpantiki",result.get().getDescription());

    verify(courseRepo,times(1)).findById(1L);
  }

  @Test
  void findById_courseDtoNotFound(){

    when(courseRepo.findById(1L)).thenReturn(Optional.empty());

    Optional<CourseDto> result = courseService.findById(1L);
    assertTrue(result.isEmpty());

    verify(courseRepo,times(1)).findById(1L);
  }



}
