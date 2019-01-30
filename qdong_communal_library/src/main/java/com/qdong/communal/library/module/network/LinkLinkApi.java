package com.qdong.communal.library.module.network;

import com.qdong.communal.library.util.Constants;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * QDongApi
 * 趣动api接口,Header没有用注解写,header添加的逻辑在RetrofitAPIManager里
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2016/6/28  11:19
 * Copyright : 2014-2016 深圳趣动智能科技有限公司-版权所有
 */
public interface LinkLinkApi {

    /********************************************************************************
     * 设备端模块
     *
     */
    //获取clientKey
    @GET(Constants.TOKEN_SERVICE + "/client/register")
    Observable<LinkLinkNetInfo> getClientKey(@Query("appId") String appId);


    /********************************************************************************
     * 公用模块
     */
    //查询版本
    @GET(Constants.SERVER_PREFIX + "/app/system/findLatestVersion/{versionName}.do")
    Observable<LinkLinkNetInfo> findLatestVersion(@Path("versionName") String versionName);


    //tfs多文件上传
    @Multipart
    @POST(Constants.FILE_SERVICE + "/file/upload")
    Observable<LinkLinkNetInfo> uploadMultipleFile1(@PartMap Map<String, RequestBody> params);


    //tfs多文件上传
    @Multipart
    @POST(Constants.FILE_SERVICE + "/file/upload/files")
    //Observable<LinkLinkNetInfo> uploadMultipleFile(@Part MultipartBody.Part file);
    Observable<LinkLinkNetInfo> uploadMultipleFile(@PartMap Map<String, RequestBody> params);

    //tfs单文件上传
    @Multipart
    @POST(Constants.FILE_SERVICE + "/file/upload")
    Observable<LinkLinkNetInfo> uploadSingleFile(@Part MultipartBody.Part file);

    /**
     * 上传一张图片
     *
     * @param description
     * @param imgs
     * @return
     */
    @Multipart
    @POST(Constants.FILE_SERVICE + "/file/upload")
    Call<String> uploadImage(@Part("fileName") String description,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs);


    /*******************************************************************************************************
     * 用户模块
     */
    //获取短信验证码
    @GET(Constants.SMS_SERVICE + "/sms/register/send")
    Observable<LinkLinkNetInfo> registSendSms(@Query("clientKey") String clientKey, @Query("tel") String tel);

    //修改密码获取短信验证码
    @GET(Constants.SMS_SERVICE + "/sms/updatePw/send")
    Observable<LinkLinkNetInfo> updatePwSendSms(
            @Query("clientKey") String clientKey,
            @Query("accessToken") String accessToken,
            @Query("tel") String tel);

    //验证短信验证码
    @GET(Constants.SMS_SERVICE + "/sms/verify")
    Observable<LinkLinkNetInfo> sendVerify(@Query("clientKey") String clientKey
            , @Query("tel") String tel
            , @Query("code") String code
    );

    //用户告警声音设置
    @GET(Constants.USER_INFO_SERVICE + "/user/alarm/sound")
    Observable<LinkLinkNetInfo> getAlarmSound(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
    );

    //获取用户余额
    @GET(Constants.USER_INFO_SERVICE + "/user/balance")
    Observable<LinkLinkNetInfo> getBalance(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
    );

    //用户告警灵敏度设置
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/send/sensitive")
    Observable<LinkLinkNetInfo> setAlarmSensitivity(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
            , @Query("sensitive") String sensitive
    );


    //更新设备告警设置
    @GET(Constants.USER_INFO_SERVICE + "/user/update/alarm/sound")
    Observable<LinkLinkNetInfo> updateAlarmSound(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
    );

    //获取用户信息
    @GET(Constants.USER_INFO_SERVICE + "/user/info")
    Observable<LinkLinkNetInfo> getUserInfo(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
    );

    //用户注册
    @POST(Constants.USER_AUTHC_SERVICE + "/user/register")
    Observable<LinkLinkNetInfo> userRegister(@Query("clientKey") String clientKey,
                                             @Body Map<String, Object> map);

    //用户修改密码
    @POST(Constants.USER_AUTHC_SERVICE + "/user/password/update")
    Observable<LinkLinkNetInfo> userUpdatePw(@Query("clientKey") String clientKey,
                                             @Query("accessToken") String accessToken,
                                             @Body Map<String, Object> map);

    //用户登陆
    @POST(Constants.USER_AUTHC_SERVICE + "/user/login")
    Observable<LinkLinkNetInfo> userLogin(@Query("clientKey") String clientKey,
                                          @Body Map<String, Object> map);

