import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by A on 09.06.2017.
 */
public class CategoryTree {
    public final static int LEAF_MIN_SIZE = 4;
    public static int baseSize = 83;
    public static int size = 6283;

    static HashMap<String, String> idsMap = new HashMap<>();
    final static String CSV_BEGIN = "Наименование;Наименование артикула;Код артикула;Валюта;Цена;Доступен для заказа;Зачеркнутая цена;Закупочная цена;В наличии;Краткое описание;Описание;Наклейка;Статус;Тип товаров;Теги;Облагается налогом;Заголовок;META Keywords;META Description;Ссылка на витрину;Адрес видео на YouTube или Vimeo;Дополнительные параметры;Производитель;Программная платформа;Тип корпуса;Кол-во SIM-карт;Размер экрана;Объем встроенной памяти;Материал;Сенсорный экран;Кол-во мегапикселов камеры;Разрешение камеры;Вес;Цвет;Изображения\n";
    final static String YML_BEGIN = "<?xml version=\"1.0\" encoding=\"utf-8\"?><!DOCTYPE yml_catalog SYSTEM \"shops.dtd\">\n" +
            "<yml_catalog date=\"2017-05-30 23:26\">\n" +
            "<shop><name>iSPARK</name>\n" +
            "<company>Интернет-магазин iSPARK</company>\n" +
            "<url>www.ispark.info</url>\n" +
            "<currencies>\n" +
            "<currency id=\"RUR\" rate=\"1\"/>\n" +
            "</currencies>\n";
    final static String YM_BEGIN = "id;available;price;currencyId;category;picture;url;name;description;manufacturer_warranty\n";
    final static String ROOT_CATEGORY = "ROOT";

    int height;
    String id;
    String name;
    ArrayList<CategoryTree> children = new ArrayList<>();
    ArrayList<Gadget> gadgets = new ArrayList<>();

    public CategoryTree(String name) {
        this.name = name;
        this.id = "0";
    }

    public CategoryTree(String name, String id, boolean isCatIdInitialized) {
        if (id == null) {
            int newId;
            if (isCatIdInitialized) {
                newId = baseSize++;
            } else {
                newId = size++;
            }
            this.id = "" + newId;
        } else {
            this.id = id;
        }
        this.name = name;
    }

    public void calcHeight(int height) {
        this.height = height;
        for (CategoryTree tree : children) {
            tree.calcHeight(height + 1);
        }
    }

    public void recalcIds() {
//        String newId = "" + Solution.counter++;
//        idsMap.put(id, newId);
//        id = newId;
        for (Gadget gadget : gadgets) {
            gadget.id = "" + Gadget.size++;
        }
        for (CategoryTree tree : children) {
            tree.recalcIds();
        }
    }

    private CategoryTree getTreeByCatName(String catName) {
        for (CategoryTree tree : children) {
            if (tree.name.equals(catName)) {
                return tree;
            }
        }
        return null;
    }

    public CategoryTree getTreeByCatId(String catId) {
        if (this.id.equals(catId)) {
            return this;
        }
        for (CategoryTree tree : children) {
            CategoryTree subTree = tree.getTreeByCatId(catId);
            if (subTree != null) {
                return subTree;
            }
        }
        return null;
    }

    public CategoryTree getTreeByCatNameOrCreate(String catName, String catId, boolean isCatIdInitialized) {
        CategoryTree tree = getTreeByCatName(catName);
        if (tree == null) {
            tree = new CategoryTree(catName, catId, isCatIdInitialized);
            children.add(tree);
        }
        return tree;
    }

    public void printYMLCategories(BufferedWriter bufferedWriter) throws IOException {
        if (height == -1) {
            for (CategoryTree child : children) {
                bufferedWriter.write("<category id=\"" + child.id + "\">" + child.name + "</category>\n");
                child.printYMLCategories(bufferedWriter);
            }
        } else {
//            Collections.sort(children, new CustomComparator());
            for (CategoryTree child : children) {
                bufferedWriter.write("<category id=\"" + child.id + "\" parentId=\"" +
                        id + "\">" + child.name + "</category>\n");
                child.printYMLCategories(bufferedWriter);
            }
        }
    }

