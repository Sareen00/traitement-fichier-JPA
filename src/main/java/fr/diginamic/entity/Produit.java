package fr.diginamic.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom", length = 255)
    private String nom;

    @Column(name = "score", length = 50)
    private String score;

    private Double energie;
    private Double graisse;
    private boolean huilePalme;



    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToMany
    @JoinTable(name="PROD_ING",
            joinColumns= @JoinColumn(name="id_produit", referencedColumnName="ID"),
            inverseJoinColumns= @JoinColumn(name="id_ingredient", referencedColumnName="ID")
    )
    private Set<Ingredient> ingredients = new HashSet<>();


    @ManyToMany
    @JoinTable(name="PROD_ALLERG",
            joinColumns= @JoinColumn(name="id_produit", referencedColumnName="ID"),
            inverseJoinColumns= @JoinColumn(name="id_allergene", referencedColumnName="ID")
    )
    private Set<Allergene> allergenes= new HashSet<>();


    @ManyToMany
    @JoinTable(name="PROD_ADDI",
            joinColumns= @JoinColumn(name="id_produit", referencedColumnName="ID"),
            inverseJoinColumns= @JoinColumn(name="id_additif", referencedColumnName="ID")
    )
    private Set<Additif> additifs= new HashSet<>();


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Double getEnergie() {
        return energie;
    }

    public void setEnergie(Double energie) {
        this.energie = energie;
    }

    public Double getGraisse() {
        return graisse;
    }

    public void setGraisse(Double graisse) {
        this.graisse = graisse;
    }

    public boolean isHuilePalme() {
        return huilePalme;
    }

    public void setHuilePalme(boolean huilePalme) {
        this.huilePalme = huilePalme;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Allergene> getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(Set<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    public Set<Additif> getAdditifs() {
        return additifs;
    }

    public void setAdditifs(Set<Additif> additifs) {
        this.additifs = additifs;
    }
}
