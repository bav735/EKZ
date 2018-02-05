import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by A on 31.10.2017.
 */
public class GadgetGroup {
    //INITIAL AUTOLOAD = 30.11.17 13:36
    final static int AD_TIME_DAY_SEC = 12 * 60 * 60;
    final static int AD_TIME_MONTH_SEC = 30 * AD_TIME_DAY_SEC;
    final static int HOUR_BEGIN = 9;
    final static int ADS_COUNT_BORDER = 300;
    final static int DAYS_OFFSET = 0;
    final static int HOUR_OFFSET = 2;
    final static int MINUTE_OFFSET = 33;
    ArrayList<ArrayList<String>> gadgets;
    String country;
    int countryId;
    String metaModel;
    //    String vendor;
//    String metaModel;
    int xmlDay;
    int xmlMinute;
    int xmlHour;
    int xmlSecond;
    int cityId;
    Gadgets parent;

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

    public GadgetGroup(int countryId, String metaModel, Gadgets gadgets) {
        this.countryId = countryId;
        country = GadgetConst.COUNTRIES.get(countryId);
        this.gadgets = new ArrayList<>();
        this.metaModel = metaModel;
        parent = gadgets;
    }

    private String getIdXML() {
        String lastAttr = Gadgets.MEMORY;
        if (!metaModel.contains(Gadgets.MEMORY_GB)) {
            lastAttr = Gadgets.MODEL;
        }
        String id = GadgetConst.CITIES[cityId] + GadgetConst
                .MAP_AD_ID_BEGIN.get(parent.companyName) + parent.getGadgetName(gadgets
                .get(0), Gadgets.VENDOR, lastAttr) + country;
        return id.replace(" ", "");
    }

    private String getFirstAttr() {
        String firstAttr = Gadgets.VENDOR;
        if (getVendor().equals("Apple")) {
            firstAttr = Gadgets.MODEL_LINE;
        }
        return firstAttr;
    }

    private String getLastAttr() {
        if (!metaModel.contains(Gadgets.MEMORY_GB) ||
                getVendor().equals("Samsung") ||
                parent.mapGadgetMetaModelWithoutMemorySingle.get(parent
                        .getMetaModelWithoutMemory(metaModel))) {
            return Gadgets.MODEL;
        }
        return Gadgets.MEMORY;
    }

    private String getAdTitle(int cityId) {
        String title = parent.getGadgetName(gadgets.get(0), getFirstAttr(),
                getLastAttr())
                + GadgetConst.MAP_COMPANY_AD_TITLE_END.get(parent.companyName);
//        if (parent.companyName.equals(Gadgets.AMOLED)) {
        if (cityId > 0) {
            title += " Рассрочка Оригинал";
        } else {
            title += " Оригинал";
        }
//        }
        return transformSpaceMemory(title);
    }

    private String transformSpaceMemory(String s) {
        if (metaModel.contains(" Gb")) {
            return s.replace("Gb", " Gb");
        }
        return s;
    }

    private String getAdPrice(int cityId) {
        if (parent.companyName.equals(Gadgets.ISPARK)) {
            cityId = 0;
        }
        int price1 = Solution.getNumber(getPriceByCity(gadgets.get(0), cityId));
        for (ArrayList<String> gadget : gadgets) {
            price1 = Math.min(price1, Solution.getNumber(getPriceByCity(gadget, cityId)));
        }
        return price1 + "";
    }

