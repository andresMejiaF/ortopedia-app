package com.example.solucionesmedicasyortopedia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ortopedia.proyecto.Application;
import ortopedia.proyecto.model.DetalleOrden;
import ortopedia.proyecto.model.Orden;
import ortopedia.proyecto.model.Producto;
import ortopedia.proyecto.model.Usuario;
import ortopedia.proyecto.service.DetalleOrdenService;
import ortopedia.proyecto.service.IUsuarioService;
import ortopedia.proyecto.service.OrdenService;
import ortopedia.proyecto.service.ProductoService;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest(classes = Application.class)
class SolucionesMedicasYOrtopediaApplicationTests {

@Autowired
private IUsuarioService usuarioService;

@Autowired
private ProductoService productoService;

@Autowired
private OrdenService ordenService;

@Autowired
private DetalleOrdenService detalleOrdenService;

    @Test
    public void registrarUsuarioTest(){

        Usuario usuario = new Usuario(1, "Julian", "xXJulian100Xx", "julian100@gmail.com", "Urbanizacion El Poblado Mz 5 Casa 20", "7435625", "ADMIN", "Julian100Xx");
        Usuario usuarioGuardado = usuarioService.save(usuario);

        Assertions.assertNotNull(usuarioGuardado);

    }

    @Test
    public void registrarProductoTest(){

        Usuario usuario = new Usuario(1, "Julian", "xXJulian100Xx", "julian100@gmail.com", "Urbanizacion El Poblado Mz 5 Casa 20", "7435625", "ADMIN", "Julian100Xx");
        Producto producto = new Producto(1, usuario, "Crema Natural", "Crema natural para el cuidado de la piel con olor a vainilla", "/ruta/imagen-1", 15.000, 10);

        Producto productoGuardado = productoService.save(producto);

        Assertions.assertNotNull(productoGuardado);

    }

    @Test
    public void actualizarProductoTest(){

        Usuario usuario = new Usuario(1, "Julian", "xXJulian100Xx", "julian100@gmail.com", "Urbanizacion El Poblado Mz 5 Casa 20", "7435625", "ADMIN", "Julian100Xx");
        Producto producto = new Producto(1, usuario, "Crema Natural", "Crema natural para el cuidado de la piel con olor a vainilla", "/ruta/imagen-1", 15.000, 10);

        Producto productoGuardado = productoService.save(producto);
        productoGuardado.setNombre("Crema no natural");
        productoService.save(productoGuardado);

        Assertions.assertEquals("Crema no natural", productoGuardado.getNombre());

    }

    @Test
    public void crearOrdenTest(){

        Usuario usuario = new Usuario(1, "Julian", "xXJulian100Xx", "julian100@gmail.com", "Urbanizacion El Poblado Mz 5 Casa 20", "7435625", "ADMIN", "Julian100Xx");
        Orden orden = new Orden(1, "1", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), usuario, 30.000);

        Orden ordenGuardada = ordenService.save(orden);

        Assertions.assertNotNull(ordenGuardada);

    }

    @Test
    public void crearDetalleOrdenTest(){

        Usuario usuario = new Usuario(1, "Julian", "xXJulian100Xx", "julian100@gmail.com", "Urbanizacion El Poblado Mz 5 Casa 20", "7435625", "ADMIN", "Julian100Xx");
        Producto producto = new Producto(1, usuario, "Crema Natural", "Crema natural para el cuidado de la piel con olor a vainilla", "/ruta/imagen-1", 15.000, 10);
        Orden orden = new Orden(1, "1", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), usuario, 30.000);

        DetalleOrden detalleOrden = new DetalleOrden(1, "Orden#1", 2, 15.000, 30.000, orden, producto);

        DetalleOrden detalleOrdenGuardada = detalleOrdenService.save(detalleOrden);

        Assertions.assertNotNull(detalleOrdenGuardada);

    }



}
