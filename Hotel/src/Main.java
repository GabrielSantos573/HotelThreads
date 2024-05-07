import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        List<Quarto> quartos = new ArrayList<>();
        // Adiciona 10 quartos à lista
        for (int i = 1; i <= 10; i++) {
            quartos.add(new Quarto(i));
        }

        // Cria uma fila de espera para hóspedes
        Queue<Hospede> filaDeEspera = new LinkedList<>();

        // Cria uma lista para armazenar as threads dos hóspedes, camareiras e
        // recepcionistas
        List<Thread> threads = new ArrayList<>();

        // Cria 50 threads representando hóspedes, cada uma competindo por um quarto
        for (int i = 0; i < 50; i++) {
            threads.add(new Hospede(quartos));
        }

        // Cria 10 threads representando camareiras, cada uma responsável pela limpeza
        // dos quartos
        for (int i = 0; i < 10; i++) {
            threads.add(new Camareira(quartos));
        }

        // Cria 5 threads representando recepcionistas, cada uma gerenciando a fila de
        // espera dos hóspedes
        for (int i = 0; i < 5; i++) {
            threads.add(new Recepcionista(quartos, filaDeEspera));
        }

        // Inicia todas as threads
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
