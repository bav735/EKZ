import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by A on 05.08.2017.
 */
public class Galaxys {
    public final static ArrayList<String> models = new ArrayList<String>(Arrays.asList(
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
            "Note 5"));

    public final static ArrayList<Integer> gadgetPerMonthCount = new ArrayList<Integer>(Arrays.asList(
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
            5,//"J3 (2017)",
            17,//"J5 (2016)",
            5,//"J5 (2017)",
            7,//"J7 (2016)",
            5,//"J7 (2017)",
            13,//"Note 3",
            11,//"Note 4",
            6));//"Note 5"));

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
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink", "Blue")));//a3 2015
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//a3 2016
            add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a3 2017
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink", "Blue")));//a5 2015
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//a5 2016
            add(new ArrayList<String>(Arrays.asList("Black", "Gold", "Blue")));//a5 2017
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink", "Blue")));//a7 2015
            add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Pink")));//a7 2016
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
}
