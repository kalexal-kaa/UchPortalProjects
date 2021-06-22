package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;

public class Splash extends Application{
    public Splash() {
        super();
    }

    public static void main(final String[] args) {
        SplashScreen splash = SplashScreen.getSplashScreen();
        try {
            Thread.sleep(3000L);
        }
        catch (InterruptedException ex) {
            ex.getMessage();
        }
        if (splash != null) {
            splash.close();
            Application.launch(Main.class, args);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
