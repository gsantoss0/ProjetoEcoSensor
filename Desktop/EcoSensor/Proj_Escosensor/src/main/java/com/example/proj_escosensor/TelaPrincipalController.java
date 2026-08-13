package com.example.proj_escosensor;

import com.fazecast.jSerialComm.SerialPort;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.util.Duration;


public class TelaPrincipalController {

    @FXML
    private LineChart<Number, Number> linechartTemperatura;

    @FXML
    private LineChart<Number, Number> linechartUmidade;

    @FXML
    private TableColumn colTemperatura;

    @FXML
    private TableColumn colUmidade;

    private XYChart.Series<Number, Number> temperaturaSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> umidadeSeries = new XYChart.Series<>();

    private Service SensorService = new Service();
    private Timeline cronometro;

    public void initialize() {

        linechartTemperatura.getData().add(temperaturaSeries);
        linechartUmidade.getData().add(umidadeSeries);

        //Tirar zoom automatico
        NumberAxis xAxisTemp = (NumberAxis) linechartTemperatura.getXAxis();
        NumberAxis xAxisUmid = (NumberAxis) linechartUmidade.getXAxis();

        NumberAxis yAxisTemp = (NumberAxis) linechartTemperatura.getYAxis();
        NumberAxis yAxisUmid = (NumberAxis) linechartUmidade.getYAxis();

        xAxisTemp.setAutoRanging(false);
        xAxisUmid.setAutoRanging(false);

        // 3. Definir a janela visual fixa para mostrar 10 pontos (ajuste conforme o tempo do seu SensorService)
        // Se o seu tempo aumenta de 1 em 1, exibe de 0 a 10.
        xAxisTemp.setLowerBound(0);
        xAxisTemp.setUpperBound(10);
        xAxisUmid.setLowerBound(0);
        xAxisUmid.setUpperBound(10);

        xAxisTemp.setTickUnit(1);
        xAxisUmid.setTickUnit(1);

        yAxisTemp.setForceZeroInRange(false);
        yAxisUmid.setForceZeroInRange(false);

        // Atualizar os programa de 2 em 2 segundos
        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            Datacenter dados = SensorService.lerProximosDados();

            // Adiciona os novos pontos nos gráficos
            temperaturaSeries.getData().add(new XYChart.Data<>(dados.getTempo(), dados.getTemperatura()));
            umidadeSeries.getData().add(new XYChart.Data<>(dados.getTempo(), dados.getUmidade()));

            // Mantém apenas os últimos 10 pontos na memória
            if (temperaturaSeries.getData().size() > 11) {
                temperaturaSeries.getData().remove(0);
                umidadeSeries.getData().remove(0);

                // 4. Move a esteira do gráfico para acompanhar o tempo atual de forma suave
                double novoMinimo = dados.getTempo() - 10;
                double novoMaximo = dados.getTempo();

                xAxisTemp.setLowerBound(novoMinimo);
                xAxisTemp.setUpperBound(novoMaximo);

                xAxisUmid.setLowerBound(novoMinimo);
                xAxisUmid.setUpperBound(novoMaximo);
            }
        }));
        cronometro.setCycleCount(Animation.INDEFINITE);
        cronometro.play();
    }

    public void btnNew(ActionEvent actionEvent) {

    }

    public void btnLigarVent(ActionEvent actionEvent) {
    }

    public void btnDesligarVent(ActionEvent actionEvent) {
    }
}
