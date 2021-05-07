package model;

import javafx.beans.property.StringProperty ;
import javafx.beans.property.SimpleStringProperty ;

/**
 * @author Ethan Bills
 */
public class Customer {

    private final StringProperty customerId = new SimpleStringProperty(this, "customerId");
    public StringProperty customerIdProperty() {
        return customerId;
    }

    /**
     *
     * @return customerId
     */
    public final String getCustomerId() {
        return customerIdProperty().get();
    }

    /**
     * set customerId
     * @param customerId
     */
    public final void setCustomerId(String customerId) {
        customerIdProperty().set(customerId);
    }

    private final StringProperty customerName = new SimpleStringProperty(this, "customerName");
    public StringProperty customerNameProperty() {
        return customerName;
    }

    /**
     *
     * @return customerName
     */
    public final String getCustomerName() {
        return customerNameProperty().get();
    }

    /**
     * set customerName
     * @param customerName
     */
    public final void setCustomerName(String customerName) {
        customerNameProperty().set(customerName);
    }

    private final StringProperty customerAddress = new SimpleStringProperty(this, "customerAddress");
    public StringProperty customerAddressProperty() {
        return customerAddress;
    }

    /**
     *
     * @return customerAddress
     */
    public final String getCustomerAddress() {
        return customerAddressProperty().get();
    }

    /**
     * set customerAddress
     * @param customerAddress
     */
    public final void setCustomerAddress(String customerAddress) {
        customerAddressProperty().set(customerAddress);
    }

    private final StringProperty postalCode = new SimpleStringProperty(this, "postalCode");
    public StringProperty customerPostalCode() {
        return postalCode;
    }

    /**
     *
     * @return postalCode
     */
    public final String getPostalCode() {
        return customerPostalCode().get();
    }

    /**
     * set postalCode
     * @param postalCode
     */
    public final void setPostalCode(String postalCode) {
        customerPostalCode().set(postalCode);
    }

    private final StringProperty customerPhone = new SimpleStringProperty(this, "customerPhone");
    public StringProperty customerPhoneProperty() {
        return customerPhone;
    }

    /**
     *
     * @return customerPhone
     */
    public final String getCustomerPhone() {
        return customerPhoneProperty().get();
    }

    /**
     * set customerPhone
     * @param customerPhone
     */
    public final void setCustomerPhone(String customerPhone) {
        customerPhoneProperty().set(customerPhone);
    }

    private final StringProperty customerCreateDate = new SimpleStringProperty(this, "customerCreateDate");
    public StringProperty customerCreateDateProperty() {
        return customerCreateDate;
    }

    /**
     *
     * @return customerCreateDate
     */
    public final String getCustomerCreateDate() {
        return customerCreateDateProperty().get();
    }

    /**
     * set customerCreateDate
     * @param customerCreateDate
     */
    public final void setCustomerCreateDate(String customerCreateDate) {
        customerCreateDateProperty().set(customerCreateDate);
    }

    private final StringProperty customerCreatedBy = new SimpleStringProperty(this, "customerCreatedBy");
    public StringProperty customerCreatedByProperty() {
        return customerCreatedBy;
    }

    /**
     *
     * @return customerCreatedBy
     */
    public final String getCustomerCreatedBy() {
        return customerCreatedByProperty().get();
    }

    /**
     * set customerCreatedBy
     * @param customerCreatedBy
     */
    public final void setCustomerCreatedBy(String customerCreatedBy) {
        customerCreatedByProperty().set(customerCreatedBy);
    }

    private final StringProperty customerDivisionId = new SimpleStringProperty(this, "customerDivisionId");
    public StringProperty customerDivisionIdProperty() {
        return customerDivisionId;
    }

    /**
     *
     * @return customerDivisionId
     */
    public final String getCustomerDivisionId() {
        return customerDivisionIdProperty().get();
    }

    /**
     * set customerDivisionId
     * @param customerDivisionId
     */
    public final void setCustomerDivisionId(String customerDivisionId) {
        customerDivisionIdProperty().set(customerDivisionId);
    }

    public Customer() {}

    /**
     * builds customer class
     * @param customerId customer id
     * @param customerName customer name
     * @param customerAddress customer address
     * @param postalCode customer postal code
     * @param customerPhone customer phone number
     * @param customerCreateDate customer created date
     * @param customerCreatedBy customer created by
     * @param customerDivisionId customer's associated division id
     */
    public Customer(String customerId, String customerName, String customerAddress, String postalCode, String customerPhone, String customerCreateDate, String customerCreatedBy, String customerDivisionId) {
        setCustomerId(customerId);
        setCustomerName(customerName);
        setCustomerAddress(customerAddress);
        setPostalCode(postalCode);
        setCustomerPhone(customerPhone);
        setCustomerCreateDate(customerCreateDate);
        setCustomerCreatedBy(customerCreatedBy);
        setCustomerDivisionId(customerDivisionId);
    }
}
