CREATE TABLE IF NOT EXISTS professor (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         birthday DATE NULL,
                                         email VARCHAR(255) NULL,
                                         first_name VARCHAR(255) NOT NULL,
                                         gender ENUM ('FEMALE', 'MALE', 'OTHER') NULL,
                                         last_name VARCHAR(255) NOT NULL,
                                         phone VARCHAR(255) NULL,
                                         CONSTRAINT UK7eo9f81hj74qjpuye6jfbaw2v UNIQUE (phone),
                                         CONSTRAINT UKqjm28ojevoom770jyieljec3e UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS course (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      description VARCHAR(255) NOT NULL,
                                      name VARCHAR(255) NOT NULL,
                                      professor_id BIGINT NULL,
                                      CONSTRAINT UK4xqvdpkafb91tt3hsb67ga3fj UNIQUE (name),
                                      CONSTRAINT FKqctak3o6xmul2nu2561al3pb5 FOREIGN KEY (professor_id)
                                          REFERENCES professor (id)
);

CREATE TABLE IF NOT EXISTS student (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       birthday DATE NULL,
                                       email VARCHAR(255) NULL,
                                       first_name VARCHAR(255) NOT NULL,
                                       gender ENUM ('FEMALE', 'MALE', 'OTHER') NULL,
                                       last_name VARCHAR(255) NOT NULL,
                                       phone VARCHAR(255) NULL,
                                       CONSTRAINT UK5s5e6lj1siq6ef1tm7p2g4uul UNIQUE (phone),
                                       CONSTRAINT UKfe0i52si7ybu0wjedj6motiim UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS student_course (
                                              student_id BIGINT NOT NULL,
                                              course_id BIGINT NOT NULL,
                                              CONSTRAINT FKq7yw2wg9wlt2cnj480hcdn6dq FOREIGN KEY (student_id)
                                                  REFERENCES student (id)
                                                  ON DELETE CASCADE,
                                              CONSTRAINT FKejrkh4gv8iqgmspsanaji90ws FOREIGN KEY (course_id)
                                                  REFERENCES course (id)
                                                  ON DELETE CASCADE,
                                              PRIMARY KEY (student_id, course_id)
);