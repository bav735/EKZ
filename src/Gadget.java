/**
 * Created by A on 14.06.2017.
 */
public class Gadget {
    String name;
    String imageUrl;
    String price;
    String vendor;
    String model;
    String description;

    public Gadget(String offer) {
        vendor = getValueByTag(offer, "vendor");
        model = getValueByTag(offer, "model");
        imageUrl = getValueByTag(offer, "picture");
        description = getValueByTag(offer, "description");
        price = getValueByTag(offer, "price");
        price = price.substring(0, price.length() - 2);
        name = getValueByTag(offer, "name") + " " + model;
//        System.out.println(description);
    }

    public static String getValueByTag(String from, String tag) {
        if (!from.contains(tag)) {
            return null;
        }
        String openTag = '<' + tag + '>';
        String closeTag = "</" + tag + ">";
        int posL = from.indexOf(openTag);
        if (posL == -1) {
            return null;
        }
        int posR = from.indexOf(closeTag);
//        String res = from.substring(posL + openTag.length(), posR);
//        if (tag.equals("vendor") || tag.equals("model")) {
//            res = formatString(res, tag);
//        }
        return from.substring(posL + openTag.length(), posR);
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
