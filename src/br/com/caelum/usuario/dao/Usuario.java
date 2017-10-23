package br.com.caelum.usuario.dao;

public class Usuario {
		
		private Long id;
		private String login;
		private String senha;

		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getLogin() {
			return login;
		}
		
		public void setLogin(String login) {
			this.login = login;
		}
		
		public String getSenha() {
			return senha;
		}
		
		public void setSenha(String senha) {
			this.senha = senha;
		}
		
		@Override
		public String toString() {
			String campos = "";
			
			campos = campos.concat("id = " + this.id);
			campos = campos.concat(", login = " + this.login);
			campos = campos.concat(", senha = " + this.senha);			
			return campos;
		}
}
