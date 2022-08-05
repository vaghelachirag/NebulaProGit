package com.nebulacompanies.ibo.util;


/**
 * Created by Palak Mehta on 11-Jan-18.
 */


public class Constants {

    public static final int REQUEST_STATUS_CODE_SUCCESS = 1;
    public static final int REQUEST_STATUS_CODE_NO_RECORDS = 0;
    public static final int REQUEST_STATUS_CODE_ERROR = -1;
    public static final int REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE = -2;

    public final static String NEBULA_URL = "http://203.88.139.169:9065";

    //INCOME LIST ID
    public static final int ID_RETAIL_INCOME = 2;
    public static final int ID_STAR_POOL_INCOME = 3;
    public static final int ID_THREE_STAR_POOL_INCOME = 4;
    public static final int ID_GENERATION_INCOME = 7;
    public static final int ID_GOLD_INCOME = 8;
    public static final int ID_SPOT_INCOME = 13;
    public static final int ID_BOOSTER_INCOME = 5;
    public static final int ID_SUPER_BOOSTER_INCOME = 6;
    public static final int ID_SU_BOOSTER_INCOME = 10;
    public static final int ID_CAR_PROGRAM_INCOME = 9;
    public static final int ID_DREAM_Volume_INCOME = 14;
    public static final int ID_LEADERSHIP_BONUS_INCOME = 15;
    public static final int ID_HOLIDAY_ACHIEVER = 16;
    public static final int ID_HOLIDAY_MINI_BONANZA = 17;
    public static final int ID_HOLIDAY_BONANZA = 18;
    public static final int ID_RANK_BONUS = 19;
    public static final int ID_SPONSOR_INCOME = 20;
    public static final int ID_HOLIDAY_REWARDS_INCOME = 1;
    public static final int ID_HOLIDAY_BOOSTER_INCOME = 1;

    //PROJECT LIST ID
    public static final int ID_AAVAAS_CHANGODER_COMPLEX = 7;
    public static final int ID_AAVAAS_CHANGODER = 5;
    public static final int ID_HAWTHORN_DWARKA = 6;
    public static final int ID_HOLIDAY = 8;
    public static final int ID_AAVAAS_HYDERABD = 4;
    public static final int ID_AAVAAS_CHENNAI = 10;

    //Holidays Packages
    public static final int HOLIDAYS_THAILAND = 8;
    public static final int HOLIDAYS_DWARKA = 9;

    // For PayUMoney
    public  static  final  String merchantKey = "0w2qzK";
    public  static  final  String saltKey = "Oa3o6OCxGvidPIIxnP2tlZ7Wq9z1VEpU";

    public static class WEB_SERVICES {

        public static final String BANERS_TEXT_LIST = "api/Notifications/NewsList";
        public static final String WS_GET_VERSION = "/Api/Dashboard/VersionCheckerNebPro";
        public static final String WS_GET_LOCATION = "/API/Dashboard/LocationUpdate";
        public static final String WS_POST_IBO_FULL_REGISTRATION ="API/Registration/FullRegisterIBO";

        public static final String WS_GET_PRODUCT_MASTER = "/API/Inventory/ProductMasterList";
        public static final String WS_GET_RATECHARTS = "/API/Downloads/MasterRateChart";
        public static final String WS_GET_RATECHARTS_DETAILS = "/API/Downloads/RateCharts";

        public static final String WS_GET_HOLIDAY_ACHIEVER_BONUS = "/API/Holidayincome/flightDate";
        public static final String WS_GET_HOLIDAY_ACHIEVER_BONUS_SELECTION = "/API/HolidayIncome/PostflightDate";
        public static final String WS_GET_HOLIDAY_ACHIEVER = "/API/HolidayIncome/AchieverDetails";

        public static final String WS_BV_LIST ="/API/Dashboard/BVDetails";


        public static final String WS_STATE_LIST = "/API/Config/StateInfo";
        public static final String WS_CITY_LIST = "/API/Config/CityInfo";

        //Testing
        public static final String WS_GET_TOKEN = "/API/Token";
        public static final String WS_GET_RANK_AND_VOLUMES = "/API/Dashboard/RankAndVolumes";
        public static final String WS_GET_RANK_HISTORY = "/API/Dashboard/RankHistory";
        public static final String WS_GET_INCOME_HISTORY = "/API/Dashboard/IncomeHistory";
        public static final String WS_GET_UPDATES = "/API/Dashboard/UpdateNotification";
        public static final String WS_GET_NEW_JOINEES = "/API/Dashboard/Last10Joinings";
        public static final String WS_GET_DOWNLINE_RANKS = "/API/Dashboard/DownlineRankCount";

        public static final String WS_GET_INCOME = "/API/Income/IncomeDetails";
        public static final String WS_GET_PROJECT_LIST = "/API/Inventory/Project";
        public static final String WS_GET_MYSALE_LIST = "/API/Sales/MySale";
        public static final String WS_GET_INCOME_LIST = "/API/Income/MyIncome";
        public static final String WS_GET_INCOME_SUMMARY = "/API/Income/IncomeSummary";
        public static final String WS_GET_MINI_BONANZA = "/API/HolidayIncome/MiniBonanzaDetails";
        public static final String WS_GET_MINI_BONANZA_LEG = "/API/Holidayincome/MiniBonanzaLegDetails";
        public static final String WS_GET_BONANZA = "/API/HolidayIncome/BonanzaDetails";
        public static final String WS_GET_BONANZA_LEG = "/API/Holidayincome/BonanzaLegDetails";