    public void printYMLOffers(BufferedWriter bufferedWriter) throws IOException {
        for (Gadget gadget : gadgets) {
            bufferedWriter.write("<offer id=\"" + gadget.id + "\">\n");
            bufferedWriter.write("<categoryId>" + id + "</categoryId>\n");
            bufferedWriter.write("<initialCategoryId>" + gadget.initialCategoryId + "</initialCategoryId>\n");
            bufferedWriter.write("<typePrefix>" + gadget.namePrefix + "</typePrefix>\n");
            bufferedWriter.write("<model>" + gadget.model + "</model>\n");
            bufferedWriter.write("<vendor>" + gadget.vendor + "</vendor>\n");
//            bufferedWriter.write("<name>" + gadget.name + "</name>\n");
            bufferedWriter.write("<price>" + gadget.price + ".0" + "</price>\n");
            bufferedWriter.write("<description>" +
                    gadget.description.replace(",\n", "\n").replaceAll("\nМодель:.+RU\\/A", "") +
                    "</description>\n");
            bufferedWriter.write("<picture>" + gadget.imageUrl + "</picture>\n");
            if (gadget.params != null) {
                bufferedWriter.write("<param " + gadget.params + "</param>\n");
            }
            bufferedWriter.write("</offer>\n");
        }
        for (CategoryTree child : children) {
            child.printYMLOffers(bufferedWriter);
        }
    }

    public void printYMSelected(BufferedWriter bufferedWriter, HashSet<String> selectedItems) throws IOException {
        for (int i = 0; i < gadgets.size(); i++) {
            Gadget gadget = gadgets.get(i);
            if (selectedItems.contains(gadget.getGoogleSheetsName())) {
                bufferedWriter.write(gadget.id + ";true;" + gadget.price + ";RUR;Мобильные телефоны;" +
                        gadget.imageUrl + ";http://ispark.info/product/" + gadget.id + ";\"" + gadget.getWebsiteName() + "\";");
                if (gadget.manufacturerWarranty) {
                    bufferedWriter.write("\"Официальная гарантия. Оплата: в кредит, ");
                } else {
                    bufferedWriter.write("Оплата: в рассрочку - " +
                            (Solution.getNumber(gadget.price) / 60) * 10 + " руб в мес, ");
                }
                bufferedWriter.write("по карте, через Р/С, наличными.\";" +
                        gadget.manufacturerWarranty + "\n");
            }
        }
        for (CategoryTree child : children) {
            child.printYMSelected(bufferedWriter, selectedItems);
        }
    }

    public void synchronizeWithPriceList() {
        for (CategoryTree child : children) {
            for (Gadget gadget : child.gadgets) {
//                System.out.println(gadget.getPriceListName() + "$");
                if (AvitoGadgets.inPriceList(gadget.getPriceListName())) {
                    if (gadget.getSubModel().equals(GadgetConst.MAP_MODEL_SUBMODEL
                            .get(gadget.getPriceListModel()).get(1))) {
                        gadget.price = AvitoGadgets.getPriceISPARK(gadget.getPriceListName(), 1) + "";
                        gadget.description = ("Тип товара: Ростест (RST)\n").concat(gadget.description);
                        gadget.manufacturerWarranty = true;
                    } else {
                        gadget.price = AvitoGadgets.getPriceISPARK(gadget.getPriceListName(), 0) + "";
                        gadget.description = ("Тип товара: Евротест (EST)\n").concat(gadget.description);
                        if (gadget.model.contains("Без Отп")) {
                            gadget.description = ("TouchID (отпечаток пальца): не работает\n").concat(gadget.description);
                        }
                        gadget.manufacturerWarranty = false;
                    }
//                    gadget.imageUrl = gadget.getImageUrlByModel();
                }
            }
            child.synchronizeWithPriceList();
        }
    }

    public void printCSV(BufferedWriter bufferedWriter, HashSet<String> presentItems) throws IOException {
        if (height > 2) {
            Collections.sort(children, new CustomComparator(true));
        } else {
            Collections.sort(children, new CustomComparator(false));
        }
        for (CategoryTree child : children) {
            for (int i = 0; i < child.height; i++) {
                bufferedWriter.write("!");
            }
            bufferedWriter.write(child.name);
            bufferedWriter.write(";;;;;;;;;;;;1;;;;;;;");
            bufferedWriter.write(child.id);
            bufferedWriter.write(";;;;;;;;;;;;;;;\n");
            for (Gadget gadget : child.gadgets) {
                bufferedWriter.write(gadget.getWebsiteName());
                bufferedWriter.write(";;;RUB;");
                bufferedWriter.write(gadget.price);
                bufferedWriter.write(";1;0;0;");
                String present = "0";
                if (presentItems.contains(gadget.getGoogleSheetsName())) {
                    present = "3";
                }
                bufferedWriter.write(present + ";;");
                if (Solution.SHOP_ITEMS_XML.equals(Solution.MVIDEO_XML)) {
                    bufferedWriter.write("<font size=\"4\"><strong>" + gadget.description.replace(",\n", "<br>")
                            + "</strong></font>");
                } else {
                    bufferedWriter.write("<font size=\"4\"><strong>" + gadget.description.replace("\n", "<br>")
                            + "</strong></font>");
                }
                bufferedWriter.write(";;1;");
                bufferedWriter.write(child.name);
                bufferedWriter.write(";;;;;;");
                bufferedWriter.write(gadget.id);
                bufferedWriter.write(";;;;;;;;;;;;;;;");
                bufferedWriter.write(gadget.imageUrl + "\n");
            }
            child.printCSV(bufferedWriter, presentItems);
        }
    }

