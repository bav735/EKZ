import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by A on 31.10.2017.
 */
public class GadgetGroup extends Gadgets {
    final static int TIME_DAY_SEC = 12 * 60 * 60;
    final static int TIME_MONTH_SEC = 30 * TIME_DAY_SEC;
    final static int HOUR_BEGIN = 9;
    final static int ADS_COUNT_BORDER = 300;
    final static boolean IS_NEW_LOAD = true;
    final static int DAYS_OFFSET = 1;
    final static long MILLISECONDS_OFFSET = (10 * 60) * 1000;
    ArrayList<ArrayList<String>> gadgets;
    String country;
    int xmlDay;
    int xmlMinute;
    int xmlHour;
    int xmlSecond;
    int cityId;
    boolean isNoDate;
    int id;
    public static final Calendar CALENDAR_ZERO;
    public static final Calendar CALENDAR_CURRENT;
    public static final int DAY_NUM_GLOBAL;

    static {
        CALENDAR_CURRENT = Calendar.getInstance();
        CALENDAR_ZERO = (Calendar) CALENDAR_CURRENT.clone();
        CALENDAR_ZERO.set(Calendar.YEAR, 2017);
        CALENDAR_ZERO.set(Calendar.MONTH, 9);//october
        CALENDAR_ZERO.set(Calendar.DAY_OF_MONTH, 31);
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

    private String getVendor() {
        return gadgets.get(0).get(Gadgets.mapGadgetAttributeNumber.get(Gadgets.VENDOR));
    }

    private String getIdXML() {
        return cityId + getVendor() + id;
    }

    private String getAdTitle() {
        return "Оригинальные Смартфоны " + GadgetConst.MAP_VENDOR_TITLE.get(getVendor()) + " (" + country + ")";
    }

    private String getAdPrice() {
        return GadgetConst.MAP_VENDOR_PRICE.get(getVendor()) + "";
    }

    private void checkWhileNewLoad(Calendar calendar) {
        if (IS_NEW_LOAD) {
            if (CALENDAR_CURRENT.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                if (calendar.getTimeInMillis() < CALENDAR_CURRENT.getTimeInMillis() - MILLISECONDS_OFFSET) {
                    calendar.add(Calendar.DAY_OF_MONTH, 30);
                }
            }
        }
    }

    public String getXmlAd() {
//        System.out.println("check:" + isInitial);
        String ad = "\t<Ad>\n";
        ad += "\t\t<Id>" + getIdXML() + "</Id>\n";
        if (!isNoDate) {
            Calendar calendarCurrent = (Calendar) CALENDAR_ZERO.clone();
            int dayNumCurrentMonth = (DAY_NUM_GLOBAL - 1) % 30 + 1;
            calendarCurrent.add(Calendar.DAY_OF_MONTH, DAY_NUM_GLOBAL - dayNumCurrentMonth - 1 + xmlDay);
//            System.out.println(checkWhileNewLoad());
            if (dayNumCurrentMonth <= DAYS_OFFSET) {
                if (xmlDay > dayNumCurrentMonth + 30 - DAYS_OFFSET) {
                    calendarCurrent.add(Calendar.DAY_OF_MONTH, -30);
                }
            } else {
                if (xmlDay <= dayNumCurrentMonth - DAYS_OFFSET) {
                    calendarCurrent.add(Calendar.DAY_OF_MONTH, 30);
                }
            }
            calendarCurrent.set(Calendar.HOUR_OF_DAY, xmlHour);
            calendarCurrent.set(Calendar.MINUTE, xmlMinute);
            calendarCurrent.set(Calendar.SECOND, xmlSecond);
            checkWhileNewLoad(calendarCurrent);
            ad += "\t\t<DateBegin>" + getDateByCalendar(calendarCurrent) + "</DateBegin>\n";
        }
        ad += "\t\t<AllowEmail>Нет</AllowEmail>\n";
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
        ad += "\t\t<Title>" + getAdTitle() + "</Title>\n";
        ad += "\t\t<Description>" + getAdText() + "</Description>\n";
        ad += "\t\t<Price>" + getAdPrice() + "</Price>\n";
//        ad += "\t\t<Images>\n";
//        ad += "\t\t\t<Image url=\"" + getImageAvitoUrl(gadget) + "\"/>\n";
//        ad += "\t\t</Images>\n";
        ad += "\t</Ad>\n";
        return ad;
    }

    private String getAdText() {
        String text = "<![CDATA[";
        text += "<p>Уважаемый покупатель,<br>" +
                "Добро пожаловать в iSPARK\uD83D\uDD25";
        text += "</p><p>\uD83D\uDCA3Акция! Мега-РОЗЫГРЫШ, подарки получат ВСЕ участники конкурса (подробнее на сайте)❗</p>";
        text += "<p>\uD83D\uDC9BМы всегда идем навстречу нашим покупателям.<br>" +
                "\uD83D\uDC49Мы предлагаем вам:<br>" +
                "\uD83D\uDD39 ТРЕЙД-ИН, ОБМЕН старого телефона<br>" +
                "\uD83D\uDD39 ОПЛАТА кредитной/дебетовой КАРТОЙ<br>" +
                "\uD83D\uDD39 ОПТ, ОПЛАТА ЧЕРЕЗ Р/С (ндс, без ндс)<br>" +
                "\uD83D\uDD39 СРОЧНАЯ ДОСТАВКА в течение дня<br>" +
                "\uD83D\uDD39 САМОВЫВОЗ из розничной точки продаж<br>" +
                "\uD83D\uDD39 КРЕДИТ от ОТП Банк/Хоум-Кредит<br>" +
                "\uD83D\uDD1DМы занимаемся продажей и ремонтом цифровой электроники более 5 лет.</p>";
        text += "<p>В нашем ассортименте только 💯оригинальные ";
        text += GadgetConst.MAP_VENDOR_OFFER.get(getVendor());
        text += " всех моделей, цветов и объемов памяти!\uD83D\uDE0A</p><p>";
        for (ArrayList<String> gadget : gadgets) {
            text += getOffer(gadget, cityId);
        }
        String quality = gadgets.get(0).get(mapGadgetAttributeNumber.get(QUALITY));
        text += "-каждый аппарат " + GadgetConst.MAP_QUALITY_DESCRIPTION.get(quality);
        text += ", версия/прошивка " + country;
        text += "</p><p>✔ полностью русифицированы, работают с сим-картами любых операторов<br>";
        text += "✔ выдаем документы о вашей покупке: товарный чек и гарантийный талон<br>";
        text += "✔ в идеальном состоянии, без следов эксплуатации, подойдут как подарок<br>";
        text += "✔ перед визитом в магазин, просим уточнять актуальное наличие товара</p>";
        text += "<p>Наше местоположение\uD83C\uDF0D<br>" +
                "▶ г. Москва, ул. Сущёвский Вал, д. 5с1, время работы (пн-вс): 11.00-21.00<br>" +
                "▶ г. Москва, ул. Багратионовский пр-д, д. 7, время работы (пн-вс): 11.00-20.30<br>" +
                "▶ г. Казань, ул. Лушникова, д. 8, время работы (пн-сб): 11.00-20.30</p>";
        text += "<p>\uD83D\uDCDE Звоните: 9:00-21:00, ежедневно</p>" +
                "<p>У нас вы сможете выгодно приобрести любой интересующий вас гаджет или аксессуар!" +
                "\uD83D\uDC4D<br>" +
                "iSPARK\uD83D\uDD25";
        text += "</p>]]>";
        return text;
    }

    private String getDateByCalendar(Calendar calendar) {
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
                mapGadgetAttributeNumber.get(COLOR) + 1));
        if (getVendor().equals("Apple")) {
            offer += " TouchID работает ";
        }
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
        return getPrice(gadget, priceAttributeNames[cityId + 1]);
    }

    public void initialize(int groupId, int groupsSize, int cityId) {
        int timeIntervalSec = TIME_MONTH_SEC / groupsSize;
        xmlSecond = groupId * timeIntervalSec;
        xmlDay = (xmlSecond - 1) / TIME_DAY_SEC + 1;
        xmlSecond %= TIME_DAY_SEC;
        xmlHour = xmlSecond / 3600 + HOUR_BEGIN;
        xmlSecond %= 3600;
        xmlMinute = xmlSecond / 60;
        xmlSecond %= 60;
        id = groupId;
        isNoDate = (groupsSize == 1);
        if (groupsSize >= ADS_COUNT_BORDER) {
            xmlDay = groupId / 10 + 1;
            xmlSecond = 0;
            xmlMinute = (groupId % 10) * 80;
            xmlHour = xmlMinute / 60 + HOUR_BEGIN;
            xmlMinute %= 60;
        }
        this.cityId = cityId;
    }
}
