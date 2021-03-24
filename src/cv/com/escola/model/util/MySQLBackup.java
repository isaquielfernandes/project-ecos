/*
 * Copyright (C) 2019 Isaquiel Fernandes.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cv.com.escola.model.util;

import cv.com.escola.model.dao.DAO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Isaquiel Fernandes
 */
public class MySQLBackup extends DAO {

    // Constantes da classe
    private static final String VERSION = "8.0.3";
    private static final String SEPARATOR = File.separator;
    private static final String MYSQL_PATH
            = "C:" + SEPARATOR
            + "Programas" + SEPARATOR
            + "MySQL" + SEPARATOR
            + "MySQL Server 8.0" + SEPARATOR
            + "bin" + SEPARATOR;

    private static final String PRESENTATION
            = "==========================================================\n"
            + "  Backup do banco de dados MySQL - Versao " + VERSION + "\n"
            + "  Autor: Isaquiel Fernandes\n\n"
            + "  Desenvolvido em 19/05/2019\n\n"
            + "  Blueberry, 2017-2019\n"
            + "==========================================================\n\n";

    // Lista dos bancos de dados a serem "backupeados"; se desejar adicionar mais,
    // basta colocar o nome separado por espaços dos outros nomes
    private final String DATABASES = db;

    private List<String> dbList = new ArrayList<>();

    @SuppressWarnings("CallToPrintStackTrace")
    public MySQLBackup() {
        
        String command = MYSQL_PATH;//"C:\\reports"+ SEPARATOR +"Backup" + SEPARATOR + "mysqldump.exe";
        String[] databases = DATABASES.split(" ");

        dbList.addAll(Arrays.asList(databases));

        // Mostra apresentação
        System.out.println(PRESENTATION);
        System.out.println("Iniciando backups...\n\n");

        // Contador
        int i = 1;

        // Tempo
        long time1, time2, time;

        // Início
        time1 = System.currentTimeMillis();

        for (String dbName : dbList) {
            String path = System.getProperty("user.home");
            String caminho = SEPARATOR + "Documents" + SEPARATOR + "Vendas" + SEPARATOR + "Backup";
            new File(path + caminho).mkdir();
            String dataAgora = LocalDate.now().toString();
            ProcessBuilder pb = new ProcessBuilder(
                    command,
                    "--user="+ user +"",
                    "--password="+ pass +"",
                    dbName,
                    "--result-file=" + path + caminho + SEPARATOR + "" + dbName + "-" + dataAgora +".sql");
            try {
                System.out.println(
                        "Backup do banco de dados (" + i + "): " + dbName + dataAgora +" ...");
                pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
        // Fim
        time2 = System.currentTimeMillis();

        // Tempo total da operação
        time = time2 - time1;

        // Avisa do sucesso
        //System.out.println("\nBackups realizados com sucesso.\n\n");
        //System.out.println("Tempo total de processamento: " + time + " ms\n");
        //System.out.println("Finalizando...");
        Mensagem.info("\nBackups realizados com sucesso.\n\n" + "Tempo total de processamento: " + time + " ms\n ");
        try {
            // Paralisa por 2 segundos
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        // Termina o aplicativo
        //System.exit(0);
    }
}
