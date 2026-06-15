
package com.API.service;

import com.API.DTO.CategoriaDTO;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class ServiceCategoria {
 
    @Autowired
    private RestTemplate restTemplate;
    private final String API_Inventario = "https://stock-0-0-1.onrender.com";
    
    public List<CategoriaDTO> listarCategorias() {
        String url = API_Inventario + "/category/all";
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            JsonNode root = response.getBody();

          
            if (root.isArray()) {
                ObjectMapper mapper = new ObjectMapper();
                CategoriaDTO[] categorias = mapper.treeToValue(root, CategoriaDTO[].class);
                return Arrays.asList(categorias);
            }

           
            String arrayProperty = "content"; 
            if (root.has(arrayProperty) && root.get(arrayProperty).isArray()) {
                ObjectMapper mapper = new ObjectMapper();
                CategoriaDTO[] categorias = mapper.treeToValue(
                        root.get(arrayProperty),
                        CategoriaDTO[].class
                );
                return Arrays.asList(categorias);
            }

            System.err.println("No se pudo encontrar un array en la respuesta");
            return new ArrayList<>();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener categorías", e);
        }
    }
}
    
