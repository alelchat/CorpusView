// Fichier : src/views/StatisticsDisplay.java
package views;

import javafx.scene.control.Label;
import models.TextModel;
import observer.Observer; // Importer l'interface

public class StatisticsDisplay implements Observer {
    private final Label levensteinLabel;
    private final Label commonWordsLabel;

    public StatisticsDisplay(Label levensteinLabel, Label commonWordsLabel) {
        this.levensteinLabel = levensteinLabel;
        this.commonWordsLabel = commonWordsLabel;
    }

    @Override
    public void update(TextModel model) {
        // Exemple de calcul (à adapter avec la logique)
        int distance = 42; // Remplacer par le vrai calcul
        int commonWords = 10;
        
        levensteinLabel.setText("Distance de Levenshtein : " + distance);
        commonWordsLabel.setText("Mots en commun : " + commonWords);
    }
}