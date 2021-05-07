package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * @author Ethan Bills
 */
public class reportsController implements Initializable {
    public Button customerButton;
    public TableColumn contactColumn;
    public TableColumn aIdColumn;
    public TableColumn titleColumn;
    public TableColumn descColumn;
    public TableColumn typeColumn;
    public TableColumn startDateColumn;
    public TableColumn endDateColumn;
    public TableColumn customerIdColumn;
    public TableView appointmentTable;
    public Text reportText;
    public AnchorPane typeYearMonthTab;
    public Text customerReportText;
    public static Database databaseHandler;
    public TableView typeYearMonthTable;
    public TableColumn countReportColumn;
    public TableColumn typeReportColumn;
    public TableColumn yearMonthColumn;
    public TableView customerReportTable;
    public TableColumn customerCountColumn;
    public TableColumn customerIdReportColumn;

    /**
     * initializes table views and report data
     * @param url database url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            databaseHandler = new Database(databaseHandler.URL, databaseHandler.databaseUser, databaseHandler.databasePassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        aIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDesc"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentContactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerId"));

        countReportColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        typeReportColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        yearMonthColumn.setCellValueFactory(new PropertyValueFactory<>("yearMonth"));

        customerCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        customerIdReportColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        try {
            typeYearMonthTable.getItems().addAll(databaseHandler.typeReport());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            try {
                appointmentTable.getItems().addAll(Database.getContactReport(String.valueOf(i)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        try {
            customerReportTable.getItems().addAll(databaseHandler.customerReport());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * loads customer table view
     * @param actionEvent
     * @throws IOException
     */
    public void onCustomerButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/customerTableView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customer Table");
        stage.setScene(scene);
        stage.show();
    }
}
