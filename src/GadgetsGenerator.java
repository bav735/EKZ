import java.util.ArrayList;

/**
 * Created by A on 07.12.2017.
 */
public class GadgetsGenerator {
    public ArrayList<ArrayList<String>> gadgetAttributesVariants;
    public ArrayList<ArrayList<String>> gadgets;
    public int globalModelLine;
    Gadgets parent;

    public GadgetsGenerator(ArrayList<ArrayList<String>> gadgetAttributesVariants,
                            int globalModelLine, Gadgets gadgets) {
        this.gadgetAttributesVariants = gadgetAttributesVariants;
        this.globalModelLine = globalModelLine;
        this.gadgets = new ArrayList<>();
        parent = gadgets;
    }

    public void generateGadgets(int attribute, ArrayList<String> gadget) {
        if (attribute == gadgetAttributesVariants.size()) {
            String model = gadget.get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.MODEL));
            for (String color : GadgetConst.MAP_MODEL_COLOR[globalModelLine].get(model)) {
                for (String memory : GadgetConst.MEMORIES) {
                    ArrayList<String> newGadget = new ArrayList<>(gadget);
                    newGadget.add(memory);
                    newGadget.add(color);
                    if (isInPriceList(newGadget)) {
                        gadgets.add(newGadget);
                    }
                }
            }
        } else {
            int attributeVariantsSize = gadgetAttributesVariants.get(attribute).size();
            for (int attributeVariant = 0; attributeVariant < attributeVariantsSize; attributeVariant++) {
                ArrayList<String> newGadget = new ArrayList<String>(gadget);
                newGadget.add(gadgetAttributesVariants.get(attribute).get(attributeVariant));
                generateGadgets(attribute + 1, newGadget);
            }
        }
    }

    public boolean isInPriceList(ArrayList<String> gadget) {
        return parent.mapGadgetNamePrices.containsKey(parent.getGadgetName(
                gadget, Gadgets.QUALITY, Gadgets.MEMORY)) ||
                parent.mapGadgetNamePrices.containsKey(parent.getGadgetName(
                        gadget, Gadgets.QUALITY, Gadgets.COLOR));
    }
}
