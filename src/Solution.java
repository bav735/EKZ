import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Solution {
    static AvitoGadgets iphonesAvito;
    static AvitoGadgets samsungsAvito;
    static YoulaGadgets iphonesYoula;

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

    public static void writeText(BufferedWriter bufferedWriter, String text) {
        try {
            bufferedWriter.write(text);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private static void computeWebsite() throws IOException {
        Scanner inScanner = Solution.getInputScanner("shop_items_global.xml");
        HashSet<String> categories = new HashSet<>();
        CategoryTree root = new CategoryTree(CategoryTree.ROOT_CATEGORY, null);
        while (inScanner.hasNext()) {
            String line = inScanner.nextLine();
            if (line.contains("<category ")) {
                String[] lineSplit = line.split("[\"<>]");
                String catId = lineSplit[2];
                if (getNumber(lineSplit[4]) == -1) {
                    root.getTreeByCatNameOrCreate(lineSplit[4], catId);
                } else {
                    CategoryTree tree = root.getTreeByCatId(lineSplit[4]);
                    tree.getTreeByCatNameOrCreate(lineSplit[6], catId);
                }
                categories.add(catId);
            }
            if (line.contains("</categories>")) {
                break;
            }
        }
        String resultYML = "";
        boolean isOffer;
        String offer;
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (line.contains("<offer ")) {
                offer = "";
                isOffer = true;
                while (inScanner.hasNextLine()) {
                    offer += line + "\n";
                    if (line.contains("</offer>")) {
                        break;
                    }
                    line = inScanner.nextLine();
                }
                String catId = Gadget.getValueByTag(offer, "categoryId");
                Gadget gadget = new Gadget(offer);
                if (categories.contains(catId)) {
                    resultYML += offer;
                    CategoryTree catTree = root.getTreeByCatId(catId);
                    CategoryTree subcatTree = catTree.getTreeByCatNameOrCreate(gadget.vendor, null);
//                    int tildaCount = 0;
//                    for (int i = 0; i < gadget.model.length(); i++) {
//                        if (gadget.model.charAt(i) == '-') {
//                            tildaCount++;
//                        }
//                    }
                    ArrayList<String> modelSplit;
//                    if (tildaCount < 2) {
//                        modelSplit = new ArrayList<>(Arrays.asList(gadget.model.split("[ ,]")));
//                    } else {
                    modelSplit = new ArrayList<>(Arrays.asList(gadget.model.split("[ ,\\-]")));
//                    }
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
                    int plusId = -1;
                    for (int i = 1; i < modelSplit.size(); i++) {
                        if (modelSplit.get(i).equals("Plus") ||
                                modelSplit.get(i).equals("Edge") ||
                                modelSplit.get(i).equals("mini") ||
                                modelSplit.get(i).equals("Cover")) {
                            plusId = i;
                            modelSplit.set(plusId - 1, modelSplit.get(plusId - 1) + " " + modelSplit.get(plusId));
                        }
                    }
                    if (plusId != -1) {
                        modelSplit.remove(plusId);
                    }
                    for (int i = 0; i < modelSplit.size() && i < 4; i++) {
                        String modelPart = modelSplit.get(i);
                        if (!modelPart.isEmpty()) {
//                            modelPart = Gadget.formatString(modelPart, gadget.vendor);
                            subcatTree = subcatTree.getTreeByCatNameOrCreate(modelPart, null);
//                            System.out.println(modelTree.catName+":"+modelTree.catId);
                        }
                    }
                    boolean isPresent = true;
                    for (Gadget gadgetCurrent : subcatTree.gadgets) {
                        if (gadgetCurrent.name.equals(gadget.name)) {
                            isPresent = false;
                        }
                    }
                    if (isPresent) {
                        subcatTree.gadgets.add(gadget);
                    }
//                    System.out.println("added to " + modelTree.catId);
                }
            } else {
                isOffer = false;
            }
            if (!isOffer) {
                resultYML += line + "\n";
            }
        }
        inScanner.close();
        root.condenseTree();
        root.removeLeaves();
        root.removeLeaves();
        root.calcHeightAndCatId(-1);
        BufferedWriter bufferedWriter = Solution.getOutputWriter("Output/Website", "shop_items.csv");
        bufferedWriter.write(CategoryTree.CSV_BEGIN);
        root.printCSV(true, bufferedWriter);
//        Solution.writeText(Solution.getOutputWriter("Output/Website", "shop_items_result.xml"), resultYML);
    }

    public static void computeAvito() {
        //        renamePhotosFiles(new File("C:/Users/A/Desktop/Фото Авито/Новая папка"),
//                new File("C:/Users/A/Desktop/Фото Авито/Новая папка/original"), 0);

        Gadgets.initializeFromPriceList();
        AvitoGadgets.initializeExcludeAds();

        iphonesAvito = new AvitoGadgets();
        iphonesAvito.initializeIPhones();
        iphonesAvito.generateGadgets(0, new ArrayList<String>());
//        iphonesAvito.printGadgets(0, new ArrayList<String>());
        iphonesAvito.generateFolders();
        iphonesAvito.generateFilesRobot();

        samsungsAvito = new AvitoGadgets();
        samsungsAvito.initializeSamsungs();
        samsungsAvito.generateGadgets(0, new ArrayList<String>());
        samsungsAvito.generateFolders();
    }


    public static void main(String[] args) {
//        try {
//            computeWebsite();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        computeAvito();

        //Youla
//        iphonesYoula = new YoulaGadgets();
//        iphonesYoula.initializeIPhones();
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
////        iphonesAvito.generateGadgetFiles();
////        galaxys.generateGadgetFiles();
    }
}
