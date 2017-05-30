import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by A on 13.03.2017.
 */
public class Gadgets {
    final static int PRICES_COUNT = 8;
    final static String ROOT_DIR = "C:/AMOLED/";
    final static String EST_RETAIL_MIN = "евротест авито/нал";
    final static String EST_RETAIL_MAX = "евротест сайт/маркет";
    final static String EST_RETAIL_OPT_MAX = "евротест опт от 3шт";
    final static String EST_RETAIL_OPT_MIN = "евротест опт от 10шт";
    final static String RST_RETAIL_MIN = "ростест авито/нал";
    final static String RST_RETAIL_MAX = "ростест сайт/маркет";
    final static String RST_RETAIL_OPT_MAX = "ростест опт от 3шт";
    final static String RST_RETAIL_OPT_MIN = "ростест опт от 10шт";

    public static HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    public static HashMap<String, Integer> mapPriceAttributeNumber;
    public static String[] priceAttributeNames = new String[]{
            EST_RETAIL_MIN,
            EST_RETAIL_MAX,
            EST_RETAIL_OPT_MAX,
            EST_RETAIL_OPT_MIN,
            RST_RETAIL_MIN,
            RST_RETAIL_MAX,
            RST_RETAIL_OPT_MAX,
            RST_RETAIL_OPT_MIN,
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
        Scanner inScanner = Solution.getInputScanner("price_list_iphone.txt");
        mapGadgetNamePrices = new HashMap<>();
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            String[] words = line.split("\\s+");
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
        inScanner = Solution.getInputScanner("price_list_samsung.txt");
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            String[] words = line.split("\\s+");
            int i = 0;
            String gadgetName = "Samsung";
            do {
                i++;
                gadgetName += " " + words[i];
            } while (!words[i].contains("Gb"));
            ArrayList<String> prices = new ArrayList<>();
            for (int priceId = PRICES_COUNT; priceId > 0; priceId--) {
                prices.add(words[words.length - priceId]);
            }
            mapGadgetNamePrices.put(gadgetName, prices);
        }
        inScanner.close();
    }

    public int[] mergeArrays(int[] a1, int[] a2, int[] order) {
        int[][] a = new int[2][];
        a[0] = a1;
        a[1] = a2;
        int[] rezult = new int[a[0].length + a[1].length];
        int[] i = new int[2];
        Arrays.fill(i, 0);
        for (int j = 0; j < rezult.length; j++) {
            int t = order[j % order.length];
            rezult[j] = a[t][i[t]];
            i[t]++;
        }
        return rezult;
    }
}
