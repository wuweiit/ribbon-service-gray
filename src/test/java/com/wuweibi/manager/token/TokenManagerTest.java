package com.wuweibi.manager.token;

import com.wuweibi.ribbon.properties.ServiceGrayProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TokenManagerTest {

    @Resource
    private ServiceGrayProperties serviceGrayProperties;


}

