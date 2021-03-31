package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Organizacao;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.OrganizacaoDAO;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Tempo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganizacaoDAOImpl extends DAO implements OrganizacaoDAO {

    public OrganizacaoDAOImpl() {
        super();
    }

    @Override
    public void create(Organizacao organizacao) {
        try {
            String sql = "INSERT INTO "+ db +".tb_organizacao(nome, sigla, email, fax, telefone, logradouro, bairro, cidade, estado, pais, descricao, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setString(1, organizacao.getNome());
            preparedStatement.setString(2, organizacao.getSigla());
            preparedStatement.setString(3, organizacao.getEmail());
            preparedStatement.setString(4, organizacao.getFax());
            preparedStatement.setString(5, organizacao.getTelefone());
            preparedStatement.setString(6, organizacao.getLogradouro());
            preparedStatement.setString(7, organizacao.getBairro());
            preparedStatement.setString(8, organizacao.getCidade());
            preparedStatement.setString(9, organizacao.getEstado());
            preparedStatement.setString(10, organizacao.getPais());
            preparedStatement.setString(11, organizacao.getDescricao());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir organização na base de dados! \n" + ex);
        }
    }

    @Override
    public void update(Organizacao organizacao) {
        try {
            String sql = "UPDATE "+ db +".tb_organizacao SET nome=?, sigla=?, email=?, fax=?, telefone=?, logradouro=?, bairro=?, cidade=?, estado=?, pais=?, descricao=? WHERE id_orgao=? ";

            preparedStatement = conector.prepareStatement(sql);

            preparedStatement.setString(1, organizacao.getNome());
            preparedStatement.setString(2, organizacao.getSigla());
            preparedStatement.setString(3, organizacao.getEmail());
            preparedStatement.setString(4, organizacao.getFax());
            preparedStatement.setString(5, organizacao.getTelefone());
            preparedStatement.setString(6, organizacao.getLogradouro());
            preparedStatement.setString(7, organizacao.getBairro());
            preparedStatement.setString(8, organizacao.getCidade());
            preparedStatement.setString(9, organizacao.getEstado());
            preparedStatement.setString(10, organizacao.getPais());
            preparedStatement.setString(11, organizacao.getDescricao());

            preparedStatement.setInt(12, organizacao.getId());

            preparedStatement.executeUpdate();
            conector.commit();
            preparedStatement.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar organização na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Integer idOrganizacao) {
        try {
            String sql = "DELETE FROM "+ db +".tb_organizacao WHERE id_orgao =?";

            preparedStatement = conector.prepareStatement(sql);
            preparedStatement.setInt(1, idOrganizacao);

            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir organização na base de dados! \n" + ex);
        }
    }

    @Override
    public List<Organizacao> findAll() {
        List<Organizacao> dados = new ArrayList<>();
        try {
            String sql = "SELECT * FROM "+ db +".tb_organizacao";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Organizacao organizacao = new Organizacao(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), Tempo.toDate(rs.getTimestamp(13)));
                dados.add(organizacao);
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar organizações na base de dados! \n" + ex);
        }

        return dados;
    }

    @Override
    public List<Organizacao> combo() {
        List<Organizacao> dados = new ArrayList<>();
        try {
            String sql = "SELECT id_orgao, nome FROM "+ db +".tb_organizacao ORDER BY nome ";
            preparedStatement = conector.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                dados.add(new Organizacao(rs.getInt(1), rs.getString(2)));
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar organizações na base de dados! \n" + ex);
        }

        return dados;
    }
}
