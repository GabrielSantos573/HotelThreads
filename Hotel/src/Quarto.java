class Quarto {
    private int num;
    private boolean ocupado;
    private boolean limpo;
    private Hospede[] hospedes;
    private int qtdHospedes;

    public Quarto(int num) {
        this.num = num;
        this.ocupado = false;
        this.limpo = true;
        this.hospedes = new Hospede[4]; // 4 hóspedes MAX
        this.qtdHospedes = 0;
    }

    public synchronized void reservar(Hospede hospede) throws InterruptedException, Qcheio {
        while (ocupado || (limpo && qtdHospedes >= 4)) {
            if (!limpo) {
                System.out.println("Quarto " + num + " sendo limpo " + hospede.getName() + "aguardando...");
            } else {
                System.out.println("Quarto " + num + " está lotado. " + hospede.getName() + " aguardando...");
            }
            System.out.println("---------------------");
            wait();
        }

        if (!ocupado) {
            hospedes[qtdHospedes++] = hospede;
            System.out.println(hospede.getName() + " reservou para si o Quarto " + num + ".");
            System.out.println("---------------------");
            if (qtdHospedes == 4) {
                ocupado = true;
                limpo = false;
                System.out.println("Quarto " + num + " está lotado. Esperando a saída dos hospedes para a limpeza.");
            }
            return;
        }

        throw new Qcheio("Quarto " + num + " está lotado.");
    }

    public synchronized void desocupar() {
        for (int i = 0; i < 4; i++) {
            if (hospedes[i] != null) {
                System.out.println("Hóspede " + hospedes[i].getName() + " liberou o Quarto " + num + ".");
                hospedes[i] = null;
            }
        }
        qtdHospedes = 0;
        ocupado = false;
        limpo = false;
        System.out.println("Quarto " + num + " livre. Limpeza acontecendo.");
        System.out.println("---------------------");
        notifyAll();
    }

    public synchronized void limpar() throws Qocupado {
        if (ocupado) {
            throw new Qocupado("Quarto " + num + " está ocupado e não pode ser limpo.");
        }

        // Limpe o quarto
        limpo = true;
        System.out.println("Quarto " + num + " limpo e pronto para ocupação.");
        System.out.println("---------------------");
        notifyAll();
    }

    public boolean isLimpo() {
        return limpo;
    }

    public boolean isOcupado() {
        return ocupado;
    }
}