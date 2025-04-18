import model.Board;
import model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;
import static util.BoardTemplate.BOARD_TEMPLATE;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;

    //define tamanho do tabuleiro
    private final static int BOARD_SIZE = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var option = -1;

        while(true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGamer();
                case 8 -> System.exit(0);
                default -> System.out.println("Opçao invalida, tente novamente");
            }
        }

    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_SIZE; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar");
    }

    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("Informe a coluna em que o númedo será inserido: ");
        var column = runUntilGetValidNumber(0, 8);

        System.out.println("Informe a linha em que o númedo será inserido: ");
        var row = runUntilGetValidNumber(0, 8);

        System.out.printf("Informe o numero que vai entrar na posicao [%s,%s]: ", column, row);
        var value = runUntilGetValidNumber(1, 9);

        if(!board.changeValue(column, row, value)){
            System.out.printf("A posicao [%s,%s] tem um valor fixo\n", column, row);
        }
    }

    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("Informe a coluna em que o númedo será removido: ");
        var column = runUntilGetValidNumber(0, 8);

        System.out.println("Informe a linha em que o númedo será removido: ");
        var row = runUntilGetValidNumber(0, 8);

        if(!board.clearValue(column, row)){
            System.out.printf("A posicao [%s,%s] tem um valor fixo\n", column, row);
        }
    }

//    private static void showCurrentGame() {
//        if(isNull(board)){
//            System.out.println("O jogo ainda não foi iniciado!");
//            return;
//        }
//
//        var args = new Object[1];
//        var argPos = 0;
//        for(int i = 0; i < BOARD_SIZE; i++){
//            for(var column: board.getSpaces()){
//                args[argPos++] = " " + ((isNull(column.get(i).getActual())) ? "": column.get(i).getActual());
//            }
//        }
//        System.out.println("Seu jogo se encontra da seguinte forma: ");
//        System.out.printf((BOARD_TEMPLATE) + "\n", args);
//    }
    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado!");
            return;
        }

        int totalSpaces = BOARD_SIZE * board.getSpaces().size(); // tamanho adequado
        var args = new Object[totalSpaces];
        var argPos = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (var column: board.getSpaces()) {
                args[argPos++] = " " + ((isNull(column.get(i).getActual())) ? " " : column.get(i).getActual());
            }
        }

        System.out.println("Seu jogo se encontra da seguinte forma: ");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado!");
            return;
        }

        System.out.printf("Seu jogo se encontra atualmente no status: %s\n", board.getGameStatus().getLabel());
        if(board.hasErrors()) {
            System.out.println("O jogo contem erros!\n");
        }else {
            System.out.printf("Seu jogo não contem erros\n");
        }
    }

    private static void clearGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("Tem certeza que deseja limpar o jogo e perder todo o seu progresso?");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")) {
            System.out.println("Informe 'sim' ou 'não'");
            confirm = scanner.next();
        }

        if(confirm.equalsIgnoreCase("sim")){
            board.reset();
        }
    }

    private static void finishGamer() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado!");
            return;
        }

        if(board.gameIsFinished()){
            System.out.println("Parabéns, vc concluiu o jogo!");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("Seu jogo contem erros, verifique o seu board e ajuste-o!");
        }
        else {
            System.out.println("Você ainda precisa preencher algum espaço!");
        }
    }

    private static int runUntilGetValidNumber(final int min, final int max) {
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Informe um numero entre  %d e %d\n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }


}