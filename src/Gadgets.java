import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by A on 13.03.2017.
 */
public class Gadgets {
    final static int PRICES_COUNT = 8;
    final static String ROOT_DIR = "C:/EKZ/";
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

    public final static HashMap<String, ArrayList<String>> modelsByModelLine;

    static {
        modelsByModelLine = new HashMap<>();
        modelsByModelLine.put("iPhone", iPhones.models);
        modelsByModelLine.put("Galaxy", Galaxys.models);
    }

    public final static HashMap<String, ArrayList<ArrayList<String>>> colorsByModelLine;

    static {
        colorsByModelLine = new HashMap<>();
        colorsByModelLine.put("iPhone", iPhones.colors);
        colorsByModelLine.put("Galaxy", Galaxys.colors);
    }

    public final static HashMap<String, ArrayList<Integer>> gadgetPerMonthCountByModelLine;

    static {
        gadgetPerMonthCountByModelLine = new HashMap<>();
        gadgetPerMonthCountByModelLine.put("iPhone", iPhones.gadgetPerMonthCount);
        gadgetPerMonthCountByModelLine.put("Galaxy", Galaxys.gadgetPerMonthCount);
    }

    public final static HashMap<String, HashMap<String, ArrayList<String>>> mapGadgetModelSubmodelByModelLine;

    static {
        mapGadgetModelSubmodelByModelLine = new HashMap<>();
        mapGadgetModelSubmodelByModelLine.put("iPhone", iPhones.mapModelSubmodel);
        mapGadgetModelSubmodelByModelLine.put("Galaxy", Galaxys.mapModelSubmodel);
    }

    public HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    public HashMap<String, Integer> mapPriceAttributeNumber;

    public ArrayList<ArrayList<String>> gadgets = new ArrayList<ArrayList<String>>();
    public HashMap<String, Integer> mapGadgetAttributeNumber;

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
