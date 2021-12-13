package com.betatech.padaria.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.betatech.padaria.entities.ProdutoEntity;
import com.betatech.padaria.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ProdutoServiceImpl implements ProdutoService{
	
	private final ProdutoRepository produtoRepository;

	@Override
	public ProdutoEntity salvar(ProdutoEntity produtoEntity) {
		log.info("Salvando um novo produto {} no database", produtoEntity.getNome());
		return produtoRepository.save(produtoEntity);
	}

	@Override
	public ProdutoEntity getProdutoEntity(String nome) {
		log.info("Informações do produto {}", nome);
		return produtoRepository.findByNome(nome);
	}

	@Override
	public List<ProdutoEntity> getProdutoEntities() {
		log.info("Informações de todos os produtos");
		return produtoRepository.findAll();
	}

	@Override
	public ProdutoEntity findProdutoById(Long id) {
		return (ProdutoEntity) produtoRepository.findProdutoById(id).orElseThrow(() -> new UsernameNotFoundException("User by id " +id+ "was not found."));
	}

	@Override
	public void deleteProduto(Long id) {
		produtoRepository.deleteProdutoById(id);
		
	}

	@Override
	public ProdutoEntity updateProduto(ProdutoEntity produtoEntity) {
		return produtoRepository.save(produtoEntity);
	}


}
