import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by A on 05.08.2017.
 */
public class iPhones {
    public final static ArrayList<String> models = new ArrayList<String>(Arrays.asList(
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
}
