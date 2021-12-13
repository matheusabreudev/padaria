package com.betatech.padaria.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.betatech.padaria.entities.FuncionarioEntity;
import com.betatech.padaria.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/padaria")
public class FuncionarioController {
	
	private final FuncionarioService funcionarioService;
	
	@GetMapping(path = "/funcionarios")
	public ResponseEntity<List<FuncionarioEntity>> getFuncionarios() {
		return ResponseEntity.ok().body(funcionarioService.getFuncionariosEntities());
	}
	
	@PostMapping(path = "/funcionario/salvar")
	public ResponseEntity<FuncionarioEntity> saveUser(@RequestBody FuncionarioEntity funcionarioEntity) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/padaria/funcionario/save").toUriString());
		return ResponseEntity.created(uri).body(funcionarioService.salvar(funcionarioEntity));
	}
	
	@GetMapping(path= "/funcionario/{id}")
	public ResponseEntity<FuncionarioEntity> getfuncionarioById (@PathVariable("id") Long id) {
		FuncionarioEntity funcionarioEntity = funcionarioService.findFuncionarioById(id);
		return new ResponseEntity<>(funcionarioEntity, HttpStatus.OK);
	}
	
	@PutMapping(path= "/attFuncionario")
	public ResponseEntity<FuncionarioEntity> updateFuncionario(@RequestBody FuncionarioEntity funcionarioEntity) {
		FuncionarioEntity updateFuncionario = funcionarioService.updateFuncionario(funcionarioEntity);
		return new ResponseEntity<>(updateFuncionario, HttpStatus.OK);
	}
	
	@DeleteMapping(path= "/deletar/funcionario/{id}")
	public ResponseEntity<?> deleteFuncionario(@PathVariable("id") Long id) {
		funcionarioService.deleteFuncionario(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path = "/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				FuncionarioEntity funcionarioEntity = funcionarioService.getFuncionario(username);

				String access_token = JWT.create().withSubject(funcionarioEntity.getUsuario())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.sign(algorithm);

				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
				// response.sendError(org.springframework.http.HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}

}
