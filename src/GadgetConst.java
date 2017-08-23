import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by A on 05.08.2017.
 */
public class GadgetConst {
    public final static ArrayList<String> vendors = new ArrayList<String>(Arrays.asList(
            "Apple",
            "Samsung"));

    public final static ArrayList<String> modelLines = new ArrayList<String>(Arrays.asList(
            "iPhone",
            "Galaxy"));

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
            "7 Plus"));

    public final static ArrayList<Integer> gadgetPerMonthCount = new ArrayList<Integer>(Arrays.asList(
            13,//"Grand Prime",
            4,//"Core Prime",
            5,//"Alpha",
            33,//"S3",
            2,//"S3 Mini",
            46,//"S4",
            19,//"S4 Mini",
            27,//"S5",
            7,//"S5 Mini",
            48,//"S6",
            23,//"S6 Edge",
            2,//"S6 Edge Plus",
            83,//"S7",
            44,//"S7 Edge",
            34,//"S8",
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
            14,//"Note 3",
            6,//"Note 4",
            6,//"Note 5"));

            103,//4
            158,//4s
            173,//5
            62,//5c
            768,//5s
            564,//6
            65,//6+
            289,//6s
            48,//6s+
            63,//se
            277,//7
            71));//7+

    public final static ArrayList<ArrayList<String>> subModels = new ArrayList<ArrayList<String>>() {
        {
            add(new ArrayList<String>(Arrays.asList("SM-G531", "SM-G531H")));//"Grand Prime",
            add(new ArrayList<String>(Arrays.asList("SM-G360", "SM-G360H")));//"Core Prime",
            add(new ArrayList<String>(Arrays.asList("SM-G850", "SM-G850F")));//"Alpha",
            add(new ArrayList<String>(Arrays.asList("GT-I9300", "GT-I9300I")));//"S3",
            add(new ArrayList<String>(Arrays.asList("GT-I8190", "GT-I8190I")));//"S3 Mini",
            add(new ArrayList<String>(Arrays.asList("GT-I9500", "GT-I9505")));//"S4",
            add(new ArrayList<String>(Arrays.asList("GT-I9192", "GT-I9192D")));//"S4 Mini",
            add(new ArrayList<String>(Arrays.asList("SM-G900", "SM-G900F")));//"S5",
            add(new ArrayList<String>(Arrays.asList("SM-G800", "SM-G800F")));//"S5 Mini",
            add(new ArrayList<String>(Arrays.asList("SM-G920", "SM-G920F")));//"S6",
            add(new ArrayList<String>(Arrays.asList("SM-G925", "SM-G925F")));//"S6 Edge",
            add(new ArrayList<String>(Arrays.asList("SM-G928", "SM-G928F")));//"S6 Edge Plus",
            add(new ArrayList<String>(Arrays.asList("SM-G930", "SM-G930F")));//"S7",
            add(new ArrayList<String>(Arrays.asList("SM-G935", "SM-G935F")));//"S7 Edge",
            add(new ArrayList<String>(Arrays.asList("SM-G950", "SM-G950F")));//"S8",
            add(new ArrayList<String>(Arrays.asList("SM-G955", "SM-G955F")));//"S8 Plus",
            add(new ArrayList<String>(Arrays.asList("SM-A300", "SM-A300F")));//"A3 (2015)",
            add(new ArrayList<String>(Arrays.asList("SM-A310", "SM-A310F")));//"A3 (2016)",
            add(new ArrayList<String>(Arrays.asList("SM-A320", "SM-A320F")));//"A3 (2017)",
            add(new ArrayList<String>(Arrays.asList("SM-A500", "SM-A500F")));//"A5 (2015)",
            add(new ArrayList<String>(Arrays.asList("SM-A510", "SM-A510F")));//"A5 (2016)",
            add(new ArrayList<String>(Arrays.asList("SM-A520", "SM-A520F")));//"A5 (2017)",
            add(new ArrayList<String>(Arrays.asList("SM-A700", "SM-A700F")));//"A7 (2015)",
            add(new ArrayList<String>(Arrays.asList("SM-A710", "SM-A710F")));//"A7 (2016)",
            add(new ArrayList<String>(Arrays.asList("SM-A720", "SM-A720F")));//"A7 (2017)",
            add(new ArrayList<String>(Arrays.asList("SM-J120", "SM-J120F")));//"J1 (2016)",
            add(new ArrayList<String>(Arrays.asList("SM-G532", "SM-G532F")));//"J2 (2016)",
            add(new ArrayList<String>(Arrays.asList("SM-J320", "SM-J320F")));//"J3 (2016)",
            add(new ArrayList<String>(Arrays.asList("SM-J330", "SM-J330F")));//"J3 (2017)",
            add(new ArrayList<String>(Arrays.asList("SM-J510", "SM-J510F")));//"J5 (2016)",
            add(new ArrayList<String>(Arrays.asList("SM-J530", "SM-J530F")));//"J5 (2017)",
            add(new ArrayList<String>(Arrays.asList("SM-J710", "SM-J710F")));//"J7 (2016)",
            add(new ArrayList<String>(Arrays.asList("SM-J730", "SM-J730F")));//"J7 (2017)",
            add(new ArrayList<String>(Arrays.asList("SM-N900", "SM-N900F")));//"Note 3",
            add(new ArrayList<String>(Arrays.asList("SM-N910", "SM-N910F")));//"Note 4",
            add(new ArrayList<String>(Arrays.asList("SM-N920", "SM-N920F")));//"Note 5"};

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
        }
    };

    public final static ArrayList<ArrayList<String>> colors = new ArrayList<ArrayList<String>>() {
        {
            add(new ArrayList<String>(Arrays.asList("Gray", "White")));//"Grand Prime",
            add(new ArrayList<String>(Arrays.asList("Gray", "White")));//"Core Prime",
            add(new ArrayList<String>(Arrays.asList("Gray", "White", "Gold", "Silver")));//"Alpha",
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
