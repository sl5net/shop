import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class Shop {
    final private String pathDirtyBugFixBecausePathWillNotFoundEverySecondTime = "/home/seeh/Projects/Idea/shop/src/";
//    final private String pathDirtyBugFixBecausePathWillNotFoundEverySecondTime2 = "./";
    JSONObject productsJson = getProductsJson();
    JSONObject productTypeRatesJson = getProductTypeRatesJson();

    public Shop() throws IOException {
    }

    public void getPriceCalculation(String product, int number) {
        JSONObject productJson = productsJson.getJSONObject(product);
        double productPriceWithoutTax = getPriceDouble(productJson);
        System.out.println(productPriceWithoutTax);
        String type = (String) productJson.get("type");
        double tax = getTaxDouble(product);
        double priceWithoutTax = number * productPriceWithoutTax;
        double priceWithTax = priceWithoutTax * tax;

        System.out.println(priceWithoutTax);
        System.out.println(priceWithTax);



        System.out.println(productJson);

//        Integer number10 = (Integer) productTypeRatesJson.get("music");
////        assertEquals(number10 * number10, 100);
//
//        JSONObject book = productsJson.getJSONObject("book");
//        BigDecimal price = (BigDecimal) book.get("price");
//        System.out.println(price);
//        System.out.println(price.doubleValue() * 10);
//
//        System.out.println(price.doubleValue() * number10);

    }

    private double getTaxDouble(String product) {

        double taxDouble = productTypeRatesJson.getDouble(product);
//        System.out.println(taxObj);
//        float taxFloat = ((float) taxObj);
//        System.out.println(taxFloat);
//        double taxDouble = (double) taxObj;
//        System.out.println(taxDouble);
//        BigDecimal tax = (BigDecimal) taxObj;
        return taxDouble;
    }

    private double getPriceDouble(JSONObject productJson) {
        BigDecimal productPriceWithoutTax = (BigDecimal) productJson.get("price");
        double priceDouble = productPriceWithoutTax.doubleValue(); // i really don't like this cast. but needed
        return priceDouble;
    }

    JSONObject getProductsJson() throws IOException {
        String productsPath = pathDirtyBugFixBecausePathWillNotFoundEverySecondTime + "/products.json";
        File productsFile = new File(productsPath);
        FileInputStream productsInputStream = new FileInputStream(productsFile);
        byte[] data = new byte[(int) productsFile.length()];
        productsInputStream.read(data);
        productsInputStream.close();
        String productsStr = new String(data, StandardCharsets.UTF_8);
        return new JSONObject(productsStr);
    }

    JSONObject getProductTypeRatesJson() throws IOException {
        String productTypesRatePath = pathDirtyBugFixBecausePathWillNotFoundEverySecondTime + "/productTypesRate.json";
        File productTypeRatesFile = new File(productTypesRatePath);
        FileInputStream productTypeRatesInputStream = new FileInputStream(productTypeRatesFile);
        byte[] data = new byte[(int) productTypeRatesFile.length()];
        productTypeRatesInputStream.read(data);
        productTypeRatesInputStream.close();
        String productTypeRatesStr = new String(data, StandardCharsets.UTF_8);
        return new JSONObject(productTypeRatesStr);
    }

}
