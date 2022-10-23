package proyecto.service;

import ortopedia.proyecto.model.Orden;
import ortopedia.proyecto.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface OrdenService {

    Orden save (Orden orden);
    List<Orden> findAll();
    String generarNumeroOrden();

    List<Orden> findByUsuario (Usuario usuario);

    Optional<Orden> finById(Integer id);

}
