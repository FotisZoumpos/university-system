package com.university.university_system.service;

import com.university.university_system.domain.Course;
import com.university.university_system.domain.Professor;
import com.university.university_system.domain.Student;
import com.university.university_system.repo.ProfessorRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfessorService {

  private final ProfessorRepo professorRepo;

  public Professor create(Professor professor) {
    return professorRepo.save(professor);
  }

  public Optional<Professor> findById(Long id) {
    return professorRepo.findById(id);
  }

  public List<Professor> findAllProfessors() {
    return professorRepo.findAll();
  }

  public Professor findByFirstName(String name) {
    return professorRepo.findByFirstName(name);
  }

  public Professor findByLastName(String name) {
    return professorRepo.findByLastName(name);
  }

  public Professor findByEmail(String email) {
    return professorRepo.findByEmail(email);
  }

  public Professor findByPhone(String phone) {
    return professorRepo.findByPhone(phone);
  }

  public Professor updateProfessor(Professor professor) {
    Optional<Professor> existingProfessorOpt = professorRepo.findById(professor.getId());

    if (existingProfessorOpt.isPresent()) {
      Professor existingProfessor = existingProfessorOpt.get();

      if (professor.getFirstName() != null) {
        existingProfessor.setFirstName(professor.getFirstName());
      }
      if (professor.getLastName() != null){
        existingProfessor.setLastName(professor.getLastName());
      }
      if(professor.getEmail()!= null){
        existingProfessor.setEmail(professor.getEmail());
      }
      if(professor.getBirthday()!=null){
        existingProfessor.setBirthday(professor.getBirthday());
      }
      if(professor.getGender()!=null){
        existingProfessor.setGender(professor.getGender());
      }
      if(professor.getPhone()!=null){
        existingProfessor.setPhone(professor.getPhone());
      }
      if(professor.getCourses() != null){
        existingProfessor.setCourses(professor.getCourses());
      }
      return professorRepo.save(existingProfessor);
    }
    return professor;

  }

  public void deleteById(Long id) {
    Professor foundProfessor = professorRepo.findById(id).orElseThrow();
    deleteProfessorRelations(foundProfessor);
    professorRepo.delete(foundProfessor);
  }

  public void deleteByFirstName(String name){
    Professor foundProfessor = professorRepo.findByFirstName(name);
    deleteProfessorRelations(foundProfessor);
    professorRepo.delete(foundProfessor);
  }
  public void deleteByLastName(String name){
    Professor foundProfessor = professorRepo.findByLastName(name);
    deleteProfessorRelations(foundProfessor);
    professorRepo.delete(foundProfessor);
  }
  public void deleteByEmail(String email){
    Professor foundProfessor = professorRepo.findByEmail(email);
    deleteProfessorRelations(foundProfessor);
    professorRepo.delete(foundProfessor);
  }

  private void deleteProfessorRelations(Professor professor){
    if(professor.getCourses() != null){
      for(Course course : professor.getCourses()){
        course.setProfessor(null);
        if(course.getStudents() != null){
          for (Student student : course.getStudents()){
            student.getCourses().remove(course);
          }
        }
      }
    }
  }
}


