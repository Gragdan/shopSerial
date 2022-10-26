import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"cheese", "bread"};
        double[] prices = {12.5, 4};
        File txtFile = new File("basket.txt");
        //  File binFile = new File("basket.bin");
        Basket myBasket = null;
        ClientLog myLog = new ClientLog();
        if (txtFile.exists()) {
            //   myBasket = Basket.loadFromBinFile(binFile);
            myBasket = Basket.loadFromTxtFile(txtFile);
        } else {
            //   binFile.createNewFile();
            txtFile.createNewFile();
            myBasket = new Basket(products, prices);
        }
        myBasket.printCart();
        System.out.println("Products for sale:");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + " " + products[i] + "  " + prices[i] + " $/pie");
        }

        while (true) {
            System.out.println("Choose number and quant of goods or inter `end`");

            String input = scanner.nextLine();
            if ("end".equals(input)) {
                myBasket.printCart();
                myLog.exportAsCSV();
                break;
            }
            int currentProduct;
            int currentQuan;
            String parts[] = input.split(" ");
            if (parts.length == 2) {
                try {
                    currentProduct = Integer.parseInt(parts[0]);
                    currentQuan = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Одно из введенных значений, либо оба - не являются числом!");
                    continue;
                }

                if (currentProduct < 1 || currentProduct > products.length) {
                    System.out.println("Товара под номером " + currentProduct + " не существует!");
                    continue;
                }

                if (Integer.parseInt(parts[1]) < 0) {
                    System.out.println("Нельзя выбрать такое количество товара! ");
                    continue;
                }
                myBasket.addToCart(currentProduct, currentQuan);
                myBasket.saveTxt(txtFile);
                myLog.log(currentProduct, currentQuan);

            } else {
                System.out.println("Введено неверное количество чисел! ");
                continue;
            }

        }

    }
}

