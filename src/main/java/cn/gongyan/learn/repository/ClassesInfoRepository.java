package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.ClassesInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassesInfoRepository extends JpaRepository<ClassesInfo, Integer> {
}
