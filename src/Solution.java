import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
    static Gadgets iphones;
    static Gadgets galaxys;

    private static void computeIPhones() {
        iphones = new Gadgets();
        iphones.initializeIPhones();
        iphones.generateGadgets(0, new ArrayList<String>());
        iphones.distributeIPhones();
    }

    /*public static void computeGalaxys() {
        galaxys = new Gadgets();
//        galaxys.initializeGalaxys();
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
    }*/

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
        String xml = "<Ads formatVersion=\"3\" target=\"Avito.ru\">\n";
        ArrayList<String> xmls = new ArrayList<>();
        int gadgetsPerDay = Gadgets.ADS_PER_DAY;
        for (int xmlDay = 1; xmlDay <= 30; xmlDay++) {
            for (int gadgetId = (xmlDay - 1) * gadgetsPerDay; gadgetId < xmlDay * gadgetsPerDay; gadgetId++) {
                for (int cityId = 0; cityId < Gadgets.CITIES.length; cityId++) {
                    xml += iphones.getXmlAd(gadgetId + cityId * Gadgets.ADS_PER_MONTH, xmlDay, cityId);
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
        computeIPhones();
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
