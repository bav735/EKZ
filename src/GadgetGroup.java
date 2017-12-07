import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by A on 31.10.2017.
 */
public class GadgetGroup extends Gadgets {
    //INITIAL AUTOLOAD = 30.11.17 13:36
    final static int AD_TIME_DAY_SEC = 12 * 60 * 60;
    final static int AD_TIME_MONTH_SEC = 30 * AD_TIME_DAY_SEC;
    final static int HOUR_BEGIN = 9;
    final static int ADS_COUNT_BORDER = 300;
    final static int DAYS_OFFSET = 0;
    final static int HOUR_OFFSET = 2;
    final static int MINUTE_OFFSET = 33;
    final static String AD_TITLE_END = " Новый Гарантия";
    ArrayList<ArrayList<String>> gadgets;
    String country;
    //    String vendor;
//    String metaModel;
    int xmlDay;
    int xmlMinute;
    int xmlHour;
    int xmlSecond;
    int cityId;
    //    boolean isGlobal;
//    String id;
    public static final Calendar CALENDAR_ZERO;
    public static final Calendar CALENDAR_CURRENT;
    public static final int DAY_NUM_GLOBAL;
    final static long MILLISECONDS_HOUR = 3600 * 1000;
    final static long MILLISECONDS_OFFSET = DAYS_OFFSET * 24 * MILLISECONDS_HOUR
            + HOUR_OFFSET * MILLISECONDS_HOUR + MINUTE_OFFSET * MILLISECONDS_HOUR / 60;

    static {
        CALENDAR_CURRENT = Calendar.getInstance();
        CALENDAR_ZERO = (Calendar) CALENDAR_CURRENT.clone();
        CALENDAR_ZERO.set(Calendar.YEAR, 2017);
        CALENDAR_ZERO.set(Calendar.MONTH, 10);//november
        CALENDAR_ZERO.set(Calendar.DAY_OF_MONTH, 30);
        resetCalendar(CALENDAR_ZERO);
        Calendar calendar = (Calendar) CALENDAR_CURRENT.clone();
        resetCalendar(calendar);
        DAY_NUM_GLOBAL = (int) ((calendar.getTimeInMillis() - CALENDAR_ZERO.getTimeInMillis())
                / 1000 / 60 / 60 / 24) + 1;
    }

    private static void resetCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public GadgetGroup(String country) {
        this.country = country;
        gadgets = new ArrayList<>();
    }

    private String getIdXML() {
//        if (isGlobal) {
//            return cityId + vendor;
//        } else {
        String id = GadgetConst.CITIES[cityId] +
                getGadgetName(gadgets.get(0), QUALITY, MEMORY) + country;
        return id.replace(" ", "");
//        }
    }

    private String getFirstAttr() {
        String firstAttr = VENDOR;
        if (getVendor().equals("Apple")) {
            firstAttr = MODEL_LINE;
        }
        return firstAttr;
    }

    private String getLastAttr() {
        if (mapGadgetMetaModelSingleMemory.get(getMetaModelWithoutMemory(
                getMetaModel()))) {
            return MODEL;
        }
        return MEMORY;
    }

    private String getAdTitle(int cityId) {
//        if (isGlobal) {
//            return GadgetConst.MAP_VENDOR_TITLE.get(vendor) + " (" + country + ")";
//        }
        String title = getGadgetName(gadgets.get(0), getFirstAttr(), getLastAttr())
                + AD_TITLE_END;
        if (cityId > 0) {
            title += " Рассрочка";
        } else {
            title += " Оригинал";
        }
        return title;
    }

    private String getAdPrice(int cityId) {
        int price = Solution.getNumber(getPriceByCity(gadgets.get(0), cityId));
        for (ArrayList<String> gadget : gadgets) {
            price = Math.min(price, Solution.getNumber(getPriceByCity(gadget, cityId)));
        }
        return price + "";
    }

