package views;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TextPanelView extends VBox {
    private final ComboBox<String> chapterSelect = new ComboBox<>();
    private final TextArea textArea = new TextArea();
    private final Button loadBtn = new Button("Charger");
    private final Button saveBtn = new Button("Enregistrer");
    private final ToggleButton editBtn = new ToggleButton("Modifier");

    public TextPanelView(String title) {
        setSpacing(10);
        setStyle("-fx-border-color: #ccc; -fx-padding: 10;");

        editBtn.setOnAction(e -> {
            boolean edition = editBtn.isSelected();
            textArea.setEditable(edition);  // rend le champ modifiable (true) ou lecture seule (false):contentReference[oaicite:1]{index=1} 
        });

        HBox header = new HBox(10);
        header.getChildren().addAll(
            new Label(title), loadBtn, chapterSelect, saveBtn, editBtn
        );

        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle("-fx-font-family: 'Noto Sans CJK SC', 'Microsoft YaHei', 'SimSun'; -fx-font-size: 14;");
        getChildren().addAll(header, new ScrollPane(textArea));
    }



    // Getters
    public Button getLoadBtn() { return loadBtn; }
    public ComboBox<String> getChapterSelect() { return chapterSelect; }
    public TextArea getTextArea() { return textArea; }
    public Button getSaveBtn() { return saveBtn; }
    public ToggleButton getEditBtn() { return editBtn; }
}