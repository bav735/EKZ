import java.util.*;

/**
 * Created by A on 13.03.2017.
 */
public class Gadgets {
    final static String QUALITY = "Качество";
    final static String VENDOR = "Производитель";
    final static String MODEL_LINE = "Модельный ряд";
    final static String MODEL = "Модель";
    final static String SUBMODEL = "Подмодель";
    final static String MEMORY = "Память";
    final static String FINGER_PRINT = "Наличие отпечатка";
    final static String COLOR = "Цвет";
    final static String TOUCH_APPLE_NO = "-";
    final static String TOUCH_LOCKED = "Без Отп";
    final static String IMG_FILE_NAME = "img";
    final static int DAYS_OFFSET = 3; //установлено 20.09.17
    final static int TIME_DAY_SEC = 12 * 60 * 60;
    final static int TIME_MONTH_SEC = 30 * TIME_DAY_SEC;
    final static int HOUR_BEGIN = 9;
    public static final Calendar CALENDAR_ZERO;
    public static final int DAY_NUM_GLOBAL;

    static {
        CALENDAR_ZERO = Calendar.getInstance();
        CALENDAR_ZERO.set(Calendar.YEAR, 2017);
        CALENDAR_ZERO.set(Calendar.MONTH, 8);//september
        CALENDAR_ZERO.set(Calendar.DAY_OF_MONTH, 15);
        setCalendarToZero(CALENDAR_ZERO);
        Calendar calendar = Calendar.getInstance();
        setCalendarToZero(calendar);
        DAY_NUM_GLOBAL = (int) ((calendar.getTimeInMillis() - CALENDAR_ZERO.getTimeInMillis())
                / 1000 / 3600 / 24) + 1;
    }

    final static int PRICES_COUNT = 4;
    final static String NO_PRICE = "-";
    final static String ISPARK = "iSPARK";
    final static String RETAIL_MIN = "евротест авито/нал";
    final static String RETAIL_MAX = "евротест сайт/маркет";
    final static String OPT_MAX = "евротест опт от 3шт";
    final static String OPT_MIN = "евротест опт от 10шт";
    public final static String[] priceAttributeNames = new String[]{
            RETAIL_MIN,
            RETAIL_MAX,
            OPT_MAX,
            OPT_MIN,
    };
    public final static String[] gadgetAttributeNames = new String[]{
            QUALITY,
            VENDOR,
            MODEL_LINE,
            MODEL,
            MEMORY,
            SUBMODEL,
            COLOR,
            FINGER_PRINT
    };

    public static HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    public static HashMap<String, Integer> mapPriceAttributeNumber;

    public ArrayList<ArrayList<String>> gadgets = new ArrayList<ArrayList<String>>();
    public static HashMap<String, Integer> mapGadgetAttributeNumber;

    static {
        mapGadgetAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < gadgetAttributeNames.length; i++) {
            mapGadgetAttributeNumber.put(gadgetAttributeNames[i], i);
        }
    }

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
//            System.out.println("from price:" + gadgetName);
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

    private static void setCalendarToZero(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static String getPrice(String gadgetName, String priceName) {
        return mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(priceName));
    }

    public static String getGadgetName(ArrayList<String> gadget) {
        String name = String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(QUALITY),
                mapGadgetAttributeNumber.get(MEMORY) + 1));
        int lastAttr = mapGadgetAttributeNumber.get(FINGER_PRINT);
        if (gadget.size() > lastAttr && gadget.get(lastAttr).length() > 1) {
            name += " " + gadget.get(lastAttr);
        }
        return name;
    }
}
