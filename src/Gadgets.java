import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.io.File;
import java.util.*;

/**
 * Created by A on 13.03.2017.
 */
public class Gadgets {
    final static String MEMORY_GB = "Gb";
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

    public String generateXMLAutoload() {
        /*if (companyName.equals(ISPARK)) {
            for (String s : mapGadgetMetaModelWithoutMemoryImages.keySet()) {
                System.out.println(s);
            }
        }*/
        String xml = "";
        for (int cityId = 0; cityId < GadgetConst.CITIES.length; cityId++) {
            LinkedHashSet<String> metaModelsUpdate = Solution.getHashSetFromInput(
                    companyName + "/update_items_" + GadgetConst
                            .CITIES_FILE_END[cityId] + ".txt");
            LinkedHashSet<String> metaModelsPresent = Solution.getHashSetFromInput(
                    companyName + "/present_items_" + GadgetConst
                            .CITIES_FILE_END[cityId] + ".txt");
            boolean isCorrect = true;
            for (String metaModel : metaModelsUpdate) {
                isCorrect &= mapMetaModelLastGadgetId.containsKey(metaModel);
            }
            for (String metaModel : metaModelsPresent) {
                isCorrect &= mapMetaModelLastGadgetId.containsKey(metaModel);
                if (!isCorrect) {
                    System.out.println("check " + metaModel);
                }
            }
            if (!isCorrect) {
                System.out.println("input gadgets " + companyName + " incorrect!");
                break;
            }
            for (String metaModel : metaModelsUpdate) {
                int metaModelLastId = getMaxLastIdMemory(metaModel.replace("Gb", ""))
                        % GadgetConst.COUNTRIES.size();
                if (metaModel.contains(Gadgets.MEMORY_GB)) {
                    for (Object metaModelIncreaseIdObj : new ArrayList<>(mapMetaModelLastGadgetId.keySet())) {
                        String metaModelIncreaseId = (String) metaModelIncreaseIdObj;
                        if (metaModelIncreaseId.startsWith(metaModel
                                .replaceAll(" ?" + Gadgets.MEMORY_GB, ""))) {
                            mapMetaModelLastGadgetId.put(metaModelIncreaseId,
                                    metaModelLastId + 1);
                        }
                    }
                    mapMetaModelLastGadgetId.put(getMetaModelWithoutMemory(metaModel),
                            metaModelLastId + 1);
                } else {
                    metaModelLastId = getMaxLastIdWithoutMemory(metaModel)
                            % GadgetConst.COUNTRIES.size();
                    for (Object metaModelIncreaseIdObj : new ArrayList<>(mapMetaModelLastGadgetId.keySet())) {
                        String metaModelIncreaseId = (String) metaModelIncreaseIdObj;
                        if (getMetaModelWithoutMemory(metaModelIncreaseId)
                                .equals(metaModel)) {
                            mapMetaModelLastGadgetId.put(metaModelIncreaseId,
                                    metaModelLastId + 1);
                        }
                    }
                }
                mapMetaModelCurrGadgetId[cityId].put(metaModel, metaModelLastId);
            }
            for (String metaModel : metaModelsPresent) {
                if (!mapMetaModelLastGadgetId.keySet().contains(metaModel)) {
                    continue;
                }
                int gadgetGroupId = mapMetaModelCurrGadgetId[cityId].get(metaModel);
                System.out.println(cityId + " " + metaModel + " " + gadgetGroupId);
                if (gadgetGroupId == -1) {
                    continue;
                }
                GadgetGroup gadgetGroup = mapGadgetMetaModelGadgetGroups.get(
                        metaModel).get(gadgetGroupId);
                xml += gadgetGroup.getXmlAd(cityId, false);
            }
        }
        return xml;
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
            if (!line.contains(MEMORY_GB) || line.contains(GadgetConst.ADTnoTouchID)) {
                continue;
            }
            line = line.replace(" (Snapdragon 625) ", " ")
                    .replace(" Dual Sim ", " ");
            String[] wordsRaw = line.split("\\s+");
            int memoryId = 0;
            for (int i = 0; i < wordsRaw.length; i++) {
                if (wordsRaw[i].contains(MEMORY_GB)) {
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
            for (String quality : GadgetConst.QUALITIES) {
                gadgetName = gadgetName.replace(quality + " ", "");
            }
            String metaModelWithoutMemory = getMetaModelWithoutMemory(gadgetName);
//            System.out.println("check " + metaModel);
            mapGadgetMetaModelWithoutMemorySingle.put(metaModelWithoutMemory,
                    !mapGadgetMetaModelWithoutMemorySingle.containsKey(metaModelWithoutMemory));
            if (!mapMetaModelLastGadgetId.containsKey(gadgetName)) {
                mapMetaModelLastGadgetId.put(gadgetName, 0);
                for (int i = 0; i < GadgetConst.CITIES.length; i++) {
                    mapMetaModelCurrGadgetId[i].put(gadgetName, -1);
                }
            }
        }
        for (Object metaModelObject : new ArrayList<>(mapMetaModelLastGadgetId.keySet())) {
            String metaModel = (String) metaModelObject;
            if (!mapGadgetMetaModelWithoutMemorySingle.containsKey(
                    getMetaModelWithoutMemory(metaModel))) {
//                System.out.println("test " + metaModel);
                mapMetaModelLastGadgetId.remove(metaModel);
            }
        }
        //because for example 6 16gb and 6 16 gb is very different variants
        //and to add models without memory
        for (Object metaModelObject : new ArrayList<>(mapMetaModelLastGadgetId.keySet())) {
            String metaModel = (String) metaModelObject;
//            System.out.println(companyName + " " + metaModel + " "
//                    + mapMetaModelLastGadgetId.get(metaModel));
            String metaModelWithoutMemory = getMetaModelWithoutMemory(
                    metaModel);
            int maxId = getMaxLastIdWithoutMemory(metaModelWithoutMemory);
            if (!mapMetaModelLastGadgetId.containsKey(metaModelWithoutMemory)) {
                mapMetaModelLastGadgetId.put(metaModelWithoutMemory, maxId);
                for (int i = 0; i < GadgetConst.CITIES.length; i++) {
                    mapMetaModelCurrGadgetId[i].put(metaModelWithoutMemory, -1);
                }
            }
            if (!mapGadgetMetaModelWithoutMemorySingle.get(metaModelWithoutMemory)) {
                String metaModelSpaceMemory = getMetaModelSpaceMemory(metaModel);
                maxId = getMaxLastIdMemory(metaModel.replace("Gb", ""));
                if (!mapMetaModelLastGadgetId.containsKey(metaModelSpaceMemory)) {
                    mapMetaModelLastGadgetId.put(metaModelSpaceMemory, maxId);
                    for (int i = 0; i < GadgetConst.CITIES.length; i++) {
                        mapMetaModelCurrGadgetId[i].put(metaModelSpaceMemory, -1);
                    }
                }
            }
        }
//        for (Object metaModel : new ArrayList<>(mapMetaModelLastGadgetId.keySet())) {
//            System.out.println(companyName + " " + metaModel + " " +
//                    mapMetaModelLastGadgetId.get(metaModel));
//        }
    }

    private String getMetaModelSpaceMemory(String metaModel) {
        return metaModel.replace("Gb", " Gb").replace("  ", " ");
    }

    private int getMaxLastIdMemory(String metaModelCommon) {
        int maxId = -1;
        for (Object metaModelObject : new ArrayList<>(mapMetaModelLastGadgetId.keySet())) {
            String metaModel = (String) metaModelObject;
            if (metaModel.startsWith(metaModelCommon)) {
                maxId = Math.max(maxId, mapMetaModelLastGadgetId.get(metaModel));
            }
        }
        return maxId;
    }

    private int getMaxLastIdWithoutMemory(String metaModelCommon) {
        int maxId = -1;
        for (Object metaModelObject : new ArrayList<>(mapMetaModelLastGadgetId.keySet())) {
            String metaModel = (String) metaModelObject;
            if (getMetaModelWithoutMemory(metaModel).equals(metaModelCommon)) {
                maxId = Math.max(maxId, mapMetaModelLastGadgetId.get(metaModel));
            }
        }
        return maxId;
    }

    public static String getMetaModelWithoutMemory(String metaModel) {
        return metaModel.replaceAll(" [0-9]+ ?Gb.*", "");
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
        ArrayList<String> pricesArray1 = mapGadgetNamePrices.get(getGadgetName(gadget, QUALITY, MEMORY)
                .replace("  ", " "));
        ArrayList<String> pricesArray2 = mapGadgetNamePrices.get(getGadgetName(gadget, QUALITY, COLOR)
                .replace("  ", " "));
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
            String metaModelSpaceMemory = getMetaModelSpaceMemory(metaModel);
            String metaModelWithoutMemory = getMetaModelWithoutMemory(metaModel);
            if (!mapMetaModelGadgets.containsKey(metaModel)) {
                mapMetaModelGadgets.put(metaModel,
                        new ArrayList<ArrayList<String>>());
                mapMetaModelGadgets.put(metaModelSpaceMemory,
                        new ArrayList<ArrayList<String>>());
            }
            //if add models without memory
            if (!mapMetaModelGadgets.containsKey(metaModelWithoutMemory)) {
                mapMetaModelGadgets.put(metaModelWithoutMemory,
                        new ArrayList<ArrayList<String>>());
            }
            mapMetaModelGadgets.get(metaModel).add(gadget);
            mapMetaModelGadgets.get(metaModelSpaceMemory).add(gadget);
            mapMetaModelGadgets.get(metaModelWithoutMemory).add(gadget);
        }
        mapGadgetMetaModelGadgetGroups = new LinkedHashMap<>();
        for (String metaModel : mapMetaModelGadgets.keySet()) {
            ArrayList<GadgetGroup> gadgetGroups = new ArrayList<>();
            for (int i = 0; i < GadgetConst.COUNTRIES.size(); i++) {
                GadgetGroup gadgetGroup = new GadgetGroup(i, metaModel, this);
                gadgetGroup.gadgets.addAll(mapMetaModelGadgets.get(metaModel));
                gadgetGroups.add(gadgetGroup.calcAdGadgetId());
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
