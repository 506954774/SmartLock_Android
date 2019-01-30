package com.qdong.communal.library.module.network;

import android.text.TextUtils;

import com.google.gson.JsonElement;

import java.io.Serializable;

/**
 * QDongNetInfo
 * 接口返回数据的总模型
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2016/6/28  11:22
 * Copyright : 趣动智能科技有限公司-版权所有
 **/
public class LinkLinkNetInfo implements Serializable {

    private static final long serialVersionUID = 19840902L;
    private static final String SUCESS_CODE = "000000";
    /**
     * success : false
     * code : 010035
     * message : 请登录！
     * data : null
     */

    private String code;
    private String message;
    private JsonElement data;


    /**客户端定义的属性actionType,这个属性不是服务器定的.是为了给同一个接口赋予不同的action.比如同一个列表请求,
     * 我们可以定义不同的动作,如刷新,加载,过滤刷新等**/
    private int actionType;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public boolean isSuccess() {
        if(TextUtils.isEmpty(code)){
            return true;
        }
        return SUCESS_CODE.equals(code);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QDongNetInfo{" +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", actionType=" + actionType +
                '}';
    }
}
