import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class Gadgets {
    final static String NAME_BEGIN = "Новый";
    final static String QUALITY = "Качество";
    final static String VENDOR = "Производитель";
    final static String MODEL_LINE = "Модельный ряд";
    final static String MODEL = "Модель";
    final static String SUBMODEL = "Подмодель";
    final static String MEMORY = "Память";
    final static String FINGER_PRINT = "Наличие отпечатка";
    final static String COLOR = "Цвет";

    public static HashMap<String, Integer> mapNameGadgetAttributeNumber;
    static String[] gadgetAttributeNames = new String[]{
            QUALITY,
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
    HashMap<String, ArrayList<String>> mapGadgetModelColor;
    HashMap<String, ArrayList<String>> mapGadgetNamePrices;
    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    ArrayList<String> prices;
    ArrayList<String> gadgetPriceListNames;
    String[] gadgetModelSequence;
    int hourStart = 10;
    int minuteInterval = 60;
    int cityId;

    public static void initialize() {
        mapNameGadgetAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < gadgetAttributeNames.length; i++) {
            mapNameGadgetAttributeNumber.put(gadgetAttributeNames[i], i);
        }
    }

    public Gadgets() {
    }

    public void initializePrices(HashMap<String, ArrayList<String>> mapGadgetNamePrices) {
        this.mapGadgetNamePrices = mapGadgetNamePrices;
        System.out.println(mapGadgetNamePrices.toString());
    }

    public void initializeIPhones() {
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Евротест", "Ростест")));
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
        submodels.add(new ArrayList<String>(Arrays.asList("A1431", "A1387", "A1387")));
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
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("", "Б/О")));
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            if (mapGadgetNamePrices.containsKey(getGadgetName(gadget))) {
                ArrayList<String> prices = mapGadgetNamePrices.get(getGadgetName(gadget));
                if (prices.get(prices.size() - 1).length() == 1 &&
                        gadget.get(mapNameGadgetAttributeNumber.get(QUALITY)).equals("Ростест")) {
                    return;
                }
                String model = gadget.get(mapNameGadgetAttributeNumber.get(MODEL));
                for (String submodel : mapGadgetModelSubmodel.get(model)) {
                    for (String color : mapGadgetModelColor.get(model)) {
                        ArrayList<String> newGadget = new ArrayList<String>(gadget);
                        newGadget.add(mapNameGadgetAttributeNumber.get(SUBMODEL), submodel);
                        newGadget.add(mapNameGadgetAttributeNumber.get(COLOR), color);
                        gadgets.add(newGadget);
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

    public String getAvitoAdName(ArrayList<String> gadget) {
        String name = NAME_BEGIN + " " + gadget.get(mapNameGadgetAttributeNumber.get(QUALITY));
        int lastAttr = mapNameGadgetAttributeNumber.get(COLOR);
        for (int i = mapNameGadgetAttributeNumber.get(MODEL_LINE); i <= lastAttr; i++) {
            if (!gadget.get(i).isEmpty()) {
                name += " " + gadget.get(i);
            }
        }
//        if (name.length() < 43) {
//            name = name + " Кредит";
//        }
        return name;
    }

    private String getGadgetName(ArrayList<String> gadget) {
        int lastAttr = mapNameGadgetAttributeNumber.get(FINGER_PRINT);
        int firstAttr = mapNameGadgetAttributeNumber.get(VENDOR);
        String name = gadget.get(firstAttr);
        for (int i = firstAttr + 1; i <= lastAttr; i++) {
            if (!gadget.get(i).isEmpty()) {
                name += " " + gadget.get(i);
            }
        }
        return name;
    }

    private int getRoundedPrice(int price) {
        return (price / 500 + 1) * 500 - 10;
    }

    private String getNewText(ArrayList<String> gadget, String city) {
        String color = gadget.get(mapNameGadgetAttributeNumber.get(COLOR));
        String text = "<![CDATA[";
        String briefName = getGadgetName(gadget);
        text += "<p>Дорогие покупатели, благодарим вас за выбор интернет-магазина цифровой электроники iSPARK!</p>" +
                "<p><strong>Почему iSPARK?</strong><br>" +
                "1) Мы всегда идем навстречу нашим клиентам и дорожим своей репутацией.<br>" +
                "2) Мы предлагаем гибкие возможности вашей покупки: КРЕДИТ. ТРЕЙД-ИН. ДОСТАВКА ПО РФ. ОПТ.<br>" +
                "3) Работаем на рынке электроники с 2009 года!";
        if (city.equals("Казань")) {
            text += " Опыт работы в Казани - более 2-х лет.";
        }
        text += "</p><p><strong>Рады предложить новый" + briefName + "</strong>"
                + " (модель" + color + "), цены актуальны только для Авито:<br>";
        String s = "";
        int priceMonthWarranty = 0;
        int priceYearWarranty = 0;
        text += "1 мес гарантии = " + priceMonthWarranty + " руб<br>";
        text += "6 мес гарантии = " + getRoundedPrice((priceMonthWarranty + priceYearWarranty) / 2) + " руб<br>";
        text += "1 год гарантии = " + priceYearWarranty + " руб<br>";
        text += "- гарантия полноценная, действует с момента покупки, предоставляется СЦ iSPARK Сервис</p>" +
                "<p>Всегда в наличии/под заказ все цвета и объемы памяти. В продаже также имеются другие телефоны. <strong>Уточняйте наличие интересующей модели телефона!</strong></p>";
        String store = "";
        String os = "";
        if (gadget.get(mapNameGadgetAttributeNumber.get(VENDOR)).contains("Apple")) {
            os = "iOS";
            store = "App Store";
        } else {
            os = "Android";
            store = "Play Market";
        }
        s = "- телефоны оригинальные, работают со всеми операторами связи";
        if (!gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).equals(" 4S")) {
            s += ", работает 4G/LTE";
            if (!gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).equals(" 5") &&
                    !gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).equals(" 5C") &&
                    gadget.get(mapNameGadgetAttributeNumber.get(FINGER_PRINT)).isEmpty()) {
                s += ", TouchID (сканер отпечатка пальца)";
            }
            if (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).equals(" 6S")) {
                s += ", 3D Touch (технология сильного нажатия)";
            }
        }
        s += "<br>";
        text += "<p>" + s + "- система " + os + ", успешно обновляются, активируются, регистрируются в магазине приложений " + store + "<br>" +
                "- к каждому аппарату предоставляется полный фабричный комплект аксессуаров: коробка, з/у, кабель, аудио-гарнитура, инструкции<br>" +
                "- новые, Евротест (США), полностью запечатаны в фабричные пленки, вскрытие и проверка при вас</p>" +
                "<p><strong>Условия покупки:</strong><br>" +
                "- самовывоз, бесплатно<br>" +
                "- быстрая доставка, 200 руб<br>" +
                "- оплата осуществляется только по факту полной проверки телефона!<br>" +
                "- при покупке выдается чек и гарантийный талон<br></p>" +
                "<p><strong>Режим работы (обслуживание по телефону): 9.00-21.00, ежедневно</strong></p>";
        if (city.equals("Казань")) {
            text += "<p><strong>Местоположение iSPARK в Казани</strong> (о нас знают 2GIS, Google Maps, Яндекс.Карты):<br>" +
                    "iSPARK Магазин (пункт выдачи заказов) - ул. Спартаковская, д. 2, к. 1<br>" +
                    "СЦ iSPARK Сервис - ул. Спартаковская, д. 2, к. 1</p>";
        } else {
            text += "<p><strong>Местоположение iSPARK в Москве:</strong><br>" +
                    "iSPARK - ул. Молодежная, д. 4, офис 3</p>";
            //Магазин (пункт выдачи заказов)
        }
        text += "<p>Будем рады видеть вас в числе наших постоянных клиентов!<br>" +
                "С уважением,<br>" +
                "<strong>Ваш iSPARK<strong/></p>";
        text = text.replace("Б/О", "без отпечатка");
        text += "]]>";
        return text;
    }

    private String getDateByCalendar(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "-" +
                convertToTwoDigit(calendar.get(Calendar.MONTH)) + "-" +
                convertToTwoDigit(calendar.get(Calendar.DAY_OF_MONTH));
    }

    private String convertToTwoDigit(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return "" + num;
    }

    public String getXmlAd(int gadgetNum, int xmlDay, String city) {
        ArrayList<String> gadget = gadgets.get(gadgetNum);
        String ad = "\t<Ad>\n";
        String id = "";
        for (int i = mapNameGadgetAttributeNumber.get(Gadgets.VENDOR); i <= mapNameGadgetAttributeNumber.get(Gadgets.COLOR); i++) {
            id += gadget.get(i);
        }
        id = id.substring(1);
        Calendar calendarZero = Calendar.getInstance();
        calendarZero.set(Calendar.YEAR, 2017);
        calendarZero.set(Calendar.MONTH, 0);
        calendarZero.set(Calendar.DAY_OF_MONTH, 1);
        Calendar calendarCurr = Calendar.getInstance();
        long seconds = (calendarCurr.getTimeInMillis() - calendarZero.getTimeInMillis()) / 1000;
        int dayNum = (int) (seconds / 3600 / 24) + 1;
        System.out.println(dayNum);
        int divideDay = calendarCurr.get(Calendar.DAY_OF_MONTH);
//        int monthmul = days / 30;
        String dateBegin = getDateByCalendar(calendarCurr);
        if (xmlDay < divideDay) {
            calendarCurr.add(Calendar.DAY_OF_MONTH, 30);
            dateBegin = getDateByCalendar(calendarCurr);
        }
        ad += "\t\t<Id>" + id + "</Id>\n";
        ad += "\t\t<DateBegin>" + dateBegin + "</DateBegin>\n";
        ad += "\t\t<AllowEmail>Да</AllowEmail>\n";
        ad += "\t\t<ManagerName>Оператор-консультант</ManagerName>\n";
        ad += "\t\t<ContactPhone>89991557000</ContactPhone>\n";
        if (city.equals("Казань")) {
            ad += "\t\t<Region>Татарстан</Region>\n";
        }
        ad += "\t\t<City>" + city + "</City>\n";
        ad += "\t\t<Category>Телефоны</Category>\n";
        String goodsType = "";
        if (gadget.get(mapNameGadgetAttributeNumber.get(Gadgets.VENDOR)).contains("Apple")) {
            goodsType = "iPhone";
        } else {
            goodsType = "Samsung";
        }
        ad += "\t\t<GoodsType>" + goodsType + "</GoodsType>\n";
        ad += "\t\t<Title>" + getAvitoAdName(gadget) + "</Title>\n";
        ad += "\t\t<Description>" + getNewText(gadget, city) + "</Description>\n";
        int price = 0;
        /*if (price == 0) {
            price = getPriceByCity(getPrices(gadget).getValue(), city);
        }*/
        ad += "\t\t<Price>" + price + "</Price>\n";
        ad += "\t\t<Images>\n";
        String imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/" + gadget.get(
                mapNameGadgetAttributeNumber.get(MODEL_LINE)).substring(1) + "/" + gadget.get(
                mapNameGadgetAttributeNumber.get(MODEL)).substring(1) + gadget.get(
                mapNameGadgetAttributeNumber.get(MEMORY)) + gadget.get(
                mapNameGadgetAttributeNumber.get(FINGER_PRINT)).replaceAll("/", "") + "/" + gadget.get(
                mapNameGadgetAttributeNumber.get(COLOR)).substring(1) + ".jpg";
        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
        imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/price_iphone.png";
        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
        ad += "\t\t</Images>\n";
        ad += "\t</Ad>\n";
        return ad;
    }
}