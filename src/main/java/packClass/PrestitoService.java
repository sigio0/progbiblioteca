package packClass;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class PrestitoService {
    private EntityManager entityManager;

    public PrestitoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void aggiungiPrestito(Prestito prestito) {
        entityManager.persist(prestito);
    }

    public List<Prestito> ricercaPrestitiPerTessera(String numeroTessera) {
        return entityManager.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .setParameter("numeroTessera", numeroTessera)
                .getResultList();
    }

    public List<Prestito> ricercaPrestitiScaduti() {
        return entityManager.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < CURRENT_DATE AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .getResultList();
    }
}
