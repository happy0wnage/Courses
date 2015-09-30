package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.News;

import java.util.List;

/**
 * Created by Владислав on 28.07.2015.
 */
public interface NewsDao {

    List<News> getNewsByIdAuthor(int idUser);

    News getNewsById(int id);

    List<News> getNewsByIdCourse(int idCourse);

    List<News> getNewsByIdUser(int idUser);

    List<News> getAllNews();

    void updateNews(News news);

    void deleteNewsById(int id);

    void insertNews(News news);
}
