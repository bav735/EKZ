import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private static void renamePhotosFiles(File parentDir, File dirOrFile) {
        File[] subdirs = dirOrFile.listFiles();
        if (subdirs == null) {
            dirOrFile.renameTo(new File(parentDir, AvitoGadgets.IMG_FILE_NAME + ".jpg"));
        } else {
            for (File subdir : subdirs) {
                renamePhotosFiles(dirOrFile, subdir);
            }
        }
    }

    private static String getValueByTag(String from, String tag) {
        String openTag = '<' + tag + '>';
        String closeTag = "</" + tag + ">";
        int posL = from.indexOf(openTag);
        if (posL == -1) {
            return null;
        }
        int posR = from.indexOf(closeTag);
        return from.substring(posL + openTag.length(), posR);
    }

    private static void computeYML() {
        Scanner inScanner = Solution.getInputScanner("categories_ids.txt");
        HashSet<String> presentItems = new HashSet<>();
        while (inScanner.hasNext()) {
            presentItems.add(inScanner.next());
        }
        inScanner.close();
        inScanner = Solution.getInputScanner("shop_items_global.xml");
        String resultYML = "";
        boolean isOffer;
        String offer;
        int offerCount = 0;
        while (inScanner.hasNextLine()) {
            String offerLine = inScanner.nextLine();
            if (offerLine.contains("<offer ")) {
                offer = "";
                isOffer = true;
                boolean isCategoryPresent = false;
                boolean isSamsungApple = true;
                while (true) {
                    String category = getValueByTag(offerLine, "categoryId");
                    if (category != null) {
                        isCategoryPresent = presentItems.contains(category);
                    }
                    String vendor = getValueByTag(offerLine, "vendor");
                    if (vendor != null) {
                        isSamsungApple = true;
//                                vendor.contains("Samsung") ||
//                                vendor.contains("Apple") ||
//                                vendor.contains("Sony") ||
//                                vendor.contains("Xiaomi") ||
//                                vendor.contains("GoPro") ||
//                                vendor.contains("Microsoft") ||
//                                vendor.contains("Meizu");
                    }
                    offer += offerLine + "\n";
                    if (offerLine.contains("</offer>") || !inScanner.hasNextLine()) {
                        break;
                    }
                    offerLine = inScanner.nextLine();
                }
                if (isCategoryPresent && isSamsungApple) {
                    resultYML += offer;
                    offerCount++;
                    if (offerCount > 10000) {
                        break;
                    }
                }
            } else {
                isOffer = false;
            }
            if (!isOffer) {
                resultYML += offerLine + "\n";
            }
        }
        System.out.println(offerCount);
        Solution.writeText(Solution.getOutputWriter("Output", "shop_items_result.xml"), resultYML);
    }

    public static void main(String[] args) {
        computeYML();

        /*Gadgets.initializeFromPriceList();
        AvitoGadgets.initializeExcludeAds();

        renamePhotosFiles(new File(Gadgets.ROOT_DIR), new File(Gadgets.ROOT_DIR + "Apple"));

        iphonesAvito = new AvitoGadgets();
        iphonesAvito.initializeIPhones();
        iphonesAvito.generateGadgets(0, new ArrayList<String>());
        iphonesAvito.generateFiles();

        samsungsAvito = new AvitoGadgets();
        samsungsAvito.initializeSamsungs();
        samsungsAvito.generateGadgets(0, new ArrayList<String>());
        samsungsAvito.generateFiles();*/

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
