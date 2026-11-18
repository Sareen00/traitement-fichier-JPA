package fr.diginamic.DAO;

import fr.diginamic.entity.Marque;
import fr.diginamic.entity.Produit;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarqueDAO {
    private final EntityManager entityManager;

    public MarqueDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Marque findById(Integer id) {
        return id == null ? null : entityManager.find(Marque.class, id);
    }

    public Map<String, Integer> ids() {
        Map<String, Integer> ids = new HashMap<>();
        List<Object[]> res = entityManager.createQuery("SELECT m.nom, m.id FROM Marque m", Object[].class).getResultList();
        for (Object[] ligne : res) {
            ids.put((String) ligne[0], (Integer) ligne[1]);
        }
        return ids;
    }

    public void insererTous(Set<String> noms) {
        Set<String> existants = new HashSet<>(entityManager.createQuery("SELECT m.nom FROM Marque m", String.class).getResultList());

        for (String nom : noms) {
            if (!existants.contains(nom)) {
                Marque m = new Marque();
                m.setNom(nom);
                entityManager.persist(m);
            }
        }
    }

}
