package com.gft.crime.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gft.crime.api.entities.Usuario;
import com.gft.crime.api.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService  {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario buscarUsuarioPorNomeUsuario(String nomeUsuario) {
		Optional<Usuario> optional = usuarioRepository.findByNomeUsuario(nomeUsuario);
		
		if(optional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		return optional.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return buscarUsuarioPorNomeUsuario(username);
	}

	public Usuario buscarUsuarioPorId(Long idUsuario) {
		Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
		
		if(optional.isEmpty()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		return optional.get();
	}
	
	public Usuario salvarUsuario(Usuario usuario) {
		
		return usuarioRepository.save(usuario);
	}
}
