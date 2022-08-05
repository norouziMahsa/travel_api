package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.model.Location;
import com.afkl.travel.exercise.model.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.afkl.travel.exercise.constants.TravelQuery.GET_BY_LANGUAGE_AND_TYPE_AND_CODE;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = GET_BY_LANGUAGE_AND_TYPE_AND_CODE)
    List<Location> findByLanguageAndTypeAndCode(LocationType type, String code, String language);
}
