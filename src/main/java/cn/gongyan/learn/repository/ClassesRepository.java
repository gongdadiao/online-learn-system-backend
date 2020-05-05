package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 龚研
 * @desc ClassesRepository
 * @date 2020/4/12
 * @qq 1085704190
 **/
public interface ClassesRepository  extends JpaRepository<Classes, String> {
}