    public String getXmlAd(boolean isArrangement) {
        if (gadgets.isEmpty()) {
            return "";
        }
        String ad = "\t<Ad>\n";
        ad += "\t\t<Id>" + getIdXML() + "</Id>\n";
        if (isArrangement /*!isGlobal*/) {
            ad += "\t\t<DateBegin>" + getAdDate() + "</DateBegin>\n";
        }
        ad += "\t\t<AllowEmail>Да</AllowEmail>\n";
        ad += "\t\t<ManagerName>Оператор-консультант</ManagerName>\n";
        ad += "\t\t<ContactPhone>" + GadgetConst.CITIES_PHONE_NUMBERS[cityId] + "</ContactPhone>\n";
        if (cityId == 0) {
            ad += "\t\t<Region>" + GadgetConst.CITIES[cityId] + "</Region>\n";
        } else {
            ad += "\t\t<Region>Татарстан</Region>\n";
            ad += "\t\t<City>" + GadgetConst.CITIES[cityId] + "</City>\n";
        }
        ad += "\t\t<Category>Телефоны</Category>\n";
        String goodsType = getVendor();
        if (goodsType.equals("Apple")) {
            goodsType = "iPhone";
        }
        ad += "\t\t<GoodsType>" + goodsType + "</GoodsType>\n";
        ad += "\t\t<Title>" + getAdTitle(cityId) + "</Title>\n";
        ad += "\t\t<Description>" + getAdText() + "</Description>\n";
        ad += "\t\t<Price>" + getAdPrice(cityId) + "</Price>\n";
        ad += "\t\t<Images>\n";
        ad += getImageAvitoUrls();
        ad += "\t\t</Images>\n";
        ad += "\t</Ad>\n";
        return ad;
    }

    private String getImageAvitoUrls() {
        String res = "";
        HashSet<String> uniqueUrls = new HashSet<>();
        for (int i = 0; i < gadgets.size() && i < 10; i++) {
            uniqueUrls.add("\t\t\t<Image url=\"" + getImageAvitoUrl(gadgets.get(i)) + "\"/>");
        }
        for (String url : uniqueUrls) {
//            res += url + "\n";
            return url + "\n";
        }
        return "";
    }

    private String getImageAvitoUrl(ArrayList<String> gadget) {
        return "https://raw.githubusercontent.com/bav735/iSPARK/master/images_avito_actual/"
                + getFullPath(gadget);
    }

