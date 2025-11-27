package com.university.university_system.mapper;

import com.university.university_system.domain.Professor;
import com.university.university_system.dto.ProfessorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

  @Mapping(target = "courses", ignore = true)
  ProfessorDto toDto(Professor professor);

  Professor toEntity(ProfessorDto professorDto);
}
