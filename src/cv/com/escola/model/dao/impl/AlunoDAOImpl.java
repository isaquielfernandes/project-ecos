package cv.com.escola.model.dao.impl;

import cv.com.escola.model.entity.Aluno;
import cv.com.escola.model.entity.Pagina;
import cv.com.escola.model.dao.AlunoDAO;
import cv.com.escola.model.dao.DAO;
import cv.com.escola.model.dao.exception.DaoException;
import cv.com.escola.model.util.Mensagem;
import cv.com.escola.model.util.Print;
import cv.com.escola.model.util.Tempo;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class AlunoDAOImpl extends DAO implements AlunoDAO {

    public AlunoDAOImpl() {
        super();
    }

    @Override
    public void create(Aluno aluno) {
        try {
            String sql = "INSERT INTO " + db + ".tb_aluno ( nome, dataNascimento, numBI, dataEmisao, resedencia, conselho,"
                    + "naturalidade, email, contato, habilitacaoLit, nacionalidade, "
                    + "foto, fotocopiaBI, descricao, data_cadastro, nomeDaMae, nomeDoPai, professao, estadoCivil, localDeEmisao, freguesia) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(),?,?,?,?,?,?)";
        
            stm = conector.prepareStatement(sql);

            stm.setString(1, aluno.getNome());
            stm.setTimestamp(2, Tempo.toTimestamp(aluno.getDataNascimento()));
            stm.setString(3, aluno.getNumBI());
            stm.setTimestamp(4, Tempo.toTimestamp(aluno.getDataEmisao()));
            stm.setString(5, aluno.getResidencia());
            stm.setString(6, aluno.getConselho());
            stm.setString(7, aluno.getNatural());
            stm.setString(8, aluno.getEmail());
            stm.setString(9, aluno.getContato());
            stm.setString(10, aluno.getHabilitacaoLit());
            stm.setString(11, aluno.getNacionalidade());
            //String name = (file != null ? file.getPath() : "/cv/com/escola/img/avater.jpg");
            if (aluno.getFile() != null) {
                aluno.setFileInputStream(new FileInputStream(aluno.getFile()));
                stm.setBinaryStream(12, aluno.getFileInputStream(), aluno.getFile().length());
            } else {
                stm.setBinaryStream(12, null);
            }
            if (aluno.filePath != null) {
                InputStream is;
                is = new FileInputStream(new File(aluno.filePath));
                stm.setBlob(13, is);
            } else if (aluno.filePath == null) {
                stm.setBlob(13, (Blob) null);
            }
            stm.setString(14, aluno.getDescricao());
            stm.setString(15, aluno.getNomeDaMae());
            stm.setString(16, aluno.getNomeDoPai());
            stm.setString(17, aluno.getProfessao());
            stm.setString(18, aluno.getEstadoCivil());
            stm.setString(19, aluno.getLocalDeEmisao());
            stm.setString(20, aluno.getFreguesia());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Aluno cadastrada com sucesso!");
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir aluno na base de dados! \n" + ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao caregar imagem  ! \n" + ex);
        }
    }

    @Override
    public void update(Aluno aluno) {
        try {
            String sql = "UPDATE " + db + ".tb_aluno SET nome=?, dataNascimento=?, numBI=?, dataEmisao=?, resedencia=?, conselho=?,"
                    + "naturalidade=?, email=?, contato=?, habilitacaoLit=?, nacionalidade=?, "
                    + "foto=?, fotocopiaBI=?, descricao=?, nomeDaMae=?, nomeDoPai=?, professao=?, estadoCivil=?, localDeEmisao=?, freguesia=? WHERE id_aluno =?";

            stm = conector.prepareStatement(sql);

            stm.setString(1, aluno.getNome());
            stm.setTimestamp(2, Tempo.toTimestamp(aluno.getDataNascimento()));
            stm.setString(3, aluno.getNumBI());
            stm.setTimestamp(4, Tempo.toTimestamp(aluno.getDataEmisao()));
            stm.setString(5, aluno.getResidencia());
            stm.setString(6, aluno.getConselho());
            stm.setString(7, aluno.getNatural());
            stm.setString(8, aluno.getEmail());
            stm.setString(9, aluno.getContato());
            stm.setString(10, aluno.getHabilitacaoLit());
            stm.setString(11, aluno.getNacionalidade());
            aluno.setFileInputStream(new FileInputStream(aluno.getFile()));
            stm.setBinaryStream(12, aluno.getFileInputStream(), aluno.getFile().length());

            if (aluno.filePath != null) {
                InputStream is;
                is = new FileInputStream(new File(aluno.filePath));
                stm.setBlob(13, is);
            } else if (aluno.filePath == null) {
                stm.setBlob(13, (Blob) null);
            }
            stm.setString(14, aluno.getDescricao());
            stm.setString(15, aluno.getNomeDaMae());
            stm.setString(16, aluno.getNomeDoPai());
            stm.setString(17, aluno.getProfessao());
            stm.setString(18, aluno.getEstadoCivil());
            stm.setString(19, aluno.getLocalDeEmisao());
            stm.setString(20, aluno.getFreguesia());

            stm.setInt(21, aluno.getIdAluno());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Aluno atualizado com sucesso!");
        } catch (SQLException | FileNotFoundException ex) {
            Mensagem.erro("Erro ao atualizar aluno na base de dados! \n" + ex);
        }
    }

    @Override
    public void editarSemFoto(Aluno aluno) {
        try {
            String sql = "UPDATE " + db + ".tb_aluno SET nome=?, dataNascimento=?, numBI=?, dataEmisao=?, resedencia=?, conselho=?,"
                    + "naturalidade=?, email=?, contato=?, habilitacaoLit=?, nacionalidade=?, "
                    + "fotocopiaBI=?, descricao=?, nomeDaMae=?, nomeDoPai=?, professao=?, estadoCivil=?, localDeEmisao=?, freguesia=? WHERE id_aluno =?";

            stm = conector.prepareStatement(sql);

            stm.setString(1, aluno.getNome());
            stm.setTimestamp(2, Tempo.toTimestamp(aluno.getDataNascimento()));
            stm.setString(3, aluno.getNumBI());
            stm.setTimestamp(4, Tempo.toTimestamp(aluno.getDataEmisao()));
            stm.setString(5, aluno.getResidencia());
            stm.setString(6, aluno.getConselho());
            stm.setString(7, aluno.getNatural());
            stm.setString(8, aluno.getEmail());
            stm.setString(9, aluno.getContato());
            stm.setString(10, aluno.getHabilitacaoLit());
            stm.setString(11, aluno.getNacionalidade());

            if (aluno.filePath != null) {
                InputStream is;
                is = new FileInputStream(new File(aluno.filePath));
                stm.setBlob(12, is);
            } else if (aluno.filePath == null) {
                stm.setBlob(12, (Blob) null);
            }
            stm.setString(13, aluno.getDescricao());
            stm.setString(14, aluno.getNomeDaMae());
            stm.setString(15, aluno.getNomeDoPai());
            stm.setString(16, aluno.getProfessao());
            stm.setString(17, aluno.getEstadoCivil());
            stm.setString(18, aluno.getLocalDeEmisao());
            stm.setString(19, aluno.getFreguesia());

            stm.setInt(20, aluno.getIdAluno());

            stm.executeUpdate();
            stm.close();
            Mensagem.info("Aluno atualizado com sucesso!");
        } catch (SQLException | FileNotFoundException  ex) {
            Mensagem.erro("Erro ao atualizar aluno na base de dados! \n" + ex);
        }
    }

    @Override
    public void delete(Integer idAluno) {
        try {
            String sql = "DELETE FROM " + db + ".tb_aluno WHERE id_aluno=?";
            stm = conector.prepareStatement(sql);
            stm.setInt(1, idAluno);
            stm.execute();
            stm.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao excluir aluno na base de dados!");
        }
    }

    @Override
    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();
        ImageView img;
        try {
            String sql = "SELECT * from " + db + ".tb_aluno order by nome";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while (rs.next()) {

                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome"));
                aluno.setDataNascimento(Tempo.toDate(rs.getTimestamp("dataNascimento")));
                aluno.setNumBI(rs.getString("numBI"));
                aluno.setDataEmisao(Tempo.toDate(rs.getTimestamp("dataEmisao")));
                aluno.setResidencia(rs.getString("resedencia"));
                aluno.setConselho(rs.getString("conselho"));
                aluno.setNatural(rs.getString("naturalidade"));
                aluno.setEmail(rs.getString("email"));
                aluno.setContato(rs.getString("contato"));
                aluno.setHabilitacaoLit(rs.getString("habilitacaoLit"));
                aluno.setNacionalidade(rs.getString("nacionalidade"));
                aluno.setFoto(rs.getBlob("foto"));
                if (aluno.getFoto() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aluno.getFoto().getBytes(1, (int) aluno.getFoto().length()));
                    aluno.image = new Image(byteArrayInputStream);
                    img = new ImageView(new Image(byteArrayInputStream));
                    img.setPreserveRatio(true);//setting the fit height and width of the image view 
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    aluno.setImageView(img);
                } else {
                    aluno.image = new Image("/cv/com/escola/img/sem_foto_0.gif");
                    img = new ImageView(new Image("/cv/com/escola/img/sem_foto_0.gif"));
                    img.setPreserveRatio(true);//setting the fit height and width of the image view 
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    aluno.setImageView(img);
                }
                aluno.setFotocopiaBI(rs.getBlob("fotocopiaBI"));
                if (aluno.getFotocopiaBI() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aluno.getFotocopiaBI().getBytes(1, (int) aluno.getFotocopiaBI().length()));
                    //aluno.file = new File(byteArrayInputStream);
                } else {

                }
                aluno.setDescricao(rs.getString("descricao"));
                aluno.setNomeDaMae(rs.getString("nomeDaMae"));
                aluno.setNomeDoPai(rs.getString("nomeDoPai"));
                aluno.setProfessao(rs.getString("professao"));
                aluno.setEstadoCivil(rs.getString("estadoCivil"));
                aluno.setLocalDeEmisao(rs.getString("localDeEmisao"));
                aluno.setFreguesia(rs.getString("freguesia"));
                aluno.setDataCadastro(Tempo.toDate(rs.getTimestamp("data_cadastro")));

                alunos.add(aluno);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar aluno na base de dados! \n" + ex);
        }
        return alunos;
    }

    @Override
    public ObservableList<Aluno> listar(int quantidade, int pagina) {
        ObservableList listaAluno = FXCollections.observableArrayList();
        try {
            ImageView img;
            String sql = "SELECT * from " + db + ".tb_aluno limit "+ quantidade * pagina +","+ quantidade +"";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome"));
                aluno.setDataNascimento(Tempo.toDate(rs.getTimestamp("dataNascimento")));
                aluno.setNumBI(rs.getString("numBI"));
                aluno.setDataEmisao(Tempo.toDate(rs.getTimestamp("dataEmisao")));
                aluno.setResidencia(rs.getString("resedencia"));
                aluno.setConselho(rs.getString("conselho"));
                aluno.setNatural(rs.getString("naturalidade"));
                aluno.setEmail(rs.getString("email"));
                aluno.setContato(rs.getString("contato"));
                aluno.setHabilitacaoLit(rs.getString("habilitacaoLit"));
                aluno.setNacionalidade(rs.getString("nacionalidade"));
                aluno.setFoto(rs.getBlob("foto"));
                if (aluno.getFoto() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aluno.getFoto().getBytes(1, (int) aluno.getFoto().length()));
                    aluno.image = new Image(byteArrayInputStream);
                    img = new ImageView(new Image(byteArrayInputStream));
                    img.setPreserveRatio(true);//setting the fit height and width of the image view
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    aluno.setImageView(img);
                } else {
                    aluno.image = new Image("/cv/com/escola/img/sem_foto_0.gif");
                    img = new ImageView(new Image("/cv/com/escola/img/sem_foto_0.gif"));
                    img.setPreserveRatio(true);//setting the fit height and width of the image view
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    aluno.setImageView(img);
                }
                aluno.setFotocopiaBI(rs.getBlob("fotocopiaBI"));
                if (aluno.getFotocopiaBI() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aluno.getFotocopiaBI().getBytes(1, (int) aluno.getFotocopiaBI().length()));
                    //aluno.file = new File(byteArrayInputStream);
                } else {

                }
                aluno.setDescricao(rs.getString("descricao"));
                aluno.setNomeDaMae(rs.getString("nomeDaMae"));
                aluno.setNomeDoPai(rs.getString("nomeDoPai"));
                aluno.setProfessao(rs.getString("professao"));
                aluno.setEstadoCivil(rs.getString("estadoCivil"));
                aluno.setLocalDeEmisao(rs.getString("localDeEmisao"));
                aluno.setFreguesia(rs.getString("freguesia"));
                aluno.setDataCadastro(Tempo.toDate(rs.getTimestamp("data_cadastro")));

                listaAluno.add(aluno);
            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAluno;
    }

    @Override
    public List<Aluno> autoCompletion() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            String sql = "SELECT id_aluno, nome, numBI from " + db + ".tb_aluno ORDER BY nome";
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome"));
                aluno.setNumBI(rs.getString("numBI"));

                alunos.add(aluno);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DaoException("Erro ao consultar aluno na base de dados!");
        }
        return alunos;
    }

    @Override
    public int totalSearchStudent(String param) {
        int total = 0;
        try {
            stm = conector.prepareStatement("select count(id_aluno) from " + db + ".tb_aluno where nome like ? or contato like ? or numBI like ?");
            stm.setString(1, param + "%");
            stm.setString(2, param + "%");
            stm.setString(3, param + "%");
            rs = stm.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    @Override
    public ObservableList<Aluno> student(int pagina) {
        ObservableList<Aluno> listData = FXCollections.observableArrayList();
        ImageView img;
        try {
            String sql = "select * from " + db + ".tb_aluno limit " + pagina;
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome"));
                aluno.setDataNascimento(Tempo.toDate(rs.getTimestamp("dataNascimento")));
                aluno.setNumBI(rs.getString("numBI"));
                aluno.setDataEmisao(Tempo.toDate(rs.getTimestamp("dataEmisao")));
                aluno.setResidencia(rs.getString("resedencia"));
                aluno.setConselho(rs.getString("conselho"));
                aluno.setNatural(rs.getString("naturalidade"));
                aluno.setEmail(rs.getString("email"));
                aluno.setContato(rs.getString("contato"));
                aluno.setHabilitacaoLit(rs.getString("habilitacaoLit"));
                aluno.setNacionalidade(rs.getString("nacionalidade"));
                aluno.setFoto(rs.getBlob("foto"));
                if (aluno.getFoto() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aluno.getFoto().getBytes(1, (int) aluno.getFoto().length()));
                    aluno.image = new Image(byteArrayInputStream);
                    img = new ImageView(aluno.image);
                    img.setPreserveRatio(true);//setting the fit height and width of the image view 
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    aluno.setImageView(img);
                } else {
                    aluno.image = new Image("/cv/com/escola/img/avater.jpg");
                    img = new ImageView(aluno.image);
                    img.setPreserveRatio(true);//setting the fit height and width of the image view 
                    img.setFitHeight(68);
                    img.setFitWidth(68);
                    aluno.setImageView(img);
                }
                aluno.setFotocopiaBI(rs.getBlob("fotocopiaBI"));
                if (aluno.getFotocopiaBI() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                            aluno.getFotocopiaBI().getBytes(1, (int) aluno.getFotocopiaBI().length()));
                } 
                aluno.setDescricao(rs.getString("descricao"));
                aluno.setNomeDaMae(rs.getString("nomeDaMae"));
                aluno.setNomeDoPai(rs.getString("nomeDoPai"));
                aluno.setProfessao(rs.getString("professao"));
                aluno.setEstadoCivil(rs.getString("estadoCivil"));
                aluno.setLocalDeEmisao(rs.getString("localDeEmisao"));
                aluno.setFreguesia(rs.getString("freguesia"));
                aluno.setDataCadastro(Tempo.toDate(rs.getTimestamp("data_cadastro")));
                listData.add(aluno);
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    @Override
    public ObservableList<Aluno> searchStudent(Pagina pagina, String query) {
        ObservableList<Aluno> listData = FXCollections.observableArrayList();
        try {
            stm = conector.prepareStatement("select * from " + db + ".tb_aluno where nome like ? "
                    + "or contato like ? or email like ? or numBI like ? limit " + pagina.getInicio() + "," + pagina.getFim());
            stm.setString(1, query + "%");
            stm.setString(2, query + "%");
            stm.setString(3, query + "%");
            stm.setString(4, query + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome"));
                aluno.setDataNascimento(Tempo.toDate(rs.getTimestamp("dataNascimento")));
                aluno.setNumBI(rs.getString("numBI"));
                aluno.setDataEmisao(Tempo.toDate(rs.getTimestamp("dataEmisao")));
                aluno.setResidencia(rs.getString("resedencia"));
                aluno.setConselho(rs.getString("conselho"));
                aluno.setNatural(rs.getString("naturalidade"));
                aluno.setEmail(rs.getString("email"));
                aluno.setContato(rs.getString("contato"));
                aluno.setHabilitacaoLit(rs.getString("habilitacaoLit"));
                aluno.setNacionalidade(rs.getString("nacionalidade"));
                aluno.setFoto(rs.getBlob("foto"));
                if (aluno.getFoto() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aluno.getFoto().getBytes(1, (int) aluno.getFoto().length()));
                    aluno.image = new Image(byteArrayInputStream);
                } else {
                    aluno.image = new Image("/cv/com/escola/img/avater.jpg");
                }
                aluno.setFotocopiaBI(rs.getBlob("fotocopiaBI"));
                if (aluno.getFotocopiaBI() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aluno.getFotocopiaBI().getBytes(1, (int) aluno.getFotocopiaBI().length()));
                    //aluno.file = new File(byteArrayInputStream);
                } else {

                }
                aluno.setDescricao(rs.getString("descricao"));
                aluno.setNomeDaMae(rs.getString("nomeDaMae"));
                aluno.setNomeDoPai(rs.getString("nomeDoPai"));
                aluno.setProfessao(rs.getString("professao"));
                aluno.setEstadoCivil(rs.getString("estadoCivil"));
                aluno.setLocalDeEmisao(rs.getString("localDeEmisao"));
                aluno.setFreguesia(rs.getString("freguesia"));
                aluno.setDataCadastro(Tempo.toDate(rs.getTimestamp("data_cadastro")));
                listData.add(aluno);
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    @Override
    public void report() {
        try {
            URL url = getClass().getResource("/cv/com/escola/reports/Aluno.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conector);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao imprimir lista de alunos! \n" + ex);
        }
    }

    @Override
    public void reportRequiremento(String biFiltro) {
        try {
            HashMap filtro = new HashMap();
            filtro.put("bi", biFiltro);

            URL url = getClass().getResource("/cv/com/escola/reports/Requiremento.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, conector);
            Print jasperViewer = new Print();//(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.viewReport("Requiremento", jasperPrint);
        } catch (JRException ex) {
            Logger.getLogger(AlunoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.erro("Erro ao imprimir requirimento! \n" + ex.getLocalizedMessage());
        }
    }
}
