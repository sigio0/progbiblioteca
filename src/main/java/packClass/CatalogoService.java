package packClass;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CatalogoService {
    private EntityManager entityManager;

    public CatalogoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void aggiungiElemento(ElementoCatalogo elemento) {
        entityManager.persist(elemento);
    }

    public void rimuoviElemento(String isbn) {
        ElementoCatalogo elemento = entityManager.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.isbn = :isbn", ElementoCatalogo.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
        if (elemento != null) {
            entityManager.remove(elemento);
        }
    }

    public ElementoCatalogo ricercaPerIsbn(String isbn) {
        return entityManager.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.isbn = :isbn", ElementoCatalogo.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    public List<ElementoCatalogo> ricercaPerAnnoPubblicazione(int anno) {
        return entityManager.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno", ElementoCatalogo.class)
                .setParameter("anno", anno)
                .getResultList();
    }

    public List<Libro> ricercaPerAutore(String autore) {
        return entityManager.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", autore)
                .getResultList();
    }

    public List<ElementoCatalogo> ricercaPerTitolo(String titolo) {
        return entityManager.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.titolo LIKE :titolo", ElementoCatalogo.class)
                .setParameter("titolo", "%" + titolo + "%")
                .getResultList();
    }
}
