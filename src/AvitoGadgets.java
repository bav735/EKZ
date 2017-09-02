import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class AvitoGadgets extends Gadgets {
    static int tSize = 11266;
    final static String NAME_BEGIN = "";
    final static String QUALITY = "Качество";
    final static String VENDOR = "Производитель";
    final static String MODEL_LINE = "Модельный ряд";
    final static String MODEL = "Модель";
    final static String SUBMODEL = "Подмодель";
    final static String MEMORY = "Память";
    final static String FINGER_PRINT = "Наличие отпечатка";
    final static String COLOR = "Цвет";
    final static String TOUCH_LOCKED = "Без Отп";
    final static String RST = "RST";
    final static String EST = "EST";
    final static String EST2 = "EST2";
    final static String IMG_FILE_NAME = "img";
    final static int TOP_COUNT = 6;
    final static int DAYS_OFFSET = 2;
    final static int TIME_DAY_SEC = 12 * 60 * 60;
    final static int TIME_MONTH_SEC = 30 * TIME_DAY_SEC;
    final static int HOUR_BEGIN = 9;
    //    final static int MINUTE_BEGIN = 30;
    public static final Calendar CALENDAR_ZERO;
    public static final int DAY_NUM_GLOBAL;

    static {
        CALENDAR_ZERO = Calendar.getInstance();
        CALENDAR_ZERO.set(Calendar.YEAR, 2017);
        CALENDAR_ZERO.set(Calendar.MONTH, 8);//september
        CALENDAR_ZERO.set(Calendar.DAY_OF_MONTH, 1);
        setCalendarToZero(CALENDAR_ZERO);
        Calendar calendar = Calendar.getInstance();
        setCalendarToZero(calendar);
        DAY_NUM_GLOBAL = (int) ((calendar.getTimeInMillis() - CALENDAR_ZERO.getTimeInMillis())
                / 1000 / 3600 / 24) + 1;
    }

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

    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    HashSet<String> includeAds;

    public AvitoGadgets() {
    }

    public void initialize() {
        initializeMapGadgetAttributeNumber(gadgetAttributeNames);
        initializeIncludeAds();
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(EST, RST)));
        gadgetAttributesVariants.add(new ArrayList<String>(GadgetConst.VENDORS));
        gadgetAttributesVariants.add(new ArrayList<String>(GadgetConst.MODEL_LINES));
        gadgetAttributesVariants.add(GadgetConst.MODELS);
        gadgetAttributesVariants.add(new ArrayList<>(GadgetConst.MEMORIES));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("", TOUCH_LOCKED)));
    }

    private static void setCalendarToZero(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void initializeIncludeAds() {
        includeAds = new HashSet<>();
        Scanner inScanner = Solution.getInputScanner("include_ads.txt");
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (line.startsWith("Новый") || line.startsWith("iPhone") || line.startsWith("Galaxy")) {
                includeAds.add(line.substring(0, line.lastIndexOf(" ")));
            }
        }
        inScanner.close();
    }

    /*public static String getLongColor(String shortColor) {
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
    }*/

    /*public static void initializeExcludeAds() {
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
    }*/

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

    public void printWebsiteYML(BufferedWriter bufferedWriter) throws IOException {
        for (ArrayList<String> gadget : gadgets) {
            int price = -1;
            String quality = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
            String vendor = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
            String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
            String subModel = gadget.get(mapGadgetAttributeNumber.get(SUBMODEL));
            String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
            String fingerPrint = gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT));
            String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
            if (quality.equals(EST) && subModel.equals(GadgetConst.MAP_MODEL_SUBMODEL.get(model).get(0))) {
                price = getPriceISPARK(getGadgetName(gadget), 0);
            }
            if (quality.equals(RST) && subModel.equals(GadgetConst.MAP_MODEL_SUBMODEL.get(model).get(1))) {
                price = getPriceISPARK(getGadgetName(gadget), 1);
            }
            if (price > 0) {
                if (model.contains("7") && color.toLowerCase().contains("red")) {
                    price = getIncreasedPrice(price) - 10;
                }
                bufferedWriter.write("<offer>\n" +
                        "<initialCategoryId>2</initialCategoryId>\n" +
                        "<typePrefix>Смартфон</typePrefix>\n" +
                        "<model>" + gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE)) + " " +
                        model + " " + gadget.get(mapGadgetAttributeNumber.get(MEMORY)) + " " +
                        color + " " + subModel);
                if (!fingerPrint.isEmpty()) {
                    bufferedWriter.write(" " + fingerPrint);
                }
                bufferedWriter.write("</model>\n" +
                        "<vendor>" + gadget.get(mapGadgetAttributeNumber.get(VENDOR)) + "</vendor>\n" +
                        "<price>" + price + ".0</price>\n" +
                        "<description>" + getDescriptionByModel(vendor, modelLine, model) + "</description>\n" +
                        "<picture>" + getImageUrlPath(gadget) + "</picture>\n" +
                        "</offer>\n");
            }
        }
        bufferedWriter.flush();
    }

    private String getDescriptionByModel(String vendor, String modelLine, String model) {
        Scanner inScanner = Solution.getInputScanner("Website/Spec/" +
                vendor + " " + modelLine + " " + model + ".txt");
        String res = inScanner.nextLine();
        while (inScanner.hasNextLine()) {
            res += "\n" + inScanner.nextLine();
        }
        inScanner.close();
        return res;
    }

    public String getAmoledImagePath(ArrayList<String> gadget) {
        return "imgs/" + gadget.get(mapGadgetAttributeNumber.get(QUALITY)) +
                "/" + getImagePath(gadget);
    }

    public String getImagePath(ArrayList<String> gadget) {
        String vendor = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
        String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
        String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        return vendor + "/" + modelLine + "/" + model.replace(" ", "") + "/" + color.replace(" ", "")
                + "/" + IMG_FILE_NAME + ".jpg";
    }

    public String getImageUrlPath(ArrayList<String> gadget) {
        return "https://raw.githubusercontent.com/bav735/EKZ/master/" + getImagePath(gadget);
    }

    private boolean notEnoughModel(ArrayList<String> gadget) {
        return false;
//        return gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("4") ||
//                gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("4S") ||
//                gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("5");
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            ArrayList<String> prices = mapGadgetNamePrices.get(getGadgetName(gadget));
            if (prices != null) {
                String quality = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
                switch (quality) {
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
                System.out.println(quality + " " + getGadgetName(gadget));
                String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
                for (String color : GadgetConst.MAP_MODEL_COLOR.get(model)) {
                    for (String submodel : GadgetConst.MAP_MODEL_SUBMODEL.get(model)) {
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

    /*private ArrayList<String> extractGadgetByModel(int modelId) {
        String model = gadgetAttributesVariants.get(mapGadgetAttributeNumber.get(MODEL)).get(modelId);
        ArrayList<ArrayList<String>> gadgetsByModel = mapGadgetModelGadgets.get(model);
        ArrayList<String> gadget = gadgetsByModel.get(gadgetsByModel.size() - 1);
        gadgetsByModel.remove(gadgetsByModel.size() - 1);
        mapGadgetModelGadgets.put(model, gadgetsByModel);
        return gadget;
    }*/

    public String getIdName(ArrayList<String> gadget) {
        return String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(QUALITY),
                mapGadgetAttributeNumber.get(COLOR) + 1)).replaceAll("[() -]", "");
    }

    public String getAvitoAdName(ArrayList<String> gadget) {
        String name = NAME_BEGIN;
        if (!gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(EST2)) {
            name += "Новый ";
        }
        name += String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(MODEL_LINE),
                mapGadgetAttributeNumber.get(COLOR) + 1)).replace("  ", " ");
        if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(EST2)) {
            name += " Реф";
        }
        name += " Магазин";
        return name;
    }

    private String getGadgetName(ArrayList<String> gadget) {
//        System.out.println(gadget.toString());
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

    private String getOfferAMOLED(ArrayList<String> gadget) {
        String offer = "<p>➡";
        offer += String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(VENDOR),
                mapGadgetAttributeNumber.get(COLOR) + 1)).replace("  ", " ");
        offer += " = " + getPriceAMOLED(gadget) + " руб ";
        switch (gadget.get(mapGadgetAttributeNumber.get(QUALITY))) {
            case EST2:
                offer += "(восстановленный)";
                break;
            case RST:
                offer += "(совершенно новый, гарантия производителя)";
        }
        offer += "</p>";
        return offer;
    }

    private int getCreditPrice(ArrayList<String> gadget) {
        int creditPrice = Integer.parseInt(getPriceAMOLED(gadget)) * 112 / 600;
        return (creditPrice / 50 + 1) * 50;
    }

    public static int getPriceISPARK(String gadgetName, int submodelNum) {
        if (submodelNum == 0) {
            return Solution.getNumber(mapGadgetNamePrices.get(gadgetName)
                    .get(mapPriceAttributeNumber.get(Gadgets.EST_RETAIL_ISPARK))) - 10;
        } else {
            return Solution.getNumber(mapGadgetNamePrices.get(gadgetName)
                    .get(mapPriceAttributeNumber.get(Gadgets.RST_RETAIL_ISPARK))) - 10;
        }
    }

    public static boolean inPriceList(String gadgetName) {
        return mapGadgetNamePrices.containsKey(gadgetName);
    }

    private String getPriceAMOLED(ArrayList<String> gadget) {
        String gadgetName = getGadgetName(gadget);
        int price = 0;
        switch (gadget.get(mapGadgetAttributeNumber.get(QUALITY))) {
            case EST:
                price -= 10;
            case EST2:
                price += Integer.parseInt(
                        mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(EST_RETAIL_AMOLED)));
                break;
            case RST:
                price += Integer.parseInt(
                        mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(RST_RETAIL_AMOLED)));
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

    private String getAdTextAvitoBot(ArrayList<String> gadget) {
        String text = "";
        text += "Уважаемый покупатель,\n" +
                "Добро пожаловать в магазин AMOLED\n\n";
        text += "АКЦИЯ, аксессуар на выбор в ПОДАРОК за отзыв!\n\n";
        text += "Мы всегда идем навстречу нашим покупателям и дорожим своей репутацией.\n" +
                "Мы предлагаем вам:\n" +
                "1) КРЕДИТ от ОТП Банк/Хоум-Кредит\n" +
                "2) ТРЕЙД-ИН, ОБМЕН старого телефона\n" +
                "3) ОПЛАТА кредитной/дебетовой КАРТОЙ\n" +
                "4) ОПТ, ОПЛАТА ЧЕРЕЗ Р/С (ндс, без ндс)\n" +
                "5) ДОСТАВКА ПО РФ через ТК CDEK (1-2 дня)\n" +
                "Мы занимаемся продажей смартфонов и аксессуаров с 2009 года.\n\n";
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Apple")) {
            text += "В нашем ассортименте оригинальный айфоны 4/4s/5/5c/5s/6/6s/se/7/plus всех цветов и объемов памяти" +
                    " по лучшей цене в Казани!\n\n";
        } else {
            text += "В нашем ассортименте оригинальный самсунг галакси s3/s4/s5/s6/s7/s8 edge/plus/alpha," +
                    " a3/a5/a7/j1/j2/j3/j5/j7 2015/2015/2017, note 3/4/5 всех цветов и объемов памяти" +
                    " по лучшей цене в Казани!\n\n";
        }
        text += getOfferAMOLED(gadget);
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

    private String getAdTextAvitoShop(ArrayList<String> gadget) {
        String text = "<![CDATA[";
        text += "<p>Уважаемый покупатель,<br>" +
                "Добро пожаловать в магазин AMOLED\uD83D\uDCF2</p>";
        text += "<p>\uD83D\uDCA3ГАРАНТИЯ ЛУЧШЕЙ ЦЕНЫ, нашли дешевле в другом магазине? сделаем СКИДКУ❗</p>";
        text += "<p>\uD83D\uDC9BМы всегда идем навстречу нашим покупателям.<br>" +
                "\uD83D\uDC49Мы предлагаем вам:<br>" +
                "\uD83D\uDD39 КРЕДИТ от ОТП Банк/Хоум-Кредит<br>" +
                "\uD83D\uDD39 ТРЕЙД-ИН, ОБМЕН старого телефона<br>" +
                "\uD83D\uDD39 ОПЛАТА кредитной/дебетовой КАРТОЙ<br>" +
                "\uD83D\uDD39 ОПТ, ОПЛАТА ЧЕРЕЗ Р/С (ндс, без ндс)<br>" +
                "\uD83D\uDD39 ДОСТАВКА ПО РФ через ТК CDEK (1-2 дня)<br>" +
                "\uD83D\uDD1DМы занимаемся продажей смартфонов и аксессуаров с 2009 года.</p>";
        text += "<p>В нашем ассортименте только \uD83D\uDCAFоригинальные ";
        switch (gadget.get(mapGadgetAttributeNumber.get(VENDOR))) {
            case "Apple":
                text += "айфоны 4/4s/5/5c/5s/6/6s/se/7/plus";
                break;
            case "Samsung":
                text += "самсунг галакси s3/s4/s5/s6/s7/s8 edge/plus/alpha, " +
                        "a3/a5/a7/j1/j2/j3/j5/j7 2015/2015/2017, note 3/4/5";
                break;
            case "Sony":
                text += "сони икспериа sp/z/z1/z2/z3/z5/compact";
                break;
        }
        text += " всех цветов и объемов памяти по лучшей цене в Казани!\uD83D\uDE0A</p>";
        text += getOfferAMOLED(gadget);
        text += "<p>✔ обеспечиваем гарантию на ремонтное обслуживание в течение 1 года<br>";
        text += "✔ выдаем товарный чек и гарантийный талон, заверенные живой печатью<br>";
        text += "✔ весь товар в пленке, без следов эксплуатации, подойдет для подарка<br>";
        text += "✔ перед визитом в магазин, просим уточнять актуальное наличие товара</p>";
        text += "<p>\uD83D\uDCDE Звоните: 10.00-20.00, ежедневно</p>" +
                "<p>У нас вы сможете наиболее выгодно купить интересующий вас гаджет или аксессуар!" +
                "\uD83D\uDC4D<br>" +
                "Магазин AMOLED\uD83D\uDCF2</p>";
        text = text.replace(TOUCH_LOCKED, "без отпечатка");
        return text + "]]>";
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

    public String getXmlAd(ArrayList<String> gadget, int xmlDay, String dateEnd, String city, int gadgetId) {
        String ad = "\t<Ad>\n";
        ad += "\t\t<Id>" + getIdName(gadget) + "</Id>\n";
        String name = getAvitoAdName(gadget);
        if (gadgetId > 0 && (!includeAds.contains(name.substring(0, name.lastIndexOf(" "))) ||
                !city.equals(GadgetConst.CITIES[0]))) {
            Calendar calendarZero = (Calendar) CALENDAR_ZERO.clone();
            int dayNumCurrentMonth = (DAY_NUM_GLOBAL - 1) % 30 + 1;
            calendarZero.add(Calendar.DAY_OF_MONTH, DAY_NUM_GLOBAL - dayNumCurrentMonth - 1 + xmlDay);
            if (dayNumCurrentMonth <= DAYS_OFFSET) {
                if (xmlDay > dayNumCurrentMonth + 30 - DAYS_OFFSET) {
                    calendarZero.add(Calendar.DAY_OF_MONTH, -30);
                }
            } else {
                if (xmlDay <= dayNumCurrentMonth - DAYS_OFFSET) {
                    calendarZero.add(Calendar.DAY_OF_MONTH, 30);
                }
            }
            String dateBegin = getDateByCalendar(calendarZero);
            ad += "\t\t<DateBegin>" + dateBegin + dateEnd + "</DateBegin>\n";
        }
        ad += "\t\t<AllowEmail>Нет</AllowEmail>\n";
        ad += "\t\t<ManagerName>Оператор-консультант</ManagerName>\n";
        ad += "\t\t<ContactPhone>89393911570</ContactPhone>\n";
        ad += "\t\t<Region>Татарстан</Region>\n";
        ad += "\t\t<City>" + city + "</City>\n";
        ad += "\t\t<Category>Телефоны</Category>\n";
        String goodsType = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
        if (goodsType.equals("Apple")) {
            goodsType = "iPhone";
        }
        ad += "\t\t<GoodsType>" + goodsType + "</GoodsType>\n";
        ad += "\t\t<Title>" + name + "</Title>\n";
        ad += "\t\t<Description>" + getAdTextAvitoShop(gadget) + "</Description>\n";
        ad += "\t\t<Price>" + getPriceAMOLED(gadget) + "</Price>\n";
        ad += "\t\t<Images>\n";
        String imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/" +
                getAmoledImagePath(gadget);
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
        ad += "Текст: " + getAdTextAvitoBot(gadget) + "\n";
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

    private String getGadgetPath(ArrayList<String> gadget, int lastAttr) {
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

    private void generateAmoledDirsPhotos(ArrayList<String> gadget) {
        File avitoImage = new File("C:/AMOLED/" + getAmoledImagePath(gadget));
        avitoImage.mkdirs();
        File gadgetImg = new File("C:/iSPARK/images/" +
                gadget.get(mapGadgetAttributeNumber.get(VENDOR)) + "/" +
                gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE)) + "/" +
                gadget.get(mapGadgetAttributeNumber.get(MODEL)).replace(" ", "") + "/" +
                gadget.get(mapGadgetAttributeNumber.get(COLOR)).replace(" ", "") + "/" +
                IMG_FILE_NAME + ".jpg");
        try {
            Files.copy(gadgetImg.toPath(), avitoImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private HashMap<String, ArrayList<ArrayList<String>>> getModelGadgetMap(ArrayList<ArrayList<String>> gadgets) {
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets = new HashMap<>();
        for (ArrayList<String> gadget : gadgets) {
            if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(EST)) {
                String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
                if (!mapGadgetModelGadgets.containsKey(model)) {
                    mapGadgetModelGadgets.put(model, new ArrayList<ArrayList<String>>());
                }
                mapGadgetModelGadgets.get(model).add(gadget);
                ArrayList<String> gadget2 = new ArrayList<>(gadget);
                gadget2.set(mapGadgetAttributeNumber.get(QUALITY), EST2);
                mapGadgetModelGadgets.get(model).add(gadget2);
            }
        }
        return mapGadgetModelGadgets;
    }

    private HashMap<String, ArrayList<ArrayList<String>>> getMemoryGadgetMap(ArrayList<ArrayList<String>> gadgets) {
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetMemoryGadgets = new HashMap<>();
        for (ArrayList<String> gadget : gadgets) {
            String memory = gadget.get(mapGadgetAttributeNumber.get(MEMORY));
            if (!mapGadgetMemoryGadgets.containsKey(memory)) {
                mapGadgetMemoryGadgets.put(memory, new ArrayList<ArrayList<String>>());
            }
            mapGadgetMemoryGadgets.get(memory).add(gadget);
        }
        return mapGadgetMemoryGadgets;
    }

    private String formatDateElem(int dateElem) {
        String res = "" + dateElem;
        if (dateElem < 10) {
            res = "0" + dateElem;
        }
        return res;
    }

    public void generateXML() throws IOException {
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets = getModelGadgetMap(gadgets);
        String xml = "<Ads formatVersion=\"3\" target=\"Avito.ru\">\n";
        int[] size = new int[GadgetConst.MODELS.size()];
        Arrays.fill(size, 0);
        for (int cityNum = 0; cityNum < GadgetConst.CITIES.length; cityNum++) {
            String city = GadgetConst.CITIES[cityNum];
            for (int modelNum = 0; modelNum < GadgetConst.MODELS.size(); modelNum++) {
                String model = GadgetConst.MODELS.get(modelNum);
                if (!mapGadgetModelGadgets.containsKey(model)) {
                    continue;
                }
                ArrayList<ArrayList<String>> gadgets = mapGadgetModelGadgets.get(model);
                Collections.sort(gadgets, new CustomComparator());
                int prevSize = size[modelNum];
                size[modelNum] += GadgetConst.GADGET_PER_MONTH_COUNT[cityNum].get(modelNum) / TOP_COUNT + 1;
                int timeIntervalSec = TIME_MONTH_SEC / (size[modelNum] - prevSize);
                System.out.println("$model:" + model + "size_curr:" + size[modelNum] +
                        " size_all: " + gadgets.size());
                for (int gadgetNum = size[modelNum]; gadgetNum >= prevSize; gadgetNum--) {
                    int gadgetId = size[modelNum] - gadgetNum;
                    int gadgetTimeSec = timeIntervalSec / 2 + gadgetId * timeIntervalSec;
                    int gadgetTimeDay = gadgetTimeSec / TIME_DAY_SEC + 1;
                    gadgetTimeSec %= TIME_DAY_SEC;
                    int gadgetTimeHour = gadgetTimeSec / 3600 + HOUR_BEGIN;
                    gadgetTimeSec %= 3600;
                    int gadgetTimeMin = gadgetTimeSec / 60;
                    gadgetTimeSec %= 60;
                    String dateEnd = "T" + formatDateElem(gadgetTimeHour) + ":" +
                            formatDateElem(gadgetTimeMin) + ":" +
                            formatDateElem(gadgetTimeSec) + "+03:00";
                    xml += getXmlAd(gadgets.get(gadgetNum), gadgetTimeDay, dateEnd, city, gadgetId);
                    generateAmoledDirsPhotos(gadgets.get(gadgetNum));
                }
            }
        }
        xml += "</Ads>";
        BufferedWriter writer = Solution.getOutputWriter("Output/", "AdsXML.xml ");
        writer.write(xml);
        writer.flush();
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
                    getAdTextAvitoBot(gadget) + "\";\"" +
                    getPriceAMOLED(gadget) + "\";\"" +
                    i + ".jpg" + ",price" + gadgetNum + ".jpg\"\n";
            gadgetNum++;
        }
        return res;
    }

    public void generateFilesAvibot() throws IOException {
        int gadgetNum = 0;
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets = getModelGadgetMap(gadgets);
        for (String model : GadgetConst.MODELS) {
            int size = 0;//Math.min(GadgetConst.MAP_MODEL_PER_MONTH_COUNT.get(model), mapGadgetModelGadgets.get(model).size());
            BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/AvitoRobot/" + model, "ads.csv");
            bufferedWriter.write(getRobotText(model, mapGadgetModelGadgets.get(model), gadgetNum, size));
            bufferedWriter.flush();
            gadgetNum += size;
//            c += Math.min(mapGadgetModelGadgetPerMonthCount.get(model), mapGadgetModelGadgets.get(model).baseSize());
        }
    }

    private class CustomComparator implements Comparator<ArrayList<String>> {
        @Override
        public int compare(ArrayList<String> g1, ArrayList<String> g2) {
            return Solution.getNumber(getPriceAMOLED(g1)) - Solution.getNumber(getPriceAMOLED(g2));
        }
    }
}