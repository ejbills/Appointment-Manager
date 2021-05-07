package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Ethan Bills
 */
public class Appointment {

    private final StringProperty appointmentId = new SimpleStringProperty(this, "appointmentId");
    public StringProperty appointmentIdProperty() {
        return appointmentId;
    }

    /**
     *
     * @return appointmentId
     */
    public final String getAppointmentId() {
        return appointmentIdProperty().get();
    }

    /**
     * set appointmentId
     * @param appointmentId
     */
    public final void setAppointmentId(String appointmentId) {
        appointmentIdProperty().set(appointmentId);
    }

    private final StringProperty appointmentTitle = new SimpleStringProperty(this, "appointmentTitle");
    public StringProperty appointmentTitleProperty() {
        return appointmentTitle;
    }

    /**
     *
     * @return appointmentTitle
     */
    public final String getAppointmentTitle() {
        return appointmentTitleProperty().get();
    }

    /**
     * set appointmentTitle
     * @param appointmentTitle
     */
    public final void setAppointmentTitle(String appointmentTitle) {
        appointmentTitleProperty().set(appointmentTitle);
    }

    private final StringProperty appointmentDesc = new SimpleStringProperty(this, "appointmentDesc");
    public StringProperty appointmentDescProperty() {
        return appointmentDesc;
    }

    /**
     *
     * @return appointmentDesc
     */
    public final String getAppointmentDesc() {
        return appointmentDescProperty().get();
    }

    /**
     * set appointmentDesc
     * @param appointmentDesc
     */
    public final void setAppointmentDesc(String appointmentDesc) {
        appointmentDescProperty().set(appointmentDesc);
    }

    private final StringProperty appointmentContactName = new SimpleStringProperty(this, "appointmentContactName");
    public StringProperty appointmentContactNameProperty() {
        return appointmentContactName;
    }

    /**
     *
     * @return appointmentContactName
     */
    public final String getAppointmentContactName() {
        return appointmentContactNameProperty().get();
    }

    /**
     * set appointmentContactName
     * @param appointmentContactName
     */
    public final void setAppointmentContactName(String appointmentContactName) {
        appointmentContactNameProperty().set(appointmentContactName);
    }

    private final StringProperty appointmentLocation = new SimpleStringProperty(this, "appointmentLocation");
    public StringProperty appointmentLocationProperty() {
        return appointmentLocation;
    }

    /**
     *
     * @return appointmentLocation
     */
    public final String getAppointmentLocation() {
        return appointmentLocationProperty().get();
    }

    /**
     * set appointmentLocation
     * @param appointmentLocation
     */
    public final void setAppointmentLocation(String appointmentLocation) {
        appointmentLocationProperty().set(appointmentLocation);
    }

    private final StringProperty appointmentType = new SimpleStringProperty(this, "appointmentType");
    public StringProperty appointmentTypeProperty() {
        return appointmentType;
    }

    /**
     *
     * @return appointmentType
     */
    public final String getAppointmentType() {
        return appointmentTypeProperty().get();
    }

    /**
     * set appointmentType
     * @param appointmentType
     */
    public final void setAppointmentType(String appointmentType) {
        appointmentTypeProperty().set(appointmentType);
    }

    private final StringProperty appointmentStart = new SimpleStringProperty(this, "appointmentStart");
    public StringProperty appointmentStartProperty() {
        return appointmentStart;
    }

    /**
     *
     * @return appointmentStart
     */
    public final String getAppointmentStart() {
        return appointmentStartProperty().get();
    }

    /**
     * set appointmentStart
     * @param appointmentStart
     */
    public final void setAppointmentStart(String appointmentStart) {
        appointmentStartProperty().set(appointmentStart);
    }

    private final StringProperty appointmentEnd = new SimpleStringProperty(this, "appointmentEnd");
    public StringProperty appointmentEndProperty() {
        return appointmentEnd;
    }

    /**
     *
     * @return appointmentEnd
     */
    public final String getAppointmentEnd() {
        return appointmentEndProperty().get();
    }

    /**
     * srt appointmentEnd
     * @param appointmentEnd
     */
    public final void setAppointmentEnd(String appointmentEnd) {
        appointmentEndProperty().set(appointmentEnd);
    }

    private final StringProperty appointmentCustomerId = new SimpleStringProperty(this, "appointmentCustomerId");
    public StringProperty appointmentCustomerIdProperty() {
        return appointmentCustomerId;
    }

    /**
     *
     * @return appointmentCustomerId
     */
    public final String getAppointmentCustomerId() {
        return appointmentCustomerIdProperty().get();
    }

    /**
     * set appointmentCustomerId
     * @param appointmentCustomerId
     */
    public final void setAppointmentCustomerId(String appointmentCustomerId) {
        appointmentCustomerIdProperty().set(appointmentCustomerId);
    }

    public Appointment() {}

    /**
     * builds appointment class
     * @param appointmentId appointment id
     * @param appointmentTitle appointment title
     * @param appointmentDesc appointment description
     * @param contactName contact's name
     * @param appointmentLocation appointment location
     * @param appointmentType appointment type
     * @param appointmentStart appointment start date/time
     * @param appointmentEnd appointment end date/time
     * @param appointmentCustomerId appointment's attached customer id
     */
    public Appointment(String appointmentId, String appointmentTitle, String appointmentDesc,String contactName, String appointmentLocation, String appointmentType, String appointmentStart, String appointmentEnd, String appointmentCustomerId) {
        setAppointmentId(appointmentId);
        setAppointmentTitle(appointmentTitle);
        setAppointmentDesc(appointmentDesc);
        setAppointmentContactName(contactName);
        setAppointmentLocation(appointmentLocation);
        setAppointmentType(appointmentType);
        setAppointmentStart(appointmentStart);
        setAppointmentEnd(appointmentEnd);
        setAppointmentCustomerId(appointmentCustomerId);
    }

}