        public static final String WS_PROFILE = "/API/Profile/Me";
        public static final String WS_POST_CHANGE_PASSWORD = "/API/Profile/ChangePassword";
        public static final String WS_POST_UPDATE_PROFILE_PIC = "/API/Profile/UpdateProfilePic";
        public static final String WS_POST_IBO_GEO_DETAIL = "/API/Dashboard/DownlineLocator";
        public static final String WS_GET_ARRIVAL_DATE = "/API/Holidays/GetArrivalDate";
        public static final String WS_GET_DATE_CHANGE_LIST = "/API/Holidays/GetArrivalDateChangeBind\n";
        public static final String WS_GET_CUSTOMER_DETAIL = "/API/Holidays/GetCustomerDetail";
        public static final String WS_POST_SUBMIT_ARRIVAL_DATE = "/API/HolidayBooking/UpdateArrivalDate";
        public static final String WS_POST_CANCEL_DATE = "/API/HolidayBooking/CancelHolidayPackage";
        public static final String WS_POST_CHANGE_ARRIVAL_DATE = "/API/HolidayBooking/ArrivalDateChange";
        public static final String WS_GET_HOLIDAY_CHARGES = "/API/Holidays/HolidayCRCharges";
        public static final String WS_GET_HOLIDAY_NAME_CHARGES = "/API/Holidays/HolidayNameChangeCharges";

        public static final String WS_GET_IBO_LIST_SPONSOR = "/API/MyDownline/SponsorTree";
        public static final String WS_GET_IBO_LIST_PLACEMENT = "/API/MyDownline/PlacementTree";
        public static final String WS_GET_MYPURCHASE_LIST = "/API/Sales/MyPurchase";
        public static final String WS_GET_DOWNLINE_IBO_RANK_LIST = "/API/Dashboard/DownlineIBORankCountWiseList";


        // For Customer and IBO
        public static final String WS_FORGOT_PASSWORD = "/API/Profile/ForgotPassword";
        //public static final String WS_POST_TOKEN_KEY = "/API/Notifications/UpdateDeviceToken";
        public static final String WS_POST_TOKEN_KEY = "/API/Notifications/NebProUpdateDeviceToken";
        public static final String WS_LOGIN_VALIDATE_KEY = "/Api/NebProDashboard/IBOLogin";
        public static final String WS_GET_PROJECT = "/API/Projects/ProjectInfo";
        public static final String WS_GET_SEND_PASSOWRD = "/API/Profile/CustomerForgotPassword";

        // Forgot API
        public static final String FORGOT_IBO = "/API/ForgotPassword/CheckUserDetails";
        public static final String FORGOT_OTP_IBO = "API/ForgotPassword/OTPSendIBO";
        public static final String FORGOT_OTP_VERIFY_IBO = "/API/ForgotPassword/ForgotPasswordOTP";

        public static final String WS_GET_UNIT_LIST = "/API/Profile/CustForgotPassword";
        public static final String WS_GET_CUSTOMER_SEND_PASSOWRD = "/API/Profile/CustomerResetPassword";
        public static final String FORGOT_OTP_VERIFY_CUSTMOER = "/API/Profile/CustomerUpdatePassword";
        public static final String PROJECT_LIST = "/API/ProjectFacility/ProjectFacility2";
        //modify sagar
//add update notification for pagination

        //Team update
        public static final String TEAM_LIST = "/API/Dashboard/UpdateTeamNotification";

        //Personal update
        public static final String PERSONAL_LIST = "/API/Dashboard/UpdatePersonalNotification";

        //Baners
        public static final String BANERS_LIST = "API/Dashboard/Bannerlist";


        //Ibo Registation
        public static final String SPONSOR_ID = "API/Registration/GetSponsorName";
        public static final String PLACEMENT_ID = "API/Registration/GetPlacementName";
        public static final String REGISTATION_OTP = "/API/Registration/SendOTP";
        public static final String REGISTATION_POST ="/API/Registration/RegisterIBO";

        public static final String PIN_DETAIL ="/API/IBOPayoutPIN/IBOPayoutPinNumberDetails";
        public static final String WS_POST_PIN ="/API/IBOPayoutPIN/IBOPayoutPinNumberDetailsPost";
        public static final String WS_RESEND_PIN ="/API/IBOPayoutPIN/ReSendPinNo";

        public static final String WS_BANK_DETAIL ="/API/Registration/GetBankList";
        public static final String WS_RELATION_DETAIL ="/API/Registration/GetRelationList";
        public static final String WS_PAN_DETAIL ="/API/Registration/VerifyPAN";
        public static final String WS_GET_IBO_DETAIL ="/API/Registration/GetQuickRegisterData";
        public static final String WS_UPDATE_IBO_DETAIL ="API/Registration/ProfileUpdateIBO";

