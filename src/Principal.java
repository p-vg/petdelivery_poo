import Controller.MenuController;
import View.MenuView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.Collections;
import javax.swing.UIManager;
public class Principal {

    public static void main(String[] args) {


        FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#5B3FE0"));
        FlatLightLaf.setup();

        UIManager.put("Button.arc", 16);
        UIManager.put("Component.arc", 12);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("defaultFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        SwingUtilities.invokeLater(() -> {
            MenuView menu = new MenuView();
            new MenuController(menu);
            menu.setVisible(true);
        });
    }
}
