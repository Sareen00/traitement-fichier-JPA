package fr.diginamic.DAO;

import fr.diginamic.entity.Allergene;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllergeneDAO {
    private final EntityManager entityManager;

    public AllergeneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Allergene findById(Integer id) {
        return id == null ? null : entityManager.find(Allergene.class, id);
    }

    public Map<String, Integer> ids() {
        Map<String, Integer> ids = new HashMap<>();
        List<Object[]> res = entityManager.createQuery("SELECT a.nom, a.id FROM Allergene a", Object[].class).getResultList();
        for (Object[] ligne : res) {
            ids.put((String) ligne[0], (Integer) ligne[1]);
        }
        return ids;
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