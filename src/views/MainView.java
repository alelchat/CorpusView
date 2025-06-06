package views;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import utils.TextUtils;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MainView extends BorderPane {
    private final TextPanelView leftPanel = new TextPanelView("Version 1");
    private final TextPanelView rightPanel = new TextPanelView("Version 2");
    private final Label levensteinLabel = new Label("Distance de Levenshtein : 0");
    private final Label commonWordsLabel = new Label("Mots en commun : 0");
    private final CheckMenuItem toggleStatsItem = new CheckMenuItem("Afficher les statistiques");
    private final MenuItem showCommonWordsItem = new MenuItem("Afficher les mots communs");

    public MainView() {
        setPadding(new Insets(10));
        buildUI();
        setupMenuBehavior();
    }

    private void buildUI() {
        // Header
        HBox header = new HBox(20);
        MenuBar menuBar = new MenuBar();
        Menu toolsMenu = new Menu("Outils");
        Menu helpMenu = new Menu("Aide");
        toolsMenu.getItems().addAll(toggleStatsItem, showCommonWordsItem);
        MenuItem helpMenuItem = new MenuItem("Besoin d'aide ?");


        helpMenuItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aide");
            alert.setHeaderText("Comment utiliser l'application ?");
            alert.setContentText("1. Chargez deux textes.\n2. Sélectionnez un chapitre pour chaque texte.\n3. Utilisez les outils pour afficher les statistiques.");
            alert.showAndWait();
        });

        helpMenu.getItems().add(helpMenuItem);
        menuBar.getMenus().add(toolsMenu);
        menuBar.getMenus().add(helpMenu);

        header.getChildren().addAll(
                new Label("Corpus Viewer"),
                menuBar);

        // Footer
        HBox footer = new HBox(20);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #f0f0f0;");
        footer.getChildren().addAll(levensteinLabel, commonWordsLabel);

        setTop(header);
        setCenter(new HBox(20, leftPanel, rightPanel));
        setBottom(footer);
    }

    private void showCommonWordsDialog(Set<String> commonWords) {
        // Permet l'ouverture d'une Pop Up avec les mots communs entre les deux textes (tous mit en minuscules et triés) lorsque
        // on clique sur le bouton "Afficher les mots communs" du menu Outils.
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Mots en commun");

        // Convertir en liste triée
        List<String> sortedWords = new ArrayList<>(commonWords);
        Collections.sort(sortedWords);

        // Créer le contenu
        TextArea content = new TextArea(String.join("\n", sortedWords));
        content.setEditable(false);
        content.setWrapText(true);
        content.setStyle("-fx-font-family: 'Noto Sans CJK SC', 'Microsoft YaHei', 'SimSun'; -fx-font-size: 14;");
        

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setPrefSize(400, 300);

        dialog.getDialogPane().setContent(scrollPane);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }

    private void setupMenuBehavior() {
        // Afficher/cacher les statistiques
        toggleStatsItem.setSelected(true);
        toggleStatsItem.setOnAction(e -> {
            boolean visible = toggleStatsItem.isSelected();
            levensteinLabel.setVisible(visible);
            commonWordsLabel.setVisible(visible);
        });

        showCommonWordsItem.setOnAction(e -> {
            // Récupérer les textes
            String leftText = leftPanel.getTextArea().getText();
            String rightText = rightPanel.getTextArea().getText();

            // Calculer les mots communs (utilise la même logique que StatisticsDisplay)
            Set<String> leftWords = TextUtils.extractWords(leftText);
            Set<String> rightWords = TextUtils.extractWords(rightText);
            leftWords.retainAll(rightWords);

            showCommonWordsDialog(leftWords);
        });
    }

    // Getters
    public TextPanelView getLeftPanel() {
        return leftPanel;
    }

    public TextPanelView getRightPanel() {
        return rightPanel;
    }

    public Label getLevensteinLabel() {
        return levensteinLabel;
    }

    public Label getCommonWordsLabel() {
        return commonWordsLabel;
    }

    public CheckMenuItem getToggleStatsItem() {
        return toggleStatsItem;
    }
}