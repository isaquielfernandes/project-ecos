
package cv.com.escola.model.util;

import java.lang.reflect.InvocationTargetException;
import org.apache.log4j.Logger;

public class ReflectionUtils {

    private static final Logger LOG = Logger.getLogger(ReflectionUtils.class.getName());
    
    public static final String GETTER_PREFIX = "get";
    public static final String SETTER_PREFIX = "set";

    private ReflectionUtils() {
        throw new UnsupportedOperationException("ReflectionUtils is not instantiable!");
    }
    
    
    public static <T> Class<T> getClass(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw handleException(className, ex);
        }
    }
     
    public static <T> T newInstance(String className) {
        try {
            Class<T> classT = getClass(className);
            return classT.getConstructor().newInstance();
        } catch (InstantiationException ex) {
            throw handleException(className, ex);
        } catch (IllegalAccessException e) {
            throw handleException(className, e);
        } catch (NoSuchMethodException noSuEX) {
            throw handleException(className, noSuEX);
        }catch (InvocationTargetException er) {
            throw handleException(className, er);
        }
    }
    
    private static IllegalArgumentException handleException(String className, ClassNotFoundException e) {
        LOG.error("Couldn't find class " + className, e);
        return new IllegalArgumentException(e);
    }
    
    private static IllegalArgumentException handleException(String methodName, IllegalAccessException e) {
        LOG.error("Couldn't invoke method " + methodName, e);
        return new IllegalArgumentException(e);
    }
    
    private static IllegalArgumentException handleException(String className, InstantiationException e) {
        LOG.error("Couldn't instantiate class " + className, e);
        return new IllegalArgumentException(e);
    }
    
    private static IllegalArgumentException handleException(String className, NoSuchMethodException e) {
        LOG.error("NoSuchMethodException: Couldn't instantiate class " + className, e);
        return new IllegalArgumentException(e);
    }
    
    private static IllegalArgumentException handleException(String className, InvocationTargetException e) {
        LOG.error("InvocationTargetException: Couldn't instantiate class " + className, e);
        return new IllegalArgumentException(e);
    }
}
