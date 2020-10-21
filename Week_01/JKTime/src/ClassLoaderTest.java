import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        Class<?> helloClas = helloClassLoader.findClass("Hello");
        Method helloMethod = helloClas.getMethod("hello");
        helloMethod.invoke(helloClas.newInstance());
    }
}
