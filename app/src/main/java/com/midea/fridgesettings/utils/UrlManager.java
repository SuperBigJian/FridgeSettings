package com.midea.fridgesettings.utils;


import com.midea.fridgesettings.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlManager {
	private static final String TAG = "UrlManager";

    /**服务端接口是否正式环境的标志，打包时根据需要更改*/
	public static final boolean ONLINE = BuildConfig.ONLINE_FLAG;
	/**
	 * 商城接口是否正式环境的标志，打包时根据需要更改
	 */
	public static final boolean MALL_ONLINE = BuildConfig.ONLINE_FLAG;

	public static final String TEST_SERVER_URL = "http://i.mideav.com:8088/";

	public static final String ONLINE_SERVER_URL = "http://test.mideav.com:8080/";

	public static final String LOCAL_SERVER_URL = "http://192.168.199.199:8081/";

	public static final String TEST_SERVER_URL_3 = "https://mztapi.mideav.com/";

	public static final String ONLINE_SERVER_URL_3 = "https://mzpapi.mideav.com/";

    /**获取服务器地址*/
    public static String getServerUrl() {
		if("localdebug".equals(BuildConfig.BUILD_TYPE)) {
			return LOCAL_SERVER_URL;
		}
        if(ONLINE) {
            return ONLINE_SERVER_URL;
        }
        return TEST_SERVER_URL;
    }

	/**获取服务器地址*/
	public static String getServer3Url() {
		if(ONLINE) {
			return ONLINE_SERVER_URL_3;
		}
		return TEST_SERVER_URL_3;
	}

	/**商城测试地址*/
	public static final String TEST_MALL_HOST = "http://api2.mideav.com";
	/**商城正式地址*/
	public static final String ONLINE_MALL_HOST = "http://api.mideav.com";

    /**获取商城地址*/
    public static String getMallUrl() {
        if(MALL_ONLINE) {
            return ONLINE_MALL_HOST;
        }
        return TEST_MALL_HOST;
    }

	public static final String PHOTO_URL = getServerUrl() + "fridgeimage/photo/";

	public static final String SHARE_DISH_URL = "http://www.mideav.com/cookbook/index.html?dishid=";

	public static final String SHARE_COOK_URL = "http://www.mideav.com/ugc/detail.html?id=";

	public static final String SHARE_FOOD_URL = "http://www.mideav.com/ugc/show.html?id=";

	public static final String MIDEA_PIC_URL = "http://www.mideav.com/data/attachment/";
	public static final String DOWNLOAD_URL = "http://www.mideav.com/smart/download1.html";// 分享下载链接

	public static String getLoginUrl() {
		return getServerUrl() + "ids/apps/login";
	}

	public static String getRegisterUrl() {
		return getServerUrl() + "ids/apps/register";
	}

	public static String getThirdLoginUrl() {
		return getServerUrl() + "ids/apps/thirdlogin";
	}

	public static String getThirdBinderUrl() {
		return getServerUrl() + "ids/apps/bindaccount";
	}

	public static String getEditNameUrl() {
		return getServerUrl() + "ids/apps/edituser";
	}

	public static String getEditpasswordUrl() {
		return getServerUrl() + "ids/apps/editpassword";
	}

	public static String getVerifyCodeUrl() {
		return getServerUrl() + "ids/apps/getCaptchaV2.do";
	}

	public static String verifyCodeUrl() {
		return getServerUrl() + "ids/apps/checkPhoneMessage";
	}

	public static String getFanCount() {
		return getServerUrl() + "ids/apps/getFansNum";
	}

	public static String getResetPasswordUrl() {
		return getServerUrl() + "ids/apps/resetPassword";
	}

	public static String getFoodTypeUrl() {
		return getServerUrl() + "apps/food/typeList";
	}

	public static String getFoodType2Url() {
		return getServerUrl() + "apps/food/fetypeList";
	}

	public static String getFoodListUrl() {
		return getServerUrl() + "apps/food/fnutritionList";
	}

	public static String getEfficacyListUrl() {
		return getServerUrl() + "apps/food/efficacyList";
	}

	public static String getEfficacyDetailsUrl() {
		return getServerUrl() + "apps/food/efficacyDetails";
	}

	public static String getFoodDetailUrl() {
		return getServerUrl() + "apps/food/fnutritionDetails";
	}

	public static String getNewPwdUrl() {
		return getServerUrl() + "ids/apps/resetPasswordBymobile";
	}

	public static String getCodeScanUrl(String code) {
		// 测试环境此接口调用不通
		return ONLINE_SERVER_URL + "fridge/productInfoAction!getProductInfo.action?barcode=" + code;
	}

	public static String getDeleteFridgeFoodUrl() {
		return getServerUrl() + "fridgefood/deleteuserfood";
	}

	public static String getUpdateFridgeFoodUrl() {
		return getServerUrl() + "fridgefood/updateuserfood";
	}

	public static String getFridgeFoodListUrl() {
		return getServerUrl() + "awe/userfood/getuserfoodlist";
	}

	/** 通过id获取冰箱食材的url */
	public static String getFridgeFoodByIdUrl() {
		return getServerUrl() + "fridgefood/getuserfoodbyid";
	}

	public static String getNutritionFoodNameUrl(String deviceId, String dateSize) {
		return getServerUrl() + "fridge/consumptionAction!recommendFoodList.action?fridgeid=" + deviceId + "&dateSize=" + dateSize;
	}

	public static String getNutritionFoodNameUrlByUid(String userId, String dateSize) {
		return getServerUrl() + "fridge/consumptionAction!recommendFoodList.action?cid=" + userId + "&dateSize=" + dateSize;
	}

	/** 食材百科功效的url */
	public static String getFoodWikiFunctionUrl() {
		return getServerUrl() + "foodClass/getGongxiao";
	}

	/** 食材百科实用百科的url */
	public static String getFoodWikiChoiceTipUrl() {
		return getServerUrl() + "foodClass/getXuangou";
	}

	/** 食材百科选购要诀的url */
	public static String getFoodWikiCyclopediaUrl() {
		return getServerUrl() + "foodClass/getShiyong";
	}

	/** 添加购物清单的URL */
	public static String getAddBuyInfoUrl() {
		return getServerUrl() + "buy/addBuyInfo";
	}

	/** 获取购物清单的URL */
	public static String getBuyInfoUrl(String userId) {
		return getServerUrl() + "buy/getBuyInfo?userid=" + userId;
	}

	/** 删除购物清单的URL */
	public static String getDeleteBuyInfoUrl(String userId, String recordIds) {
		return getServerUrl() + "buy/deleteBuyInfo?userid=" + userId + "&recordids=" + recordIds;
	}

	/** 获取商城banner的URL */
	public static String getShoppingmallBannerUrl() {
		return getServerUrl() + "credit/user/getindexpic";
	}

	/** 获取商城商品列表的URL */
	public static String getShoppingmallGoodsUrl() {
		return getServerUrl() + "credit/user/getallcreditgood";
	}

	/** 获取商城商品详情的URL */
	public static String getShoppingmallGoodsDetailUrl() {
		return getServerUrl() + "credit/user/getcreditgoodbyid";
	}

	/** 获取商品购买URL */
	public static String getShoppingmallGoodsBuyUrl() {
		return getServerUrl() + "credit/user/exchangegood";
	}

	/** 获取金币变更记录URL */
	public static String getCoinRecordsUrl() {
		return getServerUrl() + "credit/user/getcreditrecord";
	}

	/** 获取金币购买商品记录URL */
	public static String getCoinBuyRecordsUrl() {
		return getServerUrl() + "credit/user/getexchangerecord";
	}

	/** 食材储存建议URL */
	public static String getRefreshmentAdviseUrl(String foodName) {
        //测试环境此接口不通
		return ONLINE_SERVER_URL + "fridge/fridgeFoodAction!refreshmentAdvise.action?foodname=" + foodName;
	}

	/** 食材储存建议URL新接口 */
	public static String getRefreshmentAdviseUrl() {
		return getServerUrl() + "fridgefood/refreshmentadvise";
	}

	/** 添加冰箱食材URL */
	public static String getAddFridgeFoodUrl() {
		return getServerUrl() + "fridgefood/adduserfood";
	}

	/** 食材分类URL */
	public static String getFoodSpeciesUrl() {
		return getServerUrl() + "apps/food/typeList";
	}

	/** 食材详细URL，根据typeid */
	public static String getFoodDetailUrl(int typeid) {
		return getServerUrl() + "apps/food/foodList?typeid=" + typeid;
	}

	/** 食材百科食材详细URL，根据typeid */
	public static String getFoodWikiMaterialUrl() {
		return getServerUrl() + "foodClass/getFoodList";
	}

	/** 掌厨会话URL */
	public static String getZhangChuSessionUrl() {
		return "http://admin.izhangchu.com/HandheldKitchen/api/fridge/material!differentiate.do?callback=?&appid=com.midea.ai.fridge&deviceid=68464&location=深圳";
	}

	public static String getRefreshUrl() {
		return getServerUrl() + "ids/apps/refresh";
	}

	public static String getPostLogUrl()// 上传日志
	{
		return getServerUrl() + "logs/logUpload";
	}

	public static String getRelationUrl()// 粉丝、关注
	{
		return getServerUrl() + "ids/apps/getRelationList";
	}

	public static String getShareFood()// 分享食材
	{
		return getServerUrl() + "hotalbum/getsharefoods";
	}

	public static String getserachUserUrl()// 粉丝、关注
	{
		return getServerUrl() + "ids/apps/serachUser";
	}

	public static String getsetRelationUrl()// 粉丝、关注关系
	{
		return getServerUrl() + "ids/apps/setRelation";
	}

	public static String getHotPartUrl() {
		return "http://www.mideav.com/api.php?mod=json&bid=12";
	}

	public static String getHotCardUrl() {
		return "http://www.mideav.com/api.php?mod=json&bid=27";
	}

	/** 获取热门推荐地址 */
	public static String getHotRecommendUrl() {
		return getServerUrl() + "apps/dish/list";
	}
	/** 获取热门推荐详情 */
	public static String getHotRecommend() {
		return getServerUrl() + "apps/dish/getDishById";
	}
	/**获取冰箱食谱*/
	public static String getFridgeFoodUrl() {
		return getServerUrl() + "awe/dish/getCookByFood";
	}
	/**获取sessionId*/
	public static String getSessinidUrl() {
		return "http://admin.izhangchu.com/HandheldKitchen/api/fridge/material!differentiate.do";
	}
	/**搜索食谱地址*/
	public static String getSeachFoodUrl()
	{
		return "http://admin.izhangchu.com/HandheldKitchen/api/fridge/vegetable!getVegetableByName.do";
	}
	/**获取冰箱食谱详情*/
	public static String getFridgeFoodRecommdUrl() {
		return "http://admin.izhangchu.com/HandheldKitchen/api/fridge/vegetable!getVegetableById.do";
	}

	/**添加食谱收藏地址*/
	public static String addRecipeCollectionUrl() {
		return getServerUrl() + "fridge/favoritesAction!addFavorites.action";
	}

	/**取消食谱收藏地址*/
	public static String deleteRecipeCollectionUrl(String userId, String dishId) {
		return getServerUrl() + "fridge/favoritesAction!deleteFavorites.action?cid=" + userId + "&dishids=" + dishId;
	}

	/**查询食谱是否收藏地址*/
	public static String isRecipeCollectionUrl(String userId, String dishId) {
		return getServerUrl() + "fridge/favoritesAction!queryFavoritesById.action?cid=" + userId + "&dishid=" + dishId;
	}

	/** 获取消息列表地址 */
	public static String getMessageListUrl() {
		return getServerUrl() + "message/getMessage";
	}

	/** 设置消息已读标志地址 */
	public static String getMsgReadTagUrl() {
		return getServerUrl() + "ids/apps/setreadmessage";
	}

	/** 删除消息地址 */
	public static String getMsgDeleteUrl() {
		return getServerUrl() + "ids/apps/deletemessage";
	}

	/** 删除消息列表地址 */
	public static String deleteMessageUrl() {
		return getServerUrl() + "messageV2/deleteMessage.do";
	}
	
	/** 清空消息地址*/
	public static String getMsgDeleteAllUrl() {
		return getServerUrl() + "ids/apps/clearmessage";
	}

	public static String getBindFridgeUrl() {
		return getServerUrl() + "fridge/user/bind";
	}

	/**
	 * 搜索达人菜谱接口
	 * 
	 * @return
	 */
	public static String getSearchUgcUrl() {
		return getServerUrl() + "apps/guc/cookbookSearch";
	}


	public static String getHotDishDetailUrl() {
		return getServerUrl() + "apps/dish/getDishById";
	}

	public static String getFoodHealthClassUrl() {
		return getServerUrl() + "foodClass/getHealthList";
	}

	public static String getFoodHealthIntroUrl() {
		return getServerUrl() + "foodClass/getReco";
	}

	public static String getFoodHealthYijiUrl() {
		return getServerUrl() + "foodClass/getyiji";
	}
	
	public static String getFridgeDishUrl() {
		return getServerUrl() + "apps/fridge/getCookByFood";
	}

	/**获取提交图像识别任务的地址*/
    public static String getImageRecognitionUrl() {
		if(!ONLINE) {
			return "http://36.7.72.100:8899/image/addImage";
		}
		return "http://36.7.72.100:8888/image/addImage";
    }

	/**获取图像识别任务结果的地址*/
	public static String getImageRecognitionResultUrl() {
		if(!ONLINE) {
			return "http://36.7.72.100:8899/food/getInfo";
		}
		return "http://36.7.72.100:8888/food/getInfo";
	}

	/**获取图片识别结果纠错地址*/
	public static String getCameraCorrectionUrl() {
        return getServerUrl() + "arcSoft/alterFood";
	}

    public static String getFridgeFoodBySourceUrl() {
        return getServerUrl() + "awe/userfood/getuserfoodsbysrc";
    }

	//商城广告地址
	public static String goodsAdvertisementGetUrl() {
		if(ONLINE) {
			return "http://139.224.72.99:8080/fridge-bi/recommend/getMaterialResults";
		}
		return "http://139.224.72.99:8081/fridge-bi/recommend/getMaterialResults";
	}

	public static String mediaCategoryGetUrl() {
        return getServerUrl() + "awe/video/getVideoColumn";
    }

    public static String showListGetUrl() {
        return getServerUrl() + "awe/video/getVideoColumnList";
    }

	public static String videoListGetUrl() {
		return getServerUrl() + "awe/video/showsVideos";
	}

	public static String relatedVideoListGetUrl() {
		return getServerUrl() + "awe/video/randomVideo";
	}

	//---------------新版视频接口------------------
	public static String videoCategoryGetUrl() {
		return getServerUrl() + "video/v2/category.do";
	}

	public static String youkuShowListGetUrl() {
		return getServerUrl() + "video/v2/byCategory.do";
	}

	public static String youkuVideoListGetUrl() {
		return getServerUrl() + "video/v2/getVideo.do";
	}

	public static String youkuShowSearchUrl() {
		return getServerUrl() + "video/v2/showsByKeyword.do";
	}

	public static String youkuVideoDetailGetUrl() {
		return getServerUrl() + "video/v2/getVideos.do";
	}
	//---------------新版视频接口end---------------

	/**商城购物车接口*/
	public static String mallCartUrl() {
		return getMallUrl() + "/Vscreen/index.html";
	}

	public static String getDataReportUrl() {
		return "http://test.mideav.com/awe/appliance/data/report";
	}

	public static String getWeatherUrl() {
		return "http://wthrcdn.etouch.cn/weather_mini";
	}

	public static String getAddDrugUrl() {
		return getServerUrl() + "/medical/adduserdrug";
	}

	public static String upLoadDrugUrl() {
		return getServerUrl() + "/medical/openuserdrug";
	}

	public static String getDeleteDrugUrl() {
		return getServerUrl() + "/medical/deleteuserdrug";
	}

	public static String getUpdateDrugUrl() {
		return getServerUrl() + "/medical/updateuserdrug";
	}

	public static String getDrugListUrl() {
		return getServerUrl() + "/medical/getuserdrugs";
	}

	public static String getSongListUrl() {
		return getServerUrl() + "/awe/video/list";
	}

	public static String getFoodStatusUrl() {
		return getServerUrl() + "/fridgefood/foodtips";
	}

	public static String getWeatherUrl(String cityName){
		return "http://op.juhe.cn/onebox/weather/query?cityname=" + cityName + "&key=a6d1d8053bd328960709551f6215970c";
	}

	public static String getPushUrl() {
		return "http://i.mideav.com:8088/msg/pushtomobile";
	}

	public static String getHomeDataUrl() {
		return getServerUrl() + "bannerScreen/bannerListV2.do";
	}

	public static String getUpdateUrl() {
		return getServerUrl() + "midea/download";
	}

	public static String getAdminbindUrl() {
		return getServerUrl() + "mfridge/user/v2/adminbind";
	}

	public static String checkLoginNameUrl() {
		return getServerUrl() + "ids/apps/checkLoginName";
	}

	public static String getEatFoodTypeListUrl() {
		return getServerUrl() + "fridge/app/consumptionAction!getEatFoodTypeList.action";
	}
	public static String getEatFoodNutrientsListUrl() {
		return getServerUrl() + "fridge/app/consumptionAction!getEatFoodNutrientsList.action";
	}

	public static String getUpdateRfidUrl() {
		return getServerUrl() + "rfid/v2/rfidFoodAdd.do";
	}

	public static String getDishDetailsUrl() {
		return getServerUrl() + "dish/v2/getDetails.do";
	}

	public static String getUgcDetailsUrl() {
		return getServerUrl() + "apps/guc/details";
	}

	/** 人脸登录接口 */
	public static String faceLoginUrl() {
		if(ONLINE) {
			return "http://36.7.72.100:9002/" + "face/photoLogin";
		} else {
			return "http://36.7.72.100:9001/" + "face/photoLogin";
		}
	}

	/** 人脸绑定接口 */
	public static String faceBindUrl() {
		if(ONLINE) {
			return "http://36.7.72.100:9002/" + "face/uploadPhoto";
		} else {
			return "http://36.7.72.100:9001/" + "face/uploadPhoto";
		}
	}
	/**语音食材功效*/
	public static String getVoiceFoodMaterialUrl()
	{
		if(ONLINE) {
			return "http://test.mideav.com:8080/foodClass/getGongxiaoByName";
		}
		else {
			return "http://i.mideav.com:8088/foodClass/getGongxiaoByName";
		}
	}

	public static String getOtaUrl() {
		if(ONLINE) {
			return "http://ota.mideav.com:8082/ota/update.do";
		}
		return "http://ota.mideav.com:8081/ota/update.do";
	}

	public static String getUploadAppLog(){
		return getServerUrl() + "clientLog/v2/offLine.do";
	}
	//食材健康食谱获取用户的食材
	public static String getFRHUserFood()
	{
		if(ONLINE)
		{
			return "http://test.mideav.com:8080/awe/userfood/getOverdueFoodList";
		}
		return "http://i.mideav.com:8088/awe/userfood/getOverdueFoodList";
	}
	public static String getFRHhealthDetail()
	{
		if(ONLINE)
		{
			return "http://test.mideav.com:8080/foodClass/getDetailInfo";
		}
		return "http://i.mideav.com:8088/foodClass/getDetailInfo";
	}
	//食材健康食谱购买商品
	public static String getFRHfoodShop()
	{
		if(ONLINE)
		{
			return "https://vshop.mideav.com/#/app/search?subject=";
		}
		return "https://vshop2.mideav.com/#/app/search?subject=";
	}

	public static String cheakSignInfoUrl() {
		return getServer3Url() + "api/points/pointsSign/checkSignInfo.do";
	}

	public static String getSignUrl() {
		return getServer3Url() + "/api/points/pointsSign/signIn.do";
	}

	public static String getOverdueFoodListUrl() {
		return getServerUrl() + "awe/userfood/getOverdueFoodList";
	}
}
