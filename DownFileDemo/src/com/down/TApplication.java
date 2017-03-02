package com.down;

import java.io.File;
import com.down.database.DataBaseConfig;
import com.down.database.OnOperationDataBase;
import com.down.database.OperationDataBase;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * 全局公用Application类
 */
public class TApplication extends Application implements OnOperationDataBase {

	/** 全局上下文，可用于文本、图片、sp数据的资源加载 */
	public static Context context;

	/**数据库版本*/
	public static final int DATA_BASE_VERSION = 1;
	/**数据库名字*/
	public static final String DATA_BASE_DOWN = "Down_Demo";
	/**数据库操作*/
	public static OperationDataBase dateBaseHelper;

	public static final String PATH_BASE = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/Down_Demo/";
	/** 下载完成文件 */
	public static final String PATH_COMPLETE = PATH_BASE + "Down/";

	/**获取数据库对象*/
	public static OperationDataBase getOperationDataBase() {
		if (dateBaseHelper == null) {
			dateBaseHelper = new OperationDataBase(context,
					TApplication.DATA_BASE_DOWN, null,
					TApplication.DATA_BASE_VERSION);
		}
		return dateBaseHelper;
	}

	/**关闭数据库连接*/
	public static void CloseOperationDataBase() {
		if (dateBaseHelper != null) {
			dateBaseHelper.close();
			dateBaseHelper = null;
		}
	}

	@Override
	public void onCreate() {
		// 实例化全局调用的上下文
		context = getApplicationContext();

		// 数据库操作
		dateBaseHelper = new OperationDataBase(context,
				TApplication.DATA_BASE_DOWN, null,
				TApplication.DATA_BASE_VERSION, this);
		dateBaseHelper.onCreate(dateBaseHelper.getWritableDatabase());
		dateBaseHelper.close();

		// 创建保存下载完成路径
		File file_complete = new File(PATH_COMPLETE);
		if (!file_complete.exists()) {
			file_complete.mkdirs();
		}

		super.onCreate();
	}

	@Override
	public void createTable(SQLiteDatabase db) {
		String sql_down = "create table if not exists "
				+ DataBaseConfig.TABLE_DOWN + "(" // 创建下载表
				+ DataBaseConfig.ID + " integer PRIMARY KEY autoincrement, " // 自增长id.
				+ DataBaseConfig.DOWN_ID + " varchar, " // 下载id
				+ DataBaseConfig.DOWN_NAME + " varchar, " // app的名字
				+ DataBaseConfig.DOWN_ICON + " varchar, " // app的图片
				+ DataBaseConfig.DOWN_FILE_URL + " varchar, " // 广告下载的url
				+ DataBaseConfig.DOWN_STATE + " int, " // 下载状态 0==未开始 1==进行中
														// 2==完成 3==暂停 4==排队中
														// 5==失败
				+ DataBaseConfig.DOWN_FILE_SIZE + " int, " // 文件总大小
				+ DataBaseConfig.DOWN_FILE_SIZE_ING + " int" // 下载进度
				+ ");";
		db.execSQL(sql_down);
	}

	@Override
	public void updateDataBase(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
