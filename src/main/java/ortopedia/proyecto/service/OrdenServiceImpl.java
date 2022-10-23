package ortopedia.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ortopedia.proyecto.model.Orden;
import ortopedia.proyecto.model.Usuario;
import ortopedia.proyecto.repository.OrdenRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    public String generarNumeroOrden(){
        int numero=0;
        String numeroConcatenado="";

        List<Orden> ordenes = ordenRepository.findAll();
        System.out.println("ordenes.toString() = " + ordenes.toString());
        List<Integer> numeros = new ArrayList<Integer>();

        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

        if(ordenes.isEmpty()){
            numero=1;
        }else{
            numero=numeros.stream().max(Integer::compare).get(); // Mayor numero de la lista
            numero++;
        }
        if(numero<10){
            numeroConcatenado="000000000" + String.valueOf(numero);
        }else if (numero<100){
            numeroConcatenado="00000000" + String.valueOf(numero);
        } else if (numero<1000) {
            numeroConcatenado="0000000" + String.valueOf(numero);
        } else if (numero<10000) {
            numeroConcatenado="000000" + String.valueOf(numero);
        }
        return numeroConcatenado;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Orden> finById(Integer id) {

        return ordenRepository.findById(id);
    }
}
