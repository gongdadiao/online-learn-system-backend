package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Chapter;
import cn.gongyan.learn.beans.qo.ChapterCreateQo;
import cn.gongyan.learn.repository.ChapterRepository;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author 龚研
 * @desc ChapterService
 * @date 2020/4/13
 * @qq 1085704190
 **/
@Service
@Transactional
public class ChapterService {
    @Autowired
    ChapterRepository chapterRepository;

    public Chapter createChapter(ChapterCreateQo chapterCreateQo,String userId){
        try {
            Chapter chapter = new Chapter();
            chapter.setChId(IdUtil.simpleUUID());
            chapter.setChCreatorId(userId);
            chapter.setChThemeId(chapterCreateQo.getThemeId());
            chapter.setChTitle(chapterCreateQo.getTitle());
            chapter.setChResId(chapterCreateQo.getResId());
            chapter.setChSeq(chapterCreateQo.getSeq());
            chapter.setChType(chapterCreateQo.getType());
            chapter.setCreateTime(new Date());
            chapterRepository.save(chapter);
            return chapter;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
