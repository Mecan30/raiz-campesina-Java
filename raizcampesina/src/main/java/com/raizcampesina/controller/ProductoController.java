
package com.raizcampesina.controller;

import com.raizcampesina.model.Producto;
import com.raizcampesina.model.Usuario;
import com.raizcampesina.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;
import java.util.List;

// Controlador administrativo de inventario y oferta de cosechas.
// Gobierna el CRUD de productos y la carga física de recursos multimedia en el servidor.
@Controller
public class ProductoController
    {
        @Autowired
        private ProductoService productoService;

        // Mostrar formulario
        @GetMapping("/productos")
        public String mostrarProductos(Model model, HttpSession session) {

            // Validar u obtener el usuario productor desde la sesión HTTP
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

            // Seguridad: Si la sesión expiró o no está logueado, redirigir al login.
            if (usuarioLogueado == null)
                {
                    return "redirect:/login"; 
                }

            // Preparar el objeto vacío para el formulario de registro/edición.
            if (!model.containsAttribute("producto")) {
                Usuario proveedorVacio = new Usuario();
                proveedorVacio.setIdUsuario(usuarioLogueado.getIdUsuario()); // Pre-asignamos el creador

                com.raizcampesina.model.Producto nuevoProducto = new com.raizcampesina.model.Producto();
                nuevoProducto.setUsuario(proveedorVacio); 
                model.addAttribute("producto", nuevoProducto);
            }

            // Traer todos los productos y FILTRAR para dejar solo los de este proveedor.
            List<Producto> todosLosProductos = productoService.listarProductos();
            List<Producto> productosDelProveedor = java.util.Collections.emptyList();

            if (todosLosProductos != null) {
                productosDelProveedor = todosLosProductos.stream()
                    .filter(p -> p.getUsuario() != null && 
                             p.getUsuario().getIdUsuario().equals(usuarioLogueado.getIdUsuario()))
                    .toList();
            }

            // Enviar al modelo únicamente los productos del productor actual
            model.addAttribute("listaProductos", productosDelProveedor);

            return "productos";
        }

        // Guardar producto
        @PostMapping("/guardarProducto")
        public String guardarProducto(
                @ModelAttribute("producto") Producto producto,
                @RequestParam("imagenArchivo") MultipartFile archivo,
                jakarta.servlet.http.HttpSession session)
            {
                if (!archivo.isEmpty())
                    {
                        try
                            {
                                // Ruta interna del proyecto donde se guardarán físicamente las fotos.
                                String rutaDirectorio = "src/main/resources/static/images/productos";

                                Path directorioPath = Paths.get(rutaDirectorio);
                                if (!Files.exists(directorioPath))
                                    {
                                        Files.createDirectories(directorioPath);
                                    }

                                // Renombramos el archivo con un código único para que no se sobreescriban.
                                String nombreUnicoArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();

                                // Guardamos el archivo en el disco.
                                Path rutaCompleta = Paths.get(rutaDirectorio + "/" + nombreUnicoArchivo);
                                Files.write(rutaCompleta, archivo.getBytes());

                                // Guardamos el nombre en el objeto producto para que vaya a la BD.
                                producto.setImagen(nombreUnicoArchivo);

                            }
                        catch (Exception e)
                            {
                                System.out.println("Error al subir la imagen: " + e.getMessage());
                            }
                    }
                else
                    {
                        // 🔥 LÓGICA CORREGIDA: Entra aquí si NO se subió un archivo nuevo en el formulario
                        if (producto.getIdProducto() == null)
                            {
                                // Si es un producto NUEVO y no subieron foto, usa la de respaldo.
                                producto.setImagen("default-producto.png");
                            }
                        else
                            {
                                // Si es EDICIÓN, recuperamos el producto actual de la BD para mantener la foto que ya tenía
                                Producto productoExistente = productoService.buscarPorId(producto.getIdProducto());
                                if (productoExistente != null)
                                    {
                                        producto.setImagen(productoExistente.getImagen());
                                    }
                            }
                    }

                // Asignar proveedor automáticamente desde sesión.
                Usuario proveedor =
                        (Usuario) session.getAttribute("usuarioLogueado");

                producto.setUsuario(proveedor);
                
                // Guarda en la base de datos a través de tu servicio habitual.
                productoService.guardarProducto(producto); 

                return "redirect:/productos";
            }
        
        //Editar producto
        @GetMapping("/editarProducto/{id}")
        public String editarProducto(
                @PathVariable Integer id,
                Model model)
            {
                Producto producto = productoService.buscarPorId(id);

                model.addAttribute("producto", producto);
                model.addAttribute("listaProductos",
                        productoService.listarProductos());

                return "productos";
            }

        // Eliminar producto
        @GetMapping("/eliminarProducto/{id}")
        public String eliminarProducto(
                @PathVariable Integer id)
            {
                productoService.eliminarProducto(id);

                return "redirect:/productos";
            }
    }

