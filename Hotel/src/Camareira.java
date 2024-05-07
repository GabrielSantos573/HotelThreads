import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Camareira extends Thread {
  private static int count = 0;
  private List<Quarto> quartos;
  private volatile boolean ativo;

  public Camareira(List<Quarto> quartos) {
    super("Camareira " + ++count);
    this.quartos = quartos;
    this.ativo = true;
  }

  public void pararDeLimpar() {
    this.ativo = false;
  }

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
        this.parar();
      }
      try {
        Thread.sleep(11100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
