package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.Faculty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long> {
    @Override
    Set<Faculty> findAll();

    @Query("FROM Faculty f ORDER BY f.name ASC")
    Set<Faculty> findAllOrderByNameAsc();

    @Query("FROM Faculty f ORDER BY f.name DESC")
    Set<Faculty> findAllOrderByNameDesc();

    @Query("FROM Faculty f ORDER BY f.allPlaces DESC")
    Set<Faculty> findAllOrderByAllPlacesDesc();

    @Query("FROM Faculty f ORDER BY f.budgetPlaces DESC")
    Set<Faculty> findAllOrderByBudgetPlacesDesc();

/*

    @Query("SELECT t.faculty FROM TeacherFaculty t WHERE t.teacher.login = ?1 AND t.endDate is null ORDER BY t.faculty.name ASC")
    Set<Faculty> findAllFilterByTeacherLoginOrderByNameAsc(String login);

    @Query("SELECT t.faculty FROM TeacherFaculty t WHERE t.teacher.login = ?1  AND t.endDate is null ORDER BY t.faculty.name DESC")
    Set<Faculty> findAllFilterByTeacherLoginOrderByNameDesc(String login);

    @Query("SELECT t.faculty FROM TeacherFaculty t WHERE t.teacher.login = ?1  AND t.endDate is null ORDER BY t.faculty.allPlaces DESC")
    Set<Faculty> findAllFilterByTeacherLoginOrderByAllPlacesDesc(String login);

    @Query("SELECT t.faculty FROM TeacherFaculty t WHERE t.teacher.login = ?1  AND t.endDate is null ORDER BY t.faculty.budgetPlaces DESC")
    Set<Faculty> findAllFilterByTeacherLoginOrderByBudgetPlacesDesc(String login);
*/
//    @Query("from Faculty as f left join fetch f.requiredSubjectSet left join fetch f.teacherFacultySet where f.name = :name")
    Optional<Faculty> findFacultyByName(/*@Param("name")*/String name);


    @Modifying
    @Query("UPDATE Faculty f set f.allPlaces = ?2 WHERE f = ?1")
    int updateSetAllPlaces(Faculty faculty, int allPlaces);

    @Modifying
    @Query("UPDATE Faculty f set f.allPlaces = ?2, f.budgetPlaces = ?3 WHERE f = ?1")
    int updateSetAllPlacesAndBudgetPlaces(Faculty faculty, int allPlaces, int budgetPlaces);
}
