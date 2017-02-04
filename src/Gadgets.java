import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Gadgets {
    final static String NAME_BEGIN = "Начало";
    final static String QUALITY = "Качество";
    final static String VENDOR = "Производитель";
    final static String MODEL_LINE = "Модельный ряд";
    final static String MODEL = "Модель";
    final static String MEMORY = "Память";
    final static String FINGER_PRINT = "Наличие отпечатка";
    final static String COLOR = "Цвет";
    final static String NAME_END = "Конец";
    //    final static String TEXT = "Текст";
//    final static String PRICE = "Цена";
    final static int DAYS_COUNT = 30;
    final static String[] CITIES = new String[]{"Казань", "Москва"};

    public static HashMap<String, Integer> mapNameGadgetAttributeNumber;
    public static HashSet<String> setNewGadgetModel;
    static String[] gadgetAttributeNames = new String[]{
            NAME_BEGIN,
            QUALITY,
            VENDOR,
            MODEL_LINE,
            MODEL,
            MEMORY,
            FINGER_PRINT,
            COLOR,
            NAME_END,
    };

    public ArrayList<ArrayList<String>> gadgets = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    ArrayList<String> pricesMonthWarranty;
    ArrayList<String> pricesYearWarranty;
    ArrayList<String> gadgetPriceListNames;
    String[] gadgetModelSequence;
    int hourStart = 10;
    int minuteInterval = 60;
    int cityId;
    String[] iphonesModelSequence = new String[]{
            " 5S 16Gb Б/О",
            " 6 Plus 64Gb Б/О",
            " 5S 32Gb",
            " 6 16Gb",
            " 5S 64Gb Б/О",
            " 6 Plus 64Gb",
            " 6S 16Gb",
            " 6 64Gb Б/О",
            " 5S 64Gb",
            " 6 Plus 16Gb",
            " 5S 32Gb Б/О",
            " 6 64Gb",
            " 5S 16Gb",
            " 6 Plus 16Gb Б/О",
            " 6S 64Gb",
            " 6 16Gb Б/О",
    };
    String[] galaxysModelSequence = new String[]{
//            " S3",
            " A3 (2015) 16Gb",
            " S6 Edge 32Gb",
            " S4 16Gb",
//            " A5 (2016)",
//            " S5",
//            " J5 (2016)",
//            " A7 (2016)",
//            " S4",
//            " S6 Edge",
//            " S4 Mini",
//            " Note 3",
//            " A5 (2015)",
//            " S6 Edge Plus"
    };
    static String[] newGadgetModelSequence = new String[]{
            " S4 Mini 16Gb",
            " Note 3 32Gb",
            " 6 Plus Б/О",
            " SE 16Gb",
            " A5 (2015) 16Gb",
            " S6 Edge Plus 32Gb",
            " 4S 64Gb",
            " 5 64Gb",
            " 5S 64Gb",
            " 6 128Gb"
    };

    public static void initialize() {
        mapNameGadgetAttributeNumber = new HashMap<String, Integer>();
        for (int i = 0; i < gadgetAttributeNames.length; i++) {
            mapNameGadgetAttributeNumber.put(gadgetAttributeNames[i], i);
        }
//        setNewGadgetModel = new HashSet<String>();
//        for (int i = 0; i < newGadgetModelSequence.length; i++) {
//            setNewGadgetModel.add(newGadgetModelSequence[i]);
//        }
    }

    public Gadgets() {
    }

    public void initializePrices(ArrayList<String> pricesMonthWarranty, ArrayList<String> pricesYearWarranty, ArrayList<String> nameParts) {
        this.pricesMonthWarranty = pricesMonthWarranty;
        this.pricesYearWarranty = pricesYearWarranty;
        this.gadgetPriceListNames = nameParts;
    }

    public void initializeIPhones(int cityId) {
        this.cityId = cityId;
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Новый Евротест")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" RFB")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" Apple")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" iPhone")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" 4", " 4S", " 5", " 5C", " 5S", " 6", " 6 Plus", " SE", " 6S", " 7")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" 8Gb", " 16Gb", " 32Gb", " 64Gb", " 128Gb")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("", " Б/О")));
//        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" Black", " White", " Gold", " Red", " Blue", " Green", " Pink", " Purple", " Yellow", " Orange", " Brown", " Olive", " Cyan", " Violet", " Teal", " Magenta", " Beige", " Salmon", " Plum", " Lime", " Coral", " Indigo", " Crimson", " Choco", " Azure", " Aqua", " Khaki", " Peach", " Maroon", " Sandy")));
        ArrayList<String> tList = new ArrayList<>();
//        for (int i = 30 * cityId; i < 30 * (cityId + 1); i++) {
//            tList.add(" A" + (1450 + i));
//        }
//        if (cityId == 0) {
//            for (int i = 0; i < 30; i++) {
//                tList.add(" A" + (1450 + i));
//            }
//        } else {
        for (int i = 60 * cityId; i < 30 + 90 * cityId; i++) {
            tList.add(" A" + (1450 + i));
        }
//        }
        gadgetAttributesVariants.add(tList);
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" Кредит")));
        gadgetModelSequence = iphonesModelSequence;
    }

    public void initializeGalaxys() {
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("Б/у")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" ES")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" Samsung")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" Galaxy")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" S3", " S4 Mini", " S4", " S5", " S6", " S6 Edge", " Note 4", " J5 (2016)", " S6 Edge Plus", " A3 (2015)", " A5 (2016)", " A7 (2016)", " Note 3", " A5 (2015)")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" 8Gb", " 16Gb", " 32Gb", " 64Gb", " 128Gb")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" Black", " White", " Gold", " Red", " Blue", " Green", " Pink", " Purple", " Yellow", " Orange", " Brown", " Olive", " Cyan", " Violet", " Teal", " Magenta", " Beige", " Salmon", " Plum", " Lime", " Coral", " Indigo", " Crimson", " Choco", " Azure", " Aqua", " Khaki", " Peach", " Maroon", " Sandy")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(" ")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("")));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList("")));
        gadgetModelSequence = galaxysModelSequence;
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            if (!getPrices(gadget).getKey().isEmpty() &&
                    (cityId != 1 || (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)) + " ").contains(" 6 "))) {
                gadgets.add(gadget);
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

    private String getAvitoAdName(ArrayList<String> gadget) {
        String name = gadget.get(mapNameGadgetAttributeNumber.get(NAME_BEGIN));
        String color = "";
        for (int i = mapNameGadgetAttributeNumber.get(MODEL_LINE); i <= mapNameGadgetAttributeNumber.get(NAME_END); i++) {
            String word = gadget.get(i);
/*            if (i == mapNameGadgetAttributeNumber.get(MODEL)) {
                switch (gadget.get(i)) {
                    case " 4S":
                        word = " 4S/5S";
                        break;
//                    case " 5":
//                        word = " 5/5S/4S/6";
//                        break;
                    case " 5C":
                        word = " 5C/5S";
                        break;
//                    case " 5S":
//                        word = " 5S/5/6";
//                        break;
//                    case " 6":
//                        word = " 6/5S/5";
//                        break;
                    case " 6S":
                        word = " 6S/7";
                        break;
//                    case " 6 Plus":
//                        word = " 6S/5S/6+";
//                        break;
//                    case " SE":
//                        word = " SE/5S/7";
//                        break;
//                    case " 7":
//                        word = " 7/SE/5S";
                    case " S6 Edge":
                        word = " S6 Edge/S7";
                        break;
                    case " S4":
                        word = " S4/S5/S3";
                        break;
                    case " A3 (2015)":
                        word = " A3/A5/A7";
                }
            }*/
            if (i != mapNameGadgetAttributeNumber.get(COLOR)) {
                name += word;
            } else {
                color = word;
            }
        }
        name += color;
//        if (name.length() < 43) {
//            name = name + " Кредит";
//        }
        return name;
    }

    private String getBriefName(ArrayList<String> gadget) {
        String name = "";
        for (int i = mapNameGadgetAttributeNumber.get(VENDOR); i <= mapNameGadgetAttributeNumber.get(FINGER_PRINT); i++) {
            name += gadget.get(i);
        }
        return name;
    }

    /*private void generateUsedText(ArrayList<String> gadget) {
        String priceMonthWarranty = "";
        String priceYearWarranty = "";
        String briefName = getBriefName(gadget);
        for (int i = 0; i < gadgetPriceListNames.size(); i++) {
            if (briefName.substring(1).equals(gadgetPriceListNames.get(i))) {
                priceMonthWarranty = pricesMonthWarranty.get(i);
                priceYearWarranty = pricesYearWarranty.get(i);
                break;
            }
        }
        if (priceMonthWarranty.isEmpty()) {
            return;
        }
        String color = gadget.get(mapNameGadgetAttributeNumber.get(COLOR));
        String text = "<![CDATA[";
        text += "<p>В хорошем состоянии (имеются небольшие царапины по корпусу) " *//*+ nameBegin*//* + briefName
                + " (" + color.substring(1).toLowerCase() + ") по цене от " + priceMonthWarranty + " руб.</p>";
        text = text.replace("Б/О", "без отпечатка");
        text += "]]>";
        gadget.set(mapNameGadgetAttributeNumber.get(TEXT), text);
        if (firstPrice.length() > 1) {
            gadget.set(mapNameGadgetAttributeNumber.get(PRICE), firstPrice);
        } else {
            gadget.set(mapNameGadgetAttributeNumber.get(PRICE), secondPrice);
        }
        gadgets.add(gadget);
    }*/

    private Pair<String, String> getPrices(ArrayList<String> gadget) {
        String priceMonthWarranty = "";
        String priceYearWarranty = "";
        String briefName = getBriefName(gadget);
        for (int i = 0; i < gadgetPriceListNames.size(); i++) {
            if (briefName.substring(1).equals(gadgetPriceListNames.get(i))) {
                priceMonthWarranty = pricesMonthWarranty.get(i);
                priceYearWarranty = pricesYearWarranty.get(i);
                break;
            }
        }
        return new Pair<>(priceMonthWarranty, priceYearWarranty);
    }

    private int getRoundedPrice(int price) {
        return (price / 500 + 1) * 500 - 10;
    }

//    private int getPriceByCity(String strPrice, String city) {
//        if (strPrice.length() <= 1) {
//            return 0;
//        }
//        int intPrice = Integer.parseInt(strPrice);
//        if (city.equals("Москва")) {
//            intPrice = getRoundedPrice(intPrice * 9 / 10);
//        }
//        return intPrice;
//    }

    private String getNewText(ArrayList<String> gadget, String city) {
        String color = gadget.get(mapNameGadgetAttributeNumber.get(COLOR));
        String text = "<![CDATA[";
        String briefName = getBriefName(gadget);
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
        int priceMonthWarranty = Integer.parseInt(getPrices(gadget).getKey());
        int priceYearWarranty = Integer.parseInt(getPrices(gadget).getValue());
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

    private void generateCompanyText(ArrayList<String> gadget) {
        String firstPrice = "";
        String secondPrice = "";
        String briefName = getBriefName(gadget);
        for (int i = 0; i < gadgetPriceListNames.size(); i++) {
            if (briefName.substring(1).equals(gadgetPriceListNames.get(i))) {
                firstPrice = pricesMonthWarranty.get(i);
                secondPrice = pricesYearWarranty.get(i);
                break;
            }
        }
        if (firstPrice.isEmpty()) {
            return;
        }
        String color = gadget.get(mapNameGadgetAttributeNumber.get(COLOR));
        String text = "<![CDATA[";
        text += "<p>Закупались в начале ноября для сотрудников компании," +
                " несколько штук оказались лишние, продаем по причине ненадобности.</p>" +
                "<p>Телефоны ";
        if (!gadget.get(mapNameGadgetAttributeNumber.get(FINGER_PRINT)).isEmpty()) {
            text += "абсолютно";
        } else {
            text += "совершенно";
        }
        text += " новые, оригинальные, запечатанные, в коробках, вскроете, сможете " +
                "полностью проверить.</p>" +
                "<p>Отдаем " + briefName.substring(7).replace(" Б/О", "") + " по цене " + firstPrice + ", выгода ваша почти " +
                (Integer.parseInt(secondPrice) - (Integer.parseInt(firstPrice)) + 10) + " руб, покупали за " +
                secondPrice + "! Осталось всего ";
        String s = "2 телефона черного цвета, один серебристый и один золотой";
        switch (gadget.get(mapNameGadgetAttributeNumber.get(MODEL))) {
            case " 4S":
                s = "2 телефона черного цвета";
                break;
            case " 6 Plus":
                s = "один телефон черного цвета и один золотой";
                break;
            case " 5C":
                s = "2 телефона белого цвета";
        }
        text += s + ". </p>";
        s = "Работает LTE, ";
        if (!gadget.get(mapNameGadgetAttributeNumber.get(FINGER_PRINT)).isEmpty()) {
            s += "не работает ";
        }
        s += "тач айди (отпечаток пальца), система iOS.";
        if (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("4S")) {
            s = "";
        }
        if (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).equals(" 5") ||
                gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).equals(" 5C")) {
            s = "LTE работает, система iOS. ";
        }
        text += "<p>" + s + " Подробные функции/характеристики смотрите в интернете (" +
                briefName.substring(1).toLowerCase() + color.toLowerCase() + ").</p>" +
                "<p>Все документы, чеки о покупке есть. На гарантии до ноября следующего года.</p>" +
                "<p>По всем вопросам звоните/пишите в любое время, есть и другие телефоны.</p>";
        text = text.replace("б/о", "без отпечатка");
        text += "<p>Прайс-лист, актуальный для Авито:<br>" +
                "Apple iPhone 4S 16Gb=6990<br>" +
                "Apple iPhone 4S 32Gb=7990<br>" +
                "Apple iPhone 5 16Gb=11490<br>" +
                "Apple iPhone 5 32Gb=12490<br>" +
                "Apple iPhone 5C 16Gb=10490<br>" +
                "Apple iPhone 5C 32Gb=11490<br>" +
                "Apple iPhone 5S 16Gb=15990<br>" +
                "Apple iPhone 5S 32Gb=16990<br>" +
                "Apple iPhone 6 16Gb=22990<br>" +
                "Apple iPhone 6 64Gb=24990<br>" +
                "Apple iPhone 6 Plus 16Gb=29990<br>" +
                "Apple iPhone 6 Plus 64Gb=32990<br>" +
                "Apple iPhone SE 16Gb=28990<br>" +
                "Apple iPhone 6S 16Gb=34990<br>" +
                "Apple iPhone 6S 64Gb=40990<br>" +
                "Apple iPhone 7 32Gb=51990</p>";
        text += "]]>";
        /*gadget.set(mapNameGadgetAttributeNumber.get(TEXT), text);
        if (firstPrice.length() > 1) {
            gadget.set(mapNameGadgetAttributeNumber.get(PRICE), firstPrice);
        } else {
            gadget.set(mapNameGadgetAttributeNumber.get(PRICE), secondPrice);
        }
        gadgets.add(gadget);*/
    }

