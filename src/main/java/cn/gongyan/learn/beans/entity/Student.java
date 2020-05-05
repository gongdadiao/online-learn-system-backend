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
@Table(name = "student")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class Student {
    @Id
    @Column(name = "s_id")
    private String sId;

    @Column(name = "user_school")
    private String userSchool;

    @Column(name = "user_class_name")
    private String userClassName;

    @Column(name = "user_student_id")
    private String userStudentId;

    @Column(name = "user_real_name")
    private String userRealName;

    @Column(name = "user_grade")
    private String userGrade;


}