package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.Journal;

import java.util.List;

/**
 * Created by Владислав on 28.07.2015.
 */
public interface JournalDao {

    void insertJournal(Journal journal);

    void deleteJournalById(int id);

    Journal getJournalById(int id);

    List<Journal> getJournalByIdCourse(int idCourse);

    List<Journal> getJournalsByIdUser(int idUser);

    List<Journal> getAllJournals();

    List<Journal> getJournalsByIdLecturer(int idUserLecturer);

}
