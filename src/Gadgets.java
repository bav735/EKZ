import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.io.File;
import java.util.*;

/**
 * Created by A on 13.03.2017.
 */
public abstract class Gadgets {
    static final String ISPARK = "iSPARK";
    static final String AMOLED = "AMOLED";
    static final String QUALITY = "Качество";
    static final String VENDOR = "Производитель";
    static final String MODEL_LINE = "Модельный ряд";
    static final String MODEL = "Модель";
    final String SUBMODEL = "Подмодель";
    static final String MEMORY = "Память";
    static final String COLOR = "Цвет";
    final String TOUCH_LOCKED = "Без Отп";
    final String IMG_FILE_NAME = "img";

    public abstract String generateXMLAutoload();

    static final String UNUSED_PRICE = "цена не используется";
    //    final static String PREPAY_PRICE = "по предоплате (доставка по РФ: бесплатно)";
    static final String MOSCOW_PRICE = "cамовывоз Москва (курьерская доставка: +400\u20BD)";
    static final String REGIONS_PRICE = "cамовывоз Регионы (курьерская доставка: +300\u20BD)";
    static final String YEAR_WARRANTY_COST = "Гарантия на ремонтное обслуживание (1 год)";
    public static final String[] PRICE_ATTRIBUTE_NAMES = new String[]{
            UNUSED_PRICE,
            UNUSED_PRICE,
            UNUSED_PRICE,
            MOSCOW_PRICE,
            REGIONS_PRICE,
            YEAR_WARRANTY_COST,
    };
    public static final int PRICE_SHIFT = 3;
    public static final String[] GADGET_ATTRIBUTE_NAMES = new String[]{
            QUALITY,
            VENDOR,
            MODEL_LINE,
            MODEL,
            MEMORY,
            COLOR,
    };

    public static HashMap<String, Integer> mapPriceAttributeNumber;
    public static HashMap<String, Integer> mapGadgetAttributeNumber;

    public ArrayList<ArrayList<String>> gadgets = new ArrayList<>();
    public HashMap<String, Boolean> mapGadgetMetaModelWithoutMemorySingle;
    public HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    public DB GADGET_DB;
    public HTreeMap<String, Integer> mapMetaModelLastGadgetId;
    public HTreeMap<String, Integer>[] mapMetaModelCurrGadgetId;
    public String companyName;
    public LinkedHashMap<String, ArrayList<GadgetGroup>> mapGadgetMetaModelGadgetGroups;
    public HashMap<String, ArrayList<String>> mapGadgetMetaModelWithoutMemoryImages;

