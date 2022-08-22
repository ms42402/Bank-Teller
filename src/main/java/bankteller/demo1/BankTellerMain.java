package bankteller.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This is the driver class for running the Bank Teller.
 * @author Afsana Rahman, Mini Sinha
 */
public class BankTellerMain extends Application {
    /**
     * This method sets the scene of the application.
     * @param stage the stage to set the scene on
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setTitle("Bank Teller");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Driver main
     */
    public static void main(String[] args) {
        launch();
    }
}