package com.betatech.padaria.service;

import java.util.List;

import com.betatech.padaria.entities.FuncionarioEntity;

public interface FuncionarioService {
	
	FuncionarioEntity salvar(FuncionarioEntity funcionarioEntity);
	FuncionarioEntity getFuncionario(String usuario);
	List<FuncionarioEntity>getFuncionariosEntities();
	FuncionarioEntity findFuncionarioById(Long id);
	void deleteFuncionario(Long id);
	FuncionarioEntity updateFuncionario(FuncionarioEntity funcionarioEntity);

}
