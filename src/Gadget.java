import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by A on 14.06.2017.
 */
public class Gadget {
    public static int maxId = 1;
    String imageUrl;
    String price;
    String quality;
    String vendor;
    String model;
    String description;
    String webSiteName;
    //    String initialCategoryId;
    String id;
    String params;
    String namePrefix;
    public boolean isFinal = false;

    public Gadget(ArrayList<String> gadget) {
        quality = gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.QUALITY));
        vendor = gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.VENDOR));
        model = gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.MODEL_LINE)) +
                " " + gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.MODEL)) +
                " " + gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.MEMORY)) +
                " " + gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.COLOR));
        description = getDescriptionByModel(vendor,
                gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.MODEL_LINE)),
                gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.MODEL)));
        imageUrl = AvitoGadgets.getImageWebsiteUrl(gadget);
        namePrefix = "Смартфон";
//        price = "" + Gadgets.getPrice(AvitoGadgets.getGadgetName(gadget, false), Gadgets.RETAIL_MIN, );
        id = "" + maxId;
        maxId++;
        params = "";
        webSiteName = namePrefix + " " + vendor + " " + model;
    }

    public Gadget(String offer/*, String initialCategoryId*/) {
        vendor = Solution.getValueByTag(offer, "vendor");
        model = Solution.getValueByTag(offer, "model");
        imageUrl = Solution.getValueByTag(offer, "picture");
        description = Solution.getValueByTag(offer, "description");
        price = Solution.getValueByTag(offer, "price");
        namePrefix = Solution.getValueByTag(offer, "typePrefix");
        price = price.substring(0, price.length() - 2);
//        this.initialCategoryId = initialCategoryId;
        /*if (Solution.SHOP_ITEMS_XML.equals(Solution.CUSTOM_XML)) {
            id = Solution.getValueByPrefix(offer, "id=\"", '"');
        }*/
        id = "" + maxId;
        maxId++;
        params = Solution.getValueByTag(offer, "<param ", "</param>");
//        System.out.println(description);
    }

    public String getCategoryTreeName() {
        return namePrefix + " " + vendor + " " + model;
    }

    public String getPriceListName() {
        String res = vendor + " ";
        String[] modelSplit = model.split(" ");
        int posGb = -1;
        for (int i = 0; i < modelSplit.length; i++) {
            if (modelSplit[i].toLowerCase().contains("gb")) {
                posGb = i;
            }
        }
        if (posGb == -1) {
            return "";
        }
        res += String.join(" ", Arrays.copyOfRange(modelSplit, 0, posGb + 1));
        return res;
    }

    public String getPriceListModel() {
        String[] modelSplit = model.split(" ");
        int posGb = -1;
        for (int i = 0; i < modelSplit.length; i++) {
            if (modelSplit[i].toLowerCase().contains("gb")) {
                posGb = i;
            }
        }
        if (posGb == -1) {
            return "";
        }
        return String.join(" ", Arrays.copyOfRange(modelSplit, 1, posGb));
    }

    public String getModelLine() {
        if (!model.contains(" ")) {
            return "";
        }
        return model.substring(0, model.indexOf(" "));
    }

    public String getGoogleSheetsName() {
        String res = quality;
        res += " " + vendor + " ";
        boolean touchLocked = false;
        String tModel = model;
        if (model.contains(" Без Отп")) {
            touchLocked = true;
            tModel = tModel.replace(" Без Отп", "");
        }
        if (!model.contains(" ")) {
            return "";
        }
        tModel = tModel.substring(0, tModel.lastIndexOf(" "));
        String[] modelSplit = tModel.split(" ");
        int posGb = -1;
        for (int i = 0; i < modelSplit.length; i++) {
            if (modelSplit[i].toLowerCase().contains("gb")) {
                posGb = i;
            }
        }
        if (posGb == -1) {
            return "";
        }
        res += String.join(" ", Arrays.copyOfRange(modelSplit, 0, posGb + 1)) + " ";
        if (touchLocked) {
            res += "Без Отп ";
        }
        res += String.join(" ", Arrays.copyOfRange(modelSplit, posGb + 1, modelSplit.length));
//        System.out.println(res + "$");
        return res;
    }

    public String getCSV(CategoryTree child, String priceName) {
        String res = "";
        if (webSiteName != null) {
            res += webSiteName;
        } else {
            res += getCategoryTreeName();
        }
        res += ";";
        if (!priceName.isEmpty()) {
            res += GadgetConst.MAP_PRICES_DESCRIPTION.get(priceName);
        }
        res += ";;RUB;";
        if (priceName.isEmpty()) {
            res += price;
        } else {
            res += Gadgets.getPrice(getPriceListName(), priceName, true);
        }
        res += ";1;0;0;";
        String present = "0";
        if (namePrefix.equals("Смартфон")) {
            present = "10";
        }
        res += present + ";;";
//        System.out.println(description);
        res += "<font maxId=\"4\"><strong>" + description.replace(",\n", "<br>")
                + "</strong></font>";
        res += ";;1;";
        res += child.name;
        res += ";;;;;;";
        res += id;
        res += ";;;;;;;;;;;;;;;";
        res += imageUrl + "\n";
        return res;
    }

    private String getDescriptionByModel(String vendor, String modelLine, String model) {
        try {
            Scanner inScanner = new Scanner(new FileInputStream(new File("C:/iSPARK/specs/" +
                    vendor + "/" + modelLine + "/" + model.replace(" ", "") + "/" + "spec.txt")));
            String res = "";
            while (inScanner.hasNextLine()) {
                res += inScanner.nextLine() + ",\n";
            }
            inScanner.close();
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("Exception: Input file not found!");
            return null;
        }
    }

    /*public String getSubModel() {
        if (!model.contains(" ")) {
            return "";
        }
        String tModel = model.replace(" Без Отп", "");
        return tModel.substring(tModel.lastIndexOf(" ") + 1, tModel.length());
    }*/

    /*public String getImageUrlByModel() {
        String[] modelSplit = getGithubName().split(" ");
        String resUrl = "https://raw.githubusercontent.com/bav735/EKZ/master";
        int gbPos = modelSplit.length;
        for (int i = 0; i < modelSplit.length; i++) {
            String part = modelSplit[i];
            if (part.toLowerCase().contains("gb")) {
                gbPos = i;
                resUrl += "/";
            }
            if (i < gbPos) {
                if (part.equals("Plus")) {
                    resUrl += part;
                } else {
                    resUrl += "/" + part;
                }
            }
            if (i > gbPos) {
                resUrl += part;
            }
        }
        resUrl += "/img.jpg";
        return resUrl;
    }*/

//    public static String formatString(String s, String tag) {
//        return s;
        /*if (tag.equals("Apple") || tag.equals("Samsung") ||
                tag.equals("Sony")) {
            return s;
        }
        String res = s.substring(0, 1).toUpperCase();
        if (s.length() > 1) {
            res = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        if (res.endsWith("gb")) {
            res = res.replace("gb", "Gb");
        }
        res = res.replace("Iphone", "iPhone");
        res = res.replace("Ipad", "iPad");
        return res;*/
//    }
}
