package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Theme;
import cn.gongyan.learn.beans.qo.ThemeCreateQo;
import cn.gongyan.learn.repository.ThemeRepository;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 * @author 龚研
 * @desc ThemeService
 * @date 2020/4/13
 * @qq 1085704190
 **/
@Service
@Transactional
public class ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    /**
     * 创建一个主题
     * @param themeCreateQo
     * @return
     */
    public Theme createTheme(ThemeCreateQo themeCreateQo){
        try{
            Theme theme = new Theme();
            //theme.setThemeId(IdUtil.simpleUUID());
            theme.setThemeId(0);
            theme.setThemeClassId(themeCreateQo.getThemeClassId());
            theme.setThemeDescription(themeCreateQo.getThemeDescription());
            theme.setThemeName(themeCreateQo.getThemeName());
            theme.setThemeSeq(themeCreateQo.getThemeSeq());
            themeRepository.save(theme);
            return theme;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
