package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Venda;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import javafx.collections.ObservableList;

public interface OrderDAO extends CrudDAO<Venda, Integer> {

    public Venda buscar(Venda venda);

    public Venda buscarUltimaVenda();

    public void gerarReciboFatura(Long id);

    public BigDecimal valorTotalDeVendaPorAno(String ano);

    public int ultimoRegistro(int ano);

    public int ultimoRegistro();

    public ObservableList<Venda> listar(int limit, int offset);

    public Map<Integer, ArrayList<Number>> listarQuantidadeVendaPorDia(String mes, String ano);

    public Map<Integer, ArrayList<Number>> listarQuantidadeVendaPorMes();

    public Map<Integer, ArrayList<Number>> listarValorTotalVendaPorMes();

    public Map<Integer, ArrayList<Number>> listarValorTotalVendaPorMes(String ano);

}
