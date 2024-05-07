import java.util.List;
import java.util.Queue;

class Recepcionista extends Thread {
  private static int count = 0;
  private List<Quarto> quartos;
  private Queue<Hospede> filaDeEspera;
  private volatile boolean ativo;

  public Recepcionista(List<Quarto> quartos, Queue<Hospede> filaDeEspera) {
    super("Recepcionista " + ++count);
    this.quartos = quartos;
    this.filaDeEspera = filaDeEspera;
    this.ativo = true;
  }

  public void pararServico() {
    this.ativo = false;
  }

  public synchronized void reservaDeQuarto(Hospede hospede) throws Qcheio {
    for (Quarto quarto : quartos) {
      synchronized (quarto) {
        try {
          quarto.reservar(hospede);
          return;
        } catch (Qcheio e) {
          continue;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    filaDeEspera.offer(hospede);
    System.out.println("Hóspede " + hospede.getName() + " na fila.");
  }

  @Override
  public void run() {
    while (this.ativo) {
      synchronized (filaDeEspera) {
        while (!filaDeEspera.isEmpty()) {
          Hospede hospede = filaDeEspera.poll();
          try {
            reservaDeQuarto(hospede);
          } catch (Qcheio e) {
            System.out.println("Hóspede " + hospede.getName() + "saiu.");
            System.out.println("---------------------");
          }
        }
      }
      if (filaDeEspera.isEmpty()) {
        this.pararServico();
      }
      try {
        Thread.sleep(5555);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
