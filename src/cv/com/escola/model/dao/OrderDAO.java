package cv.com.escola.model.dao;

import cv.com.escola.model.entity.Venda;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import javafx.collections.ObservableList;

public interface OrderDAO extends CrudDAO<Venda, Integer>{

    Venda buscar(Venda venda);
    Venda buscarUltimaVenda();
    void reportReciboFatura(int biFiltro);
    BigDecimal totalAnual(String ano);
    int ultimoRegisto(int ano);
    int ultimoRegisto();
    ObservableList<Venda> listar(int quantidade, int pagina);
    Map<Integer, ArrayList<Number>> listarQuantidadeVendaPorDia(String mes, String ano);
    Map<Integer, ArrayList<Number>> listarQuantidadeVendaPorMes();
    Map<Integer, ArrayList<Number>> listarValorTotalVendaPorMes();
    Map<Integer, ArrayList<Number>> listarValorTotalVendaPorMes(String ano);
    
}
