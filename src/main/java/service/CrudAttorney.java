package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.AccesoDB;
import model.Attorney;
import service.spec.CrudServiceSpec;
import service.spec.RowMapper;

public class CrudAttorney implements CrudServiceSpec<Attorney>, RowMapper<Attorney> {

	// Definiendo cosas
	private final String SQL_SELECT_BASE = "SELECT id id, names, last_names, document_type, email, document_number, cell_phone, status  FROM attorney ";
	private final String SQL_INSERT = "INSERT INTO attorney(names,last_names,document_type,email,document_number,cell_phone,status) VALUES(?,?,?,?,?,?,?)";
	private final String SQL_UPDATE = "UPDATE attorney SET names=?,last_names=?,document_type=?,email=?,document_number=?,cell_phone=?,status=? WHERE id = ?";
	private final String SQL_DELETE = "DELETE FROM attorney WHERE id=?";
	// Borrado logico (De Activo a Inactivo y viceversa)
	private final String SQL_TOGGLE_STATUS = "UPDATE attorney SET status = CASE WHEN status = 'A' THEN 'I' ELSE 'A' END WHERE id = ?";


	@Override
	public List<Attorney> getAll() {
		// Variables
		Connection cn = null;
		List<Attorney> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Attorney bean;
		// Proceso
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
	public Attorney getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Attorney bean = null;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE id=?";
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

	/**
	 * Realiza la busqueda por apellido y nombre.
	 */
	@Override
	public List<Attorney> get(Attorney bean) {
		// Variables
		Connection cn = null;
		List<Attorney> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Attorney item;
		String sql;
		String last_names;
		String names;
		// Preparar los datos
		last_names = "%" + UtilService.setStringVacio(bean.getLast_names()) + "%";
		names = "%" + UtilService.setStringVacio(bean.getNames()) + "%";
		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_BASE + " WHERE last_names LIKE ? AND names LIKE ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, last_names);
			pstm.setString(2, names);
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
	public void insert(Attorney bean) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		Integer id = 0;
		// Proceso
		try {
			// Iniciar la Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			// Insertar nuevo empleado
			pstm = cn.prepareStatement(SQL_INSERT);
			pstm.setString(1, bean.getNames());
	        pstm.setString(2, bean.getLast_names());
	        pstm.setString(3, bean.getDocument_type());
	        pstm.setString(4, bean.getEmail());
	        pstm.setString(5, bean.getDocument_number());
	        pstm.setString(6, bean.getCell_phone());
	        pstm.setString(7, bean.getStatus());
			pstm.executeUpdate();
			pstm.close();
			// Fin de Tx
			bean.setId(id);
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
	public void update(Attorney bean) {
	    // Variables
	    Connection cn = null;
	    PreparedStatement pstm = null;

	    // Proceso
	    try {
	        // Iniciar la Tx
	        cn = AccesoDB.getConnection();
	        cn.setAutoCommit(false);

	        // Actualizar el registro
	        pstm = cn.prepareStatement(SQL_UPDATE);
	        pstm.setString(1, bean.getNames());
	        pstm.setString(2, bean.getLast_names());
	        pstm.setString(3, bean.getDocument_type());
	        pstm.setString(4, bean.getEmail());
	        pstm.setString(5, bean.getDocument_number());
	        pstm.setString(6, bean.getCell_phone());
	        pstm.setString(7, bean.getStatus());
	        pstm.setInt(8, bean.getId()); // Agregar el id para la cláusula WHERE
	        pstm.executeUpdate(); // Ejecutar la sentencia de actualización
	        pstm.close();

	        // Fin de Tx
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
	    // Variables
	    Connection cn = null;
	    String sql = null;
	    PreparedStatement pstm = null;
	    // Proceso
	    try {
	        // Iniciar la Tx
	        cn = AccesoDB.getConnection();
	        cn.setAutoCommit(false);
	        pstm = cn.prepareStatement(SQL_DELETE);
			pstm.setInt(1, Integer.parseInt(id));

	        // Eliminar el estudiante de la tabla
	        sql = "DELETE FROM attorney WHERE id = ?";
	        pstm = cn.prepareStatement(sql);
	        pstm.setString(1, id);
	        pstm.executeUpdate();
	        pstm.close();

	        // Fin de Tx
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

	/*Borrado Logico */
	
	public void deleteByStatus(String id) {
	    // Variables
	    Connection cn = null;
	    PreparedStatement pstm = null;

	    try {
	        // Establecer conexión
	        cn = AccesoDB.getConnection();
	        // Preparar consulta
	        pstm = cn.prepareStatement(SQL_TOGGLE_STATUS);
	        pstm.setInt(1, Integer.parseInt(id)); // Convertir el id a entero
	        // Ejecutar consulta
	        pstm.executeUpdate();
	    } catch (SQLException e) {
	        // Manejar excepciones
	        throw new RuntimeException("Error al cambiar el estado del apoderado: " + e.getMessage());
	    } finally {
	        // Cerrar recursos
	        try {
	            if (pstm != null) {
	                pstm.close();
	            }
	            if (cn != null) {
	                cn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}





	

	@Override
	public Attorney mapRow(ResultSet rs) throws SQLException {
		Attorney bean = new Attorney();
		// Columnas: id,  nombre, apellido, tipo de documento, numero de documento, email,  celular, estado 
		bean.setId(rs.getInt("id"));
		bean.setNames(rs.getString("names"));
		bean.setLast_names(rs.getString("last_names"));		
		bean.setDocument_type(rs.getString("document_type"));
		bean.setDocument_number(rs.getString("document_number"));
		bean.setEmail(rs.getString("email"));		
		bean.setCell_phone(rs.getString("cell_phone"));
		bean.setStatus(rs.getString("status"));
		return bean;
	}

	

}
