import java.util.*;

public class YoulaGadgets extends Gadgets {
    final static String QUALITY = "Качество";
    final static String VENDOR = "Производитель";
    final static String MODEL_LINE = "Модельный ряд";
    final static String MODEL = "Модель";
    final static String MEMORY = "Память";
    final static String FINGER_PRINT = "Наличие отпечатка";
    final static String COLOR = "Цвет";
    final static String TOUCH_LOCKED = "Б/О";
    final static String NEW = "Новый";
    final static String RFB = "";
    final static int DISTRIBUTION_SIZE = 6;
    final static int ADS_PER_DAY = 5;
    final static int IPHONES_COUNT = 12;

    HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets;
    HashMap<String, ArrayList<String>> mapGadgetModelColor;
    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    int[][] gadgetsDistribution;

    public YoulaGadgets() {
    }

    public void initializeIPhones() {
        gadgetAttributeNames = new String[]{
                QUALITY,
                VENDOR,
                MODEL_LINE,
                MODEL,
                MEMORY,
                FINGER_PRINT,
                COLOR,
        };
        initializeMapGadgetAttributeNumber();
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Apple")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("iPhone")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(
                "4",
                "4S",
                "5",
                "5C",
                "5S",
                "6",
                "6 Plus",
                "6S",
                "6S Plus",
                "SE",
                "7",
                "7 Plus")));
        ArrayList<String> models = gadgetAttributesVariants.get(gadgetAttributesVariants.size() - 1);
        ArrayList<ArrayList<String>> colors = new ArrayList<>();
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White")));
        colors.add(new ArrayList<String>(Arrays.asList("White", "Blue", "Green", "Yellow", "Pink")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Jet Black", "Gold", "Pink")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Jet Black", "Gold", "Pink")));
        mapGadgetModelColor = new HashMap<>();
        for (int i = 0; i < models.size(); i++) {
            mapGadgetModelColor.put(models.get(i), colors.get(i));
        }
        gadgetAttributesVariants.add(new ArrayList<>(Arrays.asList("8Gb",
                "16Gb",
                "32Gb",
                "64Gb",
                "128Gb",
                "256Gb")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("", TOUCH_LOCKED)));
        initializeDistribution();
    }

    public void initializeDistribution() {
        Scanner inScanner = Solution.getInputScanner("distribution_youla.txt");
        gadgetsDistribution = new int[IPHONES_COUNT + 1][DISTRIBUTION_SIZE];
        for (int modelId = 0; modelId < IPHONES_COUNT + 1; modelId++) {
            for (int groupId = 0; groupId < DISTRIBUTION_SIZE; groupId++) {
                gadgetsDistribution[modelId][groupId] = inScanner.nextInt();
            }
        }
        inScanner.close();
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            if (mapGadgetNamePrices.containsKey(getGadgetName(gadget))) {
                if (!gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT)).isEmpty() &&
                        !gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("5S") &&
                        !gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("6")) {
                    return;
                }
                ArrayList<String> prices = mapGadgetNamePrices.get(getGadgetName(gadget));
                if (prices.get(mapPriceAttributeNumber.get(EST_RETAIL_MIN)).length() > 1) {
                    gadget.set(mapGadgetAttributeNumber.get(QUALITY), RFB);
                } else {
                    gadget.set(mapGadgetAttributeNumber.get(QUALITY), NEW);
                }
                String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
                for (String color : mapGadgetModelColor.get(model)) {
                    ArrayList<String> newGadget = new ArrayList<String>(gadget);
                    newGadget.add(mapGadgetAttributeNumber.get(COLOR), color);
                    gadgets.add(newGadget);
//                    System.out.println(getGadgetName(newGadget));
                }
            }
        } else {
            int attributeVariantsSize = gadgetAttributesVariants.get(attribute).size();
            for (int attributeVariant = 0; attributeVariant < attributeVariantsSize; attributeVariant++) {
                ArrayList<String> newGadget = new ArrayList<String>(gadget);
                newGadget.add(gadgetAttributesVariants.get(attribute).get(attributeVariant));
                generateGadgets(attribute + 1, newGadget);
            }
        }
    }

    public void distributeIPhones() {
        Collections.shuffle(gadgets, new Random(735));
        mapGadgetModelGadgets = new HashMap<>();
        for (ArrayList<String> gadget : gadgets) {
            String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
            if (!mapGadgetModelGadgets.containsKey(model)) {
                mapGadgetModelGadgets.put(model, new ArrayList<ArrayList<String>>());
            }
            mapGadgetModelGadgets.get(model).add(gadget);
        }
        gadgets.clear();
        int[][] metaGroups = new int[DISTRIBUTION_SIZE][];
        for (int groupNum = 0; groupNum < DISTRIBUTION_SIZE; groupNum++) {
            metaGroups[groupNum] = new int[gadgetsDistribution[IPHONES_COUNT][groupNum]];
            Arrays.fill(metaGroups[groupNum], groupNum);
        }
        int[] g45 = mergeArrays(metaGroups[4], metaGroups[5], new int[]{0, 1, 0});
        int[] g245 = mergeArrays(metaGroups[2], g45, new int[]{0, 1, 0});
        int[] g2451 = mergeArrays(g245, metaGroups[1], new int[]{0, 1, 0, 1, 0});
        int[] g03 = mergeArrays(metaGroups[0], metaGroups[3], new int[]{0, 0, 1, 0, 0});
        int[] groupsOrder = mergeArrays(g03, g2451, new int[]{0, 1});
        for (int groupNum : groupsOrder) {
            ArrayList<ArrayList<String>> gadgetGroup = new ArrayList<>();
            for (int modelId = 0; modelId < IPHONES_COUNT; modelId++) {
                if (gadgetsDistribution[modelId][groupNum] == 1) {
                    gadgetGroup.add(extractGadgetByModel(modelId));
                }
            }
            gadgets.addAll(gadgetGroup);
        }
    }

    private ArrayList<String> extractGadgetByModel(int modelId) {
        String model = gadgetAttributesVariants.get(mapGadgetAttributeNumber.get(MODEL)).get(modelId);
        ArrayList<ArrayList<String>> gadgetsByModel = mapGadgetModelGadgets.get(model);
        ArrayList<String> gadget = gadgetsByModel.get(gadgetsByModel.size() - 1);
        gadgetsByModel.remove(gadgetsByModel.size() - 1);
        mapGadgetModelGadgets.put(model, gadgetsByModel);
        return gadget;
    }

    public String getYoulaAdName(ArrayList<String> gadget) {
        String name = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
        int lastAttr = mapGadgetAttributeNumber.get(COLOR);
        for (int i = mapGadgetAttributeNumber.get(MODEL_LINE); i <= lastAttr; i++) {
            if (!gadget.get(i).isEmpty()) {
                name += " " + gadget.get(i);
            }
        }
        return name;
    }

    private String getGadgetName(ArrayList<String> gadget) {
        int lastAttr = mapGadgetAttributeNumber.get(FINGER_PRINT);
        int firstAttr = mapGadgetAttributeNumber.get(VENDOR);
        String name = gadget.get(firstAttr);
        for (int i = firstAttr + 1; i <= lastAttr; i++) {
            if (!gadget.get(i).isEmpty()) {
                name += " " + gadget.get(i);
            }
        }
        return name;
    }

    private String getPrice(ArrayList<String> gadget) {
        String price = "";
        String gadgetName = getGadgetName(gadget);
        price = mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(EST_RETAIL_MAX));
        if (price.length() == 1) {
            price = mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(RST_RETAIL_MAX));
        }
        return price;
    }

    private String getYoulaDescription(ArrayList<String> gadget) {
        String text = "";
        if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(RFB)) {
            text += " Восстановленный ";
        }
        text += getYoulaAdName(gadget);
        if (!gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(RFB)) {
//            text += ", гарантия от 1 месяца с момента покупки";
//        } else {
            text += ", официальная гарантия 1 год с момента покупки";
        }
        text += ". В продаже имеется весь модельный ряд iPhone!" +
                " Также выполняем качественный ремонт любой электроники с гарантией результата." +
                " Звоните по всем интересующим вопросам:)";
        return text;
    }

    public String getAdText(ArrayList<String> gadget) {
        String adText = "";
        adText += "Название: " + getYoulaAdName(gadget) + " с гарантией\n";
        adText += "Описание: " + getYoulaDescription(gadget) + "\n";
        adText += "Цена: " + getPrice(gadget) + "\n";
        String imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/" +
                getGadgetPath(gadget, MODEL) + AvitoGadgets.IMG_FILE_NAME + ".jpg";
        adText += "Ссылка на картинку: " + imgLink;
        adText = adText.replace(TOUCH_LOCKED, "(без отпечатка)");
        adText = adText.replace("  ", " ");
        return adText;
    }

    private String getGadgetPath(ArrayList<String> gadget, String lastAttr) {
        String path = "";
        for (int i = mapGadgetAttributeNumber.get(VENDOR); i <= mapGadgetAttributeNumber.get(lastAttr); i++) {
            String attr = gadget.get(i);
            if (i == mapGadgetAttributeNumber.get(FINGER_PRINT)) {
                if (attr.isEmpty()) {
                    attr = "СО";
                } else {
                    attr = "БО";
                }
            }
            path += attr + "/";
            if (i == mapGadgetAttributeNumber.get(MODEL)) {
                path += gadget.get(mapGadgetAttributeNumber.get(COLOR)) + "/";
            }
        }
        return path;
    }

    public void generateFiles() {
        for (int day = 1; day <= 30; day++) {
            for (int gadgetId = (day - 1) * ADS_PER_DAY; gadgetId < day * ADS_PER_DAY; gadgetId++) {
                String dayNum = "" + day;
                if (day < 10) {
                    dayNum = "0" + dayNum;
                }
                Solution.writeText(Solution.getOutputWriter("Output/Youla/Day" + dayNum, (gadgetId % ADS_PER_DAY + 1)
                        + ".txt"), getAdText(gadgets.get(gadgetId)));
            }
        }
    }
}