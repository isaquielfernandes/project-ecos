package cv.com.escola.model.entity;

import java.util.Objects;

public abstract class EntidadeAbstrata implements Entidade<Long> {
    
    private static final long serialVersionUID = 1L;
    
    protected Long id;
    
    @Override
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntidadeAbstrata other = (EntidadeAbstrata) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
