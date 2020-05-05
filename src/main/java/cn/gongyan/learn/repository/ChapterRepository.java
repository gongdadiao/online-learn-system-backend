package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 龚研
 * @desc ChapterRepository
 * @date 2020/4/12
 * @qq 1085704190
 **/
public interface ChapterRepository  extends JpaRepository<Chapter, String> {
}
