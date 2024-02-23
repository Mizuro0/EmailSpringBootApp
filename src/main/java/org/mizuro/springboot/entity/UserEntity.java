package org.mizuro.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "person")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Password shouldn't be empty")
    @Size(min = 8, message = "Password should be >= 8 symbols")
    private String password;

    @NotNull(message = "Surname shouldn't be empty")
    private String surname;

    @NotNull(message = "Name shouldn't be empty")
    private String name;

    @Email(message = "Enter correct email address")
    @NotNull(message = "Email shouldn't be empty")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth shouldn't be empty")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private ERole eRole = ERole.ROLE_USER;

    @OneToMany(mappedBy = "sender")
    private List<LetterEntity> sentLetters;

    @OneToMany(mappedBy = "recipient")
    private List<LetterEntity> inboxLetters;
}
