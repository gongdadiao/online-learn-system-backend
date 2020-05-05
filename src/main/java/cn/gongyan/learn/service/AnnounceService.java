/***********************************************************
 * @Description : 公告服务
 * @author      : 龚研
 * @date        : 2020-04-11 17:37
 * @qq          : 1085704190
 ***********************************************************/
package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Announce;
import cn.gongyan.learn.beans.qo.AnnounceQo;
import cn.gongyan.learn.repository.AnnounceRepository;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AnnounceService {
    @Autowired
    AnnounceRepository announceRepository;

    public Integer setAnnounce(AnnounceQo announceQo,String userId){
        Announce announce = new Announce();
        announce.setAnnId(IdUtil.simpleUUID());
        announce.setAnnCreatorid(userId);
        announce.setAnnTitle(announceQo.getName());
        announce.setAnnContent(announceQo.getContent());
        announce.setAnnType(announceQo.getType());
        announce.setAnnVariant(announceQo.getVariant());
        announce.setCreateTime(new Date());
        announce.setAnnClassId(announceQo.getClassId());
        Announce save = announceRepository.save(announce);
        if(save==null)
            return 0;
        return 1;
    }
    public List<Announce> getAllAnnounces(String classId){
        Announce announce = new Announce();
        announce.setAnnClassId(classId);
        List<Announce> announceList = announceRepository.findAll(Example.of(announce));
        return announceList;
    }
}
