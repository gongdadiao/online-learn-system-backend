import cn.gongyan.learn.service.CodeService;
import org.apache.tools.ant.filters.StringInputStream;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * @author 龚研
 * @desc Test
 * @date 2020/4/23
 * @qq 1085704190
 **/
public class Test {
    public static void main(String[] args){
        String test="6 4 2 1 5 21 54 78 12 9";
        InputStream stream = new StringInputStream(test);
        System.setIn(stream);
        CodeService service = new CodeService();
        String execute = service.execute("import java.util.ArrayList;\n" +
                "import java.util.Comparator;\n" +
                "import java.util.Scanner;\n" +
                "public class Test {\n" +
                "    public static void main(String[] args){\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        ArrayList<Integer> arrayList = new ArrayList<>();\n" +
                "        for(int i=0;i<10;i++){\n" +
                "            String b = scanner.next();\n" +
                "            int a=Integer.parseInt(b);\n" +
                "            arrayList.add(a);\n" +
                "        }\n" +

                "        for(int i=0;i<10;i++){\n" +
                "            System.out.println(arrayList.get(i));\n" +
                "        }\n" +
                "    }\n" +
                "}");
        System.out.println(execute);
    }
}
