package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private LocalDateTime fecha;
    private Long presupuesto;
    private Long aforo;

    @OneToOne
    private EstadioEntity estadio;

}
