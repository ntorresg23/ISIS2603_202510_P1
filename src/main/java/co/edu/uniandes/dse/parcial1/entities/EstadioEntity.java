package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Long capacidad;
    private Long precioAlquiler;

    @OneToMany(mappedBy = "concierto")
    private ArrayList<ConciertoEntity> conciertos = new ArrayList<>();

}
