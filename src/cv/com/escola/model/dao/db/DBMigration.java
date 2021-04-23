package cv.com.escola.model.dao.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Isaquiel Fernandes
 */
public class DBMigration {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DBMigration.class);
    private static final Properties PROPERTIES = new Properties();
    private static File arq;
    private static InputStream inputStream;
    private static final String DB = "dbescola";
    private static String user;
    private static String pwd;
    private static String msg = "";
    private static PreparedStatement pst;

    public static void loadPropertiesFile() {
        try {
            arq = new File("database.properties");
            if (arq.exists()) {
                inputStream = new FileInputStream(arq);
                PROPERTIES.load(inputStream);
                user = PROPERTIES.getProperty("user");
                pwd = PROPERTIES.getProperty("password");
            } else {
                DBProperties.mkDbProperties();
                inputStream = new FileInputStream(arq);
                PROPERTIES.load(inputStream);
                user = PROPERTIES.getProperty("user");
                pwd = PROPERTIES.getProperty("password");
            }
        } catch (IOException e) {
            LOGGER.error("error\n" + e.getMessage());
        }
    }

    //Criando views
    public static void createView() {
        loadPropertiesFile();
        ConnectionManager con = ConnectionManager.getInstance();
        try {
            msg += "\n******************************************";
            msg += "\nCriando Views...";
            msg += "\n******************************************";

            // View exame
            pst = con.begin().prepareStatement("DROP VIEW IF EXISTS `" + DB + "`.`exame_view`;");
            pst.execute();

            pst = con.begin().prepareStatement("CREATE \n"
                    + "    ALGORITHM = UNDEFINED \n"
                    + "    DEFINER = `root`@`localhost` \n"
                    + "    SQL SECURITY DEFINER\n"
                    + "VIEW `" + DB + "`.`exame_view` AS\n"
                    + "    SELECT \n"
                    + "        `e`.`id_exame` AS `Codigo`,\n"
                    + "        `e`.`tipo_exame` AS `Tipo de Exame`,\n"
                    + "        `e`.`dia` AS `Dia`,\n"
                    + "        `e`.`hora` AS `Hora`,\n"
                    + "        `e`.`descricao` AS `Descrição`,\n"
                    + "        `e`.`fk_categoria` AS `Codigo da Categoria`,\n"
                    + "        `c`.`categoria` AS `Categoria`,\n"
                    + "        `e`.`fk_aluno` AS `Codigo Do Aluno`,\n"
                    + "        `a`.`nome` AS `Nome Do Aluno`,\n"
                    + "        `a`.`foto` AS `Fotografia`\n"
                    + "    FROM\n"
                    + "        ((`" + DB + "`.`tb_exame` `e`\n"
                    + "        JOIN `" + DB + "`.`tb_categoria` `c` ON ((`c`.`id_categoria` = `e`.`fk_categoria`)))\n"
                    + "        JOIN `" + DB + "`.`tb_aluno` `a` ON ((`a`.`id_aluno` = `e`.`fk_aluno`)));");
            pst.execute();

            //View Empresa Config
            pst = con.begin().prepareStatement("DROP VIEW IF EXISTS `" + DB + "`.`empresa_config_view;`");
            pst.execute();
            /*pst = con.mkDataBase().prepareStatement("CREATE \n"
                    + "    ALGORITHM = UNDEFINED \n"
                    + "    DEFINER = `root`@`localhost` \n"
                    + "    SQL SECURITY DEFINER\n"
                    + "VIEW `dbescola`.`empresa_config_view` AS\n"
                    + "    SELECT \n"
                    + "        emp.`id_empresa` AS `id_empresa`,\n"
                    + "        emp.`nome` AS `Nome`,\n"
                    + "        emp.`cidade` AS `Cidade`,\n"
                    + "        emp.`nif` AS `NIF`,\n"
                    + "        emp.`endereco` AS `Endereco`,\n"
                    + "        emp.`email` AS `E-Mail`,\n"
                    + "        emp.`contato` AS `Contato`,\n"
                    + "        emp.`descricao` AS `Descricao`,\n"
                    + "        emp.`tb_empresa`.`logo` AS `Logo`,\n"
                    + "        emp.`assinatura` AS `Assinatura`\n"
                    + "    FROM\n"
                    + "        `" + db +"`.`tb_empresa` as emp;");
            pst.execute();*/

            //View Inspecao Tecnica
            pst = con.begin().prepareStatement("DROP VIEW IF EXISTS `" + DB + "`.`inspecao_tecnica_view`;");
            pst.execute();

            pst = con.begin().prepareStatement("CREATE \n"
                    + "    ALGORITHM = UNDEFINED \n"
                    + "    DEFINER = `root`@`localhost` \n"
                    + "    SQL SECURITY DEFINER\n"
                    + "VIEW `" + DB + "`.`inspecao_tecnica_view` AS\n"
                    + "    SELECT \n"
                    + "        `i`.`id` AS `ID`,\n"
                    + "        `v`.`codigo` AS `Codigo`,\n"
                    + "        `v`.`placa` AS `Matricula`,\n"
                    + "        `v`.`fabricante` AS `Fabricante`,\n"
                    + "        `v`.`modelo` AS `Modelo`,\n"
                    + "        `v`.`anoFabricacao` AS `Ano Fabrico`,\n"
                    + "        `v`.`chassi` AS `Chassis`,\n"
                    + "        `i`.`tipoInspecao` AS `Tipo Inspeção`,\n"
                    + "        `i`.`dataInspecao` AS `Data`,\n"
                    + "        `i`.`duracao` AS `Duração`,\n"
                    + "        `i`.`resultado` AS `Resultado`,\n"
                    + "        `i`.`validade` AS `Válidade`\n"
                    + "    FROM\n"
                    + "        (`" + DB + "`.`tb_inspecao_tecnica` `i`\n"
                    + "        JOIN `" + DB + "`.`tb_veiculo` `v` ON ((`v`.`codigo` = `i`.`veiculo`)));");
            pst.execute();

            //View seguro auto
            pst = con.begin().prepareStatement("DROP VIEW IF EXISTS `" + DB + "`.`seguro_auto_view`;");
            pst.execute();

            pst = con.begin().prepareStatement("CREATE \n"
                    + "    ALGORITHM = UNDEFINED \n"
                    + "    DEFINER = `root`@`localhost` \n"
                    + "    SQL SECURITY DEFINER\n"
                    + "VIEW `" + DB + "`.`seguro_auto_view` AS\n"
                    + "    SELECT \n"
                    + "        `s`.`id` AS `ID`,\n"
                    + "        `s`.`compania` AS `Compania de Seguro`,\n"
                    + "        `v`.`codigo` AS `Codigo`,\n"
                    + "        `v`.`placa` AS `Matricula`,\n"
                    + "        `v`.`fabricante` AS `Fabricante`,\n"
                    + "        `v`.`modelo` AS `Modelo`,\n"
                    + "        `v`.`anoFabricacao` AS `Ano Fabrico`,\n"
                    + "        `v`.`nomeProprietario` AS `Proprietario`,\n"
                    + "        `v`.`chassi` AS `Chassis`,\n"
                    + "        `s`.`desde` AS `Desde`,\n"
                    + "        `s`.`ate` AS `Ate`,\n"
                    + "        `s`.`emissao` AS `Data Emissao`\n"
                    + "    FROM\n"
                    + "        (`" + DB + "`.`tb_seguro` `s`\n"
                    + "        JOIN `" + DB + "`.`tb_veiculo` `v` ON ((`v`.`codigo` = `s`.`veiculo_seguro`)));");
            pst.execute();

            //View Venda
            pst = con.begin().prepareStatement("DROP VIEW IF EXISTS `" + DB + "`.`venda_view`;");
            pst.execute();
            pst = con.begin().prepareStatement("CREATE \n"
                    + "    ALGORITHM = UNDEFINED \n"
                    + "    DEFINER = `root`@`localhost` \n"
                    + "    SQL SECURITY DEFINER\n"
                    + "VIEW `" + DB + "`.`venda_view` AS\n"
                    + "    SELECT \n"
                    + "        `v`.`id_vendas` AS `id_vendas`,\n"
                    + "        `v`.`data` AS `data`,\n"
                    + "        `v`.`valor_total` AS `valor_total`,\n"
                    + "        `v`.`pago` AS `pago`,\n"
                    + "        `v`.`meioDePag` AS `meioDePag`,\n"
                    + "        `v`.`desconto` AS `desconto`,\n"
                    + "        `v`.`num_fatura` AS `num_fatura`,\n"
                    + "        `c`.`id_clientes` AS `id_clientes`,\n"
                    + "        `c`.`nomeCliente` AS `nomeCliente`,\n"
                    + "        `c`.`nif` AS `nif`,\n"
                    + "        `c`.`contato` AS `contato`,\n"
                    + "        `c`.`tipoCliente` AS `tipoCliente`,\n"
                    + "        `c`.`descricao` AS `descricao`,\n"
                    + "        `c`.`endereco` AS `endereco`,\n"
                    + "        `c`.`codigoPostal` AS `codigoPostal`,\n"
                    + "        `c`.`localidade` AS `localidade`,\n"
                    + "        `u`.`id_usuario` AS `id_usuario`,\n"
                    + "        `u`.`nome` AS `nome`,\n"
                    + "        `v`.`precoTotal` AS `precoTotal`\n"
                    + "    FROM\n"
                    + "        ((`" + DB + "`.`tb_vendas` `v`\n"
                    + "        JOIN `" + DB + "`.`tb_clientes` `c` ON ((`c`.`id_clientes` = `v`.`cliente_fk`)))\n"
                    + "        JOIN `" + DB + "`.`tb_usuario` `u` ON ((`u`.`id_usuario` = `v`.`id_user`)));");
            pst.execute();

            //View resultado de exame
            pst = con.begin().prepareStatement("DROP VIEW IF EXISTS `" + DB + "`.`resultado_de_exame_view`;");
            pst.execute();

            pst = con.begin().prepareStatement("CREATE \n"
                    + "    ALGORITHM = UNDEFINED \n"
                    + "    DEFINER = `root`@`localhost` \n"
                    + "    SQL SECURITY DEFINER\n"
                    + "VIEW `" + DB + "`.`resultado_de_exame_view` AS\n"
                    + "    SELECT \n"
                    + "        `" + DB + "`.`tb_exame_resultado`.`id_exame_resultado` AS `id_exame_resultado`,\n"
                    + "        `exame_view`.`Codigo` AS `Codigo`,\n"
                    + "        `exame_view`.`Tipo de Exame` AS `Tipo de Exame`,\n"
                    + "        `exame_view`.`Dia` AS `Dia`,\n"
                    + "        `exame_view`.`Hora` AS `Hora`,\n"
                    + "        `exame_view`.`Descrição` AS `Descrição`,\n"
                    + "        `exame_view`.`Codigo da Categoria` AS `Codigo da Categoria`,\n"
                    + "        `exame_view`.`Categoria` AS `Categoria`,\n"
                    + "        `exame_view`.`Codigo Do Aluno` AS `Codigo Do Aluno`,\n"
                    + "        `exame_view`.`Nome Do Aluno` AS `Nome Do Aluno`,\n"
                    + "        `" + DB + "`.`tb_exame_resultado`.`resultado` AS `Resultado`\n"
                    + "    FROM\n"
                    + "        (`" + DB + "`.`tb_exame_resultado`\n"
                    + "        JOIN `" + DB + "`.`exame_view` ON ((`" + DB + "`.`tb_exame_resultado`.`fk_exame` = `exame_view`.`Codigo`)));");
            pst.execute();

            //View Item de Venda
            pst = con.begin().prepareStatement("DROP VIEW IF EXISTS `" + DB + "`.`item_de_venda_view`;");
            pst.execute();

            pst = con.begin().prepareStatement("CREATE \n"
                    + "    ALGORITHM = UNDEFINED \n"
                    + "    DEFINER = `root`@`localhost` \n"
                    + "    SQL SECURITY DEFINER\n"
                    + "VIEW `" + DB + "`.`item_de_venda_view` AS\n"
                    + "    SELECT \n"
                    + "        `iv`.`id_item_venda` AS `id_item_venda`,\n"
                    + "        `ar`.`id_artigo` AS `id_artigo`,\n"
                    + "        `ar`.`nomeArtigo` AS `nomeArtigo`,\n"
                    + "        `ar`.`descricao` AS `descricao`,\n"
                    + "        `ar`.`preco` AS `preco`,\n"
                    + "        `iv`.`quantidade` AS `quantidade`,\n"
                    + "        `iv`.`valor` AS `valor`,\n"
                    + "        `v`.`id_vendas` AS `id_vendas`,\n"
                    + "        `v`.`num_fatura` AS `num_fatura`,\n"
                    + "        `v`.`data` AS `data`,\n"
                    + "        `v`.`pago` AS `pago`,\n"
                    + "        `v`.`meioDePag` AS `meioDePag`,\n"
                    + "        `v`.`valor_total` AS `valor_total`,\n"
                    + "        `v`.`desconto` AS `desconto`,\n"
                    + "        `c`.`id_clientes` AS `id_clientes`,\n"
                    + "        `c`.`nomeCliente` AS `nomeCliente`,\n"
                    + "        `c`.`nif` AS `nif`,\n"
                    + "        `c`.`contato` AS `contato`,\n"
                    + "        `c`.`tipoCliente` AS `tipoCliente`,\n"
                    + "        `c`.`endereco` AS `endereco`,\n"
                    + "        `c`.`codigoPostal` AS `codigoPostal`,\n"
                    + "        `c`.`localidade` AS `localidade`,\n"
                    + "        `u`.`id_usuario` AS `id_usuario`,\n"
                    + "        `u`.`nome` AS `nome`,\n"
                    + "        `v`.`precoTotal` AS `precoTotal`\n"
                    + "    FROM\n"
                    + "        ((((`" + DB + "`.`tb_item_venda` `iv`\n"
                    + "        JOIN `" + DB + "`.`tb_artigo` `ar` ON ((`ar`.`id_artigo` = `iv`.`id_artigo`)))\n"
                    + "        JOIN `" + DB + "`.`tb_vendas` `v` ON ((`v`.`id_vendas` = `iv`.`id_venda`)))\n"
                    + "        JOIN `" + DB + "`.`tb_clientes` `c` ON ((`c`.`id_clientes` = `v`.`cliente_fk`)))\n"
                    + "        JOIN `" + DB + "`.`tb_usuario` `u` ON ((`u`.`id_usuario` = `v`.`id_user`)));");
            pst.execute();

            msg += "\nViews criado com sucesso";
            LOGGER.error(msg);
        } catch (SQLException ex) {
            LOGGER.error("error\n" + ex.getMessage());
        }
    }

    // Criando banco de dados
    public static void createDataBase() {
        loadPropertiesFile();
        ConnectionManager con = ConnectionManager.getInstance();

        try {
            msg += "\nCriando base de dado....";
            pst = con.begin().prepareStatement("create database if not exists " + DB + " DEFAULT CHARACTER SET utf8 \n"
                    + "  DEFAULT COLLATE utf8_general_ci;");
            pst.execute();

            // Tornando db como banco deflaut
            pst = con.begin().prepareStatement("use " + DB + ";");
            pst.execute();
            msg += "\nBase de dado a ser criado.\nCriando tabelas ...";

            // Tabela tipo de usuario
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_tipo_usuario` (\n"
                    + "  `id_tipo_usuario` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome` varchar(150) NOT NULL,\n"
                    + "  PRIMARY KEY (`id_tipo_usuario`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            // Criando tabela de usuario 
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_usuario` (\n"
                    + "  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome` varchar(200) NOT NULL,\n"
                    + "  `login` varchar(150) NOT NULL,\n"
                    + "  `senha` varchar(200) NOT NULL,\n"
                    + "  `email` text NOT NULL,\n"
                    + "  `status` tinyint(1) NOT NULL,\n"
                    + "  `descricao` text NOT NULL,\n"
                    + "  `data_criacao` date NOT NULL,\n"
                    + "  `fk_tipo_usuario` int(11) NOT NULL,\n"
                    + "  PRIMARY KEY (`id_usuario`),\n"
                    + "  KEY `fk_tipo_usuario` (`fk_tipo_usuario`),\n"
                    + "  CONSTRAINT `tb_usuario_ibfk_1` FOREIGN KEY (`fk_tipo_usuario`) REFERENCES `tb_tipo_usuario` (`id_tipo_usuario`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //criar tabela empresa
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_empresa` (\n"
                    + "  `id_empresa` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome` varchar(200) NOT NULL,\n"
                    + "  `cidade` varchar(200) NOT NULL,\n"
                    + "  `nif` varchar(50) NOT NULL,\n"
                    + "  `endereco` varchar(80) NOT NULL,\n"
                    + "  `email` varchar(150) DEFAULT NULL,\n"
                    + "  `contato` varchar(150) NOT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  `logo` longblob,\n"
                    + "  `assinatura` longblob,\n"
                    + "  PRIMARY KEY (`id_empresa`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            // criar tabela cliente
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_clientes` (\n"
                    + "  `id_clientes` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nomeCliente` varchar(145) NOT NULL,\n"
                    + "  `nif` varchar(95) DEFAULT NULL,\n"
                    + "  `contato` varchar(45) DEFAULT NULL,\n"
                    + "  `tipoCliente` varchar(45) DEFAULT NULL,\n"
                    + "  `descricao` longtext,\n"
                    + "  `endereco` varchar(200) DEFAULT NULL,\n"
                    + "  `codigoPostal` varchar(45) DEFAULT NULL,\n"
                    + "  `localidade` varchar(185) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`id_clientes`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            // Tabela Artigo
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_artigo` (\n"
                    + "  `id_artigo` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nomeArtigo` varchar(150) NOT NULL,\n"
                    + "  `preco` decimal(9,2) NOT NULL,\n"
                    + "  `descricao` longtext,\n"
                    + "  PRIMARY KEY (`id_artigo`),\n"
                    + "  UNIQUE KEY `nomeArtigo_UNIQUE` (`nomeArtigo`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            // criar tabela forma de pagmento
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_forma_de_pagamento` (\n"
                    + "  `id_forma_pagamento` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `froma_de_pag` varchar(150) NOT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  PRIMARY KEY (`id_forma_pagamento`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            // criar tabela organizacao
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_organizacao` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome` varchar(200) NOT NULL,\n"
                    + "  `bairro` varchar(100) DEFAULT NULL,\n"
                    + "  `cidade` varchar(100) NOT NULL,\n"
                    + "  `email` text NOT NULL,\n"
                    + "  `estado` varchar(150) DEFAULT NULL,\n"
                    + "  `fax` varchar(80) DEFAULT NULL,\n"
                    + "  `logradouro` text,\n"
                    + "  `pais` varchar(150) NOT NULL,\n"
                    + "  `sigla` varchar(50) DEFAULT NULL,\n"
                    + "  `telefone` varchar(50) NOT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  PRIMARY KEY (`id`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            // criar tabela benificio
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_benificio` (\n"
                    + "  `id_benificio` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome_benificio` varchar(100) NOT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  PRIMARY KEY (`id_benificio`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            // Tabela Cargo
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_cargo` (\n"
                    + "  `id_cargo` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome_cargo` varchar(45) NOT NULL,\n"
                    + "  PRIMARY KEY (`id_cargo`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Cargo e Salario
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_cargo_salario` (\n"
                    + "  `id_cargo_salario` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `cargo` varchar(100) NOT NULL,\n"
                    + "  `salario` double NOT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  PRIMARY KEY (`id_cargo_salario`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Categoria de Carta de conducao
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_categoria` (\n"
                    + "  `id_categoria` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `categoria` varchar(50) NOT NULL,\n"
                    + "  `descricao` text NOT NULL,\n"
                    + "  PRIMARY KEY (`id_categoria`),\n"
                    + "  UNIQUE KEY `categoria` (`categoria`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Aluno
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_aluno` (\n"
                    + "  `id_aluno` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome` text NOT NULL,\n"
                    + "  `dataNascimento` date NOT NULL,\n"
                    + "  `numBI` varchar(50) NOT NULL,\n"
                    + "  `dataEmisao` date NOT NULL,\n"
                    + "  `resedencia` varchar(200) NOT NULL,\n"
                    + "  `conselho` varchar(200) NOT NULL,\n"
                    + "  `naturalidade` varchar(200) NOT NULL,\n"
                    + "  `email` varchar(200) NOT NULL,\n"
                    + "  `contato` varchar(100) NOT NULL,\n"
                    + "  `habilitacaoLit` varchar(200) NOT NULL,\n"
                    + "  `nacionalidade` varchar(150) NOT NULL,\n"
                    + "  `foto` longblob,\n"
                    + "  `fotocopiaBI` longblob,\n"
                    + "  `descricao` text,\n"
                    + "  `data_cadastro` date NOT NULL,\n"
                    + "  `nomeDaMae` varchar(150) DEFAULT NULL,\n"
                    + "  `nomeDoPai` varchar(150) DEFAULT NULL,\n"
                    + "  `professao` varchar(80) DEFAULT NULL,\n"
                    + "  `estadoCivil` varchar(45) NOT NULL,\n"
                    + "  `localDeEmisao` varchar(85) NOT NULL,\n"
                    + "  `freguesia` varchar(150) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`id_aluno`),\n"
                    + "  UNIQUE KEY `numBI_UNIQUE` (`numBI`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Instrutor
            pst = con.begin().prepareStatement("CREATE TABLE IF NOT EXISTS " + DB + ".`tb_instrutor` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome` varchar(255) NOT NULL,\n"
                    + "  `admissao` date DEFAULT NULL,\n"
                    + "  `email` varchar(255) DEFAULT NULL,\n"
                    + "  `telefone` varchar(45) DEFAULT NULL,\n"
                    + "  `movel` varchar(45) NOT NULL,\n"
                    + "  `foto` varchar(255) DEFAULT NULL,\n"
                    + "  `pai` varchar(255) DEFAULT NULL,\n"
                    + "  `mae` varchar(255) DEFAULT NULL,\n"
                    + "  `grauAcademico` varchar(155) NOT NULL,\n"
                    + "  `tipoSanguineo` varchar(45) DEFAULT NULL,\n"
                    + "  `morada` varchar(255) NOT NULL,\n"
                    + "  `cidadeIlha` varchar(45) NOT NULL,\n"
                    + "  `numeroDeIndentificacao` varchar(95) NOT NULL,\n"
                    + "  `naturalidade` varchar(255) DEFAULT NULL,\n"
                    + "  `nacionalidade` varchar(255) NOT NULL,\n"
                    + "  `nascimento` date DEFAULT NULL,\n"
                    + "  `cartaConducao` varchar(45) NOT NULL,\n"
                    + "  `banco` varchar(105) DEFAULT NULL,\n"
                    + "  `agencia` varchar(95) DEFAULT NULL,\n"
                    + "  `numDeConta` varchar(45) DEFAULT NULL,\n"
                    + "  `obsercacao` longtext,\n"
                    + "  PRIMARY KEY (`id`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            // Tabela Curso
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_curso` (\n"
                    + "  `codigo` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome_curso` varchar(150) NOT NULL,\n"
                    + "  `duracao` int(11) NOT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  `fk_categoria` int(11) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`codigo`),\n"
                    + "  KEY `fk_categoria` (`fk_categoria`),\n"
                    + "  CONSTRAINT `tb_curso_ibfk_1` FOREIGN KEY (`fk_categoria`) REFERENCES `tb_categoria` (`id_categoria`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Exame
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_exame` (\n"
                    + "  `id_exame` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `tipo_exame` varchar(80) NOT NULL,\n"
                    + "  `dia` date NOT NULL,\n"
                    + "  `hora` time NOT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  `fk_categoria` int(11) NOT NULL,\n"
                    + "  `fk_aluno` int(11) NOT NULL,\n"
                    + "  `registroCriminal` varchar(255) DEFAULT NULL,\n"
                    + "  `atestadoMedico` varchar(255) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`id_exame`),\n"
                    + "  KEY `fk_categoria` (`fk_categoria`),\n"
                    + "  KEY `fk_aluno` (`fk_aluno`),\n"
                    + "  CONSTRAINT `tb_exame_ibfk_1` FOREIGN KEY (`fk_aluno`) REFERENCES `tb_aluno` (`id_aluno`),\n"
                    + "  CONSTRAINT `tb_exame_ibfk_2` FOREIGN KEY (`fk_categoria`) REFERENCES `tb_categoria` (`id_categoria`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Resultado de Exame
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_exame_resultado` (\n"
                    + "  `id_exame_resultado` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `fk_exame` int(11) NOT NULL,\n"
                    + "  `resultado` varchar(45) NOT NULL,\n"
                    + "  PRIMARY KEY (`id_exame_resultado`,`fk_exame`),\n"
                    + "  KEY `fk_exame_idx` (`fk_exame`),\n"
                    + "  CONSTRAINT `fk_exame` FOREIGN KEY (`fk_exame`) REFERENCES `tb_exame` (`id_exame`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Propretario
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_proprietario` (\n"
                    + "  `id_Proprietario` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome_Proprietario` varchar(155) DEFAULT NULL,\n"
                    + "  `bi_Proprietario` varchar(45) NOT NULL,\n"
                    + "  `contato_Proprietario` varchar(45) DEFAULT NULL,\n"
                    + "  `email_Proprietario` varchar(200) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`id_Proprietario`),\n"
                    + "  UNIQUE KEY `bi_Proprietario_UNIQUE` (`bi_Proprietario`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Veiculo
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_veiculo` (\n"
                    + "  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,\n"
                    + "  `placa` varchar(50) NOT NULL,\n"
                    + "  `ilha` varchar(100) NOT NULL,\n"
                    + "  `fabricante` varchar(100) NOT NULL,\n"
                    + "  `modelo` varchar(100) NOT NULL,\n"
                    + "  `anoFabricacao` int(11) NOT NULL,\n"
                    + "  `anoModelo` int(11) NOT NULL,\n"
                    + "  `chassi` varchar(80) NOT NULL,\n"
                    + "  `tipoCombustivel` varchar(100) NOT NULL,\n"
                    + "  `nomeProprietario` varchar(200) NOT NULL,\n"
                    + "  `contatoProprietario` varchar(80) NOT NULL,\n"
                    + "  `emailProprietario` text,\n"
                    + "  `dataCadastro` datetime NOT NULL,\n"
                    + "  `dataModificacao` datetime DEFAULT NULL,\n"
                    + "  `especificacao` text,\n"
                    + "  PRIMARY KEY (`codigo`),\n"
                    + "  UNIQUE KEY `placa_UNIQUE` (`placa`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Seguro Auto
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_seguro` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `compania` varchar(95) DEFAULT NULL,\n"
                    + "  `veiculo_seguro` bigint(20) NOT NULL,\n"
                    + "  `desde` date NOT NULL,\n"
                    + "  `ate` date NOT NULL,\n"
                    + "  `emissao` date NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  KEY `fk_veiculo_idx` (`veiculo_seguro`),\n"
                    + "  KEY `fk_veiculo_seguro_idx` (`veiculo_seguro`),\n"
                    + "  CONSTRAINT `fk_veiculo_seguro` FOREIGN KEY (`veiculo_seguro`) REFERENCES `tb_veiculo` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Inspecao Tecnica
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_inspecao_tecnica` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `veiculo` bigint(20) NOT NULL,\n"
                    + "  `tipoInspecao` varchar(65) NOT NULL,\n"
                    + "  `dataInspecao` date NOT NULL,\n"
                    + "  `duracao` int(11) NOT NULL,\n"
                    + "  `resultado` varchar(45) NOT NULL,\n"
                    + "  `validade` varchar(45) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  KEY `fk_veiculo_idx` (`veiculo`),\n"
                    + "  CONSTRAINT `fk_veiculo` FOREIGN KEY (`veiculo`) REFERENCES `tb_veiculo` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Tipo de telefone
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_tipo_telefone` (\n"
                    + "  `id_tipo_telefone` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `tipo_telefone` varchar(45) NOT NULL,\n"
                    + "  PRIMARY KEY (`id_tipo_telefone`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Turma
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_turma` (\n"
                    + "  `id_turma` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `fk_curso` int(11) NOT NULL,\n"
                    + "  `periodo` varchar(45) NOT NULL,\n"
                    + "  `numero_aluno` int(11) NOT NULL,\n"
                    + "  `data_inicio` date NOT NULL,\n"
                    + "  `data_fim` date NOT NULL,\n"
                    + "  PRIMARY KEY (`id_turma`),\n"
                    + "  KEY `fk_curso_idx` (`fk_curso`),\n"
                    + "  CONSTRAINT `fk_curso` FOREIGN KEY (`fk_curso`) REFERENCES `tb_curso` (`codigo`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Matricula
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_matricula` (\n"
                    + "  `id_matricula` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `data` date NOT NULL,\n"
                    + "  `aluno_id` int(11) NOT NULL,\n"
                    + "  `curso_id` int(11) NOT NULL,\n"
                    + "  `turma` varchar(45) NOT NULL,\n"
                    + "  `periodo` varchar(45) NOT NULL,\n"
                    + "  `obs` longtext,\n"
                    + "  PRIMARY KEY (`id_matricula`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Doc
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_doc` (\n"
                    + "  `numero_Doc` varchar(50) NOT NULL,\n"
                    + "  `data_Emisso` date NOT NULL,\n"
                    + "  `local_Emissao` varchar(160) NOT NULL,\n"
                    + "  `estado_civil` varchar(45) NOT NULL,\n"
                    + "  PRIMARY KEY (`numero_Doc`),\n"
                    + "  UNIQUE KEY `numero_Doc_UNIQUE` (`numero_Doc`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Categoria de Bens Da Empresa
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_categoria_bens_pratemoniais` (\n"
                    + "  `id_categoria_bens` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `categoria_bens` int(11) NOT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  PRIMARY KEY (`id_categoria_bens`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Departamento
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_departamento` (\n"
                    + "  `id_departamento` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nome_departamento` varchar(45) NOT NULL,\n"
                    + "  `descricao` longtext,\n"
                    + "  PRIMARY KEY (`id_departamento`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Contrato
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_contrato` (\n"
                    + "  `id_contrato` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `dataAssinatura` date NOT NULL,\n"
                    + "  `valor` double NOT NULL,\n"
                    + "  `tipoContrato` varchar(100) NOT NULL,\n"
                    + "  `data_inic_vegenc` date NOT NULL,\n"
                    + "  `duracao_Contrato` int(11) NOT NULL,\n"
                    + "  `dataVencimento` date NOT NULL,\n"
                    + "  `status` varchar(200) DEFAULT NULL,\n"
                    + "  `renovado` varchar(50) DEFAULT NULL,\n"
                    + "  `dataRenovacao` date DEFAULT NULL,\n"
                    + "  `novoValor` double DEFAULT NULL,\n"
                    + "  `newData_vencimento` date DEFAULT NULL,\n"
                    + "  `descricao` text,\n"
                    + "  `fk_aluno` int(11) NOT NULL,\n"
                    + "  PRIMARY KEY (`id_contrato`),\n"
                    + "  KEY `fk_aluno` (`fk_aluno`),\n"
                    + "  CONSTRAINT `tb_contrato_ibfk_1` FOREIGN KEY (`fk_aluno`) REFERENCES `tb_aluno` (`id_aluno`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Venda
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_vendas` (\n"
                    + "  `id_vendas` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `data` date NOT NULL,\n"
                    + "  `valor_total` decimal(9,2) NOT NULL,\n"
                    + "  `pago` tinyint(1) NOT NULL,\n"
                    + "  `cliente_fk` int(11) NOT NULL,\n"
                    + "  `id_user` int(11) NOT NULL,\n"
                    + "  `meioDePag` varchar(65) NOT NULL,\n"
                    + "  `desconto` float(9,2) DEFAULT NULL,\n"
                    + "  `num_fatura` varchar(55) NOT NULL,\n"
                    + "  `precoTotal` float(10,2) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`id_vendas`,`num_fatura`),\n"
                    + "  UNIQUE KEY `num_fatura_UNIQUE` (`num_fatura`),\n"
                    + "  KEY `fk_cliente_idx` (`cliente_fk`),\n"
                    + "  KEY `fk_usuario_idx` (`id_user`),\n"
                    + "  KEY `id_user_idx` (`id_user`),\n"
                    + "  CONSTRAINT `cliente_fk` FOREIGN KEY (`cliente_fk`) REFERENCES `tb_clientes` (`id_clientes`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n"
                    + "  CONSTRAINT `id_user` FOREIGN KEY (`id_user`) REFERENCES `tb_usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //Tabela Item de Venda
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_item_venda` (\n"
                    + "  `id_item_venda` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `quantidade` varchar(45) NOT NULL,\n"
                    + "  `valor` float(9,2) NOT NULL,\n"
                    + "  `id_artigo` int(11) NOT NULL,\n"
                    + "  `id_venda` int(11) NOT NULL,\n"
                    + "  PRIMARY KEY (`id_item_venda`),\n"
                    + "  KEY `fk_artigo` (`id_artigo`),\n"
                    + "  KEY `fk_venda` (`id_venda`),\n"
                    + "  CONSTRAINT `fk_artigo` FOREIGN KEY (`id_artigo`) REFERENCES `tb_artigo` (`id_artigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n"
                    + "  CONSTRAINT `fk_venda` FOREIGN KEY (`id_venda`) REFERENCES `tb_vendas` (`id_vendas`) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            //tabela Inscricao
            pst = con.begin().prepareStatement("CREATE TABLE if not exists " + DB + ".`tb_matricula` (\n"
                    + "  `id_matricula` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `data` date NOT NULL,\n"
                    + "  `aluno_id` int(11) NOT NULL,\n"
                    + "  `curso_id` int(11) NOT NULL,\n"
                    + "  `turma` varchar(45) NOT NULL,\n"
                    + "  `periodo` varchar(45) NOT NULL,\n"
                    + "  `obs` longtext,\n"
                    + "  PRIMARY KEY (`id_matricula`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            pst.execute();

            msg += "\nTabelas criado com sucesso";

            LOGGER.error(msg);

            esperar(100);

            msg += "\nAlterando Tabelas ......";
            //Alterando a estrutura da coluna
            pst = con.begin().prepareStatement("ALTER TABLE `" + DB + "`.`tb_matricula` \n"
                    + "DROP FOREIGN KEY `fk_turma`,\n"
                    + "DROP FOREIGN KEY `fk_aluno`;");
            pst.execute();

            pst = con.begin().prepareStatement("ALTER TABLE `" + DB + "`.`tb_matricula` \n"
                    + "CHANGE COLUMN `data_matricula` `data` DATE NOT NULL ,\n"
                    + "CHANGE COLUMN `cod_aluno` `curso_id` INT(11) NOT NULL ,\n"
                    + "CHANGE COLUMN `cod_turma` `aluno_id` INT(11) NOT NULL ,\n"
                    + "ADD COLUMN `turma` VARCHAR(45) NULL AFTER `aluno_id`,\n"
                    + "ADD COLUMN `periodo` VARCHAR(45) NOT NULL AFTER `turma`,\n"
                    + "ADD COLUMN `obs` LONGTEXT NULL AFTER `periodo`,\n"
                    + "DROP INDEX `fk_turma_idx` ,\n"
                    + "DROP INDEX `fk_aluno_idx` ;");
            pst.execute();

            // Fazendo alteracao na tabela empresa
            pst = con.begin().prepareStatement("ALTER TABLE `" + DB + "`.`tb_empresa` \n"
                    + "DROP COLUMN `ano_vigencia`,\n"
                    + "ADD COLUMN `assinatura` LONGBLOB NULL AFTER `logo`,\n"
                    + "CHANGE COLUMN `razao_social` `nome` VARCHAR(200) NOT NULL ,\n"
                    + "CHANGE COLUMN `nome` `cidade` VARCHAR(200) NOT NULL ,\n"
                    + "CHANGE COLUMN `cidade` `endereco` VARCHAR(250) NOT NULL ,\n"
                    + "CHANGE COLUMN `estado` `email` VARCHAR(250) NULL DEFAULT NULL ,\n"
                    + "CHANGE COLUMN `pais` `contato` VARCHAR(150) NOT NULL ;");
            pst.execute();

            esperar(200);

        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    private static void esperar(long milesegundos) {
        try {
            Thread.sleep(milesegundos);
        } catch (InterruptedException e) {
            LOGGER.error("error\n" + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
