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

    public void parar() {
        this.ativo = false;
    }

    @Override
    public void run() {
        while (this.ativo) {
            int quartoIndex = random.nextInt(quartos.size());
            Quarto quarto = quartos.get(quartoIndex);
            try {
                quarto.reservar(this);
                Thread.sleep(random.nextInt(5000));
                quarto.desocupar();
                Thread.sleep(random.nextInt(10000));
                this.parar();
            } catch (InterruptedException | Qcheio e) {
                e.printStackTrace();
            }
        }
    }
}