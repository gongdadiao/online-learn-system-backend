package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.TrainningQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainQuestionRepository extends JpaRepository<TrainningQuestion, Integer> {
}
