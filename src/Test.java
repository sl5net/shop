import org.json.JSONObject;

import java.io.*;

public class Test {
    public static void main2(String args[]) {
        JSONObject jo = new JSONObject("{ \"abc\" : \"def\" }");
        System.out.println(jo.toString());
    }
    public static void main(String args[]) throws IOException {


        String productsPath = "./resources/products.json";
        File productsFile = new File(productsPath);
        FileInputStream productsInputStream = new FileInputStream(productsFile);
        byte[] data = new byte[(int) productsFile.length()];
        productsInputStream.read(data);
        productsInputStream.close();
        String productsStr = new String(data, "UTF-8");

        JSONObject productsJsonObj = new JSONObject(productsStr);


        JSONObject musicJobj = productsJsonObj.getJSONObject("music");
        System.out.println(musicJobj);
        Object type = musicJobj.get("type");
        System.out.println(type.toString());

//        System.out.println(productsJsonObj.toString());
//        Object musicStr = productsJsonObj.get("music");
//        System.out.println(musicStr);

    }
}
