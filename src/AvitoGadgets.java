import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class AvitoGadgets extends Gadgets {
//    static int tSize = 11266;

    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    HashSet<String> includeAds;
    int globalModelLine;

    public AvitoGadgets(int globalModelLine) {
        this.globalModelLine = globalModelLine;
    }

    public void initialize() {
//        initializeIncludeAds();
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(GadgetConst.QUALITIES));
        gadgetAttributesVariants.add(new ArrayList<String>(GadgetConst.VENDORS));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(
                GadgetConst.MODEL_LINES.get(globalModelLine))));
        gadgetAttributesVariants.add(GadgetConst.MODELS[globalModelLine]);
        gadgetAttributesVariants.add(new ArrayList<>(GadgetConst.MEMORIES));
    }

    /*private void initializeIncludeAds() {
        includeAds = new HashSet<>();
        Scanner inScanner = Solution.getInputScanner("include_ads.txt");
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (line.startsWith("Новый") || line.startsWith("iPhone") || line.startsWith("Galaxy")) {
                includeAds.add(line.substring(0, line.lastIndexOf(" ")));
            }
        }
        inScanner.close();
    }*/

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
        if (attribute == gadgetAttributesVariants.maxId()) {
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
                            if (prices.get(Gadgets.mapPriceAttributeNumber.get(Gadgets.RETAIL_MIN)).length() == 1
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
            int attributeVariantsSize = gadgetAttributesVariants.get(attribute).maxId();
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

    /*public void printWebsiteYML(BufferedWriter bufferedWriter) throws IOException {
        for (ArrayList<String> gadget : gadgets) {
            int price = -1;
            String quality = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
            String vendor = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
            String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
            String subModel = gadget.get(mapGadgetAttributeNumber.get(SUBMODEL));
            String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
            String fingerPrint = gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT));
            String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
            System.out.println(getGadgetName(gadget));
            if (quality.equals(RFB) && subModel.equals(GadgetConst.MAP_MODEL_SUBMODEL[globalModelLine].get(model).get(0))) {
                price = getPriceRetailMax(getGadgetName(gadget), 0);
            }
            if (quality.equals(NEW) && subModel.equals(GadgetConst.MAP_MODEL_SUBMODEL[globalModelLine].get(model).get(1))) {
                price = getPriceRetailMax(getGadgetName(gadget), 1);
            }
            if (price > 0) {
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
                        "<picture>" + getImageUrl(gadget) + "</picture>\n" +
                        "</offer>\n");
            }
        }
        bufferedWriter.flush();
    }*/

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

    public static String getWebsitePath(ArrayList<String> gadget) {
        String vendor = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
        String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
        String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        return vendor + "/" + modelLine + "/" + model.replace(" ", "") + "/" + color.replace(" ", "")
                + "/" + IMG_FILE_NAME + ".jpg";
    }

    public static String getAvitoPath(ArrayList<String> gadget) {
        String vendor = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
        String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
        String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        String res = vendor + "/" + modelLine + "/" + model.replace(" ", "") + "/" + color.replace(" ", "");
        String quality = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
        if (quality.equals("CPO")) {
            res += "/CPO";
        }
        res += "/" + IMG_FILE_NAME + ".jpg";
        return res;
    }

    public static String getImageWebsiteUrl(ArrayList<String> gadget) {
        return "https://raw.githubusercontent.com/bav735/iSPARK/master/images/" + getWebsitePath(gadget);
    }

    public static String getImageAvitoUrl(ArrayList<String> gadget) {
        return "https://raw.githubusercontent.com/bav735/iSPARK/master/images_avito_actual/" + getFullPath(gadget);
    }

    private boolean notEnoughModel(ArrayList<String> gadget) {
        return false;
//        return gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("4") ||
//                gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("4S") ||
//                gadget.get(mapGadgetAttributeNumber.get(MODEL)).equals("5");
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
//            System.out.println(getGadgetName(gadget));
            if (mapGadgetNamePrices.containsKey(getGadgetName(gadget))) {
                String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
                for (String submodel : GadgetConst.MAP_MODEL_SUBMODEL[globalModelLine].get(model)) {
                    for (String color : GadgetConst.MAP_MODEL_COLOR[globalModelLine].get(model)) {
                        ArrayList<String> newGadget1 = new ArrayList<>(gadget);
                        newGadget1.add(submodel);
                        newGadget1.add(color);
                        newGadget1.add("");
                        if (!excludeModel(model, color, newGadget1.get(mapGadgetAttributeNumber.get(MEMORY)))) {
                            gadgets.add(newGadget1);
//                            generatePhotos(newGadget1);
                            if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals("Apple")) {
                                ArrayList<String> newGadget2 = new ArrayList<>(newGadget1);
                                newGadget2.set(mapGadgetAttributeNumber.get(FINGER_PRINT), TOUCH_LOCKED);
                                if (mapGadgetNamePrices.containsKey(getGadgetName(newGadget2))) {
                                    gadgets.add(newGadget2);
//                                    generatePhotos(newGadget2);
                                } else {
                                    newGadget1.set(mapGadgetAttributeNumber.get(FINGER_PRINT), TOUCH_APPLE_NO);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            int attributeVariantsSize = gadgetAttributesVariants.get(attribute).size();
            for (int attributeVariant = 0; attributeVariant < attributeVariantsSize; attributeVariant++) {
                ArrayList<String> newGadget = new ArrayList<String>(gadget);
//                System.out.println(gadgetAttributesVariants.get(attribute).get(attributeVariant));
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
        ArrayList<String> gadget = gadgetsByModel.get(gadgetsByModel.maxId() - 1);
        gadgetsByModel.remove(gadgetsByModel.maxId() - 1);
        mapGadgetModelGadgets.put(model, gadgetsByModel);
        return gadget;
    }*/

    public String getIdName(ArrayList<String> gadget) {
        return String.join("", gadget.subList(mapGadgetAttributeNumber.get(QUALITY),
                mapGadgetAttributeNumber.get(FINGER_PRINT) + 1)).replaceAll("[() -]", "");
    }

    public String getAvitoAdName(ArrayList<String> gadget) {
        String name = GadgetConst.MAP_QUALITY_AD_NAME
                .get(gadget.get(mapGadgetAttributeNumber.get(QUALITY))) + " ";
//        if (!gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(EST2)) {
//            name += "Новый ";
//        }
        name += String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(MODEL_LINE),
                mapGadgetAttributeNumber.get(COLOR) + 1)).replace("  ", " ").replace("  ", " ");
//        if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(EST2)) {
//            name += " Реф";
//        }
        int lastAttr = mapGadgetAttributeNumber.get(FINGER_PRINT);
        if (gadget.get(lastAttr).length() > 1) {
            name += " " + gadget.get(lastAttr);
        }
        name += " " + gadget.get(mapGadgetAttributeNumber.get(QUALITY)) + " Магазин";
        return name;
    }

    public static String getGadgetName(ArrayList<String> gadget) {
        String name = String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(QUALITY),
                mapGadgetAttributeNumber.get(MEMORY) + 1));
        int lastAttr = mapGadgetAttributeNumber.get(FINGER_PRINT);
        if (gadget.size() > lastAttr && gadget.get(lastAttr).length() > 1) {
            name += " " + gadget.get(lastAttr);
        }
        return name;
    }

    private String getMinOptPriceAmoled(ArrayList<String> gadget) {
        return mapGadgetNamePrices.get(getGadgetName(gadget)).get(
                mapPriceAttributeNumber.get(OPT_MIN));
    }

    private String getCreditOffer(ArrayList<String> gadget) {
        return "- в кредит на 6 мес = от " + getCreditPrice(gadget) + "₽ в мес<br>";
    }

    private String getOffer(ArrayList<String> gadget, int cityId) {
        String offer = "<p>➡";
        offer += String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(VENDOR),
                mapGadgetAttributeNumber.get(COLOR) + 1));
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals("Apple") &&
                gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT)).length() != 1) {
            if (gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT)).isEmpty()) {
                offer += " TouchID работает ";
            } else {
                offer += " TouchID не работает ";
            }
        }
        offer += " = " + getPriceByCity(getGadgetName(gadget), cityId) +
                "\u20BD (" + GadgetConst.MAP_QUALITY_DESCRIPTION
                .get(gadget.get(mapGadgetAttributeNumber.get(QUALITY))) + ")";
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals("Samsung")) {
            String submodelEnding = gadget.get(mapGadgetAttributeNumber.get(SUBMODEL));
            submodelEnding = submodelEnding.substring(submodelEnding.length() - 1, submodelEnding.length());
//            System.out.println("submodelEnding=" + submodelEnding);
            offer += "<br>-модель для " + GadgetConst.MAP_SAMSUNG_SUB_MODEL_ENDING_DESCRIPTION
                    .get(submodelEnding);
        }
        offer += "<br>= " + getPrice(getGadgetName(gadget), OPT_MAX) + "\u20BD от 3 шт, = " +
                getMinOptPriceAmoled(gadget) + "\u20BD от 10шт \uD83D\uDCA3</p>";
        return offer;
    }

    private int getCreditPrice(ArrayList<String> gadget) {
        int creditPrice = Integer.parseInt(getPrice(getGadgetName(gadget), RETAIL_MIN)) * 112 / 600;
        return (creditPrice / 50 + 1) * 50;
    }

    public static int getPriceRetailMax(String gadgetName) {
        return Integer.parseInt(mapGadgetNamePrices.get(gadgetName)
                .get(mapPriceAttributeNumber.get(RETAIL_MAX)));
    }

    public static boolean inPriceList(String gadgetName) {
        return mapGadgetNamePrices.containsKey(gadgetName);
    }

    /*private String getPriceRetailMin(ArrayList<String> gadget) {
//        System.out.println(getGadgetName(gadget));
        return Integer.parseInt(mapGadgetNamePrices.get(getGadgetName(gadget))
                .get(mapPriceAttributeNumber.get(RETAIL_MIN))) + "";
    }

    private String getPriceOptMax(ArrayList<String> gadget) {
//        System.out.println(getGadgetName(gadget));
        return Integer.parseInt(mapGadgetNamePrices.get(getGadgetName(gadget))
                .get(mapPriceAttributeNumber.get(OPT_MAX))) + "";
    }*/


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
                "5) ДОСТАВКА ПО РФ в течение (1-2 дня)\n" +
                "Мы занимаемся продажей цифровой электроники с 2009 года.\n\n";
        if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).contains("Apple")) {
            text += "В нашем ассортименте оригинальный айфоны 4/4s/5/5c/5s/6/6s/se/7/plus всех цветов и объемов памяти" +
                    " по лучшей цене в Казани!\n\n";
        } else {
            text += "В нашем ассортименте оригинальный самсунг галакси s3/s4/s5/s6/s7/s8 edge/plus/alpha," +
                    " a3/a5/a7/j1/j2/j3/j5/j7 2015/2015/2017, note 3/4/5 всех цветов и объемов памяти" +
                    " по лучшей цене в Казани!\n\n";
        }
        text += getOffer(gadget, 0);
        text += "\n- цена действует при оплате полной стоимости товара наличными\n";
        text += "- выдаем документы о покупке: товарный чек и гарантийный талон\n";
        text += "- товар в пленке, неоф. восстановленный, есть микроцарапины\n";
        text += "- количество товара ограничено, уточняйте актуальное наличие\n\n";
