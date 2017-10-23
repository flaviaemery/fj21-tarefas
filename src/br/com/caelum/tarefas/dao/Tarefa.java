package br.com.caelum.tarefas.dao;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Tarefa {
	
	private Long id;
	
	@NotNull @Size(min=5)
	private String descricao;
	private boolean finalizado;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Calendar dataFinalizacao;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean getFinalizado() {
		return finalizado;
	}
	
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	
	public Calendar getDataFinalizacao() {
		return dataFinalizacao;
	}
	
	public void setDataFinalizacao(Calendar dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	
	@Override
	public String toString() {
		String campos = "";
		
		campos = campos.concat("id = " + this.id);
		campos = campos.concat(", descricao = " + this.descricao);
		campos = campos.concat(", finalizado = " + this.finalizado);
		campos = campos.concat(", dataFinaizacao = " + this.dataFinalizacao);
		
		return campos;
	}
	
}