        public static final String WS_POST_SOP ="API/Dashboard/UpdateSOPStatus";
        public static final String WS_POST_SP ="API/Dashboard/UpdateSPConditionStatus";

        public static final String WS_POST_LAT_LONG ="API/Profile/UpdateLatLong";


        //Ecom
        public static final String WS_ADVERTISEMENT_IMAGES_ECOM= "/API/ECom/BannerImages";
        public static final String WS_GET_INVOICE= "/API/ECom/GetInvoice";
        public static final String WS_GET_PRODUCT_LIST = "API/ECom/ProductList";
        public static final String WS_GET_CATEGORY_LIST = "API/ECom/CategoryList";
        public static final String WS_GET_ORDER_LIST = "/API/ECom/GetOrderList";
        public static final String WS_GET_OFFER_LIST = "/API/EComCouponCode/EcomOfferList";
        public static final String ADD_TO_CART_POST = "/API/ECom/AddToCart";
        public static final String ADD_TO_CART_POST_NOLOGIN = "/API/ECom/AddToCartWithoutUser";

        public static final String CANCEL_ORDER = "/API/ECom/CancelOrder";
        public static final String PLACE_ORDER_POST = "/API/ECom/AddOrder";
        public static final String DELETE_FROM_CART_POST = "/API/ECom/RemoveFromCart";
        public static final String DELETE_FROM_CART_POST_Nologin = "/API/ECom/RemoveFromCartWithoutUser";

        public static final String DELETE_FROM_CART_POST_v2 = "/API/ECom/RemoveFromCartV2";

        public static final String ADD_FREE_PRODUCTS = "API/ECom/AddPromoToCart";
        public static final String MY_CART_LIST_ECOM = "/API/ECom/GetCartItemsList";
        public static final String MY_CART_LIST_ECOM_WO_LOGIN = "/API/ECom/GetCartItemsListWithoutUser";

        public static final String MY_CART_WALLET_AMOUNT = "/API/EComCouponCode/GetIBOWalletBalance";
        public static final String MY_BAL_WALLET = "/API/EComCouponCode/GetIBOWalletBalanceWithTempEwallet";


        public static final String MY_FREE_PRODUCT_LIST_ECOM = "/API/ECom/GetPromoData";
        public static final String MY_FREE_PRODUCT_REWARD_LIST_ECOM= "/API/ECom/GetRankRewardVariants";
        public static final String MY_FREE_PRODUCT_REWARD_UPDATE_ECOM= "/API/ECom/UpdateRankRewardVariant";

        public static final String MY_CART_REVIEW_LIST_ECOM = "/API/ECom/GetOutOfStock";
        public static final String MY_CART_REMOVE_LIST_ECOM = "/API/ECom/RemoveOutOfStock";
        public static final String EBC_DETAILS_ECOM = "/API/ECom/GetEBCDescription";
        public static final String EBC_GET_REVIEWS = "/API/ECom/GetReviewData";
        public static final String PRODUCT_SHARE = "API/EComCouponCode/AddEcomProductShareLog";
        public static final String EBC_DETAILS_ECOM_UCQuantity = "/Api/ECom/GetEBCDescriptionWithUCQuantity";
        public static final String EBC_DETAILS_ECOM_UCQuantityV2 = "/Api/ECom/GetEBCDescriptionWithUCQuantityV2";

        public static final String BOTTOM_BANNER_ECOM = "/Api/ECom/ListBottomImages";
        public static final String WS_SAVE_TICKET= "/Api/Ticket/SaveTicket";
        public static final String WS_MINIMUM_PV= "/API/ECom/GetMinimumPVForIBOPrice";
        public static final String WS_DELETE_CART= "/API/ECom/MarkCartDelete";
        public static final String WS_DELETE_EWALLET= "/API/EComCouponCode/PostTempEwalletReverseAmount";
        public static final String WS_GET_EVENTS = "/Api/Event/Category";
        public static final String WS_GET_SITE_PROGRESS_IMAGES = "/API/SiteProgress/GetSiteProgressWithPagination";
        public static final String WS_GET_EVENT_DETAILS = "/API/Event/EventByCategoryWithLikeWithPagination";
        public static final String WS_GET_SITE_PRODUCTS = "/API/SiteProgress/SiteProgressMaster";


        public static final String WS_GET_PV_TABLE = "/API/NebProDashboard/PVtable";
        public static final String WS_GET_ID_DETAILS = "/Api/NebProDashboard/NebProDashboardFirstTab";
        public static final String WS_GET_WEEKLY_MATCHING = "/API/NebProDashboard/WeeklyMatching";
        public static final String WS_GET_COMMUNITY_GROWTH = "/API/NebProDashboard/CommunityGrowthIncomeHistory";


        //promo Referral
        public static final String WS_GET_PROMO_CODES = "/API/EComCouponCode/IBOWisePromoList";
        public static final String WS_GET_GENERATE_COUPON = "/API/EComCouponCode/PromoCodeList";
        public static final String WS_GET_ACTIVE_CODES = "/API/EComCouponCode/IBOActivePromoCode";

