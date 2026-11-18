package fr.diginamic.DAO;

import fr.diginamic.entity.Produit;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProduitDAO {
    private final EntityManager entityManager;

    public ProduitDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Set<String> clesExistantes() {
        Set<String> cles = new HashSet<>();
        List<Object[]> resultats = entityManager.createQuery(
                "SELECT p.nom, m.nom FROM Produit p LEFT JOIN p.marque m", Object[].class).getResultList();
        for (Object[] ligne : resultats) {
            cles.add(ligne[0] + "|" + ligne[1]);
        }
        return cles;
    }

    public void save(Produit produit) {
        entityManager.persist(produit);
    }
}