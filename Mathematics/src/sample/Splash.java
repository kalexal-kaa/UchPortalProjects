package sample;

import javafx.application.Application;

import java.awt.*;

public class Splash {

    public Splash() {
        super();
    }

    public static void main(final String[] args) {
        SplashScreen splash = SplashScreen.getSplashScreen();
        try {
            Thread.sleep(5000L);
        }
        catch (InterruptedException ex) {
            ex.getMessage();
        }
        if (splash != null) {
            splash.close();
            Application.launch(Main.class, args);
        }
    }
}