        //Customer Support
        public static final String WS_GET_CSUPPORT_CATEGORY = "/Api/Ticket/GetEComTicketCategory";
        public static final String WS_GET_WALLET_HISTORY="/API/EComCouponCode/GetIBOWalletList";
        public static final String WS_GET_CSUPPORT_ORDER = "/Api/Ticket/GetEComOrderList";
        public static final String WS_GET_CSUPPORT_TICKET_LIST = "/api/Ticket/GetTicketsByUserId";
        public static final String WS_GET_CSUPPORT_TICKET_DETAIL_LIST = "/API/Ticket/GetTicketDetails";
        public static final String WS_GET_CSUPPORT_TICKET_MESSAGE_LIST = "/API/Ticket/SaveTicketMessage";
        public static final String WS_GET_TRENDING_LIST = "API/ECom/TrendingProductList";
        public static final String WS_GET_NEW_LIST = "API/ECom/NewProductList";
        public static final String PRODUCT_REVIEW = "/API/ECom/AddReviewForProduct";
        // public static final String MY_CARD_DETAILS_ECOM = "/API/Ecom/GetPaymentGatewayContent";
        public static final String MY_CARD_DETAILS_ECOM ="/API/Ecom/UniCommerceGetPaymentGatewayContentWithUPI";//"/API/Ecom/UniCommerceGetPaymentGatewayContentWithUPIV2";// "/API/Ecom/UniCommerceGetPaymentGatewayContent";
//        public static final String MY_CARD_DETAILS_ECOM_Paytm ="/API/Ecom/UniCommerceGetPaymentGatewayContentWithUPIPaytm";//"/API/Ecom/UniCommerceGetPaymentGatewayContentWithUPIV2";// "/API/Ecom/UniCommerceGetPaymentGatewayContent";
//        public static final String MY_CARD_DETAILS_ECOMV2_Paytm ="/API/Ecom/GenerateOrderPayUMoney";
//        public static final String MY_CARD_DETAILS_ECOMV2 ="/API/Ecom/GenerateOrderPayUMoney";

        //offer Image
        public static final String Get_OfferImageResponse = "/Api/NebProDashboard/PopOfferList";


        public static final String MY_CARD_DETAILS_ECOM_Paytm ="/API/Ecom/UniCommerceGetPaymentGatewayContentWithUPIPaytm";//"/API/Ecom/UniCommerceGetPaymentGatewayContentWithUPIV2";// "/API/Ecom/UniCommerceGetPaymentGatewayContent";
        public static final String MY_CARD_DETAILS_ECOMV2_Paytm ="/API/Ecom/GenerateOrderPaytm";
        public static final String MY_CARD_DETAILS_ECOMV2 ="/API/Ecom/UniCommerceGetPaymentGatewayContentWithUPIV3";
        public static final String MY_CARD_DETAILS_PAYTM ="/API/Ecom/GenerateOrderPaytm";


        public static final String MY_CART_TOTAL_COUNT_LIST_ECOM = "/API/ECom/GetCartSumOfQty";
        public static final String MY_PROFILE_ECOM = "/API/ECom/Me";
        public static final String TRACK_ORDER_ECOM = "/Api/ECom/GetOrderDetailWithTrackOrder";
        public static final String MY_MRP_PRICE_ECOM = "/API/Ecom/ShowMRPPrice";
        public static final String POST_CODE = "/API/EComCouponCode/GeneratePromoCode";
        public static final String ADDRESS_LIST_ECOM = "/API/ECom/GetAddresses";
        public static final String PICK_UP_LIST_ECOM = "/API/ECom/GetPickUpAddressByCity";

        //public static final String ADDRESS_LIST_ECOM = "/API/ECom/GetAddressesByDeviceId";
        public static final String ADD_ADDRESS_DETAILS ="API/ECom/AddAddress";
        public static final String SUCCESS_UPI ="API/EcomPaymentUPI/OnSuccessPaymentCallUPI";
        public static final String SUCCESS_PayU_UPI ="/API/EcomPaymentUPI/OnSuccessPaymentCallUPIPayUMoney";

        public static final String UPDATE_ADDRESS_DETAILS ="API/ECom/UpdateAddress";
        public static final String DELETE_ADDRESS_DETAILS = "/API/ECom/DeleteAddress";

        public static final String REGISTER_ACCOUNT ="/API/ECom/CustomerRegister";
        public static final String LOGIN_ACCOUNT ="/API/ECom/LoginOTP";
        public static final String WS_GET_SEARCH = "/API/ECom/SearchByText";
        public static final String WS_CATEGORY = "/API/ECom/ProductListByCategoryWithPaginationV2";
        public static final String WS_CATEGORY_REF = "/API/ECom/ProductListByCategoryWithRefId";
        public static final String PAYMENT_SUCESS_ECOM ="/API/Ecom/MatchPaymentSignature";
        //Banners
        public static final String BANERS_LIST_ECOM = "/API/ECom/ProductBanners";
        public static final String PRODUCT_VARIANTS = "/API/EcomAttribute/GetEcomAttributeValuesList";
        public static final String ID_CARD = "/ApiGetEcomAttributeValuesList/NebProDashboard/IBOIdCard";
        public static final String CATEGORY_DETAILS_LIST_ECOM = "/API/ECom/ProductListByCategory";
        public static final String EBC_ECOM = "/Api/ECom/EComEBCImages";

