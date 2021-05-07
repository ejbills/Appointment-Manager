package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Database;
import model.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Ethan Bills
 */
public class appointmentsTableController implements Initializable {
    public Text fromDate;
    public Text toDate;
    Date placeholderDate;

    public Button customerButton;
    public RadioButton allRadio;
    public RadioButton weeklyRadio;
    public RadioButton monthlyRadio;
    public Button lastButton;
    public Button nextButton;
    Database databaseHandler;

    public TableView<Appointment> appointmentTable;

    public TableColumn<Appointment, String> aIdColumn;
    public TableColumn<Appointment, String> titleColumn;
    public TableColumn<Appointment, String> descColumn;
    public TableColumn<Contact, String> contactColumn;
    public TableColumn<Appointment, String> locationColumn;
    public TableColumn<Appointment, String> typeColumn;
    public TableColumn<Appointment, String> startDateColumn;
    public TableColumn<Appointment, String> endDateColumn;
    public TableColumn<Appointment, String> customerIdColumn;

    public static List<Integer> minuteOptionsList = Arrays.asList(00,15,30,45);

    public appointmentsTableController() throws SQLException {
    }

    /**
     * initializes table view and database connection
     * @param url database url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allRadio.requestFocus();
        allRadio.setSelected(true);

        lastButton.setDisable(true);
        nextButton.setDisable(true);

        try {
            databaseHandler = new Database(Database.URL, Database.databaseUser, Database.databasePassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        aIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDesc"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentContactName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerId"));

        try {
            appointmentTable.getItems().addAll(databaseHandler.getAppointmentList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * handles "all" radio button event
     * @param actionEvent
     * @throws SQLException
     */
    public void onAllRadio(ActionEvent actionEvent) throws SQLException {
        allRadio.requestFocus();
        allRadio.setSelected(true);
        weeklyRadio.setSelected(false);
        monthlyRadio.setSelected(false);

        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(databaseHandler.getAppointmentList());

        lastButton.setDisable(true);
        nextButton.setDisable(true);

    }

    /**
     * handles "weekly" radio button event
     * @param actionEvent
     * @throws SQLException
     */
    public void onWeeklyRadio(ActionEvent actionEvent) throws SQLException {
        weeklyRadio.requestFocus();
        weeklyRadio.setSelected(true);
        allRadio.setSelected(false);
        monthlyRadio.setSelected(false);

        lastButton.setDisable(false);
        nextButton.setDisable(false);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.setTime(currentDate);
        
        placeholderDate = currentDate;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        calendar.add(Calendar.HOUR, 168);

        Date weekFromPlaceholderDate = calendar.getTime();
        System.out.println("SHOWING APPOINTMENTS FROM NOW TO "+weekFromPlaceholderDate);

        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(databaseHandler.getAppointmentInRange(dateFormat.format(currentDate), dateFormat.format(weekFromPlaceholderDate)));

        fromDate.setText(String.valueOf(currentDate));
        toDate.setText(String.valueOf(weekFromPlaceholderDate));

        placeholderDate = weekFromPlaceholderDate;

    }

    /**
     * handles "monthly" radio button event
     * @param actionEvent
     * @throws SQLException
     */
    public void onMonthlyRadio(ActionEvent actionEvent) throws SQLException {
        monthlyRadio.requestFocus();
        monthlyRadio.setSelected(true);
        allRadio.setSelected(false);
        weeklyRadio.setSelected(false);

        lastButton.setDisable(false);
        nextButton.setDisable(false);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.setTime(currentDate);

        placeholderDate = currentDate;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        calendar.add(Calendar.HOUR, 740);

        Date monthFromPlaceholderDate = calendar.getTime();
        System.out.println("SHOWING APPOINTMENTS FROM NOW TO "+monthFromPlaceholderDate);

        fromDate.setText(String.valueOf(currentDate));
        toDate.setText(String.valueOf(monthFromPlaceholderDate));

        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(databaseHandler.getAppointmentInRange(dateFormat.format(currentDate), dateFormat.format(monthFromPlaceholderDate)));

    }

