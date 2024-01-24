import java.util.concurrent.Semaphore;

class Tabuleiro {

    public Elemento [][] elementos;
    public int tamanho;
    public boolean jogoTerminado;

    Semaphore ponte = new Semaphore(1);

    Semaphore movimento = new Semaphore(1);

    public Tabuleiro(int tamanho) {

        this.tamanho = tamanho;
        elementos = new Elemento[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; i < tamanho; i++){
                elementos [i][j] = null;
            }
        }

        this.jogoTerminado = false;

        for (int i = 0; i < tamanho; i++) {
            elementos [0][i] = new Elemento(0, i, " V ", this, ponte, movimento);
            elementos [tamanho - 1][i] = new Elemento(tamanho - 1, i, " A ", this, ponte, movimento);

            if (i != (tamanho/2)){
                elementos [tamanho/2][i] = new Elemento(tamanho/2, i , " = ", this, ponte, movimento);
            }

            elementos[0][i].start();
            elementos[tamanho - 1][i].start();
        }
    }


    public void status(){
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                try {
                    System.out.print(elementos[i][j].getCor());
                }catch (NullPointerException e){
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }

    public boolean validarPosicao(int x, int y) {
        return (x >= 0 && x < tamanho-1 && y >= 0 && y < tamanho-1) && (elementos [x][y] == null);
    }

    public void verificarFimDoJogo() {
        int cont1 = 0,cont2 = 0;
        int egg = 0;

        for (int x = 0; x < tamanho-1 ; x++){
            try {
                if(elementos[0][x].getCor().equals(" V ")){
                    cont1++;
                }

                if(elementos[tamanho-1][x].getCor().equals(" A ")){
                    cont2++;
                }
            }catch (NullPointerException e){
                egg++;
            }
        }

        if (cont1 == tamanho-1 && cont2 == tamanho-1){
            encerrarJogo();
        }
    }

    public void encerrarJogo() {
        jogoTerminado = true;
    }
}

