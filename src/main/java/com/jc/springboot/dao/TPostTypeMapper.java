package com.jc.springboot.dao;

import com.jc.springboot.entity.TPostType;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 17:02
 * @Description:
 */
@Repository
public interface TPostTypeMapper {

    Boolean update(TPostType postType);
}
