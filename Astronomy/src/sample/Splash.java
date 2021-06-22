package sample;

import javafx.application.Application;

import java.awt.*;

public class Splash {
    public Splash() {
        super();
    }

    public static void main(final String[] args) {
        SplashScreen mySplash = SplashScreen.getSplashScreen();
        try {
            Thread.sleep(3000L);
        }
        catch (InterruptedException ex) {
            ex.getMessage();
        }
        if (mySplash != null) {
            mySplash.close();
            Application.launch(Main.class, args);
        }
    }
}
