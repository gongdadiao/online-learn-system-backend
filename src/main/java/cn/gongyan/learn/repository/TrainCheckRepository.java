package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.TrainningCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainCheckRepository extends JpaRepository<TrainningCheck, Integer> {
}
