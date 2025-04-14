package model;

public class Space {

    private Integer actual;
    private final int expected;     //valor esperado a ser preenchido no espaco do tabuleiro
    private final boolean fixed;    //valor que vai ficar fixo vindo no tabuleiro inicial

    public Space(Integer actual, int expected, boolean fixed) {
        this.fixed = fixed;
        this.expected = expected;
        if (fixed) {    //se fixed for true
            actual = expected; //seu valor atual ja ira ser o valor esperado, ou seja, valor vindo no tabuleiro inicial
        }
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(final Integer actual) {
        if (fixed) return;
        this.actual = actual;
    }

    public void clearSpace() {
        setActual(null);
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }


}
