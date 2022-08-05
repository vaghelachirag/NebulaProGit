
package com.nebulacompanies.ibo.Network;

import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.dwarkaPackage.model.DwarkaPlanModel;
import com.nebulacompanies.ibo.dwarkaPackage.model.PackageEMIModelDwarka;
import com.nebulacompanies.ibo.dwarkaPackage.model.PackageListModelDwarka;
import com.nebulacompanies.ibo.dwarkaPackage.model.PaymentSuccess;
import com.nebulacompanies.ibo.ecom.model.ActivePromoCodeModel;
import com.nebulacompanies.ibo.ecom.model.AddAddressDetail;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.AddressData;
import com.nebulacompanies.ibo.ecom.model.AdvertisementImageListEcom;
import com.nebulacompanies.ibo.ecom.model.Baners;
import com.nebulacompanies.ibo.ecom.model.BanersEcom;
import com.nebulacompanies.ibo.ecom.model.BottomBannerModel;
import com.nebulacompanies.ibo.ecom.model.CancelOrderModel;
import com.nebulacompanies.ibo.ecom.model.CardDetailsModelEcom;
import com.nebulacompanies.ibo.ecom.model.CardDwarkaModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelRemoveEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelReviewEcom;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.CategoryDetailsListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CategoryListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CreateTicketModel;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportCategory;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportDetailTicket;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportOrder;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportTicket;
import com.nebulacompanies.ibo.ecom.model.DeleteAddressModel;
import com.nebulacompanies.ibo.ecom.model.DeleteCartModel;
import com.nebulacompanies.ibo.ecom.model.EBCBannerModel;
import com.nebulacompanies.ibo.ecom.model.EBCDescriptionModel;
import com.nebulacompanies.ibo.ecom.model.EcomOfferModel;
import com.nebulacompanies.ibo.ecom.model.FreeProductListModel;
import com.nebulacompanies.ibo.ecom.model.FreeProductsModel;
import com.nebulacompanies.ibo.ecom.model.GenerateCouponModel;
import com.nebulacompanies.ibo.ecom.model.LoginModelEcom;
import com.nebulacompanies.ibo.ecom.model.MarkCartDeleteModel;
import com.nebulacompanies.ibo.ecom.model.MinimumPvModel;
import com.nebulacompanies.ibo.ecom.model.NewCategoryProductDetails;
import com.nebulacompanies.ibo.ecom.model.OrderListModelEcom;
import com.nebulacompanies.ibo.ecom.model.OtherProducts;
import com.nebulacompanies.ibo.ecom.model.PaymentSuccessEcomModel;
import com.nebulacompanies.ibo.ecom.model.PickUpAddressData;
import com.nebulacompanies.ibo.ecom.model.PlaceOrderModel;
import com.nebulacompanies.ibo.ecom.model.ProductListModelEcom;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.model.PromoCodeModel;
import com.nebulacompanies.ibo.ecom.model.RegisterModelEcom;
import com.nebulacompanies.ibo.ecom.model.ReviewsModel;
import com.nebulacompanies.ibo.ecom.model.SearchModelEcom;
import com.nebulacompanies.ibo.ecom.model.ShowMRPPriceModelEcom;
import com.nebulacompanies.ibo.ecom.model.TrackListModelEcom;
import com.nebulacompanies.ibo.ecom.model.TrackOrderModel;
import com.nebulacompanies.ibo.ecom.model.TrendingProductModel;
import com.nebulacompanies.ibo.ecom.model.WalletFreezeModel;
import com.nebulacompanies.ibo.ecom.model.WalletHistory;
import com.nebulacompanies.ibo.ecom.model.unicommerce.InventoryModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.InventoryRequestModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.OrderStatusModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.OrderStatusRequestModel;
import com.nebulacompanies.ibo.model.AddReviewDetail;
import com.nebulacompanies.ibo.model.BackDetails;
import com.nebulacompanies.ibo.model.BanerText;
import com.nebulacompanies.ibo.model.Bonanza;
import com.nebulacompanies.ibo.model.BoosterIncomeDetails;
import com.nebulacompanies.ibo.model.BvDetails;
import com.nebulacompanies.ibo.model.CarProgramIncomeDetails;
import com.nebulacompanies.ibo.model.ChangePassword;
import com.nebulacompanies.ibo.model.ChecksumResponse;
import com.nebulacompanies.ibo.model.CommunityGrowthModel;
import com.nebulacompanies.ibo.model.DownlineIBORankDetails;
import com.nebulacompanies.ibo.model.DownlineRanks;
import com.nebulacompanies.ibo.model.DreamVolumeDetails;
import com.nebulacompanies.ibo.model.DwarkaUpiModel;
import com.nebulacompanies.ibo.model.Events;
import com.nebulacompanies.ibo.model.FullRegistaionDetail;
import com.nebulacompanies.ibo.model.GenerationIncomeDetails;
import com.nebulacompanies.ibo.model.GetIboDetails;
import com.nebulacompanies.ibo.model.GetOfferImageResponse.GetOfferImageResponse;
import com.nebulacompanies.ibo.model.GetPin;
import com.nebulacompanies.ibo.model.GoldIncomeDetails;
import com.nebulacompanies.ibo.model.HolidayAchieverBonus;
import com.nebulacompanies.ibo.model.HolidayAchieverDetails;
import com.nebulacompanies.ibo.model.IBOGeoLocation;
import com.nebulacompanies.ibo.model.IBOListPlacement;
import com.nebulacompanies.ibo.model.IBOListSponsor;
import com.nebulacompanies.ibo.model.IDCardModel;
import com.nebulacompanies.ibo.model.IdDetailsModel;
import com.nebulacompanies.ibo.model.IncomeHistory;
import com.nebulacompanies.ibo.model.IncomeList;
import com.nebulacompanies.ibo.model.IncomeSummary;
import com.nebulacompanies.ibo.model.LeadershipBonusDetails;
import com.nebulacompanies.ibo.model.LoginValidate;
import com.nebulacompanies.ibo.model.MiniBonanza;
import com.nebulacompanies.ibo.model.MiniBonanzaLeg;
import com.nebulacompanies.ibo.model.MyAccount;
import com.nebulacompanies.ibo.model.MyPurchasesAavaasDetails;
import com.nebulacompanies.ibo.model.MySalesAavaasDetails;
import com.nebulacompanies.ibo.model.NewJoinees;
import com.nebulacompanies.ibo.model.NewSiteProgressImages;
import com.nebulacompanies.ibo.model.NewSubEventDetails;
import com.nebulacompanies.ibo.model.PVTableModel;
import com.nebulacompanies.ibo.model.PanVerify;
import com.nebulacompanies.ibo.model.PlacementID;
import com.nebulacompanies.ibo.model.PostPinDetails;
import com.nebulacompanies.ibo.model.ProductMaster;
import com.nebulacompanies.ibo.model.ProjectBaners;
import com.nebulacompanies.ibo.model.ProjectDetail;
import com.nebulacompanies.ibo.model.RankAndVolumes;
import com.nebulacompanies.ibo.model.RankBonusDetails;
import com.nebulacompanies.ibo.model.RankHistory;
import com.nebulacompanies.ibo.model.RateCharts;
import com.nebulacompanies.ibo.model.RateChartsDetail;
import com.nebulacompanies.ibo.model.ReSendPin;
import com.nebulacompanies.ibo.model.Registation;
import com.nebulacompanies.ibo.model.RegistationOTP;
import com.nebulacompanies.ibo.model.RelationDeatils;
import com.nebulacompanies.ibo.model.RetailIncomeList;
import com.nebulacompanies.ibo.model.ShareRefID;
import com.nebulacompanies.ibo.model.SiteProductModel;
import com.nebulacompanies.ibo.model.SponsorID;
import com.nebulacompanies.ibo.model.SponsorIncomeDetails;
import com.nebulacompanies.ibo.model.SpotIncomeDetails;
import com.nebulacompanies.ibo.model.StarClubIncomeDetails;
import com.nebulacompanies.ibo.model.StateDetails;
import com.nebulacompanies.ibo.model.SuBoosterIncomeDetails;
import com.nebulacompanies.ibo.model.SuperBoosterIncomeDetails;
import com.nebulacompanies.ibo.model.ThreeStarClubIncomeDetails;
import com.nebulacompanies.ibo.model.Updates;
import com.nebulacompanies.ibo.model.VersionCheck;
import com.nebulacompanies.ibo.model.WalletModel;
import com.nebulacompanies.ibo.model.WeeklyMatchingModel;
import com.nebulacompanies.ibo.util.Constants;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Class : APIInterface
 * Details:
 * Create by : Palak Mehta At Nebula Infra space LLP 09-01-2018
 * Modification by :
 */

