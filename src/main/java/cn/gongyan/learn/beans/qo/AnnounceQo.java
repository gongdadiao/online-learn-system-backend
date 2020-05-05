package cn.gongyan.learn.beans.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceQo {
    String name;
    String type;
    String variant;
    String content;
    String classId;
}
