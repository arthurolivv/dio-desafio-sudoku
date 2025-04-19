import ui.custom.screen.MainScreen;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {

//        var dimension = new Dimension(600, 600);
//        JPanel mainPanel = new MainPanel(dimension);
//        JFrame mainFrame = new MainFrame(dimension, mainPanel);
//        mainFrame.revalidate();
//        mainFrame.repaint(); //refresh na tela quando entrar componentes

        final var gameConfig = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
    }
}
