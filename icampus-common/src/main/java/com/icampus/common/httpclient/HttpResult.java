package com.icampus.common.httpclient;

/**
 * 封装HttpClient返回结果
 * 
 * @author suife
 *
 */
public class HttpResult {
    private Integer code;// 状态码

    private String body;// 响应体

    public HttpResult() {
    }

    public HttpResult(Integer code, String body) {
        this.code = code;
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
