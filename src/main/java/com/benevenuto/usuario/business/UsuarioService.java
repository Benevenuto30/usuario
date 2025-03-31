package com.benevenuto.usuario.business;

import com.benevenuto.usuario.business.converter.UsuarioConverter;
import com.benevenuto.usuario.business.dto.UsuarioDTO;
import com.benevenuto.usuario.infrastructure.entity.Usuario;
import com.benevenuto.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));
    }
}
