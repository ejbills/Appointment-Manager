package model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * @author Ethan Bills
 */
public class Region {
    private static String languageString;

    /**
     * gets language via locale on PC
     * @return language
     */
    public static String getLanguage() {
        if (Locale.getDefault().getLanguage().contains("en")) {
            return languageString = "English";

        } else if (Locale.getDefault().getLanguage().contains("fr")) {
            return languageString = "French";

        } else if (!Locale.getDefault().getLanguage().contains("fr") && !Locale.getDefault().getLanguage().contains("en")){
            return languageString = "English";
        }

        return languageString;
    }

    /**
     * handles formatting of date to parsable format
     * @param dateFromJSON initial date from
     * @param expectedFormat format expected to be formatted to
     * @param oldFormat format found in initial date format
     * @return formatted date
     */
    public static String dateFormatter(String dateFromJSON, String expectedFormat, String oldFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date date = null;
        String convertedDate = null;

        try {
            date = dateFormat.parse(dateFromJSON);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
            convertedDate = simpleDateFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDate;
    }
}
