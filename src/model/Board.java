package model;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static model.GameStatusEnum.*;

public class Board {

    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getGameStatus() {
        //pegamos a space e usamos o flatMap para ter acesso a lista mais interna e verificamos se a posicao nao é fixa e nao nula (algum valor inserido)
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {
            return NOT_STARTED;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors(){

        if(getGameStatus() == NOT_STARTED){
            return false;
        }

        //verifica se o espaço da prorpiedade atual esta com um valor preenchido diferente do expected tem um erro
        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && s.getActual().equals(s.getExpected()));

        //se nao, o usuario esta jogando corretamente
    }

    public boolean changeValue(final int column, final int row, final int value) {
        Space space = spaces.get(column).get(row);

        //verifica se é um espaço fixo (espaço com valor ja preenchido com o tabuleiro inicial)
        if(space.isFixed()){
            return false;
        }

        space.setActual(value);
        return true;

    }

    public boolean clearValue(final int column, final int row){
        Space space = spaces.get(column).get(row);

        //verifica se é um espaço fixo (espaço com valor ja preenchido com o tabuleiro inicial)
        if(space.isFixed()){
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset(){
        spaces.forEach(column -> column.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getGameStatus().equals(COMPLETE);
    }
}
