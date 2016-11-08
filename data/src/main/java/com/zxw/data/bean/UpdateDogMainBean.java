package com.zxw.data.bean;

/**
 * author：CangJie on 2016/11/8 10:55
 * email：cangjie2016@gmail.com
 */
public class UpdateDogMainBean {
    @Override
    public String toString() {
        return "UpdateVoiceCompoundBean{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", isDele=" + isDele +
                ", type=" + type +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }

    /**
     * content : #currentStation#到了，请您从后门下车，开门请当心，下车请走好。
     * id : 1
     * isDele : 0
     * type : 1
     * updateTimeKey : 201611011419
     */

    private String content;
    private int id;
    private int isDele;
    private int type;
    private long updateTimeKey;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDele() {
        return isDele;
    }

    public void setIsDele(int isDele) {
        this.isDele = isDele;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateTimeKey() {
        return updateTimeKey;
    }

    public void setUpdateTimeKey(long updateTimeKey) {
        this.updateTimeKey = updateTimeKey;
    }
}
