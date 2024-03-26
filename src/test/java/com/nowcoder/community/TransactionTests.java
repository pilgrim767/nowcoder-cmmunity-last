package com.nowcoder.community;

import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TransactionTests {

    @Autowired
    private AlphaService alphaService;

    @Test
    public void testSave1() {
        Object obj = alphaService.save1();
        System.out.println(obj);
        // 一定会报错，因为abc字符串不能转换成整数
        // 不过可以看出当程序执行过程中出错时，业务回滚，保证了有效性
    }

    @Test
    public void testSave2() {
        Object obj = alphaService.save2();
        System.out.println(obj);
        // 一定会报错，因为abc字符串不能转换成整数
        // 不过可以看出当程序执行过程中出错时，业务回滚，保证了有效性
    }

}
