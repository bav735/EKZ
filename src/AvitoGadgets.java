import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    final static String TOUCH_LOCKED = "Без Отп";
    final static String RST = "RST";
    final static String EST = "EST";
    //    final static String[] CITIES = new String[]{"Казань"};
    final static String IMG_FILE_NAME = "img";
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

    public static HashMap<String, Integer> mapGalaxysModelsNum;
    public static HashMap<String, String> mapGalaxysModelDescription;

    public static ArrayList<ArrayList<String>> galaxySubModels;

    HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets;
    HashMap<String, ArrayList<String>> mapGadgetModelSubmodel;
    HashMap<String, ArrayList<String>> mapGadgetModelColor;
    HashMap<String, Integer> mapGadgetModelGadgetPerMonthCount;
    ArrayList<String> gadgetModels;
    ArrayList<Integer> gadgetPerMonthCount;
    ArrayList<ArrayList<String>> gadgetSubModels;
    ArrayList<ArrayList<String>> gadgetColors;
    HashMap<String, String> mapGadgetModelDescription;
    ArrayList<ArrayList<String>> gadgetAttributesVariants;

    public AvitoGadgets(Scanner inScanner) {
        mapPriceAttributeNumber = new HashMap<>();
        for (int i = 0; i < priceAttributeNames.length; i++) {
            mapPriceAttributeNumber.put(priceAttributeNames[i], i);
        }
        mapGadgetNamePrices = new HashMap<>();
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            String[] words = line.split("\\s+");
            int i = 0;
            String gadgetName = String.join(" ", Arrays.copyOfRange(words, 0, words.length - PRICES_COUNT));
            String[] prices = Arrays.copyOfRange(words, words.length - PRICES_COUNT, words.length);
            mapGadgetNamePrices.put(gadgetName, new ArrayList<>(Arrays.asList(prices)));
            System.out.println(gadgetName+"|");
        }
        inScanner.close();
    }

    public void initialize(String vendor, String modelLine) {
        initializeMapGadgetAttributeNumber(gadgetAttributeNames);
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(EST, RST)));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(vendor)));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(modelLine)));
        gadgetModels = modelsByModelLine.get(modelLine);
        gadgetAttributesVariants.add(gadgetModels);
        gadgetAttributesVariants.add(new ArrayList<>(Arrays.asList("8Gb",
                "16Gb",
                "32Gb",
                "64Gb",
                "128Gb",
                "256Gb")));
        if (modelLine.equals("iPhone")) {
            gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("", TOUCH_LOCKED)));
        } else {
            gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("")));
        }
        mapGadgetModelSubmodel = mapGadgetModelSubmodelByModelLine.get(modelLine);
        mapGadgetModelDescription = new HashMap<>();
        for (String gadgetModel : gadgetModels) {
            mapGadgetModelDescription.put(gadgetModel, getDescriptionByModel(modelLine, gadgetModel));
        }
        gadgetColors = colorsByModelLine.get(modelLine);
        mapGadgetModelColor = new HashMap<>();
        for (int i = 0; i < gadgetModels.size(); i++) {
            mapGadgetModelColor.put(gadgetModels.get(i), gadgetColors.get(i));
        }
        gadgetPerMonthCountByModelLine.get(modelLine);
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
            String line = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
            String subModel = gadget.get(mapGadgetAttributeNumber.get(SUBMODEL));
            String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL));
            if (quality.equals(EST) && subModel.equals(mapGadgetModelSubmodel.get(modelLine).get(0))) {
                price = getPriceISPARK(getGadgetName(gadget), 0);
            }
            if (quality.equals(RST) && subModel.equals(mapGadgetModelSubmodel.get(modelLine).get(1))) {
                price = getPriceISPARK(getGadgetName(gadget), 1);
            }
            if (price > 0) {
                String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
                if (modelLine.contains("7") && color.toLowerCase().contains("red")) {
                    price = getIncreasedPrice(price) - 10;
                }
                bufferedWriter.write("<offer>\n" +
                        "<initialCategoryId>2</initialCategoryId>\n" +
                        "<typePrefix>Смартфон</typePrefix>\n" +
                        "<model>" + gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE)) + " " +
                        modelLine + " " + gadget.get(mapGadgetAttributeNumber.get(MEMORY)) + " " +
                        color + " " + subModel);
                String fingerPrint = gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT));
                if (!fingerPrint.isEmpty()) {
                    bufferedWriter.write(" " + fingerPrint);
                }
                bufferedWriter.write("</model>\n" +
                        "<vendor>" + gadget.get(mapGadgetAttributeNumber.get(VENDOR)) + "</vendor>\n" +
                        "<price>" + price + ".0</price>\n" +
                        "<description>" + mapGadgetModelDescription.get(modelLine) + "</description>\n" +
                        "<picture>" + getImageUrlPath(vendor, line, modelLine, color) + "</picture>\n" +
                        "</offer>\n");
            }
        }
        bufferedWriter.flush();
    }

    private String getDescriptionByModel(String modelBegin, String model) {
        Scanner inScanner = Solution.getInputScanner("Website/Spec/" + modelBegin + " " + model + ".txt");
        String res = inScanner.nextLine();
        while (inScanner.hasNextLine()) {
            res += "\n" + inScanner.nextLine();
        }
        inScanner.close();
        return res;
    }

    public String getImageUrlPath(String vendor, String modelLine, String model, String color) {
        return "https://raw.githubusercontent.com/bav735/EKZ/master/" + vendor + "/" + modelLine + "/" +
                model.replace(" ", "") + "/" + /*getLongColor(*/color/*)*/.replace(" ", "") + "/" +
                IMG_FILE_NAME + ".jpg";
    }

    /*private boolean notEnoughModel(ArrayList<String> gadget) {
        return gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("4") ||
                gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("4S") ||
                gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("5");
    }*/

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
                        if (prices.get(mapPriceAttributeNumber.get(RST_RETAIL_AMOLED)).equals(NO_PRICE) /*&&
                                !notEnoughModel(gadget)*/) {
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
//                if (!notEnoughModel(gadget)) {
                offer += "(совершенно новый)";
//                } else {
//                    offer += "(как новый)";
//                }
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
            return Solution.getNumber(mapGadgetNamePrices.get(gadgetName)
                    .get(mapPriceAttributeNumber.get(Gadgets.EST_RETAIL_ISPARK))) - 10;
        } else {
            return Solution.getNumber(mapGadgetNamePrices.get(gadgetName)
                    .get(mapPriceAttributeNumber.get(Gadgets.RST_RETAIL_ISPARK))) - 10;
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
//                if (notEnoughModel(gadget)) {
//                    price = Integer.parseInt(
//                            mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(EST_RETAIL_AMOLED)));
//                    price -= 10;
//                } else {
                price = Integer.parseInt(
                        mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(RST_RETAIL_AMOLED)));
//                }
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

    /*public String getXmlAd(int gadgetNum, int xmlDay) {
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
    }*/

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

    /*public void generateXML() {
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
    }*/

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
            int size = Math.min(mapGadgetModelGadgetPerMonthCount.get(model), mapGadgetModelGadgets.get(model).size());
            BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/AvitoRobot/" + model, "ads.csv");
            bufferedWriter.write(getRobotText(model, mapGadgetModelGadgets.get(model), gadgetNum, size));
            bufferedWriter.flush();
            gadgetNum += size;
//            c += Math.min(mapGadgetModelGadgetPerMonthCount.get(model), mapGadgetModelGadgets.get(model).baseSize());
        }
    }
}