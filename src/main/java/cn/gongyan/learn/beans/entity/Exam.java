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
@Table(name = "exam")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class Exam {
    @Id
    @Column(name = "exam_id")
    private String examId;

    @Column(name = "exam_name")
    private String examName;

    @Column(name = "exam_avatar")
    private String examAvatar;

    @Column(name = "exam_description")
    private String examDescription;

    @Column(name = "exam_questions_url")
    private String examQuestionsUrl;

    @Column(name = "exam_score")
    private Integer examScore;

    @Column(name = "exam_score_danxuan")
    private Integer examScoreDanxuan;

    @Column(name = "exam_score_duoxuan")
    private Integer examScoreDuoxuan;

    @Column(name = "exam_score_panduan")
    private Integer examScorePanduan;

    @Column(name = "exam_score_tiankong")
    private Integer examScoreTiankong;

    @Column(name = "exam_score_wenda")
    private Integer examScoreWenda;

    @Column(name = "exam_creator_id")
    private String examCreatorId;

    @Column(name = "exam_time_limit")
    private Integer examTimeLimit;

    @Column(name = "exam_start_date")
    private Date examStartDate;

    @Column(name = "exam_end_date")
    private Date examEndDate;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


}