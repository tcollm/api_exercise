module com.example.api_exercise {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.api_exercise to javafx.fxml;
    exports com.example.api_exercise;
}