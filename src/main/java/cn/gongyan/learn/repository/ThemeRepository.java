package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 龚研
 * @desc ThemeRepository
 * @date 2020/4/12
 * @qq 1085704190
 **/
public interface ThemeRepository extends JpaRepository<Theme, Integer> {
}