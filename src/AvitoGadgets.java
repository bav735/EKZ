import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class AvitoGadgets extends Gadgets {
    final static String NAME_BEGIN = "Рассрочка";
    //    final static String QUALITY = "Качество";
    final static String VENDOR = "Производитель";
    final static String MODEL_LINE = "Модельный ряд";
    final static String MODEL = "Модель";
    final static String SUBMODEL = "Подмодель";
    final static String MEMORY = "Память";
    final static String FINGER_PRINT = "Наличие отпечатка";
    final static String COLOR = "Цвет";
    final static String TOUCH_LOCKED = "Б/О";
    final static String NEW = "Новый";
    final static String RFB = "";
    //    final static String[] CITIES = new String[]{"Казань"};
    final static String IMG_FILE_NAME = "avito_img";
    final static int DISTRIBUTION_SIZE = 4;
    final static int ADS_PER_DAY = 19;
    final static int DAYS_OFFSET = 3;
    final static int IPHONES_COUNT = 12;
    final static int MAX_FREQUENCE = 4;

    HashMap<String, ArrayList<String>> mapGadgetModelSubmodel;
    HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets;
    HashMap<String, ArrayList<String>> mapGadgetModelColor;
    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    static HashSet<String> excludeAds;
    int[][] gadgetsDistribution;

    public AvitoGadgets() {
    }

    public void initializeIPhones() {
        gadgetAttributeNames = new String[]{
//                QUALITY,
                VENDOR,
                MODEL_LINE,
                MODEL,
                MEMORY,
                FINGER_PRINT,
                SUBMODEL,
                COLOR
        };
        initializeMapGadgetAttributeNumber();
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
//        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("")));
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
        ArrayList<ArrayList<String>> submodels = new ArrayList<>();
        submodels.add(new ArrayList<String>(Arrays.asList("A1349", "A1332")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1431", "A1387")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1428", "A1429", "A1442")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1456", "A1507", "A1516", "A1529", "A1532")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1453", "A1457", "A1518", "A1528", "A1530", "A1533")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1549", "A1586", "A1589")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1522", "A1524", "A1593")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1633", "A1688", "A1700")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1634", "A1687", "A1699")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1723", "A1662", "A1724")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1660", "A1778", "A1779")));
        submodels.add(new ArrayList<String>(Arrays.asList("A1661", "A1784", "A1785")));
        mapGadgetModelSubmodel = new HashMap<>();
        ArrayList<String> models = gadgetAttributesVariants.get(gadgetAttributesVariants.size() - 1);
        /*for (int i = 0; i < models.size(); i++) {
            submodels.add(new ArrayList<>(Arrays.asList("")));
        }*/
        for (int i = 0; i < models.size(); i++) {
            mapGadgetModelSubmodel.put(models.get(i), submodels.get(i));
        }
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
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Jet Black", "Gold", "Pink", "Red")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Jet Black", "Gold", "Pink", "Red")));
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
    }

    public void initializeSamsungs() {
        gadgetAttributeNames = new String[]{
                VENDOR,
                MODEL_LINE,
                MODEL,
                MEMORY,
                FINGER_PRINT,
                SUBMODEL,
                COLOR
        };
        initializeMapGadgetAttributeNumber();
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Samsung")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Galaxy")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(
                "S3",
                "S4",
                "S5",
                "S6",
                "S6 Edge",
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
                "Note 5"
        )));
        ArrayList<ArrayList<String>> submodels = new ArrayList<>();
        mapGadgetModelSubmodel = new HashMap<>();
        ArrayList<String> models = gadgetAttributesVariants.get(gadgetAttributesVariants.size() - 1);
        for (int i = 0; i < models.size(); i++) {
            submodels.add(new ArrayList<>(Arrays.asList("", "Duos")));
        }
        for (int i = 0; i < models.size(); i++) {
            mapGadgetModelSubmodel.put(models.get(i), submodels.get(i));
        }
        ArrayList<ArrayList<String>> colors = new ArrayList<>();
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s3
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Blue")));//s4
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold")));//s5
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Blue")));//s6
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Gold", "Green", "Blue")));//s6 edge
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
        for (int i = 0; i < models.size(); i++) {
            mapGadgetModelColor.put(models.get(i), colors.get(i));
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
            if (line.startsWith(NAME_BEGIN)) {
                excludeAds.add(line);
            }
        }
        inScanner.close();
    }

    private boolean excludeModel(ArrayList<String> gadget) {
        return gadget.get(mapGadgetAttributeNumber.get(MODEL)).contains("7") &&
                gadget.get(mapGadgetAttributeNumber.get(MEMORY)).contains("32") &&
                (gadget.get(mapGadgetAttributeNumber.get(COLOR)).contains("Red") ||
                        gadget.get(mapGadgetAttributeNumber.get(COLOR)).contains("Jet"));
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            if (mapGadgetNamePrices.containsKey(getGadgetName(gadget))) {
                ArrayList<String> prices = mapGadgetNamePrices.get(getGadgetName(gadget));
//                if (prices.get(mapPriceAttributeNumber.get(EST_RETAIL_MIN)).length() > 1) {
//                    gadget.set(mapGadgetAttributeNumber.get(QUALITY), RFB);
//                } else {
//                    gadget.set(mapGadgetAttributeNumber.get(QUALITY), NEW);
//                }
                String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
                for (String submodel : mapGadgetModelSubmodel.get(model)) {
                    for (String color : mapGadgetModelColor.get(model)) {
                        ArrayList<String> newGadget = new ArrayList<String>(gadget);
                        newGadget.add(mapGadgetAttributeNumber.get(SUBMODEL), submodel);
                        newGadget.add(mapGadgetAttributeNumber.get(COLOR), color);
                        if (!excludeModel(newGadget)) {
                            gadgets.add(newGadget);
//                            generateDirsPhotos(newGadget);
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
//                System.out.print(getGadgetName(g));
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
        String name = "";
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Samsung")) {
            name += "Новый ";
        }
        name += NAME_BEGIN;
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
        System.out.println(gadget.toString());
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
        if (getMinPrice(gadget).isEmpty()) {
            return "";
        }
        return "- в кредит на 6 мес = от " + getCreditPrice(gadget) + "₽ в мес<br>";
    }

    private String getRefOffer(ArrayList<String> gadget) {
        String gadgetName = getGadgetName(gadget);
        String refPrice = mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(EST_RETAIL_MIN));
        if (refPrice.length() == 1) {
            return "";
        }
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        String offer = gadgetName + " " + color + " - " + refPrice + "₽ (";
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Apple")) {
            offer += "РЕФ США";
        } else {
            offer += "ЕСТ";
        }
        offer += "), гарантия 1 ГОД от iSPARK\n";
        return offer;
    }

    private String getNewOffer(ArrayList<String> gadget) {
        String gadgetName = getGadgetName(gadget);
        String newPrice = mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(RST_RETAIL_MIN));
        if (newPrice.length() == 1) {
            return "";
        }
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        String offer = gadgetName + " " + color + " - " + newPrice + "₽ (";
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Apple")) {
            offer += "НОВЫЙ РСТ), гарантия 1 ГОД от APPLE";
        } else {
            offer += "РСТ), гарантия 1 ГОД от Samsung";
        }
        offer += "\n";
        return offer;
    }

    private int getCreditPrice(ArrayList<String> gadget) {
        int creditPrice = Integer.parseInt(getMinPrice(gadget)) * 112 / 600;
        return (creditPrice / 50 + 1) * 50;
    }

    private String getMinPrice(ArrayList<String> gadget) {
        String price = "";
        String gadgetName = getGadgetName(gadget);
        price = mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(EST_RETAIL_MIN));
        if (price.length() == 1) {
            price = mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(RST_RETAIL_MIN));
        }
        return price;
    }

    private String formatPrice(String price) {
//        int len = price.length();
//        return price.substring(0, len - 3) + " " + price.substring(len - 3, len);
        return price;
    }

    /*private String getNewTextXml(ArrayList<String> gadget) {
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        String text = "<![CDATA[";
        String gadgetName = getGadgetName(gadget);
//        String city = CITIES[cityId];
        text += "<p>Уважаемый клиент,<br>" +
                "Вас приветствует <strong>iSPARK</strong>";
        text += "</p><p>->Акция: Гарантия лучшей цены: Нашли дешевле в другом магазине?" +
                " Сделаем скидку!";
        text += "</p><p><strong>Почему iSPARK?</strong><br>" +
                "1) Мы всегда идем навстречу нашим клиентам и дорожим своей репутацией.<br>" +
                "2) Предлагаем широкие возможности вашей покупки:<br>" +
//                "- Рассрочка (0-0-6)<br>" +
//                "- Кредит (~1.5% в мес)<br>" +
//                "- Безналичная оплата (юрлицам)<br>" +
//                "- Оплата по банковской карте (в т.ч. кредитной)<br>" +
//                "- Доставка по РФ, ~300₽ (через CDEK)<br>" +
//                "- Самовывоз (Казань и Москва)<br>" +
//                "- Трейд-ин (обмен)<br>" +
//                "- Опт (от 6 шт)<br>" +
//                "- Выкуп<br>" +
                "- КРЕДИТ 1.5% в мес<br>" +
                "- РАССРОЧКА сроком до 6 мес<br>" +
                "- ОПЛАТА ПО КАРТЕ дебетовой/кредитной<br>" +
                "- БЕЗНАЛИЧНАЯ ОПЛАТА для юридических лиц<br>" +
                "- САМОВЫВОЗ КАЗАНЬ/МОСКВА, бесплатно<br>" +
                "- ДОСТАВКА ПО РФ от 2 дней, ~300₽<br>" +
                "- ТРЕЙД-ИН, система обмена<br>" +
                "- ВЫКУП в течение 10 мин<br>" +
                "- ОПТ от 6 шт<br>" +
                "3) Специализируемся на продаже и ремонте электроники с 2009 года.</p><p>";
//        if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(RFB)) {
//            text += "Предлагаем вам";
//        } else {
//            text += "Рады радовать вас новыми";
//        }
        int len = getMinPrice(gadget).length();
        text += "Рады предложить <strong>" + gadgetName + "</strong> цвета <strong>" +
                color + "</strong> по лучшей цене в Казани = <strong>" + formatPrice(getMinPrice(gadget)) + "₽!</strong> <br>" +
                "(в наличии/под заказ имеется ВЕСЬ модельный ряд iPhone)<br>";
        if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(RFB)) {
//            text += getCreditOffer(gadget);
//            text += getWholesaleOffer(gadgetName);
            text += "</p><p>";
            text += "- характеристики смартфонов смотрите на сайте ispark info<br>";
            text += "- РЕФ (восстановленный)*";
            text += ", гарантия предоставляется iSPARK<br>";
            text += "- полностью запечатаны, без следов эксплуатации, хороший подарок<br>";
        } else {
//            text += getCreditOffer(gadget);
            text += "</p><p>";
            text += "- характеристики смартфонов смотрите на сайте ispark info<br>";
            text += "- НЕ РЕФ, не восстановленные, ОФИЦИАЛЬНАЯ гарантия от Apple, 1 ГОД<br>";
            text += "- полностью запечатаны, без следов эксплуатации, отличный подарок<br>";
        }
//                        "<p><strong>Условия покупки:</strong><br>" +
//                        "- предзаказ, скидка до 5%<br>" +
//                        "- самовывоз, бесплатно<br>" +
//                        "- быстрая доставка, ";
        if (city.equals("Казань")) {
            text += "200₽<br>";
        } else {
            text += "350₽<br>";
        }
        text += "- при покупке выдается товарный чек и гарантийный талон";
        text += "<br>- пожалуйста, перед визитом уточняйте наличие товара</p>";
        text += "<p><strong>Местоположение iSPARK в Казани</strong> (см. в Яндекс.Картах, 2ГИС, Google Maps):<br>" +
                "- г. Казань, ул. Лушникова, д. 8, оф. 5<br>" +
//                "- г. Москва, ул. Молодежная, д. 4, оф. 3<br>" +
                "- время работы (пн-сб): 13.00-21.00</p>";
        text += "<p>Прием звонков: 9.00-21.00, без выходных<br>" +
                "(заказы на нашем сайте можно оставлять круглосуточно)</p>" +
                "<p>У нас вы найдете наиболее выгодное предложение по приобретению и ремонту" +
                " электроники!<br>" +
                "С уважением,<br>" +
                "<strong>iSPARK<strong/></p>";
        if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(RFB)) {
            text += "<br><p>*РЕФ (восстановленный) - телефон, прошедший заводскую процедуру восстановления до" +
                    " состояния нового путем замены корпуса, экрана, и др. запчастей</p>";
        }
        text = text.replace(TOUCH_LOCKED, "без отпечатка");
        text += "]]>";
        return text;
    }*/

    private String getNewText(ArrayList<String> gadget) {
        String text = "";
//        String gadgetName = getGadgetName(gadget);
//        String city = CITIES[cityId];
        text += "Уважаемый клиент,\n" +
                "Вас приветствует iSPARK\n\n";
        text += "- Акция: Гарантия лучшей цены: Нашли дешевле в другом магазине?" +
                " Сделаем скидку!\n\n";
        text += "Почему iSPARK?\n" +
                "1) Мы всегда идем навстречу нашим клиентам и дорожим своей репутацией.\n" +
                "2) Предлагаем широкие возможности вашей покупки:\n" +
                "- РАССРОЧКА сроком до 6 мес\n" +
                "- КРЕДИТ от банков ОТП/Хоум Кредит\n" +
                "- ОПЛАТА ПО КАРТЕ дебетовой/кредитной\n" +
                "- БЕЗНАЛИЧНАЯ ОПЛАТА для юридических лиц\n" +
                "- САМОВЫВОЗ КАЗАНЬ/МОСКВА, бесплатно\n" +
                "- ДОСТАВКА ПО РФ от 2 дней, ~300₽\n" +
                "- ТРЕЙД-ИН, система обмена\n" +
                "- ВЫКУП в течение 10 мин\n" +
                "- ОПТ от 3 шт\n" +
                "3) Специализируемся на продаже и ремонте электроники с 2009 года.\n\n";
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Apple")) {
            text += "Рады предложить оригинальные iPhone 4/4s/5/5c/5s/6/6s/SE/7 всех цветов и объемов памяти" +
                    " по лучшим ценам в Казани!\n\n";
        } else {
            text += "Рады предложить оригинальные Samsung Galaxy s3/s4/s5/s6/s7/s8 edge/plus," +
                    " a3/a5/a7 2015/2015/2017, note 3/4/5 всех цветов и объемов памяти" +
                    " по лучшим ценам в Казани!\n\n";
        }
        text += getRefOffer(gadget);
        text += getNewOffer(gadget);
        text += "- цена актуальна только для покупателей Авито, за наличный расчет\n\n";
        text += "- характеристики смотрите на сайте ispark info или в нашей группе ВК\n";
        text += "- полностью запечатаны, без следов эксплуатации, отличный подарок\n";
        text += "- при покупке выдается товарный чек и гарантийный талон с печатью\n";
        text += "- предложение ограничено, пожалуйста, бронируйте товар перед визитом\n\n";
        text += "Местоположение iSPARK в Казани (см. в Яндекс.Картах, 2ГИС, Google Maps)\n" +
                "- г. Казань, ул. Лушникова, д. 8, оф. 5\n" +
                "- время работы (пн-сб): 13.00-21.00\n\n";
        text += "Прием звонков: 9.00-21.00, без выходных\n\n" +