        /*Dwarka holiday*/
        public static final String MY_CARD_DETAILS_DWARKA = "/API/DwarkaSale/GetPaymentGatewayContent";
        public static final String DWARKA_GATEWAY = "/API/AndroidPaymentGateWay/GateWayList";
        public static final String PAYMENT_SUCESS ="/API/DwarkaSale/MatchPaymentSignature";
        public static final String PAYMENT_SUCESS_UPI ="/API/DwarkaUPIGateWay/OnSuccessPaymentCallHolidayUPI";
        public static final String PAYMENT_SUCESS_NEW ="/API/DwarkaSale/MatchPaymentSignature2";
        public static final String DWARKA_PACKAGE = "/API/Inventory/DwarkaProductList";
        public static final String DWARKA_EMI_PLAN = "/API/HolidayTimesSquare/HolidayTimeSquareEMI";
        public static final String DWARKA_PLAN = "/API/Inventory/HolidayPaymentPlanList";
        public static final String DWARKA_UPI = "/API/DwarkaUPIGateWay/GetPaymentGatewayContentHolidayUPI";
        public static final String DWARKA_TIMEPLAN_UPI = "/API/HolidayTimesSquare/GetPaymentGatewayContentTimeSquare";

        /*UNI COMMERCE*/
        public static final String UNI_BASE_URL =  "https://nebula.unicommerce.com/oauth/token";
        public static final String UNI_GET_TOKEN =  "https://nebula.unicommerce.com/oauth/token";
        public static final String UNI_GET_INVENTORY = "https://nebula.unicommerce.com/services/rest/v1/inventory/inventorySnapshot/get?";
        public static final String UNI_GET_ORDER_STATUS = "https://nebula.unicommerce.com/services/rest/v1/oms/saleorder/get";
        public static final String UNI_GET_PDF = "https://nebula.unicommerce.com/services/rest/v1/oms/invoice/show";

    }

    public static class WEB_SERVICES_PARAM {

        public static final String KEY_ADMIN_LOGIN = "AdminLogin";
        public static final String KEY_USERNAME = "Username";
        public static final String KEY_PASSWORD = "Password";
        public static final String KEY_PASSWORD_HOLIDAY = "Password";
        public static final String KEY_IMEI_NUMBER = "IMEINumberID";
        public static final String KEY_PROJECT_ID = "ProjectId";
        public static final String KEY_Token = "token";
        public static final String KEY_TOKENN = "Token";
        public static final String KEY_EVENT_ID = "EventId";
        public static final String KEY_STATUS = "Status";
        public static final String KEY_SITE_ID = "SiteID";
        public static final String KEY_PROJECT_NAME = "ProjectId";
        public static final String KEY_MONTH = "Month";
        public static final String KEY_YEAR = "Year";
        public static final String KEY_APPROVED = "approved";
        public static final String KEY_DOWNLINE_STATUS_ID = "MemberIDlevelNo";
        public static final String KEY_LAST_TEN_JOIN = "MemberID10User";
        public static final String KEY_MY_SALES_IBOID = "SaleMemberID";
        public static final String KEY_MY_IBO_ID = "MemberID";
        public static final String KEY_MY_INCOME_ID = "IncomeId";
        public static final String KEY_WEAKER_ID = "MainWeakerMemberID";
        public static final String KEY_OTHER_LEG_ID = "IBOId";
        public static final String KEY_MAIN_LEG_MEMBER_ID = "MainLegMemberID";
        public static final String KEY_WEAKER_LEG_MEMBER_ID = "WeakerLegMemberID";
        public static final String KEY_PLACEMENT_TREE = "Tree";
        public static final String KEY_SPONSER_TREE_ID = "MemberIDSponser";
        public static final String KEY_DOCUMENT_NAME = "EDocumentType";
        public static final String KEY_IBO_ID = "MemberID";
        public static final String KEY_IBO_ID_ = "IBOId";
        public static final String KEY_COUNTRY_NAME = "CountryName";
        public static final String KEY_RATE_CHART_ID = "RateChartId";
        public static final String KEY_LIKE = "isLike";
        public static final String KEY_TOKEN = "TokenKey";
        public static final String KEY_DEVICE_ID = "DeviceId";
        public static final String KEY_IMEI1 = "IMEI1";
        public static final String KEY_IMEI2 = "IMEI2";
        public static final String KEY_USERID = "UserId";

        public static final String KEY_ID = "id";
        public static final String KEY_FLAG = "flag";


        public static final String KEY_SPONSOR_ID = "SponsorID";
        public static final String KEY_PLACEMENT_ID = "PlacementID";
        public static final String KEY_MOBILE = "MobileNo";
        public static final String KEY_SPONSOR_NAME = "SponsorName";
        public static final String KEY_PLACEMENT_NAME = "PlacementName";
        public static final String KEY_FIRST = "FirstName";
        public static final String KEY_MIDDLE_NAME = "MiddleName";
        public static final String KEY_MOBILE_LAST_NAME = "LastName";
        public static final String KEY_EMAIL_REGISTATION = "EmailId";
        public static final String KEY_PHONE = "PhoneNumber";

