package br.usp.iq.lbi.caravela.statistics;

public class TesteFloat {
    public static void main(String[] args) {
        String valorString = "0,994".replace(",",".");
        Float valorFloat = Float.valueOf(valorString);
        System.out.println(valorFloat);
    }
}
