import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class ISPARKGadgets extends Gadgets {

    public ISPARKGadgets() {
        companyName = ISPARK;
        initialize();
        mapGadgetMetaModelWithoutMemoryImages = new HashMap<>();
        for (String gadgetName : mapGadgetNamePrices.keySet()) {
            ArrayList<String> gadget = new ArrayList<>();
            String[] words = gadgetName.split("\\s+");
            gadget.add(words[0] + " " + words[1]);
            gadget.add(words[2]);
            gadget.add(words[3]);
            int memoryId = 0;
            String model = "";
            for (int i = 4; i < words.length; i++) {
                if (words[i].contains("Gb")) {
                    memoryId = i;
                    break;
                }
                model += words[i] + " ";
            }
            gadget.add(model.trim());
            gadget.add(words[memoryId]);
            gadget.add("");
//            System.out.println(gadget.toString());
            gadgets.add(gadget);
        }
        initMapGadgetMetaModelGadgetGroups();
    }

    public String getWebsitePath(ArrayList<String> gadget) {
        String vendor = gadget.get(mapGadgetAttributeNumber.get(VENDOR));
        String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
        String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        return vendor + "/" + modelLine + "/" + model.replace(" ", "") + "/" + color.replace(" ", "")
                + "/" + IMG_FILE_NAME + ".jpg";
    }

    public String getImageWebsiteUrl(ArrayList<String> gadget) {
        return "https://raw.githubusercontent.com/bav735/iSPARK/master/images/" + getWebsitePath(gadget);
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
//                mapMetaModelGadgetsByVendor.get(model).add(gadget2);
        }
        return mapGadgetModelGadgets;
    }

    private String getMetaModel(String modelLine, String model) {
        return modelLine + " " + model;
    }

    public String generateXMLAutoload() {
//        for (String s : mapGadgetMetaModelWithoutMemoryImages.keySet()) {
//            System.out.println(s);
//        }
        String xml = "";
        for (int cityId = 0; cityId < GadgetConst.CITIES.length; cityId++) {
            LinkedHashSet<String> metaModelsUpdate = Solution.getHashSetFromInput(
                    companyName + "/update_items_" + GadgetConst
                            .CITIES_FILE_END[cityId] + ".txt");
            for (String metaModel : metaModelsUpdate) {
                System.out.println(cityId + " " + metaModel);
                int metaModelLastId = mapMetaModelLastGadgetId.get(metaModel)
                        % GadgetConst.COUNTRIES.size();
                mapMetaModelCurrGadgetId[cityId]
                        .put(metaModel, metaModelLastId);
                mapMetaModelLastGadgetId.put(metaModel, metaModelLastId + 1);
            }
            LinkedHashSet<String> metaModelsPresent = Solution.getHashSetFromInput(
                    companyName + "/present_items_" + GadgetConst
                            .CITIES_FILE_END[cityId] + ".txt");
            for (String metaModel : metaModelsPresent) {
                if (!mapMetaModelLastGadgetId.keySet().contains(
                        metaModel)) {
                    continue;
                }
                int gadgetGroupId = mapMetaModelCurrGadgetId[cityId]
                        .get(metaModel);
                System.out.println(cityId + " " + metaModel + " " + gadgetGroupId);
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
}