    //更新用户信息
    @POST(Constants.USER_INFO_SERVICE + "/user/update/info")
    Observable<LinkLinkNetInfo> updateUserInfo(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken,
                                               @Body Map<String, Object> map);

    //用户登出
    @GET(Constants.USER_AUTHC_SERVICE + "/user/logout")
    Observable<LinkLinkNetInfo> userLogout(
            @Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("platform") String platform

    );

    //更新pushKey
    @GET(Constants.PUSH_SERVICE + "/jiguang/register")
    Observable<LinkLinkNetInfo> jPushRegister(
            @Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("registrationId") String registrationId
            //1是安卓, 2是IOS ,3是pc
            , @Query("platform") String platform
    );


    //注册
    @POST(Constants.SERVER_PREFIX + "/app/user/register.do")
    Observable<LinkLinkNetInfo> register(@Body Map<String, Object> map);


    //登陆,用Call是为了获取响应里的cookie,拿到sessionId
    @POST(Constants.USER_AUTHC_SERVICE + "/user/login")
    Call<LinkLinkNetInfo> login(@Query("clientKey") String clientKey,
                                @Body Map<String, String> map);//为了获取头里面的sessionId


    /*********************************************************************************
     * device模块
     */
    //获取绑定的车辆列表
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/acc/bind/list")
    Observable<LinkLinkNetInfo> getDeviceList(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
    );

    //切换车辆
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/ex")
    Observable<LinkLinkNetInfo> exchangeDevices(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("bylId") String bylId
    );

