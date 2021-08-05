package com.icampus.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class PropertyService {
    @Value("${REPOSITORY_PATH}")//value注解，获取配置文件中的值，但是自在本身的容器中查找资源，不会去父容器中查找
    public String REPOSITORY_PATH;
    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