    public String getXmlAd(int cityId, boolean isArrangement) {
        this.cityId = cityId;
        if (gadgets.isEmpty()) {
            return "";
        }
        String ad = "\t<Ad>\n";
        ad += "\t\t<Id>" + getIdXML() + "</Id>\n";
        if (isArrangement /*!isGlobal*/) {
            ad += "\t\t<DateBegin>" + getAdDate() + "</DateBegin>\n";
        }
        ad += "\t\t<AllowEmail>Нет</AllowEmail>\n";
        ad += "\t\t<ManagerName>Оператор-консультант</ManagerName>\n";
        ad += "\t\t<ContactPhone>" + GadgetConst.MAP_COMPANY_CITIES_PHONE_NUMBERS
                .get(parent.companyName)[cityId] + "</ContactPhone>\n";
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
        /*HashSet<String> imageUrls = new HashSet<>();
        if (parent.companyName.equals(Gadgets.AMOLED)) {
            imageUrls.add(getImageAvitoUrl(gadgets.get(0)));
        } else {
            for (String url : parent.mapGadgetMetaModelWithoutMemoryImages.get(
                    parent.getMetaModelWithoutMemory(metaModel))) {
                imageUrls.add(url);
                if (imageUrls.size() >= 5) {
                    break;
                }
            }
        }
        String res = "";
        for (String url : imageUrls) {
            res += "\t\t\t<Image url=\"" + url + "\"/>\n";
        }*/
        String gadgetName = getGadgetName();
        if (!metaModel.contains(Gadgets.MEMORY_GB)) {
            gadgetName = gadgetName.substring(0, gadgetName.lastIndexOf(" "));
        }
        ArrayList<String> images = GadgetConst.MAP_GADGET_NAME_IMAGES.get(gadgetName);
        String url = images.get(0);
        images.remove(0);
        GadgetConst.MAP_GADGET_NAME_IMAGES.put(gadgetName, images);
        url = "https://raw.githubusercontent.com/bav735/iSPARK/master/tempimgs/" +
                gadgetName + " box/" + url;
        return "\t\t\t<Image url=\"" + url.replaceAll(" ", "%20") + "\"/>\n";
    }

    private String getImageAvitoUrl(ArrayList<String> gadget) {
        return "https://raw.githubusercontent.com/bav735/iSPARK/master/images_avito_actual/"
                + parent.getFullPath(gadget);
    }

