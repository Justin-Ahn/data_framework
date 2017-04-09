import gui.FrameworkGUI;

import javax.swing.*;

/**
 * Created by Justin on 4/8/2017.
 */
public class Main {
    public static void main (String [] args) {
        SwingUtilities.invokeLater(Main::startFramework);
    }

    private static void startFramework() {
        FrameworkGUI gui = new FrameworkGUI();
    }
}
