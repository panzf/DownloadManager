package com.down.database;

public class DataBaseConfig {
	// 下载表
	public static final String TABLE_DOWN = "table_down";
	// 自增长id
	public static final String ID = "id";
	// 下载id
	public static final String DOWN_ID = "down_id";
	// app名字
	public static final String DOWN_NAME = "down_name";
	// app图片
	public static final String DOWN_ICON = "down_icon";
	// app下载url
	public static final String DOWN_FILE_URL = "down_file_url";
	// 下载状态  -1==新增下载   0==开始下载  1==正在下载  2==等待下载  3==暂停   4==完毕 5==失败 6==删除
	public static final String DOWN_STATE = "down_state";
	// app总大小
	public static final String DOWN_FILE_SIZE = "down_file_size";
	// app下载进度
	public static final String DOWN_FILE_SIZE_ING = "down_file_size_ing";
}
