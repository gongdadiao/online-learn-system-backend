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
@Table(name = "rigister")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class Rigister {
    @Id
    @Column(name = "r_id")
    private String rId;

    @Column(name = "r_username")
    private String rUsername;

    @Column(name = "r_nickname")
    private String rNickname;

    @Column(name = "r_school")
    private String rSchool;

    @Column(name = "r_class_name")
    private String rClassName;

    @Column(name = "r_student_id")
    private String rStudentId;

    @Column(name = "r_real_name")
    private String rRealName;

    @Column(name = "r_password")
    private String rPassword;

    @Column(name = "r_avatar")
    private String rAvatar;

    @Column(name = "r_description")
    private String rDescription;

    @Column(name = "r_email")
    private String rEmail;

    @Column(name = "r_phone")
    private String rPhone;

    @Column(name = "create_time")
    private Date createTime;


}