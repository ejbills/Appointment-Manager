package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Ethan Bills
 */
public class customerTableController implements Initializable {
    public Button addCustomer;
    public Button modifyCustomer;
    public Button deleteCustomer;
    public static Database databaseHandler;

    public TableView<Customer> customerDataTable;

    public TableColumn<Customer, String> customerIdColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> addressColumn;
    public TableColumn<Customer, String> postalCodeColumn;
    public TableColumn<Customer, String> phoneColumn;
    public TableColumn<Customer, String> createDateColumn;
    public TableColumn<Customer, String> createdByColumn;
    public TableColumn<Customer, String> divisionIdColumn;

    public static List<String> usDivisions = Arrays.asList("Alabama","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","District of Columbia","Florida","Georgia","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming","Hawaii","Alaska");
    public static List<String> ukDivisions = Arrays.asList("England","Wales","Scotland","Northern Ireland");
    public static List<String> canDivisions = Arrays.asList("Northwest Territories","Alberta","British Columbia","Manitoba","New Brunswick","Nova Scotia","Prince Edward Island","Ontario","Québec","Saskatchewan","Nunavut","Yukon","Newfoundland and Labrador");

    public Button appointmentButton;
    public Button reportButton;

    /**
     * initializes table values, initializes database connection
     * @param url database url
     * @param resourceBundle resource bundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            databaseHandler = new Database(databaseHandler.URL, databaseHandler.databaseUser, databaseHandler.databasePassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("customerCreateDate"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("customerCreatedBy"));
        divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivisionId"));

        try {
            customerDataTable.getItems().addAll(databaseHandler.getCustomerList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * handles add customer button, loads add customer view
     * @param actionEvent
     * @throws IOException
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/customerView.fxml"));
        Parent root = loader.load();

        customerController tempController = loader.getController();
        tempController.customerIdField.setText(String.valueOf(databaseHandler.getNextCustomerId()));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * handles loading of modify customer view, logic checks
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onModifyCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        Customer customer = customerDataTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/customerView.fxml"));
            Parent root = loader.load();

            customerController tempController = loader.getController();
            tempController.labelText.setText("Modify Customer");
            tempController.labelText.setX(-10.0);

            tempController.customerIdField.setText(customer.getCustomerId());
            tempController.customerAddressField.setText(customer.getCustomerAddress());
            tempController.customerNameField.setText(customer.getCustomerName());
            tempController.customerPhoneField.setText(customer.getCustomerPhone());
            tempController.createdByField.setText(customer.getCustomerCreatedBy());
            tempController.createdDateField.setText(customer.getCustomerCreateDate());
            tempController.customerPostalCodeField.setText(customer.getPostalCode());

            tempController.divisionComboBox.setValue(model.Database.getDivisionName(customer.getCustomerDivisionId()));
            tempController.countryComboBox.setValue(model.Database.getCountryName(customer.getCustomerDivisionId()));

            System.out.println("Country triggered: " + tempController.countryComboBox.getValue());

            if (tempController.countryComboBox.getValue() == "United States") {
                tempController.divisionComboBox.getItems().clear();

                for (int i = 0; i < customerTableController.usDivisions.size(); i++) {
                    tempController.divisionComboBox.getItems().addAll(customerTableController.usDivisions.get(i));
                }
            } else if (tempController.countryComboBox.getValue() == "United Kingdom") {
                tempController.divisionComboBox.getItems().clear();

                for (int i = 0; i < customerTableController.ukDivisions.size(); i++) {
                    tempController.divisionComboBox.getItems().addAll(customerTableController.ukDivisions.get(i));
                }
            } else if (tempController.countryComboBox.getValue() == "Canada") {
                tempController.divisionComboBox.getItems().clear();

                for (int i = 0; i < customerTableController.canDivisions.size(); i++) {
                    tempController.divisionComboBox.getItems().addAll(customerTableController.canDivisions.get(i));
                }
            } else {
                System.out.println("Not modifying.");
            }

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a customer to modify!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

    }

    /**
     * handles deletion of customer, logic checks
     * @param actionEvent
     * @throws IOException
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws IOException {
        Customer customer = customerDataTable.getSelectionModel().getSelectedItem();

        if (customer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete customer with ID " + customer.getCustomerId() + "?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                databaseHandler.deleteCustomer(Integer.parseInt(customer.getCustomerId()));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/customerTableView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Customer Table");
                stage.setScene(scene);
                stage.show();
            } else if (alert.getResult() == ButtonType.CANCEL) {
                alert.close();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a customer to delete!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

    }

    /**
     * loads appointment view
     * @param actionEvent
     * @throws IOException
     */
    public void onAppointmentButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/appointmentsTableView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Appointments Table");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * loads reports view
     * @param actionEvent
     * @throws IOException
     */
    public void onReportButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/reportsView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}




