package com.wcy.blog.consumer;

import com.alibaba.fastjson.JSON;
import com.wcy.blog.dto.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.wcy.blog.constant.MQPrefixConst.EMAIL_QUEUE;

/**
 * 通知邮箱
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/28/17:22
 */
@Component
@RabbitListener(queues = EMAIL_QUEUE)
public class EmailConsumer {

    /**
     * 邮箱号
     */
    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitHandler
    public void process(byte[] data) {
        EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(emailDTO.getEmail());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getContent());
        javaMailSender.send(message);
    }

}