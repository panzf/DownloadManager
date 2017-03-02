package com.down.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * 操作数据库接口.
 */
public interface OnOperationDataBase {

    /**
     * 创建表接口.
     *
     * @param db 数据库操作对象.
     */
    void createTable(SQLiteDatabase db);

    /**
     * 更新数据库接口.
     *
     * @param db         数据库操作对象.
     * @param oldVersion 老版本号.
     * @param newVersion 新版本号.
     */
    void updateDataBase(SQLiteDatabase db, int oldVersion, int newVersion);

}
