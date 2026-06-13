
package com.API.Controller;

import com.API.entity.Usuario;
import com.API.service.serviceUsuario;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class controlUsuario {
    
    @Autowired
    private serviceUsuario serviceusuario;
    
    @GetMapping
    public List<Usuario> Listar(){
        return serviceusuario.listar();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
    return serviceusuario.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> obtenerPorUsername(@PathVariable String username) {
        return serviceusuario.buscarPorUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Usuario Insertar(@RequestBody Usuario user){
        return serviceusuario.insertar(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        Optional<Usuario> usuarioOpt = serviceusuario.buscarPorUsername(username);

        Map<String, Object> response = new HashMap<>();

        if (usuarioOpt.isPresent()) {
            Usuario user = usuarioOpt.get();

            if (!user.getPasswordHash().equals(password)) {
                response.put("success", false);
                response.put("message", "Contraseña incorrecta");
                return ResponseEntity.status(401).body(response);
            }

            response.put("success", true);
            response.put("message", "Login exitoso");
            response.put("usuarioId", user.getId());
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("rol", user.getRol());

            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @PutMapping
    public Usuario Actualizr(@RequestBody Usuario user){
        return serviceusuario.actualizar(user);
    }
    
    @DeleteMapping
    public void Eliminar(@RequestBody Usuario user){
        serviceusuario.eliminar(user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return serviceusuario.buscarPorId(id)
                .map(user -> {
                    serviceusuario.eliminar(user);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
