import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class AvitoGadgets extends Gadgets {
    static int tSize = 11266;
    final static String NAME_BEGIN = "С гарантией ";
    final static String QUALITY = "Качество";
    final static String VENDOR = "Производитель";
    final static String MODEL_LINE = "Модельный ряд";
    final static String MODEL = "Модель";
    final static String SUBMODEL = "Подмодель";
    final static String MEMORY = "Память";
    final static String FINGER_PRINT = "Наличие отпечатка";
    final static String COLOR = "Цвет";
    final static String TOUCH_LOCKED = "Б/О";
    final static String RST = "RST";
    final static String EST = "EST";
    //    final static String[] CITIES = new String[]{"Казань"};
    final static String IMG_FILE_NAME = "img";
    final static int DISTRIBUTION_SIZE = 4;
    final static int ADS_PER_DAY = 19;
    final static int DAYS_OFFSET = 3;
    final static int IPHONES_COUNT = 12;
    final static int MAX_FREQUENCE = 4;
    final static int TOP_COUNT = 5;

    final static String[] gadgetAttributeNames = new String[]{
            QUALITY,
            VENDOR,
            MODEL_LINE,
            MODEL,
            MEMORY,
            FINGER_PRINT,
            SUBMODEL,
            COLOR
    };

    final static String[] iphonesModels = new String[]{
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
            "7 Plus"};
    public static HashMap<String, Integer> mapIphonesModelsNum;
    public static HashMap<String, String> mapIphonesModelDescription;
    final static String[] galaxyModels = new String[]{
            "Grand Prime",
            "Core Prime",
            "Alpha",
            "S3",
            "S3 Mini",
            "S4",
            "S4 Mini",
            "S5",
            "S5 Mini",
            "S6",
            "S6 Edge",
            "S6 Edge Plus",
            "S7",
            "S7 Edge",
            "S8",
            "S8 Plus",
            "A3 (2015)",
            "A3 (2016)",
            "A3 (2017)",
            "A5 (2015)",
            "A5 (2016)",
            "A5 (2017)",
            "A7 (2015)",
            "A7 (2016)",
            "A7 (2017)",
            "J1 (2016)",
            "J2 (2016)",
            "J3 (2016)",
            "J5 (2016)",
            "J7 (2016)",
            "Note 3",
            "Note 4",
            "Note 5"};
    public static HashMap<String, Integer> mapGalaxysModelsNum;
    public static HashMap<String, String> mapGalaxysModelDescription;

    public static ArrayList<ArrayList<String>> iPhoneSubModels;
    public static ArrayList<ArrayList<String>> galaxySubModels;

    HashMap<String, ArrayList<String>> mapGadgetModelSubmodel;
    HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets;
    HashMap<String, ArrayList<String>> mapGadgetModelColor;
    HashMap<String, Integer> mapGadgetModelGadgetCount;
    ArrayList<String> gadgetQuality;
    ArrayList<String> gadgetModels;
    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    static HashSet<String> excludeAds;
    int[][] gadgetsDistribution;

    public AvitoGadgets() {
    }

    public void initializeIPhones() {
        initializeMapGadgetAttributeNumber(gadgetAttributeNames);
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(RST, EST)));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Apple")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("iPhone")));
        gadgetModels = new ArrayList<String>(Arrays.asList(iphonesModels));
        mapIphonesModelsNum = new HashMap<>();
        for (int i = 0; i < iphonesModels.length; i++) {
            mapIphonesModelsNum.put(iphonesModels[i], i);
        }
        mapIphonesModelDescription = new HashMap<>();
        for (int i = 0; i < iphonesModels.length; i++) {
            mapIphonesModelDescription.put(iphonesModels[i], getDescriptionByModel(iphonesModels[i]));
        }
        gadgetAttributesVariants.add(gadgetModels);
        ArrayList<Integer> gadgetCount = new ArrayList<Integer>(Arrays.asList(
                109,//4
                199,//4s
                211,//5
                67,//5c
                761,//5s
                607,//6
                67,//6+
                311,//6s
                71,//6s+
                73,//se
                307,//7
                79));//7+
        iPhoneSubModels = new ArrayList<>();
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1332", "A1349")));//iphone 4
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1387", "A1431")));//iphone 4s
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1428", "A1429", "A1442")));//iphone 5
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1532", "A1456", "A1516", "A1529", "A1507")));//iphone 5c
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1533", "A1457", "A1518", "A1528", "A1530", "A1453")));//iphone 5s
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1549", "A1586", "A1589")));//iphone 6
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1522", "A1524", "A1593")));//iphone 6+
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1633", "A1688", "A1700")));//iphone 6s
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1634", "A1687", "A1699")));//iphone 6s+
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1662", "A1723", "A1724")));//iphone se
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1660", "A1778", "A1779")));//iphone 7
        iPhoneSubModels.add(new ArrayList<String>(Arrays.asList("A1661", "A1784", "A1785")));//iphone 7+
        mapGadgetModelSubmodel = new HashMap<>();
        mapGadgetModelGadgetCount = new HashMap<>();
        for (int i = 0; i < gadgetModels.size(); i++) {
            mapGadgetModelSubmodel.put(gadgetModels.get(i), iPhoneSubModels.get(i));
            mapGadgetModelGadgetCount.put(gadgetModels.get(i), gadgetCount.get(i) / TOP_COUNT);
        }
        ArrayList<ArrayList<String>> colors = new ArrayList<>();
        colors.add(new ArrayList<>(Arrays.asList("Black", "White")));//4
        colors.add(new ArrayList<>(Arrays.asList("Black", "White")));//4s
        colors.add(new ArrayList<>(Arrays.asList("Black", "White")));//5
        colors.add(new ArrayList<>(Arrays.asList("White", "Blue", "Green", "Yellow", "Pink")));//5c
        colors.add(new ArrayList<>(Arrays.asList("Gray", "Silver", "Gold")));//5s
        colors.add(new ArrayList<>(Arrays.asList("Gray", "Silver", "Gold")));//6
        colors.add(new ArrayList<>(Arrays.asList("Gray", "Silver", "Gold")));//6+
        colors.add(new ArrayList<>(Arrays.asList("Gray", "Silver", "Gold", "Rose")));//6s
        colors.add(new ArrayList<>(Arrays.asList("Gray", "Silver", "Gold", "Rose")));//6s+
        colors.add(new ArrayList<>(Arrays.asList("Gray", "Silver", "Gold", "Rose")));//se
        colors.add(new ArrayList<>(Arrays.asList("Black", "Silver", "Jet", "Gold", "Rose", "Red")));//7
        colors.add(new ArrayList<>(Arrays.asList("Black", "Silver", "Jet", "Gold", "Rose", "Red")));//7+
        mapGadgetModelColor = new HashMap<>();
        for (int i = 0; i < gadgetModels.size(); i++) {
            mapGadgetModelColor.put(gadgetModels.get(i), colors.get(i));
        }
        gadgetAttributesVariants.add(new ArrayList<>(Arrays.asList("8Gb",
                "16Gb",
                "32Gb",
                "64Gb",
                "128Gb",
                "256Gb")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("", TOUCH_LOCKED)));
    }

    public static String getLongColor(String shortColor) {
        switch (shortColor) {
            case "Jet":
                return "Jet Black";
            case "Rose":
                return "Rose Gold";
            case "Gray":
                return "Space Gray";
            case "Red":
                return "(PRODUCT)RED";
            default:
                return shortColor;
        }
    }

    public void initializeSamsungs() {
        initializeMapGadgetAttributeNumber(gadgetAttributeNames);
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(RST, EST)));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Samsung")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Galaxy")));
        gadgetModels = new ArrayList<String>(Arrays.asList(galaxyModels));
        mapGalaxysModelsNum = new HashMap<>();
        for (int i = 0; i < galaxyModels.length; i++) {
            mapGalaxysModelsNum.put(galaxyModels[i], i);
        }
        mapGalaxysModelDescription = new HashMap<>();
        for (int i = 0; i < iphonesModels.length; i++) {
            mapGalaxysModelDescription.put(galaxyModels[i], getDescriptionByModel(galaxyModels[i]));
        }
        gadgetAttributesVariants.add(gadgetModels);
        ArrayList<Integer> gadgetCount = new ArrayList<Integer>(Arrays.asList(
                13,//"Grand Prime",
                5,//"Core Prime",
                7,//"Alpha",
                61,//"S3",
                5,//"S3 Mini",
                47,//"S4",
                19,//"S4 Mini",
                43,//"S5",
                11,//"S5 Mini",
                59,//"S6",
                23,//"S6 Edge",
                3,//"S6 Edge Plus",
                83,//"S7",
                41,//"S7 Edge",
                37,//"S8",
                3,//"S8 Plus",
                8,//"A3 (2015)",
                19,//"A3 (2016)",
                2,//"A3 (2017)",
                7,//"A5 (2015)",
                23,//"A5 (2016)",
                8,//"A5 (2017)",
                3,//"A7 (2015)",
                5,//"A7 (2016)",
                5,//"A7 (2017)",
                23,//"J1 (2016)",
                7,//"J2 (2016)",
                21,//"J3 (2016)",
                17,//"J5 (2016)",
                5,//"J7 (2016)",
                13,//"Note 3",
                11,//"Note 4",
                6));//"Note 5"));
        ArrayList<ArrayList<String>> submodels = new ArrayList<>();
        galaxySubModels = new ArrayList<>();
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G531", "SM-G531H")));//"Grand Prime",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G360", "SM-G360H")));//"Core Prime",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G850", "SM-G850F")));//"Alpha",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("GT-I9300", "GT-I9300I")));//"S3",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("GT-I8190", "GT-I8190I")));//"S3 Mini",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("GT-I9500", "GT-I9505")));//"S4",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("GT-I9192", "GT-I9192D")));//"S4 Mini",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G900", "SM-G900F")));//"S5",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G800", "SM-G800F")));//"S5 Mini",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G920", "SM-G920F")));//"S6",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G925", "SM-G925F")));//"S6 Edge",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G928", "SM-G928F")));//"S6 Edge Plus",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G930", "SM-G930F")));//"S7",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G935", "SM-G935F")));//"S7 Edge",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G950", "SM-G950F")));//"S8",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G955", "SM-G955F")));//"S8 Plus",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A300", "SM-A300F")));//"A3 (2015)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A310", "SM-A310F")));//"A3 (2016)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A320", "SM-A320F")));//"A3 (2017)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A500", "SM-A500F")));//"A5 (2015)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A510", "SM-A510F")));//"A5 (2016)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A520", "SM-A520F")));//"A5 (2017)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A700", "SM-A700F")));//"A7 (2015)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A710", "SM-A710F")));//"A7 (2016)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-A720", "SM-A720F")));//"A7 (2017)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-J120", "SM-J120F")));//"J1 (2016)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-G532", "SM-G532F")));//"J2 (2016)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-J320", "SM-J320F")));//"J3 (2016)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-J510", "SM-J510F")));//"J5 (2016)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-J710", "SM-J710F")));//"J7 (2016)",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-N900", "SM-N900F")));//"Note 3",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-N910", "SM-N910F")));//"Note 4",
        galaxySubModels.add(new ArrayList<String>(Arrays.asList("SM-N920", "SM-N920F")));//"Note 5"};
        mapGadgetModelSubmodel = new HashMap<>();
        mapGadgetModelGadgetCount = new HashMap<>();
        for (int i = 0; i < gadgetModels.size(); i++) {
            mapGadgetModelSubmodel.put(gadgetModels.get(i), galaxySubModels.get(i));
            mapGadgetModelGadgetCount.put(gadgetModels.get(i), gadgetCount.get(i) / TOP_COUNT);
        }
        ArrayList<ArrayList<String>> colors = new ArrayList<>();
        colors.add(new ArrayList<String>(Arrays.asList("Gray", "White", "Gold")));//"Grand Prime",
        colors.add(new ArrayList<String>(Arrays.asList("Gray", "White", "Black")));//"Core Prime",
        colors.add(new ArrayList<String>(Arrays.asList("Gray", "White", "Gold")));//"Alpha",
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s3
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s3 mini
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s4
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s4 mini
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//s5
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//s5 mini
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Blue")));//s6
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Green", "Blue")));//s6 edge
        colors.add(new ArrayList<String>(Arrays.asList("White", "Gold", "Blue")));//s6 edge+
        colors.add(new ArrayList<String>(Arrays.asList("Black", "Silver", "Gold")));//s7
        colors.add(new ArrayList<String>(Arrays.asList("Black", "Silver", "Gold")));//s7 edge
        colors.add(new ArrayList<String>(Arrays.asList("Black", "Blue", "Gold")));//s8
        colors.add(new ArrayList<String>(Arrays.asList("Black", "Blue", "Gold")));//s8+
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink", "Blue")));//a3 2015
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//a3 2016
        colors.add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a3 2017
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink", "Blue")));//a5 2015
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//a5 2016
        colors.add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a5 2017
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink", "Blue")));//a7 2015
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//a7 2016
        colors.add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a7 2017
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j1
        colors.add(new ArrayList<String>(Arrays.asList("Black", "Silver", "Gold")));//j2
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j3
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j5
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j7
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Pink")));//note 3
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//note 4
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Silver", "Gold")));//note 5
        mapGadgetModelColor = new HashMap<>();
        for (int i = 0; i < gadgetModels.size(); i++) {
            mapGadgetModelColor.put(gadgetModels.get(i), colors.get(i));
        }
        gadgetAttributesVariants.add(new ArrayList<>(Arrays.asList("8Gb",
                "16Gb",
                "32Gb",
                "64Gb")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("")));
    }

    public void initializeDistribution() {
        Scanner inScanner = Solution.getInputScanner("distribution_avito.txt");
        gadgetsDistribution = new int[IPHONES_COUNT][DISTRIBUTION_SIZE];
        for (int modelId = 0; modelId < IPHONES_COUNT; modelId++) {
            for (int groupId = 0; groupId < DISTRIBUTION_SIZE; groupId++) {
                gadgetsDistribution[modelId][groupId] = inScanner.nextInt();
            }
        }
        inScanner.close();
    }

    public static void initializeExcludeAds() {
        Scanner inScanner = Solution.getInputScanner("exclude_avito_ads.txt");
        excludeAds = new HashSet<>();
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (line.contains(NAME_BEGIN)) {
                excludeAds.add(line);
            }
        }
        inScanner = Solution.getInputScanner("include_avito_ads.txt");
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (line.contains(NAME_BEGIN)) {
                excludeAds.remove(line);
            }
        }
        inScanner.close();
    }

    /*private String transformColor(String model, String color) {
        switch (color) {
            case "Black":
                if (!model.startsWith("4") && !model.equals("5") && !model.contains("7")) {
                    return "Space Gray";
                }
                break;
            case "White":
                if (!model.startsWith("4") && !model.equals("5") && !model.equals("5C")) {
                    return "Silver";
                }
                break;
            case "Pink":
                if (!model.equals("5C")) {
                    return "Rose Gold";
                }
                break;
            case "Red":
                return "(PRODUCT)RED";
        }
        return color;
    }*/

    /*public void printWebsiteCSV(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            if (mapGadgetNamePrices.containsKey(getGadgetName(gadget))) {
                if (gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT)).equals("Б/О")) {
                    return;
                }
                String memory = gadget.get(mapGadgetAttributeNumber.get(MEMORY));
                System.out.println("!!!!" + memory + ";;;;;;;;;;;;1;;;;;;;" + tSize + ";;;;;;;;;;;;;;;");
                tSize++;
                ArrayList<String> prices = mapGadgetNamePrices.get(getGadgetName(gadget));
                String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
                for (String quality : gadgetQuality) {
                    if (quality.isEmpty()) {
                        if (prices.get(Gadgets.mapPriceAttributeNumber.get(Gadgets.RST_RETAIL_AMOLED)).length() == 1) {
                            continue;
                        }
                    }
                    for (String color : mapGadgetModelColor.get(model)) {
                        if (!quality.isEmpty()) {
                            if (prices.get(Gadgets.mapPriceAttributeNumber.get(Gadgets.EST_RETAIL_AMOLED)).length() == 1
                                    || (model.contains("7") && (color.equals("Red") || color.equals("Jet Black")
                                    || color.equals("Pink")))) {
                                continue;
                            }
                        }
//                        gadget.add(color);
                        String name = "Смартфон Apple iPhone " + model + " " + memory + " " +
                                quality + transformColor(model, color);
                        String imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/" +
                                getGadgetPathSite(gadget, color) + IMG_FILE_NAME + ".jpg";
                        System.out.println(name + ";;;RUB;6990;1;0;0;0;;;;1;" + memory + ";;;;;;" +
                                CategoryTree.translit(name) + ";;;;;;;;;;;;;;;" + imgLink);
                    }
                }
            }
        } else {
            int attributeVariantsSize = gadgetAttributesVariants.get(attribute).size();
            for (int attributeVariant = 0; attributeVariant < attributeVariantsSize; attributeVariant++) {
                ArrayList<String> newGadget = new ArrayList<String>(gadget);
                newGadget.add(gadgetAttributesVariants.get(attribute).get(attributeVariant));
                if (attribute == mapGadgetAttributeNumber.get(MODEL)) {
                    System.out.println("!!!iPhone " + newGadget.get(attribute) + ";;;;;;;;;;;;1;;;;;;;" +
                            tSize + ";;;;;;;;;;;;;;;");
                    tSize++;
                }
                printWebsiteCSV(attribute + 1, newGadget);
            }
        }
    }*/

    public void printWebsiteYML() throws IOException {
        BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/Website", "iphones.xml");
        for (ArrayList<String> gadget : gadgets) {
            int price = -1;
            String quality = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
            String subModel = gadget.get(mapGadgetAttributeNumber.get(SUBMODEL));
            String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
            if (quality.equals(EST) && subModel.equals(iPhoneSubModels.get(mapIphonesModelsNum.get(model)).get(0))) {
                price = getPriceISPARK(getGadgetName(gadget), 0);
            }
            if (quality.equals(RST) && subModel.equals(iPhoneSubModels.get(mapIphonesModelsNum.get(model)).get(1))) {
                price = getPriceISPARK(getGadgetName(gadget), 1);
            }
            if (price > 0) {
                String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
                if (model.contains("7") && color.toLowerCase().contains("red")) {
                    price = getIncreasedPrice(price) - 10;
                }
                bufferedWriter.write("<offer>\n" +
                        "<initialCategoryId>2</initialCategoryId>\n" +
                        "<typePrefix>Смартфон</typePrefix>\n" +
                        "<model>iPhone " + model + " " + gadget.get(mapGadgetAttributeNumber.get(MEMORY)) + " " +
                        color + " " + subModel);
                String fingerPrint = gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT));
                if (!fingerPrint.isEmpty()) {
                    bufferedWriter.write(" " + fingerPrint);
                }
                bufferedWriter.write("</model>\n" +
                        "<vendor>Apple</vendor>\n" +
                        "<price>" + price + ".0</price>\n" +
                        "<description>" + mapIphonesModelDescription.get(model) + "</description>\n" +
                        "<picture>" + getImageUrlByModelAndColor(model, color) + "</picture>\n" +
                        "</offer>\n");
            }
        }
        bufferedWriter.flush();
    }

    private String getDescriptionByModel(String model) {
        Scanner inScanner = Solution.getInputScanner("Website/Spec/Apple/iPhone/" + model + ".txt");
        String res = inScanner.nextLine();
        while (inScanner.hasNextLine()) {
            res += "\n" + inScanner.nextLine();
        }
        return res;
    }

    public String getImageUrlByModelAndColor(String model, String color) {
        return "https://raw.githubusercontent.com/bav735/EKZ/master/Apple/iPhone/" +
                model.replace(" ", "") + "/" + getLongColor(color).replace(" ", "") + "/img.jpg";
    }

    private boolean notEnoughModel(ArrayList<String> gadget) {
        return gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("4") ||
                gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("4S") ||
                gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("5");
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            ArrayList<String> prices = mapGadgetNamePrices.get(getGadgetName(gadget));
            if (prices != null) {
                System.out.println(getGadgetName(gadget));
                switch (gadget.get(mapGadgetAttributeNumber.get(QUALITY))) {
                    case EST:
                        if (prices.get(mapPriceAttributeNumber.get(EST_RETAIL_AMOLED)).equals(NO_PRICE)) {
                            return;
                        }
                        break;
                    case RST:
                        if (prices.get(mapPriceAttributeNumber.get(RST_RETAIL_AMOLED)).equals(NO_PRICE) &&
                                !notEnoughModel(gadget)) {
                            return;
                        }
                }
                String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
                for (String color : mapGadgetModelColor.get(model)) {
                    for (String submodel : mapGadgetModelSubmodel.get(model)) {
                        ArrayList<String> newGadget = new ArrayList<>(gadget);
                        newGadget.add(mapGadgetAttributeNumber.get(SUBMODEL), submodel);
                        newGadget.add(mapGadgetAttributeNumber.get(COLOR), color);
                        if (!excludeModel(model, color, newGadget.get(mapGadgetAttributeNumber.get(MEMORY)))) {
                            gadgets.add(newGadget);
                        }
                    }
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

    public boolean excludeModel(String model, String color, String memory) {
        return model.contains("7") &&
                memory.contains("32") && (color.contains("Jet") || color.toLowerCase().contains("red"));
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
        for (int i = 0; i < DISTRIBUTION_SIZE; i++) {
            metaGroups[i] = new int[3 * (DISTRIBUTION_SIZE - i)];
            Arrays.fill(metaGroups[i], i);
        }
        int[] g03 = mergeArrays(metaGroups[0], metaGroups[3], new int[]{0, 0, 1, 0, 0});
        int[] g12 = mergeArrays(metaGroups[1], metaGroups[2], new int[]{0, 1, 0, 1, 0});
        int[] groupsOrder = mergeArrays(g03, g12, new int[]{0, 1});
        for (int groupNum : groupsOrder) {
            ArrayList<ArrayList<String>> gadgetGroup = new ArrayList<>();
            for (int t = 0; t < ADS_PER_DAY; t++) {
                gadgetGroup.add(new ArrayList<String>());
            }
            for (int frequence = MAX_FREQUENCE; frequence > 0; frequence--) {
                for (int modelId = 0; modelId < IPHONES_COUNT; modelId++) {
                    if (gadgetsDistribution[modelId][groupNum] == frequence) {
                        int shift = ADS_PER_DAY;
                        if (frequence > 1) {
                            shift = (ADS_PER_DAY - frequence) / (frequence - 1) + 1;
                        }
                        int i = 0;
                        int r = frequence;
                        while (r > 0 && i < ADS_PER_DAY) {
                            while (!gadgetGroup.get(i).isEmpty()) {
                                i++;
                                if (i >= ADS_PER_DAY) {
                                    break;
                                }
                            }
                            gadgetGroup.set(i, extractGadgetByModel(modelId));
                            i += shift;
                            r--;
                        }
                        if (r == 1) {
                            i = ADS_PER_DAY - 1;
                            while (!gadgetGroup.get(i).isEmpty()) {
                                i--;
                            }
                            gadgetGroup.set(i, extractGadgetByModel(modelId));
                        }
                    }
                }
            }
//            for (ArrayList<String> g : gadgetGroup) {
//                System.out.printYMLCategories(getGadgetName(g));
//            }
//            System.out.println();
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

    public String getAvitoAdName(ArrayList<String> gadget) {
        String name = NAME_BEGIN;
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Samsung")) {
            name += "Новый ";
        }
        name += gadget.get(mapGadgetAttributeNumber.get(QUALITY));
        int lastAttr = mapGadgetAttributeNumber.get(COLOR);
        for (int i = mapGadgetAttributeNumber.get(MODEL_LINE); i <= lastAttr; i++) {
            if (!gadget.get(i).isEmpty()) {
                name += " " + gadget.get(i);
            }
        }
        if (name.length() < 42) {
            name += " Магазин";
        }
        if (name.length() > 50) {
            name = name.substring(0, 50);
        }
        return name;
    }

    private String getGadgetName(ArrayList<String> gadget) {
//        System.out.println(gadget.toString());
        int lastAttr = mapGadgetAttributeNumber.get(FINGER_PRINT);
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Samsung")) {
            lastAttr--;
        }
        int firstAttr = mapGadgetAttributeNumber.get(VENDOR);
        String name = gadget.get(firstAttr);
        for (int i = firstAttr + 1; i <= lastAttr; i++) {
            if (!gadget.get(i).isEmpty()) {
                name += " " + gadget.get(i);
            }
        }
        return name;
    }

    private String getWholesaleOffer(String gadgetName) {
        String price = mapGadgetNamePrices.get(gadgetName).get(PRICES_COUNT - 2);
        if (price.length() == 1) {
            return "";
        }
        return "Опт (от 3 шт) = " + price + "₽<br>";
    }

    private String getCreditOffer(ArrayList<String> gadget) {
        if (getPriceAMOLED(gadget).isEmpty()) {
            return "";
        }
        return "- в кредит на 6 мес = от " + getCreditPrice(gadget) + "₽ в мес<br>";
    }

    private String getOffer(ArrayList<String> gadget) {
        String gadgetName = getGadgetName(gadget);
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        String quality = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
        String offer = quality + " " + gadgetName + " " + color + " = " + getPriceAMOLED(gadget) + " руб, " +
                "ГАРАНТИЯ 1 ГОД ";
        switch (gadget.get(mapGadgetAttributeNumber.get(QUALITY))) {
            case EST:
                offer += "(восстановленный)";
                break;
            case RST:
                if (!notEnoughModel(gadget)) {
                    offer += "(НОВЫЙ, НЕ РЕФ)";
                } else {
                    offer += "(как новый)";
                }
        }
        offer += "\n";
        return offer;
    }

    private int getCreditPrice(ArrayList<String> gadget) {
        int creditPrice = Integer.parseInt(getPriceAMOLED(gadget)) * 112 / 600;
        return (creditPrice / 50 + 1) * 50;
    }

    private int getPriceISPARK(String gadgetName, int submodelNum) {
        if (submodelNum == 0) {
            return Solution.getNumber(Gadgets.mapGadgetNamePrices.get(gadgetName)
                    .get(Gadgets.mapPriceAttributeNumber.get(Gadgets.EST_RETAIL_ISPARK))) - 10;
        } else {
            return Solution.getNumber(Gadgets.mapGadgetNamePrices.get(gadgetName)
                    .get(Gadgets.mapPriceAttributeNumber.get(Gadgets.RST_RETAIL_ISPARK))) - 10;
        }
    }

    private String getPriceAMOLED(ArrayList<String> gadget) {
        String gadgetName = getGadgetName(gadget);
        int price = 0;
        switch (gadget.get(mapGadgetAttributeNumber.get(QUALITY))) {
            case EST:
                price = Integer.parseInt(
                        mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(EST_RETAIL_AMOLED)));
                break;
            case RST:
                if (notEnoughModel(gadget)) {
                    price = Integer.parseInt(
                            mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(EST_RETAIL_AMOLED)));
                    price -= 10;
                } else {
                    price = Integer.parseInt(
                            mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(RST_RETAIL_AMOLED)));
                }
        }
        if (gadget.get(mapGadgetAttributeNumber.get(COLOR)).equals("Red")) {
            price = getIncreasedPrice(price);
        }
        return price + "";
    }

    private int getIncreasedPrice(int price) {
        price = price * 11 / 10;
        price = price / 500 * 500 + 500;
        return price;
    }

    private String formatPrice(String price) {
//        int len = price.length();
//        return price.substring(0, len - 3) + " " + price.substring(len - 3, len);
        return price;
    }

    private String getAdText(ArrayList<String> gadget) {
        String text = "";
        text += "Уважаемый покупатель,\n" +
                "Добро пожаловать в магазин AMOLED\n\n";
        text += "-> АКЦИЯ, аксессуар на выбор в ПОДАРОК за отзыв!\n\n";
        text += "Мы всегда идем навстречу нашим покупателям и дорожим своей репутацией.\n" +
                "Гибкие возможности вашей покупки:\n" +
                "1) ОПЛАТА кредитной/дебетовой КАРТОЙ\n" +
                "2) ТРЕЙД-ИН, ОБМЕН старого телефона \n" +
                "3) ДОСТАВКА ПО РФ через ТК CDEK\n" +
                "4) РАССРОЧКА, банки ОТП/Хоум-Кредит\n" +
                "5) ОПТ, ОПЛАТА ЧЕРЕЗ Р/С (для юрид лиц)\n" +
                "Опыт продаж в сфере цифровой электроники с 2009 года.\n\n";
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Apple")) {
            text += "В нашем ассортименте оригинальный айфон 4/4s/5/5c/5s/6/6s/se/7/plus всех цветов и объемов памяти" +
                    " по лучшей цене в Казани!\n\n";
        } else {
            text += "В нашем ассортименте оригинальный самсунг галакси s3/s4/s5/s6/s7/s8 edge/plus," +
                    " a3/a5/a7 2015/2015/2017, note 3/4/5 всех цветов и объемов памяти" +
                    " по лучшей цене в Казани!\n\n";
        }
        text += getOffer(gadget);
        text += "\n- цена действует при оплате полной стоимости товара наличными\n";
        text += "- выдаем товарный чек и гарантийный талон, заверенные печатью\n";
        text += "- количество товара ограничено, уточняйте актуальное наличие\n";
        text += "- в заводской пленке, без следов эксплуатации, отличный подарок\n\n";
