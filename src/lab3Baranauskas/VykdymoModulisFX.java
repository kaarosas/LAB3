package lab3Baranauskas;

import laborai.gui.fx.Lab3WindowFX;
import java.util.Locale;
import javafx.stage.Stage;
import javafx.application.Application;

public class VykdymoModulisFX extends Application{
    
    public static void main(String[] args) {
        VykdymoModulisFX.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        Locale.setDefault(Locale.US);
        VaisiuTestai.atvaizdzioTestas();
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        Lab3WindowFX.createAndShowFXGUI(primaryStage);
    }
    
}
