import cn.gongyan.learn.LearnApplication;
import cn.gongyan.learn.service.AdminService;
import cn.gongyan.learn.service.CodeService;
import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * @author 龚研
 * @desc Test
 * @date 2020/4/23
 * @qq 1085704190
 **/
public class Test {
    public static void main(String[] args){
        ConfigurableApplicationContext run = SpringApplication.run(LearnApplication.class, args);
        AdminService bean = run.getBean(AdminService.class);

    }
}
