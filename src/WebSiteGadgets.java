import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class WebSiteGadgets extends Gadgets {
    public ArrayList<ArrayList<String>> gadgets = new ArrayList<>();
    ArrayList<ArrayList<String>> gadgetAttributesVariants;
    int globalModelLine;

    public WebSiteGadgets(int globalModelLine) {
        this.globalModelLine = globalModelLine;
        gadgetAttributesVariants = new ArrayList<ArrayList<String>>();
        gadgetAttributesVariants.add(new ArrayList<String>(GadgetConst.QUALITIES));
        gadgetAttributesVariants.add(new ArrayList<String>(GadgetConst.VENDORS));
        gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(
                GadgetConst.MODEL_LINES.get(globalModelLine))));
        gadgetAttributesVariants.add(GadgetConst.MODELS[globalModelLine]);
    }

    public static String getWebsitePath(ArrayList<String> gadget) {
        String vendor = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
        String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
        String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        return vendor + "/" + modelLine + "/" + model.replace(" ", "") + "/" + color.replace(" ", "")
                + "/" + IMG_FILE_NAME + ".jpg";
    }

    public static String getImageWebsiteUrl(ArrayList<String> gadget) {
        return "https://raw.githubusercontent.com/bav735/iSPARK/master/images/" + getWebsitePath(gadget);
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
            for (String color : GadgetConst.MAP_MODEL_COLOR[globalModelLine].get(model)) {
                for (String memory : GadgetConst.MEMORIES) {
                    ArrayList<String> newGadget = new ArrayList<>(gadget);
                    newGadget.add(memory);
                    newGadget.add(color);
                    if (!excludeModel(model, color, newGadget.get(mapGadgetAttributeNumber.get(MEMORY))) &&
                            isInPriceList(newGadget)) {
                        gadgets.add(newGadget);
                    }
                }
            }
        } else {
            int attributeVariantsSize = gadgetAttributesVariants.get(attribute).size();
            for (int attributeVariant = 0; attributeVariant < attributeVariantsSize; attributeVariant++) {
                ArrayList<String> newGadget = new ArrayList<String>(gadget);
                newGadget.add(gadgetAttributesVariants.get(attribute).get(attributeVariant));
                generateGadgets(attribute + 1, newGadget);
            }
        }
    }

    public boolean excludeModel(String model, String color, String memory) {
        return false;
//                model.contains("7") &&memory.contains("32") && color.toLowerCase().contains("red");
    }

    private String getGadgetPathSite(ArrayList<String> gadget, String color) {
        String path = "";
        for (int i = mapGadgetAttributeNumber.get(VENDOR); i <= mapGadgetAttributeNumber.get(MODEL); i++) {
            String attr = gadget.get(i);
            path += attr + "/";
        }
        return (path + color + "/").replace(" ", "");
    }

    private HashMap<String, ArrayList<ArrayList<String>>> getModelGadgetMap(ArrayList<ArrayList<String>> gadgets) {
        HashMap<String, ArrayList<ArrayList<String>>> mapGadgetModelGadgets = new HashMap<>();

        for (ArrayList<String> gadget : gadgets) {
            String metaModel = getMetaModel(gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE)),
                    gadget.get(mapGadgetAttributeNumber.get(MODEL)));
            if (!mapGadgetModelGadgets.containsKey(metaModel)) {
                mapGadgetModelGadgets.put(metaModel, new ArrayList<ArrayList<String>>());
//                System.out.println(metaModel + "|");
            }
            mapGadgetModelGadgets.get(metaModel).add(gadget);
//                ArrayList<String> gadget2 = new ArrayList<>(gadget);
//                gadget2.set(mapGadgetAttributeNumber.get(QUALITY), EST2);
//                mapGadgetMetaModelGadgetsByVendor.get(model).add(gadget2);
        }
        return mapGadgetModelGadgets;
    }

    private String getMetaModel(String modelLine, String model) {
        return modelLine + " " + model;
    }
}