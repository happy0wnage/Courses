package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.Theme;

import java.util.List;

/**
 * Created by Владислав on 28.07.2015.
 */
public interface ThemeDao {

    Theme getThemeByIdCourse(int idCourse);

    List<Theme> getAllThemes();

    Theme getThemeById(int id);

    void deleteThemeById(int id);

    void insertTheme(Theme theme);



}
