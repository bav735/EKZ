import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by A on 13.03.2017.
 */
public class Gadgets {
    final static int PRICES_COUNT = 4;
    final static String NO_PRICE = "-";
    final static String ISPARK = "iSPARK";
    final static String AMOLED = "iSPARK";
    final static String EST_RETAIL_AMOLED = "евротест авито/нал";
    final static String EST_RETAIL_ISPARK = "евротест сайт/маркет";
    final static String EST_RETAIL_OPT_MAX = "евротест опт от 3шт";
    final static String EST_RETAIL_OPT_MIN = "евротест опт от 10шт";
    final static String RST_RETAIL_AMOLED = "ростест авито/нал";
    final static String RST_RETAIL_ISPARK = "ростест сайт/маркет";
    final static String RST_RETAIL_OPT_MAX = "ростест опт от 3шт";
    final static String RST_RETAIL_OPT_MIN = "ростест опт от 10шт";
    public final static String[] priceAttributeNames = new String[]{
            EST_RETAIL_AMOLED,
            EST_RETAIL_ISPARK,
            EST_RETAIL_OPT_MAX,
            EST_RETAIL_OPT_MIN,
            RST_RETAIL_AMOLED,
            RST_RETAIL_ISPARK,
            RST_RETAIL_OPT_MAX,
            RST_RETAIL_OPT_MIN,
    };

    public static HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    public static HashMap<String, Integer> mapPriceAttributeNumber;

    public ArrayList<ArrayList<String>> gadgets = new ArrayList<ArrayList<String>>();
    public HashMap<String, Integer> mapGadgetAttributeNumber;

    public static void initializePrices(Scanner inScanner) {
        mapPriceAttributeNumber = new HashMap<>();
        for (int i = 0; i < priceAttributeNames.length; i++) {
            mapPriceAttributeNumber.put(priceAttributeNames[i], i);
        }
        mapGadgetNamePrices = new HashMap<>();
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (!line.contains("Gb")) {
                continue;
            }
            String[] words = line.split("\\s+");
            String gadgetName = String.join(" ", Arrays.copyOfRange(words, 0, words.length - PRICES_COUNT));
            String[] prices = Arrays.copyOfRange(words, words.length - PRICES_COUNT, words.length);
            mapGadgetNamePrices.put(gadgetName, new ArrayList<>(Arrays.asList(prices)));
//            System.out.println(gadgetName);
        }
        inScanner.close();
    }

    public void initializeMapGadgetAttributeNumber(String[] gadgetAttributeNames) {
        mapGadgetAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < gadgetAttributeNames.length; i++) {
            mapGadgetAttributeNumber.put(gadgetAttributeNames[i], i);
        }
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
