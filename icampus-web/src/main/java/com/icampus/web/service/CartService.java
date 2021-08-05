package com.icampus.web.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icampus.common.httpclient.HttpResult;
import com.icampus.common.service.ApiService;
import com.icampus.web.bean.Cart;
import com.icampus.web.threadlocal.UserThreadLocal;


@Service
/**
 * 后期购物车记录查询信息
 * @author suife
 *
 */
public class CartService {
    @Autowired
    private ApiService apiService;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Value("${MANAGE_ICAMPUS_URL}")
    private String MANAGE_ICAMPUS_URL;
    public boolean save(Long itemId) {
        try {
            Cart record = new Cart();
            // 设置商品id
            record.setItemId(itemId);
            // 设置用户id
            record.setUserId(UserThreadLocal.get().getId());
            String url = MANAGE_ICAMPUS_URL + "/rest/api/cart/save";
            String json = MAPPER.writeValueAsString(record);
            // 发起httpclient Post请求
            HttpResult httpResult = this.apiService.doPostJosn(url, json);
            if(httpResult.getCode() == 201){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
