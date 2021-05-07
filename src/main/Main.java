package main;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;
import model.Login;
import model.Region;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

/**
 * @author Ethan Bills
 */
public class Main extends Application {

    /**
     * the lambda expression here shortens the amount of code it takes to close the stage to one line, making it more efficient
     * and easy to utilize in this case.
     *
     * initializes javafx environment for application
     * @param stage stage
     * @throws IOException
     * @throws SQLException
     */
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/loginView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        controller.loginController loginController = loader.getController();

        // gets the system locale and parses text
        loginController.localeLabel.setText(ZoneId.systemDefault().getId());

        /**
         * lambda expression shortens the amount of code it takes to close the stage, more efficient
         */
        loginController.cancelButton.setOnAction(cancel -> stage.close());

        if (Region.getLanguage() == "English") {
            System.out.println("English being used");

            // Do nothing, because the program is already in English

        } else if (Region.getLanguage() == "French") {
            System.out.println("French being used");

            loginController.usernameText.setText("Nom d'utilisateur");
            loginController.usernameText.setX(-60.0);
            loginController.passwordText.setText("Mot de passe");
            loginController.passwordText.setX(-50.0);
            loginController.locationLabel.setText("La localisation:");
            loginController.loginButton.setText("Connexion");
            loginController.cancelButton.setText("Annuler");

        } else {
            return;

        }

    }

    /**
     * handles launch of application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
