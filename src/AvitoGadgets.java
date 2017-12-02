import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class AvitoGadgets extends Gadgets {
    final static int ADS_COUNT_MIN = 20;
    public ArrayList<ArrayList<String>> gadgets = new ArrayList<>();
    LinkedHashMap<String, ArrayList<ArrayList<String>>> mapMetaModelGadgetsByVendor[];
    LinkedHashMap<String, ArrayList<GadgetGroup>> mapGadgetMetaModelGadgetGroups;
    LinkedHashMap<String, Boolean> mapGadgetMetaModelSingleItem;

    public AvitoGadgets() {
        Set<String> selectedAvitoItems = mapGadgetNamePrices.keySet();
//                = Solution.getHashSetFromInput("present_items_msk");
        for (WebSiteGadgets webSiteGadgets : Solution.getWebSiteGadgetsArray()) {
            for (ArrayList<String> gadget : webSiteGadgets.gadgets) {
                if (selectedAvitoItems.contains(getGadgetName(gadget, QUALITY, MEMORY))
                        /*|| selectedAvitoItems.contains(getGadgetName(gadget, QUALITY, COLOR))*/) {
                    gadgets.add(gadget);
//                    generatePhotos(gadget);
                }
            }
        }
        mapMetaModelGadgetsByVendor = new LinkedHashMap[GadgetConst.VENDORS.size()];
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            mapMetaModelGadgetsByVendor[i] = new LinkedHashMap<>();
            String vendor = GadgetConst.VENDORS.get(i);
            for (ArrayList<String> gadget : gadgets) {
                if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals(vendor)) {
                    String metaModel = getMetaModel(gadget);
                    if (!mapMetaModelGadgetsByVendor[i].containsKey(metaModel)) {
                        mapMetaModelGadgetsByVendor[i].put(metaModel,
                                new ArrayList<ArrayList<String>>());
                    }
                    mapMetaModelGadgetsByVendor[i].get(metaModel).add(gadget);
                }
            }
        }
        mapMetaModelGadgetsByVendor = new LinkedHashMap[GadgetConst.VENDORS.size()];
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            mapMetaModelGadgetsByVendor[i] = new LinkedHashMap<>();
            String vendor = GadgetConst.VENDORS.get(i);
            for (ArrayList<String> gadget : gadgets) {
                if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals(vendor)) {
                    String metaModel = getMetaModel(gadget);
                    if (!mapMetaModelGadgetsByVendor[i].containsKey(metaModel)) {
                        mapMetaModelGadgetsByVendor[i].put(metaModel,
                                new ArrayList<ArrayList<String>>());
                    }
                    mapMetaModelGadgetsByVendor[i].get(metaModel).add(gadget);
                }
            }
        }

        LinkedHashMap<String, ArrayList<ArrayList<String>>> mapMetaModelGadgets = new LinkedHashMap<>();
        for (ArrayList<String> gadget : gadgets) {
            String metaModel = getMetaModel(gadget);
            if (!mapMetaModelGadgets.containsKey(metaModel)) {
                mapMetaModelGadgets.put(metaModel,
                        new ArrayList<ArrayList<String>>());
            }
            mapMetaModelGadgets.get(metaModel).add(gadget);
        }
        mapGadgetMetaModelSingleItem = new LinkedHashMap<>();
        mapGadgetMetaModelGadgetGroups = new LinkedHashMap<>();
        for (String metaModel : mapMetaModelGadgets.keySet()) {
            ArrayList<GadgetGroup> gadgetGroups = new ArrayList<>();
            for (String country : GadgetConst.COUNTRIES) {
                GadgetGroup gadgetGroup = new GadgetGroup(country);
                gadgetGroup.gadgets.addAll(mapMetaModelGadgets.get(metaModel));
                gadgetGroups.add(gadgetGroup);
            }
            mapGadgetMetaModelGadgetGroups.put(metaModel, gadgetGroups);
        }
    }

    public static String getAvitoPath(ArrayList<String> gadget) {
        String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
        String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        String res = getVendor(gadget) + "/" + modelLine + "/" + model + "/"
                + color;
        res += "/" + IMG_FILE_NAME + ".jpg";
        return res.replace(" ", "");
    }

    /*private ArrayList<String> extractGadgetByModel(int modelId) {
        String model = gadgetAttributesVariants.get(mapGadgetAttributeNumber.get(MODEL)).get(modelId);
        ArrayList<ArrayList<String>> gadgetsByModel = mapMetaModelGadgetsByVendor.get(model);
        ArrayList<String> gadget = gadgetsByModel.get(gadgetsByModel.maxId() - 1);
        gadgetsByModel.remove(gadgetsByModel.maxId() - 1);
        mapMetaModelGadgetsByVendor.put(model, gadgetsByModel);
        return gadget;
    }*/

    /*public String getAdTitle(ArrayList<String> gadget, int cityId) {
        String name = "";
        String vendor = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
        String quality = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
//        if (cityId == 0 || quality.equals(GadgetConst.CPO)) {
        name += GadgetConst.MAP_QUALITY_AD_NAME.get(quality) + " ";
//        } else {
//            name += "Новый ";
//        }
        name += String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(MODEL_LINE),
                mapGadgetAttributeNumber.get(COLOR) + 1)).replace("  ", " ").replace("  ", " ");
        String country = "";
        if (vendor.equals("Samsung")) {
            String subModel = gadget.get(mapGadgetAttributeNumber.get(SUBMODEL));
            String submodelEnding = subModel.substring(subModel.length() - 1, subModel.length());
            country = GadgetConst.MAP_SAMSUNG_SUB_MODEL_ENDING_DESCRIPTION
                    .get(submodelEnding);
            country = country.substring(country.lastIndexOf(' '), country.length() - 1);
            name = name.replace(subModel + " ", "");
        }
        int lastAttr = mapGadgetAttributeNumber.get(FINGER_PRINT);
        if (gadget.get(lastAttr).length() > 1) {
            name += " " + gadget.get(lastAttr);
        }
        if (!country.isEmpty()) {
            name += country;
        }
        name += " " + gadget.get(mapGadgetAttributeNumber.get(QUALITY)) + "Гарантия";
//        if (vendor.equals("Apple") && quality.equals(GadgetConst.REF)) {
//            name += " Качество";
//        }
        return name + " Магазин";
    }*/

