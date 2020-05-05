package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.ExamRecord;
import cn.gongyan.learn.beans.vo.ExamRecordVo;
import cn.gongyan.learn.beans.vo.GradeChartsVo;
import cn.gongyan.learn.beans.vo.RecordPageVo;

import java.util.List;

public interface RecordService {
    Boolean UpdateRecord(String id, String status, String url, Integer finalScore);
    ExamRecord getOneRecord(String userId, String chapterId, String type);
    List<ExamRecord> getAll();
    List<ExamRecordVo> getUserExamRecords(String userId);
    RecordPageVo getAllExamRecords(Integer pageNo, Integer pageSize,String classId);
    String getStudentOptions(String id);
    ExamRecord addBaijuanRecord(String userId, String examId,String chapterId);
    GradeChartsVo getGradeDistribute(String chapterId);
}
