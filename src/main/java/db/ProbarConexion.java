package db;

import java.sql.Connection;

public class ProbarConexion {
	
	public static void main(String[] args) {
		try {
			Connection cn = AccesoDB.getConnection();
			System.out.println("Conexión ok.");
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
