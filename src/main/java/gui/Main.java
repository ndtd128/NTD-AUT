package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.cloneProjectUtil.CloneProjectUtil;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Base.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("NTD-AUT");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        CloneProjectUtil.deleteFilesInDirectory("\\NTD-AUT\\src\\main\\java\\clonedProject");
    }
}