    static {
        mapPriceAttributeNumber = new HashMap<>();
        mapGadgetAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < GADGET_ATTRIBUTE_NAMES.length; i++) {
            mapGadgetAttributeNumber.put(GADGET_ATTRIBUTE_NAMES[i], i);
        }
        for (int i = 0; i < PRICE_ATTRIBUTE_NAMES.length; i++) {
            mapPriceAttributeNumber.put(PRICE_ATTRIBUTE_NAMES[i], i);
        }
    }

    public void initialize() {
        mapMetaModelCurrGadgetId = new HTreeMap[GadgetConst.CITIES.length];
        GADGET_DB = DBMaker.fileDB(new File("C:/EKZ/DB/" + companyName +
                "gadgets.db")).make();
        mapMetaModelLastGadgetId = GADGET_DB.hashMap(companyName +
                        "MAP_META_MODEL_LAST_GADGET_ID",
                Serializer.STRING, Serializer.INTEGER).createOrOpen();
        for (int i = 0; i < GadgetConst.CITIES.length; i++) {
            mapMetaModelCurrGadgetId[i] = GADGET_DB.hashMap(companyName +
                            "MAP_META_MODEL_CURR_GADGET_ID" + GadgetConst.CITIES[i],
                    Serializer.STRING, Serializer.INTEGER).createOrOpen();
        }
        mapGadgetNamePrices = new HashMap<>();
        Scanner inScanner = Solution.getInputScanner(companyName + "/price_list.txt");
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (!line.contains("Gb")) {
                continue;
            }
            line = line.replace(" (Snapdragon 625) ", " ")
                    .replace(" Dual Sim ", " ");
            String[] wordsRaw = line.split("\\s+");
            int memoryId = 0;
            for (int i = 0; i < wordsRaw.length; i++) {
                if (wordsRaw[i].contains("Gb")) {
                    wordsRaw[i] = wordsRaw[i].replaceAll("[0-9]+[/+]", "");
                    memoryId = i;
                }
            }
            if (memoryId < 4) {
                continue;
            }
            int yearId = -1;
            for (int i = 0; i < wordsRaw.length; i++) {
                if (wordsRaw[i].contains("(2016)") || wordsRaw[i].contains("(2017)")) {
                    yearId = i;
                }
            }
            if (yearId > memoryId) {
                String year = wordsRaw[yearId];
                for (int i = yearId; i > memoryId; i--) {
                    wordsRaw[i] = wordsRaw[i - 1];
                }
                wordsRaw[memoryId] = year;
                memoryId++;
            }
            String[] words = new String[memoryId + 1 + PRICE_ATTRIBUTE_NAMES.length];
            for (int i = 0; i < memoryId + 1; i++) {
                words[i] = wordsRaw[i];
            }
            String gadgetName = String.join(" ", Arrays.copyOfRange(words, 0, words.length
                    - PRICE_ATTRIBUTE_NAMES.length));
            String[] prices = Arrays.copyOfRange(wordsRaw, wordsRaw.length
                    - PRICE_ATTRIBUTE_NAMES.length, wordsRaw.length);
            mapGadgetNamePrices.put(gadgetName, new ArrayList<>(Arrays.asList(prices)));
        }
        inScanner.close();
        mapGadgetMetaModelWithoutMemorySingle = new HashMap<>();
        for (String gadgetName : mapGadgetNamePrices.keySet()) {
            String metaModel = gadgetName;
            for (String quality : GadgetConst.QUALITIES) {
                metaModel = metaModel.replace(quality + " ", "");
            }
            String metaModelWithoutMemory = getMetaModelWithoutMemory(metaModel);
//            System.out.println("check " + metaModel);
            mapGadgetMetaModelWithoutMemorySingle.put(metaModelWithoutMemory,
                    !mapGadgetMetaModelWithoutMemorySingle.containsKey(metaModelWithoutMemory));
            if (!mapMetaModelLastGadgetId.containsKey(metaModel)) {
                mapMetaModelLastGadgetId.put(metaModel, 0);
                for (int i = 0; i < GadgetConst.CITIES.length; i++) {
                    mapMetaModelCurrGadgetId[i].put(metaModel, -1);
                }
            }
        }
    }

    public String getMetaModelWithoutMemory(String metaModel) {
        return metaModel.replaceFirst(" [0-9]+Gb?*", "");
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

    public String getPrice(ArrayList<String> gadget, String priceName) {
        int priceAttrNumber = mapPriceAttributeNumber.get(priceName);
        ArrayList<String> pricesArray1 = mapGadgetNamePrices.get(getGadgetName(gadget, QUALITY, MEMORY));
        ArrayList<String> pricesArray2 = mapGadgetNamePrices.get(getGadgetName(gadget, QUALITY, COLOR));
        if (pricesArray1 != null) {
            return pricesArray1.get(priceAttrNumber);
        }
        return pricesArray2.get(priceAttrNumber);
    }

    public String getGadgetName(ArrayList<String> gadget, String firstNameAttr, String lastNameAttr) {
        return String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(firstNameAttr),
                mapGadgetAttributeNumber.get(lastNameAttr) + 1));
    }

    public String getMetaModel(ArrayList<String> gadget) {
        return getGadgetName(gadget, VENDOR, MEMORY);
    }

    public String getVendor(ArrayList<String> gadget) {
        return gadget.get(mapGadgetAttributeNumber.get(VENDOR));
    }

    public String getQuality(ArrayList<String> gadget) {
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

    public void initMapGadgetMetaModelGadgetGroups() {
        LinkedHashMap<String, ArrayList<ArrayList<String>>> mapMetaModelGadgets = new LinkedHashMap<>();
        for (ArrayList<String> gadget : gadgets) {
            String metaModel = getMetaModel(gadget);
            if (!mapMetaModelGadgets.containsKey(metaModel)) {
                mapMetaModelGadgets.put(metaModel,
                        new ArrayList<ArrayList<String>>());
            }
            mapMetaModelGadgets.get(metaModel).add(gadget);
        }
        mapGadgetMetaModelGadgetGroups = new LinkedHashMap<>();
        for (String metaModel : mapMetaModelGadgets.keySet()) {
            ArrayList<GadgetGroup> gadgetGroups = new ArrayList<>();
            for (String country : GadgetConst.COUNTRIES) {
                GadgetGroup gadgetGroup = new GadgetGroup(country, this);
                gadgetGroup.gadgets.addAll(mapMetaModelGadgets.get(metaModel));
                gadgetGroups.add(gadgetGroup);
            }
            mapGadgetMetaModelGadgetGroups.put(metaModel, gadgetGroups);
        }
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
