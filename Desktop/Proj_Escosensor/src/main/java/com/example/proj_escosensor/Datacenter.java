package com.example.proj_escosensor;

public class Datacenter {

    private int tempo;
    private double Temperatura;
    private double Umidade;

    public Datacenter(int tempo, double Temperatura, double Umidade){

        this.tempo = tempo;
        this.Temperatura = Temperatura;
        this.Umidade = Umidade;
    }

    public double getTemperatura() {
        return Temperatura;
    }

    public void setTemperatura(double temperatura) {
        Temperatura = temperatura;
    }

    public double getUmidade() {
        return Umidade;
    }

    public void setUmidade(double umidade) {
        Umidade = umidade;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
}
