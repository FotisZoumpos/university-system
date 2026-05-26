package com.university.university_system.controller;

import com.university.university_system.dto.ProfessorDto;
import com.university.university_system.service.ProfessorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor

public class ProfessorController {

  private final ProfessorService professorService;

  @PostMapping("/create")
  public ProfessorDto createProfessor(@RequestBody ProfessorDto professorDto) {
    return professorService.create(professorDto);
  }

  @GetMapping("/{id}")
  public ProfessorDto getProfessorById(@PathVariable Long id) {
    return professorService.findById(id)
        .orElseThrow(() -> new RuntimeException("Professor not found"));
  }

  @PutMapping("/update")
  public ProfessorDto updateProfessor(@RequestBody ProfessorDto professorDto) {
    return professorService.updateProfessorFields(professorDto);
  }

  @PutMapping("/update-courses")
  public ProfessorDto updateProfessorCourses(@RequestBody ProfessorDto professorDto) {
    return professorService.updateProfessorCourses(professorDto);
  }

  @DeleteMapping("/{id}")
  public ProfessorDto deleteProfessorById(@PathVariable Long id) {

    return professorService.deleteById(id);
  }

  @DeleteMapping("/list")
  public void deleteProfessorsList(@RequestBody List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new IllegalArgumentException("At least 2 ids.");
    }
    professorService.deleteAllById(ids);
  }
}


