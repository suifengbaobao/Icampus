package com.icampus.common.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icampus.common.httpclient.HttpResult;

@Service
public class ApiService implements BeanFactoryAware {
    /**
     * HttpClient对象
     */
    @Autowired(required = false)
    // 没有这个也是可以的，公用的模块不能受到各模块的影响
    private RequestConfig requestConfig;

    private BeanFactory beanFactory;

    /**
     * 没有参数的Get方法
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url) throws ClientProtocolException, IOException {
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);// 相当于输入网址
        httpGet.setConfig(this.requestConfig);
        ;// 设置链接设置
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpClient().execute(httpGet);// 相当于按下回车键
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");// 相当于获取内容
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * 有参数的GET请求
     * 
     * @param url
     * @param params
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws URISyntaxException
     */
    public String doGetParam(String url, Map<String, String> params) throws ParseException, IOException,
            URISyntaxException {
        // 定义请求参数
        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.setParameter(param.getKey(), param.getValue());
        }
        return this.doGet(builder.build().toString());
    }

    /**
     * 有参数的POST请求
     * 
     * @param url
     * @param params
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> params) throws ParseException, IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        if (null != params) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            // 设置2个post参数，一个是scope、一个是q
            for (Map.Entry<String, String> param : params.entrySet()) {
                parameters.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpClient().execute(httpPost);
            // 判断返回状态是否为200
            HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));
            return httpResult;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 提交数据类型为json的post请求
     * @param url
     * @param json
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public HttpResult doPostJosn(String url, String json) throws ParseException, IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        if (null != json) {
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpClient().execute(httpPost);
            // 判断返回状态是否为200
            HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));
            return httpResult;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
    
    /**
     * 没有参数的POST请求
     * 
     * @param url
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public HttpResult doPost(String url) throws ParseException, IOException {
        return this.doPost(url, null);
    }

    /**
     * 该方法是Spring初始化时会调用该方法，注入一个BeanFactory对像
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取CloseableHttpClient 这样做可以解决一个问题——在单例对象中使用多例对象
     * 
     * @return
     */
    private CloseableHttpClient getHttpClient() {
        return beanFactory.getBean(CloseableHttpClient.class);
    }
}
