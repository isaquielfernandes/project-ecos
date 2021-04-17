package cv.com.escola.model.dao.db;

import cv.com.escola.model.dao.AlunoDAO;
import cv.com.escola.model.dao.ArtigoDAO;
import cv.com.escola.model.dao.BenificioDAO;
import cv.com.escola.model.dao.CargoSalarioDAO;
import cv.com.escola.model.dao.CategoriaDAO;
import cv.com.escola.model.dao.ClienteDAO;
import cv.com.escola.model.dao.CursoDAO;
import cv.com.escola.model.dao.EmpresaDAO;
import cv.com.escola.model.dao.ExameDAO;
import cv.com.escola.model.dao.ExameResultadoDAO;
import cv.com.escola.model.dao.InspecaoTecnicaDAO;
import cv.com.escola.model.dao.InstrutorDAO;
import cv.com.escola.model.dao.ItemDAO;
import cv.com.escola.model.dao.LoginDAO;
import cv.com.escola.model.dao.MatriculaDAO;
import cv.com.escola.model.dao.OrderDAO;
import cv.com.escola.model.dao.OrganizacaoDAO;
import cv.com.escola.model.dao.RelatorioEscolaDAO;
import cv.com.escola.model.dao.SeguroAutoDAO;
import cv.com.escola.model.dao.UsuarioDAO;
import cv.com.escola.model.dao.VeiculoDAO;
import cv.com.escola.model.dao.impl.AlunoDAOImpl;
import cv.com.escola.model.dao.impl.ArtigoDAOImpl;
import cv.com.escola.model.dao.impl.BenificioDAOImpl;
import cv.com.escola.model.dao.impl.CargoSalarioDAOImpl;
import cv.com.escola.model.dao.impl.CategoriaDAOImpl;
import cv.com.escola.model.dao.impl.ClienteDAOImpl;
import cv.com.escola.model.dao.impl.CursoDAOImpl;
import cv.com.escola.model.dao.impl.EscolaConducaoDAOImpl;
import cv.com.escola.model.dao.impl.ExameDAOImpl;
import cv.com.escola.model.dao.impl.ExameResultadoDAOImpl;
import cv.com.escola.model.dao.impl.InspecaoTecnicaDAOImpl;
import cv.com.escola.model.dao.impl.InstrutorDAOImpl;
import cv.com.escola.model.dao.impl.InventarioDAOImpl;
import cv.com.escola.model.dao.impl.ItemDAOImpl;
import cv.com.escola.model.dao.impl.LoginDAOImpl;
import cv.com.escola.model.dao.impl.MatriculaDAOImpl;
import cv.com.escola.model.dao.impl.OrganizacaoDAOImpl;
import cv.com.escola.model.dao.impl.RelatorioEscolaDAOImpl;
import cv.com.escola.model.dao.impl.SeguroAutoDAOImpl;
import cv.com.escola.model.dao.impl.TurmaDAOImpl;
import cv.com.escola.model.dao.impl.UsuarioDAOImpl;
import cv.com.escola.model.dao.impl.VeiculoDAOImpl;
import cv.com.escola.model.dao.impl.OrderDAOImpl;

/**
 * Classe responsável por realizar o controle dos objetos DAO que contém os
 * CRUDs e diversas operações na base de dados, filtrando a criação desses
 * objetos.
 *g
 * @autor Isaquiel Fernandes
 */
public class DAOFactory {

    private static final DAOFactory BANCO = new DAOFactory();
    private final InstrutorDAOImpl instrutorDAO = new InstrutorDAOImpl();
    private final TurmaDAOImpl turmaDAO = new TurmaDAOImpl();

    public static DAOFactory daoFactury() {
        return BANCO;
    }

    public InspecaoTecnicaDAO inspecaoTecnicaDAO() {
        return new InspecaoTecnicaDAOImpl();
    }

    public LoginDAO loginDAO() {
        return new LoginDAOImpl();
    }

    public UsuarioDAO usuarioDAO() {
        return new UsuarioDAOImpl();
    }

    public CategoriaDAO categoriaDAO() {
        return new CategoriaDAOImpl();
    }

    public AlunoDAO alunoDAO() {
        return new AlunoDAOImpl();
    }

    public ExameDAO exameDAO() {
        return new ExameDAOImpl();
    }

    public VeiculoDAO veiculoDAO() {
        return new VeiculoDAOImpl();
    }

    public InstrutorDAO instrutorDAO() {
        return instrutorDAO;
    }

    public TurmaDAOImpl getTurmaDAO() {
        return turmaDAO;
    }

    public OrganizacaoDAO organizacaoDAO() {
        return new OrganizacaoDAOImpl();
    }

    public InventarioDAOImpl inventarioDAO() {
        return new InventarioDAOImpl();
    }

    public EmpresaDAO empresaDAO() {
        return new EscolaConducaoDAOImpl();
    }

    public CargoSalarioDAO getCargo_salarioDAO() {
        return new CargoSalarioDAOImpl();
    }

    public BenificioDAO benificioDAO() {
        return new BenificioDAOImpl();
    }

    public CursoDAO cursosDAO() {
        return new CursoDAOImpl();
    }

    public ExameResultadoDAO exameResultadoDAO() {
        return new ExameResultadoDAOImpl();
    }

    public SeguroAutoDAO seguroAutoDAO() {
        return new SeguroAutoDAOImpl();
    }

    public RelatorioEscolaDAO relatorioEscolaDAO() {
        return new RelatorioEscolaDAOImpl();
    }

    public ArtigoDAO artigoDAO() {
        return new ArtigoDAOImpl();
    }

    public ItemDAO itemDAO() {
        return new ItemDAOImpl();
    }

    public OrderDAO orderDAO() {
        return new OrderDAOImpl();
    }

    public ClienteDAO clienteDAO() {
        return new ClienteDAOImpl();
    }

    public MatriculaDAO matriculaDAO() {
        return new MatriculaDAOImpl();
    }

}
