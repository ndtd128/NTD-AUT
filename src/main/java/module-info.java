module org.example.examplejavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.examplejavafx to javafx.fxml;
    exports org.example.examplejavafx;
}