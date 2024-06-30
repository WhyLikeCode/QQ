package InetApi.Test;

import java.io.File;

public class Test {

    public static void traverseFolder(File folder){
        File[] list = folder.listFiles();
        if (list != null){
            for(File file : list){
                if (file.isFile()){
                    System.out.println(file.getName());
                }
            }
        }

    }
}
