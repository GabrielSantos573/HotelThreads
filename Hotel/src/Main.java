import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        List<Quarto> quartos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            quartos.add(new Quarto(i));
        }

        Queue<Hospede> filaDeEspera = new LinkedList<>();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            threads.add(new Hospede(quartos));
        }

        for (int i = 0; i < 10; i++) {
            threads.add(new Camareira(quartos));
        }

        for (int i = 0; i < 5; i++) {
            threads.add(new Recepcionista(quartos, filaDeEspera));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}