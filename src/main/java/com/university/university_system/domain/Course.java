package com.university.university_system.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "description")
  private String description;

  @Column(name = "year")
  private int year;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "professor_id")
  private Professor professor;

  @ManyToMany(mappedBy = "courses" )
  private List<Student> students;

  public void addStudent(Student student){
    if (students == null){
      students = new ArrayList<>();
    }
    students.add(student);
  }

}
