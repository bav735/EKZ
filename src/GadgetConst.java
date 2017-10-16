import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by A on 05.08.2017.
 */
public class GadgetConst {
    final static String NEW = "NEW";
    final static String REF = "REF";
    final static String CPO = "CPO";

    public final static String[] CITIES = new String[]{"Москва", "Казань"};

    public final static String[] CITIES_IN = new String[]{"Москве", "Казани"};

    public final static String[] CITIES_XML_FILE_END = new String[]{"msk", "tat"};

    public final static ArrayList<String> QUALITIES = new ArrayList<String>(Arrays.asList(
            CPO,
            REF,
            NEW));

    public final static ArrayList<String> QUALITIES_DESCRIPTION = new ArrayList<String>(Arrays.asList(
            "восстановленный Apple",
            "новый, активирован для проверки на отсутствие дефектов",
            "новый, неактивированный"));

    public final static ArrayList<String> PRICES_DESCRIPTION = new ArrayList<String>(Arrays.asList(
            "",
            "",
            "с гарантией магазина",
            "",
            "",
            "",
            "гарантия производителя",
            "",
            "",
            "",
            "",
            "уцененный или б/у",
            "",
            ""));

    public final static ArrayList<String> QUALITIES_AD_NAME = new ArrayList<String>(Arrays.asList(
            "Официальный",
            "Новый",
            "Новый"));

    public final static ArrayList<String> VENDORS = new ArrayList<String>(Arrays.asList(
            "Samsung",
            "Apple",
            "Sony",
            "HTC",
            "Xiaomi",
            "Meizu"));

    public final static ArrayList<String> VENDOR_OFFERS = new ArrayList<String>(Arrays.asList(
            "самсунг галакси",
            "айфоны",
            "сони икспериа",
            "htc",
            "ксиоми",
            "мейзу"));

    public final static ArrayList<String> MODEL_LINES = new ArrayList<String>(Arrays.asList(
            "Galaxy",
            "iPhone",
            "Xperia",
            "One",
            "Redmi",
            "Mi",
            "Pro",
            "M"));

    public final static ArrayList<String> MEMORIES = new ArrayList<String>(Arrays.asList(
            "8Gb",
            "16Gb",
            "32Gb",
            "64Gb",
            "128Gb",
            "256Gb"));

    public final static ArrayList<String> MODELS[] = new ArrayList[MODEL_LINES.size()];

    static {
        MODELS[0] = new ArrayList<String>(Arrays.asList(
                //galaxys
                "Grand Prime",
                "Core Prime",
                "C5",
                "E7",
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
//                "A7 (2015)",
                "A7 (2016)",
                "A7 (2017)",
                "J1 (2016)",
                "J2 Prime",
                "J3 (2016)",
//                "J3 (2017)",
                "J5 (2015)",
                "J5 (2016)",
                "J5 Prime",
                "J5 (2017)",
                "J7 (2015)",
                "J7 (2016)",
                "J7 (2017)",
                "Note 3",
                "Note 4",
                "Note 5"));

        MODELS[1] = new ArrayList<>(Arrays.asList(
                //iphones
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
                "7 Plus"));

        MODELS[2] = new ArrayList<>(Arrays.asList(
                //xperias
                "SP",
                "Z",
                "Z1",
                "Z1 Compact",
                "Z2",
                "Z3",
                "Z3 Compact",
                "Z5 Compact"));

        MODELS[3] = new ArrayList<>(Arrays.asList(
                //htc
                "Mini 2",
                "M7",
                "M8",
                "M9",
                "A9"));

        MODELS[4] = new ArrayList<>(Arrays.asList(
                //xiaomi redmi
                "4A",
                "4X",
                "Note 2",
                "Note 4X",
                "Note 4"));

        MODELS[5] = new ArrayList<>(Arrays.asList(
                //xiaomi mi
                "Mi4",
                "Mi5",
                "Mi5C",
                "Mi5S",
                "Mi5S Plus",
                "Mix",
                "Max 2",
                "Note 2",
                "Mi6"));

        MODELS[6] = new ArrayList<>(Arrays.asList(
                //meizu1
                "6S"));

        MODELS[7] = new ArrayList<>(Arrays.asList(
                //meizu2
                "M3X",
                "M5C",
                "M5",
                "M5 Note",
                "M5S"));
    }

