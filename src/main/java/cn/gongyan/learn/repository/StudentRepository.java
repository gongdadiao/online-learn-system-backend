package cn.gongyan.learn.repository;

import cn.gongyan.learn.beans.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 龚研
 * @desc StudentRepository
 * @date 2020/4/12
 * @qq 1085704190
 **/
public interface StudentRepository extends JpaRepository<Student, String> {
}
