package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.Faculty;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long> {
    @Override
    Set<Faculty> findAll();

    Set<Faculty> findAll(Sort sort);

    Set<Faculty> findAllByIsDeleted(Sort sort, boolean isDeleted);

    @Query("from Faculty as f left join fetch f.requiredSubjectSet where f.name = ?1")
    Optional<Faculty> findFacultyByName(String name);

    @Query("from Faculty as f left join fetch f.requiredSubjectSet where f.name = ?1 and f.isDeleted = ?2")
    Optional<Faculty> findFacultyByNameAndDeletedIs(String name, boolean deleted);

    @Modifying
    @Query("UPDATE Faculty f set f.allPlaces = ?2 WHERE f = ?1")
    int updateSetAllPlaces(Faculty faculty, int allPlaces);

    @Modifying
    @Query("UPDATE Faculty f set f.allPlaces = ?2, f.budgetPlaces = ?3 WHERE f = ?1")
    int updateSetAllPlacesAndBudgetPlaces(Faculty faculty, int allPlaces, int budgetPlaces);

    @Modifying
    @Query("UPDATE Faculty f set f.isDeleted = ?2 WHERE f.name = ?1")
    int updateSetDeleted(String name, boolean deleted);

}
