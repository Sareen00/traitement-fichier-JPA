package fr.diginamic.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LecteurCsv {

    private static final int NB_COLONNES = 31;


    public static Double toDouble(String v) {
        if (v.isBlank()) return null;
        return Double.parseDouble(v.trim());
    }



    public static List<String[]> lire(String chemin) throws IOException {
        List<String> lignes = Files.readAllLines(Path.of(chemin), StandardCharsets.UTF_8);
        List<String[]> resultat = new ArrayList<>();

        for (int i = 1; i < lignes.size(); i++) {
            String[] c = lignes.get(i).split("\\|", -1);
            if (c.length != NB_COLONNES) continue;
            if (c[2].isBlank()) continue;
            resultat.add(c);
        }
        return resultat;
    }


    public static List<String> decouper(String v, String separateurs) {
        Set<String> res = new LinkedHashSet<>();
        for (String morceau : v.split(separateurs)) {
            String nom = normaliser(morceau);
            if (!nom.isEmpty() && nom.length() <= 255) {
                res.add(nom);
            }
        }
        return new ArrayList<>(res);
    }

    public static String marque(String v) {
        return normaliser(v.split(",")[0]);
    }

    public static List<String> ingredients(String v) {
        String t = v.replaceAll("\\(.*?\\)", " ")
                .replaceAll("[0-9.,]+ ?%", " ");
        return decouper(t, "[,;]");
    }

    public static List<String> allergenes(String v) {
        return decouper(v.replaceAll("\\b[a-z]{2}:", ""), "[,;]");
    }

    public static List<String> additifs(String v) {
        List<String> res = new ArrayList<>();
        for (String a : decouper(v, ",")) {
            String nom = a.replaceFirst("^e\\d+[a-z]*\\s*-\\s*", "").trim();
            if (!nom.isEmpty() && !res.contains(nom)) res.add(nom);
        }
        return res;
    }


    public static String normaliser(String v) {
        return v.toLowerCase()
                .replaceAll("[àâä]", "a")
                .replaceAll("[éèêë]", "e")
                .replaceAll("[îï]", "i")
                .replaceAll("[ôö]", "o")
                .replaceAll("[ùûü]", "u")
                .replace("ÿ", "y")
                .replace("ç", "c")
                .replace("œ", "oe")
                .replace("æ", "ae")
                .replaceAll("[^a-z0-9 '\\-]", " ")
                .replaceAll(" +", " ")
                .trim();
    }

}