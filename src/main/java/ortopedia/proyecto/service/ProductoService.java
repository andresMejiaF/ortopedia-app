package ortopedia.proyecto.service;

import ortopedia.proyecto.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    public Producto save(Producto producto);
    public Optional<Producto> get(Integer id);
    public void update(Producto producto);
    public void delete(Integer id);

    public List<Producto> findAll();

    public List<Producto> buscarProducto(String cadena);
}