//    private String getCreditOffer(ArrayList<String> gadget) {
//        return "- в кредит на 6 мес = от " + getCreditPrice(gadget) + "₽ в мес<br>";
//    }

//    private int getCreditPrice(ArrayList<String> gadget) {
//        int creditPrice = Integer.parseInt(getPrice(getGadgetName(gadget, forAvito), PREPAY_PRICE)) * 112 / 600;
//        return (creditPrice / 50 + 1) * 50;
//    }

    /*private String getAdTextAvitoBot(ArrayList<String> gadget) {
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
    }*/

    /*private String getAdFileContent(ArrayList<String> gadget) {
        String ad = "";
        ad += "Категория: Телефоны\n";
        String goodsType = "";
        if (gadget.get(mapGadgetAttributeNumber.get(WebSiteGadgets.VENDOR)).contains("Apple")) {
            goodsType = "iPhone";
        } else {
            goodsType = "Samsung";
        }
        ad += "Вид товара: " + goodsType + "\n";
        ad += "Название: " + getAdTitle(gadget) + "\n";
        ad += "Цена: " + getPriceRetailMin(gadget) + "\n";
        ad += "Текст: " + getAdTextAvitoBot(gadget) + "\n";
        return ad;
    }*/

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

    public String generateXMLGlobal(int cityId) throws IOException {
        String xml = "";
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            GadgetGroup gadgetGroup = new GadgetGroup(GadgetConst.COUNTRIES.get(0));
            for (String metaModel : mapMetaModelGadgetsByVendor[i].keySet()) {
                ArrayList<String> gadget = mapMetaModelGadgetsByVendor[i].get(metaModel).get(0);
                if (!(getVendor(gadget).equals("Apple") || getVendor(gadget).equals("Samsung"))
                        || getQuality(gadget).startsWith("REF")) {//УБРАТЬ КОГДА ИСТЕЧЕТ СРОК РАЗМЕЩЕНИЯ!
                    gadgetGroup.gadgets.add(gadget);
                }
            }
            gadgetGroup.initialize(-1, 1, cityId);
            xml += gadgetGroup.getXmlAd(false);
        }
        return xml;
    }

    private int getMetaModelAdsSize(String vendor, String metaModel, int cityId) {
        if (!GadgetConst.MAP_MODEL_ADS_PER_MONTH[cityId].containsKey(vendor + " " + metaModel)) {
            return 0;
        }
        int adsPerMonth = GadgetConst.MAP_MODEL_ADS_PER_MONTH[cityId].get(vendor +
                " " + metaModel);
        if (adsPerMonth < 31) {
            return 1;
        }
        return adsPerMonth / ADS_COUNT_MIN;
    }

    public String generateXMLArrangement(int cityId) throws IOException {
        String xml = "";
        int globalMaxCountryCount = 0;
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            String vendor = GadgetConst.VENDORS.get(i);
            int prevMaxCountryCount = 0;
            if (cityId > 0) {
                prevMaxCountryCount = GadgetConst.CITIES_MAX_COUNTRIES[cityId - 1];
            }
            int maxCountryCount = 0;
            for (String metaModel : mapMetaModelGadgetsByVendor[i].keySet()) {
                maxCountryCount = Math.max(maxCountryCount, getMetaModelAdsSize(vendor, metaModel, cityId) /
                        mapMetaModelGadgetsByVendor[i].get(metaModel).size());
            }
            globalMaxCountryCount = Math.max(globalMaxCountryCount, maxCountryCount);
            System.out.println("max countries=" + maxCountryCount +
                    "prev max countries=" + prevMaxCountryCount);
            HashMap<String, ArrayList<GadgetGroup>> gadgetsByMetaModel = new HashMap<>();
            for (String metaModel : mapMetaModelGadgetsByVendor[i].keySet()) {
                gadgetsByMetaModel.put(metaModel, new ArrayList<GadgetGroup>());
            }
            //maxCountryCount или maxCountryCount + 1?
            for (int countryId = prevMaxCountryCount;
                 countryId < prevMaxCountryCount + maxCountryCount; countryId++) {
                for (String metaModel : mapMetaModelGadgetsByVendor[i].keySet()) {
                    ArrayList<GadgetGroup> gadgets = gadgetsByMetaModel.get(metaModel);
                    for (int metaModelId = 0; metaModelId < mapMetaModelGadgetsByVendor[i].get(metaModel)
                            .size() && gadgets.size() < getMetaModelAdsSize(vendor, metaModel, cityId); metaModelId++) {
                        String country = GadgetConst.COUNTRIES.get(countryId);
                        GadgetGroup gadget = new GadgetGroup(country);
                        gadget.gadgets.add(mapMetaModelGadgetsByVendor[i]
                                .get(metaModel).get(metaModelId));
                        gadgets.add(gadget);
                    }
                    gadgetsByMetaModel.put(metaModel, gadgets);
                }
            }
            for (String metaModel : gadgetsByMetaModel.keySet()) {
                ArrayList<GadgetGroup> gadgets = gadgetsByMetaModel.get(metaModel);
                for (int gadgetId = 0; gadgetId < gadgets.size(); gadgetId++) {
                    GadgetGroup gadget = gadgets.get(gadgetId);
                    gadget.initialize(gadgets.size() - gadgetId - 1, gadgets.size(), cityId);
                    xml += gadget.getXmlAd(gadgets.size() > 1);
                }
            }
        }
        GadgetConst.CITIES_MAX_COUNTRIES[cityId] = globalMaxCountryCount;
        return xml;
    }


    public String generateXMLAutoload() throws IOException {
        String xml = "";
        for (int cityId = 0; cityId < GadgetConst.CITIES.length; cityId++) {
            LinkedHashSet<String> metaModelsUpdate = Solution.getHashSetFromInput(
                    "AMOLED/update_items_" + GadgetConst.CITIES_FILE_END[cityId] + ".txt");
            for (String metaModel : metaModelsUpdate) {
                int metaModelLastId = GadgetConst.MAP_META_MODEL_LAST_GADGET_ID.get(metaModel)
                        % mapGadgetMetaModelGadgetGroups.get(metaModel).size();
                GadgetConst.MAP_META_MODEL_CURR_GADGET_ID[cityId]
                        .put(metaModel, metaModelLastId);
                GadgetConst.MAP_META_MODEL_LAST_GADGET_ID.put(metaModel, metaModelLastId + 1);
            }
            LinkedHashSet<String> metaModelsPresent = Solution.getHashSetFromInput(
                    "AMOLED/present_items_" + GadgetConst.CITIES_FILE_END[cityId] + ".txt");
            for (String metaModel : metaModelsPresent) {
                if (!GadgetConst.MAP_META_MODEL_LAST_GADGET_ID.keySet().contains(
                        metaModel)) {
                    continue;
                }
                int gadgetGroupId = GadgetConst.MAP_META_MODEL_CURR_GADGET_ID[cityId]
                        .get(metaModel);
                if (gadgetGroupId == -1) {
                    continue;
                }
                GadgetGroup gadgetGroup = mapGadgetMetaModelGadgetGroups.get(metaModel)
                        .get(gadgetGroupId);
                gadgetGroup.initialize(cityId);
                xml += gadgetGroup.getXmlAd(false);
            }
        }
        return xml;
    }

    /*public void generateFolders() throws IOException {
        for (int gadgetId = 0; gadgetId < gadgets.size(); gadgetId++) {
            ArrayList<String> gadget = gadgets.get(gadgetId);
//            if (!excludeAds.contains(getAdTitle(gadget))) {
            String model = gadget.get(mapGadgetAttributeNumber.get(QUALITY)).toLowerCase() +
                    gadget.get(mapGadgetAttributeNumber.get(SUBMODEL)) + "_";
            if (!gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT)).isEmpty()) {
                model += "без_отпечатка";
            }
            BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/Avito/" +
                    gadget.get(mapGadgetAttributeNumber.get(VENDOR)) + "/" +
                    gadget.get(mapGadgetAttributeNumber.get(MODEL)) + "/" +
                    gadget.get(mapGadgetAttributeNumber.get(COLOR)) + "/" +
                    gadget.get(mapGadgetAttributeNumber.get(MEMORY)), model + ".txt");
            bufferedWriter.write(getAdFileContent(gadgets.get(gadgetId)));
            bufferedWriter.flush();
//            }
        }
    }*/
}