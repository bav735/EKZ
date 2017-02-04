import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Solution {
    static Gadgets iphones[] = new Gadgets[Gadgets.CITIES.length];
    static Gadgets galaxys;

    private static void computeIPhones(int cityId) {
        iphones[cityId] = new Gadgets();
        iphones[cityId].initializeIPhones(cityId);
        try {
            Scanner inScanner = new Scanner(new FileInputStream("input.txt"));
            ArrayList<String> iphonesPriceListNames = new ArrayList<String>();
            ArrayList<String> pricesMonthWarranty = new ArrayList<>();
            ArrayList<String> pricesYearWarranty = new ArrayList<>();
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine();
                String[] words = line.split(" ");
                if (!words[1].equals("iPhone")) {
                    continue;
                }
                int i = 0;
                String name = "Apple";
                do {
                    i++;
                    name += " " + words[i];
                } while (!words[i].contains("Gb"));
                if (words[i + 1].equals("Без")) {
                    name += " Б/О";
                }
                iphonesPriceListNames.add(name);
                pricesYearWarranty.add(words[words.length - Gadgets.CITIES.length - 1]);
                pricesMonthWarranty.add(words[words.length - Gadgets.CITIES.length + cityId]);
            }
            iphones[cityId].initializePrices(pricesMonthWarranty, pricesYearWarranty, iphonesPriceListNames);
            iphones[cityId].generateGadgets(0, new ArrayList<String>());
            iphones[cityId].generateGadgetFiles();
            inScanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Exception: Input file not found!");
        }
    }

    public static void computeGalaxys() {
        galaxys = new Gadgets();
        galaxys.initializeGalaxys();
        try {
            Scanner inScanner = new Scanner(new FileInputStream("input.txt"));
            ArrayList<String> galaxyPriceListNames = new ArrayList<String>();
            ArrayList<String> onePrices = new ArrayList<>();
            ArrayList<String> secondPrices = new ArrayList<>();
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine();
                String[] words = line.split(" ");
                if (!words[1].equals("Galaxy")) {
                    continue;
                }
                int i = 0;
                String name = "Samsung";
                do {
                    i++;
                    name += " " + words[i];
                } while (!words[i].contains("Gb"));
                galaxyPriceListNames.add(name);
                onePrices.add(words[words.length - 2]);
                secondPrices.add(words[words.length - 1]);
            }
            galaxys.initializePrices(onePrices, secondPrices, galaxyPriceListNames);
            galaxys.generateGadgets(0, new ArrayList<String>());
            galaxys.generateGadgetFiles();
            inScanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Exception: Input file not found!");
        }
    }

    public static void computeYMIdIncremental() {
        Scanner inScanner = null;
        BufferedWriter outWriter = null;
        try {
            inScanner = new Scanner(new FileInputStream("price_list.xml"));
            File directory = new File("Output");
            String fileName = "price_list.xml";
            File file = new File(directory, fileName.replaceAll("[\\s/]", ""));
            file.getParentFile().mkdirs();
            OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(file.getCanonicalPath())));
            outWriter = new BufferedWriter(new OutputStreamWriter(os));
            String inputs = "";
            String line;
            int count = 1;
            while (inScanner.hasNextLine()) {
                line = inScanner.nextLine();
                if (line.contains("<offer id='")) {
                    line = "\t\t<offer id='" + count + "' available='true' bid='1' cbid='1'>";
                    count++;
                }
                inputs += line + "\n";
            }
            System.out.print(inputs);
            outWriter.write(inputs.replace("'", "\""));
            outWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inScanner != null) {
                inScanner.close();
            }
        }
    }

    public static void computeYMUpperCase() {
        BufferedReader br = null;
        BufferedWriter outWriter = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("price_list.xml")));
            File directory = new File("Output");
            String fileName = "price_list.xml";
            File file = new File(directory, fileName.replaceAll("[\\s/]", ""));
            file.getParentFile().mkdirs();
            OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(file.getCanonicalPath())));
            outWriter = new BufferedWriter(new OutputStreamWriter(os));
            String inputs = "";
            String line;
            while ((line = br.readLine()) != null) {
                inputs += line + "\n";
            }
            char[] inputc = inputs.toCharArray();

            for (int i = 2; i < inputc.length; i++) {
                boolean isRusCurr = Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(inputc[i]));
                boolean isRusPrev = Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(inputc[i - 1]));
                boolean isRusPrevPrev = Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(inputc[i - 2]));
                if (isRusCurr && (isRusPrev || isRusPrevPrev)) {
                    inputc[i] = Character.toLowerCase(inputc[i]);
                }
            }
            outWriter.write(new String(inputc));
            outWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void computeYMDescription() {
        BufferedReader br = null;
        BufferedWriter outWriter = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("price_list.xml")));
            File directory = new File("Output");
            String fileName = "price_list.xml";
            File file = new File(directory, fileName.replaceAll("[\\s/]", ""));
            file.getParentFile().mkdirs();
            OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(file.getCanonicalPath())));
            outWriter = new BufferedWriter(new OutputStreamWriter(os));
            String inputs = "";
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("<description>")) {
                    boolean isDesc = false;
                    while (!isDesc) {
                        char[] inputc = line.toCharArray();
                        for (int i = 0; i < inputc.length; i++) {
                            if (Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(inputc[i]))) {
                                isDesc = true;
                                break;
                            }
                        }
                        if (!isDesc) {
                            line = br.readLine();
                        }
                    }
                    String description = line.substring(line.indexOf(">") + 1, line.length());
                    description = description.replace("p&amp;", "");
                    description = description.replace("amp", "");
                    description = description.replace("lt", "");
                    description = description.replace("gt", "");
                    description = description.replace("nbsp", "");
                    description = description.replace("span", "");
                    description = description.replace("ndash", "");
                    description = description.replace(";", "");
                    description = description.replace("&", "");
                    description = description.replace("/", "");
                    description = description.replace("br br", "");
                    description = description.replace("br ", "\n");
                    description = description.replace("  ", " ");
                    while (!line.contains("</description>")) {
                        line = br.readLine();
                    }
                    inputs += "\t\t\t<description>" + description + "</description>";
                } else {
                    inputs += line;
                }
                inputs += "\n";
            }
            outWriter.write(inputs);
            outWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void computeXML() {
//        int iphonesSize = iphones.gadgets.size();
//        int galaxysSize = galaxys.gadgets.size();
//        System.out.print(iphonesSize + " " + galaxysSize);
//        Gadgets gadgets = iphones;//new Gadgets();
//        int[] daySequence = new int[]{2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1};
//        int iphoneI = 0;
//        int galaxyI = 0;
//        for (int day = 0; day < Gadgets.DAYS_COUNT; day++) {
//            for (int aDaySequence : daySequence) {
//                if (aDaySequence % 2 == 0) {
//                    gadgets.gadgets.add(galaxys.gadgets.get(galaxyI));
//                    galaxyI++;
//                } else {
//                    gadgets.gadgets.add(iphones.gadgets.get(iphoneI));
//                    iphoneI++;
//                }
//            }
//        }
//        System.out.println(gadgets.gadgets.size());
//        for (ArrayList<String> gadget : gadgets.gadgets) {
//            System.out.println(gadget);
//        }
        String xml = "<Ads formatVersion=\"3\" target=\"Avito.ru\">\n";
        ArrayList<String> xmls = new ArrayList<>();

//        Gadgets gadgets = iphones[cityId];
        int gadgetsPerDay = 16;//gadgets.gadgets.size() / Gadgets.DAYS_COUNT;
        for (int xmlDay = 1; xmlDay <= 30; xmlDay++) {
            for (int gadgetId = (xmlDay - 1) * gadgetsPerDay; gadgetId < xmlDay * gadgetsPerDay; gadgetId++) {
                for (int cityId = 0; cityId < Gadgets.CITIES.length; cityId++) {
//                    xmls.add(gadgets.getXmlAd(gadgetId, xmlDay, cities[cityId]));
                    xml += iphones[cityId].getXmlAd(gadgetId, xmlDay, Gadgets.CITIES[cityId]);
                }
            }
        }

        xml += "</Ads>";
        File directory = new File("Output");
        String fileName = "AdsXML.xml";
        File file = new File(directory, fileName.replaceAll("[\\s/]", ""));
        file.getParentFile().mkdirs();
        BufferedWriter outWriter = null;
        try {
            OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(file.getCanonicalPath())));
            outWriter = new BufferedWriter(new OutputStreamWriter(os));
            outWriter.write(xml);
            outWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void computePhotos() {
        File photosDir = new File("C:/AMOLED/iPhone");
        File[] subdirList = photosDir.listFiles();
        for (File subDir : subdirList) {
//        File subDir = new File("C:/AMOLED/iPhone/6 Plus 64Gb БО1");
            File[] photos = subDir.listFiles();
            for (int i = 0; i < photos.length; i++) {
                photos[i].renameTo(new File(subDir, "A" + (1510 + i) + ".jpg"));
            }
        }
    }

    public static void main(String[] args) {
        Gadgets.initialize();
        for (int i = 0; i < Gadgets.CITIES.length; i++) {
            computeIPhones(i);
        }
//        computeGalaxys();
        computeXML();
//        iphones.generateGadgetFiles();
//        galaxys.generateGadgetFiles();
//        computeYMIdIncremental();
//        computeYMDescription();
//        computeYMUpperCase();
//        computePhotos();
    }
}
