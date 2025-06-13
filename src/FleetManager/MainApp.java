package FleetManager;

import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FleetManagerGUI gui = new FleetManagerGUI();
            gui.setVisible(true);
        });
    }
}
