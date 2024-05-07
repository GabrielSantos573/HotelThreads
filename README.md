# Como executar

Para executar este projeto, siga os passos abaixo:

1. Certifique-se de ter o JDK instalado em seu ambiente de desenvolvimento.
2. Clone o repositório na sua maquina.
3. Execute a Classe main

# Simulação de Funcionamento de um Hotel

## Entidades:

### Quartos
- No mínimo, devem existir 10 quartos.

### Hóspedes
- Cada hóspede deve ser representado por uma thread.
- No mínimo, devem existir 50 hóspedes.

### Camareiras
- Cada camareira deve ser representada por uma thread.
- No mínimo, devem existir 10 camareiras.

### Recepcionistas
- Cada recepcionista deve ser representado por uma thread.
- No mínimo, devem existir 5 recepcionistas.

## Regras:

1. O hotel deve contar com vários recepcionistas, que trabalham juntos e que devem alocar hóspedes apenas em quartos vagos.
2. O hotel deve contar com várias camareiras.
3. Cada quarto possui capacidade para até 4 hóspedes e uma única chave.
4. Caso um grupo ou uma família possua mais do que 4 membros, eles devem ser divididos em vários quartos.
5. Quando os hóspedes de um quarto saem do hotel para passear, devem deixar a chave na recepção.
6. Uma camareira só pode entrar em um quarto caso ele esteja vago ou os hóspedes não estejam nele, ou seja, a chave esteja na recepção.
7. A limpeza dos quartos é feita sempre após a passagem dos hóspedes pelo quarto. Isso significa que toda vez que os hóspedes saem do quarto (para passear ou terminando sua estadia), deve haver a entrada de uma camareira para limpeza do quarto e os hóspedes só podem retornar após o fim da limpeza.
8. Um quarto vago que passa por limpeza não pode ser alocado para um hóspede novo.
9. Caso uma pessoa chegue e não tenha quartos vagos, ele deve esperar em uma fila até algum quarto ficar vago. Caso a espera demore muito, ele passeia pela cidade e retorna após um tempo para tentar alugar um quarto novamente.
10. Caso a pessoa tente duas vezes alugar um quarto e não consiga, ele deixa uma reclamação e vai embora.

## Observações:
- Não há a possibilidade de, para um mesmo quarto, somente parte dos hóspedes saírem para passear. Ou saem todos ou nenhum.
- A implementação deve ser abrangente e simular várias situações: número diferentes de hóspedes chegando, grupos com mais de 4 pessoas, todos os quartos lotados, etc.
- Atentem-se para a descrição de cada regra!! Deve haver sincronia e coordenação entre as entidades.
