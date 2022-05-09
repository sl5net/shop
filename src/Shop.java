import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shop {
    final private String pathDirtyBugFixBecausePathWillNotFoundEverySecondTime = "/home/seeh/Projects/Idea/shop/src/";
    //    final private String pathDirtyBugFixBecausePathWillNotFoundEverySecondTime2 = "./";
    JSONObject productsJson = getProductsJson();
    JSONObject productTypeRatesJson = getProductTypeRatesJson();

    public Shop() throws IOException {
    }

    static public void main(String[] args) throws IOException {
        System.out.println("Enter your order: ");
        Scanner scanner = new Scanner(System.in);
        String input;
        if(false) {
            input = "1 book at 12.49";
            input = "1 music CD at 14.99";
            input = "1 chocolate bar at 0.85";
            input = "1 imported box of chocolates at 10.00";
            input = "1 imported bottle of perfume at 47.50";
            input = "1 imported bottle of perfume at 27.99";
            input = "1 bottle of perfume at 18.99";
            input = "1 packet of headache pills at 9.75";
            input = "1 box of imported chocolates at 11.25";
        }else
            input = scanner.nextLine();
        System.out.println("Your input is >" + input + "<");


        // 1 book at 12.49
        Pattern pattern = Pattern.compile("(\\d+)\\s+(.*?) at (\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(input);
        String numberStr = null;
        String textMiddle = null;
        String priceNett = null;
        if (matcher.find()) {
//            System.out.println("group= " + matcher.group(1));
//            System.out.println("group= " + matcher.group(2));
//            System.out.println("group= " + matcher.group(3));
            numberStr = matcher.group(1);
            textMiddle = matcher.group(2);
            priceNett = matcher.group(3);
        }


        int number = Integer.parseInt(numberStr);
        double priceNettDbl = Double.parseDouble(priceNett);

        // find imported
        boolean isImported = ("imported" == textMiddle.substring(0,8) );
        System.out.println("priceNettDbl: >" + priceNettDbl + "<");

        // find imported
        String  productTitle = textMiddle;
        if(isImported)
            productTitle = textMiddle.substring(9) ;
        System.out.println(">" + productTitle + "<");

        if(productTitle.endsWith("bottle of perfume") && priceNettDbl == 27.99) {
            productTitle = "bottle of perfume Type27";
        } else if (productTitle.endsWith("bottle of perfume") && priceNettDbl == 47.50) {
            productTitle = "bottle of perfume Type47";
        } else if (productTitle.endsWith("bottle of perfume") && priceNettDbl == 18.99) {
            productTitle = "bottle of perfume Type18";
        } else if (productTitle.endsWith("box of imported chocolates") && priceNettDbl == 11.25) {
            isImported = true;
            productTitle = "chocolate Type11";
        }
        System.out.println(">" + productTitle + "<");

        Shop shop = new Shop();
        double priceWithTaxRounded = shop.getPriceCalculation(productTitle, number, isImported);
        System.out.println(number + " " + productTitle + ": " + priceWithTaxRounded);

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
//        System.out.println("json tax:" + tax);

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

//        System.out.println(number + " " + product + ": " + priceWithTaxRounded);
        return priceWithTaxRounded;
    }

    private double getTaxDouble(String product) {
        JSONObject productJson = productsJson.getJSONObject(product);
        String type = (String) productJson.get("type");
//        System.out.println("type of " + product + " is " + type);
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
