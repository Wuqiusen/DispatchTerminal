package com.zxw.dispatch_driver.db;

/**
 * author：CangJie on 2016/11/1 19:24
 * email：cangjie2016@gmail.com
 */
public class ServiceWordBean {
    private Long id;			//自增id
    private String title;		//标题
    private String keyCode;		//唯一键值
    private String content;		//内容
    private Integer isDele;		//是否删除,格式：0否、1是
    private Long updateTimeKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsDele() {
        return isDele;
    }

    public void setIsDele(Integer isDele) {
        this.isDele = isDele;
    }

    public Long getUpdateTimeKey() {
        return updateTimeKey;
    }

    public void setUpdateTimeKey(Long updateTimeKey) {
        this.updateTimeKey = updateTimeKey;
    }

    @Override
    public String toString() {
        return "ServiceWordBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", keyCode='" + keyCode + '\'' +
                ", content='" + content + '\'' +
                ", isDele=" + isDele +
                ", updateTimeKey=" + updateTimeKey +
                '}';
    }
}
