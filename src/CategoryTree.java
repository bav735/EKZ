import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by A on 09.06.2017.
 */
public class CategoryTree {
    final static String CSV_BEGIN = "Наименование;Наименование артикула;Код артикула;Валюта;Цена;Доступен для заказа;Зачеркнутая цена;Закупочная цена;В наличии;Краткое описание;Описание;Наклейка;Статус;Тип товаров;Теги;Облагается налогом;Заголовок;META Keywords;META Description;Ссылка на витрину;Адрес видео на YouTube или Vimeo;Дополнительные параметры;Производитель;Программная платформа;Тип корпуса;Кол-во SIM-карт;Размер экрана;Объем встроенной памяти;Материал;Сенсорный экран;Кол-во мегапикселов камеры;Разрешение камеры;Вес;Цвет;Изображения\n";
    final static String ROOT_CATEGORY = "ROOT";
    static int size = 5003;
    int height;
    String catId;
    String catName;
    ArrayList<CategoryTree> children = new ArrayList<>();
    ArrayList<Gadget> gadgets = new ArrayList<>();

    public CategoryTree(String catName, String catId) {
        if (catId == null) {
            this.catId = "" + size;
            size++;
//            System.out.println("size"+" "+size);
        } else {
            this.catId = catId;
        }
        this.catName = catName;
    }

    public void calcHeightAndCatId(int height) {
        this.height = height;
        for (CategoryTree tree : children) {
            tree.calcHeightAndCatId(height + 1);
        }
    }

    private CategoryTree getTreeByCatName(String catName) {
        for (CategoryTree tree : children) {
            if (tree.catName.equals(catName)) {
                return tree;
            }
        }
        return null;
    }

    public CategoryTree getTreeByCatId(String catId) {
        if (this.catId.equals(catId)) {
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

    public CategoryTree getTreeByCatNameOrCreate(String catName, String catId) {
        CategoryTree tree = getTreeByCatName(catName);
        if (tree == null) {
            tree = new CategoryTree(catName, catId);
            children.add(tree);
        }
        return tree;
    }

    public void printYML(boolean isLastCat, BufferedWriter bufferedWriter) throws IOException {
        if (children.isEmpty() && isLastCat) {
            bufferedWriter.flush();
        } else {
            int countCat = 1;
            for (CategoryTree child : children) {
                System.out.println(child.catName + ":" + child.gadgets.size());
                boolean isLast = isLastCat && (countCat == children.size());
                countCat++;
                child.printYML(isLast, bufferedWriter);
            }
        }
    }

    public void printCSV(boolean isLastCat, BufferedWriter bufferedWriter) throws IOException {
        if (children.isEmpty() && isLastCat) {
            bufferedWriter.flush();
        } else {
            int countCat = 1;
            if (height > 1) {
                Collections.sort(children, new CustomComparator());
            }
            for (CategoryTree child : children) {
                boolean isLast = isLastCat && (countCat == children.size());
                countCat++;
                for (int i = 0; i < child.height; i++) {
                    bufferedWriter.write("!");
                }
                bufferedWriter.write(child.catName);
                bufferedWriter.write(";;;;;;;;;;;;1;;;;;;;");
                bufferedWriter.write(child.catId);
                bufferedWriter.write(";;;;;;;;;;;;;;;\n");
                for (Gadget gadget : child.gadgets) {
                    bufferedWriter.write(gadget.name);
                    bufferedWriter.write(";;;RUB;");
                    bufferedWriter.write(gadget.price);
                    bufferedWriter.write(";1;0;0;0;;;;1;");
                    bufferedWriter.write(child.catName);
                    bufferedWriter.write(";;;;;;");
                    bufferedWriter.write(translit(gadget.name));
                    bufferedWriter.write(";;;;;;;;;;;;;;;");
                    bufferedWriter.write(gadget.imageUrl + "\n");
                }
                child.printCSV(isLast, bufferedWriter);
            }
        }
    }

    public void removeLeaves() {
        boolean remove = true;
        for (CategoryTree child : children) {
            if (child.gadgets.size() == 0 || child.gadgets.size() > 2) {
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
                    subSubChild.catName = subChild.catName + " " + subSubChild.catName;
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
        @Override
        public int compare(CategoryTree o1, CategoryTree o2) {
            int memory1 = getMemory(o1.catName);
            int memory2 = getMemory(o2.catName);
            if (memory1 != -1 && memory2 != -1) {
                return memory1 - memory2;
            }
            return o1.catName.compareTo(o2.catName);
        }
    }
}
