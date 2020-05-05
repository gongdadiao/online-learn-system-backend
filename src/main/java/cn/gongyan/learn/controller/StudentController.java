package cn.gongyan.learn.controller;

import cn.gongyan.learn.beans.entity.Rigister;
import cn.gongyan.learn.beans.qo.StudentExcelQo;
import cn.gongyan.learn.beans.vo.ResultVO;
import cn.gongyan.learn.beans.vo.StudentVo;
import cn.gongyan.learn.enums.StudentExcelEnum;
import cn.gongyan.learn.service.ClassService;
import cn.gongyan.learn.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 龚研
 * @desc StudentController
 * @date 2020/4/17
 * @qq 1085704190
 **/

@RestController
@Api(tags = "Student APIs")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    ClassService classService;

    @GetMapping("/reglist")
    @ApiOperation("获取注册列表")
    ResultVO<List<Rigister>> getRegList(@RequestParam("page") Integer page, @RequestParam("size") Integer number) {
        ResultVO<List<Rigister>> resultVO;
        try {
            List<Rigister> rigisterList = studentService.getRigisterList(page, number);
            resultVO = new ResultVO<>(0, "获取注册列表成功", rigisterList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取注册列表失败", null);
        }
        return resultVO;
    }

    @GetMapping("/classList/{id}")
    @ApiOperation("获取这门课学生的列表")
    ResultVO<List<StudentVo>> getResList(@PathVariable String id) {
        ResultVO<List<StudentVo>> resultVO;
        try {
            List<StudentVo> studentsList = classService.getCourseStudentsList(id);
            resultVO = new ResultVO<>(0, "获取学生列表成功", studentsList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取学生列表失败", null);
        }
        return resultVO;
    }

    @GetMapping("/list")
    @ApiOperation("获取学生的列表")
    ResultVO<List<StudentVo>> getResList(@RequestParam("page") Integer page,@RequestParam("size") Integer number) {
        ResultVO<List<StudentVo>> resultVO;
        try {
            List<StudentVo> studentList = studentService.getStudentList(page, number);
            resultVO = new ResultVO<>(0, "获取学生列表成功", studentList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取学生列表失败", null);
        }
        return resultVO;
    }


    @PostMapping("/importfile")
    @ApiOperation("上传学生批量加入文件")
    public ResultVO<String> importLogisticsCcode(@RequestParam("file") MultipartFile file) throws Exception {
        ResultVO<String> resultVO=null;
        // 获取文件名
        String fileName = file.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)){
            resultVO=new ResultVO<>(-1,"文件不能为空",null);
        }
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        if (!prefix.toLowerCase().contains("xls") && !prefix.toLowerCase().contains("xlsx") ){
            resultVO=new ResultVO<>(-1,"文件格式异常，请上传Excel文件格式",null);
        }
        // 防止生成的临时文件重复-建议使用UUID
        final File excelFile = File.createTempFile(System.currentTimeMillis()+"", prefix);
        file.transferTo(excelFile);

        //由于2003和2007的版本所使用的接口不一样，所以这里统一用Workbook做兼容
        boolean isExcel2003 = prefix.toLowerCase().endsWith("xls")?true:false;
        Workbook workbook = null;
        if(isExcel2003){
            workbook = new HSSFWorkbook(new FileInputStream(excelFile));
        }else{
            workbook = new XSSFWorkbook(new FileInputStream(excelFile));
        }
        //Excel表中的内容
        Sheet sheet = workbook.getSheetAt(0);
        //这里重1开始，跳过了标题，直接从第二行开始解析
        for(int i=1; i<sheet.getLastRowNum()+1; i++) {
            Row row =sheet.getRow(i);
            //设置行格式和验证start 这里最好做成一个方法，免得代码多处复制
            if(row.getCell(0)!=null){
                row.getCell(0).setCellType(CellType.STRING);
            }
            if(row.getCell(1)!=null){
                row.getCell(1).setCellType(CellType.STRING);
            }
            if(row.getCell(2)!=null){
                row.getCell(2).setCellType(CellType.STRING);
            }
            if(row.getCell(3)!=null){
                row.getCell(3).setCellType(CellType.STRING);
            }
            String name = row.getCell(StudentExcelEnum.NAME.getKey()).getStringCellValue(); //订单编号
            if (StringUtils.isEmpty(name)){
                resultVO=new ResultVO<>(-1,"姓名出现问题",null);
            }
            String school = row.getCell(StudentExcelEnum.SCHOOL.getKey()).getStringCellValue(); //订单编号
            if (StringUtils.isEmpty(school)){
                resultVO=new ResultVO<>(-1,"学校出现问题",null);
            }
            String grade = row.getCell(StudentExcelEnum.GRADE.getKey()).getStringCellValue(); //订单编号
            if (StringUtils.isEmpty(grade)){
                resultVO=new ResultVO<>(-1,"年级出现问题",null);
            }
            String stuId = row.getCell(StudentExcelEnum.STUDENTID.getKey()).getStringCellValue(); //订单编号
            if (StringUtils.isEmpty(stuId)){
                resultVO=new ResultVO<>(-1,"学号出现问题",null);
            }
            //组装列表
            StudentExcelQo excelQo = new StudentExcelQo();
            excelQo.setName(name);
            excelQo.setGrade(grade);
            excelQo.setSchool(school);
            excelQo.setStudentId(stuId);
            Integer result = studentService.addOneStudent(excelQo);
            if(result==0){
                throw new Exception("添加学生错误");
            }
        }
        //删除临时转换的文件
        if (excelFile.exists()) {
            excelFile.delete();
        }
        resultVO = new ResultVO<String>(0,"批量添加学生成功", null);
        return  resultVO;
    }
}