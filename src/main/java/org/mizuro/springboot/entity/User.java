package org.mizuro.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "person")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @Size(min = 8, max = 32, message = "Username's size should be 8-32 symbols")
    @NotNull(message = "Username shouldn't be empty")
    private String username;

    @Column(name = "password")
    @Size(min = 8, max = 16, message = "Password's size should be 8-16 symbols")
    @NotNull(message = "Password shouldn't be empty")
    private String password;

    @Column(name = "surname")
    @NotNull(message = "Surname shouldn't be empty")
    private String surname;

    @Column(name = "name")
    @NotNull(message = "Name shouldn't be empty")
    private String name;

    @Column(name = "email")
    @Email(message = "Enter correct email address")
    @NotNull(message = "Email shouldn't be empty")
    private String email;

    @Column(name = "birth_date")
    @NotNull(message = "Date of birth shouldn't be empty")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}
