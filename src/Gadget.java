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
        if (Solution.SHOP_ITEMS_XML.equals(Solution.CUSTOM_XML)) {
            id = Solution.getValueByPrefix(offer, "id=\"", '"');
        }
        if (id == null) {
            id = "" + size++;
        }
        params = Solution.getValueByTag(offer, "<param ", "</param>");
//        System.out.println(description);
    }

    public String getWebsiteName() {
        String res = namePrefix + " " + vendor + " " + model;
        String[] modelSplit = res.split(" ");
        if (namePrefix.equals("Смартфон") && model.contains("iPhone")) {
            int colorPos = modelSplit.length - 1;
            while (!modelSplit[colorPos].startsWith("A1")) {
                colorPos--;
            }
            colorPos--;
            modelSplit[colorPos] = AvitoGadgets.getLongColor(modelSplit[colorPos]);
        }
        return String.join(" ", modelSplit);
    }

    public String getGoogleSheetsName() {
        String res = getWebsiteName();
        if (manufacturerWarranty) {
            res = res.replace("Смартфон", "RST");
        } else {
            res = res.replace("Смартфон", "EST");
        }
        int posSpace = res.lastIndexOf(" ");
        if (posSpace == -1) {
            posSpace = res.length();
        }
        return res.substring(0, posSpace);
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
