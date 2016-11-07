package com.zxw.data.bean;

/**
 * author：CangJie on 2016/11/7 16:18
 * email：cangjie2016@gmail.com
 */
public class UpdateLineBean {

    /**
     * companyId : 2
     * companyName : 二公司
     * id : 2
     * isDele : 0
     * lineCode : 303
     * linePartMark : 1
     * updateTimeKey : 201610291002
     * version : 0
     */

    private int companyId;
    private String companyName;
    private int id;
    private String isDele;
    private String lineCode;
    private int linePartMark;
    private long updateTimeKey;
    private int version;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsDele() {
        return isDele;
    }

    public void setIsDele(String isDele) {
        this.isDele = isDele;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public int getLinePartMark() {
        return linePartMark;
    }

    public void setLinePartMark(int linePartMark) {
        this.linePartMark = linePartMark;
    }

    public long getUpdateTimeKey() {
        return updateTimeKey;
    }

    public void setUpdateTimeKey(long updateTimeKey) {
        this.updateTimeKey = updateTimeKey;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "UpdateLineBean{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", id=" + id +
                ", isDele='" + isDele + '\'' +
                ", lineCode='" + lineCode + '\'' +
                ", linePartMark=" + linePartMark +
                ", updateTimeKey=" + updateTimeKey +
                ", version=" + version +
                '}';
    }
}
