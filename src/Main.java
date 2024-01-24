import static java.lang.Thread.currentThread;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int tamanhoDoTabuleiro = 11;


        Tabuleiro tabuleiro = new Tabuleiro(tamanhoDoTabuleiro);

        while (true){
            tabuleiro.status();
            Thread.sleep(1000);

            System.out.println();
            System.out.println();
            System.out.println();

            if (tabuleiro.jogoTerminado){
                break;
            }
        }

        tabuleiro.status();


        System.out.println();
        System.out.println();
        System.out.println("  Fim do jogo  ");
    }
}