package com.betatech.padaria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betatech.padaria.entities.ProdutoEntity;
import com.betatech.padaria.service.ProdutoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/padaria")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	
	@GetMapping(path= "/produtos")
	public ResponseEntity<List<ProdutoEntity>> getProdutos() {
		return ResponseEntity.ok().body(produtoService.getProdutoEntities());
	}
	
	@GetMapping(path= "/produto/{id}")
	public ResponseEntity<ProdutoEntity> getProdutoById (@PathVariable("id") Long id) {
		ProdutoEntity produtoEntity = produtoService.findProdutoById(id);
		return new ResponseEntity<>(produtoEntity, HttpStatus.OK);
	}
	
	@PostMapping(path= "/salvar")
	public ResponseEntity<ProdutoEntity> salvarProduto(@RequestBody ProdutoEntity produtoEntity) {
		ProdutoEntity newProdutoEntity = produtoService.salvar(produtoEntity);
		return new ResponseEntity<>(newProdutoEntity, HttpStatus.CREATED);
	}
	
	@PutMapping(path= "/attProduto")
	public ResponseEntity<ProdutoEntity> updateProduto(@RequestBody ProdutoEntity produtoEntity) {
		ProdutoEntity updateProduto = produtoService.updateProduto(produtoEntity);
		return new ResponseEntity<>(updateProduto, HttpStatus.OK);
	}
	
	@DeleteMapping(path= "/deletar/produto/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable("id") Long id) {
		produtoService.deleteProduto(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
