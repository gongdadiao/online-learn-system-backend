package cn.gongyan.learn.beans.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "course_student")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class CourseStudent {
    @Id
    @Column(name = "cs_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer csId;

    @Column(name = "cs_course_id")
    private String csCourseId;

    @Column(name = "cs_student_id")
    private String csStudentId;

    @Column(name = "create_time")
    private Date createTime;


}