import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Solution {
//    public final static String MVIDEO_XML = "shop_items_global.xml";
//    public final static String CUSTOM_XML = "shop_items.xml";
    public final static String BASE_XML = "shop_items.xml";
//    public static String SHOP_ITEMS_XML = CUSTOM_XML;

//    public static int counter = 0;

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
                    root.getTreeByCatNameOrCreate(catName, catId, true);
                } else {
                    CategoryTree tree = root.getTreeByCatId(parentCatId);
                    tree.getTreeByCatNameOrCreate(catName, catId, true);
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
                boolean isCatIdInitialized = getValueByTag(offer, "categoryId") != null;
//                System.out.println(isCatIdInitialized);
                String catId;
                if (Solution.SHOP_ITEMS_XML.equals(Solution.CUSTOM_XML)) {
                    catId = getValueByTag(offer, "initialCategoryId");
                } else {
                    catId = getValueByTag(offer, "categoryId");
                }
                Gadget gadget = new Gadget(offer, catId);
                if (categories.contains(catId)) {
                    CategoryTree catTree = root.getTreeByCatId(catId);
                    CategoryTree subcatTree;
                    subcatTree = catTree.getTreeByCatNameOrCreate(gadget.vendor, null, isCatIdInitialized);
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
                    if (gadget.namePrefix.equals("Смартфон") && (gadget.model.contains("Galaxy S") ||
                            gadget.model.contains("Galaxy A") ||
                            gadget.model.contains("Galaxy J") ||
                            gadget.model.contains("Galaxy E"))) {
                        int pos = gadget.model.indexOf("Galaxy ");
                        if (getNumber(gadget.model.charAt(pos + 8) + "") != -1) {
                            for (int galaxyId = 0; galaxyId < modelSplit.size(); galaxyId++) {
                                if (modelSplit.get(galaxyId).equals("Galaxy")) {
                                    modelSplit.add(galaxyId + 1, gadget.model.charAt(pos + 7) + "");
                                }
                            }
                        }
                    }
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
                            subcatTree = subcatTree.getTreeByCatNameOrCreate(modelPart, null, isCatIdInitialized);
                        }
                    }
                    boolean isPresent = true;
                    if (SHOP_ITEMS_XML.equals(MVIDEO_XML)) {
                        for (Gadget gadgetCurrent : subcatTree.gadgets) {
                            if (gadgetCurrent.getWebsiteName().equals(gadget.getWebsiteName()) &&
                                    gadget.imageUrl.equals(gadgetCurrent.imageUrl)) {
                                isPresent = false;
                            }
                        }
                    }
                    if (isPresent) {
                        subcatTree.gadgets.add(gadget);
//                        System.out.println(gadget.getWebsiteName());
                    }
                }
            }
        }
        inScanner.close();
        root.condenseTree();
        root.removeLeaves();
        root.removeLeaves();
        root.calcHeight(-1);
//        root.recalcIds();
        root.synchronizeWithPriceList();

        inScanner = Solution.getInputScanner("present_items.txt");
        HashSet<String> presentItems = new HashSet<>();
        while (inScanner.hasNextLine()) {
            presentItems.add(inScanner.nextLine());
        }
        inScanner.close();
        BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/Website", "shop_items.csv");
        bufferedWriter.write(CategoryTree.CSV_BEGIN);
        root.printCSV(bufferedWriter, presentItems);
        bufferedWriter.flush();

        bufferedWriter = Solution.getOutputWriter("Output/Website", "shop_items.xml");
        bufferedWriter.write(CategoryTree.YML_BEGIN);
        bufferedWriter.write("<categories>\n");
        root.printYMLCategories(bufferedWriter);
//        bufferedWriter.write(categoriesInitial);
        bufferedWriter.write("</categories>\n");

        bufferedWriter.write("<offers>\n");
        root.printYMLOffers(bufferedWriter);
//        bufferedWriter.write(offers);
        bufferedWriter.write("</offers></shop></yml_catalog>");
        bufferedWriter.flush();

        inScanner = Solution.getInputScanner("selected_ym_items.txt");
        HashSet<String> selectedItems = new HashSet<>();
        while (inScanner.hasNextLine()) {
            selectedItems.add(inScanner.nextLine());
        }
        inScanner.close();
        bufferedWriter = Solution.getOutputWriter("Output/Website", "ym_shop_items.csv");
        bufferedWriter.write(CategoryTree.YM_BEGIN);
        root.printYMSelected(bufferedWriter, selectedItems);
        bufferedWriter.flush();
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
        for (int modelLine = 0; modelLine < GadgetConst.MODEL_LINES.size(); modelLine++) {
            AvitoGadgets avitoGadgets = new AvitoGadgets(modelLine);
            avitoGadgets.initialize();
            avitoGadgets.generateGadgets(0, new ArrayList<String>());
            avitoGadgets.generateXML();
        }
    }


    public static void main(String[] args) {
        Gadgets.initializePrices(Solution.getInputScanner("price_list.txt"));

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
//        WebSiteGadgets webSiteGadgets = new WebSiteGadgets();
//        webSiteGadgets.initializeFromCSV();
//        webSiteGadgets.synchronizePrices();
//        webSiteGadgets.printCSVGadgets(getOutputWriter("Output", "shop_items.csv"));

        //Yandex-Market
//        webSiteGadgets.printYMGadgets(getOutputWriter("Output", "yandex_market_items.csv"));
////        avitoGadgets.generateGadgetFiles();
////        galaxys.generateGadgetFiles();
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
