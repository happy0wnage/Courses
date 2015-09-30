package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.RoleDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 01.08.2015.
 */
public class RoleDaoImpl extends DAO implements RoleDao {

    @Override
    public Role getRoleById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ROLE_BY_ID);
            pst.setInt(FIRST, id);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractRole(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Roles with id: " + id, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public Role getRoleByName(String name) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ROLE_BY_NAME);
            pst.setString(FIRST, name);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractRole(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Roles with name: " + name, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<Role>();

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_ROLES);
            rs = pst.executeQuery();
            while (rs.next()) {
                roles.add(extractRole(rs));
            }
            return roles;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Roles", e);
        } finally {
            commit(con);
        }
    }

    private Role extractRole(ResultSet rs) throws SQLException {
        Role role = new Role(rs.getInt(Fields.ROLE_ID_ROLE));
        role.setName(rs.getString(Fields.ROLE_NAME));
        return role;
    }
}
