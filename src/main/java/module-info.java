module org.example.noughtsandcrosses {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens org.example.noughtsandcrosses to javafx.fxml;
    exports org.example.noughtsandcrosses;
}