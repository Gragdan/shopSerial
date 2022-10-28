import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Basket implements Serializable{
    protected String[] products;
    protected double[] prices;
    protected int[] selected;

    public Basket(String[] products, double[] prices) {
        this.products = products;
        this.prices = prices;
        this.selected = new int[prices.length];
    }

   private Basket(String[] products, double[] prices, int[] selected) {
        this.products = products;
        this.prices = prices;
        this.selected = selected;
    }


    public void saveJSONObject(File jsonFile) throws IOException {
        try(PrintWriter out = new PrintWriter(jsonFile)) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            out.println(json);
        }
    }

    public static Basket loadFromJson(File jsonFile) throws IOException{
        try (Scanner scanner = new Scanner(jsonFile)) {
            String json = scanner.nextLine();
            Gson gson = new Gson();
            Basket myBasket = gson.fromJson(json, Basket.class);
            return myBasket;
        }
    }

    public void saveTxt(File txtFile) throws FileNotFoundException {
        // loadFromTxtFile(file);
        try (PrintWriter writer = new PrintWriter(txtFile)) {
            for (String product : products) {
                writer.print(product + " ");
            }
            writer.println();
            for (double price : prices) {
                writer.print(price + " ");
            }
            writer.println();
            for (int sel : selected) {
                writer.print(sel + " ");
            }

        }
    }

/*    public static Basket loadFromTxtFile(File txtFile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(txtFile))) {
            String[] products = scanner.nextLine().trim().split(" ");
            double[] prices = Arrays.stream(scanner.nextLine().trim().split(" "))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            int[] selected = Arrays.stream(scanner.nextLine().trim().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            return new Basket(products, prices, selected);
        }
    }*/


    public int[] addToCart(int productNum, int amount) {
        selected[productNum - 1] = selected[productNum - 1] + amount;
        return selected;
    }

    public void printCart() {
        System.out.println("Goods in yuor cart: ");
        for (int i = 0; i < products.length; i++) {
            if (selected[i] != 0) {
                System.out.println(products[i] + ", prise: " + prices[i] + "/pie, quantity: " + selected[i] + " sum  " + prices[i] * selected[i]);
            }
        }

    }


}
