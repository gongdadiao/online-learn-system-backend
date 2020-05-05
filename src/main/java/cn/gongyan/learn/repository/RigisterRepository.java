package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.Rigister;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 龚研
 * @desc RigisterRepository
 * @date 2020/4/12
 * @qq 1085704190
 **/
public interface RigisterRepository extends JpaRepository<Rigister, String> {
}