import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by A on 09.06.2017.
 */
public class CategoryTree {
    static int size = 5003;
    static int[] count = new int[12000];
    String catId;
    String catName;
    HashMap<String, CategoryTree> children;

    public CategoryTree(String catName, String catId) {
        if (catName.equals("ROOT")) {
            Arrays.fill(count, 0);
        }
        if (catId == null) {
            this.catId = "" + size;
            size++;
        } else {
            this.catId = catId;
        }
        this.catName = catName;
        children = new HashMap<>();
    }

    public CategoryTree getChildTree(String catName, String catId) {
        if (!children.containsKey(catName)) {
            CategoryTree categoryTree = new CategoryTree(catName, catId);
            children.put(catName, categoryTree);
        }
        return children.get(catName);
    }

    public void print(boolean isLastCat, BufferedWriter bufferedWriter) throws IOException {
//        System.out.println(catId + " " + catName);
        if (children.isEmpty() && isLastCat) {
            bufferedWriter.flush();
        } else {
            int count = 1;
            for (String catName : children.keySet()) {
                boolean isLast = isLastCat && (count == children.size());
                count++;
                CategoryTree child = children.get(catName);
                if (!child.catName.startsWith("ignore")) {
                    bufferedWriter.write("<category id=\"" + child.catId + "\" parentId=\"" + this.catId + "\">" +
                            child.catName + "</category>\n");
                }
                children.get(catName).print(isLast, bufferedWriter);
            }
        }
    }

    public void condenseCategories() {
        for (String child : children.keySet()) {
            CategoryTree childTree = children.get(child);

        }
    }

    public void condenseTree() {
        boolean remove = true;
        for (String child : children.keySet()) {
            CategoryTree childTree = children.get(child);
            if (count[Integer.parseInt(childTree.catId)] == 0 ||
                    count[Integer.parseInt(childTree.catId)] > 2) {
                remove = false;
            }
        }
        if (remove) {
            children = new HashMap<>();
        }
        if (children.size() == 1) {
            for (String child : children.keySet()) {
                CategoryTree childTree = children.get(child);
                for (String subchild : childTree.children.keySet()) {
                    CategoryTree subchildTree = childTree.children.get(subchild);
                    subchildTree.catName = childTree.catName + " " + subchildTree.catName;
                }
                children = childTree.children;
            }
        }
        for (String child : children.keySet()) {
            CategoryTree childTree = children.get(child);
            childTree.condenseTree();
        }
    }
}
