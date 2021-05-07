package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TypeCountReport {

    private final StringProperty count = new SimpleStringProperty(this, "count");
    public StringProperty countProperty() {
        return count;
    }

    /**
     *
     * @return count
     */
    public final String getCount() {
        return countProperty().get();
    }

    /**
     * set count
     * @param count count
     */
    public final void setCount(String count) {
        countProperty().set(count);
    }

    private final StringProperty type = new SimpleStringProperty(this, "type");
    public StringProperty typeProperty() {
        return type;
    }

    /**
     *
     * @return type of apptmt
     */
    public final String getType() {
        return typeProperty().get();
    }

    /**
     * set type
     * @param type
     */
    public final void setType(String type) {
        typeProperty().set(type);
    }

    private final StringProperty yearMonth = new SimpleStringProperty(this, "yearMonth");
    public StringProperty yearMonthProperty() {
        return yearMonth;
    }

    /**
     *
     * @return year month
     */
    public final String getYearMonth() {
        return yearMonthProperty().get();
    }

    /**
     * set year month
     * @param yearMonth
     */
    public final void setYearMonth(String yearMonth) {
        yearMonthProperty().set(yearMonth);
    }

    public TypeCountReport() {}

    /**
     * type count report class
     * @param count count
     * @param type apptmt type
     * @param yearMonth year and month
     */
    public TypeCountReport(String count, String type, String yearMonth) {
        setCount(count);
        setType(type);
        setYearMonth(yearMonth);
    }

}