    public final static ArrayList<ArrayList<String>> SUB_MODELS[] = new ArrayList[MODEL_LINES.size()];

    static {
        SUB_MODELS[0] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("SM-G531")));//"Grand Prime
                add(new ArrayList<String>(Arrays.asList("SM-G360")));//"Core Prime
                add(new ArrayList<String>(Arrays.asList("SM-G5000")));//"C5
                add(new ArrayList<String>(Arrays.asList("SM-E700")));//"E7
                add(new ArrayList<String>(Arrays.asList("SM-G850")));//"Alpha
                add(new ArrayList<String>(Arrays.asList("GT-I9300")));//"S3
                add(new ArrayList<String>(Arrays.asList("GT-I8190")));//"S3 Mini
                add(new ArrayList<String>(Arrays.asList("GT-I9505")));//"S4
                add(new ArrayList<String>(Arrays.asList("GT-I9192")));//"S4 Mini
                add(new ArrayList<String>(Arrays.asList("SM-G900")));//"S5
                add(new ArrayList<String>(Arrays.asList("SM-G800")));//"S5 Mini
                add(new ArrayList<String>(Arrays.asList("SM-G920")));//"S6
                add(new ArrayList<String>(Arrays.asList("SM-G925")));//"S6 Edge
                add(new ArrayList<String>(Arrays.asList("SM-G928")));//"S6 Edge Plus
                add(new ArrayList<String>(Arrays.asList("SM-G930")));//"S7
                add(new ArrayList<String>(Arrays.asList("SM-G935")));//"S7 Edge
                add(new ArrayList<String>(Arrays.asList("SM-G950")));//"S8
                add(new ArrayList<String>(Arrays.asList("SM-G955")));//"S8 Plus
                add(new ArrayList<String>(Arrays.asList("SM-A300")));//"A3 (2015)
                add(new ArrayList<String>(Arrays.asList("SM-A310")));//"A3 (2016)
                add(new ArrayList<String>(Arrays.asList("SM-A320")));//"A3 (2017)
                add(new ArrayList<String>(Arrays.asList("SM-A500")));//"A5 (2015)
                add(new ArrayList<String>(Arrays.asList("SM-A510")));//"A5 (2016)
                add(new ArrayList<String>(Arrays.asList("SM-A520")));//"A5 (2017)
//                add(new ArrayList<String>(Arrays.asList("SM-A700")));//"A7 (2015)
                add(new ArrayList<String>(Arrays.asList("SM-A710")));//"A7 (2016)
                add(new ArrayList<String>(Arrays.asList("SM-A720")));//"A7 (2017)
                add(new ArrayList<String>(Arrays.asList("SM-J120")));//"J1 (2016)
                add(new ArrayList<String>(Arrays.asList("SM-G532")));//"J2 Prime
                add(new ArrayList<String>(Arrays.asList("SM-J320")));//"J3 (2016)
