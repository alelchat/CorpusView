package views;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class MainView extends BorderPane {
    private final TextPanelView leftPanel = new TextPanelView("Version 1");
    private final TextPanelView rightPanel = new TextPanelView("Version 2");
    private final Label levensteinLabel = new Label("Distance de Levenshtein : 0");
    private final Label commonWordsLabel = new Label("Mots en commun : 0");
    private final CheckMenuItem toggleStatsItem = new CheckMenuItem("Afficher les statistiques");

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
        toolsMenu.getItems().add(toggleStatsItem);
        helpMenu.getItems().add(new MenuItem("Vous avez un problème ? Contactez-nous"));
        menuBar.getMenus().add(toolsMenu);
        menuBar.getMenus().add(helpMenu);
        
        header.getChildren().addAll(
            new Label("UnknownVariable"),
            menuBar
        );

        // Footer
        HBox footer = new HBox(20);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #f0f0f0;");
        footer.getChildren().addAll(levensteinLabel, commonWordsLabel);

        setTop(header);
        setCenter(new HBox(20, leftPanel, rightPanel));
        setBottom(footer);
    }

    private void setupMenuBehavior() {
        // Afficher/cacher les statistiques
        toggleStatsItem.setSelected(true);
        toggleStatsItem.setOnAction(e -> {
            boolean visible = toggleStatsItem.isSelected();
            levensteinLabel.setVisible(visible);
            commonWordsLabel.setVisible(visible);
        });
    }

    // Getters
    public TextPanelView getLeftPanel() { return leftPanel; }
    public TextPanelView getRightPanel() { return rightPanel; }
    public Label getLevensteinLabel() { return levensteinLabel; }
    public Label getCommonWordsLabel() { return commonWordsLabel; }
    public CheckMenuItem getToggleStatsItem() { return toggleStatsItem; }
}