public interface APIInterface {

    @GET(Constants.WEB_SERVICES.BANERS_LIST)
    Call<ProjectBaners> getProjectBanersList();

    @GET(Constants.WEB_SERVICES.BANERS_TEXT_LIST)
    Call<BanerText> getBanerTextList();

    //Testing
    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_GET_TOKEN)
    Call<JsonObject> getToken(@Field(Constants.WEB_SERVICES_PARAM.KEY_USERNAME_) String username,
                              @Field(Constants.WEB_SERVICES_PARAM.KEY_PASSWORD_) String password,
                              @Field(Constants.WEB_SERVICES_PARAM.KEY_GRANT_TYPE) String GrantType);

    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_POST_TOKEN_KEY)
    Call<JsonObject> postTokenKey(@Field(Constants.WEB_SERVICES_PARAM.KEY_TOKEN) String token,
                                  @Field(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID) String deviceid,
                                  @Field(Constants.WEB_SERVICES_PARAM.KEY_IMEI1) String imei1,
                                  @Field(Constants.WEB_SERVICES_PARAM.KEY_IMEI2) String imei2,
                                  @Field(Constants.WEB_SERVICES_PARAM.KEY_USERID) String UserID);

    @GET(Constants.WEB_SERVICES.WS_LOGIN_VALIDATE_KEY)
    Call<LoginValidate> loginValidate(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboId);

    // Generate OTP For Mobile
    @GET(Constants.WEB_SERVICES.FORGOT_OTP_IBO)
    Call<JsonObject> getIBOForgotPasswordOTP(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_FORGOT_PASSWORD) String IBOId);

    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_FORGOT_PASSWORD)
    Call<JsonObject> getChangedPassword(@Field(Constants.WEB_SERVICES_PARAM.KEY_USERNAME) String Username);

    // Verify OTP for Mobile
    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.FORGOT_OTP_VERIFY_IBO)
    Call<JsonObject> postIBOForgotPasswordOTPVerify(@Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_PHONE_NUMBER_FORGOT_PASSWORD_VERIFY) String phone,
                                                    @Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_OTP_FORGOT_PASSWORD_VERIFY) String otp,
                                                    @Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_FORGOT_PASSWORD_VERIFY) String id,
                                                    @Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_CODE_FORGOT_PASSWORD_VERIFY) String code,
                                                    @Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_PASSWORD_FORGOT_PASSWORD_VERIFY) String password);

    @GET(Constants.WEB_SERVICES.FORGOT_IBO)
    Call<JsonObject> getIBOForgotPassword(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_FORGOT_PASSWORD) String IBOId);

    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_POST_UPDATE_PROFILE_PIC)
    Call<JsonObject> UpdateProfilePic(@Field(Constants.WEB_SERVICES_PARAM.KEY_UPDATE_PIC_TYPE) String Image);

    @GET(Constants.WEB_SERVICES.WS_PROFILE)
    Call<MyAccount> getMyAccountDetails();

    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_POST_CHANGE_PASSWORD)
    Call<ChangePassword> ChangePassword(@Field(Constants.WEB_SERVICES_PARAM.KEY_OLD_PASSWORD_TYPE) String OldPassword,
                                        @Field(Constants.WEB_SERVICES_PARAM.KEY_NEW_PASSWORD_TYPE) String NewPassword);

    @GET(Constants.WEB_SERVICES.WS_GET_RATECHARTS)
    Call<RateCharts> getRateChartList();

    @GET(Constants.WEB_SERVICES.WS_CITY_LIST)
    Call<JsonObject> getCities(@Query(Constants.WEB_SERVICES_PARAM.KEY_STATE_ID) int StateId);

    @GET(Constants.WEB_SERVICES.WS_STATE_LIST)
    Call<StateDetails> getStates(@Query(Constants.WEB_SERVICES_PARAM.KEY_COUNTRY_NAME) String CountryName);


    @GET(Constants.WEB_SERVICES.WS_GET_INCOME_SUMMARY)
    Call<IncomeSummary> getIncomeSummary();

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME_LIST)
    Call<IncomeList> getIncomeList();

    @GET(Constants.WEB_SERVICES.WS_GET_IBO_LIST_PLACEMENT)
    Call<IBOListPlacement> getPlacementTree(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_INDEX) Integer PageIndex,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) Integer PageLength,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_SEARCH_TEXT) String SearchText);

    @GET(Constants.WEB_SERVICES.WS_GET_IBO_LIST_SPONSOR)
    Call<IBOListSponsor> getSponsortTree(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_INDEX) Integer PageIndex,
                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) Integer PageLength,
                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_SEARCH_TEXT) String SearchText);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<RetailIncomeList> getRetailIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<StarClubIncomeDetails> getStarClubIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);


    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<ThreeStarClubIncomeDetails> getThreeStarClubIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<GenerationIncomeDetails> getGenerationIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<SpotIncomeDetails> getSpotIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<GoldIncomeDetails> getGoldIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_MINI_BONANZA)
    Call<MiniBonanza> getMiniBonanza();

    @GET(Constants.WEB_SERVICES.WS_GET_BONANZA)
    Call<Bonanza> getBonanza();

    @GET(Constants.WEB_SERVICES.WS_GET_MINI_BONANZA_LEG)
    Call<MiniBonanzaLeg> getMiniBonanzaLegDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_LEG_KEY) String LegIBOKeyId);

    @GET(Constants.WEB_SERVICES.WS_GET_BONANZA_LEG)
    Call<MiniBonanzaLeg> getBonanzaLegDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_LEG_KEY) String LegIBOKeyId);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<CarProgramIncomeDetails> getCarProgramIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<SuperBoosterIncomeDetails> getSuperBoosterIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<LeadershipBonusDetails> getLeadershipBonus(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<SuBoosterIncomeDetails> getSuBoosterIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<SponsorIncomeDetails> getSponsorIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<BoosterIncomeDetails> getBoosterIncome(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_HOLIDAY_ACHIEVER_BONUS)
    Call<HolidayAchieverBonus> checkHolidayAchieverBonus();

    @POST(Constants.WEB_SERVICES.WS_GET_HOLIDAY_ACHIEVER_BONUS_SELECTION)
    Call<JsonObject> sendHolidayAchieverBonus(@Query((Constants.WEB_SERVICES_PARAM.KEY_ID)) Integer id,
                                              @Query((Constants.WEB_SERVICES_PARAM.KEY_FLAG)) String flag);

    @GET(Constants.WEB_SERVICES.WS_GET_PROJECT_LIST)
    Call<ProjectDetail> getProjectDetail();

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<RankBonusDetails> getRankBonus(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME)
    Call<DreamVolumeDetails> getDreamVolume(@Query(Constants.WEB_SERVICES_PARAM.KEY_INCOME_TYPE) String IncomeType);

    @GET(Constants.WEB_SERVICES.WS_GET_HOLIDAY_ACHIEVER)
    Call<HolidayAchieverDetails> getHolidayAchieverDetails();


    @GET(Constants.WEB_SERVICES.WS_GET_RATECHARTS_DETAILS)
    Call<RateChartsDetail> getRateChartsDetail(@Query(Constants.WEB_SERVICES_PARAM.KEY_RATE_CHART_ID) String RateID);


    @GET(Constants.WEB_SERVICES.WS_GET_MYSALE_LIST)
    Call<MySalesAavaasDetails> getMySalesAavaas(@Query(Constants.WEB_SERVICES_PARAM.KEY_CATEGORY) String CategoryType,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) String LengthType,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_START_INDEX) String IndexType,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PROJECT_ID) Integer ProjectType);

    @GET(Constants.WEB_SERVICES.WS_GET_RANK_HISTORY)
    Call<RankHistory> getRankHistory();

    @GET(Constants.WEB_SERVICES.WS_GET_RANK_AND_VOLUMES)
    Call<RankAndVolumes> getRankAndVolumes();

    @GET(Constants.WEB_SERVICES.WS_GET_INCOME_HISTORY)
    Call<IncomeHistory> getIncomeHistory();

    @GET(Constants.WEB_SERVICES.TEAM_LIST)
    Call<Updates> getTeamUpdates(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_INDEX) Integer PageIndex,
                                 @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) Integer PageLength);

    @GET(Constants.WEB_SERVICES.WS_GET_NEW_JOINEES)
    Call<NewJoinees> getNewJoinees();

    @GET(Constants.WEB_SERVICES.WS_GET_DOWNLINE_RANKS)
    Call<DownlineRanks> getDownlineRanks();

    @GET(Constants.WEB_SERVICES.WS_GET_DOWNLINE_IBO_RANK_LIST)
    Call<DownlineIBORankDetails> getDownlineIBORank(@Query(Constants.WEB_SERVICES_PARAM.KEY_RANK_ID) String RankID);

    //Personal update
    @GET(Constants.WEB_SERVICES.PERSONAL_LIST)
    Call<Updates> getPersonalUpdates(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_INDEX) Integer PageIndex,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) Integer PageLength);

    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_POST_IBO_GEO_DETAIL)
    Call<IBOGeoLocation> postIBOGeoLocation(@Field(Constants.WEB_SERVICES_PARAM.KEY_LATITUDE) double latitude,
                                            @Field(Constants.WEB_SERVICES_PARAM.KEY_LONGITUDE) double longitude);


    @GET(Constants.WEB_SERVICES.WS_GET_PRODUCT_MASTER)
    Call<ProductMaster> getProductMaster(@Query(Constants.WEB_SERVICES_PARAM.KEY_PROJECT_ID) Integer ProjectId);

    @GET(Constants.WEB_SERVICES.WS_GET_MYPURCHASE_LIST)
    Call<MyPurchasesAavaasDetails> getMyPurchaseVacation(@Query(Constants.WEB_SERVICES_PARAM.KEY_CATEGORY) String CategoryType,
                                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) String LengthType,
                                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_START_INDEX) String IndexType,
                                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_PROJECT_ID) Integer ProjectType);

    @GET(Constants.WEB_SERVICES.WS_GET_MYPURCHASE_LIST)
    Call<MyPurchasesAavaasDetails> getMyPurchase(@Query(Constants.WEB_SERVICES_PARAM.KEY_CATEGORY) String CategoryType,
                                                 @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) String LengthType,
                                                 @Query(Constants.WEB_SERVICES_PARAM.KEY_START_INDEX) String IndexType,
                                                 @Query(Constants.WEB_SERVICES_PARAM.KEY_PROJECT_ID) Integer ProjectType);


    @GET(Constants.WEB_SERVICES.WS_GET_HOLIDAY_CHARGES)
    Call<JsonObject> getHolidayCharges();


    @GET(Constants.WEB_SERVICES.SPONSOR_ID)
    Call<SponsorID> getSponsorID(@Query(Constants.WEB_SERVICES_PARAM.KEY_SPONSOR_ID) String sponsorID);

    @GET(Constants.WEB_SERVICES.PLACEMENT_ID)
    Call<PlacementID> getPlacementID(@Query(Constants.WEB_SERVICES_PARAM.KEY_SPONSOR_ID) String sponsorID,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PLACEMENT_ID) String placementID);


    @GET(Constants.WEB_SERVICES.REGISTATION_OTP)
    Call<RegistationOTP> getRegistationOtp(@Query(Constants.WEB_SERVICES_PARAM.KEY_MOBILE) String MobileNumber);


    @GET(Constants.WEB_SERVICES.PIN_DETAIL)
    Call<GetPin> getGetPin();

    @POST(Constants.WEB_SERVICES.WS_POST_PIN)
    Call<PostPinDetails> getpostpin(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_SUMMARY) String paymentsummary);

    @GET(Constants.WEB_SERVICES.WS_RESEND_PIN)
    Call<ReSendPin> getResendPin(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_SUMMARY) String paymentsummary);

    @GET(Constants.WEB_SERVICES.WS_BV_LIST)
    Call<BvDetails> getGetBvDetail();


    @POST(Constants.WEB_SERVICES.REGISTATION_POST)
    Call<Registation> getregistation(@Query(Constants.WEB_SERVICES_PARAM.KEY_SPONSOR_ID) String sponsorID,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_SPONSOR_NAME) String sponsorName,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PLACEMENT_ID) String placementID,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PLACEMENT_NAME) String placementName,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_FIRST) String firstName,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_MIDDLE_NAME) String middleName,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_MOBILE_LAST_NAME) String lastName,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_EMAIL_REGISTATION) String email,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_DOB) String dob,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PHONE) String phone);

    @GET(Constants.WEB_SERVICES.WS_BANK_DETAIL)
    Call<BackDetails> getGetBackDetails();

    @GET(Constants.WEB_SERVICES.WS_RELATION_DETAIL)
    Call<RelationDeatils> getGetRelationDetails();

    @GET(Constants.WEB_SERVICES.WS_PAN_DETAIL)
    Call<PanVerify> getGetPanVerify(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAN_CARD) String panCardNumber);

    @GET(Constants.WEB_SERVICES.WS_GET_IBO_DETAIL)
    Call<GetIboDetails> getGetIboDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_) String iboid);

    @POST(Constants.WEB_SERVICES.WS_POST_IBO_FULL_REGISTRATION)
    Call<FullRegistaionDetail> SendRegistaionFullDetail(@Query(Constants.WEB_SERVICES_PARAM.KEY_SPONSOR_ID) String sponsorId,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_SPONSOR_NAME) String sponsorName,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_PLACEMENT_ID) String placementId,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_PLACEMENT_NAME) String placementName,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_FIRST) String firstName,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_MIDDLE_NAME) String MiddleName,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_MOBILE_LAST_NAME) String LastName,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_EMAIL_REGISTATION) String emailId,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_PHONE) String PhoneNumber,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_DOB) String dob,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_GENDER) String gender,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_NEW) String address,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_STATE) String state,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_CITY) String city,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_PINCODE_NEW) String pincode,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_ALTE_NUMBER) String alteNumber,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_PAN_NUMBER) String pancardNumber,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_AADHAR_NUMBER) String AadharNumber,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_NOMINEE_NAME) String NomineeName,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_NOMINEE_SHIP) String NomineeRelationship,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_BANK_ID) Integer bankId,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_ACCOUNT_NAME) String AccountHolderName,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_ACCOUNT_NUMBER) String AccountNo,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_IFSC_CODE) String IFSCCode,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_BRANCH_NAME) String BranchName,
                                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_BRANCH_CITY) String BranchCity);


    @POST(Constants.WEB_SERVICES.WS_UPDATE_IBO_DETAIL)
    Call<FullRegistaionDetail> SendRegistaionUpdateDetail(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_) String iboId,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_GENDER) String gender,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_NEW) String address,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_STATE) String state,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_CITY) String city,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_PINCODE_NEW) String pincode,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_ALTE_NUMBER) String alteNumber,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_PAN_NUMBER) String pancardNumber,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_AADHAR_NUMBER) String AadharNumber,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_NOMINEE_NAME) String NomineeName,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_NOMINEE_SHIP) String NomineeRelationship,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_BANK_ID) Integer bankId,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_ACCOUNT_NAME) String AccountHolderName,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_ACCOUNT_NUMBER) String AccountNo,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_IFSC_CODE) String IFSCCode,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_BRANCH_NAME) String BranchName,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_BRANCH_CITY) String BranchCity);

    @POST(Constants.WEB_SERVICES.WS_POST_SOP)
    Call<JSONObject> PostSop();

    @POST(Constants.WEB_SERVICES.WS_POST_SP)
    Call<CreateTicketModel> PostSpConditions();

    @POST(Constants.WEB_SERVICES.WS_POST_LAT_LONG)
    Call<JSONObject> PostLatLong(@Query(Constants.WEB_SERVICES_PARAM.KEY_LAT) double lat,
                                 @Query(Constants.WEB_SERVICES_PARAM.KEY_LON) double lon);

    //Ecom
    @GET(Constants.WEB_SERVICES.WS_ADVERTISEMENT_IMAGES_ECOM)
    Call<AdvertisementImageListEcom> getAdvertisementListEcom( @Query(Constants.WEB_SERVICES_PARAM.KEY_ISNEBPRO) boolean isneb,
                                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_ISHome) boolean ishome);

    @GET(Constants.WEB_SERVICES.WS_MINIMUM_PV)
    Call<MinimumPvModel> getMinimumPV();

    @GET(Constants.WEB_SERVICES.WS_GET_SITE_PRODUCTS)
    Call<SiteProductModel> getSiteProductList();

    @GET(Constants.WEB_SERVICES.WS_ADVERTISEMENT_IMAGES_ECOM)
    Call<Baners> getBanersList( @Query(Constants.WEB_SERVICES_PARAM.KEY_ISNEBPRO) boolean isneb,
                                @Query(Constants.WEB_SERVICES_PARAM.KEY_ISHome) boolean ishome);

    @GET(Constants.WEB_SERVICES.WS_GET_EVENTS)
    Call<Events> getEventList();

    @GET(Constants.WEB_SERVICES.WS_GET_SITE_PROGRESS_IMAGES)
    Call<NewSiteProgressImages> getSiteProgressImages(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_INDEX) int PageIndex,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) int PageLength,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_PROJECT_ID) Integer SiteID,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_MONTH) Integer Month,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_YEAR) String Year);

    @GET(Constants.WEB_SERVICES.WS_GET_EVENT_DETAILS)
    Call<NewSubEventDetails> getEvent(@Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_INDEX) int PageIndex,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) int PageLength,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_EVENT_NAME) String EventName,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_USERID) String userId);

    @GET(Constants.WEB_SERVICES.WS_GET_PRODUCT_LIST)
    Call<ProductListModelEcom> getProductListEcom();

    @GET(Constants.WEB_SERVICES.CATEGORY_DETAILS_LIST_ECOM)
    Call<CategoryDetailsListModelEcom> getCategoryDetailsList(@Query(Constants.WEB_SERVICES_PARAM.KEY_CATEGORY_ID) Integer catid);

    @GET(Constants.WEB_SERVICES.WS_GET_CATEGORY_LIST)
    Call<CategoryListModelEcom> getCategoryListEcom();


    @GET(Constants.WEB_SERVICES.WS_GET_ORDER_LIST)
    Call<OrderListModelEcom> getOrderListEcom();

    @GET(Constants.WEB_SERVICES.WS_GET_OFFER_LIST)
    Call<EcomOfferModel> getOfferListEcom();


    @POST(Constants.WEB_SERVICES.WS_DELETE_CART)
    Call<MarkCartDeleteModel> deleteCart(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_ISDELETED) boolean issuccess,
                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID,
                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceId);

    @GET(Constants.WEB_SERVICES.WS_DELETE_EWALLET)
    Call<MarkCartDeleteModel> deleteEwallet(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_ORDER_NUMBER) String orderid,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboID,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_AMOUNT) String amountid);

    @GET(Constants.WEB_SERVICES.WS_GET_CSUPPORT_CATEGORY)
    Call<CustomerSupportCategory> getSupportCategoryList();

    @GET(Constants.WEB_SERVICES.WS_GET_WALLET_HISTORY)
    Call<WalletHistory> getWallethistory(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboid);

    @GET(Constants.WEB_SERVICES.WS_GET_CSUPPORT_ORDER)
    Call<CustomerSupportOrder> getSupportOrderList(@Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_CSUPPORT_TICKET_LIST)
    Call<CustomerSupportTicket> getSupporTicketList(@Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID,
                                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_STATUS) String status,
                                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_CATEGORY_ID) String ticketCategoryId);


    @GET(Constants.WEB_SERVICES.WS_GET_CSUPPORT_TICKET_DETAIL_LIST)
    Call<CustomerSupportDetailTicket> getSupporTicketDetailList(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_MASTER_ID) int ticketMasterId);

    //@FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_GET_CSUPPORT_TICKET_MESSAGE_LIST)
    @Multipart
    Call<CreateTicketModel> supportMessage(@Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_ID) RequestBody ticketMasterId,
                                           @Part MultipartBody.Part imageFile,
                                           @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_MESSAGE) RequestBody message);

    @POST(Constants.WEB_SERVICES.WS_SAVE_TICKET)
    @Multipart
    Call<CreateTicketModel> createTicket(@Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_USER_ID) RequestBody UserId,
                                         @Part MultipartBody.Part imageFile,
                                         @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_CREATE_CATEGORY_ID) RequestBody TicketCategoryId,
                                         @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_SUBJECT) RequestBody Subject,
                                         @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_REMARK) RequestBody Remarks,
                                         @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_ORDER_NUMBER) RequestBody OrderNumber);

    @POST(Constants.WEB_SERVICES.ADD_TO_CART_POST)
    Call<AddToCartModel> getAddToCartModelCall(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_QUANTITY) Integer productQuantity,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_CART_FLAG) String CartFlag);

    @POST(Constants.WEB_SERVICES.ADD_TO_CART_POST_NOLOGIN)
    Call<AddToCartModel> getAddToCartModelCallNOLogin(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_QUANTITY) Integer productQuantity,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_CART_FLAG) String CartFlag);


    @POST(Constants.WEB_SERVICES.ADD_TO_CART_POST)
    Call<AddToCartModel> getAddToCartModelCallNotification(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) String productID,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_QUANTITY) Integer productQuantity,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_CART_FLAG) String CartFlag);


    @POST(Constants.WEB_SERVICES.CANCEL_ORDER)
    Call<CancelOrderModel> cancelOrderCall(@Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_ID_ECOM) String cancelOrderDetailsId,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_REASON_ECOM) String cancelReason,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_REMARK_ECOM) String cancelRemark,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_QUANTITY_ECOM) String cancelQuantity);


    @POST(Constants.WEB_SERVICES.PLACE_ORDER_POST)
    Call<PlaceOrderModel> placeOrder(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID);

    @POST(Constants.WEB_SERVICES.DELETE_FROM_CART_POST)
    Call<DeleteCartModel> getDeleteFromCartModelCall(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID
    );

    @POST(Constants.WEB_SERVICES.DELETE_FROM_CART_POST_Nologin)
    Call<DeleteCartModel> getDeleteFromCartModelCallNologin(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID
    );

    @POST(Constants.WEB_SERVICES.DELETE_FROM_CART_POST_v2)
    Call<DeleteCartModel> getDeleteFromCartModelCallV2(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                       @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                       @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID,
                                                       @Query(Constants.WEB_SERVICES_PARAM.KEY_ID) Integer id);

    @POST(Constants.WEB_SERVICES.ADD_FREE_PRODUCTS)
    Call<FreeProductsModel> submitProducts(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_PROMO_ID) Integer promoId);

    @POST(Constants.WEB_SERVICES.DELETE_ADDRESS_DETAILS)
    Call<DeleteAddressModel> deleteAddressDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_ID_ECOM) Integer ID);


    @GET(Constants.WEB_SERVICES.BANERS_LIST_ECOM)
    Call<BanersEcom> getBanersListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productid);

    @GET(Constants.WEB_SERVICES.PRODUCT_VARIANTS)
    Call<OtherProducts> getProductVariants(@Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) Integer pickupid,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) Integer productid);

    @GET(Constants.WEB_SERVICES.ID_CARD)
    Call<IDCardModel> getIDCard(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboid);


