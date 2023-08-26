package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.AccesoDB;
import model.Operation;
import service.spec.CrudServiceSpec2;
import service.spec.RowMapper;

public class CrudOperation implements CrudServiceSpec2<Operation>, RowMapper<Operation> {

    private final String SQL_SELECT_BASE = "SELECT id, date_time, transaction_type, description, amount, source_account, destination_account, manager_id, attorney_id, payment_method, voucher_number FROM operation";
    private final String SQL_INSERT = "INSERT INTO operation(date_time, transaction_type, description, amount, source_account, destination_account, manager_id, attorney_id, payment_method, voucher_number) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE operation SET date_time = ?, transaction_type = ?, description = ?, amount = ?, source_account = ?, destination_account = ?, manager_id = ?, attorney_id = ?, payment_method = ?, voucher_number = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM operation WHERE id = ?";

    @Override
    public List<Operation> getAll() {
        Connection cn = null;
        List<Operation> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Operation bean;

        try {
            cn = AccesoDB.getConnection();
            pstm = cn.prepareStatement(SQL_SELECT_BASE);
            rs = pstm.executeQuery();

            while (rs.next()) {
                bean = mapRow(rs);
                lista.add(bean);
            }

            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }

        return lista;
    }

    @Override
    public Operation getForId(String id) {
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Operation bean = null;
        String sql;

        try {
            cn = AccesoDB.getConnection();
            sql = SQL_SELECT_BASE + " WHERE id = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(id));
            rs = pstm.executeQuery();

            if (rs.next()) {
                bean = mapRow(rs);
            }

            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }

        return bean;
    }

    @Override
    public List<Operation> get(Operation bean) {
        Connection cn = null;
        List<Operation> lista = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Operation item;
        String sql;
        String description;

        description = "%" + UtilService.setStringVacio(bean.getDescription()) + "%";

        try {
            cn = AccesoDB.getConnection();
            sql = SQL_SELECT_BASE + " WHERE description LIKE ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, description);
            rs = pstm.executeQuery();

            while (rs.next()) {
                item = mapRow(rs);
                lista.add(item);
            }

            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }

        return lista;
    }

    @Override
    public void insert(Operation bean) {
        Connection cn = null;
        PreparedStatement pstm = null;

        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            pstm = cn.prepareStatement(SQL_INSERT);
            pstm.setString(1, bean.getDate_time());
            pstm.setString(2, bean.getTransaction_Type());
            pstm.setString(3, bean.getDescription());
            pstm.setDouble(4, bean.getAmount());
            pstm.setString(5, bean.getSource_Account());
            pstm.setString(6, bean.getDestinationAccount());
            pstm.setInt(7, bean.getManagerId());
            pstm.setInt(8, bean.getAttorneyId());
            pstm.setString(9, bean.getPayment_Method());
            pstm.setString(10, bean.getVoucher_Number());
            pstm.executeUpdate();
            pstm.close();

            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
                cn.close();
            } catch (Exception e2) {
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }
    }

    @Override
    public void update(Operation bean) {
        Connection cn = null;
        PreparedStatement pstm = null;

        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            pstm = cn.prepareStatement(SQL_UPDATE);
            pstm.setString(1, bean.getDate_time());
            pstm.setString(2, bean.getTransaction_Type());
            pstm.setString(3, bean.getDescription());
            pstm.setDouble(4, bean.getAmount());
            pstm.setString(5, bean.getSource_Account());
            pstm.setString(6, bean.getDestinationAccount());
            pstm.setInt(7, bean.getManagerId());
            pstm.setInt(8, bean.getAttorneyId());
            pstm.setString(9, bean.getPayment_Method());
            pstm.setString(10, bean.getVoucher_Number());
            pstm.setInt(11, bean.getId());
            pstm.executeUpdate();
            pstm.close();

            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
                cn.close();
            } catch (Exception e2) {
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }
    }

    @Override
    public void delete(String id) {
        Connection cn = null;
        PreparedStatement pstm = null;

        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            pstm = cn.prepareStatement(SQL_DELETE);
            pstm.setInt(1, Integer.parseInt(id));
            pstm.executeUpdate();
            pstm.close();

            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
                cn.close();
            } catch (Exception e2) {
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
            }
        }
    }

    @Override
    public Operation mapRow(ResultSet rs) throws SQLException {
        Operation bean = new Operation();
        bean.setId(rs.getInt("id"));
        bean.setDate_time(rs.getString("date_time"));
        bean.setTransaction_Type(rs.getString("transaction_type"));
        bean.setDescription(rs.getString("description"));
        bean.setAmount(rs.getDouble("amount"));
        bean.setSource_Account(rs.getString("source_account"));
        bean.setDestinationAccount(rs.getString("destination_account"));
        bean.setManagerId(rs.getInt("manager_id"));
        bean.setAttorneyId(rs.getInt("attorney_id"));
        bean.setPayment_Method(rs.getString("payment_method"));
        bean.setVoucher_Number(rs.getString("voucher_number"));
        return bean;
    }
}
