package com.version.common.entity.message;

import java.io.Serializable;

/**
 * @Author 周希来
 * @Date 2019/9/4 18:57
 */
public interface IMessage extends Serializable {
    int getCode();
    Object getData(Class clazz);
}
