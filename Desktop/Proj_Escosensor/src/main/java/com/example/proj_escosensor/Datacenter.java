package com.example.proj_escosensor;

import java.time.LocalDateTime;

public class Datacenter {

    private LocalDateTime tempo;
    private double temperatura;
    private double umidade;

    public Datacenter(LocalDateTime tempo, double temperatura, double umidade) {

        this.tempo = tempo;
        this.temperatura = temperatura;
        this.umidade = umidade;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public LocalDateTime getTempo() {
        return tempo;
    }

    public void setTempo(LocalDateTime tempo) {
        this.tempo = tempo;
    }
}
