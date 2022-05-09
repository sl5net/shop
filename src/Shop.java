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

    public double getPriceCalculation(String product, int number) {
        boolean imported = false;
        return getPriceCalculation(product, number, imported);
    }

    public double getPriceCalculation(String product, int number, boolean isImported) {
        JSONObject productJson = productsJson.getJSONObject(product);
        double productPriceWithoutTax = getPriceDouble(productJson);
//        System.out.println(productPriceWithoutTax);
        double priceWithoutTax = number * productPriceWithoutTax;

        double tax = getTaxDouble(product);
        System.out.println("json tax:" + tax);

        if (isImported) tax += 5;


        double taxFactor = (tax > 0) ? tax / 100 : 0;
        double taxCosts = priceWithoutTax * taxFactor;


        /*
        Import duty is an additional sales tax applicable
        on all imported goods at a rate of 5%, with no exemptions.
         */
        double roundedTimes20 = Math.ceil(taxCosts * 20);
        taxCosts = roundedTimes20 / 20;


        double priceWithTax = priceWithoutTax + taxCosts;

        double priceWithTaxRounded = Math.round(priceWithTax * 100.0) / 100.0;

        System.out.println(number + " " + product + ": " + priceWithTaxRounded);
        return priceWithTaxRounded;
    }

    private double getTaxDouble(String product) {
        JSONObject productJson = productsJson.getJSONObject(product);
        String type = (String) productJson.get("type");
        System.out.println("type of " + product + " is " + type);
        return productTypeRatesJson.getDouble(type);
    }

    private double getPriceDouble(JSONObject productJson) {
        BigDecimal productPriceWithoutTax = (BigDecimal) productJson.get("price");
        return productPriceWithoutTax.doubleValue();
    }

    double getPriceDouble(String productName) {
        JSONObject productJson = productsJson.getJSONObject(productName);
        BigDecimal productPriceWithoutTax = (BigDecimal) productJson.get("price");
        return productPriceWithoutTax.doubleValue();
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
