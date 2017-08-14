package br.com.util;

import br.com.models.User;
import java.util.Calendar;
import java.util.Date;

public class UserUtil {

    public static User userON;
    public static int MAX_LOGGED_TIME = 15000000;
    public static Calendar timer = Calendar.getInstance();
    public static boolean keepOn;

    public static boolean isKeepLogged() {
        return keepOn;
    }

    public static void setKeepOn(boolean keepOnp) {
        keepOn = keepOnp;
    }

    public static Calendar getTimer() {
        return timer;
    }

    public static void updateLastLoginInstant() {
        timer.setTime(new Date());
    }

    public static User getLoggedUser() {
        return userON;
    }

    public static void setUserON(User userONp) {
        userON = userONp;
    }

    public static void logout() {
        userON = null;
        updateLastLoginInstant();
    }
}
