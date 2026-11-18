package fr.diginamic.DAO;

import fr.diginamic.entity.Categorie;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategorieDAO {
    private final EntityManager entityManager;

    public CategorieDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Categorie findById(Integer id) {
        return id == null ? null : entityManager.find(Categorie.class, id);
    }

    public Map<String, Integer> ids() {
        Map<String, Integer> ids = new HashMap<>();
        List<Object[]> res = entityManager.createQuery("SELECT c.nom, c.id FROM Categorie c", Object[].class).getResultList();
        for (Object[] ligne : res) {
            ids.put((String) ligne[0], (Integer) ligne[1]);
        }
        return ids;
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