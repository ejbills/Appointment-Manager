package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.sql.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * @author Ethan Bills
 */
public class customerController implements Initializable {
    public Text labelText;
    public Button cancelButton;
    public TextField createdDateField;
    public Button saveButton;
    public TextField createdByField;
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField customerAddressField;
    public TextField customerPostalCodeField;
    public TextField customerPhoneField;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;

    ObservableList<String> countryList =
            FXCollections.observableArrayList(
                    "United States",
                    "United Kingdom",
                    "Canada"
            );

    /**
     * initializes add/modify customer view - gets and sets time
     * @param url database url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timestamp ts = java.sql.Timestamp.valueOf(LocalDateTime.now());
        createdDateField.setText(String.valueOf(ts));

        createdByField.setText("Application");

        countryComboBox.setItems(countryList);

    }

    /**
     * handles cancel button, loads customer table
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/customerTableView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customer Table");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * handles save button, sends info to database and handles logic checks
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onSaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        String tempName = customerNameField.getText();
        String tempAddress = customerAddressField.getText();
        String tempPostalCode = customerPostalCodeField.getText();
        String tempPhone = customerPhoneField.getText();

        if (!tempName.equals("") || !tempAddress.equals("") || !tempPostalCode.equals("") || !tempPhone.equals("")) {
            if (countryComboBox.getValue() != null && divisionComboBox.getValue() != null) {
                boolean canSave;

                if (countryComboBox.getValue() == "United States" && customerTableController.usDivisions.contains(divisionComboBox.getValue())) {
                    canSave = true;
                } else if (countryComboBox.getValue() == "United States" && !customerTableController.usDivisions.contains(divisionComboBox.getValue())) {
                    canSave = false;
                } else if (countryComboBox.getValue() == "United Kingdom" && customerTableController.ukDivisions.contains(divisionComboBox.getValue())) {
                    canSave = true;
                } else if (countryComboBox.getValue() == "United Kingdom" && !customerTableController.ukDivisions.contains(divisionComboBox.getValue())) {
                    canSave = false;
                } else if (countryComboBox.getValue() == "Canada" && customerTableController.canDivisions.contains(divisionComboBox.getValue())) {
                    canSave = true;
                } else if (countryComboBox.getValue() == "Canada" && !customerTableController.canDivisions.contains(divisionComboBox.getValue())) {
                    canSave = false;
                } else {
                    return;
                }

                if (canSave) {
                    String tempDivisionName = "'" + divisionComboBox.getValue() + "'";

                    if (labelText.getText() == "Modify Customer") {
                        System.out.println("Modify Save");

                        Database.updateCustomer(Integer.parseInt(customerIdField.getText()), customerNameField.getText(), customerAddressField.getText(), customerPostalCodeField.getText(), customerPhoneField.getText(), createdDateField.getText(), createdByField.getText(), Database.getDivisionId(tempDivisionName));

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/customerTableView.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Customer Table");
                        stage.setScene(scene);
                        stage.show();

                    } else {
                        System.out.println("Add Save");

                        Database.addCustomer(Integer.parseInt(customerIdField.getText()), customerNameField.getText(), customerAddressField.getText(), customerPostalCodeField.getText(), customerPhoneField.getText(), createdDateField.getText(), createdByField.getText(), Database.getDivisionId(tempDivisionName));

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/customerTableView.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Customer Table");
                        stage.setScene(scene);
                        stage.show();
                    }
                } else {
                    String error = "Country and division must match!";

                    Alert alert = new Alert(Alert.AlertType.ERROR, error, ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        alert.close();
                    }
                }
            } else {
                String error = "Select country and division.";

                Alert alert = new Alert(Alert.AlertType.ERROR, error, ButtonType.OK);
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
     * handles parsing of division data into division combobox when the country is selected
     * @param actionEvent
     */
    public void onCountryComboBox(ActionEvent actionEvent) {
        System.out.println("Country triggered: " + countryComboBox.getValue());

        if (countryComboBox.getValue() == "United States") {
            divisionComboBox.getItems().clear();

            for (int i = 0; i < customerTableController.usDivisions.size(); i++) {
                divisionComboBox.getItems().addAll(customerTableController.usDivisions.get(i));
            }
        } else if (countryComboBox.getValue() == "United Kingdom") {
            divisionComboBox.getItems().clear();

            for (int i = 0; i < customerTableController.ukDivisions.size(); i++) {
                divisionComboBox.getItems().addAll(customerTableController.ukDivisions.get(i));
            }
        } else if (countryComboBox.getValue() == "Canada") {
            divisionComboBox.getItems().clear();

            for (int i = 0; i < customerTableController.canDivisions.size(); i++) {
                divisionComboBox.getItems().addAll(customerTableController.canDivisions.get(i));
            }
        } else {
            System.out.println("ignoring.");
        }
    }
}
