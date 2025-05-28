import javafx.application.Application;
import javafx.stage.Stage;
import models.TextModel;
import views.MainView;
import views.StatisticsDisplay;
import controller.TextController;
import javafx.scene.Scene;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        TextModel leftModel = new TextModel();
        TextModel rightModel = new TextModel();

        // Configuration des contrôleurs
        new TextController(leftModel, mainView.getLeftPanel());
        new TextController(rightModel, mainView.getRightPanel());

        // Statistiques
        StatisticsDisplay stats = new StatisticsDisplay(
            mainView.getLevensteinLabel(), 
            mainView.getCommonWordsLabel(),
            leftModel,
            rightModel,
            mainView.getLeftPanel(),
            mainView.getRightPanel()
        );
        
        leftModel.addObserver(stats);
        rightModel.addObserver(stats);

        stage.setScene(new Scene(mainView, 1000, 600));
        stage.setTitle("Corpus Viewer");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}