package com.university.university_system.repo;

import com.university.university_system.domain.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
  Optional<Course> findByDescription(String description);

  List<Course> findAllByYear(int year);
}
