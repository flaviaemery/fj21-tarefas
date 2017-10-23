package br.com.caelum.tarefas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.tarefas.daoException.DAOException;
import br.com.caelum.tarefas.jdbc.ConnectionFactory;

@Repository
public class JdbcTarefaDao {
	private Connection connection;

	public JdbcTarefaDao(Connection connection) {
		this.connection = connection;
	}
	
	@Autowired
	public JdbcTarefaDao(DataSource dataSource) {
		try {
			this.connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
public void adiciona (Tarefa tarefa) {
	String sql = "insert into tarefas" + "(descricao, finalizado, dataFinalizacao)" + "values (?,?,?)";
	
	try {
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setString(1, tarefa.getDescricao());
		stmt.setBoolean(2, tarefa.getFinalizado());

		//Converte calendar para Data do SQL
		Date dataDoSql = null;
		if (tarefa.getDataFinalizacao() != null) {
		long dataDeFinalizacaoEmMilissegundos = tarefa.getDataFinalizacao().getTimeInMillis();
		dataDoSql = new Date(dataDeFinalizacaoEmMilissegundos);
		}
		
		stmt.setDate(3, dataDoSql);
		stmt.execute();
		stmt.close();
	}catch (SQLException e) {
		// TODO: handle exception
		throw new DAOException(e, DAOException._FAIL_TO_INSERT, "Erro ao inserir tarefa");
	}
}

public List<Tarefa> getLista() {
	try {
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		PreparedStatement stmt = this.connection.prepareStatement("select * from tarefas");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			//criando o objeto Contato
			Tarefa tarefa = new Tarefa();
			tarefa.setId(rs.getLong("id"));
			tarefa.setDescricao(rs.getString("descricao"));
			tarefa.setFinalizado(rs.getBoolean("finalizado"));
			
			//Converte calendar para Data do SQL
			Date dataDoSqlList = null;
			if (tarefa.getDataFinalizacao() != null) {
			long dataDeFinalizacaoEmMilissegundos = tarefa.getDataFinalizacao().getTimeInMillis();
			dataDoSqlList = new Date(dataDeFinalizacaoEmMilissegundos);
			}
			
			//adicionando o objeto à Lista
			tarefas.add(tarefa);
		}
		rs.close();
		stmt.close();
		return tarefas;
	} catch (SQLException e) {
		throw new DAOException(e, DAOException._ERROR_GETTING_LIST, "Erro ao recuperar tarefas do banco de dados");
	}
}

public void altera(Tarefa tarefa) {
	String sql = "update tarefas set descricao=?, finalizado=?, " + 
				"dataFinalizacao=? where id=?";
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, tarefa.getDescricao());
				stmt.setBoolean(2, tarefa.getFinalizado());
				Date dataDoSqlList = null;
				if (tarefa.getDataFinalizacao() != null) {
					long dataDeFinalizacaoEmMilissegundos = tarefa.getDataFinalizacao().getTimeInMillis();
					dataDoSqlList = new Date(dataDeFinalizacaoEmMilissegundos);
					stmt.setDate(3,dataDoSqlList);
				} else {
					stmt.setNull(3, Types.DATE);
				} 
				Long id = tarefa.getId();
				stmt.setLong(4,id);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
}

public void remove(Tarefa tarefa) {
	try {
		PreparedStatement stmt = connection.prepareStatement("delete from tarefas where id=?");
		stmt.setLong(1, tarefa.getId());
		stmt.execute();
		stmt.close();
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}
	
public Tarefa buscaPorId (Long id) {
		
		try {
			//Cria a consulta SQL
			PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM tarefas WHERE id = ?");
			//Seta o id procurado na consulta SQL
			stmt.setLong(1, id);
			
			//Executa a consulta
			ResultSet rs = stmt.executeQuery();
					
			//Cria um objeto do tipo Contato vazio
			Tarefa tarefa = null;
			
			while (rs.next()) {
				tarefa = new Tarefa();
				//Preenche o objeto com as informações recuperadas do banco de dados
				tarefa.setId(rs.getLong("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
				//Converte calendar para Data do SQL
				Calendar dataCalendar = null;
				if (tarefa.getDataFinalizacao() != null) {
					Date Data = rs.getDate(4);
					dataCalendar = new GregorianCalendar();
					dataCalendar.setTime(Data);
					tarefa.setDataFinalizacao(dataCalendar);
				} 
			}
			
			rs.close();
			stmt.close();
			return tarefa;
		} catch (SQLException e) {
			throw new DAOException(e, DAOException._ERRO_NA_PESQUISA, "Erro ao pesquisar a tarefa com o id=" + id + " no banco de dados.");
		}
}

/**
 * 
 * @param id
 */
public void finaliza (Long id) {
	Tarefa tarefa = buscaPorId(id);
	tarefa.setFinalizado(true);
	System.out.println("Tarefa = " + tarefa);
	//tarefa.getDataFinalizacao();
	altera(tarefa);
}
}
