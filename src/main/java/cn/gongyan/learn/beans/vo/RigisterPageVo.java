package cn.gongyan.learn.beans.vo;

import cn.gongyan.learn.beans.entity.Rigister;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 龚研
 * @desc RigisterPageVo
 * @date 2020/4/12
 * @qq 1085704190
 **/
@Data
public class RigisterPageVo {
    /**
     * 分页时每个分页的大小
     */
    private Integer pageSize;

    /**
     * 当前是在第几页，注意要比前端传过来地小1
     */
    private Integer pageNo;

    /**
     * 一共有多少条符合条件的记录
     */
    private Long totalCount;

    /**
     * 一共有多少页
     */
    private Integer totalPage;

    /**
     * 当前页的详细数据
     */
    @JsonProperty("data")
    private List<Rigister> rigisterList;
}