//    private String getMetaModel(ArrayList<String> gadget) {
//        String metaModel = gadget.get(mapNameGadgetAttributeNumber.get(MODEL))+gadget.get(mapNameGadgetAttributeNumber.get(FINGER_PRINT));
//        if (metaModel.contains("4S") || metaModel.contains("5C") || metaModel.contains("6S")) {
//            return "4S5C6S";
//        }
//        if (metaModel.contains("5") || metaModel.contains("5S")) {
//            return "55S";
//        }
//        if (metaModel.contains("6 Plus") || metaModel.contains("6")) {
//            return "66+";
//        }
//        if (gadget.get(mapNameGadgetAttributeNumber.get(FINGER_PRINT)).contains("Б")) {
//
//        }
//        if (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("S3") || gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("S4")) {
//            metaModel = "S3S4";
//        }
//        if (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("J5") || gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("Note")) {
//            metaModel = "J5Note";
//        }
//        if (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("A3") || gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("A5")) {
//            metaModel = "A3A5";
//        }
//        if (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("S5") || gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("A7")) {
//            metaModel = "S5A7";
//        }
//        if (gadget.get(mapNameGadgetAttributeNumber.get(MODEL)).contains("S6")) {
//            metaModel = "S6";
//        }
//        return metaModel;
//    }

    private void shufleMemory() {
        LinkedHashMap<String, ArrayList<ArrayList<String>>> mapModelGadgets = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
        for (ArrayList<String> gadget : gadgets) {
            String modelName = gadget.get(mapNameGadgetAttributeNumber.get(MODEL)) +
                    gadget.get(mapNameGadgetAttributeNumber.get(FINGER_PRINT)) +
                    gadget.get(mapNameGadgetAttributeNumber.get(COLOR));
            if (!mapModelGadgets.containsKey(modelName)) {
                mapModelGadgets.put(modelName, new ArrayList<ArrayList<String>>());
            }
            mapModelGadgets.get(modelName).add(gadget);
        }
        gadgets.clear();
        for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : mapModelGadgets.entrySet()) {
            for (ArrayList<String> gadget : entry.getValue()) {
                gadgets.add(gadget);
            }
        }
    }

    private void shuffleModel(int shiftK) {
        LinkedHashMap<String, ArrayList<ArrayList<String>>> mapModelGadgets = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
        for (String modelName : gadgetModelSequence) {
            mapModelGadgets.put(modelName, new ArrayList<ArrayList<String>>());
        }
        for (ArrayList<String> gadget : gadgets) {
            String modelName = gadget.get(mapNameGadgetAttributeNumber.get(MODEL)) +
                    gadget.get(mapNameGadgetAttributeNumber.get(MEMORY)) +
                    gadget.get(mapNameGadgetAttributeNumber.get(FINGER_PRINT));
//            System.out.println(modelName);
            mapModelGadgets.get(modelName).add(gadget);
        }
        gadgets.clear();
        int maxSize = -1;
        for (String model : mapModelGadgets.keySet()) {
            maxSize = Math.max(maxSize, mapModelGadgets.get(model).size());
        }
        ArrayList<String> models = new ArrayList<String>();
        models.addAll(mapModelGadgets.keySet());
        for (int gadgetNum = 0; gadgetNum < maxSize; gadgetNum++) {
            for (int shift = 0; shift < models.size(); shift++) {
                String model = models.get(shift);
                int modelSize = mapModelGadgets.get(model).size();
                if (gadgetNum < modelSize) {
                    ArrayList<String> gadget = mapModelGadgets.get(model).get((gadgetNum + shift * shiftK) % modelSize);
                    gadgets.add(gadget);
                }
            }
        }
    }

    public void generateGadgetFiles() {
//        if (gadgetAttributesVariants.get(mapNameGadgetAttributeNumber.get(VENDOR)).contains("Apple")) {
        shufleMemory();
        shuffleModel(1);
//        } else {
//            shuffleModel(1);
//        }
//        LinkedHashMap<String, Integer> mapGadgetMetaModelCount = new LinkedHashMap<String, Integer>();
//        for (ArrayList<String> gadget : gadgets) {
//            int modelDayTime = 1;
//            String metaModel = getMetaModel(gadget);
//            if (mapGadgetMetaModelCount.containsKey(metaModel)) {
//                modelDayTime = mapGadgetMetaModelCount.get(metaModel) + 1;
//            }
//            mapGadgetMetaModelCount.put(metaModel, modelDayTime);
//        }
//        LinkedHashMap<String, Integer> mapGadgetMetaModelShift = new LinkedHashMap<String, Integer>();
//        int shift = 0;
//        for (String metaModel : mapGadgetMetaModelCount.keySet()) {
//            System.out.print(metaModel + " " + mapGadgetMetaModelCount.get(metaModel) + "\n");
//            mapGadgetMetaModelShift.put(metaModel, shift);
//            shift += 2;
//        }

//        HashMap<String, Integer> mapMetaModelCount = new HashMap<String, Integer>();
        for (int i = 0; i < gadgets.size(); i++) {
            System.out.println(getAvitoAdName(gadgets.get(i)));
            ArrayList<String> gadget = gadgets.get(i);
//            String metaModel = getMetaModel(gadget);
//            int gadgetNum = 0;
//            if (mapMetaModelCount.containsKey(metaModel)) {
//                gadgetNum = mapMetaModelCount.get(metaModel) + 1;
//            }
//            mapMetaModelCount.put(metaModel, gadgetNum);
//            createGadgetFile(gadget, gadgetNum, mapGadgetMetaModelCount.get(metaModel));
            createGadgetFile(gadget, i, gadgets.size());
        }
    }

    private void createGadgetFile(ArrayList<String> gadget, int gadgetNum, int metaModelSize) {
        int metaModelsPerDay = metaModelSize / DAYS_COUNT;
        int dayRawMinute = hourStart * 60 + gadgetNum % metaModelsPerDay * minuteInterval;
        int dayMinute = dayRawMinute % 60;
        int dayHour = dayRawMinute / 60;
        int dayNum = gadgetNum / metaModelsPerDay;
        File directory = new File("Готовые тексты");
        directory = new File(directory, "day" + (dayNum + 1));
        directory = new File(directory, dayHour + "-" + dayMinute);
        String gadgetFileName = getAvitoAdName(gadget) + ".txt";
        final File gadgetFile = new File(directory, gadgetFileName.replaceAll("[\\s/]", ""));
        gadgetFile.getParentFile().mkdirs();
        try {
            Path gadgetPath = Paths.get(gadgetFile.getCanonicalPath());
            OutputStream os = new BufferedOutputStream(Files.newOutputStream(gadgetPath));
            BufferedWriter outWriter = new BufferedWriter(new OutputStreamWriter(os));
//            outWriter.write(gadget.get(mapNameGadgetAttributeNumber.get(TEXT)));
            outWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        int price = Integer.parseInt(getPrices(gadget).getKey());
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