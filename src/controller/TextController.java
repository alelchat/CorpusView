package controller;

import java.util.Map;
import models.strategy.LoadChapterStrategy;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import models.TextModel;
import views.TextPanelView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class TextController {
    public TextController(TextModel model, TextPanelView view) {
        model.setLoadStrategy(new LoadChapterStrategy());

        // Récupère le bouton de chargement et initie les évènements
        view.getLoadBtn().setOnAction(e -> {
            File file = new FileChooser().showOpenDialog(null);
            if (file != null) {
                model.loadText(file);
                Map<String, String> chapters = model.getChapters();

                // Mettre à jour la ComboBox
                view.getChapterSelect().getItems().setAll(chapters.keySet());

                // Afficher le premier chapitre par défaut
                if (!chapters.isEmpty()) {
                    String firstChapter = chapters.keySet().iterator().next();
                    view.getChapterSelect().getSelectionModel().select(firstChapter);
                    view.getTextArea().setText(chapters.get(firstChapter)); // Affiche uniquement le chapitre
                }
                model.notifyObservers();
            }
        });

        // Récupère le bouton d'édition et initie les évènements
        view.getEditBtn().setOnAction(e -> {
            boolean edition = view.getEditBtn().isSelected();
            view.getTextArea().setEditable(edition);  // rend le champ modifiable (true) ou lecture seule (false)

            if(edition) {
                view.getEditBtn().setText("Enregistrer");
            } else {
                view.getEditBtn().setText("Modifier");
                // Lorsqu'on repasse à modifier, on veut que le nombre de mot en commun et la distance de Levenshtein soient recalculés
                // Pour s'adapter aux modifications faites dans le TextArea
                model.notifyObservers();
            }
        });

        // Récupère le bouton d'édition et initie les évènements
        view.getSaveBtn().setOnAction(e -> {
            // Si aucun fichier n'a été chargé, on ne peut rien sauvegarder.
            File currentFile = model.getCurrentFile();
            if (currentFile == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Aucun fichier chargé");
                alert.setContentText("Veuillez charger un fichier avant de sauvegarder.");
                alert.showAndWait();
                return;
            }

            // Construire le nom du fichier de sauvegarde
            String originalName = currentFile.getName();
            String baseName;
            String extension = "";
            int dotIndex = originalName.lastIndexOf('.');
            if (dotIndex > 0) {
                baseName = originalName.substring(0, dotIndex);
                extension = originalName.substring(dotIndex);
            } else {
                baseName = originalName;
            }
            String newName = baseName + "_modified" + extension;

            File dataDir = new File("Data");
            if (!dataDir.exists()) dataDir.mkdir();


            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(dataDir);
            fileChooser.setInitialFileName(newName);
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
            File saveFile = fileChooser.showSaveDialog(null);

            if (saveFile != null) {
                try (BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(saveFile), StandardCharsets.UTF_8))) {

                    for (Map.Entry<String, String> entry : model.getChapters().entrySet()) {
                        String titleLine = entry.getKey() + "." + System.lineSeparator();
                        writer.write(titleLine);

                        String content = entry.getValue().replace("\n", System.lineSeparator());
                        writer.write(content);
                        writer.write(System.lineSeparator()); // Sépare les chapitres
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sauvegarde réussie");
                    alert.setHeaderText(null);
                    alert.setContentText("Le fichier a été sauvegardé dans : " + saveFile.getAbsolutePath());
                    alert.showAndWait();// Récupère le bouton d'édition et initie les évènements

                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur lors de la sauvegarde");
                    alert.setContentText("Impossible de sauvegarder le fichier : " + ex.getMessage());
                    alert.showAndWait();
                    ex.printStackTrace();
                }
            }
        });

        // Gérer le changement de chapitre
        view.getChapterSelect().setOnAction(e -> {
            String selectedChapter = view.getChapterSelect().getValue();
            if (selectedChapter != null && !selectedChapter.isEmpty()) {
                view.getTextArea().setText(model.getChapters().get(selectedChapter));
                // Changement de chapitre -> On veut recalculer les statistiques (distance de Levenshtein et mots en commun)
                model.notifyObservers();
            }
        });

        view.getTextArea().textProperty().addListener((obs, oldVal, newVal) -> {
            String selectedChapter = view.getChapterSelect().getValue();
            if (selectedChapter != null) {
                model.getChapters().put(selectedChapter, newVal);
                //model.notifyObservers();
            }
        });
    }
}
