package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import main.Main;
import model.Database;
import model.Login;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Region;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Ethan Bills
 */
public class loginController {
    public Text localeLabel;
    public Button loginButton;
    public TextField usernameField;
    public TextField passwordField;
    public Button cancelButton;
    public Text usernameText;
    public Text passwordText;
    public Text locationLabel;

    Database databaseHandler;

    /**
     * handles function of logging in, handles logic checks (user/pass, upcoming appointments, etc.)
     * @param actionEvent
     * @throws IOException
     */
    public void onLoginClick(ActionEvent actionEvent) throws IOException {
        if (usernameField.getLength() > 0 && passwordField.getLength() > 0) {
            Login.loginSuccessCheck(usernameField.getText(), passwordField.getText());
            Login.attemptLogin(usernameField.getText(), passwordField.getText());

            if (Login.loginGranted) {
                try {
                    databaseHandler = new Database(databaseHandler.URL, databaseHandler.databaseUser, databaseHandler.databasePassword);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                try {

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                    java.util.Date currentDate = new java.util.Date();
                    dateFormat.format(currentDate);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);

                    calendar.add(Calendar.MINUTE, 15);

                    Date futureDate = calendar.getTime();
                    System.out.println(dateFormat.format(futureDate));

                    String upcomingAppointment = databaseHandler.fifteenMinuteAppointmentCheck(dateFormat.format(currentDate), dateFormat.format(futureDate));
                    System.out.println("Upcoming appointment: " + upcomingAppointment);
                    if (upcomingAppointment != null) {
                        System.out.println("UPCOMING APPOINTMENT DETECTED");

                        Alert alert = new Alert(Alert.AlertType.INFORMATION, upcomingAppointment, ButtonType.OK);
                        alert.setTitle("UPCOMING APPOINTMENT - WITHIN 15 MINUTES");
                        alert.setHeaderText("You have an appointment in the next 15 minutes.");
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            alert.close();
                        }
                    } else {
                        System.out.println("UPCOMING APPOINTMENT NOT DETECTED");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have no upcoming appointments.", ButtonType.OK);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            alert.close();
                        }
                    }

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
                    alert.showAndWait();

                    System.out.println(e);

                    if (alert.getResult() == ButtonType.YES) {
                        alert.close();
                    }
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/customerTableView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Customer Table");
                stage.setScene(scene);
                stage.show();

            } else {
                if (model.Region.getLanguage() == "English") {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password!", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        alert.close();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Mauvais nom d'utilisateur ou mot de passe!", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        alert.close();
                    }
                }
            }

        } else if (model.Region.getLanguage() == "English") {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to input values for the username and password!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        } else if (model.Region.getLanguage() == "French") {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vous devez saisir des valeurs pour le nom\n" + "d'utilisateur et le mot de passe!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    /**
     * initializes cancel button function (function set in main)
     * @param actionEvent
     */
    public void onCancelClick(ActionEvent actionEvent) {
    }
}
