package ai.quod.challenge;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

    /*
    Returns an array of length 4 with components [y, M, d, h]
    Eg: 2019-08-01T00:00:00Z -> [2019,8,1,0]
     */
    private static int[] getUTCDateComponents(String dateString) {
        int[] dtComponents = new int[4];
        try {
            String[] components = dateString.split("T",0);
            String[] dateComponents = components[0].split("-", 0);
            String[] timeComponents = components[1].split(":", 0);
            dtComponents[0] = Integer.parseInt(dateComponents[0], 10);
            dtComponents[1] = Integer.parseInt(dateComponents[1], 10);
            dtComponents[2] = Integer.parseInt(dateComponents[2], 10);
            dtComponents[3] = Integer.parseInt(timeComponents[0], 10);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid Date Format. Make sure it is UTC YYYY-MM-DDThh:mm:ssZ");
        }
        if (dtComponents[1] > 12 || dtComponents[2] > getDaysInMonthYear(dtComponents[1], dtComponents[0]) || dtComponents[3] > 23) {
            throw new IllegalArgumentException("Invalid Date. Make sure you enter a valid calendar date.");
        }
        return dtComponents;
    }

    private static int getDaysInMonthYear(int month, int year) {
        return month == 2 ? (year % 4 == 0 ? 29 : 28) : (month <= 7 ? 30 + month%2 : 31 - month%2);
    }

    private static String getQueryString(int year, int month, int day, int startHour, int endHour) {
        String hourString = startHour == endHour ? Integer.toString(startHour) : String.format("{%d..%d}", startHour, endHour);
        return String.format("%d-%s%d-%s%d-%s", year, month < 10 ? "0":"", month, day < 10 ? "0":"", day, hourString);
    }

    public static List<String> getQueryStrings(String startDateUTC, String endDateUTC) {
        int[] startDtComponents = getUTCDateComponents(startDateUTC);
        int[] endDtComponents = getUTCDateComponents(endDateUTC);
        List<String> queryStrings = new ArrayList<String>();
        int y = startDtComponents[0];
        int m = startDtComponents[1];
        int d = startDtComponents[2];
        while (y <= endDtComponents[0]) {
            if (y > startDtComponents[0]) {
                m = 1;
            }
            while (m <= endDtComponents[1] || y < endDtComponents[0] && m <= 12) {
                if (y > startDtComponents[0] || m > startDtComponents[1]) {
                    d = 1;
                }
                while (d <= endDtComponents[2] ||
                        (m < endDtComponents[1] || y < endDtComponents[0]) && d <= getDaysInMonthYear(m, y)) {
                    if (d == startDtComponents[2] && d == endDtComponents[2] && m == endDtComponents[1]) {
                        queryStrings.add(getQueryString(y, m, d, startDtComponents[3], endDtComponents[3]));
                        break;
                    } else if (d == startDtComponents[2] && m == startDtComponents[1]) {
                        queryStrings.add(getQueryString(y, m, d, startDtComponents[3], 23));
                    } else if (d == endDtComponents[2] && m == endDtComponents[1]) {
                        queryStrings.add(getQueryString(y, m, d, 0, endDtComponents[3]));
                        break;
                    } else {
                        queryStrings.add(getQueryString(y, m, d, 0, 23));
                    }
                    d++;
                }
                m++;
            }
            y++;
        }
        return queryStrings;
    }
}
