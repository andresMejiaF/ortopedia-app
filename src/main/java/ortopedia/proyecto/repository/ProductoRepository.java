package ortopedia.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ortopedia.proyecto.model.Producto;

import java.util.List;

@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Integer> {


    @Query("select p from Producto p  where p.nombre like concat('%', :cadena,'%')")
    List<Producto> buscarProductos(String cadena);
}