/*


    @GET(Constants.WEB_SERVICES.MY_CART_LIST_ECOM)
    Call<CartListModelEcom> getMyCartListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID);
*/


    @GET(Constants.WEB_SERVICES.MY_CART_LIST_ECOM)
    Call<CartListModelEcom> getMyCartListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_Pickup) int cityId);

    @GET(Constants.WEB_SERVICES.MY_CART_LIST_ECOM_WO_LOGIN)
    Call<CartListModelEcom> getMyCartListEcomWoLogin(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_Pickup) int cityId);

    @GET(Constants.WEB_SERVICES.MY_CART_WALLET_AMOUNT)
    Call<WalletModel> getMyWalletAmount(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboid);

    @GET(Constants.WEB_SERVICES.MY_BAL_WALLET)
    Call<WalletFreezeModel> getBalWalletAmount(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboid);

    @GET(Constants.WEB_SERVICES.MY_FREE_PRODUCT_LIST_ECOM)
    Call<FreeProductListModel> getMyFreeProduct(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SUBTOTAL) double subTotal,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTALPV) double totalPV);

    @GET(Constants.WEB_SERVICES.MY_FREE_PRODUCT_REWARD_LIST_ECOM)
    Call<FreeProductListModel> getMyFreeRewardProduct(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_VARIANTID) long variantid,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_QUANTITY) int quantity);

    // (string deviceid, string userid, long oldvariant, long newvariant, int quantity)
    @GET(Constants.WEB_SERVICES.MY_FREE_PRODUCT_REWARD_UPDATE_ECOM)
    Call<FreeProductsModel> updateMyFreeRewardProduct(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_NEWVARIANTID) long newvariantid,
                                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_OLDVARIANTID) long variantid,
                                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_QUANTITY) int quantity,
                                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceid
    );


    @GET(Constants.WEB_SERVICES.MY_CART_REVIEW_LIST_ECOM)
    Call<CartListModelReviewEcom> getMyCartListReviewEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupid);

    @GET(Constants.WEB_SERVICES.MY_CART_REMOVE_LIST_ECOM)
    Call<CartListModelRemoveEcom> getMyCartListRemoveEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupid);


    @GET(Constants.WEB_SERVICES.MY_CARD_DETAILS_ECOM)
    Call<CardDetailsModelEcom> getCardDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM_PAY) String addressType,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_SHIPPING_ADDRESS_ECOM) String shippingAddress,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_BILLING_ADDRESS_ECOM) String billingaddress,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PICKUP_POINT_ECOM) String pickuppoint,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_MODE_ECOM) String paymentmode,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String isWaiveOff,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_PAYMENT) String userpayment);
    @GET(Constants.WEB_SERVICES.MY_CARD_DETAILS_ECOM_Paytm)
    Call<CardDetailsModelEcom> getCardDetailsPaytm(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM_PAY) String addressType,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_SHIPPING_ADDRESS_ECOM) String shippingAddress,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_BILLING_ADDRESS_ECOM) String billingaddress,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PICKUP_POINT_ECOM) String pickuppoint,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_MODE_ECOM) String paymentmode,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String isWaiveOff,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_PAYMENT) String userpayment);



    @GET(Constants.WEB_SERVICES.MY_CARD_DETAILS_PAYTM)
    Call<CardDetailsModelEcom> getCardDetailsV2Paytm(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM_PAY) String addressType,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SHIPPING_ADDRESS_ECOM) String shippingAddress,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_BILLING_ADDRESS_ECOM) String billingaddress,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PICKUP_POINT_ECOM) String pickuppoint,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_MODE_ECOM) String paymentmode,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String isWaiveOff,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_PAYMENT) String userpayment,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_EWALLET_PAYMENT) String ewallet);
    @GET(Constants.WEB_SERVICES.MY_CARD_DETAILS_ECOMV2)
    Call<CardDetailsModelEcom> getCardDetailsV2(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM_PAY) String addressType,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SHIPPING_ADDRESS_ECOM) String shippingAddress,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_BILLING_ADDRESS_ECOM) String billingaddress,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PICKUP_POINT_ECOM) String pickuppoint,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_MODE_ECOM) String paymentmode,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String isWaiveOff,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_PAYMENT) String userpayment,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_EWALLET_PAYMENT) String ewallet);

    @GET(Constants.WEB_SERVICES.MY_CART_TOTAL_COUNT_LIST_ECOM)
    Call<CartListTotalCountModelEcom> getMyCartTotalCountListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                                  @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_TRENDING_LIST)
    Call<TrendingProductModel> getTrendingProduct();

    @GET(Constants.WEB_SERVICES.WS_GET_NEW_LIST)
    Call<TrendingProductModel> getNewProduct();

    @GET(Constants.WEB_SERVICES.ADDRESS_LIST_ECOM)
    Call<AddressData> getAddressListEcom();

    @GET(Constants.WEB_SERVICES.PICK_UP_LIST_ECOM)
    Call<PickUpAddressData> getPickUpListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_CITY_ID_ECOM) String cityID);

    @POST(Constants.WEB_SERVICES.PRODUCT_SHARE)
    Call<ShareRefID> getReferenceId(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String ibokeyid,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PRODUCT_ID) int productId,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_REF_ID) String refid,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_LINK) String link);

    @POST(Constants.WEB_SERVICES.PRODUCT_REVIEW)
    Call<AddReviewDetail> addReview(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_PRODUCT) Long productId,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userid,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TITLE) String title,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_DESCRIPTION) String description,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_RATING) String rating);

    @GET(Constants.WEB_SERVICES.MY_PROFILE_ECOM)
    Call<ProfileModelEcom> getMyProfileEcom();

    @GET(Constants.WEB_SERVICES.TRACK_ORDER_ECOM)
    Call<TrackListModelEcom> getTrackEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_ID) String orderID);


    @GET(Constants.WEB_SERVICES.WS_GET_PROMO_CODES)
    Call<PromoCodeModel> getPromoCodes(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_ACTIVE_CODES)
    Call<ActivePromoCodeModel> getActivePromoCodes(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_GENERATE_COUPON)
    Call<GenerateCouponModel> getCoupon();

    @GET(Constants.WEB_SERVICES.MY_MRP_PRICE_ECOM)
    Call<ShowMRPPriceModelEcom> getMRPPriceEcom();


    @POST(Constants.WEB_SERVICES.POST_CODE)
    Call<AddAddressDetail> generatePromocode(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String ibokeyid,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_PROMO_MASTER_ID) Integer promoid);

    @POST(Constants.WEB_SERVICES.ADD_ADDRESS_DETAILS)
    Call<AddAddressDetail> AddAddressDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_MOBILE_ECOM) String mobile,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_FULLNAME_ECOM) String fullName,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_ONE_ECOM) String addressLine1,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TWO_ECOM) String addressLine2,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_LANDMARK_ECOM) String landmark,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_CITY_ECOM) String city,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_STATE_ECOM) String state,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_PINCODE_ECOM) String pinCode,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM) String addressType,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceId);

    @POST(Constants.WEB_SERVICES.SUCCESS_UPI)
    Call<AddAddressDetail> successUPI(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_OrderId) String orderID,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_txnID) String txnID,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Approval) String approval,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Response) String response,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Status) String status,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_txnREF) String txnRef,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Remarks) String Remarks);

    //mihpayid=&mode=&status=&unmappedstatus=&key=&txnid=
    @POST(Constants.WEB_SERVICES.SUCCESS_PayU_UPI)
    Call<AddAddressDetail> successPayUUPI(@Query(Constants.WEB_SERVICES_PARAM.KEY_mihpayid) String mihpayid,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_mode) String mode,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_status) String status,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_unmap) String unmap,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_key) String key,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_txnid) String txnId);


    @POST(Constants.WEB_SERVICES.UPDATE_ADDRESS_DETAILS)
    Call<AddAddressDetail> editAddressDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_MOBILE_ECOM) String mobile,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_FULLNAME_ECOM) String fullName,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_ONE_ECOM) String addressLine1,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TWO_ECOM) String addressLine2,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_LANDMARK_ECOM) String landmark,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_CITY_ECOM) String city,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_STATE_ECOM) String state,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PINCODE_ECOM) String pinCode,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM) String addressType,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_ID_ECOM) Integer id);

    @POST(Constants.WEB_SERVICES.REGISTER_ACCOUNT)
    Call<RegisterModelEcom> registerAccountEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_FIRST_NAME) String firstName,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_LAST_NAME) String lastName,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_MOBILE) String mobile,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_EMAIL) String email,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_GENDER) String gender);

    @GET(Constants.WEB_SERVICES.LOGIN_ACCOUNT)
    Call<LoginModelEcom> loginAccountEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_MOBILE_NO) String mobile);


    @GET(Constants.WEB_SERVICES.WS_GET_SEARCH)
    Call<SearchModelEcom> getSearch(@Query(Constants.WEB_SERVICES_PARAM.KEY_SEARCH_TEXT_ECOM) String SearchText);


    @GET(Constants.WEB_SERVICES.WS_CATEGORY)
    Call<NewCategoryProductDetails> getCategory(@Query(Constants.WEB_SERVICES_PARAM.KEY_CATEGORY_ID) Integer catid,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_CITY_ID) Integer pickupid,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_INDEX) int PageIndex,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) int PageLength);


    @POST(Constants.WEB_SERVICES.PAYMENT_SUCESS_ECOM)
    Call<PaymentSuccessEcomModel> paymentSuccessEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_NAME) String userName,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentid,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_ORDER_ID) String orderid,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE) String addressType,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_SHIPPING_ADDRESS) String shippingAddress,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_BILLING_ADDRESS) String billingAddress,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PICKUP_POINT) String pickupPoint,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_MODE) String paymentMode);

    /*Dwarka Holiday*/
    @GET(Constants.WEB_SERVICES.MY_CARD_DETAILS_DWARKA)
    Call<CardDwarkaModelEcom> getCardDetailsDwarka(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String waiveOff,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PRODUCT_ID) String productId,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_VERSION) String version);

    @GET(Constants.WEB_SERVICES.DWARKA_UPI)
    Call<CardDwarkaModelEcom> getUPIDwarka(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String userID,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String waiveOff,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PRODUCT_ID) String productId);


    @GET(Constants.WEB_SERVICES.DWARKA_TIMEPLAN_UPI)
    Call<CardDwarkaModelEcom> getTimeplanUPIDwarka(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String userID,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String waiveOff,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PRODUCT_ID) String productId,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_Paymentplan_ID) String paymentId);


    @POST(Constants.WEB_SERVICES.PAYMENT_SUCESS)
    Call<PaymentSuccess> paymentSuccess(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_NAME) String userName,
                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentid,
                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_ORDER_ID) String orderid,
                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID_DWARKA) String productId,
                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                        @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String waiveOff);

    @POST(Constants.WEB_SERVICES.PAYMENT_SUCESS_UPI)
    Call<AddAddressDetail> paymentUpiSuccess(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboID,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_UPI_ORDER_ID) String orderid,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_txnID) String txnid,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Approval) String approval,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Response) String response,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Status) String status,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_txnREF) String txnRef,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Remarks) String remarks);


    @GET(Constants.WEB_SERVICES.DWARKA_PACKAGE)
    Call<PackageListModelDwarka> getDwarkaPackage();

    @GET(Constants.WEB_SERVICES.DWARKA_EMI_PLAN)
    Call<PackageEMIModelDwarka> getDwarkaEMIPackage(@Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID_DWARKA) String pID,
                                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_Paymentplan_ID) String planID,
                                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_TOKENN) String token);

    @GET(Constants.WEB_SERVICES.DWARKA_PLAN)
    Call<DwarkaPlanModel> getDwarkaPlan();

    @GET(Constants.WEB_SERVICES.WS_GET_VERSION)
    Call<VersionCheck> getVersion();

    @GET(Constants.WEB_SERVICES.DWARKA_GATEWAY)
    Call<DwarkaUpiModel> getDwarkaGateway();

    @GET(Constants.WEB_SERVICES.EBC_ECOM)
    Call<EBCBannerModel> getSiteProductList(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC_DECS) String ecomProductDetailsId);


    /*UNI COMMERCE API*/
    @GET(Constants.WEB_SERVICES.UNI_GET_TOKEN)
    Call<JsonObject> getUniCommToken(@Query(Constants.WEB_SERVICES_PARAM.KEY_USERNAME_) String username,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PASSWORD_) String password,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_GRANT_TYPE) String GrantType,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_CLIENT_ID) String ClientID);

   /*@POST(Constants.WEB_SERVICES.WS_GET_TOKEN)
     Call<JsonObject> getToken(@Field(Constants.WEB_SERVICES_PARAM.KEY_USERNAME_) String username,
                              @Field(Constants.WEB_SERVICES_PARAM.KEY_PASSWORD_) String password,
                              @Field(Constants.WEB_SERVICES_PARAM.KEY_GRANT_TYPE) String GrantType);*/

    @GET(Constants.WEB_SERVICES.EBC_DETAILS_ECOM)
    Call<EBCDescriptionModel> getEBCDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) String idEBC);

    @GET(Constants.WEB_SERVICES.EBC_GET_REVIEWS)
    Call<ReviewsModel> getReviews(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String idEBC,
                                  @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID_ECOM) String idproduct);

    @GET(Constants.WEB_SERVICES.EBC_DETAILS_ECOM_UCQuantity)
    Call<EBCDescriptionModel> getEBCDetailsWithUCQuantity(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) String idEBC,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupidEBC);

    @GET(Constants.WEB_SERVICES.EBC_DETAILS_ECOM_UCQuantityV2)
    Call<EBCDescriptionModel> getEBCDetailsWithUCQuantityV2(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) String idEBC,
                                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupidEBC);

    @GET(Constants.WEB_SERVICES.BOTTOM_BANNER_ECOM)
    Call<BottomBannerModel> getBottomBanner(@Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupid,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_ISNEBPRO) boolean isneb,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_ISHome) boolean ishome);

    //@FormUrlEncoded
    /*@POST(Constants.WEB_SERVICES.UNI_GET_INVENTORY)
    Call<InventoryModel> postInventory(@Query(Constants.WEB_SERVICES_PARAM.KEY_ITEM_TYPE_SKUS) List<String> itemTypeSKUs,
                                       @Query(Constants.WEB_SERVICES_PARAM.KEY_UPDATED_SINCE_IN_MINTUES) String updatedSinceInMinutes);*/
    /*@POST("api/login")
    Call<ApiResponse> loginUser(@Body HashMap<String, String> user);*/

    @POST(Constants.WEB_SERVICES.UNI_GET_INVENTORY)
    Call<InventoryModel> postInventory(@Body InventoryRequestModel inventoryModel);

    @POST(Constants.WEB_SERVICES.UNI_GET_ORDER_STATUS)
    Call<OrderStatusModel> getOrderStatus(@Body OrderStatusRequestModel orderStatusRequestModel);

    @GET(Constants.WEB_SERVICES.UNI_GET_PDF)
    Call<ResponseBody> getPDF(@Query("invoiceCodes") String invoice);

    @GET(Constants.WEB_SERVICES.WS_GET_PV_TABLE)
    Call<PVTableModel> getPvTable(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String idIBO);

    @GET(Constants.WEB_SERVICES.WS_GET_ID_DETAILS)
    Call<IdDetailsModel> getIdDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String idIBO);

    @GET(Constants.WEB_SERVICES.WS_GET_WEEKLY_MATCHING)
    Call<WeeklyMatchingModel> getWeeklyMatching(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String idIBO);

    @GET(Constants.WEB_SERVICES.WS_GET_COMMUNITY_GROWTH)
    Call<CommunityGrowthModel> getCommunityGrowth(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String idIBO);

    @GET(Constants.WEB_SERVICES.WS_GET_INVOICE)
    Call<TrackOrderModel> getInvoice(@Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_NO) String orderNo);



    @GET(Constants.WEB_SERVICES.Get_OfferImageResponse)
    Call<GetOfferImageResponse> getOfferImageResponse();

}
