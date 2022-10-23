package ortopedia.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ortopedia.proyecto.model.DetalleOrden;
import ortopedia.proyecto.repository.DetalleOrdenRepository;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {

        return detalleOrdenRepository.save(detalleOrden);
    }
}
