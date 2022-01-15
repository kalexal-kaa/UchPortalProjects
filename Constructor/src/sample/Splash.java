package sample;

import java.awt.SplashScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Splash extends Application {

    public Splash() {

        super();

    }

    public static void main(final String[] args) {

        SplashScreen mySplash = SplashScreen.getSplashScreen();

        try {

            Thread.sleep(3000L);

        } catch (InterruptedException ex) {

            ex.getMessage();

        }

        if (mySplash != null) {

            mySplash.close();

            Application.launch(Main.class, args);

        }

    }

    @Override

    public void start(Stage primaryStage) throws Exception {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

}
