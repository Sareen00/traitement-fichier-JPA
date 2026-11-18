package fr.diginamic.DAO;

import fr.diginamic.entity.Categorie;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategorieDAO {
    private final EntityManager entityManager;

    public CategorieDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Categorie findByNom(String nom) {
        List<Categorie> res = entityManager.createQuery("SELECT c FROM Categorie c WHERE c.nom = :nom", Categorie.class)
                .setParameter("nom", nom)
                .getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public void insererTous(Set<String> noms) {
        Set<String> existants = new HashSet<>(entityManager.createQuery("SELECT c.nom FROM Categorie c", String.class).getResultList());

        for (String nom : noms) {
            if (!existants.contains(nom)) {
                Categorie c = new Categorie();
                c.setNom(nom);
                entityManager.persist(c);
            }}
    }
}