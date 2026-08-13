module com.example.proj_escosensor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.xml.crypto;


    opens com.example.proj_escosensor to javafx.fxml;
    exports com.example.proj_escosensor;
}