package com.example.proj_escosensor;

import com.fazecast.jSerialComm.SerialPort;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.util.List;


public class TelaPrincipalController {

    int tempoGrafico = 0;

    @FXML
    private LineChart<Number, Number> linechartTemperatura;

    @FXML
    private LineChart<Number, Number> linechartUmidade;

    @FXML
    private TableColumn colTemperatura;

    @FXML
    private TableColumn colUmidade;

    @FXML
    private TableColumn colData;

    @FXML
    private TableView<Datacenter> tbvLogs;

    private final ObservableList<Datacenter> listaLogs =
            FXCollections.observableArrayList();

    private XYChart.Series<Number, Number> temperaturaSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> umidadeSeries = new XYChart.Series<>();

    private Service SensorService = new Service();
    private Timeline cronometro;

    public void initialize() {

        lista();
        configurarGraficos();

        SerialPort[] portas = SerialPort.getCommPorts();

        // ------------------ confirmar porta e conexao com o arduino
        for (SerialPort porta : portas) {
            System.out.println(
                    porta.getSystemPortName()
            );
        }

        SensorService.conectar();
    }

    public void btnNewCSV(ActionEvent actionEvent) {

        if (listaLogs.isEmpty()) {
            Utils.mostrarAlerta(
                    "Atenção",
                    "Não existem logs para exportar."
            );

            return;
        }

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Exportar arquivo CSV");

        fileChooser.setInitialFileName("log.csv");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Arquivos CSV (*.csv)",
                        "*.csv"
                )
        );

        Window janela = tbvLogs.getScene().getWindow();

        File arquivo = fileChooser.showSaveDialog(janela);

        if (arquivo == null) {
            return;
        }

        if (!arquivo.getName().toLowerCase().endsWith(".csv")) {

            arquivo = new File(
                    arquivo.getAbsolutePath() + ".csv"
            );
        }

        try {

            CSV.salvar(listaLogs, arquivo);

            Utils.mostrarAlerta(
                    "Exportação concluída",
                    "Arquivo CSV exportado com sucesso!"
            );

        } catch (Exception e) {

            Utils.mostrarAlerta(
                    "Erro",
                    "Não foi possível exportar o arquivo CSV.\n\n"
                            + e.getMessage()
            );
        }
    }

    public void btnLigarVent(ActionEvent actionEvent) {
    }

    public void btnDesligarVent(ActionEvent actionEvent) {
    }

    public void configurarGraficos() {

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
        cronometro = new Timeline(new KeyFrame(Duration.seconds(2), event -> {

            Datacenter dados = SensorService.lerproximosDados();

            // Só atualiza o gráfico se o Arduino tiver enviado dados
            if (dados != null) {

                tempoGrafico += 2;

                temperaturaSeries.getData().add(
                        new XYChart.Data<>(
                                tempoGrafico,
                                dados.getTemperatura()
                        )
                );

                umidadeSeries.getData().add(
                        new XYChart.Data<>(
                                tempoGrafico,
                                dados.getUmidade()
                        )
                );

                listaLogs.add(dados);

                // Mantém apenas os últimos 11 pontos
                if (temperaturaSeries.getData().size() > 11) {

                    temperaturaSeries.getData().remove(0);
                    umidadeSeries.getData().remove(0);

                    double novoMinimo = tempoGrafico - 10;
                    double novoMaximo = tempoGrafico;

                    xAxisTemp.setLowerBound(novoMinimo);
                    xAxisTemp.setUpperBound(novoMaximo);

                    xAxisUmid.setLowerBound(novoMinimo);
                    xAxisUmid.setUpperBound(novoMaximo);
                }
            }
        }));
        cronometro.setCycleCount(Animation.INDEFINITE);
        cronometro.play();
    }

    public void lista() {

        colData.setCellValueFactory(
                new PropertyValueFactory<>("tempo")
        );

        colTemperatura.setCellValueFactory(
                new PropertyValueFactory<>("temperatura")
        );

        colUmidade.setCellValueFactory(
                new PropertyValueFactory<>("umidade")
        );

        tbvLogs.setItems(listaLogs);
    }
}
