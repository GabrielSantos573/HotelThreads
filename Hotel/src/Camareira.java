import java.util.List;

class Camareira extends Thread {
  // count: Uma variável que mantém o número total de camareiras criadas.
  private static int count = 0;
  // quartos: Uma lista de quartos que a camareira precisa limpar.
  private List<Quarto> quartos;
  // ativo: Uma variável volátil que indica se a camareira está ativa ou não. É
  // usada para controlar o ciclo de limpeza.
  private volatile boolean ativo;

  // Construtor Camareira: Incrementa o contador de camareiras e define o nome da
  // thread com base no número de camareiras criadas, inicializa a camareira como
  // ativa.
  public Camareira(List<Quarto> quartos) {
    super("Camareira " + ++count);
    this.quartos = quartos;
    this.ativo = true;
  }

  // pararDeLimpar: Metodo, ele simplesmente define a variável ativo como false.
  public void pararDeLimpar() {
    this.ativo = false;
  }

  // Responsável pela limpeza dos quartos enquanto a camareira está trabalhando.
  // Verifica se todos os quartos estão limpos. Se um quarto estiver ocupado,
  // continua limpando os outros. Dorme por 11,1 segundos após cada verificação.
  @Override
  public void run() {
    while (this.ativo) {
      boolean todosQuartosLimpos = true;
      for (Quarto quarto : quartos) {
        synchronized (quarto) {
          try {
            quarto.limpar();
          } catch (Qocupado e) {
            todosQuartosLimpos = false;
            continue;
          }
        }
      }
      if (todosQuartosLimpos) {
        this.pararDeLimpar();
      }
      try {
        Thread.sleep(11100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
