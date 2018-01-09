import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class Solution {
    public final static String BASE_XML = "shop_items.xml";

    public static Scanner getInputScanner(String fileName) {
        try {
            Scanner inScanner = new Scanner(new FileInputStream(new File("C:/EKZ/Input/" + fileName)));
            return inScanner;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("Exception: Input file not found!");
            return null;
        }
    }

    public static BufferedWriter getOutputWriter(String directoryName, String fileName) {
        try {
            File directory = new File(directoryName);
            File file = new File(directory, fileName.replaceAll("[\\s/]", ""));
            file.getParentFile().mkdirs();
            OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(file.getCanonicalPath())));
            return new BufferedWriter(new OutputStreamWriter(os));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public static void writeText(BufferedWriter bufferedWriter, String text) {
        try {
            bufferedWriter.write(text);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private static void renamePhotosFiles(File parentDir, File dirOrFile, int num) {
        File[] subdirs = dirOrFile.listFiles();
        if (subdirs == null) {
            dirOrFile.renameTo(new File(parentDir, "img" + num + ".jpg"));
        } else {
            for (File subdir : subdirs) {
                num++;
                renamePhotosFiles(dirOrFile, subdir, num);
            }
        }
    }

    public static int getNumber(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return -1;
        }
    }

    private static void computeCategoryTreeFromXML(ISPARKGadgets isparkGadgets)
            throws Exception {
        Scanner inScanner = Solution.getInputScanner(Solution.BASE_XML);
        HashSet<String> categories = new HashSet<>();
        CategoryTree root = new CategoryTree(CategoryTree.ROOT_CATEGORY);
        while (inScanner.hasNext()) {
            String line = inScanner.nextLine();
            if (line.contains("<category ")) {
                String catId = getValueByPrefix(line, "id=\"", '"');
                String parentCatId = getValueByPrefix(line, "parentId=\"", '"');
                String catName = getValueByPrefix(line, "\">", '<');
                if (parentCatId == null) {
                    root.getTreeByCatNameOrCreate(catName, catId);
                } else {
                    CategoryTree tree = root.getTreeByCatId(parentCatId);
                    tree.getTreeByCatNameOrCreate(catName, catId);
                }
                categories.add(catId);
            }
            if (line.contains("</categories>")) {
                break;
            }
        }
        String offer;
        HashMap<String, String> mapMetaModelTempMemory = new HashMap<>();
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (line.contains("<offer")) {
                offer = "";
                while (inScanner.hasNextLine()) {
                    offer += line + "\n";
                    if (line.contains("</offer>")) {
                        break;
                    }
                    line = inScanner.nextLine();
                }
                String catId = getValueByTag(offer, "categoryId");
                if (categories.contains(catId)) {
                    offer = offer
                            .replaceAll("\\s+", " ")
                            .replaceAll("(?i)gb", "Gb")
                            .replaceAll("3/32Gb", "32Gb")
                            .replaceAll("APPLE", "Apple")
                            .replaceAll(" 2016 ", " (2016) ")
                            .replaceAll(" 2017 ", " (2017) ")
                            .replaceAll("GALAXY", "Galaxy")
                            .replaceAll(" edge ", " Edge ")
                            .replaceAll("Sony XZ", "Sony Xperia  XZ")
                            .replaceAll("(?i)zenfone", "ZenFone")
                            .replaceAll("MOTO", "Moto")
                            .replaceAll("8\\+ ", "8 Plus ")
                            .replaceAll(" 6s ", " 6S ")
                            .replaceAll("[ (,]+[0-9A-Z/]*[0-9]RU[0-9A-Z/]*[ ),]*", "");
                    for (String memory : GadgetConst.MEMORIES) {
                        memory = memory.substring(0, memory.length() - 2);
                        offer = offer.replaceAll(memory + " Gb", memory + "Gb");
                    }
                    Gadget gadget = new Gadget(offer);
                    int maxCoincidence = 0;
                    String metaModelWithoutMemoryMax = "";
                    for (String metaModelWithoutMemory : isparkGadgets
                            .mapGadgetMetaModelWithoutMemorySingle.keySet()) {
                        if ((gadget.vendor + gadget.model).replaceAll(" ", "").toLowerCase()
                                .startsWith(metaModelWithoutMemory.replaceAll(" ", "")
                                        .toLowerCase()) &&
                                metaModelWithoutMemory.length() > maxCoincidence) {
                            maxCoincidence = metaModelWithoutMemory.length();
                            metaModelWithoutMemoryMax = metaModelWithoutMemory;
                        }
                    }
                    if (!metaModelWithoutMemoryMax.isEmpty()) {
                        String memory = getMemory(gadget.model);
                        if (!isparkGadgets.mapGadgetMetaModelWithoutMemoryImages
                                .containsKey(metaModelWithoutMemoryMax)) {
                            mapMetaModelTempMemory.put(metaModelWithoutMemoryMax, memory);
                            isparkGadgets.mapGadgetMetaModelWithoutMemoryImages.put(
                                    metaModelWithoutMemoryMax, new ArrayList<String>());
                        }
                        if (mapMetaModelTempMemory.get(metaModelWithoutMemoryMax)
                                .equals(memory)) {
                            isparkGadgets.mapGadgetMetaModelWithoutMemoryImages.get(
                                    metaModelWithoutMemoryMax).add(gadget.imageUrl);
                        }
                    }
                    ArrayList<String> modelSplit;
                    modelSplit = new ArrayList<>(Arrays.asList(gadget.model.split("[ ,\\-]")));
                    int j = 0;
                    ArrayList<String> modelSplitT = new ArrayList<>();
                    while (j < modelSplit.size()) {
                        if (Pattern.matches(".*\\p{InCyrillic}.*", modelSplit.get(j))) {
                            String s = modelSplit.get(j);
                            j++;
                            while (j < modelSplit.size() &&
                                    Pattern.matches(".*\\p{InCyrillic}.*", modelSplit.get(j))) {
                                s += " " + modelSplit.get(j);
                                j++;
                            }
                            modelSplitT.add(s);
                        } else {
                            modelSplitT.add(modelSplit.get(j));
                            j++;
                        }
                    }
                    modelSplit = modelSplitT;
                    condenseModelSplit(modelSplit);
                    condenseModelSplit(modelSplit);
                    CategoryTree catTree = root.getTreeByCatId(catId);
                    CategoryTree subcatTree = catTree.getTreeByCatNameOrCreate(gadget.vendor, null);
                    for (int i = 0; i < modelSplit.size() && i < 4; i++) {
                        String modelPart = modelSplit.get(i);
                        if (!modelPart.isEmpty()) {
                            if (modelPart.equals("S6 Edge Edge Plus")) {
                                modelPart = "S6 Edge Plus";
                            }
                            if (modelSplit.size() > 3) {
                                if (modelPart.equals("Galaxy") && modelSplit.get(i + 1).equals("Note")) {
                                    modelSplit.set(i + 2, "Note ".concat(modelSplit.get(i + 2)));
                                }
                            }
                            subcatTree = subcatTree.getTreeByCatNameOrCreate(modelPart, null);
                        }
                    }
                    boolean isPresent = true;
                    for (Gadget gadgetCurrent : subcatTree.gadgets) {
                        if (CategoryTree.translit(gadgetCurrent.getWebSiteName()).equals(
                                CategoryTree.translit(gadget.getWebSiteName()))) {
                            isPresent = false;
                        }
                    }
                    if (isPresent) {
                        subcatTree.gadgets.add(gadget);
                    }
                }
            }
        }
        inScanner.close();

        /*ISPARKGadgets webSiteGadgets[] = getWebSiteGadgetsArray();
        for (int modelLine = 0; modelLine < webSiteGadgets.length; modelLine++) {
            for (ArrayList<String> webSiteGadget : webSiteGadgets[modelLine].gadgets) {
                CategoryTree catTree = root.getTreeByCatId("761");
                int startId = Gadgets.mapGadgetAttributeNumber.get(Gadgets.VENDOR);
                CategoryTree subcatTree = catTree.getTreeByCatNameOrCreate(webSiteGadget.get(startId), null);
                int endId = Gadgets.mapGadgetAttributeNumber.get(Gadgets.COLOR);
                for (int attrId = startId + 1; attrId <= endId; attrId++) {
                    String attr = webSiteGadget.get(attrId);
                    if (attr.equals("Galaxy")) {
                        String nextAttr = webSiteGadget.get(attrId + 1);
                        if (getNumber("" + nextAttr.charAt(1)) != -1) {
                            attr += " " + nextAttr.charAt(0);
                        } else {
                            attr += " " + nextAttr.split(" ")[0];
                        }
                    }
                    subcatTree = subcatTree.getTreeByCatNameOrCreate(attr, null);
                }
                subcatTree.gadgets.add(new Gadget(webSiteGadget));
            }
        }*/

        root.condenseTree();
        root.removeLeaves();
        root.removeLeaves();
        root.calcHeight(-1);
//        root.recalcIds();
//        root.synchronizeWithPriceList();

    /*inScanner = Solution.getInputScanner("present_items_kzn.txt");
    HashSet<String> presentItems = new HashSet<>();
    while (inScanner.hasNextLine()) {
        presentItems.add(inScanner.nextLine());
    }
    inScanner.close();*/
        BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/Website", "shop_items.csv");
        bufferedWriter.write(CategoryTree.CSV_BEGIN);
        root.printCSV(bufferedWriter);
        bufferedWriter.flush();

        bufferedWriter = Solution.getOutputWriter("Output/Website", "shop_items.xml");
        bufferedWriter.write(CategoryTree.YML_BEGIN);
        bufferedWriter.write("<categories>\n");
        root.printYMLCategories(bufferedWriter);
//        bufferedWriter.write(categoriesInitial);
        bufferedWriter.write("</categories>\n");

        bufferedWriter.write("<offers>\n");
//        root.printYMLOffers(bufferedWriter);
//        bufferedWriter.write(offers);
        bufferedWriter.write("</offers></shop></yml_catalog>");
        bufferedWriter.flush();

        bufferedWriter = Solution.getOutputWriter("Output/Website", "ym_shop_items.csv");
        bufferedWriter.write(CategoryTree.YM_BEGIN);
        root.printYMSelected(bufferedWriter,

                getHashSetFromInput("selected_ym_items.txt"));
        bufferedWriter.flush();
    }

    public static LinkedHashSet<String> getHashSetFromInput(String fileName) {
        Scanner inScanner = Solution.getInputScanner(fileName);
        LinkedHashSet<String> selectedItems = new LinkedHashSet<>();
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            selectedItems.add(line);
        }
        inScanner.close();
        return selectedItems;
    }

    public static void condenseModelSplit(ArrayList<String> modelSplit) {
        int plusId = -1;
        HashSet<String> words = new HashSet<>(Arrays.asList(
                "plus",
                "edge",
                "edge plus",
                "mini",
                "cover",
                "lite",
                "prime"));
        for (int i = 1; i < modelSplit.size(); i++) {
            String word = modelSplit.get(i).toLowerCase();
            if (words.contains(word)) {
                plusId = i;
                modelSplit.set(plusId - 1, modelSplit.get(plusId - 1) + " " + modelSplit.get(plusId));
            }
        }
        if (plusId != -1) {
            modelSplit.remove(plusId);
        }
    }

    public static void computeXMLAutoload(Gadgets gadgets, String fileName)
            throws IOException {
        System.out.println("printing..." + fileName);
        BufferedWriter writer = getOutputWriter("Output/Avito/", fileName + ".xml");
        writer.write("<Ads formatVersion=\"3\" target=\"Avito.ru\">\n");
        writer.write(gadgets.generateXMLAutoload());
        writer.write("</Ads>");
        writer.flush();
    }

    public static void main(String[] args) {
        AMOLEDGadgets amoledGadgest = new AMOLEDGadgets();
        ISPARKGadgets isparkGadgets = new ISPARKGadgets();
        try {
            computeCategoryTreeFromXML(isparkGadgets);
            computeXMLAutoload(amoledGadgest, "AdsXML_msk");
//            computeXMLAutoload(isparkGadgets, "AdsXML_tat");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Youla
//        iphonesYoula = new YoulaGadgets();
//        iphonesYoula.initialize();
//        iphonesYoula.generateGadgets(0, new ArrayList<String>());
//        iphonesYoula.distributeIPhones();
//        iphonesYoula.generateFolders();

        //WebSite
//        AMOLEDGadgets webSiteGadgets = new AMOLEDGadgets();
//        webSiteGadgets.initializeFromCSV();
//        webSiteGadgets.synchronizePrices();
//        webSiteGadgets.printCSVGadgets(getOutputWriter("Output", "shop_items.csv"));

        //Yandex-Market
//        webSiteGadgets.printYMGadgets(getOutputWriter("Output", "yandex_market_items.csv"));
////        webSiteGadgets.generateGadgetFiles();
////        galaxys.generateGadgetFiles();
        amoledGadgest.GADGET_DB.commit();
        amoledGadgest.GADGET_DB.close();
        isparkGadgets.GADGET_DB.commit();
        isparkGadgets.GADGET_DB.close();
    }

    public static String getValueByPrefix(String from, String prefix, char end) {
        if (!from.contains(prefix)) {
            return null;
        }
        int pos = from.indexOf(prefix) + prefix.length();
        String res = "";
        while (pos < from.length() && from.charAt(pos) != end) {
            res += from.charAt(pos);
            pos++;
        }
        if (res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static String getValueByTag(String from, String openTag, String closeTag) {
        int posL = from.indexOf(openTag);
        if (posL == -1) {
            return "";
        }
        int posR = from.lastIndexOf(closeTag);
        if (posR == -1) {
            return "";
        }
        return from.substring(posL + openTag.length(), posR);
    }

    public static String getValueByTag(String from, String tag) {
        String openTag = '<' + tag + '>';
        String closeTag = "</" + tag + ">";
        return getValueByTag(from, openTag, closeTag);
    }

    private static String getMemory(String s) {
        String[] parts = s.split("[ +\\\\/]");
        for (String part : parts) {
            if (part.contains("Gb")) {
                return part;
            }
        }
        return "";
    }

    /*private static void filterXML() {
        String offers = "";
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (line.contains("<offer")) {
                offer = "";
                while (inScanner.hasNextLine()) {

                    offer += line + "\n";
                    if (line.contains("</offer>")) {
                        break;
                    }
                    line = inScanner.nextLine();
                }
                String tag = "categoryId";
                String catId = getValueByTag(offer, tag);
                offer = offer.replace(tag + ">" + catId + "</" + tag,
                        tag + ">" + CategoryTree.idsMap.get(catId) + "</" + tag);
                if (categories.contains(catId)) {
                    offers += offer;
                }
            }
        }
        inScanner.close();
    }*/
}
