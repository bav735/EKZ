import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by A on 05.08.2017.
 */
public class GadgetConst {
    public final static String[] CITIES = new String[]{"Казань"};

    public final static String[] CITIES_IN = new String[]{"Казани"};

    public final static ArrayList<Integer> MODEL_COUNT = new ArrayList<Integer>(Arrays.asList(
            36,
            59,
            8));

    public final static ArrayList<String> VENDORS = new ArrayList<String>(Arrays.asList(
            "Samsung",
            "Apple",
            "Sony"));

    public final static ArrayList<String> VENDOR_OFFERS = new ArrayList<String>(Arrays.asList(
            "самсунг галакси",
            "айфоны",
            "сони икспериа"));

    public final static ArrayList<String> MODEL_LINES = new ArrayList<String>(Arrays.asList(
            "iPhone",
            "Galaxy",
            "Xperia"));

    public final static ArrayList<String> MEMORIES = new ArrayList<String>(Arrays.asList(
            "0Gb",
            "8Gb",
            "16Gb",
            "32Gb",
            "64Gb",
            "128Gb",
            "256Gb"));

    public final static ArrayList<String> MODELS = new ArrayList<String>(Arrays.asList(
            //galaxys
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
            "J3 (2017)",
            "J5 (2016)",
            "J5 (2017)",
            "J7 (2016)",
            "J7 (2017)",
            "Note 3",
            "Note 4",
            "Note 5",
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
            "7 Plus",
            //xperias
            "SP",
            "Z",
            "Z1",
            "Z1 Compact",
            "Z2",
            "Z3",
            "Z3 Compact",
            "Z5 Compact"));


    public final static ArrayList<Integer>[] GADGET_PER_MONTH_COUNT = new ArrayList[CITIES.length];

