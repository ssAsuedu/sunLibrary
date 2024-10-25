module com.example.sundevilslibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;


    opens com.example.sundevilslibrary to javafx.fxml;
    exports com.example.sundevilslibrary;
}