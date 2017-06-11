import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

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

    private static String getValueByTag(String from, String tag) {
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
//        System.out.println(from + " " + posL + " " + posR);
        return from.substring(posL + openTag.length(), posR);
    }

    private static void computeYML() throws IOException {
        Scanner inScanner = Solution.getInputScanner("categories_ids.txt");
        HashSet<String> categories = new HashSet<>();
        while (inScanner.hasNext()) {
            categories.add(inScanner.next());
        }
        inScanner.close();
        inScanner = Solution.getInputScanner("shop_items_global.xml");
        String resultYML = "";
        boolean isOffer;
        String offer;
        int offerCount = 0;
        CategoryTree categoryTree = new CategoryTree("ROOT", null);
        while (inScanner.hasNextLine()) {
            String offerLine = inScanner.nextLine();
            if (offerLine.contains("<offer ")) {
                offer = "";
                isOffer = true;
                String category = null;
                String vendor = null;
                String model = null;
                while (true) {
                    if (category == null) {
                        category = getValueByTag(offerLine, "categoryId");
                    }
                    if (vendor == null) {
                        vendor = getValueByTag(offerLine, "vendor");
                    }
                    if (model == null) {
                        model = getValueByTag(offerLine, "model");
                    }
                    offer += offerLine + "\n";
                    if (offerLine.contains("</offer>") || !inScanner.hasNextLine()) {
                        offer = offer.replaceAll("<description>[\\S\\s]+<\\/description>",
                                "<description></description>");
                        break;
                    }
                    offerLine = inScanner.nextLine();
                }
                if (categories.contains(category)) {
//                    subcategorieVendor.put(category, vendor);
//                    resultYML += offer;
                    CategoryTree subcatTree = categoryTree.getChildTree("ignore" + category, category);
                    CategoryTree vendorTree = subcatTree.getChildTree(vendor, null);
                    CategoryTree modelTree = vendorTree;
                    ArrayList<String> modelSplit = new ArrayList<>(Arrays.asList(model.split("[ \\-,]")));
                    int plusId = -1;
                    for (int i = 0; i < modelSplit.size(); i++) {
                        if (modelSplit.get(i).equals("Plus")) {
                            plusId = i;
                        }
                    }
                    if (plusId != -1) {
                        modelSplit.set(plusId - 1, modelSplit.get(plusId - 1) + " Plus");
                        modelSplit.remove(plusId);
                    }
                    for (int i = 0; i < modelSplit.size() && i < 4; i++) {
                        String modelPart = modelSplit.get(i);
                        System.out.println(modelPart);
                        if (!modelPart.isEmpty()) {
                            modelTree = modelTree.getChildTree(modelPart, null);
                            CategoryTree.count[Integer.parseInt(modelTree.catId)]++;
                        }
                    }
                }
            } else {
                isOffer = false;
            }
            if (!isOffer) {
                resultYML += offerLine + "\n";
            }
        }

        categoryTree.condenseTree();
        categoryTree.print(true, Solution.getOutputWriter("Output", "category_result.xml"));
        System.out.println(offerCount);
        Solution.writeText(Solution.getOutputWriter("Output", "shop_items_result.xml"), resultYML);
    }

    public static void main(String[] args) {
        try {
            computeYML();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gadgets.initializeFromPriceList();
        AvitoGadgets.initializeExcludeAds();

//        renamePhotosFiles(new File("C:/Users/A/Desktop/Фото Авито/Новая папка"),
//                new File("C:/Users/A/Desktop/Фото Авито/Новая папка/original"), 0);

        iphonesAvito = new AvitoGadgets();
        iphonesAvito.initializeIPhones();
        iphonesAvito.generateGadgets(0, new ArrayList<String>());
        iphonesAvito.generateFiles();

        samsungsAvito = new AvitoGadgets();
        samsungsAvito.initializeSamsungs();
        samsungsAvito.generateGadgets(0, new ArrayList<String>());
        samsungsAvito.generateFiles();

        //Youla
//        iphonesYoula = new YoulaGadgets();
//        iphonesYoula.initializeIPhones();
//        iphonesYoula.generateGadgets(0, new ArrayList<String>());
//        iphonesYoula.distributeIPhones();
//        iphonesYoula.generateFiles();

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
