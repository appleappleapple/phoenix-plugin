package com.github.phoenix.plugin.bulider;


/**
 * 
 * 功能概要：从文本构建实例
 * 
 * @author linbingwen
 * @since  2016年8月30日
 */
public interface TextInstanceBuilder {

    /**
     * 可以构建对像的最小单元
     * @author linbingwen
     * @since  2016年8月30日 
     * @param type
     * @param text
     * @return
     */
    public <T> T buildInstanceFromText(Class<T> type, String text);

}