    static {
        GADGET_PER_MONTH_COUNT[0] = new ArrayList<Integer>(Arrays.asList(
                2,//"Grand Prime",
                1,//"Core Prime",
                1,//"Alpha",
                7,//"S3",
                1,//"S3 Mini",
                7,//"S4",
                2,//"S4 Mini",
                5,//"S5",
                1,//"S5 Mini",
                7,//"S6",
                4,//"S6 Edge",
                1,//"S6 Edge Plus",
                16,//"S7",
                7,//"S7 Edge",
                9,//"S8",
                1,//"S8 Plus",
                1,//"A3 (2015)",
                2,//"A3 (2016)",
                2,//"A3 (2017)",
                1,//"A5 (2015)",
                6,//"A5 (2016)",
                2,//"A5 (2017)",
                1,//"A7 (2015)",
                1,//"A7 (2016)",
                1,//"A7 (2017)",
                5,//"J1 (2016)",
                1,//"J2 (2016)",
                5,//"J3 (2016)",
                1,//"J3 (2017)",
                1,//"J5 (2016)",
                1,//"J5 (2017)",
                1,//"J7 (2016)",
                1,//"J7 (2017)",
                3,//"Note 3",
                1,//"Note 4",
                2,//"Note 5"));

                12,//4
                16,//4s
                18,//5
                9,//5c
                108,//5s
                54,//6 //87
                43,//6+
                36,//6s //45
                18,//6s+
                10,//se
                48,//7
                22,//7+

                1,//"SP",
                1,//"Z",
                2,//"Z1",
                1,//"Z1 Compact",
                2,//"Z2",
                3,//"Z3",
                4,//"Z3 Compact",
                1//"Z5 Compact"
        ));

        /*GADGET_PER_MONTH_COUNT[1] = new ArrayList<Integer>(Arrays.asList(
                1,//"Grand Prime",
                1,//"Core Prime",
                2,//"Alpha",
                23,//"S3",
                1,//"S3 Mini",
                19,//"S4",
                10,//"S4 Mini",
                20,//"S5",
                4,//"S5 Mini",
                26,//"S6",
                10,//"S6 Edge",
                1,//"S6 Edge Plus",
                18,//"S7",
                6,//"S7 Edge",
                1,//"S8",
                1,//"S8 Plus",
                1,//"A3 (2015)",
                6,//"A3 (2016)",
                1,//"A3 (2017)",
                1,//"A5 (2015)",
                12,//"A5 (2016)",
                1,//"A5 (2017)",
                1,//"A7 (2015)",
                1,//"A7 (2016)",
                1,//"A7 (2017)",
                12,//"J1 (2016)",
                1,//"J2 (2016)",
                21,//"J3 (2016)",
                1,//"J3 (2017)",
                1,//"J5 (2016)",
                1,//"J5 (2017)",
                1,//"J7 (2016)",
                1,//"J7 (2017)",
                1,//"Note 3",
                1,//"Note 4",
                1,//"Note 5"));

                30,//4
                1,//4s
                6,//5
                13,//5c
                154,//5s
                1,//6
                129,//6+
                72,//6s
                7,//6s+
                23,//se
                72,//7
                6,//7+

                1,//"SP",
                1,//"Z",
                12,//"Z1",
                1,//"Z1 Compact",
                1,//"Z2",
                6,//"Z3",
                6,//"Z3 Compact",
                1//"Z5 Compact"
        ));

        GADGET_PER_MONTH_COUNT[2] = new ArrayList<Integer>(Arrays.asList(
                1,//"Grand Prime",
                1,//"Core Prime",
                1,//"Alpha",
                6,//"S3",
                1,//"S3 Mini",
                1,//"S4",
                1,//"S4 Mini",
                1,//"S5",
                1,//"S5 Mini",
                6,//"S6",
                1,//"S6 Edge",
                1,//"S6 Edge Plus",
                6,//"S7",
                1,//"S7 Edge",
                1,//"S8",
                1,//"S8 Plus",
                1,//"A3 (2015)",
                1,//"A3 (2016)",
                1,//"A3 (2017)",
                1,//"A5 (2015)",
                1,//"A5 (2016)",
                1,//"A5 (2017)",
                1,//"A7 (2015)",
                1,//"A7 (2016)",
                1,//"A7 (2017)",
                1,//"J1 (2016)",
                1,//"J2 (2016)",
                1,//"J3 (2016)",
                1,//"J3 (2017)",
                1,//"J5 (2016)",
                1,//"J5 (2017)",
                1,//"J7 (2016)",
                1,//"J7 (2017)",
                1,//"Note 3",
                1,//"Note 4",
                1,//"Note 5"));

                1,//4
                1,//4s
                1,//5
                6,//5c
                63,//5s
                1,//6
                47,//6+
                15,//6s
                1,//6s+
                6,//se
                20,//7
                1,//7+

                1,//"SP",
                1,//"Z",
                1,//"Z1",
                1,//"Z1 Compact",
                1,//"Z2",
                1,//"Z3",
                1,//"Z3 Compact",
                1//"Z5 Compact"
        ));

        GADGET_PER_MONTH_COUNT[3] = new ArrayList<Integer>(Arrays.asList(
                1,//"Grand Prime",
                1,//"Core Prime",
                1,//"Alpha",
                6,//"S3",
                1,//"S3 Mini",
                1,//"S4",
                1,//"S4 Mini",
                1,//"S5",
                1,//"S5 Mini",
                6,//"S6",
                1,//"S6 Edge",
                1,//"S6 Edge Plus",
                6,//"S7",
                1,//"S7 Edge",
                1,//"S8",
                1,//"S8 Plus",
                1,//"A3 (2015)",
                1,//"A3 (2016)",
                1,//"A3 (2017)",
                1,//"A5 (2015)",
                1,//"A5 (2016)",
                1,//"A5 (2017)",
                1,//"A7 (2015)",
                1,//"A7 (2016)",
                1,//"A7 (2017)",
                1,//"J1 (2016)",
                1,//"J2 (2016)",
                1,//"J3 (2016)",
                1,//"J3 (2017)",
                1,//"J5 (2016)",
                1,//"J5 (2017)",
                1,//"J7 (2016)",
                1,//"J7 (2017)",
                1,//"Note 3",
                1,//"Note 4",
                1,//"Note 5"));

                1,//4
                1,//4s
                1,//5
                6,//5c
                47,//5s
                1,//6
                40,//6+
                19,//6s
                1,//6s+
                1,//se
                9,//7
                1,//7+

                1,//"SP",
                1,//"Z",
                1,//"Z1",
                1,//"Z1 Compact",
                1,//"Z2",
                1,//"Z3",
                1,//"Z3 Compact",
                1//"Z5 Compact"
        ));*/
    }

