
package com.raizcampesina.controller;

import com.raizcampesina.model.Favorito;
import com.raizcampesina.model.Producto;
import com.raizcampesina.model.Usuario;
import com.raizcampesina.repository.FavoritoRepository;
import com.raizcampesina.service.FavoritoService;
import com.raizcampesina.service.ProductoService;
import com.raizcampesina.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Gestión del módulo de productos preferidos por los usuarios.
// Controla la persistencia y conmutación (Toggle) de la tabla intermedia de favoritos.
@Controller
public class FavoritoController
    {
        @Autowired
        private FavoritoService favoritoService;

        @Autowired
        private ProductoService productoService;

        @Autowired
        private UsuarioService usuarioService;

        @Autowired 
        private FavoritoRepository favoritoRepository;

        // Lista únicamente los productos favoritos vinculados al consumidor que tiene la sesión activa.
        @GetMapping("/favoritos")
        public String listarFavoritos(HttpSession session, Model model)
            {
                // Obtener el usuario que está logueado en la sesión actual
                Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

                // Validar por seguridad que sí haya una sesión activa
                if (usuarioLogueado == null)
                    {
                        return "redirect:/login"; 
                    }

                // Filtrar la lista trayendo ÚNICAMENTE los favoritos de ese usuario
                List<Favorito> favoritosDelUsuario = favoritoRepository.findByUsuario(usuarioLogueado);

                // Enviar a Thymeleaf solo los registros del usuario actual
                model.addAttribute("listaFavoritos", favoritosDelUsuario);

                return "favoritos";
            }

        // Guardar favorito (Formulario tradicional)
        @PostMapping("/guardarFavorito")
        public String guardarFavorito(@ModelAttribute Favorito favorito)
            {
                favoritoService.guardarFavorito(favorito);
                return "redirect:/favoritos";
            }

        // Eliminar favorito desde la pantalla de favoritos
        @GetMapping("/eliminarFavorito/{id}")
        public String eliminarFavorito(@PathVariable Integer id)
            {
                favoritoService.eliminarFavorito(id);
                return "redirect:/favoritos";
            }

        // Lógica avanzada (Toggle): Si el producto no es favorito, lo agrega; si ya existe, lo quita.
        // Evita duplicados innecesarios en la tabla relacional intermedia de la base de datos.
        @GetMapping("/productos/favorito/{id}/toggle")
        public String alternarFavoritoProducto(@PathVariable("id") Integer idProducto, HttpSession session)
        {
            // Verificar el usuario en sesión.
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null)
                {
                    // Si no hay sesión, lo mandamos al login para evitar NullPointerException.
                    return "redirect:/login"; 
                }

            // Buscar el producto seleccionado.
            Producto producto = productoService.buscarPorId(idProducto);

            if (producto != null)
                {
                    // Consultar la tabla intermedia 'favoritos' para ver si ya existía este vínculo
                    List<Favorito> todosLosFavoritos = favoritoService.listarFavoritos();
                    Favorito favoritoExistente = null;

                    if (todosLosFavoritos != null)
                        {
                            for (Favorito fav : todosLosFavoritos)
                                {
                                    if (fav.getProducto() != null && fav.getProducto().getIdProducto().equals(idProducto) &&
                                        fav.getUsuario() != null && fav.getUsuario().getIdUsuario().equals(usuarioLogueado.getIdUsuario()))
                                        {
                                            favoritoExistente = fav;
                                            break; // Encontrado
                                        }
                                }
                        }

                    // Lógica de Conmutación (Toggle)
                    if (favoritoExistente != null)
                        {
                            // SI YA EXISTE: El usuario quiere quitarlo de favoritos
                            favoritoService.eliminarFavorito(favoritoExistente.getIdFavorito());
                            System.out.println("❌ Eliminado de la tabla intermedia favoritos.");
                        }
                    else
                        {
                            // SI NO EXISTE: El usuario quiere agregarlo
                            Favorito nuevoFavorito = new Favorito();
                            nuevoFavorito.setProducto(producto);
                            nuevoFavorito.setUsuario(usuarioLogueado);

                            favoritoService.guardarFavorito(nuevoFavorito);
                            System.out.println("⭐ Insertado en la tabla intermedia favoritos.");
                        }
                }

            // Redirección segura de retorno al catálogo de origen
            return "redirect:/pedidos";
        }
    }

