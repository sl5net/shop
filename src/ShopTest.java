import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class ShopTest {

    final private String pathDirtyBugFixBecausePathWillNotFoundEverySecondTime = "/home/seeh/Projects/Idea/shop/src/";
    Shop shop = new Shop();

    public ShopTest() throws IOException {
    }

    @org.junit.jupiter.api.Test
    public void getPriceCalculation() throws IOException {
        shop.getPriceCalculation("music",10);
    }

    @org.junit.jupiter.api.Test
    public void test_multiply_numbers_ProductTypeRates() throws IOException {
        JSONObject productTypeRatesJson = shop.getProductTypeRatesJson();
        Integer number10 = (Integer) productTypeRatesJson.get("music");
        assertEquals(number10 * number10, 100);
    }

    @org.junit.jupiter.api.Test
    public void test_multiply_numbers_from_json1() throws IOException {
        JSONObject productsJson = shop.getProductsJson();
        JSONObject book = productsJson.getJSONObject("book");
        BigDecimal price = (BigDecimal) book.get("price");
        System.out.println(price);
        System.out.println(price.doubleValue() * 10);
        System.out.println(price.doubleValue() * price.doubleValue());
    }

    @org.junit.jupiter.api.Test
    public void test_multiply_ProductTypeRates_Products() throws IOException {
        JSONObject productTypeRatesJson = shop.getProductTypeRatesJson();
        Integer number10 = (Integer) productTypeRatesJson.get("music");
        assertEquals(number10 * number10, 100);

        JSONObject productsJson = shop.getProductsJson();
        JSONObject book = productsJson.getJSONObject("book");
        BigDecimal price = (BigDecimal) book.get("price");
        System.out.println(price);
        System.out.println(price.doubleValue() * 10);

        System.out.println(price.doubleValue() * number10);

    }


    @org.junit.jupiter.api.Test
    public void checkProductMusicHasTypeMusic() throws IOException {
        JSONObject productsJsonObj = shop.getProductsJson();
        JSONObject musicJobj = productsJsonObj.getJSONObject("music");
        Object type = musicJobj.get("type");
        String musicTypeStr = type.toString();
        assertEquals(musicTypeStr, "music");
    }

    @org.junit.jupiter.api.Test
    public void name_of_productTypesRates_areIn_products() throws IOException {
        JSONObject productsJson = shop.getProductsJson();
        JSONObject productTypeRatesJson = shop.getProductTypeRatesJson();

        Map<String, Object> productsMap = productsJson.toMap();
        Map<String, Object> productTypeRatesMap = productTypeRatesJson.toMap();
        for (String name : productTypeRatesMap.keySet()) {
            assertTrue(productsMap.containsKey(name));
        }
    }

    @org.junit.jupiter.api.Test
    public void readAllProducts() throws IOException {
        JSONObject productsJson = shop.getProductsJson();

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


    //    1 book at 12.49
    @org.junit.jupiter.api.Test
    public void buy1book() throws IOException {
        JSONObject productsJson = shop.getProductsJson();
        Object price = productsJson.getJSONObject("book").get("price");
        assertEquals(price.toString(), "12.49");
    }

    @org.junit.jupiter.api.Test
    public void buy1music_CD() throws IOException {
        JSONObject productsJson = shop.getProductsJson();
        JSONObject productTypeRatesJson = shop.getProductTypeRatesJson();
        BigDecimal price = (BigDecimal) productsJson.getJSONObject("music").get("price");
        double p = price.doubleValue();
        System.out.println(">" + price + "<");
//        BigDecimal tax = (BigDecimal) productTypeRatesJson.get("music");
        BigDecimal tax = null;
        if (tax.doubleValue() > 0)
            tax = (BigDecimal) productTypeRatesJson.get("music");
        else {
            tax = BigDecimal.valueOf(0);
        }
        double sum;
        if (0 > tax.doubleValue()) {
            double t = tax.doubleValue();
            System.out.println(">" + p + "<");
            System.out.println(">" + t + "<");
            sum = p * t / 100;
        } else
            sum = p;
        System.out.println(">" + sum + "<");
//        BigDecimal sum = price.multiply(tax).divide(BigDecimal.valueOf(100));
        assertEquals(sum, 14.99);
    }


}
