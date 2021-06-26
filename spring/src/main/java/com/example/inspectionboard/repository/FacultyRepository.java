package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.Faculty;
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

    @Query("from Faculty as f left join fetch f.requiredSubjectSet where f.name = ?1")
    Optional<Faculty> findFacultyByName(String name);

    @Query("FROM Faculty f ORDER BY f.name ASC")
    Set<Faculty> findAllOrderByNameAsc();

    @Query("FROM Faculty f ORDER BY f.name DESC")
    Set<Faculty> findAllOrderByNameDesc();

    @Query("FROM Faculty f ORDER BY f.allPlaces DESC")
    Set<Faculty> findAllOrderByAllPlacesDesc();

    @Query("FROM Faculty f ORDER BY f.budgetPlaces DESC")
    Set<Faculty> findAllOrderByBudgetPlacesDesc();

    @Modifying
    @Query("UPDATE Faculty f set f.allPlaces = ?2 WHERE f = ?1")
    int updateSetAllPlaces(Faculty faculty, int allPlaces);

    @Modifying
    @Query("UPDATE Faculty f set f.allPlaces = ?2, f.budgetPlaces = ?3 WHERE f = ?1")
    int updateSetAllPlacesAndBudgetPlaces(Faculty faculty, int allPlaces, int budgetPlaces);
}
