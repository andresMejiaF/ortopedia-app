package ortopedia.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ortopedia.proyecto.model.Producto;

@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Integer> {



}
