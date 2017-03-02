package com.down.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作类.
 */
public class OperationDataBase extends SQLiteOpenHelper {

    /**
     * 数据库操作接口对象
     */
    private OnOperationDataBase operation;
    /**
     * 数据库操作链接
     */
    private SQLiteDatabase db;
    /**
     * 游标对象
     */
    private Cursor cursor;

    /**
     * 构造方法.
     *
     * @param context       上下文环境.
     * @param name          数据库名称.
     * @param cursorFactory 游标工厂.
     * @param version       数据库版本号.
     */
    public OperationDataBase(Context context, String name, CursorFactory cursorFactory, int version) {
        super(context, name, null, version);
    }

    /**
     * 构造方法,传入数据库创建表与更新数据库的实现方法.
     *
     * @param context       上下文环境.
     * @param name          数据库名称.
     * @param cursorFactory 游标工厂.
     * @param version       数据库版本号.
     * @param operation     操作数据库的实现接口.
     */
    public OperationDataBase(Context context, String name, CursorFactory cursorFactory, int version, OnOperationDataBase operation) {
        super(context, name, null, version);
        this.operation = operation;
    }

    /**
     * 重写父类的onCreate方法,调用OperationDataBase接口中的createTable方法.
     *
     * @param db 数据库操作对象.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (operation != null) {
            operation.createTable(db);
        }
    }

    /**
     * 重写父类的onUpgrade方法,调用OperationDataBase接口中的updateDataBase方法.
     *
     * @param db         数据库操作对象.
     * @param oldVersion 旧版本号.
     * @param newVersion 新版本号.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (operation != null) {
            operation.updateDataBase(db, oldVersion, newVersion);
        }
    }


    /**
     * 数据库降级
     *
     * @param db
     * @param oldVersion 旧版本
     * @param newVersion 新版本
     */

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (operation != null) {
            operation.updateDataBase(db, oldVersion, newVersion);
        }
    }

    /**
     * 获取数据库写入权限操作对象.
     *
     * @return 数据库写入权限操作对象.
     */
    public SQLiteDatabase getWrite() {
        if (null == db || db.isReadOnly()) {
            db = this.getWritableDatabase();
        }
        return db;
    }

    /**
     * 获取数据库读取权限操作对象.
     *
     * @return 数据库读取权限操作对象.
     */
    public SQLiteDatabase getRead() {
        if (null == db || !db.isReadOnly()) {
            db = this.getReadableDatabase();
        }
        return db;
    }

    /**
     * 向数据库的指定表中插入数据.
     *
     * @param needClose 是否需要关闭数据库连接.true为关闭,否则不关闭.
     * @param table     表名.
     * @param titles    字段名.
     * @param values    数据值.
     * @return 若传入的字段名与插入值的长度不等则返回false, 否则执行成功则返回true.
     */
    public boolean insert(boolean needClose, String table, String[] titles, String[] values) {
        if (titles.length != values.length) // 判断传入的字段名数量与插入数据的数量是否相等
            return false;
        else {
            if (null == db || db.isReadOnly()) {
                db = this.getWritableDatabase();
            }
            if (db.isOpen()) {
                // 将插入值与对应字段放入ContentValues实例中
                ContentValues contentValues = new ContentValues();
                for (int i = 0; i < titles.length; i++) {
                    contentValues.put(titles[i], values[i]);
                }
                getWrite().insert(table, null, contentValues); // 执行插入操作
                if (needClose) {
                    close();
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 删除数据库的指定表中的指定数据.
     *
     * @param needClose   是否需要关闭数据库连接.true为关闭,否则不关闭.
     * @param table       表名.
     * @param conditions  条件字段.
     * @param whereValues 条件值.
     */
    public int delete(boolean needClose, String table, String conditions, String[] whereValues) {
        int resultClode=-1;
    	if (null == db || db.isReadOnly()) {
            db = this.getWritableDatabase();
        }
        if (db.isOpen()) {
        	resultClode=getWrite().delete(table, conditions, whereValues); // 执行删除操作
        }
        if (needClose) {
            close();
        }
        return resultClode; 
    }

    /**
     * 修改数据库的指定表中的指定数据.
     *
     * @param needClose   是否需要关闭数据库连接.true为关闭,否则不关闭.
     * @param table       表名.
     * @param titles      字段名.
     * @param values      数据值.
     * @param conditions  条件字段.
     * @param whereValues 条件值.
     * @return 若传入的字段名与插入值的长度不等则返回false, 否则执行成功则返回true.
     */
    public boolean update(boolean needClose, String table, String[] titles, String[] values, String conditions, String[] whereValues) {
        if (titles.length != values.length) {
            return false;
        } else {
            if (null == db || db.isReadOnly()) {
                db = this.getWritableDatabase();
            }
            if (db.isOpen()) {
                // 将插入值与对应字段放入ContentValues实例中
                ContentValues contentValues = new ContentValues();
                for (int i = 0; i < titles.length; i++) {
                    contentValues.put(titles[i], values[i]);
                }
                getWrite().update(table, contentValues, conditions, whereValues); // 执行修改操作
                if (needClose) {
                    close();
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 查询数据库的指定表中的指定数据.
     *
     * @param table         表名.
     * @param columns       查询字段.
     * @param selection     条件字段.
     * @param selectionArgs 条件值.
     * @param groupBy       分组名称.
     * @param having        分组条件.与groupBy配合使用.
     * @param orderBy       排序字段.
     * @param limit         分页.
     * @return 查询结果游标
     */
    public Cursor select(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        cursor = getRead().query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit); // 执行查询操作.
        return cursor;
    }

    /**
     * 删除数据库的指定表中的所有数据.
     *
     * @param needClose 是否需要关闭数据库连接.true为关闭,否则不关闭.
     * @param table     表名
     */
    public void clear(boolean needClose, String table) {
        getWrite().execSQL("delete from " + table);
        if (needClose) {
            close();
        }
    }

    /**
     * 关闭打开的所有数据库对象.
     */
    public void close() {
        if (null != cursor && !cursor.isClosed()) {
            cursor.close();
        }
        if (null != db && db.isOpen()) {
            db.close();
            db = null;
        }
        super.close();
    }
}