    //解设防
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/send/defense")
    Observable<LinkLinkNetInfo> bikeDefence(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //清空轨迹列表
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/clear/stat")
    Observable<LinkLinkNetInfo> clearStat(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("bylId") String bylId
    );

    //电子围栏信息
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/fence/info")
    Observable<LinkLinkNetInfo> queryFencingInfo(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //电子围栏开启状态
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/fence/status")
    Observable<LinkLinkNetInfo> queryFencingStatus(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //电子围栏更新
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/fence/update")
    Observable<LinkLinkNetInfo> fencingUpdate(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //一键启动
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/send/startup")
    Observable<LinkLinkNetInfo> pushBtnStart(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //喇叭
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/send/search")
    Observable<LinkLinkNetInfo> bikeHorn(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //获取车辆信息
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/basic/info")
    Observable<LinkLinkNetInfo> getBikeInfo(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken

            , @Query("bylId") String bylId
    );

    //获取车辆信息
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/gps/list")
    Observable<LinkLinkNetInfo> getBikeGps(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("bylId") String bylId
            , @Query("date") String date
    );

    //修改车辆名字
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/update/name")
    Observable<LinkLinkNetInfo> updateBikeName(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("bylId") String bylId
            , @Query("bylName") String bylName
    );

    //查询指令结果
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/cmd/status")
    Observable<LinkLinkNetInfo> queryCmdStatus(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("cmdId") String cmdId
            , @Query("imei") String imei
    );

    //车辆绑定
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/acc/bind")
    Observable<LinkLinkNetInfo> bind(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("bylId") String bylId
    );

    //用户车辆解绑
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/acc/unbind")
    Observable<LinkLinkNetInfo> unbind(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("bylId") String bylId
    );

    //查询是否激活
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/act/status")
    Observable<LinkLinkNetInfo> queryActivationStatus(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //激活
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/act")
    Observable<LinkLinkNetInfo> active(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );


    //车辆轨迹列表
    @POST(Constants.SONGI_DEVICE_SERVICE + "/device/track/list")
    Observable<LinkLinkNetInfo> getBikeTraceList(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken,
                                                 @Body Map<String, Object> map);

    //车辆轨迹详情
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/track/detail")
    Observable<LinkLinkNetInfo> getBikeTraceDetails(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("trackId") String trackId
    );

    //车辆最后位置
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/latest/gps")
    Observable<LinkLinkNetInfo> getBikeLatestGps(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //获取设备告警属性
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/status")
    Observable<LinkLinkNetInfo> getDeviceStatus(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //自动解设防
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/update/autoDefense")
    Observable<LinkLinkNetInfo> updateAutoDefense(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //是否告警
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/update/isAlarm")
    Observable<LinkLinkNetInfo> updateIsAlarm(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );

    //超速报警
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/speedAlarm/update")
    Observable<LinkLinkNetInfo> updateSpeedAlarm(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("imei") String imei
    );


    //告警信息列表
    @POST(Constants.SONGI_DEVICE_SERVICE + "/device/alarm/list")
    Observable<LinkLinkNetInfo> getAlarmList(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken,
                                             @Body Map<String, Object> map);

    //系统消息列表
    @GET(Constants.MESSAGE_INFO_SERVICE + "/msg/user/message/list")
    Observable<LinkLinkNetInfo> getSystemMessageList(@Query("pageIndex") String pageIndex,
                                                     @Query("pageSize") String pageSize);


    //用户检测
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/user/check")
    Observable<LinkLinkNetInfo> inspect(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("bylId") String bylId
    );

    //查询用户最近的一次检测结果
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/user/check/log")
    Observable<LinkLinkNetInfo> getLatestInspection(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
            , @Query("bylId") String bylId
    );

    //投诉提交
    @POST(Constants.CONSULT_INFO_SERVICE + "/consult/log")
    Observable<LinkLinkNetInfo> consultCommit(@Body Map<String, Object> map);

    //投诉列表
    @GET(Constants.CONSULT_INFO_SERVICE + "/consult/list")
    Observable<LinkLinkNetInfo> consultList(@Query("kind") String kind
            , @Query("pageIndex") int pageIndex
            , @Query("pageSize") int pageSize
    );

    //投诉详情
    @GET(Constants.CONSULT_INFO_SERVICE + "/consult/info")
    Observable<LinkLinkNetInfo> consultInfo(@Query("consultId") String consultId

    );

    //开始码表
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/real/stat/start")
    Observable<LinkLinkNetInfo> startGpsLog(@Query("clientKey") String clientKey,
                                            @Query("bylId") String bylId);

    //结束码表
    @POST(Constants.SONGI_DEVICE_SERVICE + "/device/real/stat/end")
    Observable<LinkLinkNetInfo> endGpsLog(@Body Map<String, Object> map);

    //录入实时gps信息
    @POST(Constants.SONGI_DEVICE_SERVICE + "/device/real/gps/log")
    Observable<LinkLinkNetInfo> inputGpsLog(@Body Map<String, Object> map);



    /******************************************************************************
     * 字典服务
     */
    //获取全部的投诉类型
    @GET(Constants.DICT_SERVICE + "/consult/query-all")
    Observable<LinkLinkNetInfo> queryAllConsult(@Query("kind") String kind);

    //获取全部的投诉类型
    @GET(Constants.DICT_SERVICE + "/consult/query-all")
    Call<LinkLinkNetInfo> queryAllConsult2(@Query("kind") String kind);

    //获取全部的区域
    @GET(Constants.DICT_SERVICE + "/area/query-all")
    Observable<LinkLinkNetInfo> queryAllArea(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
    );

    //获取子区域
    @GET(Constants.DICT_SERVICE + "/area/query-children")
    Observable<LinkLinkNetInfo> queryChildAreaByCode(@Query("code") String code);


    //获取全部的职业集合,二维
    @GET(Constants.DICT_SERVICE + "/vocation/query-all")
    Observable<LinkLinkNetInfo> queryAllVocation(@Query("clientKey") String clientKey
            , @Query("accessToken") String accessToken
    );

    /*********************************************************************************
     * 门店业务
     */
    //获取保修卡详情
    @GET(Constants.STORE_INFO_SERVICE + "/repair/card/info")
    Observable<LinkLinkNetInfo> queryCardInfo(@Query("devImei") String devImei
    );

    //获取门店列表
    @POST(Constants.STORE_INFO_SERVICE + "/store/app/list")
    Observable<LinkLinkNetInfo> queryStoreList(@Query("clientKey") String clientKey,
                                               @Query("accessToken") String accessToken,
                                               @Body Map<String, Object> map);

    //获取门店地图列表
    @POST(Constants.STORE_INFO_SERVICE + "/store/app/map/list")
    Observable<LinkLinkNetInfo> queryStoreMapList(@Query("clientKey") String clientKey,
                                                  @Query("accessToken") String accessToken,
                                                  @Body Map<String, Object> map);

    /******************************************************************************
     * 保险服务
     */
    //获取全部的保险类型
    @GET(Constants.POLICY_INFO_SERVICE + "/policy/cate/list")
    Observable<LinkLinkNetInfo> queryAllPolicyType(@Query("clientKey") String clientKey,
                                                   @Query("accessToken") String accessToken);

    //获取尚未购保的车辆集合
    @GET(Constants.POLICY_INFO_SERVICE + "/policy/byl/list")
    Observable<LinkLinkNetInfo> queryBylList(@Query("clientKey") String clientKey,
                                             @Query("accessToken") String accessToken);

    //获取以前提交过的资料
    @GET(Constants.POLICY_INFO_SERVICE + "/policy/holder/info-by-accCode")
    Observable<LinkLinkNetInfo> queryInfoHolder(@Query("clientKey") String clientKey,
                                                @Query("accessToken") String accessToken);

    //提交保险资料
    @POST(Constants.POLICY_INFO_SERVICE + "/policy/create")
    Observable<LinkLinkNetInfo> policyCreate(@Query("clientKey") String clientKey,
                                             @Query("accessToken") String accessToken,
                                             @Body Map<String, Object> map);

    //获取保险全部信息
    @GET(Constants.POLICY_INFO_SERVICE + "/policy/info")
    Observable<LinkLinkNetInfo> queryPolicyInfo(@Query("clientKey") String clientKey,
                                                @Query("accessToken") String accessToken,
                                                @Query("plcId") String plcId);

    //获取购保信息
    @GET(Constants.POLICY_INFO_SERVICE + "/policy/list")
    Observable<LinkLinkNetInfo> queryPolicyRecord(@Query("clientKey") String clientKey,
                                                  @Query("accessToken") String accessToken,
                                                  @Query("pageIndex") String pageIndex,
                                                  @Query("pageSize") String pageSize);

    //更新投保资料
    @POST(Constants.POLICY_INFO_SERVICE + "/policy/update")
    Observable<LinkLinkNetInfo> updatePolicyInfo(@Query("clientKey") String clientKey,
                                                 @Body Map<String, Object> map);

    /*********************************************************************************
     * 理赔模块
     */
    //获取理赔基本信息
    @GET(Constants.POLICY_INFO_SERVICE+ "/claim/basic/info")
    Observable<LinkLinkNetInfo> queryCompensateBasicInfo(@Query("clientKey") String clientKey,
                                                @Query("plcId") String plcId);

    //获取理赔全部信息
    @GET(Constants.POLICY_INFO_SERVICE+ "/claim/detail")
    Observable<LinkLinkNetInfo> queryCompensateInfoDetail(@Query("clientKey") String clientKey,
                                                @Query("plcId") String plcId);

    //理赔报失
    @GET(Constants.POLICY_INFO_SERVICE+ "/claim/create")
    Observable<LinkLinkNetInfo> queryCompensateCreate(@Query("clientKey") String clientKey,
                                                @Query("plcId") String plcId);

    //取消理赔
    @GET(Constants.POLICY_INFO_SERVICE+ "/claim/cancel")
    Observable<LinkLinkNetInfo> queryCompensateCancel(@Query("clientKey") String clientKey,
                                                @Query("clmId") String clmId);

    //上传报警回执
    @POST(Constants.POLICY_INFO_SERVICE+ "/claim/alarm/create")
    Observable<LinkLinkNetInfo> queryCompensateAlarmCreate(@Query("clientKey") String clientKey,
                                                           @Body Map<String, Object> map);

    //追车拍照取证
    @POST(Constants.POLICY_INFO_SERVICE+ "/claim/location/create")
    Observable<LinkLinkNetInfo> queryCompensateLocationCreate(@Query("clientKey") String clientKey,
                                                              @Body Map<String, Object> map);

    //追车拍照取证
    @POST(Constants.POLICY_INFO_SERVICE+ "/claim/distance/check")
    Observable<LinkLinkNetInfo> queryDistanceCheck(@Query("imei") String imei,
                                                   @Query("lat") double lat,
                                                   @Query("lng") double lng);


    /*********************************************************************************
     * 支付模块
     */
    //获取支付数额列表
    @GET(Constants.PAY_INFO_SERVICE + "/rcg/fee/list")
    Observable<LinkLinkNetInfo> getFeeList(@Query("clientKey") String clientKey,
                                           @Query("accessToken") String accessToken);

    //微信支付
    @GET(Constants.PAY_INFO_SERVICE + "/wx/pay")
    Observable<LinkLinkNetInfo> wxPay(@Query("clientKey") String clientKey,
                                      @Query("accessToken") String accessToken,
                                      @Query("feeId") String feeId);

    //阿里支付
    @GET(Constants.PAY_INFO_SERVICE + "/alipay/pay")
    Observable<LinkLinkNetInfo> aliPay(@Query("clientKey") String clientKey,
                                       @Query("accessToken") String accessToken,
                                       @Query("feeId") String feeId);

    //获取流水列表
    @GET(Constants.PAY_INFO_SERVICE + "/bill/list")
    Observable<LinkLinkNetInfo> billList(@Query("clientKey") String clientKey,
                                         @Query("accessToken") String accessToken,
                                         @Query("pageIndex") int pageIndex,
                                         @Query("pageSize") int pageSize);

    //获取充值列表
    @GET(Constants.PAY_INFO_SERVICE + "/rcg/list")
    Observable<LinkLinkNetInfo> rcgList(@Query("clientKey") String clientKey,
                                        @Query("accessToken") String accessToken,
                                        @Query("pageIndex") int pageIndex,
                                        @Query("pageSize") int pageSize
    );

    //微信支付盗抢险
    @GET(Constants.PAY_INFO_SERVICE + "/wx/pay/theft")
    Observable<LinkLinkNetInfo> wxPayForPolicy(@Query("clientKey") String clientKey,
                                               @Query("accessToken") String accessToken,
                                               @Query("plcId") String plcId);

    //阿里支付盗抢险
    @GET(Constants.PAY_INFO_SERVICE + "/alipay/pay/theft")
    Observable<LinkLinkNetInfo> aliPayForPolicy(@Query("clientKey") String clientKey,
                                                @Query("accessToken") String accessToken,
                                                @Query("plcId") String plcId);

    /*********************************************************************************
     * 项目模块
     */
    //新增项目(建筑公司发标的前一步)
    @POST(Constants.SERVER_PREFIX + "/app/prj/create.do")
    Observable<LinkLinkNetInfo> create(@Body Map<String, Object> map);


    //更新工人头像
    @GET(Constants.SERVER_PREFIX + "/app/user/updateUsrPictureUri/{usrPictureUri}.do")
    Observable<LinkLinkNetInfo> updateUsrPictureUri(@Path("usrPictureUri") String usrPictureUri);


    /***********************************************************************************************************
     * 支付
     */
    //v101 查询余额
    @GET(Constants.SERVER_PREFIX + "/app/user/getAccountBalance.do")
    Observable<LinkLinkNetInfo> getAccountBalance();


    //v101 获取
    @POST(Constants.SERVER_PREFIX + "/app/fave/myFavoriteWork.do")
    Observable<LinkLinkNetInfo> myFavoriteWork(@Body Map<String, Object> map);


    @POST(Constants.INFORMATION_INFO_SERVICE + "/info/app/list")
    Observable<LinkLinkNetInfo> infoList(@Body Object map);

    /**
     * 1：松吉门店APP，2：松吉智能APP（用户）
     *
     * @param scoCode
     * @return
     */
    @GET(Constants.DICT_SERVICE + "/info/query/categorys")
    Observable<LinkLinkNetInfo> infoTitles(@Query("scoCode") int scoCode);


    /**
     * 查询用户绑定设备(车辆)信息
     *
     * @return
     */
    @GET(Constants.DEVICE_INFO_SERVICE + "/device/acc/bind/list")
    Observable<LinkLinkNetInfo> getDeviceList();

    /**
     * 遥控器配对
     *
     * @return
     */

    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/send/match")
    Observable<LinkLinkNetInfo> deviceMatch(@Query("imei") String imei);

    /**
     * 查询指令状态(只有等于4时指令操作成功)
     *
     * @param cmdId
     * @return
     */
    @GET(Constants.SONGI_DEVICE_SERVICE + "/device/match/result")
    Observable<LinkLinkNetInfo> matchResult(@Query("cmdId") long cmdId);

    /**
     * 运营系统根据门店ID查询门店信息
     *
     * @param storeID
     * @return
     */
    @GET(Constants.STORE_INFO_SERVICE + "/store/query/storeid")
    Observable<LinkLinkNetInfo> storeInfo(@Query("storeID") String storeID);

    /**
     * 查询产品信息 用于用户APP
     *
     * @param storeID
     * @return
     */
    @GET(Constants.STORE_INFO_SERVICE + "/product/user/query")
    Observable<LinkLinkNetInfo> productInfos(@Query("storeID") String storeID, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    /**
     * 产品详情
     *
     * @param productID
     * @return
     */
    @GET(Constants.STORE_INFO_SERVICE + "/product/query/productid")
    Observable<LinkLinkNetInfo> productDetail(@Query("productID") String productID);

    /**
     * 根据imei查询信息
     * @param devImei
     * @return
     */
    @GET(Constants.FACTORY_INFO_SERVICE + "/intro/app/query/imei")
    Observable<LinkLinkNetInfo> queryDoc(@Query("devImei") String devImei);

}
