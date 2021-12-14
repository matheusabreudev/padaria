package com.betatech.padaria;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.betatech.padaria.entities.FuncionarioEntity;
import com.betatech.padaria.entities.ProdutoEntity;
import com.betatech.padaria.entities.RoleEntity;
import com.betatech.padaria.service.FuncionarioService;
import com.betatech.padaria.service.ProdutoService;

@SpringBootApplication
public class PadariaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PadariaApiApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	@Bean
	CommandLineRunner runner(FuncionarioService funcionarioService, ProdutoService produtoService) {
		return args -> {
			funcionarioService.saveRole(new RoleEntity(null,"ROLE_USER"));
			funcionarioService.saveRole(new RoleEntity(null,"ROLE_MANAGER"));
			funcionarioService.saveRole(new RoleEntity(null,"ROLE_ADMIN"));
			funcionarioService.saveRole(new RoleEntity(null,"ROLE_SUPER_ADMIN"));
			
			funcionarioService.salvar(new FuncionarioEntity(null, "Matheus Abreu", "matheus123", "1234", "matheus@betatech.com", new ArrayList<>()));
			funcionarioService.salvar(new FuncionarioEntity(null, "Joao Alfredo", "joao123", "1234", "joao@betatech.com", new ArrayList<>()));
			funcionarioService.salvar(new FuncionarioEntity(null, "Jose Mauricio", "jose123", "1234", "jose@betatech.com", new ArrayList<>()));
			funcionarioService.salvar(new FuncionarioEntity(null, "Antonio Furtado", "antonio123", "1234", "antonio@betatech.com", new ArrayList<>()));

			funcionarioService.addRoleToFuncionario("joao123", "ROLE_USER");
			funcionarioService.addRoleToFuncionario("jose123", "ROLE_MANAGER");
			funcionarioService.addRoleToFuncionario("antonio123", "ROLE_ADMIN");
			funcionarioService.addRoleToFuncionario("matheus123", "ROLE_USER");
			funcionarioService.addRoleToFuncionario("matheus123", "ROLE_SUPER_ADMIN");
			
			produtoService.salvar(new ProdutoEntity(null,7,"Pão Bengala","7.99","Pãozinho feito com amor do tamanho da sua fome"));
			produtoService.salvar(new ProdutoEntity(null,15,"Coxinha","3.90","Late Coração"));
			
		};
		
	}
	*/

}
