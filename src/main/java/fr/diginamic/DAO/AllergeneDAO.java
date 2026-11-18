package fr.diginamic.DAO;

import fr.diginamic.entity.Allergene;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllergeneDAO {
    private final EntityManager entityManager;

    public AllergeneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Allergene findByNom(String nom) {
        List<Allergene> res = entityManager.createQuery("SELECT a FROM Allergene a WHERE a.nom = :nom", Allergene.class)
                .setParameter("nom", nom)
                .getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public void insererTous(Set<String> noms) {
        Set<String> existants = new HashSet<>(
                entityManager.createQuery("SELECT a.nom FROM Allergene a", String.class).getResultList());

        for (String nom : noms) {
            if (!existants.contains(nom)) {
                Allergene a = new Allergene();
                a.setNom(nom);
                entityManager.persist(a);
            }
        }
    }
}