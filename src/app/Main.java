package app;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import components.ZenFrame;
import view.MainView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();

            UIManager.put("ClassLoader", FlatSVGIcon.class.getClassLoader());
            UIManager.put("Component.arrowType", "chevron");
            UIManager.put("CheckBox.font", new Font("SansSerif", Font.PLAIN, 20));
            UIManager.put("CheckBox.foreground", Color.decode("#4A473E"));
            UIManager.put("CheckBox.background", Color.decode("#C0BCA5"));
            UIManager.put("CheckBox.focusWidth", 0);
            UIManager.put("CheckBox.iconSize", 20); // aumenta o tamanho da caixa
            UIManager.put("CheckBox.iconTextGap", 15);

            UIManager.put("Panel.background", Color.decode("#BFBDA6"));
            UIManager.put("CheckBox.background", Color.decode("#BFBDA6"));
            UIManager.put("CheckBox.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Button.background", Color.decode("#635F54"));
            UIManager.put("Button.foreground", Color.decode("#BDB8A2"));
            UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("TextField.background", new Color(255, 255, 255));
            UIManager.put("TextField.foreground", new Color(40, 40, 40));
            UIManager.put("TextField.caretForeground", Color.BLACK);
            UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Component.focusWidth", 0);

        } catch (Exception e) {
            System.err.println("Erro ao aplicar FlatLaf.");
        }

        SwingUtilities.invokeLater(() -> {
            ZenFrame frame = new ZenFrame(new MainView());
            frame.setVisible(true);
        });
    }
}