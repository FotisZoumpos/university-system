package com.university.university_system.repo;

import com.university.university_system.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
  Student findByFirstName(String name);

  Student findByLastName(String name);

  Student findByEmail(String email);

  Student findByPhone(String phone);

  void deleteByFirstName(String name);

  void deleteByLastName(String name);

  void deleteByEmail(String email);
}
