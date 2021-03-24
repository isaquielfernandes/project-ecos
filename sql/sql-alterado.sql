-- Alterando Tabela Matricula

ALTER TABLE `tb_matricula` 
DROP FOREIGN KEY `fk_turma`, 
DROP FOREIGN KEY `fk_aluno`;

ALTER TABLE `tb_matricula` 
DROP INDEX `fk_turma_idx` ,
DROP INDEX `fk_aluno_idx` ;

ALTER TABLE `tb_matricula`
CHANGE COLUMN `data_matricula` `data` DATE NOT NULL ,
CHANGE COLUMN `cod_aluno` `curso_id` INT(11) NOT NULL ,
CHANGE COLUMN `cod_turma` `aluno_id` INT(11) NOT NULL;

ALTER TABLE `tb_matricula`
ADD COLUMN `turma` VARCHAR(45) NULL AFTER `aluno_id`,
ADD COLUMN `periodo` VARCHAR(45) NOT NULL AFTER `turma`,
ADD COLUMN `obs` LONGTEXT NULL AFTER `periodo`;

                    
-- Fazendo alteracao na tabela empresa

ALTER TABLE `tb_empresa` 
DROP COLUMN `ano_vigencia`,
ADD COLUMN `assinatura` LONGBLOB NULL AFTER `logo`,
CHANGE COLUMN `razao_social` `nome` VARCHAR(200) NOT NULL ,
CHANGE COLUMN `nome` `cidade` VARCHAR(200) NOT NULL ,
CHANGE COLUMN `cidade` `endereco` VARCHAR(250) NOT NULL ,
CHANGE COLUMN `estado` `email` VARCHAR(250) NULL DEFAULT NULL ,
CHANGE COLUMN `pais` `contato` VARCHAR(150) NOT NULL ;

USE `dbescola`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `venda_view` AS
    SELECT 
        `v`.`id_vendas` AS `id_vendas`,
        `v`.`data` AS `data`,
        `v`.`valor_total` AS `valor_total`,
        `v`.`pago` AS `pago`,
        `v`.`meioDePag` AS `meioDePag`,
        `v`.`desconto` AS `desconto`,
        `v`.`num_fatura` AS `num_fatura`,
        `c`.`id_clientes` AS `id_clientes`,
        `c`.`nomeCliente` AS `nomeCliente`,
        `c`.`nif` AS `nif`,
        `c`.`contato` AS `contato`,
        `c`.`tipoCliente` AS `tipoCliente`,
        `c`.`descricao` AS `descricao`,
        `c`.`endereco` AS `endereco`,
        `c`.`codigoPostal` AS `codigoPostal`,
        `c`.`localidade` AS `localidade`,
        `u`.`id_usuario` AS `id_usuario`,
        `u`.`nome` AS `nome`,
        `v`.`precoTotal` AS `precoTotal`
    FROM
        ((`tb_vendas` `v`
        JOIN `tb_clientes` `c` ON ((`c`.`id_clientes` = `v`.`cliente_fk`)))
        JOIN `tb_usuario` `u` ON ((`u`.`id_usuario` = `v`.`id_user`)));

describe tb_empresa;
select * from tb_empresa;