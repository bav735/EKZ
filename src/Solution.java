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

    private static void computeCategoryTreeFromXML() throws Exception {
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
//                System.out.println(isCatIdInitialized);
                String catId = getValueByTag(offer, "categoryId");
                Gadget gadget = new Gadget(offer/*, catId*/);
                if (categories.contains(catId) && !catId.equals("761")) {
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

        WebSiteGadgets webSiteGadgets[] = getWebSiteGadgetsArray();
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
        }

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
        root.printYMSelected(bufferedWriter, getHashSetFromInput("selected_ym_items.txt"));
        bufferedWriter.flush();
    }

    public static WebSiteGadgets[] getWebSiteGadgetsArray() {
        WebSiteGadgets webSiteGadgets[] = new WebSiteGadgets[GadgetConst.MODEL_LINES.size()];
        for (int modelLine = 0; modelLine < GadgetConst.MODEL_LINES.size(); modelLine++) {
            webSiteGadgets[modelLine] = new WebSiteGadgets(modelLine);
            webSiteGadgets[modelLine].generateGadgets(0, new ArrayList<String>());
        }
        return webSiteGadgets;
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
        for (int i = 1; i < modelSplit.size(); i++) {
            if (modelSplit.get(i).equals("Plus") ||
                    modelSplit.get(i).equals("Edge") ||
                    modelSplit.get(i).equals("Edge Plus") ||
                    modelSplit.get(i).equals("Mini") ||
                    modelSplit.get(i).equals("Cover") ||
                    modelSplit.get(i).equals("Lite") ||
//                                modelSplit.get(i).equals("Prime")||
                    modelSplit.get(i).equals("Lite")) {
                plusId = i;
                modelSplit.set(plusId - 1, modelSplit.get(plusId - 1) + " " + modelSplit.get(plusId));
            }
        }
        if (plusId != -1) {
            modelSplit.remove(plusId);
        }
    }

    public static void computeAvito() throws IOException {

        System.out.println("printing...");
        BufferedWriter writer = Solution.getOutputWriter("Output/Avito/", "AdsXML_" +
                    /*GadgetConst.CITIES_FILE_END[cityId] +*/ "msk.xml");
        writer.write("<Ads formatVersion=\"3\" target=\"Avito.ru\">\n");
        /*for (int cityId = 0; cityId < GadgetConst.CITIES.length; cityId++) {
            System.out.println("city=" + cityId);
            AvitoGadgets avitoGadgets = new AvitoGadgets(cityId);
            writer.write(avitoGadgets.generateXMLAutoload(cityId));
            writer.write(avitoGadgets.generateXMLArrangement(cityId));
            writer.write(avitoGadgets.generateXMLGlobal(cityId));
        }*/
        AvitoGadgets avitoGadgets = new AvitoGadgets();
        writer.write(avitoGadgets.generateXMLAutoload());
        writer.write("</Ads>");
        writer.flush();
    }

    public static void main(String[] args) {
        Gadgets.initializePrices(Solution.getInputScanner("AMOLED/price_list.txt"));
        try {
            computeAvito();
            computeCategoryTreeFromXML();
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
//        AvitoGadgets webSiteGadgets = new AvitoGadgets();
//        webSiteGadgets.initializeFromCSV();
//        webSiteGadgets.synchronizePrices();
//        webSiteGadgets.printCSVGadgets(getOutputWriter("Output", "shop_items.csv"));

        //Yandex-Market
//        webSiteGadgets.printYMGadgets(getOutputWriter("Output", "yandex_market_items.csv"));
////        webSiteGadgets.generateGadgetFiles();
////        galaxys.generateGadgetFiles();
        GadgetConst.GADGET_DB.commit();
        GadgetConst.GADGET_DB.close();
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
            return null;
        }
        int posR = from.lastIndexOf(closeTag);
        if (posR == -1) {
            return null;
        }
        return from.substring(posL + openTag.length(), posR);
    }

    public static String getValueByTag(String from, String tag) {
        String openTag = '<' + tag + '>';
        String closeTag = "</" + tag + ">";
        return getValueByTag(from, openTag, closeTag);
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
