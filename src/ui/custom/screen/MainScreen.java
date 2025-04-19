package ui.custom.screen;

import service.BoardService;
import ui.custom.button.CheckGameStatusButton;
import ui.custom.button.FinishGameButton;
import ui.custom.button.ResetButton;
import ui.custom.frame.MainFrame;
import ui.custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addFinishButton(JPanel mainPanel) {
        JButton finishButton = new FinishGameButton(e -> {
            if (boardService.gameGameIsFinished()){
                JOptionPane.showMessageDialog(null,"Parabéns, você completou o jogo!");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            }else{
                var message = "Seu jogo contém alguma inconsisteência, ajuste e tente novamente!";
                JOptionPane.showMessageDialog(null, message);
            }
        });
        mainPanel.add(finishButton);

    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        JButton checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getGameStatus();
            var message = switch (gameStatus){
                case NOT_STARTED -> "Jogo não iniciado";
                case INCOMPLETE -> "Jogo incompleto";
                case COMPLETE -> "Jogo completo";
            };
            message += hasErrors ? " e contém erros!" : " e não contem erros";
            JOptionPane.showMessageDialog(null, message);
        });
        mainPanel.add(checkGameStatusButton);

    }

    private void addResetButton(JPanel mainPanel) {
       JButton resetButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );
            if (dialogResult==0){
                boardService.reset();
            }
        });
        mainPanel.add(resetButton);

    }


}
