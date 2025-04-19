package ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class FinishGameButton extends JButton {

    public FinishGameButton(final ActionListener actionListener) {
        this.setText("Finalizar Game");
        this.addActionListener(actionListener);
    }
}
