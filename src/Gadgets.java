import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by A on 13.03.2017.
 */
public class Gadgets {
    final static int PRICES_COUNT = 6;
    final static String ROOT_DIR = "C:/AMOLED/";
    final static String RST_RETAIL = "ростест розница";
    final static String RST_OPT = "ростест опт";
    final static String EST_RETAIL_MIN = "евротест 14 дней гарантия";
    final static String EST_RETAIL_MAX = "евротест год гарантия";
    final static String EST_RETAIL_PER = "евротест цена 1 мес гарантии";
    final static String EST_RETAIL_OPT = "евротест опт";

    public static HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    public static HashMap<String, Integer> mapPriceAttributeNumber;
    public static String[] priceAttributeNames = new String[]{
            EST_RETAIL_MAX,
            EST_RETAIL_MIN,
            EST_RETAIL_OPT,
            EST_RETAIL_PER,
            RST_RETAIL,
            RST_OPT,
    };

    public ArrayList<ArrayList<String>> gadgets = new ArrayList<ArrayList<String>>();
    public HashMap<String, Integer> mapGadgetAttributeNumber;
    public String[] gadgetAttributeNames;


    public void initializeMapGadgetAttributeNumber() {
        mapGadgetAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < gadgetAttributeNames.length; i++) {
            mapGadgetAttributeNumber.put(gadgetAttributeNames[i], i);
        }
    }

    public static void initializeFromPriceList() {
        mapPriceAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < priceAttributeNames.length; i++) {
            mapPriceAttributeNumber.put(priceAttributeNames[i], i);
        }
        try {
            Scanner inScanner = new Scanner(new FileInputStream(new File("C:/EKZ/Input/price_list_ispark.txt")));
            mapGadgetNamePrices = new HashMap<>();
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine();
                String[] words = line.split("\\s+");
                if (!words[1].equals("iPhone")) {
                    continue;
                }
                int i = 0;
                String gadgetName = "Apple";
                do {
                    i++;
                    gadgetName += " " + words[i];
                } while (!words[i].contains("Gb"));
                if (words[i + 1].equals("Без")) {
                    gadgetName += " Б/О";
                }
                ArrayList<String> prices = new ArrayList<>();
                for (int priceId = PRICES_COUNT; priceId > 0; priceId--) {
                    prices.add(words[words.length - priceId]);
                }
                mapGadgetNamePrices.put(gadgetName, prices);
            }
            inScanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Exception: Input file not found!");
        }
    }
}
