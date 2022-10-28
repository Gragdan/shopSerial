import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, XPathExpressionException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"cheese", "bread"};
        double[] prices = {12.5, 4};
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("config.xml");

        XPath xPath = XPathFactory.newInstance().newXPath();
        String loadFileName = xPath.compile("/config/load/fileName").evaluate(doc);

        Boolean loadFileEnabled = Boolean
                .parseBoolean(xPath.compile("/config/load/enabled").evaluate(doc));

        Boolean saveFileEnabled = Boolean
                .parseBoolean(xPath.compile("/config/save/enabled").evaluate(doc));

        Boolean logEnabled = Boolean
                .parseBoolean(xPath.compile("/config/log/enabled").evaluate(doc));

        String logFileName = xPath.compile("/config/log/fileName").evaluate(doc);
      //  System.out.println(loadFileName);

        String saveFileName = xPath.compile("/config/save/fileName").evaluate(doc);

        String saveFormat = xPath.compile("/config/save/format").evaluate(doc);


        File jsonFile = new File(loadFileName);
        File saveJson = new File(saveFileName);
        File saveText = new File("basket.txt");

        Basket myBasket = null;
        ClientLog myLog = new ClientLog();
        if (loadFileEnabled) {
            myBasket = myBasket.loadFromJson(jsonFile);
        } else {
            jsonFile.createNewFile();
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
                if (logEnabled) {
                    myLog.exportAsCSV(logFileName);
                }

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
                if (saveFileEnabled) {
                    if("json".equals(saveFormat)) {
                        myBasket.saveJSONObject(saveJson);
                    }
                    if("text".equals(saveFormat)) {
                        myBasket.saveTxt(saveText);
                    }

                }
                if (logEnabled) {
                    myLog.log(currentProduct, currentQuan);
                }


            } else {
                System.out.println("Введено неверное количество чисел! ");
                continue;
            }

        }

    }
}

