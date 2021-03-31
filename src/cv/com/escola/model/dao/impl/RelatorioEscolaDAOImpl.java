/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.dao.impl;

import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.RelatorioEscolaDAO;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Nota;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Isaquiel Fernandes
 */
public class RelatorioEscolaDAOImpl extends DAO implements RelatorioEscolaDAO {

    public RelatorioEscolaDAOImpl() {
        super();
    }

    //contar o numero de exame marcado por ano
    @Override
    public int count(Year ano) {
        try {
            String sql = "SELECT count(id_exame) FROM " + db + ".`tb_exame` where extract(year from dia) = " + ano + "";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados\n!" + ex);
        }
        return 0;
    }

    //contar o numero de exame marcado por tipo e ano
    @Override
    public int count(String tipo, Year ano) {
        try {
            String sql = "SELECT count(id_exame) FROM " + db + ".`tb_exame` where tipo_exame = '" + tipo + "' and extract(year from dia) = " + ano + "";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados\n!" + ex);
        }
        return 0;
    }
    
    //contar o numero de aprovado, reprovado, faltou por ano
    @Override
    public int countResultado(String resultado, String ano) {
        try {
            String sql = "SELECT count(r.`id_exame_resultado`) as Qtd FROM " + db + ".`resultado_de_exame_view` r where r.`Resultado` = '"+ resultado +"' and  extract(year from r.`Dia`) = " + ano + ";";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados\n!");
        }
        return 0;
    }
    
    //contar o numero de aprovado, reprovado, faltou por ano
    @Override
    public int countResultadoPorTipoExame(String tipo, String resultado, String ano) {
        try {
            String sql = "SELECT count(r.`id_exame_resultado`) as Qtd FROM " + db + ".`resultado_de_exame_view` r where r.`Tipo De Exame` ='" + tipo +"' and r.`Resultado` = '"+ resultado +"' and  extract(year from r.`Dia`) = " + ano + ";";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados\n!");
        }
        return 0;
    }

    @Override
    public Map<String, ArrayList> listarExamePorTipoDeExame(Year ano) {
        String sql = "SELECT count(id_exame) as Qtd, tipo_exame, extract(year from dia) as ano FROM " + db + ".tb_exame where extract(year from dia) = " + ano + " group by tipo_exame;";
        Map<String, ArrayList> retorno = new HashMap();
        try {
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList linha = new ArrayList();
                linha.add(rs.getInt("Qtd"));
                linha.add(rs.getString("tipo_exame"));
                retorno.put(rs.getString("tipo_exame"), linha);

            }
            return retorno;
        } catch (SQLException ex) {
            Nota.erro("Erro: " + ex);
        }
        return retorno;
    }
}