        public static final String KEY_EVENT_NAME = "Category";

        public static final String KEY_IBO_ID_PV = "IBOKeyID";
        public static final String KEY_PROMO_MASTER_ID ="PromoCodeMasterID";
        public static final String KEY_ORDER_NO = "orderno";

        public static final String KEY_LINK = "ProductLink";
        public static final String KEY_REF_ID = "RefID";

        //Holiday

        public static final String KEY_USER_NAME = "UserName";
        public static final String KEY_USER_NAME_HOLIDAY = "UserName";

        public static final String KEY_BASE_CURRENCY = "base";
        public static final String KEY_SYMBOL_CURRENCY = "symbols";
        public static final String KEY_HOTEL_ID = "HotelId";
        public static final String KEY_PACKAGE_ID = "PackageId";
        // Modified By Sagar.
        public static final String KEY_TITLE = "Title";
        public static final String KEY_COMMENT = "Comment";
        public static final String KEY_RATING = "Rating";
        public static final String KEY_CUSTOMERID = "SaleId";
        public static final String KEY_REVIEWID = "ReviewId";
        // Modified by Palak Mehta
        public static final String KEY_INCOME_ID = "IncomeId";
        public static final String KEY_AUTHOR_ID = "OtherIBOID";
        public static final String KEY_COUNTRY_ID = "CountryId";
        public static final String KEY_STATE_ID = "StateId";

        //Testing
        /*public static final String KEY_USERNAME_ = "username";
        public static final String KEY_PASSWORD_ = "password";
        public static final String KEY_GRANT_TYPE = "grant_type";*/

        public static final String KEY_USERNAME_ = "username";
        public static final String KEY_PASSWORD_ = "password";
        public static final String KEY_GRANT_TYPE = "grant_type";


        public static final String KEY_TOKEN_TYPE = "token_type";
        public static final String KEY_ACCESS_TOKEN = "access_token";
        public static final String KEY_REFRESH_TOKEN = "refresh_token";
        public static final String KEY_ERROR_DECRIPTION = "error_description";
        public static final String KEY_ROLE = "Role";
        public static final String KEY_DISPLAYNAME = "DisplayName";
        public static final String KEY_IBO_KEY_ID = "IBOKeyId";
        public static final String KEY_IBO_REF_ID = "EncryptUserName";

        public static final String KEY_INCOME_TYPE = "IncomeType";
        public static final String KEY_UPDATE_PIC_TYPE = "EncodedImage";
        public static final String KEY_OLD_PASSWORD_TYPE = "OldPassword";
        public static final String KEY_NEW_PASSWORD_TYPE = "NewPassword";
        public static final String KEY_CATEGORY = "Category";
        public static final String KEY_PAGE_LENGTH = "PageLength";
        public static final String KEY_START_INDEX = "StartIndex";
        public static final String KEY_RANK_ID = "RankId";
        public static final String KEY_SEARCH_TEXT = "SearchText";
        public static final String KEY_PAGE_INDEX = "PageIndex";

        //Added By Jadav Chirag

        public static final String KEY_LATITUDE = "Latitude";
        public static final String KEY_LONGITUDE = "Longitude";
        public static final String KEY_VIDEO_TYPE = "VideoType";

        public static final String KEY_PRODUCT_SALE_ID = "ProductSaleID";
        public static final String KEY_CHARGE_AMOUNT = "ChargeAmount";
        public static final String KEY_ARRIVAL_DATE = "ArrivalDate";

        public static final String KEY_SALE_IBO_KEY_ID = "SaleIBOKeyId";
        public static final String KEY_FIRST_NAME = "FirstName";
        public static final String KEY_LAST_NAME = "LastName";
        public static final String KEY_GENDER = "Gender";
        public static final String KEY_AMOUNT = "Amount";
        public static final String KEY_MARITAL_STATUS = "MaritalStatus";
        public static final String KEY_DOB = "DOB";
        public static final String KEY_ADDRESS = "AddressLine1";
        public static final String KEY_PHONE_NUMBER = "PhoneNumber";
        public static final String KEY_ALTERNATE_NUMBER = "AlternateMobile";
        public static final String KEY_STATE = "State";
        public static final String KEY_CITY = "City";
        public static final String KEY_COUNTRY = "Country";
        public static final String KEY_PINCODE = "ZIPCode";
        public static final String KEY_EMAIL = "Email";
        public static final String KEY_PASSPORT_NO = "PassportNo";
        public static final String KEY_PASSPORT_EXP_DATE = "PassportExpiryDate";
        public static final String KEY_EMERG_CONTACT_NAME = "EmergencyContactName";
        public static final String KEY_EMERG_CONTACT_NUMBER = "EmergencyContact";
        public static final String KEY_PASSPORT_PROOF = "PassportProof";
        public static final String KEY_ID_PROOF = "IDProof";
        public static final String KEY_IBO_LEG_KEY = "LegIBOKeyId";

