package com.university.university_system.mapper;

import com.university.university_system.domain.Professor;
import com.university.university_system.dto.ProfessorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

  ProfessorDto toDto(Professor professor);

  Professor toEntity(ProfessorDto professorDto);
}
