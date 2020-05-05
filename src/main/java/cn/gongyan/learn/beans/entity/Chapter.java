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
@Table(name = "chapter")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class Chapter {
    @Id
    @Column(name = "ch_id")
    private String chId;

    @Column(name = "ch_title")
    private String chTitle;

    @Column(name = "ch_theme_id")
    private Integer chThemeId;

    @Column(name = "ch_type")
    private String chType;

    @Column(name = "ch_res_id")
    private String chResId;

    @Column(name = "ch_creator_id")
    private String chCreatorId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "ch_seq")
    private Integer chSeq;


}