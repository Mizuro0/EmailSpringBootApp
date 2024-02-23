package org.mizuro.springboot.services;

import lombok.AllArgsConstructor;
import org.mizuro.springboot.entity.LetterEntity;
import org.mizuro.springboot.entity.UserEntity;
import org.mizuro.springboot.repo.LetterRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class LetterService {
    private final LetterRepo letterRepo;

    @Transactional
    public void save(LetterEntity letter) {
        letter.setSentAt(new Date());
        letterRepo.save(letter);
    }

    public List<LetterEntity> findInbox(UserEntity userEntity, String sortBy) {
        return letterRepo.findAllByRecipient(userEntity, Sort.by(sortBy));
    }

    public List<LetterEntity> findSent(UserEntity userEntity, String sortBy) {
        return letterRepo.findAllBySender(userEntity, Sort.by(sortBy));
    }

    public LetterEntity findOne(int id) {
        return letterRepo.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, LetterEntity letter) {
        letter.setId(id);
        letterRepo.save(letter);
    }

    @Transactional
    public void remove(int id) {
        letterRepo.deleteById(id);
    }
}