//        text += "Местоположение iSPARK, см. в Яндекс.Картах, 2ГИС, Google Maps \uD83C\uDF0D\n" +
//                "▶ г. Казань, ул. Лушникова, д. 8, оф. 5; время работы (пн-сб): 13.00-21.00 ⏰\n\n";
        text += "-> Звоните: 10.00-20.00, ежедневно\n\n" +
//                "(заказы на нашем сайте можно оставлять круглосуточно)\n\n" +
                "У нас вы сможете наиболее выгодно и дешево купить интересующий вас гаджет или аксессуар!\n" +
//                "С уважением\n" +
                "Магазин AMOLED";
        text = text.replace(TOUCH_LOCKED, "без отпечатка");
        return text;
    }

    private String getDateByCalendar(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "-" +
                convertToTwoDigit(calendar.get(Calendar.MONTH) + 1) + "-" +
                convertToTwoDigit(calendar.get(Calendar.DAY_OF_MONTH));
    }

    private String convertToTwoDigit(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return "" + num;
    }

    public String getXmlAd(int gadgetNum, int xmlDay) {
        ArrayList<String> gadget = gadgets.get(gadgetNum);
        String ad = "\t<Ad>\n";
        Calendar calendarZero = Calendar.getInstance();
        calendarZero.set(Calendar.YEAR, 2017);
        calendarZero.set(Calendar.MONTH, 1);//february
        calendarZero.set(Calendar.DAY_OF_MONTH, 6);
        Calendar calendarCurr = Calendar.getInstance();
//        System.out.println(calendarCurr.get(Calendar.DAY_OF_MONTH) + " " + calendarCurr.get(Calendar.MONTH) + " " + calendarCurr.get(Calendar.YEAR));
        long seconds = (calendarCurr.getTimeInMillis() - calendarZero.getTimeInMillis()) / 1000;
        int dayNum = (int) (seconds / 3600 / 24) + 1;
        int divideDay = (dayNum - 1) % 30 + 1;
//        System.out.println(dayNum + " " + divideDay);
        calendarZero.add(Calendar.DAY_OF_MONTH, dayNum - divideDay - 1 + xmlDay);
        if (divideDay <= DAYS_OFFSET) {
            int tDivideDay = divideDay + 30 - DAYS_OFFSET;
            if (xmlDay >= tDivideDay) {
                calendarZero.add(Calendar.DAY_OF_MONTH, -30);
            }
        } else {
            if (xmlDay < divideDay - DAYS_OFFSET) {
                calendarZero.add(Calendar.DAY_OF_MONTH, 30);
            }
        }
        String dateBegin = getDateByCalendar(calendarZero);
        ad += "\t\t<Id>a" + gadgetNum + "</Id>\n";
        ad += "\t\t<DateBegin>" + dateBegin + "</DateBegin>\n";
        ad += "\t\t<AllowEmail>Да</AllowEmail>\n";
        ad += "\t\t<ManagerName>Оператор-консультант</ManagerName>\n";
        ad += "\t\t<ContactPhone>89991557000</ContactPhone>\n";
//        String city = CITIES[cityId];
//        if (city.equals("Казань")) {
        ad += "\t\t<Region>Татарстан</Region>\n";
//        }
        ad += "\t\t<City>Казань</City>\n";
        ad += "\t\t<Category>Телефоны</Category>\n";
        String goodsType = "";
        if (gadget.get(mapGadgetAttributeNumber.get(AvitoGadgets.VENDOR)).contains("Apple")) {
            goodsType = "iPhone";
        } else {
            goodsType = "Samsung";
        }
        ad += "\t\t<GoodsType>" + goodsType + "</GoodsType>\n";
        ad += "\t\t<Title>" + getAvitoAdName(gadget) + "</Title>\n";
//        ad += "\t\t<Description>" + getNewTextXml(gadgets) + "</Description>\n";
//        int price = Integer.parseInt(getPriceAMOLED(gadgets, cityId));
//        price = price * 95 / 100;
//        price += 100 - price % 100;
//        System.out.println(price);
        ad += "\t\t<Price>" + getPriceAMOLED(gadget) + "</Price>\n";
        ad += "\t\t<Images>\n";
        String imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/" +
                getGadgetPathAvito(gadget, SUBMODEL) + IMG_FILE_NAME + ".jpg";
        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
//        imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/price_iphone.png";
//        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
        ad += "\t\t</Images>\n";
        ad += "\t</Ad>\n";
        return ad;
    }

    private String getAdFileContent(ArrayList<String> gadget) {
        String ad = "";
        ad += "Категория: Телефоны\n";
        String goodsType = "";
        if (gadget.get(mapGadgetAttributeNumber.get(AvitoGadgets.VENDOR)).contains("Apple")) {
            goodsType = "iPhone";
        } else {
            goodsType = "Samsung";
        }
        ad += "Вид товара: " + goodsType + "\n";
        ad += "Название: " + getAvitoAdName(gadget) + "\n";
        ad += "Цена: " + getPriceAMOLED(gadget) + "\n";
        ad += "Текст: " + getAdText(gadget) + "\n";
        return ad;
    }

    private String getGadgetPathAvito(ArrayList<String> gadget, String lastAttr) {
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

    private String getGadgetPathSite(ArrayList<String> gadget, String color) {
        String path = "";
        for (int i = mapGadgetAttributeNumber.get(VENDOR); i <= mapGadgetAttributeNumber.get(MODEL); i++) {
            String attr = gadget.get(i);
            path += attr + "/";
        }
        return (path + color + "/").replace(" ", "");
    }

    public void generateDirsPhotos(ArrayList<String> gadget) {
        File gadgetDirImg = new File(ROOT_DIR + getGadgetPathAvito(gadget, SUBMODEL));
        gadgetDirImg.mkdirs();
        gadgetDirImg = new File(gadgetDirImg, IMG_FILE_NAME + ".jpg");
        File gadgetImg = new File(ROOT_DIR + getGadgetPathAvito(gadget, MODEL) + IMG_FILE_NAME + ".jpg");
        try {
            Files.copy(gadgetImg.toPath(), gadgetDirImg.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void generateXML() {
        String xml = "<Ads formatVersion=\"3\" target=\"Avito.ru\">\n";
        for (int xmlDay = 1; xmlDay <= 30; xmlDay++) {
            for (int gadgetId = (xmlDay - 1) * ADS_PER_DAY; gadgetId < xmlDay * ADS_PER_DAY; gadgetId++) {
                xml += getXmlAd(gadgetId, xmlDay);
            }
        }
        xml += "</Ads>";
        File directory = new File("Output");
        String fileName = "AdsXML.xml";
        File file = new File(directory, fileName.replaceAll("[\\s/]", ""));
        file.getParentFile().mkdirs();
        BufferedWriter outWriter = null;
        try {
            OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(file.getCanonicalPath())));
            outWriter = new BufferedWriter(new OutputStreamWriter(os));
            outWriter.write(xml);
            outWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.copy(new File("C:/EKZ/Output/AdsXML.xml").toPath(), new File(Gadgets.ROOT_DIR + "AdsXML.xml")
                    .toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void generateFolders() throws IOException {
        for (int gadgetId = 0; gadgetId < gadgets.size(); gadgetId++) {
            ArrayList<String> gadget = gadgets.get(gadgetId);
//            if (!excludeAds.contains(getAvitoAdName(gadget))) {
            String name = gadget.get(mapGadgetAttributeNumber.get(QUALITY)).toLowerCase() +
                    gadget.get(mapGadgetAttributeNumber.get(SUBMODEL)) + "_";
            if (!gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT)).isEmpty()) {
                name += "без_отпечатка";
            }
            BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/Avito/" +
                    gadget.get(mapGadgetAttributeNumber.get(VENDOR)) + "/" +
                    gadget.get(mapGadgetAttributeNumber.get(MODEL)) + "/" +
                    gadget.get(mapGadgetAttributeNumber.get(COLOR)) + "/" +
                    gadget.get(mapGadgetAttributeNumber.get(MEMORY)), name + ".txt");
            bufferedWriter.write(getAdFileContent(gadgets.get(gadgetId)));
            bufferedWriter.flush();
//            }
        }
    }

    public String getRobotText(String model, ArrayList<ArrayList<String>> gadgets, int gadgetNumT, int size) {
        String res = "";
        Collections.shuffle(gadgets, new Random(7351));
        int gadgetNum = gadgetNumT + 1;
        for (int i = 1; i <= size; i++) {
            ArrayList<String> gadget = gadgets.get(i - 1);
            res += "\"" + getAvitoAdName(gadget) + "\";\"" +
                    getAdText(gadget) + "\";\"" +
                    getPriceAMOLED(gadget) + "\";\"" +
                    i + ".jpg" + ",price" + gadgetNum + ".jpg\"\n";
            gadgetNum++;
        }
        return res;
    }

    public void generateFilesAvibot() throws IOException {
        mapGadgetModelGadgets = new HashMap<>();
        for (ArrayList<String> gadget : gadgets) {
            String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
            if (!mapGadgetModelGadgets.containsKey(model)) {
                mapGadgetModelGadgets.put(model, new ArrayList<ArrayList<String>>());
            }
            mapGadgetModelGadgets.get(model).add(gadget);
        }
        int gadgetNum = 0;
        for (String model : gadgetModels) {
            int size = Math.min(mapGadgetModelGadgetCount.get(model), mapGadgetModelGadgets.get(model).size());
            BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/AvitoRobot/" + model, "ads.csv");
            bufferedWriter.write(getRobotText(model, mapGadgetModelGadgets.get(model), gadgetNum, size));
            bufferedWriter.flush();
            gadgetNum += size;
//            c += Math.min(mapGadgetModelGadgetCount.get(model), mapGadgetModelGadgets.get(model).baseSize());
        }
    }
}