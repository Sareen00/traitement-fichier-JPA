package fr.diginamic.DAO;

import fr.diginamic.entity.Marque;
import fr.diginamic.entity.Produit;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarqueDAO {
    private final EntityManager entityManager;

    public MarqueDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Marque findByNom(String nom) {
        List<Marque> res = entityManager.createQuery("SELECT m FROM Marque m WHERE m.nom = :nom", Marque.class)
                .setParameter("nom", nom)
                .getResultList();
        return res.isEmpty() ? null : res.getFirst();
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
