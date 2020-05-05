package cn.gongyan.learn.beans.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "trainning_question")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class TrainningQuestion {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "q_id")
    private Integer qId;

    @Column(name = "q_title")
    private String qTitle;

    @Column(name = "q_desc")
    private String qDesc;

    @Column(name = "q_content")
    private String qContent;

    @Column(name = "q_initial_code")
    private String qInitialCode;

    @Column(name = "q_condition")
    private String qCondition;

    @JsonIgnore
    @Column(name="q_real_answer")
    private String qRealAnswer;

}