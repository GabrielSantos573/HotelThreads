class Quarto {
    private int num;
    private boolean ocupado;
    private boolean limpo;
    private Hospede[] hospedes;
    private int nHospedes;

    public Quarto(int num) {
        this.num = num;
        this.ocupado = false;
        this.limpo = true;
        this.hospedes = new Hospede[4]; // 4 hóspedes MAX
        this.nHospedes = 0;
    }

    // Método para reservar o quarto com um hóspede específico
    public synchronized void reservar(Hospede hospede) throws InterruptedException, Qcheio {
        // Enquanto o quarto estiver ocupado ou limpo e atingiu a capacidade máxima de hóspedes, espera
        while (ocupado || (limpo && nHospedes >= 4)) {
            if (!limpo) {
                System.out.println("Quarto " + num + " sendo limpo " + hospede.getName() + "aguardando...");
            } else {
                System.out.println("Quarto " + num + " está lotado. " + hospede.getName() + " aguardando...");
            }
            System.out.println("---------------------");
            wait();
            // Aguarda notificação para continuar
        }

        // Se o quarto não estiver ocupado, adiciona o hóspede e atualiza o estado do quarto
        if (!ocupado) {
            hospedes[nHospedes++] = hospede;
            System.out.println(hospede.getName() + " reservou para si o Quarto " + num + ".");
            System.out.println("---------------------");
            if (nHospedes == 4) {
                ocupado = true;
                limpo = false;
                System.out.println("Quarto " + num + " está lotado. Esperando a saída dos hospedes para a limpeza.");
            }
            return;
        }
        // Lança uma exceção se o quarto estiver lotado
        throw new Qcheio("Quarto " + num + " está lotado.");
    }

    public synchronized void desocupar() {
        for (int i = 0; i < 4; i++) {
            if (hospedes[i] != null) {
                System.out.println("Hóspede " + hospedes[i].getName() + " liberou o Quarto " + num + ".");
                hospedes[i] = null;
            }
        }
        nHospedes = 0;// Reseta o contador de hóspedes
        ocupado = false;
        limpo = false;
        System.out.println("Quarto " + num + " livre. Limpeza acontecendo.");
        System.out.println("---------------------");
        notifyAll();
    }

    // Método para limpar o quarto
    public synchronized void limpar() throws Qocupado {
        if (ocupado) {
            throw new Qocupado("Quarto " + num + " está ocupado e não pode ser limpo.");
        }

        // Marca o quarto como limpo e notifica threads em espera que o quarto está pronto para ocupação
        limpo = true;
        System.out.println("Quarto " + num + " limpo e pronto para ocupação.");
        System.out.println("---------------------");
        notifyAll();
    }

    //getters
    public boolean isLimpo() {
        return limpo;
    }

    public boolean isOcupado() {
        return ocupado;
    }
}