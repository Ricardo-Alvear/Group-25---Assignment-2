module org.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.assignment2 to javafx.fxml;
    exports org.example.assignment2;
}