import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Gadgets {
    //    final static String NAME_BEGIN = "";
    final static String CERTIFICATION = "Сертификация";
    final static String VENDOR = "Производитель";
    final static String MODEL_LINE = "Модельный ряд";
    final static String MODEL = "Модель";
    final static String SUBMODEL = "Подмодель";
    final static String MEMORY = "Память";
    final static String FINGER_PRINT = "Наличие отпечатка";
    final static String COLOR = "Цвет";
    final static String TOUCH_LOCKED = "Б/О";
    final static String EST = "EST";
    final static String RST = "RST";
    final static int PRICES_COUNT = 7;
    final static String[] CITIES = new String[]{"Казань"};
    final static int DISTRIBUTION_SIZE = 5;
    final static int ADS_PER_DAY = 16;
    final static int ADS_PER_MONTH = 480;

    static HashMap<String, Integer> mapAttributeNumber;
    static String[] gadgetAttributeNames = new String[]{
            CERTIFICATION,
            VENDOR,
            MODEL_LINE,
            MODEL,
            MEMORY,
            FINGER_PRINT,
            SUBMODEL,
            COLOR
    };

    public ArrayList<ArrayList<String>> gadgets = new ArrayList<ArrayList<String>>();
    HashMap<String, ArrayList<String>> mapGadgetModelSubmodel;
    HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets;
    HashMap<String, ArrayList<String>> mapGadgetModelColor;
    HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    String[] iphonesModelSequence = new String[]{
            "6 Plus",
            "7 Plus",
            "6S Plus",
            "4",
            "4S",
            "5",
            "5C",
            "5S",
            "6",
            "7",
            "6S",
            "SE",
    };
    int[][] iphonesDistribution;

    public static void initialize() {
        mapAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < gadgetAttributeNames.length; i++) {
            mapAttributeNumber.put(gadgetAttributeNames[i], i);
        }
    }

    public Gadgets() {
    }

    public void initializePrices() {
        try {
            Scanner inScanner = new Scanner(new FileInputStream("input.txt"));
            mapGadgetNamePrices = new HashMap<>();
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine();
                String[] words = line.split(" ");
                if (!words[1].equals("iPhone")) {
                    continue;
                }
                int i = 0;
                String gadgetName = "Apple";
                do {
                    i++;
                    gadgetName += " " + words[i];
                } while (!words[i].contains("Gb"));
                if (words[i + 1].equals("Без")) {
                    gadgetName += " Б/О";
                }
                ArrayList<String> prices = new ArrayList<>();
                for (int priceId = PRICES_COUNT; priceId > 0; priceId--) {
                    prices.add(words[words.length - priceId]);
                }
                mapGadgetNamePrices.put(gadgetName, prices);
            }
            inScanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Exception: Input file not found!");
        }
    }

    public void initializeDistribution() {
        try {
            Scanner inScanner = new Scanner(new FileInputStream("distribution.txt"));
            iphonesDistribution = new int[iphonesModelSequence.length][DISTRIBUTION_SIZE];
            for (int modelId = 0; modelId < iphonesModelSequence.length; modelId++) {
                for (int groupId = 0; groupId < DISTRIBUTION_SIZE; groupId++) {
                    iphonesDistribution[modelId][groupId] = inScanner.nextInt();
                }
            }
            inScanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Exception: Input file not found!");
        }
    }

    public void initializeIPhones() {
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(EST, RST)));
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
        submodels.add(new ArrayList<String>(Arrays.asList("A1431", "A1387", "A1378")));
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
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Jet Black", "Gold", "Pink")));
        colors.add(new ArrayList<String>(Arrays.asList("Black", "White", "Jet Black", "Gold", "Pink")));
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
        initializePrices();
        initializeDistribution();
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            if (mapGadgetNamePrices.containsKey(getGadgetName(gadget))) {
                ArrayList<String> prices = mapGadgetNamePrices.get(getGadgetName(gadget));
                if (prices.get(prices.size() - 1).length() == 1 &&
                        gadget.get(mapAttributeNumber.get(CERTIFICATION)).equals(RST)) {
                    return;
                }
                String model = gadget.get(mapAttributeNumber.get(MODEL));
                for (String submodel : mapGadgetModelSubmodel.get(model)) {
                    for (String color : mapGadgetModelColor.get(model)) {
                        ArrayList<String> newGadget = new ArrayList<String>(gadget);
                        newGadget.add(mapAttributeNumber.get(SUBMODEL), submodel);
                        newGadget.add(mapAttributeNumber.get(COLOR), color);
                        gadgets.add(newGadget);
                        generateDirsPhotos(newGadget);
                        System.out.println(getAvitoAdName(newGadget));
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
            String model = gadget.get(mapAttributeNumber.get(MODEL));
            if (!mapGadgetModelGadgets.containsKey(model)) {
                mapGadgetModelGadgets.put(model, new ArrayList<ArrayList<String>>());
            }
            mapGadgetModelGadgets.get(model).add(gadget);
        }
//        for (String model : iphonesModelSequence) {
//            System.out.println(model + " = " + mapGadgetModelGadgets.get(model).size());
//        }
        gadgets.clear();
        int size = 60;
        for (int groupNum = 0; groupNum < size; groupNum++) {
            int groupId = 1;
            switch (groupNum % 5) {
                case 0:
                    groupId = 2;
                    break;
                case 2:
                case 4:
                    groupId = 0;
            }
            if (groupNum % 10 == 1) {
                if (groupNum / 10 % 2 == 0) {
                    groupId = 3;
                } else {
                    groupId = 4;
                }
            }
            ArrayList<ArrayList<String>> gadgetGroup = new ArrayList<>();
            for (int t = 0; t < ADS_PER_DAY; t++) {
                gadgetGroup.add(new ArrayList<String>());
            }
            for (int frequence = 3; frequence >= 0; frequence--) {
                for (int modelId = 0; modelId < iphonesModelSequence.length; modelId++) {
                    if (iphonesDistribution[modelId][groupId] == frequence) {
                        switch (frequence) {
                            case 3:
                                int i = 7;
                                while (!gadgetGroup.get(i).isEmpty()) {
                                    i++;
                                }
                                gadgetGroup.set(i, extractGadgetByModel(modelId));
                            case 2:
                                i = 0;
                                while (!gadgetGroup.get(i).isEmpty()) {
                                    i++;
                                }
                                gadgetGroup.set(i, extractGadgetByModel(modelId));
                            case 1:
                                i = 15;
                                while (!gadgetGroup.get(i).isEmpty()) {
                                    i--;
                                }
                                gadgetGroup.set(i, extractGadgetByModel(modelId));
                        }
                    }
                }
            }
            gadgets.addAll(gadgetGroup);
        }
    }

    private ArrayList<String> extractGadgetByModel(int modelId) {
        ArrayList<ArrayList<String>> gadgetsByModel = mapGadgetModelGadgets.get(iphonesModelSequence[modelId]);
        ArrayList<String> gadget = gadgetsByModel.get(gadgetsByModel.size() - 1);
        gadgetsByModel.remove(gadgetsByModel.size() - 1);
        mapGadgetModelGadgets.put(iphonesModelSequence[modelId], gadgetsByModel);
        return gadget;
    }

    public String getAvitoAdName(ArrayList<String> gadget) {
        String name = /*NAME_BEGIN + " " + */gadget.get(mapAttributeNumber.get(CERTIFICATION));
        int lastAttr = mapAttributeNumber.get(COLOR);
        for (int i = mapAttributeNumber.get(MODEL_LINE); i <= lastAttr; i++) {
            if (!gadget.get(i).isEmpty()) {
                name += " " + gadget.get(i);
            }
        }
        name = name + " Кредит 6 мес";
        if (name.length() < 42) {
            name += " Гарантия";
        }
        if (name.length() > 50) {
            name = name.substring(0, 50);
        }
        return name;
    }

    private String getGadgetName(ArrayList<String> gadget) {
        int lastAttr = mapAttributeNumber.get(FINGER_PRINT);
        int firstAttr = mapAttributeNumber.get(VENDOR);
        String name = gadget.get(firstAttr);
        for (int i = firstAttr + 1; i <= lastAttr; i++) {
            if (!gadget.get(i).isEmpty()) {
                name += " " + gadget.get(i);
            }
        }
        return name;
    }

    private String getPriceOfferByWarranty(String gadgetName, int months, int cityId) {
        String price = getPriceByWarranty(gadgetName, months, cityId);
        if (price.length() == 1) {
            return "";
        }
        return months + " мес гарантии = " + price + " руб<br>";
    }

    private String getWholesaleOffer(String gadgetName) {
        String price = mapGadgetNamePrices.get(gadgetName).get(PRICES_COUNT - 2);
        if (price.length() == 1) {
            return "";
        }
        return "Опт (от 6 шт) = " + price + " руб<br>";
    }

    private String getPriceByWarranty(String gadgetName, int months, int cityId) {
        int priceId = months % 4;
        if (cityId == 1 && priceId != 0) {
            priceId += 2;
        }
        return mapGadgetNamePrices.get(gadgetName).get(priceId);
    }

    private String getNewText(ArrayList<String> gadget, int cityId) {
        String color = gadget.get(mapAttributeNumber.get(COLOR));
        String text = "<![CDATA[";
        String gadgetName = getGadgetName(gadget);
        String city = CITIES[cityId];
        text += "<p>Уважаемый покупатель,<br>" +
                "Вас приветствует <strong>интернет-магазин iSPARK!</strong>";
        if (city.equals("Казань")) {
            text += "<br>(нажмите на иконку магазина, чтобы увидеть весь наш ассортимент)";
        }
        text += "</p><p><strong>Почему iSPARK?</strong><br>" +
                "1) Мы всегда идем навстречу нашим клиентам и дорожим своей репутацией.<br>" +
                "2) Мы предлагаем гибкие возможности вашей покупки:<br>" +
                "- Кредит от 6 до 36 мес под 2% в мес (условия см. на нашем сайте)<br>" +
                "- Трейд-ин (обмен вашего телефона на наш)<br>" +
                "- Доставка по РФ наложенным платежом через CDEK (~2 дня)<br>" +
                "- Опт (высылаем прайс-лист по запросу)<br>" +
                "3) Работаем на рынке электроники с 2009 года! Находимся в Казани и Москве";
        text += "</p><p>Рады радовать вас наличием новых <strong>" + gadgetName + "</strong> цвета <strong>" + color +
                "</strong> по очень приятным ценам:<strong>";
        if (gadget.get(mapAttributeNumber.get(CERTIFICATION)).equals(EST)) {
            text += "<br>";
            text += getPriceOfferByWarranty(gadgetName, 1, cityId);
            text += getPriceOfferByWarranty(gadgetName, 6, cityId);
            text += getPriceOfferByWarranty(gadgetName, 12, cityId);
            text += getWholesaleOffer(gadgetName);
            text += "</strong></p><p>";
            text += "- продукция Евротест, гарантия предоставляется СЦ iSPARK Сервис (г. Казань и Москва)<br>";
            text += "- гарантия полноценная (обмен/возврат в течение 14 дней, по истечению - бесплатное устранение неисправности)<br>";
        } else {
            text += " " + mapGadgetNamePrices.get(gadgetName).get(1 + 2 * CITIES.length) + " руб</strong>";
            text += "</p><p>";
            text += "- продукция Ростест, гарантия официальная, предоставляется Apple (на всей территории РФ)<br>";
        }
        String s = "";
        String os = "";
        if (gadget.get(mapAttributeNumber.get(VENDOR)).contains("Apple")) {
            os = "iOS";
            s = "App Store, iCloud/iTunes";
            if (gadget.get(mapAttributeNumber.get(FINGER_PRINT)).isEmpty() &&
                    gadgetName.contains("iPhone 5S") ||
                    gadgetName.contains("iPhone 6") ||
                    gadgetName.contains("iPhone 6S")) {
                s += ", работает TouchID (отпечаток пальца)";
            }
        } else {
            os = "Android";
            s = "Play Market";
        }
        text += "- успешно обновляются, регистрируются в официальном магазине приложений " + s + "<br>" +
                "- к каждому аппарату предоставляется полный комплект аксессуаров: коробка, з/у, кабель, аудио-гарнитура, инструкции<br>" +
                "- полностью запечатаны в фабричные пленки, без следов эксплуатации, вскрытие и проверка происходят при вас</p>" +
                "<p><strong>Условия покупки:</strong><br>" +
                "- самовывоз, бесплатно<br>" +
                "- быстрая доставка ";
        if (city.equals("Казань")) {
            text += "200 руб<br>";
        } else {
            text += "350 руб<br>";
        }
        text += "- оплата осуществляется только по факту полной проверки товара<br>" +
                "- при покупке выдается чек и гарантийный талон<br>" +
                "- пожалуйста предварительно резервируйте интересующий вас товар</p>" +
                "<p><strong>Режим работы:</strong><br>" +
                "Прием звонков: 9.00-21.00, ежедневно<br>" +
                "(заказы на нашем сайте можно оставлять круглосуточно)</p>";
        if (city.equals("Казань")) {
            text += "<p><strong>Местоположение iSPARK в Казани</strong> (о нас знают 2GIS, Google Maps, Яндекс.Карты):<br>" +
                    "СЦ iSPARK Сервис - ул. Спартаковская, д. 2, к. 1 (+пункт выдачи заказов)<br>" +
                    "- время работы: 11.00-19.00<br>" +
                    "iSPARK Магазин - ул. Лушникова, д. 8<br>" +
                    "- время работы: 17.00-21.00</p>";
        } else {
            text += "<p><strong>Местоположение iSPARK в Москве:</strong><br>" +
                    "iSPARK Магазин - ул. Молодежная, д. 4, офис 3 (пункт выдачи заказов)</p>";
        }
        text += "<p>Будем рады видеть вас в числе наших постоянных клиентов!<br>" +
                "С уважением,<br>" +
                "<strong>Ваш iSPARK<strong/></p>";
        text = text.replace(TOUCH_LOCKED, "без отпечатка");
        text += "]]>";
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

    public String getXmlAd(int gadgetNum, int xmlDay, int cityId) {
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
        int offset = 0;
        if (divideDay <= offset) {
            int tDivideDay = divideDay + 30 - offset;
            if (xmlDay >= tDivideDay) {
                calendarZero.add(Calendar.DAY_OF_MONTH, -30);
            }
        } else {
            if (xmlDay < divideDay - offset) {
                calendarZero.add(Calendar.DAY_OF_MONTH, 30);
            }
        }
        String dateBegin = getDateByCalendar(calendarZero);
        ad += "\t\t<Id>" + gadgetNum + "</Id>\n";
        ad += "\t\t<DateBegin>" + dateBegin + "</DateBegin>\n";
        ad += "\t\t<AllowEmail>Да</AllowEmail>\n";
        ad += "\t\t<ManagerName>Оператор-консультант</ManagerName>\n";
        ad += "\t\t<ContactPhone>89991557000</ContactPhone>\n";
        String city = CITIES[cityId];
        if (city.equals("Казань")) {
            ad += "\t\t<Region>Татарстан</Region>\n";
        }
        ad += "\t\t<City>" + city + "</City>\n";
        ad += "\t\t<Category>Телефоны</Category>\n";
        String goodsType = "";
        if (gadget.get(mapAttributeNumber.get(Gadgets.VENDOR)).contains("Apple")) {
            goodsType = "iPhone";
        } else {
            goodsType = "Samsung";
        }
        ad += "\t\t<GoodsType>" + goodsType + "</GoodsType>\n";
        ad += "\t\t<Title>" + getAvitoAdName(gadget) + "</Title>\n";
        ad += "\t\t<Description>" + getNewText(gadget, cityId) + "</Description>\n";
        String price;
        String gadgetName = getGadgetName(gadget);
        if (gadget.get(mapAttributeNumber.get(CERTIFICATION)).equals(EST)) {
            price = getPriceByWarranty(gadgetName, 1, cityId);
            if (price.length() == 1) {
                price = getPriceByWarranty(gadgetName, 12, cityId);
            }
        } else {
            price = mapGadgetNamePrices.get(gadgetName).get(PRICES_COUNT - 1);
        }
        ad += "\t\t<Price>" + price + "</Price>\n";
        ad += "\t\t<Images>\n";
        String imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/" +
                getGadgetPath(gadget, SUBMODEL) + "img.jpg";
        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
//        imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/price_iphone.png";
//        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
        ad += "\t\t</Images>\n";
        ad += "\t</Ad>\n";
        return ad;
    }

    private String getGadgetPath(ArrayList<String> gadget, String lastAttr) {
        String path = "";
        for (int i = mapAttributeNumber.get(VENDOR); i <= mapAttributeNumber.get(lastAttr); i++) {
            String attr = gadget.get(i);
            if (i == mapAttributeNumber.get(FINGER_PRINT)) {
                if (attr.isEmpty()) {
                    attr = "СО";
                } else {
                    attr = "БО";
                }
            }
            path += attr + "/";
            if (i == mapAttributeNumber.get(MODEL)) {
                path += gadget.get(mapAttributeNumber.get(COLOR)) + "/";
            }
        }
        return path;
    }

    public void generateDirsPhotos(ArrayList<String> gadget) {
        String rootDir = "C:/AMOLED/";
        File gadgetDirImg = new File(rootDir + getGadgetPath(gadget, SUBMODEL));
        gadgetDirImg.mkdirs();
        gadgetDirImg = new File(gadgetDirImg, "img.jpg");
        File gadgetImg = new File(rootDir + getGadgetPath(gadget, MODEL) + "img.jpg");
        try {
            Files.copy(gadgetImg.toPath(), gadgetDirImg.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private void deleteFile(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                deleteFile(c);
        }
        if (!f.delete())
            throw new FileNotFoundException("Failed to deleteFile file: " + f);
    }
}