//                add(new ArrayList<String>(Arrays.asList("SM-J330")));//"J3 (2017)
                add(new ArrayList<String>(Arrays.asList("SM-J500")));//"J5 (2015)
                add(new ArrayList<String>(Arrays.asList("SM-J510")));//"J5 (2016)
                add(new ArrayList<String>(Arrays.asList("SM-J570")));//"J5 Prime
                add(new ArrayList<String>(Arrays.asList("SM-J530")));//"J5 (2017)
                add(new ArrayList<String>(Arrays.asList("SM-J700")));//"J7 (2015)
                add(new ArrayList<String>(Arrays.asList("SM-J710")));//"J7 (2016)
                add(new ArrayList<String>(Arrays.asList("SM-J730")));//"J7 (2017)
                add(new ArrayList<String>(Arrays.asList("SM-N900")));//"Note 3
                add(new ArrayList<String>(Arrays.asList("SM-N910")));//"Note 4
                add(new ArrayList<String>(Arrays.asList("SM-N920")));//"Note 5
            }
        };

        SUB_MODELS[1] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("A1332", "A1349")));//iphone 4
                add(new ArrayList<String>(Arrays.asList("A1387", "A1431")));//iphone 4s
                add(new ArrayList<String>(Arrays.asList("A1428", "A1429", "A1442")));//iphone 5
                add(new ArrayList<String>(Arrays.asList("A1507", "A1532", "A1516", "A1529", "A1456")));//iphone 5c
                add(new ArrayList<String>(Arrays.asList("A1457", "A1533", "A1518", "A1528", "A1530", "A1453")));//iphone 5s
                add(new ArrayList<String>(Arrays.asList("A1586", "A1549", "A1589")));//iphone 6
                add(new ArrayList<String>(Arrays.asList("A1524", "A1522", "A1593")));//iphone 6+
                add(new ArrayList<String>(Arrays.asList("A1688", "A1633", "A1700")));//iphone 6s
                add(new ArrayList<String>(Arrays.asList("A1687", "A1634", "A1699")));//iphone 6s+
                add(new ArrayList<String>(Arrays.asList("A1723", "A1662", "A1724")));//iphone se
                add(new ArrayList<String>(Arrays.asList("A1778", "A1660", "A1779")));//iphone 7
                add(new ArrayList<String>(Arrays.asList("A1784", "A1661", "A1785")));//iphone 7+
            }
        };

        SUB_MODELS[2] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("C5302", "C5303", "C5306")));//"SP"
                add(new ArrayList<String>(Arrays.asList("C6602", "C6603", "C6616")));// "Z",
                add(new ArrayList<String>(Arrays.asList("C6902", "C6903", "C6906", "C6916")));//"Z1",
                add(new ArrayList<String>(Arrays.asList("D5502", "D5503")));//"Z1 Compact",
                add(new ArrayList<String>(Arrays.asList("D6502", "D6503")));//"Z2",
                add(new ArrayList<String>(Arrays.asList("D6633", "D6643", "D6708")));//"Z3",
                add(new ArrayList<String>(Arrays.asList("D5803", "D5833")));//"Z3 Compact",
                add(new ArrayList<String>(Arrays.asList("E5803", "E5823")));//"Z5 Compact"
            }
        };

        for (int i = 3; i < MODEL_LINES.size(); i++) {
            SUB_MODELS[i] = new ArrayList<>();
            for (int j = 0; j < MODELS[i].size(); j++) {
                SUB_MODELS[i].add(new ArrayList<String>(Arrays.asList("")));
            }
        }
    }

    private final static String[] SAMSUNG_SUB_MODEL_DESCRIPTION = new String[]{
            "европейского рынка (прошивка Europe)",
            "американского рынка (прошивка USA)",
            "канадского рынка (прошивка Canada)",
            "китайского рынка (прошивка China)",
            "корейского рынка (прошивка Korea)"};
    private final static String[] SUB_MODEL_ENDINGS = new String[]{"F", "V", "W", "0", "K"};

    public static final HashMap<String, String> MAP_SAMSUNG_SUB_MODEL_ENDING_DESCRIPTION;

    static {
        MAP_SAMSUNG_SUB_MODEL_ENDING_DESCRIPTION = new HashMap<>();
        for (int i = 0; i < SUB_MODEL_ENDINGS.length; i++) {
            MAP_SAMSUNG_SUB_MODEL_ENDING_DESCRIPTION.put(SUB_MODEL_ENDINGS[i], SAMSUNG_SUB_MODEL_DESCRIPTION[i]);
        }
    }

    static {
        for (int i = 0; i < MODELS[0].size(); i++) {
            ArrayList<String> subModelList = SUB_MODELS[0].get(i);
            String submodel = subModelList.get(0);
            subModelList.remove(0);
            for (String modelEnding : SUB_MODEL_ENDINGS) {
                String newSubModel = submodel + modelEnding;
                subModelList.add(newSubModel);
            }
        }
    }

    public final static ArrayList<ArrayList<String>> COLORS[] = new ArrayList[MODEL_LINES.size()];

    static {
        COLORS[0] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("Gray", "White", "Gold")));//"Grand Prime
                add(new ArrayList<String>(Arrays.asList("Gray", "White")));//"Core Prime
                add(new ArrayList<String>(Arrays.asList("Dark Gray", "Gold", "Pink Gold")));//"C5
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//"E7
                add(new ArrayList<String>(Arrays.asList("Gray", "White", "Gold", "Silver")));//"Alpha
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue", "Red")));//s3
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s3 mini
                add(new ArrayList<String>(Arrays.asList("White Frost", "Black Mist", "Arctic Blue", "Black Edition")));//s4
                add(new ArrayList<String>(Arrays.asList("Black", "White")));//s4 mini
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Blue")));//s5
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Blue")));//s5 mini
                add(new ArrayList<String>(Arrays.asList("Black Sapphire", "White Pearl", "Gold Platinum", "Blue Topaz")));//s6
                add(new ArrayList<String>(Arrays.asList("Black Sapphire", "White Pearl", "Gold Platinum", "Green Emerald")));//s6 edge
                add(new ArrayList<String>(Arrays.asList("White Pearl", "Gold Platinum", "Black Sapphire")));//s6 edge+
                add(new ArrayList<String>(Arrays.asList("Black Onyx", "Silver Titanium", "Gold Platinum", "White Pearl", "Pink Gold")));//s7
                add(new ArrayList<String>(Arrays.asList("Black Onyx", "Silver Titanium", "Gold Platinum", "Smoke Sapphire", "White Pearl", "Pink Gold")));//s7 edge
                add(new ArrayList<String>(Arrays.asList("Midnight Black", "Orchid Gray", "Arctic Silver", "Coral Blue", "Maple Gold")));//s8
                add(new ArrayList<String>(Arrays.asList("Midnight Black", "Orchid Gray", "Arctic Silver", "Coral Blue", "Maple Gold", "Rose Pink")));//s8+
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a3 2015
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a3 2016
                add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a3 2017
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Silver")));//a5 2015
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//a5 2016
                add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a5 2017
