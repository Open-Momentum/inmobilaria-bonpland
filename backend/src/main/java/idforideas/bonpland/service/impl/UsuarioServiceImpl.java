/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package idforideas.bonpland.service.impl;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.CorreoExistenteException;
import idforideas.bonpland.exception.RolNoEncontradoException;
import idforideas.bonpland.repository.RolRepository;
import idforideas.bonpland.repository.UsuarioRepository;
import idforideas.bonpland.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 *
 * @author Figueroa Mauro
 */
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public Usuario guardarUsuario(UsuarioDTO dto) {
        validarCorreo(dto.getCorreo());
        Rol rolEncontrado = validarRol();

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setClave(dto.getClave());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(rolEncontrado);
        return usuarioRepository.save(usuario);
    }

    private Rol validarRol() {
        return rolRepository.findByNombre("USUARIO")
                       .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado"));
    }


    private  void validarCorreo(String correo) {
        Optional<Usuario> correoEncontrado = usuarioRepository.findByCorreo(correo);
        if (correoEncontrado.isPresent()) {
            throw new CorreoExistenteException("El correo ya existe");
        }
    }
}
