module org.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;
    requires java.desktop;
    opens org.example.assignment2 to com.google.gson, javafx.fxml;

    exports org.example.assignment2;
}