import java.io.*;

public class Basket implements Serializable {
    private String[] products;
    private double[] prices;
    private int[] selected;

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

    public void saveBin(File binFile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(binFile))) {
            out.writeObject(this);
        }
    }

    public static Basket loadFromBinFile(File binFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(binFile))) {
            return (Basket) inputStream.readObject();
        }
    }


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
