package components;

import javax.swing.*;
import java.awt.*;

public class ZenScrollBar extends JScrollBar {
    public ZenScrollBar() {
        super(VERTICAL);
        setUI(new ZenScrollBarUI());
        setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
        setUnitIncrement(16);
        setOpaque(false);
    }
}