    private String getAdText() {
        String text = "<![CDATA[<p>";
//        String quality = parent.getQuality(gadgets.get(0));
        text += transformSpaceMemory(parent.getGadgetName(gadgets.get(0),
                getFirstAttr(), getLastAttr()));
//        String warrantyCost = parent.getPrice(gadgets.get(0),
//                Gadgets.YEAR_WARRANTY_COST);
        text += " в магазине AMOLED\uD83C\uDF08<br>" +
                "➡У нас в продаже весь модельный ряд производителя!</p>";
        text += "<p>\uD83D\uDC9BМы всегда идем навстречу нашим покупателям.<br>" +
                "\uD83D\uDC49Мы предлагаем вам:<br>";
        if (cityId > 0) {
            text += "\uD83D\uDD39 РАССРОЧКА/КРЕДИТ от банков-партнеров<br>";
        }
        text += "\uD83D\uDD39 ТРЕЙД-ИН/ВЫКУП старого телефона<br>";
        if (cityId > 0) {
            text += "\uD83D\uDD39 СРОЧНАЯ ДОСТАВКА в течение дня<br>";
        } else {
            text += "\uD83D\uDD39 БЕСПЛАТНАЯ ДОСТАВКА в течение дня<br>";
        }
        text += "\uD83D\uDD39 ОПЛАТА кредитной/дебетовой КАРТОЙ<br>";
        text += "\uD83D\uDD39 ОПТ, ОПЛАТА ЧЕРЕЗ Р/С (ндс и без ндс)<br>";
        text += "\uD83D\uDD39 САМОВЫВОЗ из розничной точки продаж<br>";
        text += "\uD83D\uDD1DМы занимаемся продажей смартфонов более 7 лет!</p>";
        text += "<p>✅ телефон как новый, в идеальном состоянии, отличный подарок<br>";
        text += "✅ евротест, все функции протестированы, работает с любыми sim<br>";
        text += "✅ 100% оригинал, оплата только после полной вашей проверки<br>";
        text += "✅ документы к вашей покупке: товарный чек и гарантийный талон";
//        if (parent.companyName.equals(Gadgets.ISPARK)) {
//            text += "не";
//        }
//        text += "активирован, для пользования в " + country + ", " +
//                " оператор " + GadgetConst.MAP_COUNTRIES_OPERATOR.get(country) + "<br>";
//        if (cityId > 0) {
//        }
        text += "</p><p>✔ гарантия на обмен 14 дней при возникновении заводского брака<br>";
        text += "✔ по истечению 14 дней действует гарантия на ремонт на 1 год<br>";
        text += "✔ перед визитом к нам, бронируйте интересующий вас товар<br>";
        text += "✔ IMEI-номер: " + getUnusedIMEI();
        text += "</p><p>Наше местоположение\uD83C\uDF0D<br>" +
                "▶ г. Москва, ул. Новослободская, д. 26с1, время работы: 11.00-19.00<br>" +
                "▶ г. Казань, ул. Лушникова, д. 8, время работы: 11.00-19.00 (пн-сб)</p>";
        text += "<p>\uD83D\uDCDE Звоните: 9:00-21:00 (без выходных)</p>";
        text += "<p>В AMOLED\uD83C\uDF08 вы сможете выгодно купить любой " +
                "интересующий вас смартфон, а также получить массу позитивных эмоций " +
                "от покупки!\uD83D\uDC4D";
//        }
        /*} else {
            int questionNum = 1;
            if (cityId == 1) {
                text += " в рассрочку без переплат!";
            }
//            text += "<p>К купленному телефону бронь-стекло, чехол и PowerBank<br>";
            text += "<br>Ответим на популярные вопросы:<br>";
            text += questionNum++ + ") Телефон новый?<br>";
            boolean isOnlyNew = true;
            for (ArrayList<String> gadget : gadgets) {
                isOnlyNew &= !gadget.get(parent.mapGadgetAttributeNumber.get(
                        Gadgets.QUALITY)).contains(GadgetConst.CPO);
            }
            if (isOnlyNew) {
                text += "- Телефон абсолютно новый.<br>";
            } else {
                text += "- В нашем ассортименте имеются новые и официально " +
                        "восстановленные телефоны.<br>";
            }
            text += questionNum++ + ") Почему такая низкая цена?<br>";
            text += "- Цена низкая, так как телефон предназначен для пользования в " +
                    "стране " +
                    country + ". Закупочная цена таких телефонов ниже, чем у других " +
                    "моделей.<br>";
            text += questionNum++ + ") Будет ли телефон работать с российскими" +
                    " сим-картами?<br>";
            text += "- Телефон, указанный в объявлении, работает только с оператором " +
                    GadgetConst.MAP_COUNTRIES_OPERATOR.get(country) + ", однако " +
                    "в продаже имеются и другие модели.<br>";
            text += questionNum++ + ") Телефон активированный?<br>";
            text += "- Нет, статус активации вы сможете проверить на сайте" +
                    " производителя перед покупкой.<br>";
            if (metaModel.contains(Gadgets.MEMORY_GB)) {
                text += questionNum++ + ") Запчасти у телефона оригинальные?<br>";
                text += "- У телефона все запчасти оригинальные.<br>";
                text += questionNum++ + ") Аксессуары к телефону оригинальные?<br>";
                text += "- Все аксессуары к телефону оригинальные.<br>";
                text += questionNum++ + ") Есть ли гарантия производителя на телефон?<br>";
                text += "- Гарантия производителя сроком 1 год действует с момента " +
                        "активации телефона.<br>";
                text += questionNum++ + ") Какую гарантию вы даете на телефон?<br>";
                text += "- На телефон предоставляется гарантия от магазина на обмен либо" +
                        " возврат в течение 14 дней с момента покупки, а также от сервисного" +
                        " центра на бесплатный ремонт в течение 1 года с момента покупки.<br>";
                text += questionNum++ + ") Как можно купить телефон?<br>";
                text += "- Вы можете забронировать телефон для самовывоза либо заказать " +
                        "доставку. Операторы call-центра принимают заказы с 9:00 до " +
                        "21:00, без выходных. В остальное время вы можете оставить нам " +
                        "сообщение.<br>";
                text += questionNum++ + ") Есть ли срочная доставка и сколько она стоит?<br>";
                text += "- Доставка день в день осуществляется по Москве и " +
                        "Казани. Стоимость доставки по Москве составляет 400 руб в " +
                        "пределах МКАД и +30 руб за каждый км после МКАД. Стоимость " +
                        "доставки по Казани составляет 300 руб.<br>";*//* После получения и проверки " +
                    "телефона вы оплачиваете курьеру стоимость телефона и доставки.*//*
                text += questionNum++ + ") Нужна ли предоплата при отправке телефона в " +
                        "другой город?<br>";
                text += "- Необходима частичная предоплата заказа в размере 500 " +
                        "руб. Оставшуюся сумму" +
                        " вы оплачиваете после получения и проверки телефона.<br>";
                text += questionNum++ + ") Можно ли оплатить покупку картой?<br>";
                text += "- Оплатить покупку можно любой картой без комиссии.<br>";
                text += questionNum++ + ") Можно ли купить телефон в кредит или рассрочку?<br>";
                text += "- Покупка в кредит либо рассрочку возможна только в Казани" +
                        " и предоставляется банками Хоум Кредит, Тинькофф, ОТП.<br>";
                text += questionNum++ + ") Есть ли дополнительные скидки или " +
                        "подарки?<br>";
                text += "- К купленному телефону идет в подарок портативный " +
                        "PowerBank за любой отзыв о нас." +
                        " Скидка до 500 руб дается на день рождения и при покупке не " +
                        "менее двух " +
                        "телефонов.<br>";
                text += questionNum++ + ") Можно ли оплатить покупку перечислением через" +
                        " расчетный счет юридического лица?<br>";
                text += "- Оплата принимается с комиссией 5% от юридических лиц, " +
                        "работающих как" +
                        " с НДС, так и без НДС.<br>";
                text += questionNum++ + ") Можно ли сдать свой телефон на выкуп или " +
                        "обменять на новый?<br>";
                text += "- Обмен и выкуп возможны для телефонов производителей" +
                        ", представленных в нашем ассортименте.<br>";
                text += questionNum++ + ") Есть ли покупка телефонов оптом?<br>";
                text += "- Да, оптовый прайс лист высылается по запросу.<br>";
                text += questionNum++ + ") Где посмотреть характеристики телефона?<br>";
                text += "- Характеристики телефона вы можете посмотреть на сайте " +
                        "iSPARK.<br>";
            }
        }*/
        text += "</p>";
        text += "]]>";
        return text;
    }

    private String getGadgetName() {
        return parent.getGadgetName(gadgets.get(0), Gadgets.VENDOR, Gadgets.MEMORY);
    }

    private String getUnusedIMEI() {
        ArrayList<String> IMEIs = GadgetConst.MAP_GADGET_NAME_IMEIS.get(getGadgetName());
        String IMEI = IMEIs.get(0);
        IMEIs.remove(0);
        GadgetConst.MAP_GADGET_NAME_IMEIS.put(getGadgetName(), IMEIs);
        return IMEI;
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
        offer += String.join(" ", gadget.subList(Gadgets.mapGadgetAttributeNumber
                .get(Gadgets.VENDOR), Gadgets.mapGadgetAttributeNumber.get(getLastAttr()) + 1));
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
        return parent.getPrice(gadget, Gadgets.PRICE_ATTRIBUTE_NAMES[cityId
                + Gadgets.PRICE_SHIFT]);
    }

    private String getVendor() {
        return parent.getVendor(gadgets.get(0));
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