    public final static ArrayList<ArrayList<String>> SUB_MODELS = new ArrayList<ArrayList<String>>() {
        {
            add(new ArrayList<String>(Arrays.asList("SM-G531")));//"Grand Prime
            add(new ArrayList<String>(Arrays.asList("SM-G360")));//"Core Prime
            add(new ArrayList<String>(Arrays.asList("SM-G850")));//"Alpha
            add(new ArrayList<String>(Arrays.asList("GT-I9300")));//"S3
            add(new ArrayList<String>(Arrays.asList("GT-I8190")));//"S3 Mini
            add(new ArrayList<String>(Arrays.asList("GT-I9500")));//"S4
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
            add(new ArrayList<String>(Arrays.asList("SM-A700")));//"A7 (2015)
            add(new ArrayList<String>(Arrays.asList("SM-A710")));//"A7 (2016)
            add(new ArrayList<String>(Arrays.asList("SM-A720")));//"A7 (2017)
            add(new ArrayList<String>(Arrays.asList("SM-J120")));//"J1 (2016)
            add(new ArrayList<String>(Arrays.asList("SM-G532")));//"J2 (2016)
            add(new ArrayList<String>(Arrays.asList("SM-J320")));//"J3 (2016)
            add(new ArrayList<String>(Arrays.asList("SM-J330")));//"J3 (2017)
            add(new ArrayList<String>(Arrays.asList("SM-J510")));//"J5 (2016)
            add(new ArrayList<String>(Arrays.asList("SM-J530")));//"J5 (2017)
            add(new ArrayList<String>(Arrays.asList("SM-J710")));//"J7 (2016)
            add(new ArrayList<String>(Arrays.asList("SM-J730")));//"J7 (2017)
            add(new ArrayList<String>(Arrays.asList("SM-N900")));//"Note 3
            add(new ArrayList<String>(Arrays.asList("SM-N910")));//"Note 4
            add(new ArrayList<String>(Arrays.asList("SM-N920")));//"Note 5

            add(new ArrayList<String>(Arrays.asList("A1332", "A1349")));//iphone 4
            add(new ArrayList<String>(Arrays.asList("A1387", "A1431")));//iphone 4s
            add(new ArrayList<String>(Arrays.asList("A1428", "A1429", "A1442")));//iphone 5
            add(new ArrayList<String>(Arrays.asList("A1532", "A1456", "A1516", "A1529", "A1507")));//iphone 5c
            add(new ArrayList<String>(Arrays.asList("A1533", "A1457", "A1518", "A1528", "A1530", "A1453")));//iphone 5s
            add(new ArrayList<String>(Arrays.asList("A1549", "A1586", "A1589")));//iphone 6
            add(new ArrayList<String>(Arrays.asList("A1522", "A1524", "A1593")));//iphone 6+
            add(new ArrayList<String>(Arrays.asList("A1633", "A1688", "A1700")));//iphone 6s
            add(new ArrayList<String>(Arrays.asList("A1634", "A1687", "A1699")));//iphone 6s+
            add(new ArrayList<String>(Arrays.asList("A1662", "A1723", "A1724")));//iphone se
            add(new ArrayList<String>(Arrays.asList("A1660", "A1778", "A1779")));//iphone 7
            add(new ArrayList<String>(Arrays.asList("A1661", "A1784", "A1785")));//iphone 7+

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
    private final static String[] SUB_MODEL_ENDING_DESCRIPTION = new String[]{
            "Neverlocked",
            "Unlocked (US Cellular)",
            "Unlocked (Verizon)",
            "Unlocked (AT&T)",
            "Unlocked (T-Mobile)",
            "Unlocked (Sprint)",};
    private final static String[] SUB_MODEL_ENDINGS = new String[]{"F", "R", "V", "A", "T", "P"};

    static {
        for (int i = 0; i < MODEL_COUNT.get(0); i++) {
            ArrayList<String> subModelList = SUB_MODELS.get(i);
            String submodel = subModelList.get(0);
            subModelList.remove(0);
            for (String modelEnding : SUB_MODEL_ENDINGS) {
                String newSubModel = submodel + modelEnding;
                subModelList.add(newSubModel);
            }
            System.out.println("size=" + SUB_MODELS.get(i).size());
        }
    }

    public final static ArrayList<ArrayList<String>> COLORS = new ArrayList<ArrayList<String>>() {
        {
            add(new ArrayList<String>(Arrays.asList("Gray", "White")));//"Grand Prime
            add(new ArrayList<String>(Arrays.asList("Gray", "White")));//"Core Prime
            add(new ArrayList<String>(Arrays.asList("Gray", "White", "Gold", "Silver")));//"Alpha
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s3
            add(new ArrayList<String>(Arrays.asList("Black", "White")));//s3 mini
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s4
            add(new ArrayList<String>(Arrays.asList("Black", "White")));//s4 mini
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Blue")));//s5
            add(new ArrayList<String>(Arrays.asList("Black", "White")));//s5 mini
            add(new ArrayList<String>(Arrays.asList("Black Sapphire", "White Pearl", "Gold Platinum", "Blue Topaz")));//s6
            add(new ArrayList<String>(Arrays.asList("Black Sapphire", "White Pearl", "Gold Platinum", "Green Emerald")));//s6 edge
            add(new ArrayList<String>(Arrays.asList("White Pearl", "Gold Platinum", "Black Sapphire")));//s6 edge+
            add(new ArrayList<String>(Arrays.asList("Black Onyx", "Silver Titanium", "Gold Platinum")));//s7
            add(new ArrayList<String>(Arrays.asList("Black Onyx", "Silver Titanium", "Gold Platinum", "Smoke Sapphire")));//s7 edge
            add(new ArrayList<String>(Arrays.asList("Midnight Black", "Orchid Gray", "Maple Gold")));//s8
            add(new ArrayList<String>(Arrays.asList("Midnight Black", "Orchid Gray", "Maple Gold")));//s8+
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a3 2015
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a3 2016
            add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a3 2017
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Silver")));//a5 2015
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a5 2016
            add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a5 2017
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a7 2015
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//a7 2016
            add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a7 2017
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j1
            add(new ArrayList<String>(Arrays.asList("Black", "Silver", "Gold")));//j2
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j3 2016
            add(new ArrayList<String>(Arrays.asList("Black", "Blue", "Gold", "Pink")));//j3 2017
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j5 2016
            add(new ArrayList<String>(Arrays.asList("Black", "Blue", "Gold", "Pink")));//j5 2017
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//j7 2016
            add(new ArrayList<String>(Arrays.asList("Black", "Blue", "Gold", "Pink")));//j7 2017
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Pink")));//note 3
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//note 4
            add(new ArrayList<String>(Arrays.asList("Black Sapphire", "White Pearl", "Gold Platinum", "Silver Titanium")));//note 5

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

    public static final HashMap<String, ArrayList<String>> MAP_MODEL_SUBMODEL;

    static {
        MAP_MODEL_SUBMODEL = new HashMap<>();
        for (int i = 0; i < MODELS.size(); i++) {
            MAP_MODEL_SUBMODEL.put(MODELS.get(i), SUB_MODELS.get(i));
        }
    }

    public static final HashMap<String, ArrayList<String>> MAP_MODEL_COLOR;

    static {
        MAP_MODEL_COLOR = new HashMap<>();
        for (int i = 0; i < MODELS.size(); i++) {
            MAP_MODEL_COLOR.put(MODELS.get(i), COLORS.get(i));
        }
    }

    public static final HashMap<String, String> MAP_VENDOR_OFFER;

    static {
        MAP_VENDOR_OFFER = new HashMap<>();
        for (int i = 0; i < VENDORS.size(); i++) {
            MAP_VENDOR_OFFER.put(VENDORS.get(i), VENDOR_OFFERS.get(i));
        }
    }

    public static final HashMap<String, Integer>[] MAP_MODEL_PER_MONTH_COUNT
            = new HashMap[CITIES.length];

    static {
        for (int i = 0; i < CITIES.length; i++) {
            MAP_MODEL_PER_MONTH_COUNT[i] = new HashMap<>();
            for (int j = 0; j < MODELS.size(); j++) {
                MAP_MODEL_PER_MONTH_COUNT[i].put(MODELS.get(j), GADGET_PER_MONTH_COUNT[i].get(j));
            }
        }
    }
}
