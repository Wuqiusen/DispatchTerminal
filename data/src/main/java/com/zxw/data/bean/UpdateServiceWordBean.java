package com.zxw.data.bean;

/**
 * author：CangJie on 2016/11/8 10:53
 * email：cangjie2016@gmail.com
 */
public class UpdateServiceWordBean {
    @Override
    public String toString() {
        return "UpdateServiceWordBean{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", isDele=" + isDele +
                ", keyCode='" + keyCode + '\'' +
                ", title='" + title + '\'' +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }

    /**
     * content : 各位乘客，尊老爱幼是中华民族的传统美德，请您给老，弱，病，残，孕及抱小孩的乘客让座，谢谢
     * id : 1
     * isDele : 0
     * keyCode : 1
     * title : 1
     * updateTimeKey : 201611011419
     */

    private String content;
    private int id;
    private int isDele;
    private String keyCode;
    private String title;
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

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateTimeKey() {
        return updateTimeKey;
    }

    public void setUpdateTimeKey(long updateTimeKey) {
        this.updateTimeKey = updateTimeKey;
    }
}
