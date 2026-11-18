package fr.diginamic;

import fr.diginamic.DAO.*;
import fr.diginamic.csv.LecteurCsv;
import fr.diginamic.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntegrationOpenFoodFacts {

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("traitement-fichier-jpa");
        EntityManager em = emf.createEntityManager();

        CategorieDAO categorieDao = new CategorieDAO(em);
        MarqueDAO marqueDao = new MarqueDAO(em);
        ProduitDAO produitDao = new ProduitDAO(em);
        IngredientDAO ingredientDao = new IngredientDAO(em);
        AllergeneDAO allergeneDao = new AllergeneDAO(em);
        AdditifDAO additifDao = new AdditifDAO(em);


        Set<String> nomsCategories = new HashSet<>();
        Set<String> nomsMarques = new HashSet<>();
        Set<String> nomsIngredients = new HashSet<>();
        Set<String> nomsAllergenes = new HashSet<>();
        Set<String> nomsAdditif = new HashSet<>();


        List<String[]> lignes = LecteurCsv.lire("src/main/resources/open-food-facts.csv");
        System.out.println(lignes.size() + " produits lus");


        for (String[] c : lignes) {
            nomsCategories.add(LecteurCsv.normaliser(c[0]));
            nomsMarques.add(LecteurCsv.marque(c[1]));
            nomsIngredients.addAll(LecteurCsv.ingredients(c[4]));
            nomsAllergenes.addAll(LecteurCsv.allergenes(c[28]));
            nomsAdditif.addAll(LecteurCsv.additifs(c[29]));
        }

        em.getTransaction().begin();
        categorieDao.insererTous(nomsCategories);
        marqueDao.insererTous(nomsMarques);
        ingredientDao.insererTous(nomsIngredients);
        allergeneDao.insererTous(nomsAllergenes);
        additifDao.insererTous(nomsAdditif);
        em.getTransaction().commit();

        em.getTransaction().begin();

        Set<String> produitsExistants = produitDao.clesExistantes();
        int compteur = 0;

        for (String[] c : lignes) {
            String nomProduit = c[2].trim();
            String nomMarque  = LecteurCsv.marque(c[1]);
            String cle        = nomProduit + "|" + nomMarque;

            if (produitsExistants.contains(cle)) {
                continue;
            }
            produitsExistants.add(cle);

            Produit produit = new Produit();
            produit.setNom(nomProduit);
            produit.setScore(c[3].trim().toLowerCase());
            produit.setEnergie(LecteurCsv.toDouble(c[5]));
            produit.setGraisse(LecteurCsv.toDouble(c[6]));
            produit.setHuilePalme(c[27].trim().equals("1"));
            produit.setCategorie(categorieDao.findByNom(LecteurCsv.normaliser(c[0])));
            produit.setMarque(marqueDao.findByNom(nomMarque));

            for (String nom : LecteurCsv.ingredients(c[4])) {
                Ingredient i = ingredientDao.findByNom(nom);
                if (i != null) produit.getIngredients().add(i);
            }
            for (String nom : LecteurCsv.allergenes(c[28])) {
                Allergene a = allergeneDao.findByNom(nom);
                if (a != null) produit.getAllergenes().add(a);
            }
            for (String nom : LecteurCsv.additifs(c[29])) {
                Additif a = additifDao.findByNom(nom);
                if (a != null) produit.getAdditifs().add(a);
            }

            produitDao.save(produit);

            if (++compteur % 500 == 0) {
                em.getTransaction().commit();
                em.clear();
                em.getTransaction().begin();
                System.out.println(compteur + " produits insérés");
            }
        }

        em.getTransaction().commit();
        System.out.println(compteur + " produits insérés au total");


        em.close();
        emf.close();
    }
}