//                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a7 2015
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a7 2016
                add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a7 2017
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j1
                add(new ArrayList<String>(Arrays.asList("Black", "Silver", "Gold", "Pink")));//j2 prime
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j3 2016
//                add(new ArrayList<String>(Arrays.asList("Black", "Blue", "Gold", "Pink")));//j3 2017
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j5 2015
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j5 2016
                add(new ArrayList<String>(Arrays.asList("Black", "Gold")));//j5 prime
                add(new ArrayList<String>(Arrays.asList("Black", "Blue", "Gold", "Pink")));//j5 2017
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j7 2015
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j7 2016
                add(new ArrayList<String>(Arrays.asList("Black", "Blue", "Gold", "Pink")));//j7 2017
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//note 3
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//note 4
                add(new ArrayList<String>(Arrays.asList("Black Sapphire", "White Pearl", "Gold Platinum", "Silver Titanium")));//note 5
            }
        };

        COLORS[1] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<>(Arrays.asList("Black", "White")));//4
                add(new ArrayList<>(Arrays.asList("Black", "White")));//4s
                add(new ArrayList<>(Arrays.asList("Black", "White")));//5
                add(new ArrayList<>(Arrays.asList("White", "Blue", "Green", "Yellow", "Pink")));//5c
                add(new ArrayList<>(Arrays.asList("Space Gray", "Silver", "Gold")));//5s
                add(new ArrayList<>(Arrays.asList("Space Gray", "Silver", "Gold")));//6
                add(new ArrayList<>(Arrays.asList("Space Gray", "Silver", "Gold")));//6+
                add(new ArrayList<>(Arrays.asList("Space Gray", "Silver", "Gold", "Rose Gold")));//6s
                add(new ArrayList<>(Arrays.asList("Space Gray", "Silver", "Gold", "Rose Gold")));//6s+
                add(new ArrayList<>(Arrays.asList("Space Gray", "Silver", "Gold", "Rose Gold")));//se
                add(new ArrayList<>(Arrays.asList("Black", "Silver", "Jet Black", "Gold", "Rose Gold", "(PRODUCT)RED")));//7
                add(new ArrayList<>(Arrays.asList("Black", "Silver", "Jet Black", "Gold", "Rose Gold", "(PRODUCT)RED")));//7+
            }
        };

        COLORS[2] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Red")));//"SP"
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Purple")));// "Z",
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Purple")));//"Z1",
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Pink", "Lime")));//"Z1 Compact",
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Purple")));//"Z2",
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Copper", "Silver Green", "Purple")));//"Z3",
                add(new ArrayList<String>(Arrays.asList("Black", "White", "Green", "Orange")));//"Z3 Compact"
                add(new ArrayList<String>(Arrays.asList("White", "Graphite Black", "Yellow", "Coral")));//"Z5 Compact"
            }
        };

        COLORS[3] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("Gold", "Gray", "Silver")));//"Mini 2",
                add(new ArrayList<String>(Arrays.asList("Silver", "Black")));//"M7",
                add(new ArrayList<String>(Arrays.asList("Gold", "Gray", "Silver")));//"M8",
                add(new ArrayList<String>(Arrays.asList("Silver Gold", "Gray")));//"M9",
                add(new ArrayList<String>(Arrays.asList("White", "Carbon Grey")));//"A9"));
            }
        };

        COLORS[4] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("Gold", "Dark Gray", "Rose Gold")));//"4A",
                add(new ArrayList<String>(Arrays.asList("Gold", "Black", "Pink")));//  "4X",
                add(new ArrayList<String>(Arrays.asList("White", "Blue", "Yellow", "Pink", "Mint Green")));//"Note 2",
                add(new ArrayList<String>(Arrays.asList("Gold", "Gray", "Black", "Pink")));// "Note 4X",
                add(new ArrayList<String>(Arrays.asList("Gold", "Gray", "Black", "Lake Blue")));//"Note 4"));
            }
        };

        COLORS[5] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("White", "Black")));//"Mi4",
                add(new ArrayList<String>(Arrays.asList("Gold", "Black", "White", "Ceramic")));//"Mi5",
                add(new ArrayList<String>(Arrays.asList("Gold", "Black", "Rose Gold")));//"Mi5C",
                add(new ArrayList<String>(Arrays.asList("Silver", "Gray", "Gold", "Rose Gold")));//"Mi5S",
                add(new ArrayList<String>(Arrays.asList("Silver", "Gray", "Gold", "Rose Gold")));//"Mi5S Plus",
                add(new ArrayList<String>(Arrays.asList("Black", "White")));//"Mix",
                add(new ArrayList<String>(Arrays.asList("Gold", "Matte Black")));//"Max 2",
                add(new ArrayList<String>(Arrays.asList("Gold", "Black", "Silver")));//"Note 2",
                add(new ArrayList<String>(Arrays.asList("Blue", "Black", "White", "Ceramic Black")));//"Mi6",
            }
        };

        COLORS[6] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("Gold", "Gray", "Silver", "Rose Gold")));//"6S"));
            }
        };

        COLORS[7] = new ArrayList<ArrayList<String>>() {
            {
                add(new ArrayList<String>(Arrays.asList("Gold", "Blue", "Black", "Silver")));//"M3X",
                add(new ArrayList<String>(Arrays.asList("Gold", "Black", "Blue", "Pink", "Red")));//"M5C",
                add(new ArrayList<String>(Arrays.asList("Mint Green", "Glacier White", "Champanage Gold", "Sapphire Blue", "Matte Black")));//"M5",
                add(new ArrayList<String>(Arrays.asList("Gold", "Gray", "Silver", "Blue")));//"M5 Note",
                add(new ArrayList<String>(Arrays.asList("Champagne Gold", "Stay Gray", "Moonlight Silver", "Rose Gold")));//"M5S"));
            }
        };
    }


    public static final HashMap<String, ArrayList<String>> MAP_MODEL_SUBMODEL[] = new HashMap[MODEL_LINES.size()];

    static {
        for (int i = 0; i < MODEL_LINES.size(); i++) {
            MAP_MODEL_SUBMODEL[i] = new HashMap<>();
            for (int j = 0; j < MODELS[i].size(); j++) {
                MAP_MODEL_SUBMODEL[i].put(MODELS[i].get(j), SUB_MODELS[i].get(j));
            }
        }
    }

    public static final HashMap<String, ArrayList<String>> MAP_MODEL_COLOR[] = new HashMap[MODEL_LINES.size()];
    ;

    static {
        for (int i = 0; i < MODEL_LINES.size(); i++) {
            MAP_MODEL_COLOR[i] = new HashMap<>();
            for (int j = 0; j < MODELS[i].size(); j++) {
                MAP_MODEL_COLOR[i].put(MODELS[i].get(j), COLORS[i].get(j));
            }
        }
    }

    public static final HashMap<String, String> MAP_VENDOR_OFFER;

    static {
        MAP_VENDOR_OFFER = new HashMap<>();
        for (int i = 0; i < VENDORS.size(); i++) {
            MAP_VENDOR_OFFER.put(VENDORS.get(i), VENDOR_OFFERS.get(i));
        }
    }

    public static final HashMap<String, String> MAP_QUALITY_DESCRIPTION;

    static {
        MAP_QUALITY_DESCRIPTION = new HashMap<>();
        for (int i = 0; i < QUALITIES.size(); i++) {
            MAP_QUALITY_DESCRIPTION.put(QUALITIES.get(i), QUALITIES_DESCRIPTION.get(i));
        }
    }

    public static final HashMap<String, String> MAP_PRICES_DESCRIPTION;

    static {
        MAP_PRICES_DESCRIPTION = new HashMap<>();
        for (int i = 0; i < Gadgets.newPriceAttributeNames.length; i++) {
            MAP_PRICES_DESCRIPTION.put(Gadgets.newPriceAttributeNames[i], PRICES_DESCRIPTION.get(i));
        }
    }

    public static final HashMap<String, String> MAP_QUALITY_AD_NAME;

    static {
        MAP_QUALITY_AD_NAME = new HashMap<>();
        for (int i = 0; i < QUALITIES.size(); i++) {
            MAP_QUALITY_AD_NAME.put(QUALITIES.get(i), QUALITIES_AD_NAME.get(i));
        }
    }

    public final static HashMap<String, int[]> MAP_METAMODEL_ADS_PER_MONTH = new HashMap<>();

    static {
        Scanner inScanner = Solution.getInputScanner("ads_per_month.txt");
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            String[] split = line.split("[|]");
            int[] adsPerMonth = new int[split.length - 1];
            for (int i = 0; i < adsPerMonth.length; i++) {
                adsPerMonth[i] = Solution.getNumber(split[i + 1]);
            }
            MAP_METAMODEL_ADS_PER_MONTH.put(split[0], adsPerMonth);
        }
        inScanner.close();
    }
}