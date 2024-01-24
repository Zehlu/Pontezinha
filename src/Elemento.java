import java.util.Random;
import java.util.concurrent.Semaphore;
class Elemento extends Thread {
    private int x;
    private int y;
    private final int xF;
    private final int yI;
    private String cor;
    Semaphore ponte;
    Tabuleiro tabuleiro;
    Semaphore movimento;


    public Elemento(int x, int y, String cor, Tabuleiro tabuleiro, Semaphore ponte, Semaphore movimento) {
        this.x = x;
        this.y = y;

        if (x == 0){
            this.xF = tabuleiro.tamanho-1;
        }else{
            this.xF = 0;
        }
        this.yI = y;
        this.cor = cor;
        this.tabuleiro = tabuleiro;
        this.ponte = ponte;
        this.movimento = movimento;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCor() {
        return cor;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void mudarCor() {
        if(cor.equals(" V ")){
            cor = " A ";
        }else{
            cor = " V ";
        }
    }

    @Override
    public void run() {
        Random random = new Random();
        int x = 0;


        while (true) {

            if (x == 1) {
                break;
            }

            int direcaoX = random.nextInt(3) - 1; // -1, 0 ou 1
            int direcaoY = random.nextInt(3) - 1; // -1, 0 ou 1

            int correnteX = this.getX();
            int correnteY = this.getY();

            int novoX = correnteX + direcaoX;
            int novoY = correnteY + direcaoY;

            if (tabuleiro.validarPosicao(novoX, novoY)) {

                if (novoX == tabuleiro.tamanho / 2 && novoY == tabuleiro.tamanho / 2) {
                    if (tabuleiro.elementos[xF][yI] == null){
                        this.mudarCor();
                        tabuleiro.elementos[xF][yI] = this;
                        tabuleiro.elementos[correnteX][correnteY] = null;
                        x++;
                    }

                    ponte.release();

                } else {
                    if (movimento.tryAcquire()) {

                        tabuleiro.elementos[correnteX][correnteY] = null;
                        tabuleiro.elementos[novoX][novoY] = this;
                        setX(novoX);
                        setY(novoY);

                        movimento.release();
                    }
                }

            }
            tabuleiro.verificarFimDoJogo();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
