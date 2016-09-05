package com.github.phoenix.plugin.bulider;

import java.io.InputStream;

/**
 * 
 * 功能概要：文件操作接口
 * 
 * @author linbingwen
 * @since  2016年8月30日
 */
public interface FileInstanceBuilder extends TextInstanceBuilder{

    public <T> T buildInstanceFromFile(Class<T> type, String file);
    
    public <T> T buildInstanceFromFile(Class<T> type, InputStream ips);

}

