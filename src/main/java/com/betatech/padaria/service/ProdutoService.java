package com.betatech.padaria.service;

import java.util.List;

import com.betatech.padaria.entities.ProdutoEntity;


public interface ProdutoService {
	
	ProdutoEntity salvar(ProdutoEntity produtoEntity);
	ProdutoEntity getProdutoEntity(String nome);
	List<ProdutoEntity>getProdutoEntities();
	ProdutoEntity findProdutoById(Long id);
	void deleteProduto(Long id);
	ProdutoEntity updateProduto(ProdutoEntity produtoEntity);
}
