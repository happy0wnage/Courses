package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.ThemeDao;
import ua.nure.petrov.Course.db.entity.db.Theme;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 28.07.2015.
 */
public class ThemeDaoImpl extends DAO implements ThemeDao {

    @Override
    public Theme getThemeByIdCourse(int idCourse) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_THEME_BY_ID_COURSE);
            pst.setInt(FIRST, idCourse);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractTheme(rs);

        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Theme with idCourse = " + idCourse, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Theme> getAllThemes() {
        List<Theme> themes = new ArrayList<Theme>();

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_THEMES);
            rs = pst.executeQuery();
            while (rs.next()) {
                themes.add(extractTheme(rs));
            }
            return themes;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get all themes", e);
        } finally {
            commit(con);
        }

    }

    @Override
    public Theme getThemeById(int idTheme) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_THEME_BY_ID);
            pst.setInt(FIRST, idTheme);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractTheme(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Theme with id = " + idTheme, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteThemeById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_THEME_BY_ID);
            pst.setInt(FIRST, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to delete Theme with id = " + id, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void insertTheme(Theme theme) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.INSERT_THEME);
            pst.setString(k++, theme.getName());
            pst.setString(k, theme.getDescription());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to insert Theme " + theme, e);
        } finally {
            commit(con);
        }
    }

    private Theme extractTheme(ResultSet rs) throws SQLException {
        Theme theme = new Theme(rs.getInt(Fields.THEME_ID_THEME));
        theme.setName(rs.getString(Fields.THEME_NAME));
        theme.setDescription(rs.getString(Fields.THEME_DESCRIPTION));
        return theme;
    }
}
