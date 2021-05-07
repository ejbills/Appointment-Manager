package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Database;
import model.Hours;
import model.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Ethan Bills
 */

public class appointmentController implements Initializable {
public Text addAppointmentText;
    public TextField appointmentTitle;
    public TextField appointmentLocation;
    public TextField appointmentDescription;
    public TextField appointmentType;
    public DatePicker startDate;
    public DatePicker endDate;
    public ComboBox startMinute;
    public ComboBox endMinute;
    public ComboBox appointmentContact;
    public ComboBox appointmentCustomer;
    public Button saveButton;
    public Button cancelButton;
    public TextField startHourField;
    public TextField endHourField;
    public TextField appointmentId;

    /**
     * initializes window view
     * @param resourceBundle loads view
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * handles save button function, logic checks
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException, SQLException {
        String tempTitle = appointmentTitle.getText();
        String tempDesc = appointmentDescription.getText();
        String tempLocation = appointmentLocation.getText();
        String tempType = appointmentType.getText();

        if (!tempTitle.equals("") || !tempDesc.equals("") || !tempLocation.equals("") || !tempType.equals("")) {
            if (addAppointmentText.getText() == "Add Appointment") {
                if (startDate.getValue() != null && endDate.getValue() != null) {
                    if (Integer.parseInt(startHourField.getText()) < 24 && Integer.parseInt(startHourField.getText()) > 0 || Integer.parseInt(endHourField.getText()) < 24 && Integer.parseInt(endHourField.getText()) > 0) {
                        String startTime = startDate.getValue() + " " + startHourField.getText() + ":" + startMinute.getValue() + ":00";
                        String endTime = endDate.getValue() + " " + endHourField.getText() + ":" + endMinute.getValue() + ":00";

                        if (!Database.overlappingAppointmentCheck(startTime, endTime, String.valueOf(appointmentCustomer.getValue()))) {
                            if (Hours.isInOfficeHours(startTime, endTime)) {
                                if (appointmentContact.getValue() != null) {
                                    System.out.println("Add Save");

                                    Database.addAppointment(appointmentId.getText(), appointmentTitle.getText(), appointmentDescription.getText(), appointmentLocation.getText(), appointmentType.getText(), startTime, endTime, "Application", "Application", String.valueOf(appointmentCustomer.getValue()), String.valueOf(1), Database.getContactId(String.valueOf(appointmentContact.getValue())));

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/appointmentsTableView.fxml"));
                                    Parent root = loader.load();
                                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                    Scene scene = new Scene(root);
                                    stage.setTitle("Appointments Table");
                                    stage.setScene(scene);
                                    stage.show();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a valid contact!", ButtonType.OK);
                                    alert.showAndWait();

                                    if (alert.getResult() == ButtonType.YES) {
                                        alert.close();
                                    }
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment is not within office hours!", ButtonType.OK);
                                alert.showAndWait();

                                if (alert.getResult() == ButtonType.YES) {
                                    alert.close();
                                }
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Overlapping appointments. Please change time.", ButtonType.OK);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                alert.close();
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Hour field must be in range of 1-23!", ButtonType.OK);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            alert.close();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You must select dates for BOTH start and end!", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        alert.close();
                    }
                }
            } else if (addAppointmentText.getText() == "Modify Appointment") {
                if (startDate.getValue() != null && endDate.getValue() != null) {
                    if (Integer.parseInt(startHourField.getText()) < 24 && Integer.parseInt(startHourField.getText()) > 0 || Integer.parseInt(endHourField.getText()) < 24 && Integer.parseInt(endHourField.getText()) > 0) {
                        String startTime = startDate.getValue() + " " + startHourField.getText() + ":" + startMinute.getValue() + ":00";
                        String endTime = endDate.getValue() + " " + endHourField.getText() + ":" + endMinute.getValue() + ":00";

                        System.out.println(startTime + endTime);

                        if (!Database.overlappingAppointmentCheck(startTime, endTime, String.valueOf(appointmentCustomer.getValue()))) {
                            if (Hours.isInOfficeHours(startTime, endTime)) {
                                if (appointmentContact.getValue() != null) {
                                    System.out.println("Modify Save");

                                    Database.updateAppointment(appointmentId.getText(), appointmentTitle.getText(), appointmentDescription.getText(), appointmentLocation.getText(), appointmentType.getText(), startTime, endTime, String.valueOf(appointmentCustomer.getValue()), Database.getContactId(String.valueOf(appointmentContact.getValue())));

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/appointmentsTableView.fxml"));
                                    Parent root = loader.load();
                                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                    Scene scene = new Scene(root);
                                    stage.setTitle("Appointments Table");
                                    stage.setScene(scene);
                                    stage.show();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a valid contact!", ButtonType.OK);
                                    alert.showAndWait();

                                    if (alert.getResult() == ButtonType.YES) {
                                        alert.close();
                                    }
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment is not within office hours!", ButtonType.OK);
                                alert.showAndWait();

                                if (alert.getResult() == ButtonType.YES) {
                                    alert.close();
                                }
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Overlapping appointments. Please change time.", ButtonType.OK);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                alert.close();
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Hour field must be in range of 1-23!", ButtonType.OK);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            alert.close();
                        }
                    }


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You must select dates for BOTH start and end!", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        alert.close();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred whilst trying to save. Please try again.", ButtonType.OK);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    alert.close();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please input values for all fields.", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    /**
     * handles closing of window, loads appointment table
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/appointmentsTableView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Appointments Table");
        stage.setScene(scene);
        stage.show();
    }
}
