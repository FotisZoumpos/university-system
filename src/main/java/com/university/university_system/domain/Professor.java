package com.university.university_system.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table (name = "professor")
public class Professor {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false , updatable = false)
  private long id;

  @Column(name = "first_name" , nullable = false)
  private String firstName;

  @Column(name = "last_name" , nullable = false)
  private String lastName;

  @Column(name = "email",unique = true)
  private String email;

  @Column(name = "phone",unique = true)
  private String phone;

  @Column(name = "birthday")
  private LocalDate birthday;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @ToString.Exclude
  @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
  private List<Course> courses = new ArrayList<>();

}
