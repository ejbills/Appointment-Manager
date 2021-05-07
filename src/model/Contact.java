package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Ethan Bills
 */
public class Contact {
    private final StringProperty contactId = new SimpleStringProperty(this, "customerId");
    public StringProperty contactIdProperty() {
        return contactId;
    }

    /**
     *
     * @return contactId
     */
    public final String getContactId() {
        return contactIdProperty().get();
    }

    /**
     * set contactId
     * @param contactId
     */
    public final void setContactId(String contactId) {
        contactIdProperty().set(contactId);
    }

    private final StringProperty contactName = new SimpleStringProperty(this, "contactName");
    public StringProperty contactNameProperty() {
        return contactName;
    }

    /**
     *
     * @return contactName
     */
    public final String getContactName() {
        return contactNameProperty().get();
    }

    /**
     * set contactName
     * @param contactName
     */
    public final void setContactName(String contactName) {
        contactNameProperty().set(contactName);
    }

    private final StringProperty contactEmail = new SimpleStringProperty(this, "contactEmail");
    public StringProperty contactEmailProperty() {
        return contactEmail;
    }

    /**
     *
     * @return contactEmail
     */
    public final String getContactEmail() {
        return contactEmailProperty().get();
    }

    /**
     * set contactEmail
     * @param contactEmail
     */
    public final void setContactEmail(String contactEmail) {
        contactEmailProperty().set(contactEmail);
    }

    public Contact() {}

    /**
     * builds contact class
     * @param contactId contact id
     * @param contactName contact name
     * @param contactEmail contact email
     */
    public Contact(String contactId, String contactName, String contactEmail) {
        setContactId(contactId);
        setContactName(contactName);
        setContactEmail(contactEmail);
    }
}
