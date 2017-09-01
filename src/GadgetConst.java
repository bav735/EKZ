import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by A on 05.08.2017.
 */
public class GadgetConst {
    public final static ArrayList<Integer> modelCount = new ArrayList<Integer>(Arrays.asList(
            36,
            59,
            8));

    public final static ArrayList<String> vendors = new ArrayList<String>(Arrays.asList(
            "Samsung",
            "Apple",
            "Sony"));

    public final static ArrayList<String> modelLines = new ArrayList<String>(Arrays.asList(
            "iPhone",
            "Galaxy",
            "Xperia"));

    public final static ArrayList<String> memories = new ArrayList<String>(Arrays.asList(
            "0Gb",
            "8Gb",
            "16Gb",
            "32Gb",
            "64Gb",
            "128Gb",
            "256Gb"));

    public final static ArrayList<String> models = new ArrayList<String>(Arrays.asList(
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

    public final static ArrayList<Integer> gadgetPerMonthCount = new ArrayList<Integer>(Arrays.asList(
            11,//"Grand Prime",
            4,//"Core Prime",
            5,//"Alpha",
            33,//"S3",
            2,//"S3 Mini",
            36,//"S4",
            19,//"S4 Mini",
            27,//"S5",
            7,//"S5 Mini",
            48,//"S6",
            23,//"S6 Edge",
            2,//"S6 Edge Plus",
            93,//"S7",
            56,//"S7 Edge",
            53,//"S8",
            3,//"S8 Plus",
            4,//"A3 (2015)",
            23,//"A3 (2016)",
            12,//"A3 (2017)",
            7,//"A5 (2015)",
            23,//"A5 (2016)",
            9,//"A5 (2017)",
            4,//"A7 (2015)",
            6,//"A7 (2016)",
            6,//"A7 (2017)",
            22,//"J1 (2016)",
            4,//"J2 (2016)",
            21,//"J3 (2016)",
            1,//"J3 (2017)",
            13,//"J5 (2016)",
            4,//"J5 (2017)",
            12,//"J7 (2016)",
            1,//"J7 (2017)",
            16,//"Note 3",
            8,//"Note 4",
            7,//"Note 5"));

            95,//4
            171,//4s
            190,//5
            54,//5c
            700,//5s
            600,//6
            65,//6+
            289,//6s
            48,//6s+
            63,//se
            300,//7
            85,//7+

            3,//"SP",
            10,//"Z",
            12,//"Z1",
            5,//"Z1 Compact",
            15,//"Z2",
            36,//"Z3",
            13,//"Z3 Compact",
            5//"Z5 Compact"
    ));

    public final static ArrayList<ArrayList<String>> subModels = new ArrayList<ArrayList<String>>() {
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
    private final static String[] subModelEndings = new String[]{"F", "V", "A", "T", "FD"};

    static {
        for (int i = 0; i < modelCount.get(0); i++) {
            ArrayList<String> subModelList = subModels.get(i);
            String submodel = subModelList.get(0);
            for (String modelEnding : subModelEndings) {
                String newSubModel = submodel + modelEnding;
                subModelList.add(newSubModel);
            }
        }
    }

    public final static ArrayList<ArrayList<String>> colors = new ArrayList<ArrayList<String>>() {
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

    public static final HashMap<String, ArrayList<String>> mapModelSubmodel;

    static {
        mapModelSubmodel = new HashMap<>();
        for (int i = 0; i < models.size(); i++) {
            mapModelSubmodel.put(models.get(i), subModels.get(i));
        }
    }

    public static final HashMap<String, ArrayList<String>> mapModelColor;

    static {
        mapModelColor = new HashMap<>();
        for (int i = 0; i < models.size(); i++) {
            mapModelColor.put(models.get(i), colors.get(i));
        }
    }

    public static final HashMap<String, Integer> mapModelPerMonthCount;

    static {
        mapModelPerMonthCount = new HashMap<>();
        for (int i = 0; i < models.size(); i++) {
            mapModelPerMonthCount.put(models.get(i), gadgetPerMonthCount.get(i));
        }
    }
}
