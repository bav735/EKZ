import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class AvitoGadgets extends Gadgets {
    public ArrayList<ArrayList<String>> gadgets = new ArrayList<>();
    final static int MAX_MODEL_VARIETY_COUNT = 10000;

    public AvitoGadgets() {
        LinkedHashSet<String> selectedAvitoItems = Solution.getHashSetFromInput("selected_avito_items.txt");
        for (WebSiteGadgets webSiteGadgets : Solution.getWebSiteGadgetsArray()) {
            for (ArrayList<String> gadget : webSiteGadgets.gadgets) {
                if (selectedAvitoItems.contains(getGadgetName(gadget, QUALITY, MEMORY))) {
                    gadgets.add(gadget);
//                    System.out.println(getGadgetName(gadget, QUALITY, COLOR));
                }
            }
        }
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

    public static String getImageAvitoUrl(ArrayList<String> gadget) {
        return "https://raw.githubusercontent.com/bav735/iSPARK/master/images_avito_actual/" + getFullPath(gadget);
    }

    /*private ArrayList<String> extractGadgetByModel(int modelId) {
        String model = gadgetAttributesVariants.get(mapGadgetAttributeNumber.get(MODEL)).get(modelId);
        ArrayList<ArrayList<String>> gadgetsByModel = mapGadgetModelGadgets.get(model);
        ArrayList<String> gadget = gadgetsByModel.get(gadgetsByModel.maxId() - 1);
        gadgetsByModel.remove(gadgetsByModel.maxId() - 1);
        mapGadgetModelGadgets.put(model, gadgetsByModel);
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

    private static String getFullPath(ArrayList<String> gadget) {
        String path = "";
        for (int i = mapGadgetAttributeNumber.get(QUALITY); i <= mapGadgetAttributeNumber.get(COLOR); i++) {
            String attr = gadget.get(i).replaceAll("[() -]", "");
            path += attr + "/";
        }
//        if (gadget.get(mapGadgetAttributeNumber.get(FINGER_PRINT)).length() > 1) {
//            path += "БО";
//        } else {
//            path += "СО";
//        }
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

    private HashMap<String, ArrayList<ArrayList<String>>> getModelGadgetMapByVendor(String vendor) {
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets = new HashMap<>();
        for (ArrayList<String> gadget : gadgets) {
            if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals(vendor)) {
                String metaModel = getMetaModel(gadget);
                if (!mapGadgetModelGadgets.containsKey(metaModel)) {
                    mapGadgetModelGadgets.put(metaModel, new ArrayList<ArrayList<String>>());
                }
                mapGadgetModelGadgets.get(metaModel).add(gadget);
            }
        }
        return mapGadgetModelGadgets;
    }

    private String getMetaModel(ArrayList<String> gadget) {
        return gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE)) + " " +
                gadget.get(mapGadgetAttributeNumber.get(MODEL));
    }

    public void generateXML(BufferedWriter writer, int cityId) throws IOException {
        String xml = "";
        for (String vendor : GadgetConst.VENDORS) {
            HashMap<String, ArrayList<ArrayList<String>>> mapMetaModelGadgets =
                    getModelGadgetMapByVendor(vendor);
            int minMetaModelSize = MAX_MODEL_VARIETY_COUNT;
            for (String metaModel : mapMetaModelGadgets.keySet()) {
                minMetaModelSize = Math.min(minMetaModelSize, mapMetaModelGadgets.get(metaModel).size());
            }
            LinkedHashSet<String> countries = Solution.getHashSetFromInput("countries.txt");
            GadgetGroup[] gadgetGroups = new GadgetGroup[GadgetConst.MAP_VENDOR_ADS_SIZE[cityId].get(vendor)];
            int groupId = 0;
            System.out.println(minMetaModelSize);
            for (int metaModelId = 0; metaModelId < minMetaModelSize; metaModelId++) {
                for (String country : countries) {
                    gadgetGroups[groupId] = new GadgetGroup(country);
                    for (String metaModel : mapMetaModelGadgets.keySet()) {
                        gadgetGroups[groupId].gadgets.add(mapMetaModelGadgets.get(metaModel).get(metaModelId));
                    }
                    groupId++;
                    if (groupId == gadgetGroups.length) {
                        break;
                    }
                }
                if (groupId == gadgetGroups.length) {
                    break;
                }
            }

            for (groupId = 0; groupId < gadgetGroups.length; groupId++) {
                gadgetGroups[groupId].initialize(groupId, gadgetGroups.length, cityId);
                xml += gadgetGroups[groupId].getXmlAd();
//                    generateAmoledDirsPhotos(gadgets.get(gadgetNum));
            }
        }
        writer.write(xml);
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