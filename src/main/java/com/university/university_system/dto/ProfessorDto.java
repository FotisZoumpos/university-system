package com.university.university_system.dto;

import com.university.university_system.domain.Gender;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProfessorDto {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private LocalDate birthday;
  private Gender gender;
  private List<CourseDto> courses;

}
