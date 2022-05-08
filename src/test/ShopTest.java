package test;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class ShopTest {

    private String productsPath = "./resources/products.json";

    @org.junit.jupiter.api.Test
    public void checkProductMusicHasTypeMusic() throws IOException {
        String productsStr = productsJsonAsString();
        JSONObject productsJsonObj = new JSONObject(productsStr);
        JSONObject musicJobj = productsJsonObj.getJSONObject("music");
        Object type = musicJobj.get("type");
        String musicTypeStr = type.toString();
        assertEquals(musicTypeStr, "music");
    }

    @org.junit.jupiter.api.Test
    public void readAllProducts() throws IOException {
        String productsStr = productsJsonAsString();
        JSONObject productsJsonObj = new JSONObject(productsStr);

        Map<String, Object> stringObjectMap = productsJsonObj.toMap();

        Integer countProducts = 0;
        String key;
        key = "type";
        key = "music";
        for (String name : stringObjectMap.keySet()) {
//            System.out.println("key: " + name);
            countProducts++;
        }
        assertEquals(countProducts, 11);


//        if (stringObjectMap.containsKey(key))
//        {
//            count++;
//        }
//        System.out.println(countProducts);

//        JSONObject musicJobj = productsJsonObj.getJSONObject("music");
//        Object type = musicJobj.get("type");
//        String musicTypeStr = type.toString();
//        assertEquals(musicTypeStr, "music");
    }

    private String productsJsonAsString() throws IOException {
        File productsFile = new File(productsPath);
        FileInputStream productsInputStream = new FileInputStream(productsFile);
        byte[] data = new byte[(int) productsFile.length()];
        productsInputStream.read(data);
        productsInputStream.close();
        String productsStr = new String(data, "UTF-8");
        return productsStr;
    }

    @org.junit.jupiter.api.Test
    public void checkFreeTaxProductsAreInProducts2(String args[]) {
    }

    void checkFreeTaxProductsAreInProducts(){
    }
}
