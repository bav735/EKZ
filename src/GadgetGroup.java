import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by A on 31.10.2017.
 */
public class GadgetGroup extends Gadgets {
    final static int DAYS_OFFSET = 1; //установлено 11.10.17
    ArrayList<ArrayList<String>> gadgets;
    String country;
    int xmlDay;
    String dateBeginRight;
    int cityId;
    boolean isNoDate;
    int id;
    public static final Calendar CALENDAR_ZERO;
    public static final int DAY_NUM_GLOBAL;

    static {
        CALENDAR_ZERO = Calendar.getInstance();
        CALENDAR_ZERO.set(Calendar.YEAR, 2017);
        CALENDAR_ZERO.set(Calendar.MONTH, 8);//september
        CALENDAR_ZERO.set(Calendar.DAY_OF_MONTH, 15);
        setCalendarToZero(CALENDAR_ZERO);
        Calendar calendar = Calendar.getInstance();
        setCalendarToZero(calendar);
        DAY_NUM_GLOBAL = (int) ((calendar.getTimeInMillis() - CALENDAR_ZERO.getTimeInMillis())
                / 1000 / 3600 / 24) + 1;
    }

    private static void setCalendarToZero(Calendar calendar) {
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

    public String getXmlAd() {
//        System.out.println("check:" + isInitial);
        String ad = "\t<Ad>\n";
        ad += "\t\t<Id>" + getIdXML() + "</Id>\n";
        if (!isNoDate) {
            Calendar calendarZero = (Calendar) CALENDAR_ZERO.clone();
            int dayNumCurrentMonth = (DAY_NUM_GLOBAL - 1) % 30 + 1;
            calendarZero.add(Calendar.DAY_OF_MONTH, DAY_NUM_GLOBAL - dayNumCurrentMonth - 1 + xmlDay);
            if (dayNumCurrentMonth <= DAYS_OFFSET) {
                if (xmlDay > dayNumCurrentMonth + 30 - DAYS_OFFSET) {
                    calendarZero.add(Calendar.DAY_OF_MONTH, -30);
                }
            } else {
                if (xmlDay <= dayNumCurrentMonth - DAYS_OFFSET) {
                    calendarZero.add(Calendar.DAY_OF_MONTH, 30);
                }
            }
            String dateBeginLeft = getDateByCalendar(calendarZero);
            ad += "\t\t<DateBegin>" + dateBeginLeft + dateBeginRight + "</DateBegin>\n";
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
                convertToTwoDigit(calendar.get(Calendar.DAY_OF_MONTH));
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
}
