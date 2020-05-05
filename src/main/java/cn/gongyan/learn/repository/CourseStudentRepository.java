package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 龚研
 * @desc CourseStudentRepository
 * @date 2020/4/14
 * @qq 1085704190
 **/
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Integer> {
}
