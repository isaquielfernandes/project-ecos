package cv.com.escola.model.util;

public class GerarNumero {
    
    private int codigo;
    private final int count=1;
    private String num="";

    public void generar(int codigo) {
        this.codigo = codigo;
           if((this.codigo>=10000000) || (this.codigo<100000000)) 
           {
               int novo=count+this.codigo;
               num = "" + novo; 
           }
           if((this.codigo>=1000000) || (this.codigo<10000000)) 
           {
               int novo=count+this.codigo;
               num = "0" + novo; 
           }
           if((this.codigo>=100000) || (this.codigo<1000000)) 
           {
               int novo=count+this.codigo;
               num = "00" + novo; 
           }
           if((this.codigo>=10000) || (this.codigo<100000)) 
           {
               int novo=count+this.codigo;
               num = "000" + novo; 
           }
           if((this.codigo>=1000) || (this.codigo<10000)) 
           {
               int novo=count+this.codigo;
               num = "0000" + novo; 
           }
           if((this.codigo>=100) || (this.codigo<1000))
           {
               int novo=count+this.codigo;
               num = "00000" + novo; 
           }
           if((this.codigo>=9) || (this.codigo<100)) 
           {
                int novo=count+this.codigo;
               num = "000000" + novo; 
           }
           if (this.codigo<9)
           {
               int novo=count+this.codigo;
               num = "0000000" + novo; 
           }
          
    }

    public String serie()
    {
        return this.num;
    }
}
