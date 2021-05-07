package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List ;
import java.util.ArrayList ;
import java.util.TimeZone;

/**
 * @author Ethan Bills
 */
public class Database {
    public static final String databaseName = "WJ08jpE";
    public static final String URL = "jdbc:mysql://wgudb.ucertify.com/" + databaseName + "?useSSL=false";
    public static final String databaseUser = "U08jpE";
    public static final String databasePassword = "53689309778";

    private static Connection connection;

    /**
     * handles databse connection
     * @param dbURL database url
     * @param user username for database
     * @param password password for database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Database(String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection(URL, databaseUser, databasePassword);

        System.out.println("Connection Established");
    }

    /**
     * handles database connection shutdown
     * @throws SQLException
     */
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * gets list of customers
     * @return list of customers
     * @throws SQLException
     */
    public List<Customer> getCustomerList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT * FROM WJ08jpE.customers;");
        ) {
            List<Customer> customerList = new ArrayList<>();
            while (rs.next()) {
                String customerId = rs.getString("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String customerCreateDate = rs.getString("Create_Date");
                String customerCreatedBy = rs.getString("Created_By");
                String customerDivisionId = rs.getString("Division_ID");
                Customer customer = new Customer(customerId, customerName, customerAddress, customerPostalCode, customerPhone, customerCreateDate, customerCreatedBy, customerDivisionId);

                customerList.add(customer);
            }
            return customerList;
        }
    }

    /**
     * gets list of customer ID's
     * @return list of customer ID's
     * @throws SQLException
     */
    public List<String> getCustomerIdList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT * FROM WJ08jpE.customers WHERE Customer_ID");
        ) {
            List<String> customerIdList = new ArrayList<>();
            while (rs.next()) {
                String customerId = rs.getString("Customer_ID");

                customerIdList.add(customerId);
            }
            return customerIdList;
        }
    }

    /**
     * gets next customer id
     * @return customer id
     * @throws SQLException
     */
    public int getNextCustomerId() throws SQLException {
        int customerId = 0;

        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'customers'");
        ) {
            while (rs.next()) {
                int tempCustomerId = rs.getInt("AUTO_INCREMENT");

                customerId = tempCustomerId;
            }
        }
        return customerId;
    }

    /**
     * handles add customer function to database
     * @param customerId customer id
     * @param customerName customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone number
     * @param createDate create date
     * @param createdBy created by
     * @param divisionId customer division id
     * @throws SQLException
     */
    public static void addCustomer(int customerId, String customerName, String address, String postalCode, String phone, String createDate, String createdBy, Integer divisionId) throws SQLException {
        try {
            String saveString = "Insert into WJ08jpE.customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Division_ID) values(?,?,?,?,?,?,?,?);";
            PreparedStatement prepareStmt = connection.prepareStatement(saveString);

            prepareStmt.setString(1, String.valueOf(customerId));
            prepareStmt.setString(2, customerName);
            prepareStmt.setString(3, address);
            prepareStmt.setString(4, postalCode);
            prepareStmt.setString(5, phone);
            prepareStmt.setString(6, createDate);
            prepareStmt.setString(7, createdBy);
            prepareStmt.setString(8, String.valueOf(divisionId));

            prepareStmt.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    /**
     * handles update customer function to database
     * @param customerId customer id
     * @param customerName customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone number
     * @param createDate create date
     * @param createdBy created by
     * @param divisionId customer division id
     * @throws SQLException
     */
    public static void updateCustomer(int customerId, String customerName, String address, String postalCode, String phone, String createDate, String createdBy, Integer divisionId) throws SQLException {
        try {
            String updateString = "UPDATE WJ08jpE.customers SET Customer_Name = '" + customerName + "', Address = '" + address + "', Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = " + customerId;
            PreparedStatement prepareStmt = connection.prepareStatement(updateString);

            //prepareStmt.setString(1, String.valueOf(customerId));
            prepareStmt.setString(1, postalCode);
            prepareStmt.setString(2, phone);
            prepareStmt.setString(3, String.valueOf(divisionId));

            prepareStmt.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    /**
     * deletes customer from database
     * @param customerId customer id
     */
    public static void deleteCustomer(Integer customerId) {
        try {
            String deleteString = "DELETE FROM `WJ08jpE`.`appointments` WHERE (`Customer_ID` = ?);";
            String saveString = "DELETE FROM `WJ08jpE`.`customers` WHERE (`Customer_ID` = ?);";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteString);
            PreparedStatement saveStmt = connection.prepareStatement(saveString);

            deleteStmt.setString(1, String.valueOf(customerId));
            saveStmt.setString(1, String.valueOf(customerId));

            deleteStmt.execute();
            saveStmt.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            System.out.println(e);

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    /**
     * gets division id by division name
     * @param divisionName division name
     * @return division id
     */
    public static int getDivisionId(String divisionName) {
        int divisionId = 0;

        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from WJ08jpE.first_level_divisions where Division = " + divisionName);

            System.out.println(divisionName + " SEARCH QUERY");

            while (rs.next()) {
                int tempDivisionId = rs.getInt("Division_ID");

                divisionId = tempDivisionId;
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

        return divisionId;
    }

    /**
     * gets division name by division id
     * @param divisionId division id
     * @return division name
     */
    public static String getDivisionName(String divisionId) {
        String divisionName = "DIVISION";

        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select division from WJ08jpE.first_level_divisions where Division_ID = " + divisionId);

            System.out.println(divisionId + " SEARCH QUERY");

            while (rs.next()) {
                String tempDivision = rs.getString("Division");

                divisionName = tempDivision;
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

        return divisionName;
    }

    /**
     * gets country name by division id
     * @param divisionId division id
     * @return country name
     */
    public static String getCountryName(String divisionId) {
        String countryName = "COUNTRY";
        int countryId;

        try {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select Country_ID from WJ08jpE.first_level_divisions where Division_ID = " + divisionId);

                while (rs.next()) {
                    int tempCountryId = rs.getInt("Country_ID");

                    countryId = tempCountryId;

                    Statement statement = connection.createStatement();
                    ResultSet res = statement.executeQuery("select country from WJ08jpE.countries where Country_ID = " + countryId);

                    System.out.println(countryId + " SEARCH QUERY");

                    while (res.next()) {
                        String tempCountry = res.getString("Country");

                        countryName = tempCountry;
                        System.out.println("UNFORMATTED: " + countryName);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

        if (countryName.contentEquals("U.S")) {
            countryName = "United States";

        } else if (countryName.contentEquals("UK")) {
            countryName = "United Kingdom";

        } else {
            countryName = "Canada";

        }

        return countryName;
    }

    // BEGIN APPOINTMENT HANDLING

    /**
     * handles adding appointment to database
     * @param appointmentId appointment id
     * @param appointmentTitle appointment title
     * @param appointmentDesc appointment description
     * @param appointmentLocation appointment location
     * @param appointmentType appointment type
     * @param appointmentStart appointment start date
     * @param appointmentEnd appointment end date
     * @param appointmentCreatedBy appointment created by
     * @param appointmentLastUpdatedBy appointment last updated by
     * @param customerId customer id
     * @param userId user id
     * @param contactId contact id
     */
    public static void addAppointment(String appointmentId, String appointmentTitle, String appointmentDesc, String appointmentLocation, String appointmentType, String appointmentStart, String appointmentEnd, String appointmentCreatedBy, String appointmentLastUpdatedBy, String customerId, String userId, String contactId) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String tempStartDate = Region.dateFormatter(appointmentStart, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(tempStartDate, dateTimeFormatter);

            String tempEndDate = Region.dateFormatter(appointmentEnd, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            LocalDateTime endDate = LocalDateTime.parse(tempEndDate, dateTimeFormatter);

            Calendar now = Calendar.getInstance();
            TimeZone timeZone = now.getTimeZone();
            ZoneId fromTimeZone = ZoneId.of(timeZone.getID());
            System.out.println(fromTimeZone);

            ZoneId toTimeZone = ZoneId.of("UTC");

            ZonedDateTime fromStartDate = startDate.atZone(fromTimeZone);
            ZonedDateTime toStartDate = fromStartDate.withZoneSameInstant(toTimeZone);

            ZonedDateTime fromEndDate = endDate.atZone(fromTimeZone);
            ZonedDateTime toEndDate = fromEndDate.withZoneSameInstant(toTimeZone);

            String saveString = "Insert into WJ08jpE.appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) values(?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement prepareStmt = connection.prepareStatement(saveString);

            prepareStmt.setString(1, appointmentId);
            prepareStmt.setString(2, appointmentTitle);
            prepareStmt.setString(3, appointmentDesc);
            prepareStmt.setString(4, appointmentLocation);
            prepareStmt.setString(5, appointmentType);
            prepareStmt.setString(6, dateTimeFormatter.format(toStartDate));
            prepareStmt.setString(7, dateTimeFormatter.format(toEndDate));
            prepareStmt.setString(8, appointmentCreatedBy);
            prepareStmt.setString(9, appointmentLastUpdatedBy);
            prepareStmt.setString(10, customerId);
            prepareStmt.setString(11, userId);
            prepareStmt.setString(12, contactId);

            prepareStmt.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            System.out.println(e);

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    /**
     * handles updating appointment in database
     * @param appointmentId appointment id
     * @param appointmentTitle appointment title
     * @param appointmentDesc appointment description
     * @param appointmentLocation appointment location
     * @param appointmentType appointment type
     * @param appointmentStart appointment start date
     * @param appointmentEnd appointment end date
     * @param customerId customer id
     * @param contactId contact id
     */
    public static void updateAppointment(String appointmentId, String appointmentTitle, String appointmentDesc, String appointmentLocation, String appointmentType, String appointmentStart, String appointmentEnd, String customerId, String contactId) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String tempStartDate = Region.dateFormatter(appointmentStart, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(tempStartDate, dateTimeFormatter);

            String tempEndDate = Region.dateFormatter(appointmentEnd, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            LocalDateTime endDate = LocalDateTime.parse(tempEndDate, dateTimeFormatter);

            Calendar now = Calendar.getInstance();
            TimeZone timeZone = now.getTimeZone();
            ZoneId fromTimeZone = ZoneId.of(timeZone.getID());
            System.out.println(fromTimeZone);

            ZoneId toTimeZone = ZoneId.of("UTC");

            ZonedDateTime fromStartDate = startDate.atZone(fromTimeZone);
            ZonedDateTime toStartDate = fromStartDate.withZoneSameInstant(toTimeZone);

            ZonedDateTime fromEndDate = endDate.atZone(fromTimeZone);
            ZonedDateTime toEndDate = fromEndDate.withZoneSameInstant(toTimeZone);

            String updateString = "UPDATE WJ08jpE.appointments SET Title = '" + appointmentTitle + "', Description = '" + appointmentDesc + "', Location = '" + appointmentLocation + "', Type = '" + appointmentType + "', Start = '" + dateTimeFormatter.format(toStartDate) + "', End = '" + dateTimeFormatter.format(toEndDate) + "', Created_By = 'Application', Last_Updated_By = 'Application', Customer_ID = '" + customerId + "', User_ID = '1', Contact_ID = '" + contactId + "' WHERE Appointment_ID = " + appointmentId;
            PreparedStatement prepareStmt = connection.prepareStatement(updateString);

            prepareStmt.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            System.out.println(e);

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    /**
     * gets next appointment id
     * @return appointment id
     * @throws SQLException
     */
    public int getNextAppointmentId() throws SQLException {
        int appointmentId = 0;

        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'appointments'");
        ) {
            while (rs.next()) {
                int tempAppointmentId = rs.getInt("AUTO_INCREMENT");

                appointmentId = tempAppointmentId;
            }
        }
        return appointmentId;
    }

    /**
     * gets list of appointments
     * @return appointment list
     * @throws SQLException
     */
    public List<Appointment> getAppointmentList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT * FROM WJ08jpE.appointments;");
        ) {
            List<Appointment> appointmentList = new ArrayList<>();
            while (rs.next()) {
                String appointmentId = rs.getString("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDesc = rs.getString("Description");
                String tempContactId = rs.getString("Contact_ID");
                String contactName = Database.getContactName(tempContactId);
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");

                String appointmentStart = rs.getString("Start");
                String appointmentEnd = rs.getString("End");

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String tempStartDate = Region.dateFormatter(appointmentStart, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(tempStartDate, dateTimeFormatter);

                String tempEndDate = Region.dateFormatter(appointmentEnd, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime endDate = LocalDateTime.parse(tempEndDate, dateTimeFormatter);

                Calendar now = Calendar.getInstance();
                TimeZone timeZone = now.getTimeZone();
                ZoneId localZone = ZoneId.of(timeZone.getID());
                System.out.println(localZone);

                ZoneId fromTimeZone = ZoneId.of("UTC");

                ZonedDateTime fromStartDate = startDate.atZone(fromTimeZone);
                ZonedDateTime toStartDate = fromStartDate.withZoneSameInstant(localZone);

                ZonedDateTime fromEndDate = endDate.atZone(fromTimeZone);
                ZonedDateTime toEndDate = fromEndDate.withZoneSameInstant(localZone);

                String appointmentCustomerId = rs.getString("Customer_ID");
                Appointment appointment = new Appointment(appointmentId, appointmentTitle, appointmentDesc, contactName, appointmentLocation, appointmentType, dateTimeFormatter.format(toStartDate), dateTimeFormatter.format(toEndDate), appointmentCustomerId);

                appointmentList.add(appointment);
            }
            return appointmentList;
        }
    }

    /**
     * handles deleting appointment in database by appointment id
     * @param appointmentId appointment id
     */
    public static void deleteAppointment(Integer appointmentId) {
        try {
            String deleteString = "DELETE FROM `WJ08jpE`.`appointments` WHERE (`Appointment_ID` = ?);";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteString);

            deleteStmt.setString(1, String.valueOf(appointmentId));

            deleteStmt.execute();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            System.out.println(e);

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    // BEGIN CONTACT HANDLING

    /**
     * gets contact name by contact id
     * @param contactId contact id
     * @return contact name
     */
    public static String getContactName(String contactId) {
        String contactName = null;

        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select Contact_Name from WJ08jpE.contacts WHERE Contact_ID = " + contactId);

            while (rs.next()) {
                contactName = rs.getString("Contact_Name");

            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

        return contactName;
    }

    /**
     * gets contact id by contact name
     * @param contactName contact name
     * @return contact id
     */
    public static String getContactId(String contactName) {
        String contactId = null;

        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select Contact_ID from WJ08jpE.contacts WHERE Contact_Name = '" + contactName + "'");

            while (rs.next()) {
                contactId = rs.getString("Contact_ID");

            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK);
            alert.showAndWait();

            System.out.println(e);

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

        return contactId;
    }

    // BEGIN DATE HANDLING

    /**
     * handles logic for getting appointment in range of 2 dates
     * @param currentDate initial date (from)
     * @param futureDate final date (to)
     * @return list of appointments found in range
     * @throws SQLException
     */
    public static List<Appointment> getAppointmentInRange(String currentDate, String futureDate) throws SQLException {
        DateTimeFormatter localToUtcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String tempLocalStartDateTime = Region.dateFormatter(currentDate, "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
        LocalDateTime localStartDateTime = LocalDateTime.parse(tempLocalStartDateTime, localToUtcFormatter);

        String tempLocalEndDateTime = Region.dateFormatter(futureDate, "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
        LocalDateTime localEndDateTime = LocalDateTime.parse(tempLocalEndDateTime, localToUtcFormatter);

        Calendar localNow = Calendar.getInstance();
        TimeZone localTimeZone = localNow.getTimeZone();
        ZoneId localZoneId = ZoneId.of(localTimeZone.getID());
        System.out.println(localZoneId);

        ZoneId toUtcTimeZone = ZoneId.of("UTC");

        ZonedDateTime fromLocalStartDate = localStartDateTime.atZone(localZoneId);
        ZonedDateTime toUtcStartDate = fromLocalStartDate.withZoneSameInstant(toUtcTimeZone);

        ZonedDateTime fromLocalEndDate = localEndDateTime.atZone(localZoneId);
        ZonedDateTime toUtcEndDate = fromLocalEndDate.withZoneSameInstant(toUtcTimeZone);

        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT * FROM WJ08jpE.appointments WHERE Start BETWEEN '" + toUtcStartDate + "' AND '" + toUtcEndDate + "';");
        ) {
            List<Appointment> appointmentList = new ArrayList<>();
            while (rs.next()) {
                String appointmentId = rs.getString("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDesc = rs.getString("Description");

                String tempContactId = rs.getString("Contact_ID");
                String contactName = getContactName(tempContactId);

                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");

                String appointmentStart = rs.getString("Start");
                String appointmentEnd = rs.getString("End");

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String tempStartDate = Region.dateFormatter(appointmentStart, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(tempStartDate, dateTimeFormatter);

                String tempEndDate = Region.dateFormatter(appointmentEnd, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime endDate = LocalDateTime.parse(tempEndDate, dateTimeFormatter);

                Calendar now = Calendar.getInstance();
                TimeZone timeZone = now.getTimeZone();
                ZoneId localZone = ZoneId.of(timeZone.getID());
                System.out.println(localZone);

                ZoneId fromTimeZone = ZoneId.of("UTC");

                ZonedDateTime fromStartDate = startDate.atZone(fromTimeZone);
                ZonedDateTime toStartDate = fromStartDate.withZoneSameInstant(localZone);

                ZonedDateTime fromEndDate = endDate.atZone(fromTimeZone);
                ZonedDateTime toEndDate = fromEndDate.withZoneSameInstant(localZone);


                String appointmentCustomerId = rs.getString("Customer_ID");
                Appointment appointment = new Appointment(appointmentId, appointmentTitle, appointmentDesc, contactName, appointmentLocation, appointmentType, dateTimeFormatter.format(toStartDate), dateTimeFormatter.format(toEndDate), appointmentCustomerId);

                appointmentList.add(appointment);
            }
            return appointmentList;
        }
    }

    /**
     * gets appointments that are overlapping in range VIA CUSTOMER ID
     * @param apptmtStartDate appointment start date (from)
     * @param apptmtEndDate appointment end date (to)
     * @param customerId customer id
     * @return list of overlapping appointments found
     * @throws SQLException
     */
    public static boolean overlappingAppointmentCheck(String apptmtStartDate, String apptmtEndDate, String customerId) throws SQLException {
        boolean overlappingAppointments;

        DateTimeFormatter localToUtcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String tempLocalStartDateTime = Region.dateFormatter(apptmtStartDate, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
        LocalDateTime localStartDateTime = LocalDateTime.parse(tempLocalStartDateTime, localToUtcFormatter);

        String tempLocalEndDateTime = Region.dateFormatter(apptmtEndDate, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
        LocalDateTime localEndDateTime = LocalDateTime.parse(tempLocalEndDateTime, localToUtcFormatter);

        Calendar localNow = Calendar.getInstance();
        TimeZone localTimeZone = localNow.getTimeZone();
        ZoneId localZoneId = ZoneId.of(localTimeZone.getID());
        System.out.println(localZoneId);

        ZoneId toUtcTimeZone = ZoneId.of("UTC");

        ZonedDateTime fromLocalStartDate = localStartDateTime.atZone(localZoneId);
        ZonedDateTime toUtcStartDate = fromLocalStartDate.withZoneSameInstant(toUtcTimeZone);

        ZonedDateTime fromLocalEndDate = localEndDateTime.atZone(localZoneId);
        ZonedDateTime toUtcEndDate = fromLocalEndDate.withZoneSameInstant(toUtcTimeZone);

        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT c.Customer_ID, a.* FROM WJ08jpE.appointments a INNER JOIN WJ08jpE.customers c ON a.Customer_ID = c.Customer_ID WHERE a.Start AND a.End BETWEEN '" + toUtcStartDate + "' AND '" + toUtcEndDate + "' AND c.Customer_ID = '" + customerId + "' ;");
        ) {
            List<Appointment> appointmentList = new ArrayList<>();
            while (rs.next()) {
                String appointmentId = rs.getString("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDesc = rs.getString("Description");

                String tempContactId = rs.getString("Contact_ID");
                String contactName = getContactName(tempContactId);

                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");

                String appointmentStart = rs.getString("Start");
                String appointmentEnd = rs.getString("End");

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String tempStartDate = Region.dateFormatter(appointmentStart, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(tempStartDate, dateTimeFormatter);

                String tempEndDate = Region.dateFormatter(appointmentEnd, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime endDate = LocalDateTime.parse(tempEndDate, dateTimeFormatter);

                Calendar now = Calendar.getInstance();
                TimeZone timeZone = now.getTimeZone();
                ZoneId localZone = ZoneId.of(timeZone.getID());
                System.out.println(localZone);

                ZoneId fromTimeZone = ZoneId.of("UTC");

                ZonedDateTime fromStartDate = startDate.atZone(fromTimeZone);
                ZonedDateTime toStartDate = fromStartDate.withZoneSameInstant(localZone);

                ZonedDateTime fromEndDate = endDate.atZone(fromTimeZone);
                ZonedDateTime toEndDate = fromEndDate.withZoneSameInstant(localZone);


                String appointmentCustomerId = rs.getString("Customer_ID");
                Appointment appointment = new Appointment(appointmentId, appointmentTitle, appointmentDesc, contactName, appointmentLocation, appointmentType, dateTimeFormatter.format(toStartDate), dateTimeFormatter.format(toEndDate), appointmentCustomerId);

                appointmentList.add(appointment);

            }

            if (appointmentList.isEmpty()) {
                overlappingAppointments = false;
            } else {
                overlappingAppointments = true;
            }

            return overlappingAppointments;
        }
    }

    /**
     * gets appointments in the next 15 mins
     * @param currentDate current date (from)
     * @param futureDate 15 mins in future date (to)
     * @return appointment found in 15 mins
     * @throws SQLException
     */
    public static String fifteenMinuteAppointmentCheck(String currentDate, String futureDate) throws SQLException {
        String upcomingAppointmentInfo = null;

        DateTimeFormatter localToUtcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String tempLocalStartDateTime = Region.dateFormatter(currentDate, "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
        LocalDateTime localStartDateTime = LocalDateTime.parse(tempLocalStartDateTime, localToUtcFormatter);

        String tempLocalEndDateTime = Region.dateFormatter(futureDate, "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
        LocalDateTime localEndDateTime = LocalDateTime.parse(tempLocalEndDateTime, localToUtcFormatter);

        Calendar localNow = Calendar.getInstance();
        TimeZone localTimeZone = localNow.getTimeZone();
        ZoneId localZoneId = ZoneId.of(localTimeZone.getID());
        System.out.println(localZoneId);

        ZoneId toUtcTimeZone = ZoneId.of("UTC");

        ZonedDateTime fromLocalStartDate = localStartDateTime.atZone(localZoneId);
        ZonedDateTime toUtcStartDate = fromLocalStartDate.withZoneSameInstant(toUtcTimeZone);

        ZonedDateTime fromLocalEndDate = localEndDateTime.atZone(localZoneId);
        ZonedDateTime toUtcEndDate = fromLocalEndDate.withZoneSameInstant(toUtcTimeZone);

        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT * FROM WJ08jpE.appointments WHERE Start BETWEEN '" + toUtcStartDate + "' AND '" + toUtcEndDate + "';");
        ) {
            while (rs.next()) {
                String appointmentId = rs.getString("Appointment_ID");
                String appointmentStart = rs.getString("Start");
                String appointmentEnd = rs.getString("End");

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String tempStartDate = Region.dateFormatter(appointmentStart, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(tempStartDate, dateTimeFormatter);

                String tempEndDate = Region.dateFormatter(appointmentEnd, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime endDate = LocalDateTime.parse(tempEndDate, dateTimeFormatter);

                Calendar now = Calendar.getInstance();
                TimeZone timeZone = now.getTimeZone();
                ZoneId localZone = ZoneId.of(timeZone.getID());
                System.out.println(localZone);

                ZoneId fromTimeZone = ZoneId.of("UTC");

                ZonedDateTime fromStartDate = startDate.atZone(fromTimeZone);
                ZonedDateTime toStartDate = fromStartDate.withZoneSameInstant(localZone);

                ZonedDateTime fromEndDate = endDate.atZone(fromTimeZone);
                ZonedDateTime toEndDate = fromEndDate.withZoneSameInstant(localZone);

                upcomingAppointmentInfo = "Appointment ID: " + appointmentId + "\n" + "Start: " + dateTimeFormatter.format(toStartDate) + "\n" + "End: " + dateTimeFormatter.format(toEndDate);

            }

            return upcomingAppointmentInfo;
        }
    }

    // BEGIN REPORT HANDLING

    /**
     * gets list of appointments by type and date
     * @return list of appointments found
     * @throws SQLException
     */
    public static List<TypeCountReport> typeReport() throws SQLException {
        List<TypeCountReport> typeReportList = new ArrayList<>();

        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select count(appointments.Type), Type, DATE_FORMAT(Start, '%Y %M') AS YearMonth FROM WJ08jpE.appointments GROUP BY Type, YearMonth");
        ) {
            while (rs.next()) {
                String count = rs.getString("count(appointments.type)");
                String type = rs.getString("Type");
                String yearMonth = rs.getString("YearMonth");

                TypeCountReport typeMonth = new TypeCountReport(count, type, yearMonth);

                typeReportList.add(typeMonth);

            }

            return typeReportList;
        }
    }

    /**
     * gets list of contacts for contact schedule report by contact id
     * @param contactId contact id
     * @return list of contacts and their schedule
     * @throws SQLException
     */
    public static List<Appointment> getContactReport(String contactId) throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT * FROM WJ08jpE.appointments WHERE Contact_ID = '" + contactId + "';");
        ) {
            List<Appointment> appointmentList = new ArrayList<>();
            while (rs.next()) {
                String appointmentId = rs.getString("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDesc = rs.getString("Description");

                String tempContactId = rs.getString("Contact_ID");
                String contactName = getContactName(tempContactId);

                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");

                String appointmentStart = rs.getString("Start");
                String appointmentEnd = rs.getString("End");

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String tempStartDate = Region.dateFormatter(appointmentStart, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(tempStartDate, dateTimeFormatter);

                String tempEndDate = Region.dateFormatter(appointmentEnd, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                LocalDateTime endDate = LocalDateTime.parse(tempEndDate, dateTimeFormatter);

                Calendar now = Calendar.getInstance();
                TimeZone timeZone = now.getTimeZone();
                ZoneId localZone = ZoneId.of(timeZone.getID());
                System.out.println(localZone);

                ZoneId fromTimeZone = ZoneId.of("UTC");

                ZonedDateTime fromStartDate = startDate.atZone(fromTimeZone);
                ZonedDateTime toStartDate = fromStartDate.withZoneSameInstant(localZone);

                ZonedDateTime fromEndDate = endDate.atZone(fromTimeZone);
                ZonedDateTime toEndDate = fromEndDate.withZoneSameInstant(localZone);

                String appointmentCustomerId = rs.getString("Customer_ID");
                Appointment appointment = new Appointment(appointmentId, appointmentTitle, appointmentDesc, contactName, appointmentLocation, appointmentType, dateTimeFormatter.format(toStartDate), dateTimeFormatter.format(toEndDate), appointmentCustomerId);

                appointmentList.add(appointment);
            }
            return appointmentList;
        }
    }

    /**
     * gets report of customers and amount of appointments for each
     * @return list of appointments for each customer id if > 1
     * @throws SQLException
     */
    public static List<CustomerReport> customerReport() throws SQLException {
        List<CustomerReport> customerReportList = new ArrayList<>();

        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select Customer_ID, count(appointments.Appointment_ID)  FROM WJ08jpE.appointments GROUP BY Customer_ID");
        ) {
            while (rs.next()) {
                String count = rs.getString("count(appointments.Appointment_ID)");
                String customerId = rs.getString("Customer_ID");

                CustomerReport customer = new CustomerReport(count, customerId);

                customerReportList.add(customer);

            }

            return customerReportList;
        }
    }
}