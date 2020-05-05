package cn.gongyan.learn.beans.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class Announce {
    @Id
    String annId;
    String annTitle;
    String annType;
    String annVariant;
    String annCreatorid;
    String annContent;
    Date createTime;
    String annClassId;
}
