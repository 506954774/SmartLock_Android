package com.qdong.communal.library.widget.TimePicker.entity;

import java.io.Serializable;
import java.util.List;

/**
 * VillageResponse
 * Created By:WuJH
 * Des:
 * on 2018/12/8 10:05
 */
public class VillageResponse implements Serializable{


    /**
     * code : 130224001
     * name : 奔城街道
     * nameEn : 奔城街道
     * children : []
     */

    private String code;
    private String name;
    private String nameEn;
    private List<?> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }
}
