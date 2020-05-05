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
@Table(name = "user")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_username")
    private String userUsername;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_role_id")
    private Integer userRoleId;

    @Column(name = "user_avatar")
    private String userAvatar;

    @Column(name = "user_description")
    private String userDescription;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "user_data_id")
    private String userDataId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "user_enable")
    private Integer userEnable;


}