import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ShopTest {

    @org.junit.jupiter.api.Test
    public void checkProductMusicHasTypeMusic() throws IOException {
        JSONObject productsJsonObj = getProductsJson();
        JSONObject musicJobj = productsJsonObj.getJSONObject("music");
        Object type = musicJobj.get("type");
        String musicTypeStr = type.toString();
        assertEquals(musicTypeStr, "music");
    }

    @org.junit.jupiter.api.Test
    public void name_of_productTypesRates_areIn_products() throws IOException {
        JSONObject productsJson = getProductsJson();
        JSONObject productTypeRatesJson = getProductTypeRatesJson();

        Map<String, Object> productsMap = productsJson.toMap();
        Map<String, Object> productTypeRatesMap = productTypeRatesJson.toMap();
        for (String name : productTypeRatesMap.keySet()) {
            assertTrue(productsMap.containsKey(name));
        }
    }

    @org.junit.jupiter.api.Test
    public void readAllProducts() throws IOException {
        JSONObject productsJson = getProductsJson();

        Map<String, Object> productsMap = productsJson.toMap();

        Integer countProducts = 0;
        String key;
//        key = "type";
//        key = "music";
        for (String name : productsMap.keySet()) {
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

    private JSONObject getProductsJson() throws IOException {
        String productsPath = "../resources/products.json";
        File productsFile = new File(productsPath);
        FileInputStream productsInputStream = new FileInputStream(productsFile);
        byte[] data = new byte[(int) productsFile.length()];
        productsInputStream.read(data);
        productsInputStream.close();
        String productsStr = new String(data, StandardCharsets.UTF_8);
        return new JSONObject(productsStr);
    }
    private JSONObject getProductTypeRatesJson() throws IOException {
        String productTypesRatePath = "../resources/productTypesRate.json";
        File productTypeRatesFile = new File(productTypesRatePath);
        FileInputStream productTypeRatesInputStream = new FileInputStream(productTypeRatesFile);
        byte[] data = new byte[(int) productTypeRatesFile.length()];
        productTypeRatesInputStream.read(data);
        productTypeRatesInputStream.close();
        String productTypeRatesStr = new String(data, StandardCharsets.UTF_8);
        return new JSONObject(productTypeRatesStr);
    }

}
