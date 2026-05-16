package com.university.university_system;

import com.university.university_system.mapper.CourseMapper;
import com.university.university_system.mapper.ProfessorMapper;
import com.university.university_system.mapper.StudentMapper;
import com.university.university_system.service.CourseService;
import com.university.university_system.service.ProfessorService;
import com.university.university_system.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UniversitySystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(UniversitySystemApplication.class, args);
  }

  @SuppressWarnings("checkstyle:CommentsIndentation")
  @Bean
  public CommandLineRunner commandLineRunner(ProfessorService professorService,
                                             CourseService courseService,
                                             StudentService studentService,
                                             ProfessorMapper professorMapper,
                                             StudentMapper studentMapper,
                                             CourseMapper courseMapper) {
    return args -> {

    };
  }
}
