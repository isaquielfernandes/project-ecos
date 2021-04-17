package cv.com.escola.model.util;

import cv.com.escola.model.dao.DAO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.debug(PRESENTATION);
        log.debug("Iniciando backups...\n\n");

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
                log.debug("Backup do banco de dados (" + i + "): " + dbName + dataAgora +" ...");
                pb.start();
            } catch (IOException ex) {
                log.error("IO: ", ex); 
            }
            i++;
        }
        // Fim
        time2 = System.currentTimeMillis();

        // Tempo total da operação
        time = time2 - time1;
        
        Mensagem.info("\nBackups realizados com sucesso.\n\n" + "Tempo total de processamento: " + time + " ms\n ");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            log.warn("Interrupted!: ", ex);
            Thread.currentThread().interrupt();
        }
    }
}
