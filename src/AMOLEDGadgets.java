import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class AMOLEDGadgets extends Gadgets {
    final int ADS_COUNT_MIN = 20;
    public LinkedHashMap<String, ArrayList<ArrayList<String>>> mapMetaModelGadgetsByVendor[];

    public AMOLEDGadgets() {
        companyName = AMOLED;
        initialize();
        Set<String> selectedAvitoItems = mapGadgetNamePrices.keySet();
//                = Solution.getHashSetFromInput("present_items_msk");
        GadgetsGenerator gadgetsGenerators[] = new GadgetsGenerator[GadgetConst.MODEL_LINES.size()];
        for (int modelLine = 0; modelLine < GadgetConst.MODEL_LINES.size(); modelLine++) {
            ArrayList<ArrayList<String>> gadgetAttributesVariants = new ArrayList<>();
            gadgetAttributesVariants.add(new ArrayList<String>(GadgetConst.QUALITIES));
            gadgetAttributesVariants.add(new ArrayList<String>(GadgetConst.VENDORS));
            gadgetAttributesVariants.add(new ArrayList<String>(Arrays.asList(
                    GadgetConst.MODEL_LINES.get(modelLine))));
            gadgetAttributesVariants.add(GadgetConst.MODELS[modelLine]);
            gadgetsGenerators[modelLine] = new GadgetsGenerator(
                    gadgetAttributesVariants, modelLine, this);
            gadgetsGenerators[modelLine].generateGadgets(0, new ArrayList<String>());
        }
        for (GadgetsGenerator gadgetsGenerator : gadgetsGenerators) {
            for (ArrayList<String> gadget : gadgetsGenerator.gadgets) {
                if (selectedAvitoItems.contains(getGadgetName(gadget, QUALITY, MEMORY))
                        /*|| selectedAvitoItems.contains(getGadgetName(gadget, QUALITY, COLOR))*/) {
                    gadgets.add(gadget);
                    generatePhotos(gadget);
                }
            }
        }
        mapMetaModelGadgetsByVendor = new LinkedHashMap[GadgetConst.VENDORS.size()];
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            mapMetaModelGadgetsByVendor[i] = new LinkedHashMap<>();
            String vendor = GadgetConst.VENDORS.get(i);
            for (ArrayList<String> gadget : gadgets) {
                if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals(vendor)) {
                    String metaModel = getMetaModel(gadget);
                    if (!mapMetaModelGadgetsByVendor[i].containsKey(metaModel)) {
                        mapMetaModelGadgetsByVendor[i].put(metaModel,
                                new ArrayList<ArrayList<String>>());
                    }
                    mapMetaModelGadgetsByVendor[i].get(metaModel).add(gadget);
                }
            }
        }
        mapMetaModelGadgetsByVendor = new LinkedHashMap[GadgetConst.VENDORS.size()];
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            mapMetaModelGadgetsByVendor[i] = new LinkedHashMap<>();
            String vendor = GadgetConst.VENDORS.get(i);
            for (ArrayList<String> gadget : gadgets) {
                if (gadget.get(mapGadgetAttributeNumber.get(VENDOR)).equals(vendor)) {
                    String metaModel = getMetaModel(gadget);
                    if (!mapMetaModelGadgetsByVendor[i].containsKey(metaModel)) {
                        mapMetaModelGadgetsByVendor[i].put(metaModel,
                                new ArrayList<ArrayList<String>>());
                    }
                    mapMetaModelGadgetsByVendor[i].get(metaModel).add(gadget);
                }
            }
        }
        initMapGadgetMetaModelGadgetGroups();
    }

    public String getAvitoPath(ArrayList<String> gadget) {
        String modelLine = gadget.get(mapGadgetAttributeNumber.get(MODEL_LINE));
        String model = gadget.get(mapGadgetAttributeNumber.get(MODEL));
        String color = gadget.get(mapGadgetAttributeNumber.get(COLOR));
        String res = getVendor(gadget) + "/" + modelLine + "/" + model + "/"
                + color;
        res += "/" + IMG_FILE_NAME + ".jpg";
        return res.replace(" ", "");
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

    /*public String generateXMLGlobal(int cityId) throws IOException {
        String xml = "";
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            GadgetGroup gadgetGroup = new GadgetGroup(GadgetConst.COUNTRIES.get(0),
                    ,this);
            for (String metaModel : mapMetaModelGadgetsByVendor[i].keySet()) {
                ArrayList<String> gadget = mapMetaModelGadgetsByVendor[i].get(metaModel).get(0);
                if (!(getVendor(gadget).equals("Apple") || getVendor(gadget).equals("Samsung"))
                        || getQuality(gadget).startsWith("REF")) {//УБРАТЬ КОГДА ИСТЕЧЕТ СРОК РАЗМЕЩЕНИЯ!
                    gadgetGroup.gadgets.add(gadget);
                }
            }
            gadgetGroup.initialize(-1, 1, cityId);
            xml += gadgetGroup.getXmlAd(false);
        }
        return xml;
    }*/

    private int getMetaModelAdsSize(String vendor, String metaModel, int cityId) {
        if (!GadgetConst.MAP_MODEL_ADS_PER_MONTH[cityId].containsKey(vendor + " " + metaModel)) {
            return 0;
        }
        int adsPerMonth = GadgetConst.MAP_MODEL_ADS_PER_MONTH[cityId].get(vendor +
                " " + metaModel);
        if (adsPerMonth < 31) {
            return 1;
        }
        return adsPerMonth / ADS_COUNT_MIN;
    }

    public String generateXMLArrangement(int cityId) throws IOException {
        String xml = "";
        int globalMaxCountryCount = 0;
        for (int i = 0; i < GadgetConst.VENDORS.size(); i++) {
            String vendor = GadgetConst.VENDORS.get(i);
            int prevMaxCountryCount = 0;
            if (cityId > 0) {
                prevMaxCountryCount = GadgetConst.CITIES_MAX_COUNTRIES[cityId - 1];
            }
            int maxCountryCount = 0;
            for (String metaModel : mapMetaModelGadgetsByVendor[i].keySet()) {
                maxCountryCount = Math.max(maxCountryCount, getMetaModelAdsSize(vendor, metaModel, cityId) /
                        mapMetaModelGadgetsByVendor[i].get(metaModel).size());
            }
            globalMaxCountryCount = Math.max(globalMaxCountryCount, maxCountryCount);
            System.out.println("max countries=" + maxCountryCount +
                    "prev max countries=" + prevMaxCountryCount);
            HashMap<String, ArrayList<GadgetGroup>> gadgetsByMetaModel = new HashMap<>();
            for (String metaModel : mapMetaModelGadgetsByVendor[i].keySet()) {
                gadgetsByMetaModel.put(metaModel, new ArrayList<GadgetGroup>());
            }
            //maxCountryCount или maxCountryCount + 1?
            for (int countryId = prevMaxCountryCount;
                 countryId < prevMaxCountryCount + maxCountryCount; countryId++) {
                for (String metaModel : mapMetaModelGadgetsByVendor[i].keySet()) {
                    ArrayList<GadgetGroup> gadgets = gadgetsByMetaModel.get(metaModel);
                    for (int metaModelId = 0; metaModelId < mapMetaModelGadgetsByVendor[i].get(metaModel)
                            .size() && gadgets.size() < getMetaModelAdsSize(vendor, metaModel, cityId); metaModelId++) {
                        String country = GadgetConst.COUNTRIES.get(countryId);
                        GadgetGroup gadget = new GadgetGroup(country, metaModel, this);
                        gadget.gadgets.add(mapMetaModelGadgetsByVendor[i]
                                .get(metaModel).get(metaModelId));
                        gadgets.add(gadget);
                    }
                    gadgetsByMetaModel.put(metaModel, gadgets);
                }
            }
            for (String metaModel : gadgetsByMetaModel.keySet()) {
                ArrayList<GadgetGroup> gadgets = gadgetsByMetaModel.get(metaModel);
                for (int gadgetId = 0; gadgetId < gadgets.size(); gadgetId++) {
                    GadgetGroup gadget = gadgets.get(gadgetId);
                    gadget.initialize(gadgets.size() - gadgetId - 1, gadgets.size(), cityId);
                    xml += gadget.getXmlAd(cityId, gadgets.size() > 1);
                }
            }
        }
        GadgetConst.CITIES_MAX_COUNTRIES[cityId] = globalMaxCountryCount;
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