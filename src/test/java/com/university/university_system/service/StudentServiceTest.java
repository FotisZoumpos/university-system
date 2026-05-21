package com.university.university_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import java.util.Optional;
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

      StudentDto result = studentService.create(inputDto);

      assertEquals(1L, result.getId());
      assertEquals("Fotis", result.getFirstName());
      assertEquals("Zoumpos",result.getLastName());
      assertEquals(LocalDate.of(1993,4,4),result.getBirthday());
      assertEquals("123",result.getPhone());
      assertEquals("f@z",result.getEmail());
      assertEquals(Gender.MALE,result.getGender());

      verify(studentRepo, times(1)).save(student);
    }

  @Test
  void create_shouldThrowExceptionWhenStudentDtoIsNull(){

    assertThrows(IllegalArgumentException.class,()->studentService.create(null));

    verify(studentRepo,times(0)).save(any());
  }

  @Test
    void findById_shouldFindStudentDto(){

      Student foundStudent = Student.builder()
          .id(1L)
          .firstName("fotis")
          .lastName("zoumpos")
          .birthday(LocalDate.of(1993,4,4))
          .email("f@z")
          .gender(Gender.MALE)
          .phone("123")
          .build();

      StudentDto expectedDto = StudentDto.builder()
          .id(1L)
          .firstName("fotis")
          .lastName("zoumpos")
          .birthday(LocalDate.of(1993,4,4))
          .email("f@z")
          .gender(Gender.MALE)
          .phone("123")
          .build();

      when(studentRepo.findById(1L)).thenReturn(Optional.of(foundStudent));
      when(studentMapper.toDto(foundStudent)).thenReturn(expectedDto);

      Optional<StudentDto> result = studentService.findById(1L);

      assertEquals(1L, result.get().getId());
      assertEquals("fotis", result.get().getFirstName());
      assertEquals("zoumpos",result.get().getLastName());
      assertEquals(LocalDate.of(1993,4,4),result.get().getBirthday());
      assertEquals("123",result.get().getPhone());
      assertEquals("f@z",result.get().getEmail());
      assertEquals(Gender.MALE,result.get().getGender());

      verify(studentRepo,times(1)).findById(1L);
    }

  @Test
  void findById_StudentDtoNotFound(){

    when(studentRepo.findById(1L)).thenReturn(Optional.empty());

    Optional<StudentDto> result = studentService.findById(1L);

    assertTrue(result.isEmpty());
    verify(studentRepo,times(1)).findById(1L);

  }

  @Test
  void findById_shouldThrowExceptionWhenIdIsNull(){

    assertThrows(IllegalArgumentException.class,()->studentService.findById(null));

    verify(studentRepo,times(0)).findById(any());

  }
}
