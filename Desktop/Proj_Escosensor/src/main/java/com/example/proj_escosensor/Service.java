package com.example.proj_escosensor;

import com.fazecast.jSerialComm.SerialPort;

import java.time.LocalDateTime;

public class Service {

    private final SerialPort porta;

    public Service() {

        porta = SerialPort.getCommPort("COM14"); // porta do arduino

        porta.setBaudRate(9600);
        porta.setNumDataBits(8);
        porta.setNumStopBits(SerialPort.ONE_STOP_BIT);
        porta.setParity(SerialPort.NO_PARITY);
    }

    public void conectar() {
        if (porta.openPort()) {
            System.out.println("Arduino conectado");
        } else {
            System.out.println("Arduino desconectado");
        }
    }

    public Datacenter lerproximosDados() {

        byte[] buffer = new byte[100];

        int quantidade = porta.readBytes(buffer, buffer.length);

        if (quantidade > 0) {

            String dadosRecebidos = new String(buffer, 0 , quantidade).trim();

            String[] valores = dadosRecebidos.split(",");

            double temperatura = Double.parseDouble(valores[0]);
            double umidade = Double.parseDouble(valores[1]);

            System.out.println(temperatura);
            System.out.println(umidade);

            LocalDateTime tempo = LocalDateTime.now();

            return new Datacenter(tempo, temperatura, umidade);
        }

        return null;
    }
}
