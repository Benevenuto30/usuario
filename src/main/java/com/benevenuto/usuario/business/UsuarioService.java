package com.benevenuto.usuario.business;

import com.benevenuto.usuario.business.converter.UsuarioConverter;
import com.benevenuto.usuario.business.dto.UsuarioDTO;
import com.benevenuto.usuario.infrastructure.entity.Usuario;
import com.benevenuto.usuario.infrastructure.exceptions.ConflictException;
import com.benevenuto.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.benevenuto.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConcurrentModificationException("Email ja cadastrado " +  email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));
    }

    public Usuario buscarUsuarioPorNome(String nome){
        return usuarioRepository.findByNome(nome).orElseThrow(()->new ResourceNotFoundException("Nome não encontrado" + nome));
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}
