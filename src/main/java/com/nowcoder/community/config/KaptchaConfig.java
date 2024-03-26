package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

//生成验证码
@Configuration
public class KaptchaConfig {

    //接口类型：producer
    @Bean
    public Producer kaptchaProducer() {


        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");//font字号
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        //从以下的字符串中选择字符生成验证码
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");
        //选择4个
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //生成的图片加一些干扰噪声，仿机器人暴力破解
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");

        //实现类
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        //通过config对象配置
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

}
