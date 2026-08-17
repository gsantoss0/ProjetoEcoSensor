package com.example.proj_escosensor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSV {

    public static void salvar(List<Datacenter> datacenters, File arquivo) {

        try (PrintWriter escritor = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(arquivo),
                        StandardCharsets.UTF_8))) {

            escritor.println(
                    "Temperatura;Umidade;Data/hora"
            );

            for (Datacenter datacenter : datacenters) {

                escritor.println(
                        datacenter.getTemperatura() + ";" +
                                datacenter.getUmidade() + ";" +
                                datacenter.getTempo()
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao salvar o arquivo CSV: " + e.getMessage(),
                    e
            );
        }
    }
}
