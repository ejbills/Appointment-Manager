package model;

import controller.loginController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ethan Bills
 */
public class Login {
    public static boolean loginGranted;

    /**
     * checks if user and password is correct
     * @param attemptUsername username entered
     * @param attemptPassword password entered
     * @return true or false for if user entered right username or password
     */
    public static boolean loginSuccessCheck(String attemptUsername, String attemptPassword) {
        if (attemptUsername.contentEquals("test") && attemptPassword.contentEquals("test")) {
            return loginGranted = true;
        } else {
            return loginGranted = false;
        }
    }

    /**
     * handles attemp of login
     * @param attemptUsername username entered
     * @param attemptPassword password entered
     */
    public static void attemptLogin(String attemptUsername, String attemptPassword) {
        try {
            File loginActivityFile = new File("login_activity.txt");

            if (!loginActivityFile.exists()) {
                loginActivityFile.createNewFile();
            }

            PrintWriter loginActivity = new PrintWriter(new FileWriter(loginActivityFile, true));

            loginActivityFile.canWrite();

            if (loginGranted) {
                loginActivity.println("Sign in successful.");
            } else {
                loginActivity.println("Sign in unsuccessful.");
            }

            loginActivity.println(attemptUsername);
            loginActivity.println(attemptPassword);
            loginActivity.println(java.time.LocalDateTime.now() + "\n");
            loginActivity.close();

            if (loginSuccessCheck(attemptUsername, attemptPassword)) {
                loginActivity.write("Login successful");
                System.out.println("GRANTED");
            } else {
                loginActivity.write("Login unsuccessful");
                System.out.println("DENIED");
            }

            System.out.println("USER AND PASS ENTERED: " + attemptUsername + " " + attemptPassword);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}