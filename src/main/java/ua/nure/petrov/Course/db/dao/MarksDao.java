package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.Marks;

import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public interface MarksDao {

    List<Marks> getMarksByIdUser(int idUser);

    List<Marks> getMarksByIdUserAndJournal(int idUser, int idJournal);

    List<Marks> getMarksByIdJournal(int idJournal);

    List<Marks> getAllMarks();

    List<Marks> getAllMarksByIdLecturer(int idUserLecturer);

    Marks getMarkById(int idMark);

    void insertMark(Marks mark);

    void updateMark(Marks mark);

    void deleteMarkById(int id);

}
