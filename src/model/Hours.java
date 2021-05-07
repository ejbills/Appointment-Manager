package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Ethan Bills
 */
public class Hours {
    public static boolean isOfficeHours;

    /**
     * handles logic for checking if appointment is within office hours
     * @param checkStartTime initial appointment start time (from)
     * @param checkEndTime initial appointment end time (to)
     * @return true or false if appointment is found or not within 15 mins
     */
    public static boolean isInOfficeHours(String checkStartTime, String checkEndTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");

        String tempStartDate = Region.dateFormatter(checkStartTime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(tempStartDate, dateTimeFormatter);

        String tempEndDate = Region.dateFormatter(checkEndTime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
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

        int appointmentStartHour = Integer.parseInt(hourFormatter.format(toStartDate));
        int appointmentEndHour = Integer.parseInt(hourFormatter.format(toEndDate));

        if (appointmentStartHour < 12 && appointmentStartHour > 2 || appointmentEndHour < 12 && appointmentEndHour > 2) {
            isOfficeHours = false;
        } else {
            isOfficeHours = true;
        }
        return isOfficeHours;
    }
}