    /**
     * the lambda expression here simplifies a for loop to one line, improving efficiency
     *
     * handles add appointment button and logic
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        List<String> customerIdList = databaseHandler.getCustomerIdList();

        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<Integer> minutes = FXCollections.observableArrayList();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/appointmentView.fxml"));
        Parent root = loader.load();
        appointmentController tempController = loader.getController();

        tempController.addAppointmentText.setText("Add Appointment");
        tempController.appointmentId.setText(String.valueOf(databaseHandler.getNextAppointmentId()));

        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }

        /**
         * this lambda expression simplifies a for loop to one line, improving efficiency of the code.
         */
        minuteOptionsList.forEach(i -> minutes.add(i));

        tempController.startMinute.setItems(minutes);
        tempController.endMinute.setItems(minutes);

        tempController.appointmentCustomer.getItems().addAll(customerIdList);

        for (int i = 0; i < 4; i++) {
            tempController.appointmentContact.getItems().addAll(databaseHandler.getContactName(String.valueOf(i)));
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * the lambda expression here simplifies a for loop to one line, improving efficiency
     *
     * handles modify appointment button and logic
     * @param actionEvent
     * @throws IOException
     * @throws ParseException
     * @throws SQLException
     */
    public void onModifyAppointment(ActionEvent actionEvent) throws IOException, ParseException, SQLException {
        List<String> customerIdList = databaseHandler.getCustomerIdList();

        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<Integer> minutes = FXCollections.observableArrayList();

        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (appointment != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/appointmentView.fxml"));
            Parent root = loader.load();

            appointmentController tempController = loader.getController();

            tempController.appointmentId.setText(appointment.getAppointmentId());

            tempController.addAppointmentText.setText("Modify Appointment");
            tempController.addAppointmentText.setX(-13.5);
            tempController.appointmentTitle.setText(appointment.getAppointmentTitle());
            tempController.appointmentDescription.setText(appointment.getAppointmentDesc());
            tempController.appointmentContact.setValue(appointment.getAppointmentContactName());
            tempController.appointmentLocation.setText(appointment.getAppointmentLocation());
            tempController.appointmentType.setText(appointment.getAppointmentType());
            tempController.appointmentCustomer.setValue(appointment.getAppointmentCustomerId());

            // DATE SETTER

            String tempStartDate = Region.dateFormatter(appointment.getAppointmentStart(), "MM-dd-yyyy", "yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate startDate = LocalDate.parse(tempStartDate, startFormatter);

            String tempEndDate = Region.dateFormatter(appointment.getAppointmentEnd(), "MM-dd-yyyy", "yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter endFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate endDate = LocalDate.parse(tempEndDate, endFormatter);

            tempController.startDate.setValue(startDate);
            tempController.endDate.setValue(endDate);

            // TIME SETTER

            String tempStartHour = Region.dateFormatter(appointment.getAppointmentStart(), "HH", "yyyy-MM-dd HH:mm:ss");
            String tempStartMinute = Region.dateFormatter(appointment.getAppointmentStart(), "mm", "yyyy-MM-dd HH:mm:ss");
            String tempEndHour = Region.dateFormatter(appointment.getAppointmentEnd(), "HH", "yyyy-MM-dd HH:mm:ss");
            String tempEndMinute = Region.dateFormatter(appointment.getAppointmentEnd(), "mm", "yyyy-MM-dd HH:mm:ss");

            tempController.startHourField.setText(tempStartHour);
            tempController.startMinute.setValue(tempStartMinute);
            tempController.endHourField.setText(tempEndHour);
            tempController.endMinute.setValue(tempEndMinute);

            for (int i = 0; i < 24; i++) {
                hours.add(i);
            }

            /**
             * this lambda expression simplifies a for loop to one line, improving efficiency of the code.
             */
            minuteOptionsList.forEach(i -> minutes.add(i));

            tempController.startMinute.setItems(minutes);
            tempController.endMinute.setItems(minutes);

            tempController.appointmentCustomer.getItems().addAll(customerIdList);

            for (int i = 0; i < 4; i++) {
                tempController.appointmentContact.getItems().addAll(databaseHandler.getContactName(String.valueOf(i)));
            }

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select an appointment to modify!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

    }

    /**
     * handles delete appointment button and logic
     * @param actionEvent
     * @throws IOException
     */
    public void onDeleteAppointment(ActionEvent actionEvent) throws IOException {
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (appointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete appointment with ID " + appointment.getAppointmentId() + " and Type: " + appointment.getAppointmentType() +"?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                System.out.println(appointment + " deleted.");
                Database.deleteAppointment(Integer.parseInt(appointment.getAppointmentId()));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/appointmentsTableView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Customer Table");
                stage.setScene(scene);
                stage.show();
            } else {
                alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select an appointment to delete!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
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

    /**
     * handles last button event and logic for time sifting (looking at appointments by week/month)
     * @param actionEvent
     * @throws SQLException
     */
    public void onLastButton(ActionEvent actionEvent) throws SQLException {
        if (allRadio.isSelected()) {

        } else if (monthlyRadio.isSelected()) {
            lastButton.setDisable(false);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(placeholderDate);
            System.out.println(calendar.getTime());

            calendar.add(Calendar.HOUR, -740);

            Date monthFromPlaceholderDate = calendar.getTime();
            System.out.println(monthFromPlaceholderDate);

            appointmentTable.getItems().clear();
            appointmentTable.getItems().addAll(databaseHandler.getAppointmentInRange(dateFormat.format(monthFromPlaceholderDate), dateFormat.format(placeholderDate)));

            fromDate.setText(String.valueOf(placeholderDate));
            toDate.setText(String.valueOf(monthFromPlaceholderDate));

            placeholderDate = monthFromPlaceholderDate;

        } else {
            lastButton.setDisable(false);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(placeholderDate);
            System.out.println(calendar.getTime());

            calendar.add(Calendar.HOUR, -168);

            Date weekFromPlaceholderDate = calendar.getTime();
            System.out.println(weekFromPlaceholderDate);

            appointmentTable.getItems().clear();
            appointmentTable.getItems().addAll(databaseHandler.getAppointmentInRange(dateFormat.format(weekFromPlaceholderDate), dateFormat.format(placeholderDate)));

            fromDate.setText(String.valueOf(placeholderDate));
            toDate.setText(String.valueOf(weekFromPlaceholderDate));

            placeholderDate = weekFromPlaceholderDate;

        }
    }

    /**
     * handles next button event and logic for time sifting (looking at appointments by week/month)
     * @param actionEvent
     * @throws SQLException
     */
    public void onNextButton(ActionEvent actionEvent) throws SQLException {
        if (allRadio.isSelected()) {

        } else if (monthlyRadio.isSelected()) {
            nextButton.setDisable(false);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(placeholderDate);
            System.out.println(calendar.getTime());

            calendar.add(Calendar.HOUR, 740);

            Date monthFromPlaceholderDate = calendar.getTime();
            System.out.println(monthFromPlaceholderDate);

            appointmentTable.getItems().clear();
            appointmentTable.getItems().addAll(databaseHandler.getAppointmentInRange(dateFormat.format(placeholderDate), dateFormat.format(monthFromPlaceholderDate)));

            fromDate.setText(String.valueOf(placeholderDate));
            toDate.setText(String.valueOf(monthFromPlaceholderDate));

            placeholderDate = monthFromPlaceholderDate;

        } else {
            nextButton.setDisable(false);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(placeholderDate);
            System.out.println(calendar.getTime());

            calendar.add(Calendar.HOUR, 168);
            
            Date weekFromPlaceholderDate = calendar.getTime();
            System.out.println(weekFromPlaceholderDate);

            appointmentTable.getItems().clear();
            appointmentTable.getItems().addAll(databaseHandler.getAppointmentInRange(dateFormat.format(placeholderDate), dateFormat.format(weekFromPlaceholderDate)));

            fromDate.setText(String.valueOf(placeholderDate));
            toDate.setText(String.valueOf(weekFromPlaceholderDate));

            placeholderDate = weekFromPlaceholderDate;
            
        }
    }
}


