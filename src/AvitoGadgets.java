import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class AvitoGadgets extends Gadgets {
    final static int ADS_COUNT_MIN = 20;
    public ArrayList<ArrayList<String>> gadgets = new ArrayList<>();
    LinkedHashMap<String, ArrayList<ArrayList<String>>> mapGadgetMetaModelGadgetsByVendor[];

    public AvitoGadgets() {
        LinkedHashSet<String> selectedAvitoItems = Solution.getHashSetFromInput("selected_avito_items.txt");
        for (WebSiteGadgets webSiteGadgets : Solution.getWebSiteGadgetsArray()) {
            for (ArrayList<String> gadget : webSiteGadgets.gadgets) {
                if (selectedAvitoItems.contains(getGadgetName(gadget, QUALITY, MEMORY))) {
                    gadgets.add(gadget);
                }
            }
        }
        mapGadgetMetaModelGadgetsByVendor = new LinkedHashMap[GadgetConst.VENDORS.size()];
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            mapGadgetMetaModelGadgetsByVendor[i] = new LinkedHashMap<>();
            String vendor = GadgetConst.VENDORS.get(i);
            for (ArrayList<String> gadget : gadgets) {
                if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals(vendor)) {
                    String metaModel = getMetaModel(gadget);
                    if (!mapGadgetMetaModelGadgetsByVendor[i].containsKey(metaModel)) {
                        mapGadgetMetaModelGadgetsByVendor[i].put(metaModel,
                                new ArrayList<ArrayList<String>>());
                    }
                    mapGadgetMetaModelGadgetsByVendor[i].get(metaModel).add(gadget);
                }
            }
        }
    }

    public static String getAvitoPath(ArrayList<String> gadget) {
        String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
        String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        String res = getVendor(gadget) + "/" + modelLine + "/" + model.replace(" ", "") + "/"
                + color.replace(" ", "");
        if (getQuality(gadget).equals("CPO")) {
            res += "/CPO";
        }
        res += "/" + IMG_FILE_NAME + ".jpg";
        return res;
    }

    /*private ArrayList<String> extractGadgetByModel(int modelId) {
        String model = gadgetAttributesVariants.get(mapGadgetAttributeNumber.get(MODEL)).get(modelId);
        ArrayList<ArrayList<String>> gadgetsByModel = mapGadgetMetaModelGadgetsByVendor.get(model);
        ArrayList<String> gadget = gadgetsByModel.get(gadgetsByModel.maxId() - 1);
        gadgetsByModel.remove(gadgetsByModel.maxId() - 1);
        mapGadgetMetaModelGadgetsByVendor.put(model, gadgetsByModel);
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
            for (String metaModel : mapGadgetMetaModelGadgetsByVendor[i].keySet()) {
                gadgetGroup.gadgets.add(mapGadgetMetaModelGadgetsByVendor[i].get(metaModel).get(0));
            }
            gadgetGroup.initialize(-1, 1, cityId);
            xml += gadgetGroup.getXmlAd();
        }
        return xml;
    }

    private int getMetaModelAdsSize(String vendor, String metaModel, int cityId) {
        if (!GadgetConst.MAP_MODEL_ADS_PER_MONTH[cityId].containsKey(vendor + " " + metaModel)) {
            return 0;
        }
        int adsPerMonth = GadgetConst.MAP_MODEL_ADS_PER_MONTH[cityId].get(vendor + " " + metaModel);
        return (adsPerMonth - 1) / ADS_COUNT_MIN;
    }

    public String generateXMLModels(int cityId) throws IOException {
        String xml = "";
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            String vendor = GadgetConst.VENDORS.get(i);
            int maxCountryCount = 0;
            for (String metaModel : mapGadgetMetaModelGadgetsByVendor[i].keySet()) {
                maxCountryCount = Math.max(maxCountryCount, getMetaModelAdsSize(vendor, metaModel, cityId) /
                        mapGadgetMetaModelGadgetsByVendor[i].get(metaModel).size());
            }
            System.out.println("max countries=" + maxCountryCount);
            HashMap<String, ArrayList<GadgetGroup>> gadgetsByMetaModel = new HashMap<>();
            for (String metaModel : mapGadgetMetaModelGadgetsByVendor[i].keySet()) {
                gadgetsByMetaModel.put(metaModel, new ArrayList<GadgetGroup>());
            }
            for (int countryId = 1; countryId < maxCountryCount + 1; countryId++) {
                for (String metaModel : mapGadgetMetaModelGadgetsByVendor[i].keySet()) {
                    ArrayList<GadgetGroup> gadgets = gadgetsByMetaModel.get(metaModel);
                    for (int metaModelId = 0; metaModelId < mapGadgetMetaModelGadgetsByVendor[i].get(metaModel)
                            .size() && gadgets.size() < getMetaModelAdsSize(vendor, metaModel, cityId); metaModelId++) {
                        String country = GadgetConst.COUNTRIES.get(countryId);
                        GadgetGroup gadget = new GadgetGroup(country);
                        gadget.gadgets.add(mapGadgetMetaModelGadgetsByVendor[i]
                                .get(metaModel).get(metaModelId));
                        gadgets.add(gadget);
                    }
//                    System.out.println(metaModel + " size=" + gadgets.size() +" from "+
//                            getMetaModelAdsSize(vendor, metaModel, cityId));
                    gadgetsByMetaModel.put(metaModel, gadgets);
                }
            }
            for (String metaModel : gadgetsByMetaModel.keySet()) {
                ArrayList<GadgetGroup> gadgets = gadgetsByMetaModel.get(metaModel);
                for (int gadgetId = 0; gadgetId < gadgets.size(); gadgetId++) {
                    GadgetGroup gadget = gadgets.get(gadgetId);
                    gadget.initialize(gadgets.size() - gadgetId - 1, gadgets.size(), cityId);
                    xml += gadget.getXmlAd();
                }
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