    public void removeLeaves() {
        boolean remove = true;
        for (CategoryTree child : children) {
            if (child.gadgets.size() == 0 || child.gadgets.size() >= LEAF_MIN_SIZE) {
                remove = false;
            }
        }
        if (remove) {
            for (CategoryTree child : children) {
                gadgets.addAll(child.gadgets);
            }
            children = new ArrayList<>();
        }
        for (CategoryTree child : children) {
            child.removeLeaves();
        }
    }

    public void condenseTree() {
        for (CategoryTree child : children) {
            while (child.children.size() == 1) {
                CategoryTree subChild = child.children.get(0);
                for (CategoryTree subSubChild : subChild.children) {
                    subSubChild.name = subChild.name + " " + subSubChild.name;
                }
                if (subChild.children.isEmpty()) {
                    child.gadgets = subChild.gadgets;
                }
                child.children = subChild.children;
            }
            child.condenseTree();
        }
    }

    public static String translit(String s) {
        HashMap<Character, String> f = new HashMap<>();
        f.put('а', "a");
        f.put('б', "b");
        f.put('в', "v");
        f.put('г', "g");
        f.put('д', "d");
        f.put('е', "e");
        f.put('ё', "e");
        f.put('ж', "zh");
        f.put('з', "z");
        f.put('и', "i");
        f.put('й', "y");
        f.put('к', "k");
        f.put('л', "l");
        f.put('м', "m");
        f.put('н', "n");
        f.put('о', "o");
        f.put('п', "p");
        f.put('р', "r");
        f.put('с', "s");
        f.put('т', "t");
        f.put('у', "u");
        f.put('ф', "f");
        f.put('х', "h");
        f.put('ц', "ts");
        f.put('ч', "ch");
        f.put('ш', "sh");
        f.put('щ', "sch");
        f.put('ъ', "");
        f.put('ы', "y");
        f.put('ь', "");
        f.put('э', "e");
        f.put('ю', "yu");
        f.put('я', "ya");
        f.put(' ', "_");
        f.put(',', "");
        f.put('/', "_");
        f.put('\\', "_");
        f.put('.', "_");
        f.put('"', "");
        f.put('\'', "");
        s = s.toLowerCase();
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (f.containsKey(c)) {
                res += f.get(c);
            } else {
                res += c;
            }
        }
        return res;
    }

    private int getMemory(String s) {
        if (s.endsWith("Gb")) {
            return Solution.getNumber(s.substring(0, s.length() - 2));
        }
        return -1;
    }

    private class CustomComparator implements Comparator<CategoryTree> {
        boolean isModel;

        public CustomComparator(boolean isModel) {
            this.isModel = isModel;
        }

        @Override
        public int compare(CategoryTree o1, CategoryTree o2) {
            if (!isModel) {
                int memory1 = getMemory(o1.name);
                int memory2 = getMemory(o2.name);
                if (memory1 != -1 && memory2 != -1) {
                    return memory1 - memory2;
                }
            /*int modelOrder1 = getModelOrder(o1.name);
            int modelOrder2 = getModelOrder(o2.name);
            if (modelOrder1 != -1 && modelOrder2 != -1) {
                return modelOrder1 - modelOrder2;
            }*/
                int k1 = getVendorOrder(o1.name);
                int k2 = getVendorOrder(o2.name);
                return k1 - k2;
            } else {
                return o1.name.compareTo(o2.name);
            }
        }
    }

    private int getModelOrder(String model) {
        /*String modelLine;
        switch (model) {
            case "iPhone":
                modelLine
        }
        if ((model.startsWith("iPhone")) && model.length() > 7) {
            model = model.substring(7);
            for (int i = 0; i < Gadgets.modelsByModelLine.get(modelLine).size(); i++) {
                if (Gadgets.modelsByModelLine.get(modelLine).get(i).contains(model)) {
                    return i;
                }
            }
            return 100;
        }*/
        return -1;
    }

    private int getVendorOrder(String vendor) {
        switch (vendor) {
            case "Apple":
                return 1;
            case "Samsung":
                return 2;
            case "Sony":
                return 3;
        }
        return 4;
    }
}
