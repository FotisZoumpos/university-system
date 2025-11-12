package com.university.university_system.mapper;

import com.university.university_system.domain.Student;
import com.university.university_system.dto.StudentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  StudentDto toDto(Student student);

  Student toEntity(StudentDto studentDto);
}
