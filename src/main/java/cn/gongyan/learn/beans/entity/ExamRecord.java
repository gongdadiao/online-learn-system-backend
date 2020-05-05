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
@Table(name = "exam_record")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class ExamRecord {
    @Id
    @Column(name = "exam_record_id")
    private String examRecordId;

    @Column(name = "exam_joiner_id")
    private String examJoinerId;

    @Column(name = "exam_join_date")
    private Date examJoinDate;

    @Column(name = "exam_time_cost")
    private Integer examTimeCost;

    @Column(name = "exam_join_score")
    private Integer examJoinScore;

    @Column(name = "exam_option_url")
    private String examOptionUrl;

    @Column(name = "exam_id")
    private String examId;

    @Column(name = "class_id")
    private String classId;

    @Column(name = "chapter_id")
    private String chapterId;

    @Column(name = "exam_or_homework")
    private String examOrHomework;

    @Column(name = "exam_status")
    private String examStatus;


}