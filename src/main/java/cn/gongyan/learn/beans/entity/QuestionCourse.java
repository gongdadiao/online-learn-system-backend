package cn.gongyan.learn.beans.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "question_course")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class QuestionCourse {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "course_id")
    private String courseId;


}