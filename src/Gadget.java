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
        return namePrefix + " " + vendor + " " + model;
    }

    public String getGoogleSheetsName() {
        String quality = "";
        if (manufacturerWarranty) {
            quality += "RST";
        } else {
            quality += "EST";
        }
        return quality + " " + getGithubName();
    }

    public String getGithubName() {
        int posSpace = model.lastIndexOf(" ");
        if (posSpace == -1) {
            posSpace = model.length();
        }
        return vendor + " " + model.substring(0, posSpace);
    }

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
