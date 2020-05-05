package cn.gongyan.learn.beans.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "classes_info")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class ClassesInfo {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "desc")
    private String desc;

    @Column(name = "goal")
    private String goal;

    @Column(name = "outline")
    private String outline;

    @Column(name = "pre_knowledge")
    private String preKnowledge;

    @Column(name = "request")
    private String request;

    @Column(name = "information")
    private String information;

    @Column(name = "classes_id")
    private String classesId;


}