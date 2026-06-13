
package com.API.service;

import com.API.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.API.repo.repoUsuario;
import java.util.List;
import java.util.Optional;

@Service
public class serviceUsuario {
    
    @Autowired
    private repoUsuario repousuario;
    
    public Usuario insertar (Usuario user){
        return repousuario.save(user);
    }
    
    public Usuario actualizar (Usuario user){
        return repousuario.save(user);
    }
    
    public List<Usuario> listar (){
        return repousuario.findAll();
    }
    
    public void  eliminar (Usuario user){
        repousuario.delete(user);
    }
    
    public Optional<Usuario> buscarPorId(Long id) {
    return repousuario.findById(id);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
    return repousuario.findByUsername(username);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
    return repousuario.findByEmail(email);
    }

    public boolean existePorUsername(String username) {
    return repousuario.existsByUsername(username);
    }

    public boolean existePorEmail(String email) {
    return repousuario.existsByEmail(email);
    }
}