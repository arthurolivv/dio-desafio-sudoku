package model;

public class Space {

    private Integer actual;
    private final int expected;     //valor esperado a ser preenchido no espaco do tabuleiro
    private final boolean fixed;    //valor que vai ficar fixo vindo no tabuleiro inicial

    public Space(final int expected, final boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if (fixed){
            actual = expected;
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
