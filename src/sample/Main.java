package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Numerical Methods");
        primaryStage.setScene(new Scene(root, 900, 480));
        primaryStage.setMaxHeight(480);
        primaryStage.setMaxWidth(900);
        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(900);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
