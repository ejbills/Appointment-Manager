package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Ethan Bills
 */
public class Division {

    private final StringProperty divisionName = new SimpleStringProperty(this, "divisionName");
    public StringProperty divisonNameProperty() {
        return divisionName;
    }

    /**
     *
     * @return divisionName
     */
    public final String getDivisionName() {
        return divisonNameProperty().get();
    }

    /**
     * set divisionName
     * @param divisionName
     */
    public final void setDivisonName(String divisionName) {
        divisonNameProperty().set(divisionName);
    }

    private final StringProperty divisionId = new SimpleStringProperty(this, "divisionId");
    public StringProperty divisionIdProperty() {
        return divisionId;
    }

    /**
     *
     * @return divisionId
     */
    public final String getDivisionId() {
        return divisionIdProperty().get();
    }

    /**
     * set divisionId
     * @param divisionId
     */
    public final void setDivisionId(String divisionId) {
        divisionIdProperty().set(divisionId);
    }

    public Division() {}

    public Division(String divisionName, String divisionId) {
        setDivisonName(divisionName);
        setDivisionId(divisionId);
    }
}
