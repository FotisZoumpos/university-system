package com.university.university_system.mapper;

import com.university.university_system.domain.Course;
import com.university.university_system.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
  @Mapping(target = "professor", ignore = true)
  CourseDto toDto(Course course);

  Course toEntity(CourseDto courseDto);
}
