import javafx.util.Pair;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class WebSiteGadgets extends Gadgets {
    /*final static String ID = "id";
    final static String AVAILABLE = "AVAILABLE";
    final static String PRICE = "PRICE";
    final static String CURRENCY_ID = "Валюта";
    final static String CATEGORY = "CATEGORY";
    final static String PICTURE = "PICTURE_URL";
    final static String URL = "WEBPAGE URL";
    final static String NAME = "NAME";
    final static String DESCRIPTION = "DESCRIPTION";
    final static String TAGS = "TAGS";
    final static String CATEGORY_ID = "CATEGORY_ID";
    final static String SUBCATEGORY_ID = "SUBCATEGORY_ID";
    String[] gadgetAttributeNames = new String[]{
            ID,
            AVAILABLE,
            PRICE,
            CURRENCY_ID,
            CATEGORY,
            PICTURE,
            URL,
            NAME,
            DESCRIPTION,
            TAGS,
            CATEGORY_ID,
            SUBCATEGORY_ID,
    };

    public WebSiteGadgets() {
    }

    public void initializeFromCSV() {
        initializeMapGadgetAttributeNumber(gadgetAttributeNames);
        Scanner inScanner = Solution.getInputScanner("shop_items.csv");
        String csvText = "";
        inScanner.nextLine();
        while (inScanner.hasNextLine()) {
            csvText += inScanner.nextLine();
        }
        String[] gadgetTexts = csvText.split(">\";");
        for (String gadgetText : gadgetTexts) {
            ArrayList<String> gadget = new ArrayList<>(Collections.nCopies(
                    gadgetAttributeNames.length, ""));
            String[] attributes = gadgetText.split(";");
            int descriptionId = mapGadgetAttributeNumber.get(DESCRIPTION);
            for (int partId = 0; partId < descriptionId; partId++) {
                gadget.set(partId, attributes[partId]);
            }
            for (int partId = descriptionId; partId < attributes.length; partId++) {
                gadget.set(descriptionId, gadget.get(descriptionId) + attributes[partId] + ";");
            }
            gadget.set(descriptionId, gadget.get(descriptionId) + ">\"");
            Pair<String, String> categories = getCategoriesByPicture(gadget.get(mapGadgetAttributeNumber.get(PICTURE)));
            gadget.set(mapGadgetAttributeNumber.get(CATEGORY_ID), categories.getKey());
            gadget.set(mapGadgetAttributeNumber.get(SUBCATEGORY_ID), categories.getValue());
            gadget.set(mapGadgetAttributeNumber.get(URL), "http://ispark.info/shop/" + categories.getKey() + "/" +
                    categories.getValue());
            gadget.set(descriptionId, getModifiedDescription(gadget.get(descriptionId)));
            gadgets.add(gadget);
        }

        *//*while (inScanner.hasNextLine()) {
            int beginTagId = csvLine.indexOf("\"<");
            if (beginTagId != -1) {
                String newCsvLine = String.copyValueOf(csvLine.toCharArray());
                //                int endTagId = csvLine.indexOf(">\"");
                while (!newCsvLine.contains(">\";") && inScanner.hasNextLine()) {
                    csvText += newCsvLine;
                    newCsvLine = inScanner.nextLine();
                }
                csvLine = newCsvLine;
//                csvLine = csvLine.substring(0, beginTagId) + "\"описание\";";
            }
        }*//*
        inScanner.close();
    }

    public void printCSVGadgets(BufferedWriter outWriter) {
        String outText = "id;available;price;currencyId;category;picture;url;name;description;tags\n";
        for (ArrayList<String> gadget : gadgets) {
            if (!gadget.get(mapGadgetAttributeNumber.get(CATEGORY)).contains("iPhone ")) {
                continue;
            }
            for (int i = 0; i <= mapGadgetAttributeNumber.get(DESCRIPTION); i++) {
                outText += gadget.get(i) + ";";
            }
            outText += "\n";
        }
        Solution.writeText(outWriter, outText);
    }

    public void synchronizePrices() {
        for (ArrayList<String> gadget : gadgets) {
            String oldGadgetName = gadget.get(mapGadgetAttributeNumber.get(NAME));
            String[] nameParts = oldGadgetName.substring(1, oldGadgetName.length() - 1).split(" ");
            String gadgetName = "";
            int partId = 0;
            while (partId < nameParts.length && !nameParts[partId].toLowerCase().contains("gb")) {
                gadgetName += nameParts[partId] + " ";
                partId++;
            }
            if (partId < nameParts.length) {
                gadgetName += nameParts[partId].substring(0, nameParts[partId].length() - 2) + "Gb";
            }
            String price = "";
            if (!mapGadgetNamePrices.containsKey(gadgetName)) {
                continue;
            }
            if (gadget.get(mapGadgetAttributeNumber.get(CATEGORY)).contains("RST")) {
                price = mapGadgetNamePrices.get(gadgetName).get(mapPriceAttributeNumber.get(RST_RETAIL_ISPARK));
            } else {
                String s = "";
                if (gadget.get(mapGadgetAttributeNumber.get(CATEGORY)).contains("БО")) {
                    s = " Б/О";
                }
                price = mapGadgetNamePrices.get(gadgetName + s).get(mapPriceAttributeNumber.get(EST_RETAIL_ISPARK));
//                if (price.length() == 1) {
//                    price = mapGadgetNamePrices.get(gadgetName + s).get(mapPriceAttributeNumber.get(EST_RETAIL_AMOLED));
//                }
            }
            partId++;
            while (partId < nameParts.length) {
                gadgetName += " " + nameParts[partId];
                partId++;
            }
            gadget.set(mapGadgetAttributeNumber.get(NAME), "\"" + gadgetName + "\"");
            gadget.set(mapGadgetAttributeNumber.get(PRICE), price);
        }
    }

    private Pair<String, String> getCategoriesByPicture(String picture) {
        String[] parts = picture.split("/");
        String categoryId = parts[parts.length - 2].substring(0, 6);
        String subCategoryId = parts[parts.length - 1].substring(0, 6);
        return new Pair<>(categoryId, subCategoryId);
    }

    private String getModifiedDescription(String description) {
        description = description.replaceAll("<strong>", "@");
        description = description.replaceAll("</strong>", "#");
        description = description.replaceAll("<.*?>", "~");
        description = description.replaceAll("&[a-z]+;", "");
        description = description.replace("  ", " ");
        description = description.replaceAll("~+", "~");
        description = description.replaceAll("^\"~", "");
        description = description.replaceAll("~\"$", "");
        description = description.replaceAll("~@~", "~@");
        description = description.replaceAll("~#~", "#~");
        description = description.replaceAll("@#", "");
        description = description.replaceAll("~~", "~");
        description = description.replaceAll("@", "<strong>");
        description = description.replaceAll("#", "</strong>");
        description = description.replaceAll("~", "<br><br>");
        description = "\"<span style=\"\"font-baseSize: medium;\"\">" + description + "</span>\"";
        return description;
    }

    public void printYMGadgets(BufferedWriter outWriter) {
        Scanner inScanner = Solution.getInputScanner("selected_ym_items.txt");
        System.out.println("YM Gadgets..");
        HashSet<String> setSelectedItems = new HashSet<>();
        while (inScanner.hasNextLine()) {
            setSelectedItems.add(inScanner.nextLine());
        }
        String outText = "id;available;price;currencyId;category;picture;url;name;description;manufacturer_warranty\n";
        for (int i = 0; i < gadgets.size(); i++) {
            ArrayList<String> gadget = gadgets.get(i);
            String gadgetName = gadget.get(mapGadgetAttributeNumber.get(NAME)).replace("\"", "");
            if (gadgetName.contains("восстановленный")) {
                gadgetName = gadgetName.substring(0, gadgetName.length() - 18);
            }
            String category = "Мобильные телефоны;";
            String s = "";
            if (gadget.get(mapGadgetAttributeNumber.get(CATEGORY)).contains("EST")) {
                s = "EST ";
            } else if (gadget.get(mapGadgetAttributeNumber.get(CATEGORY)).contains("RST")) {
                s = "RST ";
            } else {
                category = "Моноколеса и гироскутеры;";
            }
            String warranty = "false";
            if (s.equals("RST ")) {
                warranty = "true";
            }
            System.out.println(gadgetName);
            if (setSelectedItems.contains(s + gadgetName)) {
                outText += i + ";true;";
                outText += gadget.get(mapGadgetAttributeNumber.get(PRICE)) + ";RUR;" + category;
                outText += gadget.get(mapGadgetAttributeNumber.get(PICTURE)) + ";";
                outText += gadget.get(mapGadgetAttributeNumber.get(URL)) + ";";
                outText += gadget.get(mapGadgetAttributeNumber.get(NAME)) + ";";
                if (warranty.equals("true")) {
                    outText += "\"Официальная гарантия от Apple - 1 год с момента покупки! ";
                } else {
                    outText += "\"Гарантия от iSPARK! ";
                }
                outText += gadgetName + " будет с тобой каждую секунду жизни. Быстрый, отзывчивый, " +
                        "незаменимый, словно надежный и проверенный друг, которому просто и легко довериться. Он" +
                        " создан сделать твою жизнь проще и позволит получить удовольствие от каждого её мгновения. " +
                        "Купите " + gadgetName + ", и в этом легко будет убедиться!\";";
                outText += warranty + "\n";
            }
        }
        Solution.writeText(outWriter, outText);
    }*/
}