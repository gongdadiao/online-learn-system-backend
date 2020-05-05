package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.QuestionCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseQuestionRepository extends JpaRepository<QuestionCourse, Integer> {
}
