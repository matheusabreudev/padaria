package com.betatech.padaria.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.betatech.padaria.entities.FuncionarioEntity;
import com.betatech.padaria.entities.RoleEntity;
import com.betatech.padaria.repository.FuncionarioRepository;
import com.betatech.padaria.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FuncionarioServiceImpl implements FuncionarioService, UserDetailsService {
	
	private final FuncionarioRepository funcionarioRepository;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		FuncionarioEntity funcionarioEntity = funcionarioRepository.findByUsuario(username);
		if(funcionarioEntity == null) {
			log.info("Usuário não encontrado no database.");
			throw new UsernameNotFoundException("Usuário não encontrado no database.");
		}else {
			log.info("Usuário {} encontrado no database.",username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		funcionarioEntity.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getNome()));
		});
		return new User(funcionarioEntity.getUsuario(),funcionarioEntity.getSenha(),authorities);
	}
	
	@Override
	public FuncionarioEntity salvar(FuncionarioEntity funcionarioEntity) {
		log.info("Salvando o novo usuário {} no database", funcionarioEntity.getNome());
		funcionarioEntity.setSenha(encoder.encode(funcionarioEntity.getSenha()));
		return funcionarioRepository.save(funcionarioEntity);
	}
	
	@Override
	public RoleEntity saveRole(RoleEntity role) {
		log.info("Saving new role {} to the database", role.getNome());
		return roleRepository.save(role);
	}

	@Override
	public FuncionarioEntity getFuncionario(String usuario) {
		log.info("Localizando usuário {} ", usuario);
		return funcionarioRepository.findByUsuario(usuario);	
	}

	@Override
	public List<FuncionarioEntity> getFuncionariosEntities() {
		log.info("Localizando todos os usuários");
		return funcionarioRepository.findAll();
	}

	@Override
	public FuncionarioEntity findFuncionarioById(Long id) {
		// TODO Auto-generated method stub
		return (FuncionarioEntity) funcionarioRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado pelo id " +id+"." ));
	}
		
	@Override
	public void addRoleToFuncionario(String usuario, String roleName) {
		log.info("Adding role {} to user {}", roleName, usuario);
		FuncionarioEntity funcionarioEntity = funcionarioRepository.findByUsuario(usuario);
		RoleEntity roleEntity = roleRepository.findByNome(roleName);
		funcionarioEntity.getRoles().add(roleEntity);
		
	}
	
	@Override
	public void deleteFuncionario(Long id) {
		log.info("Deletando o usuário pelo id {} ", id);
		funcionarioRepository.deleteById(id);
	}

	@Override
	public FuncionarioEntity updateFuncionario(FuncionarioEntity funcionarioEntity) {
		log.info("Atualizando informações do usuário {} ", funcionarioEntity.getNome());
		return funcionarioRepository.save(funcionarioEntity);
	}



}
