package org.mizuro.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "letter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sentAt;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "sender", referencedColumnName = "email")
    private UserEntity sender;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "recipient", referencedColumnName = "email")
    private UserEntity recipient;
}
