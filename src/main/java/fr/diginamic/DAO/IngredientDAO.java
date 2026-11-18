package fr.diginamic.DAO;

import fr.diginamic.entity.Ingredient;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IngredientDAO {
    private final EntityManager entityManager;

    public IngredientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Ingredient findById(Integer id) {
        return id == null ? null : entityManager.find(Ingredient.class, id);
    }

    public Map<String, Integer> ids() {
        Map<String, Integer> ids = new HashMap<>();
        List<Object[]> res = entityManager.createQuery("SELECT i.nom, i.id FROM Ingredient i", Object[].class).getResultList();
        for (Object[] ligne : res) {
            ids.put((String) ligne[0], (Integer)   ligne[1]);
        }
        return ids;
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