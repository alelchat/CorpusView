// Fichier : src/views/StatisticsDisplay.java
package views;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javafx.scene.control.Label;
import models.TextModel;
import observer.Observer; // Importer l'interface

public class StatisticsDisplay implements Observer {
    private final Label levensteinLabel;
    private final Label commonWordsLabel;
    private final TextModel leftModel;
    private final TextModel rightModel;
    private final TextPanelView leftView;
    private final TextPanelView rightView;

    public StatisticsDisplay(Label levensteinLabel, Label commonWordsLabel, TextModel leftModel, TextModel rightModel, TextPanelView leftView, TextPanelView rightView) {
        this.levensteinLabel = levensteinLabel;
        this.commonWordsLabel = commonWordsLabel;
        this.leftModel = leftModel;
        this.rightModel = rightModel;
        this.leftView = leftView;
        this.rightView = rightView;
    }

    private String getSelectedChapterText(TextModel model, TextPanelView view) {
        String selectedChapter = view.getChapterSelect().getValue();
        return (selectedChapter != null) ? model.getChapters().get(selectedChapter) : "";
    }

    @Override
    public void update(TextModel model) {

        String leftText = getSelectedChapterText(leftModel, leftView);
        String rightText = getSelectedChapterText(rightModel, rightView);

        // Calculer les mots communs
        Set<String> leftWords = extractWords(leftText);
        Set<String> rightWords = extractWords(rightText);
        leftWords.retainAll(rightWords);
        commonWordsLabel.setText("Mots en commun : " + leftWords.size());

        // Calcul optimisé de la distance de Levenshtein (sur le texte brut)
        int distance = computeLevenshteinDistance(leftText, rightText);
        levensteinLabel.setText("Distance de Levenshtein : " + distance);
    }

    // Implémentation optimisée de Levenshtein (sur les caractères)
    private int computeLevenshteinDistance(String a, String b) {
        int[] dp = new int[b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            int[] temp = new int[b.length() + 1];
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    temp[j] = j;
                } else if (j == 0) {
                    temp[j] = i;
                } else {
                    int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                    temp[j] = Math.min(
                            dp[j] + 1,
                            Math.min(temp[j - 1] + 1, dp[j - 1] + cost));
                }
            }
            dp = temp;
        }
        return dp[b.length()];
    }

    private Set<String> extractWords(String text) {
        Set<String> words = new HashSet<>();
        if (text == null || text.trim().isEmpty()) {
            return words; // Retourne un set vide si le texte est vide
        }
        Scanner scanner = new Scanner(text.toLowerCase());
        scanner.useDelimiter("[^\\p{L}]+");
        while (scanner.hasNext()) {
            String word = scanner.next().trim();
            if (!word.isEmpty()) { // Ignore les chaînes vides
                words.add(word);
            }
        }
        scanner.close();
        return words;
    }
}