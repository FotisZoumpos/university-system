package com.university.university_system.repo;

import com.university.university_system.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepo extends JpaRepository<Professor,Long> {

  Professor findByFirstName(String name);

  Professor findByLastName(String name);

  Professor findByEmail(String email);

  Professor findByPhone(String phone);

}
