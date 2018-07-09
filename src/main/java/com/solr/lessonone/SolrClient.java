package com.solr.lessonone;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wgp on 2018/7/9.
 */
@SuppressWarnings("deprecation")
public class SolrClient {

    private final static  String solrUrl = "http://localhost:8983/solr/solr_sample";




    public void solrQuery()throws Exception{
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000).withSocketTimeout(60000).build();
        Map<String,String> queryParaMap = new HashMap<>();
        queryParaMap.put("id","000001");
        queryParaMap.put("name","武刚鹏");
        queryParaMap.put("age","24");
        queryParaMap.put("addr","杭州");
        MapSolrParams queryParams = new MapSolrParams(queryParaMap);
        QueryResponse response = client.query(queryParams);
        SolrDocumentList documents = response.getResults();

        for(SolrDocument doc:documents){
            System.out.println("id:");
        }
    }


    @Test
public void solrAdd()throws Exception{
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000).withSocketTimeout(60000).build();
        SolrInputDocument doc = new SolrInputDocument();
        String str = UUID.randomUUID().toString();
    System.out.println(str);
    doc.addField("id",str);
    doc.addField("name","ledary");
    UpdateResponse response = client.add(doc);
    System.out.println(response.getElapsedTime());
    client.commit();
}

@Test
public void solrDelete()throws  Exception{
    HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
            .withConnectionTimeout(10000).withSocketTimeout(60000).build();
    client.deleteById("2999");
    client.commit();
    client.close();
}

@Test
    public void solrDeleteList()throws Exception{
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000).withSocketTimeout(60000).build();

        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("3000");
        client.deleteById(ids);
        client.commit();
        client.close();
    }

    @Test
    public void addBean()throws Exception{
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000).withSocketTimeout(60000).build();
        List<Product> list = new ArrayList<>();

        for(int i =1;i<3000;i++){
            Product product = new Product();
            product.setId("" + i);
            product.setP_name("测试商品啊");
            product.setP_catalog_name("测试商品分类名称");
            product.setP_price(399F);
            product.setP_number(30000L);
            product.setP_description("测试商品描述");
            product.setP_picture("测试商品图片.jpg");
            list.add(product);
        }

        //[4]添加对象
        UpdateResponse response = client.addBeans(list);
        //[5]提交操作
        client.commit();
        //[6]关闭资源
        client.close();

    }

    @Test
    public void addBean2()throws Exception{
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000).withSocketTimeout(60000).build();
        List<Product> list = new ArrayList<>();


            Product product = new Product();
            product.setId("1");
            product.setP_name("测试商品啊");
            product.setP_catalog_name("测试商品分类名称");
            product.setP_price(399F);
            product.setP_number(30000L);
            product.setP_description("测试商品描述");
            product.setP_picture("测试商品图片.jpg");


        //[4]添加对象
        UpdateResponse response = client.addBean(product);
        //[5]提交操作
        client.commit();
        //[6]关闭资源
        client.close();

    }

    @Test
    public void queryBean()throws Exception{
        //创建solrClient同时指定超时时间，不指定走默认配置
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();

        SolrQuery query = new SolrQuery("id:*");
//        query.addField("id");
//        query.addField("p_name");
//        query.addField("p_price");
//        query.addField("p_catalog_name");
//        query.addField("p_number");
//        query.addField("p_picture");
        query.setRows(100);
        QueryResponse response = client.query(query);
        List<Product> products = response.getBeans(Product.class);
        for(Product product:products){
            System.out.println("id:"+product.getId()
                    +"\tp_name:"+product.getP_name()
                    +"\tp_price:"+product.getP_price()
                    +"\tp_catalog_name:"+product.getP_catalog_name()
                    +"\tp_number:"+product.getP_number()
                    +"\tp_picture:"+product.getP_picture()
            );
        }
        client.close();
    }


    @Test
    public void deleteBean()throws  Exception{
        //创建solrClient同时指定超时时间，不指定走默认配置
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();

        client.deleteByQuery("id:*");
        client.commit();
        client.close();
    }

}
