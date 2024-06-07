package org.example;

import com.example.librarycatalog.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();

        CatalogoService catalogoService = new CatalogoService(em);
        PrestitoService prestitoService = new PrestitoService(em);

        em.getTransaction().begin();

        // Aggiungi un libro
        Libro libro = new Libro();
        libro.setIsbn("1234567890");
        libro.setTitolo("Il Signore degli Anelli");
        libro.setAnnoPubblicazione(1954);
        libro.setNumeroPagine(1216);
        libro.setAutore("J.R.R. Tolkien");
        libro.setGenere("Fantasy");
        catalogoService.aggiungiElemento(libro);

        // Aggiungi una rivista
        Rivista rivista = new Rivista();
        rivista.setIsbn("0987654321");
        rivista.setTitolo("National Geographic");
        rivista.setAnnoPubblicazione(2023);
        rivista.setNumeroPagine(120);
        rivista.setPeriodicita(Rivista.Periodicita.MENSILE);
        catalogoService.aggiungiElemento(rivista);

        // Aggiungi un utente
        Utente utente = new Utente();
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setDataNascita(LocalDate.of(1990, 1, 1));
        utente.setNumeroTessera("T12345");
        em.persist(utente);

        // Effettua un prestito
        Prestito prestito = new Prestito();
        prestito.setUtente(utente);
        prestito.setElementoPrestato(libro);
        prestito.setDataInizioPrestito(LocalDate.now());
        prestitoService.aggiungiPrestito(prestito);

        em.getTransaction().commit();

        // Ricerca per ISBN
        ElementoCatalogo elemento = catalogoService.ricercaPerIsbn("1234567890");
        System.out.println("Elemento trovato per ISBN 1234567890: " + elemento.getTitolo());

        // Ricerca per anno di pubblicazione
        List<ElementoCatalogo> elementiAnno = catalogoService.ricercaPerAnnoPubblicazione(1954);
        System.out.println("Elementi pubblicati nel 1954: " + elementiAnno.size());

        // Ricerca per autore
        List<Libro> libriAutore = catalogoService.ricercaPerAutore("J.R.R. Tolkien");
        System.out.println("Libri di J.R.R. Tolkien: " + libriAutore.size());

        // Ricerca per titolo
        List<ElementoCatalogo> elementiTitolo = catalogoService.ricercaPerTitolo("Signore");
        System.out.println("Elementi con 'Signore' nel titolo: " + elementiTitolo.size());

        // Ricerca prestiti attivi per utente
        List<Prestito> prestitiUtente = prestitoService.ricercaPrestitiPerTessera("T12345");
        System.out.println("Prestiti attivi per l'utente con tessera T12345: " + prestitiUtente.size());

        // Ricerca prestiti scaduti
        List<Prestito> prestitiScaduti = prestitoService.ricercaPrestitiScaduti();
        System.out.println("Prestiti scaduti e non restituiti: " + prestitiScaduti.size());

        em.close();
        emf.close();
    }
}