    private String getAdText() {
        String text = "<![CDATA[<p>";
        String quality = getQuality(gadgets.get(0));
//        if (!isGlobal) {
        text += getGadgetName(gadgets.get(0), getFirstAttr(), getLastAttr()) + "<br>";
        String warrantyCost = getPrice(gadgets.get(0), YEAR_WARRANTY_COST);
//        if (warrantyCost.length() > 1) {
        text += "➡в наличии в магазине AMOLED\uD83C\uDF08<br>" +
                "У нас в продаже имеется весь модельный ряд производителя!";
//        }
//        text += "➡ " + GadgetConst.MAP_QUALITY_DESCRIPTION.get(quality);
//        text += ", версия/прошивка " + country;
//        if (quality.startsWith(GadgetConst.REF)) {
//            text += "; в наличии также имеется новая ";
//            if (vendor.equals("Apple")) {
//                text += "и официально восстановленная ";
//            }
//            text += "продукция";
//        }
//        }
//        if (isGlobal) {
//            text += "Уважаемый покупатель,<br>" +
//                    "Добро пожаловать в iSPARK\uD83D\uDD25";
//            text += "</p><p>\uD83D\uDCA3Акция! Мега-РОЗЫГРЫШ iPHONE 7, подарки получат ВСЕ участники конкурса (подробности на сайте ispark info)❗</p>";
//        }
        text += "<p>\uD83D\uDC9BМы всегда идем навстречу нашим покупателям.<br>" +
                "\uD83D\uDC49Мы предлагаем вам:<br>" +
                "\uD83D\uDD39 ОПЛАТА кредитной/дебетовой КАРТОЙ<br>" +
                "\uD83D\uDD39 ТРЕЙД-ИН, ОБМЕН старого телефона<br>";
        if (cityId > 0) {
            text += "\uD83D\uDD39 РАССРОЧКА/КРЕДИТ от банков-партнеров<br>";
        }
        text += "\uD83D\uDD39 ОПТ, ОПЛАТА ЧЕРЕЗ Р/С (ндс, без ндс)<br>" +
                "\uD83D\uDD39 СРОЧНАЯ ДОСТАВКА в течение дня<br>" +
                "\uD83D\uDD39 САМОВЫВОЗ из розничной точки продаж<br>" +
                "\uD83D\uDD1DМы занимаемся продажей смартфонов более 5 лет!</p>";
        /*if (isGlobal) {
            text += "<p>В нашем ассортименте только оригинальные ";
            text += GadgetConst.MAP_VENDOR_OFFER.get(vendor);
            text += " всех моделей, цветов и объемов памяти!\uD83D\uDE0A</p><p>";
            for (ArrayList<String> gadget : gadgets) {
                text += getOffer(gadget, cityId);
            }
            text += "-каждый аппарат " + GadgetConst.MAP_QUALITY_DESCRIPTION.get(quality);
            text += ", версия/прошивка " + country;
            if (quality.startsWith(GadgetConst.REF)) {
                text += "; в наличии также имеется новая ";
                if (vendor.equals("Apple")) {
                    text += "и официально восстановленная ";
                }
                text += "продукция";
            }
        }*/
        text += "</p><p>";
        text += "✔ гарантия на обмен 25 дней при возникновения заводского брака<br>";
        text += "✔ после замены вновь дается гарантия 25 дней с момента замены<br>";
        text += "✔ стоимость расширенной гарантии на 1 год составляет " + warrantyCost + "\u20BD<br>";
        text += "✔ документы к вашей покупке: товарный чек и гарантийный талон<br>";
        text += "<p>Наше местоположение\uD83C\uDF0D<br>" + GadgetConst
                .CITIES_LOCATION[cityId] + "</p>";
        text += "<p>\uD83D\uDCDE Звоните: 9:00-21:00 (без выходных) или напишите сообщение</p><p>";
        text += "✅ запечатаны в фабричную пленку, оплата по факту вскрытия и проверки<br>";
        text += "✅ абсолютно без следов эксплуатации, отлично подойдет для подарка<br>";
        text += "✅ оригинал, активированы, для пользования в " + country + ", заблокированы" +
                " под оператора " + GadgetConst.MAP_COUNTRIES_OPERATOR.get(country) + ", " +
                "но в наличие имеются и другие модели<br>";
        text += "✅ перед визитом в магазин, просим бронировать интересующий вас товар</p>";
        text += "<p>В AMOLED\uD83C\uDF08 вы сможете выгодно купить любой " +
                "интересующий вас смартфон, а также получить массу позитивных эмоций " +
                "от покупки!\uD83D\uDC4D<br>";
        text += "</p>";
        text += "]]>";
        return text;
    }

