package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.FilePath;
import utils.cloneProjectUtil.CloneProjectUtil;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ChooseToolScene.fxml")));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("AUT tool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
        CloneProjectUtil.deleteFilesInDirectory(FilePath.clonedProjectPath);
    }
}