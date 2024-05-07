import java.util.List;
import java.util.Random;

class Hospede extends Thread {
    private static int count = 0;
    private int id;
    private List<Quarto> quartos;
    private Random random = new Random();
    private volatile boolean ativo;

    public Hospede(List<Quarto> quartos) {
        this.id = ++count;
        this.quartos = quartos;
        this.ativo = true;
    }
    // Método para parar o hóspede, definindo o estado como inativo
    public void parar() {
        this.ativo = false;
    }

    @Override
    // Método que será executado quando o hóspede for iniciado como uma thread
    public void run() {
        // Enquanto o hóspede estiver ativo, ele continuará realizando ações
        while (this.ativo) {
            int quartoIndex = random.nextInt(quartos.size());
            Quarto quarto = quartos.get(quartoIndex);
            try {
                quarto.reservar(this);
                // Simula a permanência do hóspede no quarto por um período de tempo aleatório
                Thread.sleep(random.nextInt(5000));
                quarto.desocupar();
                Thread.sleep(random.nextInt(10000));
                // Define o hóspede como inativo, indicando o fim da estadia
                this.parar();
            } catch (InterruptedException | Qcheio e) {
                // Trata possíveis exceções, como interrupção da thread ou quarto cheio
                e.printStackTrace();
            }
        }
    }
}