//        text += "Местоположение iSPARK, см. в Яндекс.Картах, 2ГИС, Google Maps \uD83C\uDF0D\n" +
//                "▶ г. Казань, ул. Лушникова, д. 8, оф. 5 время работы (пн-сб): 13.00-21.00 ⏰\n\n";
        text += "-> Звоните: 10.00-20.00, ежедневно\n\n" +
//                "(заказы на нашем сайте можно оставлять круглосуточно)\n\n" +
                "У нас вы сможете наиболее выгодно и дешево купить интересующий вас гаджет или аксессуар!\n" +
//                "С уважением\n" +
                "Магазин AMOLED";
        text = text.replace(TOUCH_LOCKED, "без отпечатка");
        return text;
    }

    private String getAdTextAvitoShop(ArrayList<String> gadget, int cityId) {
        String text = "<![CDATA[";
        text += "<p>Уважаемый покупатель,<br>" +
                "Добро пожаловать в iSPARK\uD83D\uDD25";
        if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(GadgetConst.REF)) {
            text += "Дискаунтер";
        } else {
            text += "Электроникс";
        }
        if (cityId == 0) {
            text += "</p><p>\uD83C\uDF41ОСЕННИЙ ЦЕНОПАД, до конца недели продаем в розницу по оптовым ценам (только для Авито)❗</p>";
            text += "<p>\uD83D\uDC9BМы всегда идем навстречу нашим покупателям.<br>" +
                    "\uD83D\uDC49Мы предлагаем вам:<br>" +
//                    "\uD83D\uDD39 КРЕДИТ от ОТП Банк/Хоум-Кредит<br>" +
                    "\uD83D\uDD39 ТРЕЙД-ИН, ОБМЕН старого телефона<br>" +
                    "\uD83D\uDD39 ОПЛАТА кредитной/дебетовой КАРТОЙ<br>" +
                    "\uD83D\uDD39 ОПТ, ОПЛАТА ЧЕРЕЗ Р/С (ндс, без ндс)<br>" +
                    "\uD83D\uDD39 СРОЧНАЯ ДОСТАВКА в течение часа, 400\u20BD<br>" +
                    "\uD83D\uDD39 САМОВЫВОЗ из розничной точки продаж, 0\u20BD<br>" +
                    "\uD83D\uDD1DМы занимаемся продажей и ремонтом цифровой электроники более 5 лет.</p>";
            text += "<p>В нашем ассортименте имеются ";
            text += GadgetConst.MAP_VENDOR_OFFER.get(gadget.get(mapGadgetAttributeNumber.get(VENDOR)));
            text += " всех моделей, цветов и объемов памяти!\uD83D\uDE0A</p>";
            text += getOffer(gadget, cityId);
//            text += "✔ обеспечиваем гарантию на сервисное обслуживание в течение 1 года<br>";
            text += "<p>✔ выдаем документы о вашей покупке: товарный чек и гарантийный талон<br>";
            text += "✔ запечатанные в пленку, без следов эксплуатации, подойдут как подарок<br>";
            text += "✔ перед визитом в магазин, просим уточнять актуальное наличие товара</p>";
            text += "<p>Наше местоположение\uD83C\uDF0D<br>" +
                    "▶ г. Москва, ул. Сущёвский Вал, д. 5с1, время работы (пн-вс): 11.00-21.00<br>" +
                    "▶ г. Казань, ул. Лушникова, д. 8, время работы (пн-сб): 11.00-19.00</p>";
            text += "<p>\uD83D\uDCDE Звоните: 9:00-21:00, ежедневно</p>" +
                    "<p>У нас вы сможете выгодно приобрести любой интересующий вас гаджет или аксессуар!" +
                    "\uD83D\uDC4D<br>" +
                    "iSPARK\uD83D\uDD25";
            if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(GadgetConst.REF)) {
                text += "Дискаунтер";
            } else {
                text += "Электроникс";
            }
            text += "</p>]]>";
            return text;
        } else {
            text += "</p><p>\uD83C\uDF41ОСЕННИЙ ЦЕНОПАД, до конца недели продаем в розницу по оптовым ценам (только для Авито)❗</p>";
            text += "<p>\uD83D\uDC9BМы всегда идем навстречу нашим покупателям.<br>" +
                    "\uD83D\uDC49Мы предлагаем вам:<br>" +
                    "\uD83D\uDD39 КРЕДИТ от ОТП Банк/Хоум-Кредит<br>" +
                    "\uD83D\uDD39 ТРЕЙД-ИН, ОБМЕН старого телефона<br>" +
                    "\uD83D\uDD39 ОПЛАТА кредитной/дебетовой КАРТОЙ<br>" +
                    "\uD83D\uDD39 ОПТ, ОПЛАТА ЧЕРЕЗ Р/С (ндс, без ндс)<br>" +
                    "\uD83D\uDD39 СРОЧНАЯ ДОСТАВКА в течение часа, 300\u20BD<br>" +
                    "\uD83D\uDD39 САМОВЫВОЗ из розничной точки продаж, 0\u20BD<br>" +
                    "\uD83D\uDD1DМы занимаемся продажей и ремонтом цифровой электроники более 5 лет.</p>";
            text += "<p>В нашем ассортименте имеются ";
            text += GadgetConst.MAP_VENDOR_OFFER.get(gadget.get(mapGadgetAttributeNumber.get(VENDOR)));
            text += " всех моделей, цветов и объемов памяти!\uD83D\uDE0A</p>";
            text += getOffer(gadget, cityId);
            text += "<p>✔ полноценная ГАРАНТИЯ на ремонтное обслуживание на 1 ГОД<br>";
            text += "✔ товарный чек и гарантийный талон, заверенные живой печатью<br>";
            text += "✔ запечатаны, в идеальном состоянии, подойдут как подарок<br>";
//            text += "✔ перед визитом в магазин, уточняйте актуальное наличие товара</p>";
            text += "<p>Наше местоположение\uD83C\uDF0D<br>" +
                    "▶ г. Казань, ул. Лушникова, д. 8, время работы (пн-сб): 11.00-19.00<br>" +
                    "▶ г. Москва, ул. Сущёвский Вал, д. 5с1, время работы (пн-вс): 11.00-21.00</p>";
            text += "<p>\uD83D\uDCDE Звоните: 9:00-21:00, ежедневно</p>" +
                    "<p>У нас вы сможете наиболее выгодно купить интересующий вас гаджет или аксессуар!" +
                    "\uD83D\uDC4D<br>" +
                    "iSPARK\uD83D\uDD25Дискаунтер</p>";
            return text + "]]>";
        }
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

    public String getXmlAd(ArrayList<String> gadget, int xmlDay, String dateBeginRight, int cityId,
                           boolean isInitial) {
//        System.out.println("check:" + isInitial);
        String ad = "\t<Ad>\n";
        ad += "\t\t<Id>" + getIdName(gadget) + "</Id>\n";
        String name = getAvitoAdName(gadget);
        if (!isInitial) {
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
            String dateBeginLeft = getDateByCalendar(calendarZero);
            ad += "\t\t<DateBegin>" + dateBeginLeft + dateBeginRight + "</DateBegin>\n";
        }
        ad += "\t\t<AllowEmail>Нет</AllowEmail>\n";
        ad += "\t\t<ManagerName>Оператор-консультант</ManagerName>\n";
        if (cityId == 0) {
            ad += "\t\t<ContactPhone>84995834751</ContactPhone>\n";
            ad += "\t\t<Region>" + GadgetConst.CITIES[cityId] + "</Region>\n";
        } else {
            ad += "\t\t<ContactPhone>88432515105</ContactPhone>\n";
            ad += "\t\t<Region>Татарстан</Region>\n";
            ad += "\t\t<City>" + GadgetConst.CITIES[cityId] + "</City>\n";
        }
        ad += "\t\t<Category>Телефоны</Category>\n";
        String goodsType = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
        if (goodsType.equals("Apple")) {
            goodsType = "iPhone";
        }
        ad += "\t\t<GoodsType>" + goodsType + "</GoodsType>\n";
        ad += "\t\t<Title>" + name + "</Title>\n";
        ad += "\t\t<Description>" + getAdTextAvitoShop(gadget, cityId) + "</Description>\n";
//        if (gadget.get(mapGadgetAttributeNumber.get(QUALITY)).equals(EST2)) {
        ad += "\t\t<Price>" + getPriceByCity(getGadgetName(gadget), cityId) + "</Price>\n";
//        } else {
//            ad += "\t\t<Price>" + getMaxOptPriceAmoled(gadget) + "</Price>\n";
//        }
        ad += "\t\t<Images>\n";
        ad += "\t\t\t<Image url=\"" + getImageAvitoUrl(gadget) + "\"/>\n";
//        imgLink = "https://raw.githubusercontent.com/bav735/AMOLED/master/price_iphone.png";
//        ad += "\t\t\t<Image url=\"" + imgLink + "\"/>\n";
        ad += "\t\t</Images>\n";
        ad += "\t</Ad>\n";
        return ad;
    }

    private String getPriceByCity(String gadgetName, int cityId) {
        if (cityId == 0) {
            return getPrice(gadgetName, OPT_MAX);
        } else {
            return getPrice(gadgetName, OPT_MAX);
        }
    }

    /*private String getAdFileContent(ArrayList<String> gadget) {
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
        ad += "Цена: " + getPriceRetailMin(gadget) + "\n";
        ad += "Текст: " + getAdTextAvitoBot(gadget) + "\n";
        return ad;
    }*/

    private static String getFullPath(ArrayList<String> gadget) {
        String path = "";
        for (int i = mapGadgetAttributeNumber.get(QUALITY); i <= mapGadgetAttributeNumber.get(COLOR); i++) {
            String attr = gadget.get(i).replaceAll("[() -]", "");
            path += attr + "/";
        }
        if (gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT)).length() > 1) {
            path += "БО";
        } else {
            path += "СО";
        }
        return path + "/" + IMG_FILE_NAME + ".jpg";
    }

    private String getGadgetPathSite(ArrayList<String> gadget, String color) {
        String path = "";
        for (int i = mapGadgetAttributeNumber.get(VENDOR); i <= mapGadgetAttributeNumber.get(MODEL); i++) {
            String attr = gadget.get(i);
            path += attr + "/";
        }
        return (path + color + "/").replace(" ", "");
    }

    private void generatePhotos(ArrayList<String> gadget) {
        File avitoImage = new File("C:/iSPARK/images_avito_actual/" + getFullPath(gadget));
        avitoImage.mkdirs();
        File gadgetImg = new File("C:/iSPARK/images_avito/" + getAvitoPath(gadget));
        try {
            Files.copy(gadgetImg.toPath(), avitoImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private HashMap<String, ArrayList<ArrayList<String>>> getModelGadgetMap(ArrayList<ArrayList<String>> gadgets) {
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets = new HashMap<>();

        for (ArrayList<String> gadget : gadgets) {
            String metaModel = getMetaModel(gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE)),
                    gadget.get(mapGadgetAttributeNumber.get(MODEL)));
            if (!mapGadgetModelGadgets.containsKey(metaModel)) {
                mapGadgetModelGadgets.put(metaModel, new ArrayList<ArrayList<String>>());
                System.out.println(metaModel + "|");
            }
            mapGadgetModelGadgets.get(metaModel).add(gadget);
//                ArrayList<String> gadget2 = new ArrayList<>(gadget);
//                gadget2.set(mapGadgetAttributeNumber.get(QUALITY), EST2);
//                mapGadgetModelGadgets.get(model).add(gadget2);
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

    private String getMetaModel(String modelLine, String model) {
        return modelLine + " " + model;
    }

    public void generateXML(BufferedWriter writer, int cityId) throws IOException {
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets = getModelGadgetMap(gadgets);
        String xml = "";
        int[] size = new int[GadgetConst.MODELS[globalModelLine].size()];
//        Arrays.fill(maxId, 0);
        int megaSize = 0;
        for (int modelNum = 0; modelNum < GadgetConst.MODELS[globalModelLine].size(); modelNum++) {
            String metaModel = getMetaModel(GadgetConst.MODEL_LINES.get(globalModelLine),
                    GadgetConst.MODELS[globalModelLine].get(modelNum));
//                if (!mapGadgetModelGadgets.containsKey(model)) {
//                    continue;
//                }
            ArrayList<ArrayList<String>> gadgets = mapGadgetModelGadgets.get(metaModel);
//                Collections.sort(gadgets, new CustomComparator());
            int prevSize = 0;//maxId[modelNum];
//                maxId[modelNum] += GadgetConst.GADGET_PER_MONTH_COUNT[cityId].get(modelNum);
//                System.out.print("$model:" + metaModel + " ");
            size[modelNum] = Math.min(gadgets.size(), GadgetConst.MAP_METAMODEL_ADS_PER_MONTH.get(
                    metaModel)[cityId]);
            megaSize += size[modelNum];// - prevSize;
            System.out.println(metaModel + " size_curr:" + size[modelNum] +
                    " size_all: " + gadgets.size());
            for (int gadgetNum = prevSize; gadgetNum < size[modelNum]; gadgetNum++) {
                int gadgetId = gadgetNum - prevSize;
                if (size[modelNum] < 10) {
                    if (gadgetNum == 0) {
                        xml += getXmlAd(gadgets.get(gadgetNum), 0, "", cityId, true);
                    }
                } else {
                    int timeIntervalSec = TIME_MONTH_SEC / (size[modelNum] - prevSize);
                    int gadgetTimeSec = gadgetId * timeIntervalSec;
                    int gadgetTimeDay = gadgetTimeSec / TIME_DAY_SEC + 1;
                    gadgetTimeSec %= TIME_DAY_SEC;
                    int gadgetTimeHour = gadgetTimeSec / 3600 + HOUR_BEGIN;
                    gadgetTimeSec %= 3600;
                    int gadgetTimeMin = gadgetTimeSec / 60;
                    gadgetTimeSec %= 60;
                    String dateEnd = "T" + formatDateElem(gadgetTimeHour) + ":" +
                            formatDateElem(gadgetTimeMin) + ":" +
                            formatDateElem(gadgetTimeSec) + "+03:00";
                    xml += getXmlAd(gadgets.get(gadgetNum), gadgetTimeDay, dateEnd, cityId, false);
                }
//                    generateAmoledDirsPhotos(gadgets.get(gadgetNum));
            }
        }
        writer.write(xml);
        System.out.println("megasize" + megaSize);
    }

    /*public void generateFolders() throws IOException {
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
    }*/

    /*public String getRobotText(String model, ArrayList<ArrayList<String>> gadgets, int gadgetNumT, int size) {
        String res = "";
        Collections.shuffle(gadgets, new Random(7351));
        int gadgetNum = gadgetNumT + 1;
        for (int i = 1; i <= size; i++) {
            ArrayList<String> gadget = gadgets.get(i - 1);
            res += "\"" + getAvitoAdName(gadget) + "\";\"" +
                    getAdTextAvitoBot(gadget) + "\";\"" +
                    getPriceRetailMin(gadget) + "\";\"" +
                    i + ".jpg" + ",price" + gadgetNum + ".jpg\"\n";
            gadgetNum++;
        }
        return res;
    }*/

    /*public void generateFilesAvibot() throws IOException {
        int gadgetNum = 0;
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets = getModelGadgetMap(gadgets);
        for (String model : GadgetConst.MODELS) {
            int maxId = 0;//Math.min(GadgetConst.MAP_MODEL_PER_MONTH_COUNT.get(model), mapGadgetModelGadgets.get(model).maxId());
            BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/AvitoRobot/" + model, "ads.csv");
            bufferedWriter.write(getRobotText(model, mapGadgetModelGadgets.get(model), gadgetNum, maxId));
            bufferedWriter.flush();
            gadgetNum += maxId;
//            c += Math.min(mapGadgetModelGadgetPerMonthCount.get(model), mapGadgetModelGadgets.get(model).baseSize());
        }
    }*/

    /*private class CustomComparator implements Comparator<ArrayList<String>> {
        @Override
        public int compare(ArrayList<String> g1, ArrayList<String> g2) {
            return Solution.getNumber(getPriceRetailMin(g1)) - Solution.getNumber(getPriceRetailMin(g2));
        }
    }*/
}