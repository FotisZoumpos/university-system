package com.university.university_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Gender;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.dto.CourseDto;
import com.university.university_system.dto.StudentDto;
import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.mapper.StudentMapper;
import com.university.university_system.repository.CourseRepository;
import com.university.university_system.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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

  @Test
  void updateStudentFields_shouldUpdateStudent() {

    StudentDto inputDto = StudentDto.builder()
        .id(1L)
        .firstName("leo")
        .lastName("c")
        .birthday(LocalDate.of(2000, 4, 4))
        .email("l@c")
        .phone("321")
        .gender(Gender.FEMALE)
        .build();

    Student foundStudent = Student.builder()
        .id(1L)
        .firstName("fotis")
        .lastName("zou")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("f@z")
        .phone("123")
        .gender(Gender.MALE)
        .build();

    Student updatedStudent = Student.builder()
        .id(1L)
        .firstName("leo")
        .lastName("c")
        .birthday(LocalDate.of(2000, 4, 4))
        .email("l@c")
        .phone("321")
        .gender(Gender.FEMALE)
        .build();

    StudentDto expectedDto = StudentDto.builder()
        .id(1L)
        .firstName("leo")
        .lastName("c")
        .birthday(LocalDate.of(2000, 4, 4))
        .email("l@c")
        .phone("321")
        .gender(Gender.FEMALE)
        .build();

    when(studentRepo.findById(1L)).thenReturn(Optional.of(foundStudent));
    when(studentRepo.save(foundStudent)).thenReturn(updatedStudent);
    when(studentMapper.toDto(updatedStudent)).thenReturn(expectedDto);

    StudentDto result = studentService.updateStudentFields(inputDto);

    assertEquals(expectedDto.getId(), result.getId());
    assertEquals(expectedDto.getFirstName(), result.getFirstName());
    assertEquals(expectedDto.getLastName(), result.getLastName());
    assertEquals(expectedDto.getBirthday(), result.getBirthday());
    assertEquals(expectedDto.getEmail(), result.getEmail());
    assertEquals(expectedDto.getPhone(), result.getPhone());
    assertEquals(expectedDto.getGender(), result.getGender());

    verify(studentRepo,times(1)).findById(1L);
    verify(studentRepo,times(1)).save(foundStudent);
  }

  @Test
  void updateStudentFields_shouldThrowExceptionWhenStudentNotFound(){

    when(studentRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class,()->studentService.updateStudentFields(
            StudentDto.builder().id(1L).build()
        )
    );
    verify(studentRepo,times(1)).findById(1L);
    verify(studentRepo,never()).save(any());
  }

  @Test
  void updateStudentFields_shouldUpdateOnlyProvidedFields() {

    StudentDto inputDto = StudentDto.builder()
        .id(1L)
        .email("new@email.com")
        .build();

    Student foundStudent = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("old@email.com")
        .phone("123")
        .gender(Gender.MALE)
        .build();

    Student updatedStudent = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("new@email.com")
        .phone("123")
        .gender(Gender.MALE)
        .build();

    StudentDto expectedDto = StudentDto.builder()
        .id(1L)
        .firstName("Fotis")
        .lastName("Zoumpos")
        .birthday(LocalDate.of(1993, 4, 4))
        .email("new@email.com")
        .phone("123")
        .gender(Gender.MALE)
        .build();

    when(studentRepo.findById(1L)).thenReturn(Optional.of(foundStudent));

    when(studentRepo.save(foundStudent)).thenReturn(updatedStudent);

    when(studentMapper.toDto(updatedStudent)).thenReturn(expectedDto);

    StudentDto result = studentService.updateStudentFields(inputDto);

    assertEquals("Fotis", result.getFirstName());
    assertEquals("Zoumpos", result.getLastName());
    assertEquals("new@email.com", result.getEmail());
    assertEquals("123", result.getPhone());

    verify(studentRepo).findById(1L);
    verify(studentRepo).save(foundStudent);

    assertEquals("Fotis", foundStudent.getFirstName());
    assertEquals("new@email.com", foundStudent.getEmail());
  }

  @Test
  void updateStudentFields_shouldThrowExceptionWhenStudentDtoIsNull(){

    assertThrows(IllegalArgumentException.class,()->studentService.updateStudentFields(null));

  }

  @Test
  void updateStudentFields_shouldThrowExceptionWhenIdIsNull(){

    StudentDto inputDto = StudentDto.builder()
        .id(null)
        .build();

    assertThrows(IllegalArgumentException.class,()->studentService.updateStudentFields(inputDto));
  }
  @Test
  void updateStudentCourse_ShouldUpdateStudentCourse(){

    Professor professor = Professor.builder()
        .id(1L)
        .build();

    Course course = Course.builder()
        .id(1L)
        .name("simpantiki")
        .description("fisiki")
        .professor(professor)
        .build();

    StudentDto inputDto = StudentDto.builder()
        .id(1L)
        .firstName("leo")
        .lastName("c")
        .birthday(LocalDate.of(2000, 4, 4))
        .email("l@c")
        .phone("321")
        .gender(Gender.FEMALE)
        .courses(List.of(CourseDto.builder().id(1L).description("fisiki").name("simpantiki").build()))
        .build();

    Student foundStudent = Student.builder()
        .id(1L)
        .firstName("leo")
        .lastName("c")
        .birthday(LocalDate.of(2000, 4, 4))
        .email("l@c")
        .phone("321")
        .gender(Gender.FEMALE)
        .build();

    StudentDto expectedStudent = StudentDto.builder()
        .id(1L)
        .firstName("leo")
        .lastName("c")
        .birthday(LocalDate.of(2000, 4, 4))
        .email("l@c")
        .phone("321")
        .gender(Gender.FEMALE)
        .courses(List.of(CourseDto.builder().id(1L).description("fisiki").name("simpantiki").build()))
        .build();

    when(studentRepo.findById(1L)).thenReturn(Optional.of(foundStudent));
    when(courseRepo.findAllById(List.of(1L))).thenReturn(List.of(course));
    when(studentMapper.toDto(foundStudent)).thenReturn(expectedStudent);

    StudentDto result = studentService.updateStudentCourse(inputDto);

    assertEquals(1L,result.getId());
    assertEquals("leo",result.getFirstName());
    assertNotNull(result.getCourses());

    verify(studentRepo,times(1)).findById(1L);
    verify(courseRepo,times(1)).findAllById(List.of(1L));


  }

  @Test
  void updateStudentCourse_shouldThrowExceptionWhenStudentNotFound(){

    when(studentRepo.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class,()->studentService.updateStudentCourse(
        StudentDto.builder().id(1L).build()
    ));
    verify(studentRepo,times(1)).findById(1L);
  }

  @Test
  void updateStudentCourse_shouldThrowExceptionWhenCourseHasNoProfessor(){

    Course course = Course.builder()
        .id(1L)
        .name("fisiki")
        .professor(null)
        .build();

    Student foundStudent = Student.builder()
        .id(1L)
        .build();

    StudentDto inputDto = StudentDto.builder()
        .id(1L)
        .courses(List.of(CourseDto.builder()
            .id(1L)
            .build()))
        .build();

    when(studentRepo.findById(1L)).thenReturn(Optional.of(foundStudent));
    when(courseRepo.findAllById(List.of(1L))).thenReturn(List.of(course));

    assertThrows(IllegalStateException.class,()->studentService.updateStudentCourse(inputDto));

    verify(studentRepo).findById(1L);
    verify(courseRepo).findAllById(List.of(1L));
  }

  @Test
  void updateStudentCourse_shouldThrowExceptionWhenStudentDtoIsNull(){

    assertThrows(IllegalArgumentException.class,()->studentService.updateStudentCourse(null));

  }

  @Test
  void updateStudentCourse_shouldThrowExceptionWhenIdIsNull(){

    StudentDto inputDto = StudentDto.builder()
        .id(null)
        .build();

    assertThrows(IllegalArgumentException.class,()->studentService.updateStudentCourse(inputDto));

  }

  @Test
  void updateStudentCourse_shouldNotUpdateCoursesWhenCoursesAreNull() {

    Student foundStudent = Student.builder()
        .id(1L)
        .firstName("Fotis")
        .build();

    StudentDto expectedDto = StudentDto.builder()
        .id(1L)
        .firstName("Fotis")
        .build();

    StudentDto inputDto = StudentDto.builder()
        .id(1L)
        .courses(null)
        .build();

    when(studentRepo.findById(1L))
        .thenReturn(Optional.of(foundStudent));

    when(studentMapper.toDto(foundStudent))
        .thenReturn(expectedDto);

    StudentDto result = studentService.updateStudentCourse(inputDto);

    assertEquals(1L, result.getId());
    assertEquals("Fotis", result.getFirstName());

    verify(studentRepo).findById(1L);
    verify(courseRepo, never()).findAllById(anyList());

  }

}