    private String getAdDate() {
        Calendar calendar = (Calendar) CALENDAR_ZERO.clone();
        int dayNumCurrentMonth = (DAY_NUM_GLOBAL - 1) % 30 + 1;
        calendar.add(Calendar.DAY_OF_MONTH, DAY_NUM_GLOBAL - dayNumCurrentMonth - 1 + xmlDay);
        calendar.set(Calendar.HOUR_OF_DAY, xmlHour);
        calendar.set(Calendar.MINUTE, xmlMinute);
        calendar.set(Calendar.SECOND, xmlSecond);
        long calendarTimeInMillis = calendar.getTimeInMillis();
        if (calendarTimeInMillis > CALENDAR_CURRENT.getTimeInMillis()) {
            calendarTimeInMillis -= 30 * 24 * MILLISECONDS_HOUR;
            if (calendarTimeInMillis > CALENDAR_CURRENT.getTimeInMillis() - MILLISECONDS_OFFSET) {
                calendar.add(Calendar.DAY_OF_MONTH, -30);
            }
        } else if (calendarTimeInMillis < CALENDAR_CURRENT.getTimeInMillis() - MILLISECONDS_OFFSET) {
            calendar.add(Calendar.DAY_OF_MONTH, 30);
        }

        return calendar.get(Calendar.YEAR) + "-" +
                convertToTwoDigit(calendar.get(Calendar.MONTH) + 1) + "-" +
                convertToTwoDigit(calendar.get(Calendar.DAY_OF_MONTH)) +
                "T" + convertToTwoDigit(calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
                convertToTwoDigit(calendar.get(Calendar.MINUTE)) + ":" +
                convertToTwoDigit(calendar.get(Calendar.SECOND)) + "+03:00";
    }

    private String formatHourMinuteSecond(int dateElem) {
        String res = "" + dateElem;
        if (dateElem < 10) {
            res = "0" + dateElem;
        }
        return res;
    }

    private String convertToTwoDigit(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return "" + num;
    }

    private String getOffer(ArrayList<String> gadget, int cityId) {
        String offer = "➡ ";
        offer += String.join(" ", gadget.subList(mapGadgetAttributeNumber.get(VENDOR),
                mapGadgetAttributeNumber.get(getLastAttr()) + 1));
//        if (vendor.equals("Apple")) {
//            offer += " TouchID работает ";
//        }
        offer += " = " + getPriceByCity(gadget, cityId) + "\u20BD<br>";
//        String quality = gadget.get(mapGadgetAttributeNumber.get(QUALITY));
//        if (cityId == 0 || quality.equals(GadgetConst.CPO)) {
//            if (quality.equals(GadgetConst.CPO)) {
//        offer += " (" + GadgetConst.MAP_QUALITY_DESCRIPTION.get(quality) + ")";
//            }
//        }
//        offer += "<br>= " + getPrice(getGadgetName(gadget), REGIONS_PRICE) + "\u20BD от 3 шт, = " +
//                getMinOptPriceAmoled(gadget) + "\u20BD от 10шт \uD83D\uDCA3</p>";
//        offer += "высылаем по запросу полный оптовый прайс-лист\uD83D\uDCA3";
        return offer;
    }

    private String getPriceByCity(ArrayList<String> gadget, int cityId) {
        return getPrice(gadget, PRICE_ATTRIBUTE_NAMES[cityId + PRICE_SHIFT]);
    }

    private String getVendor() {
        return getVendor(gadgets.get(0));
    }

    private String getMetaModel() {
        return getMetaModel(gadgets.get(0));
    }

    public void initialize(int cityId) {
        this.cityId = cityId;
    }

    public void initialize(int xmlId, int groupsSize, int cityId) {
        if (gadgets.isEmpty()) {
            return;
        }
//        isGlobal = (xmlId == -1);
//        id = xmlId + "";
        int timeIntervalSec = AD_TIME_MONTH_SEC / groupsSize;
        xmlSecond = xmlId * timeIntervalSec;
        xmlDay = xmlSecond / AD_TIME_DAY_SEC + 1;
        xmlSecond %= AD_TIME_DAY_SEC;
        xmlHour = xmlSecond / 3600 + HOUR_BEGIN;
        xmlSecond %= 3600;
        xmlMinute = xmlSecond / 60;
        xmlSecond %= 60;
        this.cityId = cityId;
    }

    /*
    if (groupsSize >= ADS_COUNT_BORDER) {
            xmlDay = groupId / 10 + 1;
            xmlSecond = 0;
            xmlMinute = (groupId % 10) * 80;
            xmlHour = xmlMinute / 60 + HOUR_BEGIN;
            xmlMinute %= 60;
        }

    if (dayNumCurrentMonth <= DAYS_OFFSET + 1) {
            if (xmlDay > dayNumCurrentMonth + 29 - DAYS_OFFSET) {
                calendar.add(Calendar.DAY_OF_MONTH, -30);
            }
        } else {
            if (xmlDay < dayNumCurrentMonth - DAYS_OFFSET) {
                calendar.add(Calendar.DAY_OF_MONTH, 30);
            }
        }*/
}
