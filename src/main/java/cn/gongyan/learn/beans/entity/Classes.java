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
@Table(name = "classes")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class Classes {
    @Id
    @Column(name = "c_id")
    private String cId;

    @Column(name = "c_title")
    private String cTitle;

    @Column(name = "c_avartar")
    private String cAvartar;

    @Column(name = "c_creator_id")
    private String cCreatorId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "c_term")
    private String cTerm;

    @Column(name = "c_enable")
    private Long cEnable;

    @Column(name="c_description")
    private String cDescription;

    @Column(name="c_theme_id")
    private Integer cThemeId;

    @Column(name="c_nav_recommend_id")
    private Integer cNavRecommendId;

    @Column(name="c_type")
    private String cType;
}