package org.coastline.amber.server.model;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
public enum ImportType {

    /**
     * 通过本平台创建
     */
    CACHE_PLATFORM,

    /**
     * 外部导入
     */
    IMPORT,

    /**
     * 通过 sentinel 自动导入
     */
    SENTINEL;

}
