package com.example.proj_escosensor;

public class Datacenter {

    private String tempo;
    private double temperatura;
    private double umidade;

    public Datacenter(String tempo, double temperatura, double umidade) {

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

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}
