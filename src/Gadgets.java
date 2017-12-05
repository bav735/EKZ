import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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
    final static String COLOR = "Цвет";
    final static String TOUCH_LOCKED = "Без Отп";
    final static String IMG_FILE_NAME = "img";

    final static String UNUSED_PRICE = "цена не используется";
    //    final static String PREPAY_PRICE = "по предоплате (доставка по РФ: бесплатно)";
    final static String MOSCOW_PRICE = "cамовывоз Москва (курьерская доставка: +400\u20BD)";
    final static String REGIONS_PRICE = "cамовывоз Регионы (курьерская доставка: +300\u20BD)";
    final static String YEAR_WARRANTY_COST = "Гарантия на ремонтное обслуживание (1 год)";
    public final static String[] PRICE_ATTRIBUTE_NAMES = new String[]{
            UNUSED_PRICE,
            UNUSED_PRICE,
            UNUSED_PRICE,
            MOSCOW_PRICE,
            REGIONS_PRICE,
            YEAR_WARRANTY_COST,
    };
    final static int PRICE_SHIFT = 3;
    public final static String[] GADGET_ATTRIBUTE_NAMES = new String[]{
            QUALITY,
            VENDOR,
            MODEL_LINE,
            MODEL,
            MEMORY,
            COLOR,
    };

    public static HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    public static HashMap<String, Integer> mapPriceAttributeNumber;
    public static HashMap<String, Integer> mapGadgetAttributeNumber;
    public static HashMap<String, Boolean> mapGadgetMetaModelSingleMemory;

    static {
        mapGadgetAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < GADGET_ATTRIBUTE_NAMES.length; i++) {
            mapGadgetAttributeNumber.put(GADGET_ATTRIBUTE_NAMES[i], i);
        }
    }

    public static void initializePrices(Scanner inScanner) {
        mapPriceAttributeNumber = new HashMap<>();
        for (int i = 0; i < PRICE_ATTRIBUTE_NAMES.length; i++) {
            mapPriceAttributeNumber.put(PRICE_ATTRIBUTE_NAMES[i], i);
        }
        mapGadgetNamePrices = new HashMap<>();
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (!line.contains("Gb")) {
                continue;
            }
            String[] words = line.split("\\s+");
            String gadgetName = String.join(" ", Arrays.copyOfRange(words, 0, words.length
                    - PRICE_ATTRIBUTE_NAMES.length));
            String[] prices = Arrays.copyOfRange(words, words.length
                    - PRICE_ATTRIBUTE_NAMES.length, words.length);
            mapGadgetNamePrices.put(gadgetName, new ArrayList<>(Arrays.asList(prices)));
        }
        inScanner.close();
        mapGadgetMetaModelSingleMemory = new HashMap<>();
        for (String gadgetName : mapGadgetNamePrices.keySet()) {
            String metaModel = gadgetName.substring(gadgetName.indexOf(" ") + 1);
            String metaModelWithoutMemory = getMetaModelWithoutMemory(metaModel);
            mapGadgetMetaModelSingleMemory.put(metaModelWithoutMemory,
                    !mapGadgetMetaModelSingleMemory.containsKey(metaModelWithoutMemory));
            if (!GadgetConst.MAP_META_MODEL_LAST_GADGET_ID.containsKey(metaModel)) {
                GadgetConst.MAP_META_MODEL_LAST_GADGET_ID.put(metaModel, 0);
                for (int i = 0; i < GadgetConst.CITIES.length; i++) {
                    GadgetConst.MAP_META_MODEL_CURR_GADGET_ID[i].put(metaModel, -1);
                }
            }
        }
    }

    public static String getMetaModelWithoutMemory(String metaModel) {
        return metaModel.substring(0, metaModel.lastIndexOf(" "));
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

    public static String getPrice(ArrayList<String> gadget, String priceName) {
        int priceAttrNumber = mapPriceAttributeNumber.get(priceName);
        ArrayList<String> pricesArray1 = mapGadgetNamePrices.get(getGadgetName(gadget, QUALITY, MEMORY));
        ArrayList<String> pricesArray2 = mapGadgetNamePrices.get(getGadgetName(gadget, QUALITY, COLOR));
        if (pricesArray1 != null) {
            return pricesArray1.get(priceAttrNumber);
        }
        return pricesArray2.get(priceAttrNumber);
    }

    public static boolean isInPriceList(ArrayList<String> gadget) {
        return mapGadgetNamePrices.containsKey(getGadgetName(gadget, QUALITY, MEMORY)) ||
                mapGadgetNamePrices.containsKey(getGadgetName(gadget, QUALITY, COLOR));
    }

    public static String getGadgetName(ArrayList<String> gadget, String firstNameAttr, String lastNameAttr) {
        return String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(firstNameAttr),
                mapGadgetAttributeNumber.get(lastNameAttr) + 1));
    }

    public static String getMetaModel(ArrayList<String> gadget) {
        return getGadgetName(gadget, VENDOR, MEMORY);
    }

    public static String getVendor(ArrayList<String> gadget) {
        return gadget.get(mapGadgetAttributeNumber.get(VENDOR));
    }

    public static String getQuality(ArrayList<String> gadget) {
        return gadget.get(mapGadgetAttributeNumber.get(QUALITY));
    }

    public String getFullPath(ArrayList<String> gadget) {
        String path = "";
        for (int i = mapGadgetAttributeNumber.get(QUALITY); i <= mapGadgetAttributeNumber.get(COLOR); i++) {
            String attr = gadget.get(i).replaceAll("[() -]", "");
            path += attr + "/";
        }
        return path + IMG_FILE_NAME + ".jpg";
    }

    /*

    public static void initializeNewPrices(Scanner inScanner) {
        mapNewPriceAttributeNumber = new HashMap<>();
        for (int i = 0; i < newPriceAttributeNames.length; i++) {
            mapNewPriceAttributeNumber.put(newPriceAttributeNames[i], i);
        }
        mapGadgetNameNewPrices = new HashMap<>();
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (!line.contains("Gb")) {
                continue;
            }
            String[] words = line.split("\\s+");
            String gadgetName = String.join(" ", Arrays.copyOfRange(words, 0, words.length - NEW_PRICES_COUNT));
            String[] prices = Arrays.copyOfRange(words, words.length - NEW_PRICES_COUNT, words.length);
            mapGadgetNameNewPrices.put(gadgetName, new ArrayList<>(Arrays.asList(prices)));
        }
        inScanner.close();
    }

    */
}
