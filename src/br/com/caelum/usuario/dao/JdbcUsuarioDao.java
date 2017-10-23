package br.com.caelum.usuario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.caelum.usuario.dao.Usuario;
import br.com.caelum.tarefas.jdbc.ConnectionFactory;

public class JdbcUsuarioDao {
		private Connection connection;

		public JdbcUsuarioDao(Connection connection) {
			this.connection = connection;
		}
		
		public JdbcUsuarioDao() {
			this.setConnection(new ConnectionFactory().getConnection());
		}
		
		public Connection getConnection() {
			return connection;
		}
		
		public void setConnection(Connection connection) {
			this.connection = connection;
		}
		
		public boolean existeUsuario (Usuario usuario) {
			if (usuario == null) {
				throw new IllegalArgumentException("Usuário não deve ser nulo");
			}
			
			try {
				PreparedStatement stmt = this.connection.prepareStatement("select * from usuarios where login = ? and senha = ?");
				stmt.setString(1, usuario.getLogin());
				stmt.setString(2, usuario.getSenha());
				ResultSet rs = stmt.executeQuery();
				
				boolean encontrado = rs.next();
				rs.close();
				stmt.close();
				
				return encontrado;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
}
