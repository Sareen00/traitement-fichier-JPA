package fr.diginamic.DAO;

import fr.diginamic.entity.Ingredient;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IngredientDAO {
    private final EntityManager entityManager;

    public IngredientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Ingredient findByNom(String nom) {
        List<Ingredient> res = entityManager.createQuery("SELECT i FROM Ingredient i WHERE i.nom = :nom", Ingredient.class)
                .setParameter("nom", nom)
                .getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public void insererTous(Set<String> noms) {
        Set<String> existants = new HashSet<>(
                entityManager.createQuery("SELECT i.nom FROM Ingredient i", String.class).getResultList());

        for (String nom : noms) {
            if (!existants.contains(nom)) {
                Ingredient i = new Ingredient();
                i.setNom(nom);
                entityManager.persist(i);
            }
        }
    }
}