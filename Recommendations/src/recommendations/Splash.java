/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendations;

import java.awt.SplashScreen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author alex
 */
public class Splash extends Application{
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
            Application.launch(Recommendations.class, args);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
