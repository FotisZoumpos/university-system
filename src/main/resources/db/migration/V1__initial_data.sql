INSERT INTO professor (birthday, email, first_name, gender, last_name, phone)
VALUES
    ('1993-04-04','f@z.gr','fotis','MALE','zoumpos','6980972601'),
    ('1987-03-23','v@l.gr','vicky','FEMALE','lortou','2103424020'),
    ('2020-05-25','a@d.gr','antonis','MALE','davilas','29491491'),
    ('1993-03-24','g@d.gr','giannis','MALE','davilas','43242525'),
    ('1993-12-19','k@p.gr','kyriakos','MALE','papadopoulos','524524524');

insert into student (birthday, email, first_name, gender, last_name, phone)
VALUES ('1993-04-04','k@p.gr','kostas','MALE','p','11111111111'),
       ('1987-03-23','s@m.gr','spiros','FEMALE','m','222222222'),
       ('2020-05-25','l@c.gr','leonidas','MALE','c','3333333333'),
       ('1993-03-24','t@z.gr','thodoris','MALE','t','444444444444'),
       ('1993-12-19','c@e.gr','chrysanti','MALE','e','55555555555');

insert into course (description, name, professor_id)
VALUES ('algevra','mathimatika',null),
       ('epistrimi','fusiki',null);
