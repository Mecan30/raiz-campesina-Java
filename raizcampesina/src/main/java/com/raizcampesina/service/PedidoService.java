
package com.raizcampesina.service;

import com.raizcampesina.model.Pedido;
import com.raizcampesina.model.Producto;
import com.raizcampesina.model.Notificacion;
import com.raizcampesina.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private NotificacionService notificacionService; // Inyectamos el servicio de alertas

    // Guardar pedido (Automatizado al crear la compra)
    public void guardarPedido(Pedido pedido) {
        Producto producto = pedido.getProducto();

        Double total = producto.getPrecio() * pedido.getCantidad();

        pedido.setTotal(total);
        pedido.setEstado("Pendiente");
        pedido.setFechaPedido(LocalDateTime.now());

        // 1. Guardamos el pedido en la BD
        pedidoRepository.save(pedido);

        // 2. 🔥 ALERTA AUTOMÁTICA: Notificar al Campesino/Proveedor del producto
        try {
            if (producto != null && producto.getUsuario() != null) {
                Notificacion notiProductor = new Notificacion();
                notiProductor.setTitulo("🌾 ¡Nueva orden recibida!");
                notiProductor.setDescripcion("Te han solicitado " + pedido.getCantidad() + " unidad(es) de '" + producto.getNombre() + "'.");
                notiProductor.setUsuario(producto.getUsuario()); // ID del Proveedor asociado al producto
                notiProductor.setLeida(false);
                notiProductor.setVisibleUsuario(true);
                notiProductor.setFechaNotificacion(LocalDateTime.now());
                
                notificacionService.guardarNotificacion(notiProductor);
            }
        } catch (Exception e) {
            System.err.println("Error generando notificación automática de compra: " + e.getMessage());
        }
    }

    // Listar pedidos
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // Buscar por ID
    public Pedido buscarPorId(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    // Eliminar pedido
    public void eliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }
    
    public List<Pedido> buscarPorConsumidor(Integer idUsuario) {
        return pedidoRepository.buscarPedidosPorConsumidor(idUsuario);
    }

    public List<Pedido> buscarPorTransportador(Integer idUsuario) {
        return pedidoRepository.buscarPedidosPorTransportador(idUsuario);
    }

    public List<Pedido> buscarPorProveedor(Integer idUsuario) {
        return pedidoRepository.buscarPedidosPorProveedor(idUsuario);
    }
    
    // Actualizar Pedido (Módulo Automatizado de Alertas en Raíz Campesina)
    public void actualizarPedido(Pedido pedido)
        {
            // 1. Guardamos los cambios reales en MySQL (Estado y Transportador incluidos)
            pedidoRepository.save(pedido);

            // 2. 🔥 ALERTA PARA EL CONSUMIDOR (El cliente que realizó la compra)
            try {
                if (pedido.getUsuario() != null) { // 'usuario' representa al consumidor en Pedido.java
                    Notificacion notiConsumidor = new Notificacion();
                    notiConsumidor.setTitulo("📦 Actualización de tu Pedido");
                    notiConsumidor.setDescripcion("El pedido #" + pedido.getIdPedido() + " conteniendo '" + pedido.getProducto().getNombre() + "' cambió al estado: " + pedido.getEstado() + ".");
                    notiConsumidor.setUsuario(pedido.getUsuario()); // Destinatario
                    notiConsumidor.setLeida(false);
                    notiConsumidor.setVisibleUsuario(true);
                    notiConsumidor.setFechaNotificacion(LocalDateTime.now());

                    notificacionService.guardarNotificacion(notiConsumidor);
                }
            } catch (Exception e) {
                System.err.println("Error en alerta al consumidor: " + e.getMessage());
            }

            // 3. 🔥 ALERTA PARA EL TRANSPORTADOR (Se ejecuta cuando se le vincula al flete)
            try {
                if (pedido.getTransportador() != null) { // Evaluamos la relación mapeada en el PDF
                    Notificacion notiTransportador = new Notificacion();
                    notiTransportador.setTitulo("🚛 ¡Nuevo Flete Asignado!");
                    notiTransportador.setDescripcion("Se te ha asignado el despacho del pedido #" + pedido.getIdPedido() + " ('" + pedido.getProducto().getNombre() + "'). Revisa tu panel de rutas.");
                    notiTransportador.setUsuario(pedido.getTransportador()); // 🎯 Destinatario: El transportador asignado
                    notiTransportador.setLeida(false);
                    notiTransportador.setVisibleUsuario(true);
                    notiTransportador.setFechaNotificacion(LocalDateTime.now());

                    notificacionService.guardarNotificacion(notiTransportador);
                }
            } catch (Exception e) {
                System.err.println("Error en alerta al transportador: " + e.getMessage());
            }
        }
}