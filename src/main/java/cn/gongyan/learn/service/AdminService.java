package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.*;
import cn.gongyan.learn.repository.*;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author 龚研
 * @desc AdminService
 * @date 2020/5/20
 * @qq 1085704190
 **/
@Service
@Transactional
public class AdminService {
    @Autowired
    ClassesRepository classesRepository;
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    ThemeRepository themeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseStudentRepository courseStudentRepository;
    @Autowired
    AnnounceRepository announceRepository;
    @Autowired
    StudentRepository studentRepository;

    String[][] themeNames={
            {"第一周","第二周","第三周"},
            {"接触Java","Java小试身手","深入探究"},
            {"入门学习","中级学习","深入学习"}};

    /**
     * 设置章节+小节内容
     * @return
     */
    public Boolean setCourseInfo(){
        List<Classes> classesList = classesRepository.findAll();
        List<Resource> resourceList = resourceRepository.findAll();
        for (Classes course : classesList) {
            if(course.getCType().compareTo("实训")==0)
                continue;
            Random random = new Random();
            int index = random.nextInt(3);
            for(String name:themeNames[index]) {
                Theme theme = new Theme();
                theme.setThemeId(0);
                theme.setThemeName(name);
                theme.setThemeDescription("章节");
                theme.setThemeClassId(course.getCId());
                Theme theme_saved = themeRepository.save(theme);

                for (Resource res : resourceList) {
                    int number = random.nextInt(5);
                    if(number%2==0){
                        Chapter chapter = new Chapter();
                        chapter.setChId(IdUtil.simpleUUID());
                        chapter.setCreateTime(new Date());
                        chapter.setChType(res.getResType());
                        chapter.setChResId(res.getResId());
                        chapter.setChTitle(res.getResName());
                        chapter.setChCreatorId("");
                        chapter.setChThemeId(theme_saved.getThemeId());
                        chapterRepository.save(chapter);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 学生选课信息加入
     * @return
     */
    public Boolean chooseCourses(){
        List<User> all = userRepository.findAll();
        List<Classes> classesList = classesRepository.findAll();
        Random random = new Random();
        for (User user : all) {
            if(user.getUserRoleId()==2)
                continue;
            for (Classes course : classesList) {
                int number = random.nextInt(5);
                if(number==1) {
                    CourseStudent courseStudent = new CourseStudent();
                    courseStudent.setCsId(0);
                    courseStudent.setCsStudentId(user.getUserId());
                    courseStudent.setCsCourseId(course.getCId());
                    courseStudent.setCreateTime(new Date());
                    courseStudentRepository.save(courseStudent);
                }
            }
        }
        return true;
    }

    String[][] titles={
            {"欢迎来到java课程","置顶","warning","你会在这门课学到你想要的内容"},
            {"老师需要认识你","普通","info","多和教师互动"},
            {"如何检测自己","普通","info","多完成考试"},
            {"你希望学习什么？","普通","info","你可以根据自己的需要浏览课件"},
            {"查看你的课件","紧急","danger","请查看自己的课件是否正常"},
    };

    /**
     * 设置公告
     * @return
     */
    public Boolean setAnnounces(){
        List<Classes> all = classesRepository.findAll();
        for (Classes course : all) {
            if(course.getCType().compareTo("实训")==0)
                continue;
            for(int i=0;i<titles.length;i++){
                Announce announce = new Announce();
                announce.setAnnId(IdUtil.simpleUUID());
                announce.setAnnClassId(course.getCId());
                announce.setCreateTime(new Date());
                announce.setAnnTitle(titles[i][0]);
                announce.setAnnType(titles[i][1]);
                announce.setAnnVariant(titles[i][2]);
                announce.setAnnContent(titles[i][3]);
                announce.setAnnCreatorid(course.getCCreatorId());
                announceRepository.save(announce);
            }
        }
        return true;
    }
    String[] xing={"王","李","龚","郑","赵","吴","钱","俞","凤","周",
            "雷","韦","鲁","苗","朱","秦","尤","许","何","吕","施","张",
            "孔","曹","严","华","金","魏","陶","姜"};
    String[] ming={"旭","梅","萍","元","雪","晓","莉","明","燕","杨","敏",
            "黄","俊","飞","文","斌","卫","国","伟","九","庆","以","莲","留","强","晓","敏","汝","彬","台","铭",
        "林","帆","伟","东","明","骏","世","鹏",""};

    /**
     * 批量添加用户
     * @return
     */
    public Boolean setStudents(){
        int num=0;
        Long number_id=1600200201l;
        for(int i=0;i<xing.length;i++){
            for(int j=0;j<ming.length;j++){
                    num++;
                    if(num>200)
                        return true;
                    String name=xing[i]+ming[j];
                    User user = new User();
                    Student student = new Student();
                    //设置学生信息
                    student.setSId(IdUtil.simpleUUID());
                    student.setUserGrade("大二");
                    student.setUserSchool("桂林电子工业大学");
                    student.setUserRealName(name);
                    student.setUserStudentId(number_id.toString());
                    student.setUserClassName("");
                    Student student1 = studentRepository.save(student);

                    //设置用户
                    user.setUserId(IdUtil.simpleUUID());
                    user.setUserNickname(name);
                    user.setUserDescription("无");
                    user.setUserUsername(number_id.toString());
                    number_id++;
                    user.setCreateTime(new Date());
                    user.setUpdateTime(new Date());
                    user.setUserDataId(student1.getSId());
//                    user.setUserEmail("");
                    user.setUserRoleId(3);
                    user.setUserEnable(1);
//                    user.setUserPhone("");
                    user.setUserAvatar("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3968417432,4100418615&fm=26&gp=0.jpg");
                    user.setUserPassword(Base64.encode("123"));
                    userRepository.save(user);

            }
        }
        return true;
    }

    String[] firstParagraph={
            "Java不仅是一门程序设计语言，而且发展为一个庞大的生态圈。Java被广泛应用到企业级系统、手机、桌面软件等开发，是就业市场最急需的技术。本课程讲授Java技术进阶部分，涉及多方位应用，并通过大量程序进行佐证讲解。学生学习课程后，可完成常见的功能应用开发。本课程是Java/EE、安卓等的先导课程。",
            "Java不仅是一门程序设计语言，而且发展为一个庞大的生态圈。Java被广泛应用到企业级系统、手机、桌面软件等开发，是应用市场最急需的技术。本课程讲授Java技术高阶部分，学习Java深层次原理，剖析当前主流的框架的底层技术，助力学生成为Java Geeker。",
            "Java是一门程序设计语言，长期位居编程语言排行榜TIOBE的首位。从1995年面世以来，已经被广泛应用到企业级系统、手机、桌面软件等开发。据Oracle统计，已有超过30亿电子设备运行Java程序。本课程主要讲授Java核心技术原理，并通过大量程序进行佐证讲解。本课程也是Java EE、Android等技术的先导课程。",
            "本课程由江苏海洋大学计算机工程学院智慧教育团队倾情打造，在讲授Java面向对象编程知识的同时，努力培养学生的计算思维和创新思维，让同学们在快乐中学习，在学习中实践，在实践中进步，在进步中成才！本课程已经开设5期，累计选课人数近4.5万，受到学习者的普遍欢迎。欢迎更多学习者参加第6期学习！",
            "Java语言作为近几十年来最流行的程序设计语言，深受全世界程序员的喜爱。本课程带领您从零基础开始学习，您将领会面向对象程序设计思想和方法，学会Java语法、数组、对象与类、继承与多态、I/O、多线程与并行编程、异常处理、JavaFX GUI等知识，并能熟练应用Java语言编写富互联网应用的大型软件系统。",
            "Java是一种优秀的面向对象的语言，具有跨平台性、用途广泛、容易学习等特点，众多的开源项目都是用Java实现的，可以说Java是程序设计必学的语言。这门课程掌握Java语言、面向对象的特点，掌握Java在多线程、图形用户界面、网络等方面的应用，同时要养成良好的编程习惯，能够编写有一定规模的应用程序。",
            "Java语言以其卓越的简洁性、通用性、可移植性和安全性等优势，受到世界上众多软件公司和广大程序员的青睐。在世界权威编程语言排行榜TIOBE上，Java语言多年来的领先地位不可撼动。《Java程序设计》课程将由3位经验丰富的一线教师引领大家理解Java程序设计的理念和精髓，带你遨游有趣的Java世界！",
            "Java Web应用开发是软件技术专业的核心课程。该课程融入“技能竞赛+课堂”的育人模式，重点突出对学生实际编程能力的培养和开发经验的积累，通过贯穿案例教学法使学生熟练掌握应用 Java 技术开发 Web 应用程序的技能，满足Web应用开发工程师、Java软件工程师等的岗位任职要求。",
            "C++？ Java？ 选哪个好？ 来吧，选这门课，两种语言一锅烩，对比学习、齐头并进~",
            "Java是目前世界上最流行的计算机编程语言，Java内容繁多，因此一些Java课程无暇顾及程序基本设计思想，而是要求学习者具备编程经验，特别是大多将C语言作为学习本课程的先修课程。我们没有这个约束，一切从零开始学习程序设计。没有程序设计经验？或者只具备简单的程序设计能力？这门课程都适合你。",
            "Java具有简单易学,面向对象,多线程等众多特性,在软件设计中应用广泛。课程从零基础出发系统讲解了Java SE的核心知识,内容覆盖计算机二级等级考试大纲要求。课程简明形象表达Java编程概念,结合趣味性案例讲述Java编程思想与方法,在教材《Java8入门与实践(微课视频版》一书中给出了更丰富内容和案例。",
            "如果说编程世界是五颜六色的，那么Java绝对是其中的最灿烂的一抹红。从发展至今，Java已经长期占据编程语言排行榜的第一位。本课程由江苏财经职业技术学院计算机专业团队倾情打造，讲授Java面向对象程序设计的基本思想和方法，让同学们能运用所学的知识编制出实用的Java程序。让我们一起出发吧！"
    };
    public Boolean setClassDesc(){
        List<Classes> classesList = classesRepository.findAll();
        int index=0;
        for (Classes course : classesList) {
            course.setCDescription(firstParagraph[index]);
            index++;
            if(index>=firstParagraph.length)
                index=0;
        }
        return true;
    }
}