        public static final String KEY_BLOCK_NO_KEY = "BlockNo";
        public static final String KEY_FLAT_KEY = "flat";

        //Forgot IBO Password
        public static final String KEY_IBO_ID_FORGOT_PASSWORD = "IBOID";

        //Forgot OTP VERIFY IBO Password
        public static final String KEY_IBO_PHONE_NUMBER_FORGOT_PASSWORD_VERIFY = "PhoneNumber";
        public static final String KEY_IBO_OTP_FORGOT_PASSWORD_VERIFY = "OTP";
        public static final String KEY_IBO_ID_FORGOT_PASSWORD_VERIFY = "UserName";
        public static final String KEY_IBO_CODE_FORGOT_PASSWORD_VERIFY = "Code";
        public static final String KEY_IBO_PASSWORD_FORGOT_PASSWORD_VERIFY = "Password";

        //add sagar
        //holiday Key
        public static final String KEY_PROJECTS_ID = "projectid";
        public static final String KEY_PHONE_EMAIL = "CustPhoneNumberEmail";
        public static final String KEY_UNIT_ID = "unitid";
        public static final String KEY_CUSTMOER_NAME = "customerUserName";
        public static final String KEY_NEW_PASSWORD = "newPassword";
        public static final String KEY_PAYMENT_SUMMARY = "PaymentSummaryID";
        public static final String KEY_PAN_CARD = "PANCard";

        public static final String KEY_PINCODE_NEW = "Pincode";
        public static final String KEY_ALTE_NUMBER = "AlternateMobileNumber";
        public static final String KEY_PAN_NUMBER = "PANCardNumber";
        public static final String KEY_AADHAR_NUMBER = "AadharNumber";
        public static final String KEY_NOMINEE_NAME = "NomineeName";
        public static final String KEY_NOMINEE_SHIP = "NomineeRelationship";
        public static final String KEY_BANK_ID = "BankId";
        public static final String KEY_ACCOUNT_NAME = "AccountHolderName";
        public static final String KEY_ACCOUNT_NUMBER = "AccountNo";
        public static final String KEY_IFSC_CODE = "IFSCCode";
        public static final String KEY_BRANCH_NAME = "BranchName";
        public static final String KEY_BRANCH_CITY = "BranchCity";
        public static final String KEY_ADDRESS_NEW = "Address";

        public static final String KEY_LAT = "lat";
        public static final String KEY_LON = "longi";


        //ECOM
        public static final String KEY_CLIENT_ID = "client_id";
        public static final String KEY_TOKEN_TYPE_UNICOMMERCE = "token_type";
        public static final String KEY_ACCESS_TOKEN_UNICOMMERCE = "access_token";
        public static final String KEY_DEVICE_ID_ECOM = "deviceid";
        public static final String KEY_PICK_UP_ID_ECOM = "pickupid";
        public static final String KEY_ISNEBPRO ="isNebPro";
        public static final String KEY_ISHome ="isHomeStore";
        public static final String KEY_CITY_ID_ECOM = "cityId";
        public static final String KEY_USER_ID = "userid";
        public static final String KEY_ID_PRODUCT = "productid";
        public static final String KEY_ID_TITLE = "title";
        public static final String KEY_ID_RATING = "rating";
        public static final String KEY_ID_DESCRIPTION = "description";
        public static final String KEY_CART_FLAG = "CartFlag";
        public static final String KEY_PRODUCT_ID = "productid";
        public static final String KEY_PROMO_ID = "promoId";
        public static final String KEY_CATEGORY_ID = "catid";
        public static final String KEY_CITY_ID = "pickupid";
        public static final String KEY_QUANTITY = "quantity";
        public static final String KEY_PRODUCT_ID_ECOM = "productid";
        public static final String KEY_MOBILE_ECOM = "mobile";
        public static final String KEY_ID_EBC = "Id";
        public static final String KEY_ID_EBC_DECS = "EComProductDetailsId";
        public static final String KEY_ID_TICKET_STATUS = "status";
        public static final String KEY_ID_TICKET_CATEGORY_ID = "ticketCategoryId";
        public static final String KEY_ID_TICKET_CREATE_CATEGORY_ID = "TicketCategoryId";
        public static final String KEY_ID_TICKET_SUBJECT = "Subject";
        public static final String KEY_ID_TICKET_REMARK = "Remarks";
        public static final String KEY_ID_TICKET_ORDER_NUMBER = "OrderNumber";
        public static final String KEY_ID_TICKET_USER_ID = "UserId";
        public static final String KEY_ID_TICKET_MASTER_ID = "id";
        public static final String KEY_ID_TICKET_ID = "TicketId";
        public static final String KEY_ID_TICKET_MESSAGE = "Description";
        public static final String KEY_ID_ISDELETED = "issuccess";

        public static final String KEY_SUBTOTAL = "subtotal";
        public static final String KEY_VARIANTID = "variantid";
        public static final String KEY_NEWVARIANTID = "newvariant";
        public static final String KEY_OLDVARIANTID = "oldvariant";
        public static final String KEY_TOTALPV = "totalpv";


