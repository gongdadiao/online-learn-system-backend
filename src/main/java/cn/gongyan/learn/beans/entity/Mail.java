package cn.gongyan.learn.beans.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mail")
@Getter
@Setter
@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" }, ignoreUnknown = true)
@DynamicInsert
public class Mail {
    @Id
    @Column(name = "m_id")
    private String mId;

    @Column(name = "m_title")
    private String mTitle;

    @Column(name = "m_sender_id")
    private String mSenderId;

    @Column(name = "m_receiver_id")
    private String mReceiverId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "m_content")
    private String mContent;

    @Column(name="m_classId")
    private String mClassId;
}