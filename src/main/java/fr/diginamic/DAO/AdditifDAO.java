package fr.diginamic.DAO;

import fr.diginamic.entity.Additif;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdditifDAO {
    private final EntityManager entityManager;

    public AdditifDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Additif findByNom(String nom) {
        List<Additif> res = entityManager.createQuery("SELECT a FROM Additif a WHERE a.nom = :nom", Additif.class)
                .setParameter("nom", nom)
                .getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public void insererTous(Set<String> noms) {
        Set<String> existants = new HashSet<>(
                entityManager.createQuery("SELECT a.nom FROM Additif a", String.class).getResultList());

        for (String nom : noms) {
            if (!existants.contains(nom)) {
                Additif a = new Additif();
                a.setNom(nom);
                entityManager.persist(a);
            }
        }
    }
}