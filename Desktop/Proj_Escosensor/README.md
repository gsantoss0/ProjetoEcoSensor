# Projeto EcoSensor

Sistema de monitoramento de **temperatura e umidade** desenvolvido utilizando **Arduino** e uma aplicação desktop em *
*Java/JavaFX**.

O projeto realiza a leitura de dados de um sensor DHT11 conectado ao Arduino e disponibiliza uma interface gráfica para
visualização das informações, incluindo gráficos, registros e exportação dos dados para arquivos CSV.

---

## Funcionalidades

*  Leitura da temperatura através do sensor DHT11.
*  Leitura da umidade através do sensor DHT11.
*  Comunicação entre Arduino e computador pela porta serial.
*  Visualização da temperatura em gráfico.
* Visualização da umidade em gráfico.
*  Armazenamento dos registros de temperatura, umidade e data/hora.
*  Exibição dos registros em uma tabela.
*  Exportação dos registros para um arquivo `.CSV`.
*  Identificação de condições de temperatura e umidade fora dos limites definidos.
*  Acionamento de saída digital do Arduino para sinalização de alerta.

---

## Tecnologias utilizadas

### Desktop

* Java
* JavaFX 21.0.6
* Maven
* FXML
* jSerialComm
* IntelliJ IDEA

### Arduino

* Arduino
* C/C++
* Sensor DHT11
* Biblioteca `DHT`
* Comunicação Serial

---

## Estrutura do projeto

```text
ProjetoEcoSensor/
│
├── README.md
│
├── Docs/
│   └── Documentação técnica Projeto EcoSensor.pdf
│
├── Firmware/
│   └── Arduino-CODE.ino
│
└── Desktop/
    └── Proj_Escosensor/
        │
        ├── pom.xml
        ├── mvnw
        ├── mvnw.cmd
        ├── .gitignore
        │
        ├── .mvn/
        │   └── wrapper/
        │       ├── maven-wrapper.jar
        │       └── maven-wrapper.properties
        │
        └── src/
            │
            └── main/
                │
                ├── java/
                │   │
                │   ├── module-info.java
                │   │
                │   └── com/
                │       └── example/
                │           └── proj_escosensor/
                │               │
                │               ├── CSV.java
                │               ├── Datacenter.java
                │               ├── HelloApplication.java
                │               ├── Launcher.java
                │               ├── Service.java
                │               ├── TelaPrincipalController.java
                │               └── Utils.java
                │
                └── resources/
                    │
                    └── com/
                        └── example/
                            └── proj_escosensor/
                                └── TelaPrincipal.fxml
```

---

### `TelaPrincipalController.java`

É o principal controlador da interface gráfica.

Suas responsabilidades incluem:

* Inicializar a tela.
* Configurar os gráficos.
* Conectar com o Arduino.
* Ler os dados do sensor.
* Atualizar os gráficos.
* Atualizar a tabela de registros.
* Exportar os dados para CSV.
* Controlar a atualização periódica dos dados.

A aplicação utiliza um `Timeline` para realizar a leitura dos dados periodicamente.

---

### `Service.java`

Responsável pela comunicação serial com o Arduino utilizando a biblioteca:

```text
jSerialComm
```

A configuração utilizada atualmente é:

```text
Porta: COM14
Baud Rate: 9600
Data Bits: 8
Stop Bits: 1
Parity: None
```

> A porta `COM14` pode precisar ser alterada dependendo da porta COM atribuída ao Arduino no computador.

---

### `Datacenter.java`

Representa os dados coletados pelo sensor.

Cada registro possui:

```text
Temperatura
Umidade
Data/Hora
```

Exemplo:

```text
Temperatura: 27.5 °C
Umidade: 55 %
Data/Hora: 18/08/2026 08:30
```

---

### `CSV.java`

Responsável pela exportação dos registros.

O arquivo gerado possui as seguintes colunas:

```text
Temperatura;Umidade;Data/hora
```

---

### `Utils.java`

Possui métodos auxiliares utilizados pela aplicação.

Atualmente é utilizado para exibir mensagens através de caixas de diálogo (`Alert`) do JavaFX.

---

### `TelaPrincipal.fxml`

Arquivo responsável pela construção da interface gráfica utilizando **FXML**.

A tela contém componentes utilizados para:

* Exibir os gráficos.
* Exibir os registros.
* Interagir com o sistema.
* Apresentar informações ao usuário.

---

## Funcionamento do sistema

O funcionamento geral do projeto pode ser representado da seguinte maneira:

```text
┌──────────────────┐
│      DHT11       │
│                  │
│ Temperatura      │
│ Umidade          │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│     Arduino      │
│                  │
│ Leitura do sensor│
│ Regras de alerta │
└────────┬─────────┘
         │
         │ Serial 9600
         ▼
┌─────────────────────────┐
│     Aplicação JavaFX    │
│                         │
│      Service.java       │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ TelaPrincipalController │
└────────────┬────────────┘
             │
       ┌─────┼─────┐
       ▼     ▼     ▼
   Gráfico Tabela  CSV
```

---

## Configuração do ambiente

Para executar o projeto desktop, é necessário possuir:

* Java instalado.
* Maven.
* IntelliJ IDEA ou outra IDE compatível.
* Arduino IDE.
* Arduino compatível com o sensor utilizado.
* Sensor DHT11.
* Cabo USB para comunicação com o computador.

---

## Executando o Arduino

1. Abra o arquivo:

```text
Firmware/Arduino-CODE.ino
```

2. Abra o projeto na Arduino IDE.

3. Instale a biblioteca:

```text
DHT sensor library
```

4. Conecte o Arduino ao computador.

5. Selecione a placa correta.

6. Selecione a porta COM correta.

7. Faça o upload do código.

---

## Executando a aplicação JavaFX

Entre na pasta:

```text
Desktop/Proj_Escosensor/
```

E execute:

```bash
mvn clean javafx:run
```

Ou, utilizando o Maven Wrapper:

### Windows

```bash
mvnw.cmd clean javafx:run
```

---

## Configuração da porta serial

No arquivo:

```text
Service.java
```

a porta está configurada atualmente como:

```java
SerialPort.getCommPort("COM14");
```

Caso o Arduino esteja utilizando outra porta, altere:

```java
SerialPort.getCommPort("COM14");
```

para a porta correspondente.

Por exemplo:

```java
SerialPort.getCommPort("COM3");
```

A porta utilizada pode ser encontrada no **Gerenciador de Dispositivos do Windows**, em:

```text
Portas (COM e LPT)
```

---

## Dependências

O projeto utiliza Maven para gerenciar as dependências.

Principais bibliotecas utilizadas:

```text
JavaFX Controls 21.0.6
JavaFX FXML 21.0.6
jSerialComm
JUnit 5
```

As dependências estão configuradas no arquivo:

```text
Desktop/Proj_Escosensor/pom.xml
```

---

## Arquitetura

O projeto utiliza uma separação básica de responsabilidades:

```text
Interface
   │
   ▼
TelaPrincipal.fxml
   │
   ▼
TelaPrincipalController
   │
   ├── Service
   │     └── Comunicação Serial
   │
   ├── Datacenter
   │     └── Dados dos sensores
   │
   ├── CSV
   │     └── Exportação
   │
   └── Utils
         └── Mensagens/Alertas
```

---

##  Documentação

A documentação técnica do projeto está disponível em:

```text
Docs/Documentação técnica Projeto EcoSensor.pdf
```

