package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Mail;
import cn.gongyan.learn.beans.entity.User;
import cn.gongyan.learn.beans.qo.MailQo;
import cn.gongyan.learn.beans.vo.MailVo;
import cn.gongyan.learn.repository.MailRepository;
import cn.gongyan.learn.repository.UserRepository;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 龚研
 * @desc MailService
 * @date 2020/4/21
 * @qq 1085704190
 **/
@Service
@Transactional
public class MailService {
    @Autowired
    MailRepository mailRepository;
    @Autowired
    UserRepository userRepository;

    public Integer sendOneMessage(MailQo mailQo){
        Mail mail = new Mail();
        mail.setMId(IdUtil.simpleUUID());
        mail.setCreateTime(new Date());
        mail.setMContent(mailQo.getContent());
        mail.setMReceiverId(mailQo.getReceivorId());
        mail.setMSenderId(mailQo.getSendorId());
        mail.setMTitle(mailQo.getTitle());
        mail.setMClassId(mailQo.getClassId());
        Mail save = mailRepository.save(mail);
        return (save==null)?0:1;
    }

    public List<MailVo> getMessages(String receivorId){
        Mail mail = new Mail();
        mail.setMReceiverId(receivorId);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<Mail> mailList = mailRepository.findAll(Example.of(mail),sort);
        ArrayList<MailVo> mailVos = new ArrayList<>();
        for (Mail item : mailList) {
            MailVo vo = new MailVo();
            BeanUtils.copyProperties(item,vo);
            String senderId = vo.getMSenderId();
            User user = userRepository.findById(senderId).orElse(null);
            vo.setAvartar(user.getUserAvatar());
            vo.setName(user.getUserNickname());
            mailVos.add(vo);
        }
        return mailVos;
    }
}
