package packClass;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RIVISTA")
public class Rivista extends ElementoCatalogo {
    public enum Periodicita {
        SETTIMANALE, MENSILE, SEMESTRALE
    }

    private Periodicita periodicita;

    // Getters and setters

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }
}
