package ua.nure.petrov.Course.db.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Владислав on 28.07.2015.
 */
public class DAO {

    private static final Logger LOG = Logger.getLogger(DAO.class);

    protected PreparedStatement pst = null;
    protected ResultSet rs = null;
    protected Connection con = null;
    protected static final int FIRST = 1;

    protected void rollBack(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    protected void commit(Connection con) {
        if (con != null) {
            try {
                con.commit();
                con.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }
}