//                "(заказы на нашем сайте можно оставлять круглосуточно)\n\n" +
                "У нас вы найдете наиболее выгодное предложение по приобретению и ремонту" +
                " электроники!\n" +
                "С уважением,\n" +
                "iSPARK";
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
//        ad += "\t\t<Description>" + getNewTextXml(gadget) + "</Description>\n";
//        int price = Integer.parseInt(getMinPrice(gadget, cityId));
//        price = price * 95 / 100;
//        price += 100 - price % 100;
//        System.out.println(price);
        ad += "\t\t<Price>" + getMinPrice(gadget) + "</Price>\n";
        ad += "\t\t<Images>\n";
        String imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/" +
                getGadgetPath(gadget, SUBMODEL) + IMG_FILE_NAME + ".jpg";
        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
//        imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/price_iphone.png";
//        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
        ad += "\t\t</Images>\n";
        ad += "\t</Ad>\n";
        return ad;
    }

    private String getAdText(ArrayList<String> gadget) {
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
        ad += "Цена: " + getMinPrice(gadget) + "\n";
        ad += "Текст: " + getNewText(gadget) + "\n";
        return ad;
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

    public void generateDirsPhotos(ArrayList<String> gadget) {
        File gadgetDirImg = new File(ROOT_DIR + getGadgetPath(gadget, SUBMODEL));
        gadgetDirImg.mkdirs();
        gadgetDirImg = new File(gadgetDirImg, IMG_FILE_NAME + ".jpg");
        File gadgetImg = new File(ROOT_DIR + getGadgetPath(gadget, MODEL) + IMG_FILE_NAME + ".jpg");
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

    public void generateFiles() {
        for (int gadgetId = 0; gadgetId < gadgets.size(); gadgetId++) {
            ArrayList<String> gadget = gadgets.get(gadgetId);
            if (!excludeAds.contains(getAvitoAdName(gadget))) {
                Solution.writeText(Solution.getOutputWriter("Output/Avito/" +
                                gadget.get(mapGadgetAttributeNumber.get(VENDOR)) + "/" +
                                gadget.get(mapGadgetAttributeNumber.get(MODEL)) + "/" +
                                gadget.get(mapGadgetAttributeNumber.get(MEMORY)), getAvitoAdName(gadget) + ".txt"),
                        getAdText(gadgets.get(gadgetId)));
            }
        }
    }
}