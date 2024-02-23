package org.mizuro.springboot.repo;

import org.mizuro.springboot.entity.LetterEntity;
import org.mizuro.springboot.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterRepo extends JpaRepository<LetterEntity, Integer> {
    List<LetterEntity> findAllByRecipient(UserEntity userEntity, Sort by);
    List<LetterEntity> findAllBySender(UserEntity userEntity, Sort by);
}
