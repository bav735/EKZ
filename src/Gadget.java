import java.util.Arrays;

/**
 * Created by A on 14.06.2017.
 */
public class Gadget {
    public static int size = 103379;

    String imageUrl;
    String price;
    String vendor;
    String model;
    String description;
    String initialCategoryId;
    String id;
    String params;
    String namePrefix;
    boolean manufacturerWarranty;

    public Gadget(String offer, String initialCategoryId) {
        vendor = Solution.getValueByTag(offer, "vendor");
        model = Solution.getValueByTag(offer, "model");
        imageUrl = Solution.getValueByTag(offer, "picture");
        description = Solution.getValueByTag(offer, "description");
        price = Solution.getValueByTag(offer, "price");
        namePrefix = Solution.getValueByTag(offer, "typePrefix");
        price = price.substring(0, price.length() - 2);
        this.initialCategoryId = initialCategoryId;
        /*if (Solution.SHOP_ITEMS_XML.equals(Solution.CUSTOM_XML)) {
            id = Solution.getValueByPrefix(offer, "id=\"", '"');
        }*/
        if (id == null) {
            id = "" + size++;
        }
        params = Solution.getValueByTag(offer, "<param ", "</param>");
//        System.out.println(description);
    }

    public String getWebsiteName() {
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
        boolean touchLocked = false;
        if (model.contains(" Без Отп")) {
            touchLocked = true;
        }
        if (!model.contains(" ")) {
            return "";
        }
        if (touchLocked) {
            res += " Без Отп";
        }
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
        String res = "RFB";
        if (manufacturerWarranty) {
            res = "NEW";
        }
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

    public String getSubModel() {
        if (!model.contains(" ")) {
            return "";
        }
        String tModel = model.replace(" Без Отп", "");
        return tModel.substring(tModel.lastIndexOf(" ") + 1, tModel.length());
    }

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
