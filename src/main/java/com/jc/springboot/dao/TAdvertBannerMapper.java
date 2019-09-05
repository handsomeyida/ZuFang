package com.jc.springboot.dao;

import com.jc.springboot.entity.TAdvertBanner;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/19 12:05
 * @Description:
 */
@Repository
public interface TAdvertBannerMapper {
    //添加广告栏信息
    Boolean insert(TAdvertBanner tAdvertBanner);

}
