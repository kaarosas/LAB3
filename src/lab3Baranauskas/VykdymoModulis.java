package lab3Baranauskas;

import laborai.gui.swing.Lab3Window;
import java.util.Locale;

public class VykdymoModulis {
    
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        VaisiuTestai.atvaizdzioTestas();
        Lab3Window.createAndShowGUI();
    }
    
}
