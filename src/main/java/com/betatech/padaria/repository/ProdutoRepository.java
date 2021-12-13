package com.betatech.padaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betatech.padaria.entities.ProdutoEntity;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
	ProdutoEntity findByNome(String nome);

	void deleteProdutoById(Long id);

	Optional<ProdutoEntity> findProdutoById(Long id);
}
