import java.util.List;
import java.util.Queue;

class Recepcionista extends Thread {
  private static int count = 0; // Contador estático para atribuir nomes únicos aos recepcionistas
  private List<Quarto> quartos; // Lista de quartos disponíveis no hotel
  private Queue<Hospede> filaDeEspera; // Fila de espera para hóspedes sem quartos disponíveis
  private volatile boolean ativo; // Flag indicando se o recepcionista está ativo ou não

  // Construtor
  public Recepcionista(List<Quarto> quartos, Queue<Hospede> filaDeEspera) {
    super("Recepcionista " + ++count); // Define o nome do recepcionista
    this.quartos = quartos;
    this.filaDeEspera = filaDeEspera;
    this.ativo = true; // Define o recepcionista como ativo
  }

  // Método para parar o serviço do recepcionista
  public void pararServico() {
    this.ativo = false; // Define o recepcionista como inativo
  }

  // Método para reservar um quarto para um hóspede
  public synchronized void reservaDeQuarto(Hospede hospede) throws Qcheio {
    for (Quarto quarto : quartos) {
      synchronized (quarto) { // Sincroniza o acesso ao quarto
        try {
          quarto.reservar(hospede); // Tenta reservar o quarto para o hóspede
          return;
        } catch (Qcheio e) {
          continue;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    // Se não houver quartos disponíveis, adiciona o hóspede à fila de espera
    filaDeEspera.offer(hospede);
    System.out.println("Hóspede " + hospede.getName() + " na fila.");
  }

  @Override
  public void run() {
    while (this.ativo) {
      synchronized (filaDeEspera) {
        while (!filaDeEspera.isEmpty()) {
          Hospede hospede = filaDeEspera.poll(); // Obtém o próximo hóspede da fila
          try {
            reservaDeQuarto(hospede);
          } catch (Qcheio e) {
            // Se não houver quartos disponíveis, o hóspede desiste
            System.out.println("Hóspede " + hospede.getName() + "saiu.");
            System.out.println("---------------------");
          }
        }
      }
      // Se a fila de espera estiver vazia, encerra o serviço do recepcionista
      if (filaDeEspera.isEmpty()) {
        this.pararServico();
      }
      try {
        Thread.sleep(5555); // Aguarda um intervalo antes de verificar novamente a fila de espera
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
