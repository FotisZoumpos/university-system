package com.university.university_system.mapper;

import com.university.university_system.domain.Student;
import com.university.university_system.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {CourseMapper.class})
public interface StudentMapper {

  @Mapping(target = "courses", ignore = false)
  StudentDto toDto(Student student);

  Student toEntity(StudentDto studentDto);
}
