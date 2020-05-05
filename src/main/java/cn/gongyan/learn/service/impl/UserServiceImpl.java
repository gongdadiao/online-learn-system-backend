/***********************************************************
 * @Description : 用户服务
 * @author      : 龚研
 * @date        : 2019-05-16 23:40
 * @qq          : 1085704190
 ***********************************************************/
package cn.gongyan.learn.service.impl;

import cn.gongyan.learn.beans.entity.*;
import cn.gongyan.learn.beans.qo.LoginQo;
import cn.gongyan.learn.beans.qo.RegisterQo;
import cn.gongyan.learn.beans.qo.SettingQo;
import cn.gongyan.learn.beans.vo.*;
import cn.gongyan.learn.enums.LoginTypeEnum;
import org.springframework.data.domain.Page;
import cn.gongyan.learn.repository.*;
import cn.gongyan.learn.service.UserService;
import cn.gongyan.learn.utils.JwtUtils;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private RigisterRepository rigisterRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Value("${user.default.avatar}")
    private String defaultAvatar;
    @Value("${user.default.username}")
    private String defaultUsername;

    @Override
    public Boolean anthCheck(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        assert user!=null;
        if(roleRepository.findById(user.getUserRoleId()).orElse(null).getRoleName().compareTo("学生")==0){
            return false;
        }
        return true;
    }

    /**
     * 注册，等候管理员认证
     * @param registerDTO 注册参数
     * @return
     */
    @Override
    public Rigister register(RegisterQo registerDTO) {
        try {
            User example = new User();
            example.setUserUsername(registerDTO.getUserName());
            example.setUserEnable(1);
            if(userRepository.findOne(Example.of(example)).orElse(null)!=null){
                //已经存在
                return null;
            }
            Rigister rigister = new Rigister();
            rigister.setRId(IdUtil.simpleUUID());
            rigister.setRRealName(registerDTO.getRealName());
            rigister.setRUsername(registerDTO.getUserName());
            rigister.setRPassword(registerDTO.getPassword());
            rigister.setRStudentId(registerDTO.getStudentId());
            rigister.setRSchool(registerDTO.getSchool());
            rigister.setRNickname(registerDTO.getNickName());
            rigister.setCreateTime(new Date());
            rigisterRepository.save(rigister);
            return rigister;
        } catch (Exception e) {
            e.printStackTrace(); // 用户已经存在
            // 出异常，返回null，表示注册失败
            return null;
        }
    }

    @Override
    public RigisterPageVo listRegRequest(Integer pageNo, Integer pageSize) {
        try{
            // 构造分页请求,注意前端面页面的分页是从1开始的，后端是从0开始地，所以要减去1
            PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
            Page<Rigister> rigisterPage = rigisterRepository.findAll(pageRequest);
            RigisterPageVo rigisterPageVo = new RigisterPageVo();
            rigisterPageVo.setPageNo(pageNo);
            rigisterPageVo.setPageSize(pageSize);
            rigisterPageVo.setTotalCount(rigisterPage.getTotalElements());
            rigisterPageVo.setTotalPage(rigisterPage.getTotalPages());
            List<Rigister> content = rigisterPage.getContent();
            rigisterPageVo.setRigisterList(content);
            return rigisterPageVo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 认证单个用户
     * @param rigisterId
     * @return
     */
    @Override
    public User authenUser(String rigisterId) {
        try{
            Rigister rigister = rigisterRepository.findById(rigisterId).orElse(null);
            assert rigister != null;
            //设置用户信息
            Student student = new Student();
            String studentId=IdUtil.simpleUUID();
            student.setSId(studentId);
            student.setUserStudentId(rigister.getRStudentId());
            student.setUserSchool(rigister.getRSchool());
            student.setUserRealName(rigister.getRRealName());
            studentRepository.save(student);
            //设置用户
            User user = new User();
            user.setUserId(IdUtil.simpleUUID());
            user.setUserUsername(rigister.getRUsername());
            user.setUserNickname(rigister.getRNickname());
            user.setUserRoleId(3);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setUserEnable(1);
            user.setUserDataId(studentId);
            user.setUserPassword(rigister.getRPassword());
            userRepository.save(user);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String login(LoginQo loginQo) {
        User user;
        Integer tmp_role=3;
        if (LoginTypeEnum.TEACHER.getType().equals(loginQo.getLoginType())) {
            tmp_role=2;
        } else if(LoginTypeEnum.STUDENT.getType().equals(loginQo.getLoginType())){
            tmp_role=3;
        }
        User tmp_user=new User();
        tmp_user.setUserRoleId(tmp_role);
        tmp_user.setUserUsername(loginQo.getUserName());
        tmp_user.setUserEnable(1);
        Example<User> example=Example.of(tmp_user);
        user = userRepository.findOne(example).orElse(null);
        System.out.println(user.toString());
        if (user != null) {
            // 如果user不是null即能找到，才能验证用户名和密码
            // 数据库存的密码
            try {
                String passwordDb = Base64.decodeStr(user.getUserPassword());
                // 用户请求参数中的密码
                String passwordQo = loginQo.getPassword();
                System.out.println(passwordDb);
                System.out.println(passwordQo);
                if (passwordQo.compareTo(passwordDb)==0) {
                    // 如果密码相等地话说明认证成功,返回生成的token，有效期为一天
                    return JwtUtils.genJsonWebToken(user);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public UserVo getUserInfo(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        UserVo userVo = new UserVo();
        assert user != null;
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public UserInfoVo getInfo(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        UserInfoVo userInfoVo = new UserInfoVo();
        // 1.尽可能的拷贝属性
        BeanUtils.copyProperties(user, userInfoVo);
        Integer roleId = user.getUserRoleId();
        Role role = roleRepository.findById(roleId.intValue()).orElse(null);
        assert role != null;
        String roleName = role.getRoleName();

        // 2.设置角色名称
        userInfoVo.setRoleName(roleName);

        // 3.设置当前用户的角色细节
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);

        // 4.设置角色的可访问页面
        String rolePageIds = role.getRolePageIds();
        String[] pageIdArr = rolePageIds.split("-");
        List<PageVo> pageVoList = new ArrayList<>();
        for (String pageIdStr : pageIdArr) {
            // 获取页面的id
            Integer pageId = Integer.parseInt(pageIdStr);

            // 4.1 向Role中添加Page
            cn.gongyan.learn.beans.entity.Page page = pageRepository.findById(pageId).orElse(null);
            PageVo pageVo = new PageVo();
            BeanUtils.copyProperties(page, pageVo);

            // 4.2 向Page中添加action
            List<ActionVo> actionVoList = new ArrayList<>();
            String actionIdsStr = page.getActionIds();
            String[] actionIdArr = actionIdsStr.split("-");
            for (String actionIdStr : actionIdArr) {
                Integer actionId = Integer.parseInt(actionIdStr);
                Action action = actionRepository.findById(actionId).orElse(null);
                ActionVo actionVo = new ActionVo();
                assert action != null;
                BeanUtils.copyProperties(action, actionVo);
                actionVoList.add(actionVo);
            }
            // 设置actionVoList到pageVo中，然后把pageVo加到pageVoList中
            pageVo.setActionVoList(actionVoList);
            // 设置pageVoList，下面再设置到RoleVo中
            pageVoList.add(pageVo);
        }
        // 设置PageVo的集合到RoleVo中
        roleVo.setPageVoList(pageVoList);
        // 最终把PageVo设置到UserInfoVo中，这样就完成了拼接
        userInfoVo.setRoleVo(roleVo);
        return userInfoVo;
    }

    @Override
    public Integer setUserInfo(String userId, SettingQo settingDTO) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            user.setUserEmail(settingDTO.getEmail());
            if(settingDTO.getAvartar()!=null)
                user.setUserAvatar(settingDTO.getAvartar());
            user.setUserNickname(settingDTO.getNickName());
            user.setUserDescription(settingDTO.getBio());
            user.setUserPassword(Base64.encode(settingDTO.getPassword()));
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
