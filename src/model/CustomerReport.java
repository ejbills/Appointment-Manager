package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerReport {
    private final StringProperty count = new SimpleStringProperty(this, "count");
    public StringProperty countProperty() {
        return count;
    }

    /**
     * get count
     * @return count
     */
    public final String getCount() {
        return countProperty().get();
    }

    /**
     * set count
     * @param count
     */
    public final void setCount(String count) {
        countProperty().set(count);
    }

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

    public CustomerReport() {}

    /**
     *
     * @param count count
     * @param customerId count customer id
     */
    public CustomerReport(String count, String customerId) {
        setCount(count);
        setCustomerId(customerId);
    }
}
