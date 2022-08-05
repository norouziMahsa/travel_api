package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.model.Translation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TranslationRepository extends CrudRepository<Translation, Integer> {

    List<Translation> findAllByLanguage(String language);
}
