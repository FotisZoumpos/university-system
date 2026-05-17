package com.university.university_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.university.university_system.domain.Gender;
import com.university.university_system.domain.Student;
import com.university.university_system.dto.StudentDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.mapper.StudentMapper;
import com.university.university_system.repository.CourseRepository;
import com.university.university_system.repository.StudentRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository studentRepo;

  @Mock
  private StudentMapper studentMapper;

  @Mock
  private CourseMapper courseMapper;

  @Mock
  private CourseRepository courseRepo;

  @InjectMocks
  private StudentService studentService;



    @Test
    void create_shouldReturnStudentDto() {

      StudentDto inputDto = StudentDto.builder()
          .firstName("Fotis")
          .lastName("Zoumpos")
          .birthday(LocalDate.of(1993,4,4))
          .gender(Gender.MALE)
          .phone("123")
          .email("f@z")
          .build();

      Student student = Student.builder()
          .firstName("Fotis")
          .lastName("Zoumpos")
          .birthday(LocalDate.of(1993,4,4))
          .gender(Gender.MALE)
          .phone("123")
          .email("f@z")
          .build();

      Student savedStudent = Student.builder()
          .id(1L)
          .firstName("Fotis")
          .lastName("Zoumpos")
          .birthday(LocalDate.of(1993,4,4))
          .gender(Gender.MALE)
          .phone("123")
          .email("f@z")
          .build();

      StudentDto expectedDto = StudentDto.builder()
          .id(1L)
          .firstName("Fotis")
          .lastName("Zoumpos")
          .birthday(LocalDate.of(1993,4,4))
          .gender(Gender.MALE)
          .phone("123")
          .email("f@z")
          .build();

      when(studentMapper.toEntity(inputDto)).thenReturn(student);
      when(studentRepo.save(student)).thenReturn(savedStudent);
      when(studentMapper.toDto(savedStudent)).thenReturn(expectedDto);

      // Act
      StudentDto result = studentService.create(inputDto);

      // Assert
      assertEquals(1L, result.getId());
      assertEquals("Fotis", result.getFirstName());
      assertEquals("Zoumpos",result.getLastName());
      assertEquals(LocalDate.of(1993,4,4),result.getBirthday());
      assertEquals("123",result.getPhone());
      assertEquals("f@z",result.getEmail());
      assertEquals(Gender.MALE,result.getGender());

      verify(studentRepo, times(1)).save(student);
    }
  }
