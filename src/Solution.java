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

    private static void computeYML() {
        Scanner inScanner = Solution.getInputScanner("categories_ids.txt");
        HashSet<Integer> presentItems = new HashSet<>();
        while (inScanner.hasNextInt()) {
            presentItems.add(inScanner.nextInt());
        }
        inScanner.close();
        inScanner = Solution.getInputScanner("shop_items_global.xml");
        String resultYML = "";
        boolean isOffer = false;
        String offer;
        int offerCount = 0;
        while (inScanner.hasNextLine()) {
            String line = inScanner.nextLine();
            if (line.contains("<offer id")) {
                isOffer = true;
                offer = line + "\n";
                boolean isCategoryPresent = false;
                boolean isSamsungApple = false;
                while (inScanner.hasNextLine()) {
                    String offerLine = inScanner.nextLine();
                    if (offerLine.contains("<categoryId>")) {
                        int pos = offerLine.indexOf(">");
                        int category = Integer.parseInt(offerLine.substring(pos + 1, pos + 8));
                        isCategoryPresent = presentItems.contains(category);
                    }
                    if (offerLine.contains("<name>")) {
                        isSamsungApple = offerLine.contains("Samsung") ||
                                offerLine.contains("Apple") ||
                                offerLine.contains("Sony") ||
                                offerLine.contains("Xiaomi");
                    }
                    offer += offerLine + "\n";
                    if (offerLine.contains("</offer>")) {
                        break;
                    }
                }
                if (isCategoryPresent && isSamsungApple) {
                    resultYML += offer;
                    offerCount++;
//                    if (offerCount > 3000) {
//                        break;
//                    }
                }
            } else {
                isOffer = false;
            }
            if (!isOffer) {
                resultYML += line + "\n";
            }
        }
        System.out.println(offerCount);
        Solution.writeText(Solution.getOutputWriter("Output", "shop_items_result.xml"), resultYML);
    }

    public static void main(String[] args) {
        computeYML();

        /*Gadgets.initializeFromPriceList();
        AvitoGadgets.initializeExcludeAds();

        //Avito
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