        //OrderID,TxnId,,ApprovalRef,ResponseCode,Status,txnRef,Remarks
        public static final String KEY_ID_OrderId = "OrderID";
        public static final String KEY_ID_txnID = "TxnId";
        public static final String KEY_ID_Approval = "ApprovalRef";
        public static final String KEY_ID_Response = "ResponseCode";
        public static final String KEY_ID_Status = "Status";
        public static final String KEY_ID_txnREF = "txnRef";
        public static final String KEY_ID_Remarks = "Remarks";

        public static final String KEY_mihpayid = "mihpayid";
        public static final String KEY_mode = "mode";
        public static final String KEY_status = "status";
        public static final String KEY_unmap = "unmappedstatus";
        public static final String KEY_key = "key";
        public static final String KEY_txnid = "txnid";



        public static final String KEY_FULLNAME_ECOM ="fullName";
        public static final String KEY_ADDRESS_ONE_ECOM = "addressLine1";
        public static final String KEY_ADDRESS_TWO_ECOM = "addressLine2";
        public static final String KEY_LANDMARK_ECOM = "landmark";
        public static final String KEY_CITY_ECOM = "city";
        public static final String KEY_STATE_ECOM = "state";
        public static final String KEY_PINCODE_ECOM = "pinCode";
        public static final String KEY_ADDRESS_TYPE_ECOM = "addressType";
        public static final String KEY_ADDRESS_ID_ECOM = "id";
        public static final String KEY_IBO_KEY_ID_NEW = "userid";
        public static final String KEY_IBO_KEY_ID_Pickup = "pickupid";

        public static final String KEY_REGISTER_FIRST_NAME = "firstname";
        public static final String KEY_REGISTER_LAST_NAME = "lastname";
        public static final String KEY_REGISTER_MOBILE = "mobile";
        public static final String KEY_REGISTER_MOBILE_NO = "mobileno";
        public static final String KEY_ORDER_ID = "order_id";
        public static final String KEY_UPI_ORDER_ID = "Order_Id";
        public static final String KEY_REGISTER_EMAIL = "email";
        public static final String KEY_REGISTER_GENDER = "gender";
        public static final String KEY_SEARCH_TEXT_ECOM = "searchtext";


        public static final String KEY_ORDER_ID_ECOM = "OrderDetailsId";
        public static final String KEY_ORDER_REASON_ECOM = "Reason";
        public static final String KEY_ORDER_REMARK_ECOM = "Remark";
        public static final String KEY_ORDER_QUANTITY_ECOM = "Quantity";

        /*Dwarka Holiday*/
        public static final String KEY_TOTAL_AMOUNT = "TotalAmount";
        public static final String KEY_REGISTER_USER_NAME = "UserName";
        public static final String KEY_REGISTER_PAYMENT_ID = "paymentid";
        public static final String KEY_REGISTER_ADDRESS_TYPE = "AddressType";
        public static final String KEY_REGISTER_SHIPPING_ADDRESS = "ShippingAddress";
        public static final String KEY_REGISTER_BILLING_ADDRESS = "BillingAddress";
        public static final String KEY_REGISTER_PICK_UP_POINT = "PickupPoint";
        public static final String KEY_REGISTER_PAYMENT_MODE = "PaymentMode";
        public static final String KEY_REGISTER_ORDER_ID = "orderid";
        public static final String KEY_REGISTER_PRODUCT_ID = "ProductId";
        public static final String KEY_Paymentplan_ID = "PaymentPlanId";
        public static final String KEY_VERSION = "Version";
        public static final String KEY_SIGNATURE= "signature";
        public static final String KEY_PRODUCT_ID_DWARKA= "ProductID";
        public static final String KEY_PRODUCT_IS_WAIVE_OFF= "IsWaiveOff";
        public static final String KEY_USER_PAYMENT= "UserPaymentType";
        public static final String KEY_EWALLET_PAYMENT="ewalletamt";
        public static final String KEY_ADDRESS_TYPE= "AddressType";
        public static final String KEY_SHIPPING_ADDRESS= "ShippingAddress";
        public static final String KEY_BILLING_ADDRESS= "BillingAddress";
        public static final String KEY_PICKUP_POINT= "PickupPoint";
        public static final String KEY_PAYMENT_MODE= "PaymentMode";


        public static final String KEY_ADDRESS_TYPE_ECOM_PAY= "addresstype";
        public static final String KEY_SHIPPING_ADDRESS_ECOM= "shippingaddress";
        public static final String KEY_BILLING_ADDRESS_ECOM= "billingaddress";
        public static final String KEY_PICKUP_POINT_ECOM= "pickuppoint";
        public static final String KEY_PAYMENT_MODE_ECOM= "paymentmode";


        //UNICOMMERCE

        public static final String KEY_ITEM_TYPE_SKUS= "itemTypeSKUs";
        public static final String KEY_UPDATED_SINCE_IN_MINTUES= "updatedSinceInMinutes";



    }

    
    public static  String getNullvalue(String str_Data){
        String str_MyString = "";
        if (str_Data == null || str_Data.equals("")){
            str_MyString = "";
            return str_MyString;
        }
      return str_MyString  ;
    }
}
