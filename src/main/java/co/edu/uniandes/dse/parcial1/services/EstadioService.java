package co.edu.uniandes.dse.parcial1.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

    @Autowired
    EstadioRepository estadioRepository;

    @Transactional
    public EstadioEntity crearEstadio (EstadioEntity estadio) throws IllegalOperationException {

        log.info("Inicia proceso de creación del estadio");

        if(estadio.getNombre().length() <3){
            throw new IllegalOperationException("El nombre es muy corto");
        }

        if(estadio.getCapacidad() <= 1000){

            throw new IllegalOperationException("La capacidad debe de ser mayor a 1000 personas");

        }

        if(estadio.getPrecioAlquiler() <= 100000){

            throw new IllegalOperationException("El precio de alquiler debe de ser mayor a 100000 dolares");

        }

        return estadioRepository.save(estadio);

    }

    @Transactional
    public List<EstadioEntity> getEstadios (){

        log.info("Empieza la busqueda de todos los estadios");
        return estadioRepository.findAll();

    }

    @Transactional
    public EstadioEntity getEstadio(Long estadioId) throws EntityNotFoundException {

        log.info("Empieza el proceso de encontrar el estadio por nombre");

        if(estadioRepository.findById(estadioId).isEmpty()){

            throw new EntityNotFoundException("El Id no es valido");

        }
        
        return estadioRepository.findById(estadioId).get();

    }

    @Transactional
    public EstadioEntity updateEstadio(Long estadioid, EstadioEntity estadio) throws IllegalOperationException, EntityNotFoundException {

        log.info("Empieza el proceso de actualizar el estadio");

        Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioid);

        if(estadioEntity.isEmpty()){

            throw new EntityNotFoundException("El estadio no existe");

        }

        estadio.setId(estadioid);
        return estadioRepository.save(estadio);



    }

    @Transactional
    public void deleteEstadio(Long estadioId) throws EntityNotFoundException, IllegalOperationException {

        log.info("Empieza el proceso de borrar el estadio");

        Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioId);
        if (estadioEntity.isEmpty()){

            throw new EntityNotFoundException("El Id no es válido");

        }

        List<ConciertoEntity> conciertos = estadioEntity.get().getConciertos();
        if (!conciertos.isEmpty()){

            throw new IllegalOperationException("No se puede borrar el estadio ya que tiene conciertos asociados");

        }

        estadioRepository.deleteById(estadioId);

    }

}
