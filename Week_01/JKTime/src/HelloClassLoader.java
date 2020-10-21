import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HelloClassLoader extends ClassLoader{

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = getClassBytes("D:\\Hello.xlass");
        if(classBytes == null) {
            System.out.println("Not found class file.");
            throw new ClassNotFoundException(name);
        }
        return defineClass(name,classBytes,0,classBytes.length);
    }

    /**
     * 获取处理后的byte字节码
     * @param filePath
     * @return
     */
    private byte[] getClassBytes(String filePath) {
        if(filePath == null || filePath.length() <= 0) {
            return null;
        }
        File classFile = new File(filePath);
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(classFile);
            int size = fi.available();
            byte[] classBytes = new byte[size];
            fi.read(classBytes);

            for(int i=0; i<classBytes.length; i++) {
                classBytes[i] = (byte)( 255 - classBytes[i]);
            }
            return classBytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fi != null) {
                try {
                    fi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
