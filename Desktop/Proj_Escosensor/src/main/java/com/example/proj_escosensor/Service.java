package com.example.proj_escosensor;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Random;

public class Service {

    private int tempoSegundos = 0;
    private final Random random = new Random();

    public Datacenter lerProximosDados() {

        tempoSegundos++;

        double temp = 25.0 + random.nextDouble() * 55;
        double umid = 60.0 + random.nextDouble() * 5;

        System.out.println(temp + "\n" + umid);

        return new Datacenter(tempoSegundos, temp, umid);
    }
}
