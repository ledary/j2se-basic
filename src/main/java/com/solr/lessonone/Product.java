package com.solr.lessonone;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Properties;

/**
 * Created by wgp on 2018/7/9.
 */
public class Product {

    public Product(){}

    public Product(String id, String p_name, String p_catalog_name, Float p_price, Long p_number, String p_picture,
                   String p_description) {
        super();
        this.id = id;
        this.p_name = p_name;
        this.p_catalog_name = p_catalog_name;
        this.p_price = p_price;
        this.p_number = p_number;
        this.p_picture = p_picture;
        this.p_description = p_description;
    }

    /**
     * 商品编号
     */
    @Field
    private String id;

    /**
     * 商品名称
     */
    @Field
    private String p_name;

    /**
     * 商品分类名称
     */
    @Field
    private String p_catalog_name;

    /**
     * 价格
     */
    @Field
    private Float p_price;

    /**
     * 数量
     */
    @Field
    private Long p_number;

    /**
     * 图片名称
     */
    @Field
    private String p_picture;

    /**
     * 商品描述
     */
    @Field
    private String p_description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_catalog_name() {
        return p_catalog_name;
    }

    public void setP_catalog_name(String p_catalog_name) {
        this.p_catalog_name = p_catalog_name;
    }

    public Float getP_price() {
        return p_price;
    }

    public void setP_price(Float p_price) {
        this.p_price = p_price;
    }

    public Long getP_number() {
        return p_number;
    }

    public void setP_number(Long p_number) {
        this.p_number = p_number;
    }

    public String getP_picture() {
        return p_picture;
    }

    public void setP_picture(String p_picture) {
        this.p_picture = p_picture;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }
}
