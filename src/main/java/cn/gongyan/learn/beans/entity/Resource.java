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
@Table(name = "resource")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class Resource {
    @Id
    @Column(name = "res_id")
    private String resId;

    @Column(name = "res_name")
    private String resName;

    @Column(name = "res_description")
    private String resDescription;

    @Column(name = "res_url")
    private String resUrl;

    @Column(name = "res_type")
    private String resType;

    @Column(name = "res_exam_id")
    private String resExamId;

    @Column(name = "res_creator_id")
    private String resCreatorId;

    @Column(name = "res_avatar")
    private String resAvatar;

    @Column(name = "res_upload_date")
    private Date resUploadDate;


}