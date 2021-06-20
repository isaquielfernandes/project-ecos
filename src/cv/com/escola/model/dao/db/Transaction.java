/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv.com.escola.model.dao.db;

/**
 *
 * @author iisaq
 */
public interface Transaction {
    
    public void begin();
    
    public void commit();
    
    public void rollback();
    
    public boolean isActive();
    
}
