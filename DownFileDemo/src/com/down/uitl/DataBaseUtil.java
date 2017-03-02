package com.down.uitl;

import java.util.ArrayList;

import android.database.Cursor;
import android.text.TextUtils;

import com.down.TApplication;
import com.down.bean.DownLoadBean;
import com.down.database.DataBaseConfig;
import com.down.database.OperationDataBase;
import com.down.executors.DownLoadManager;

public class DataBaseUtil {
	
	 

	public synchronized static void insertDown(DownLoadBean bean) {
		String table = DataBaseConfig.TABLE_DOWN;// 表名
		OperationDataBase helper = TApplication.getOperationDataBase();

		/** 字段名对应字段值 **/
		String[] titles = new String[] { DataBaseConfig.DOWN_ID,
				DataBaseConfig.DOWN_NAME, DataBaseConfig.DOWN_ICON,
				DataBaseConfig.DOWN_FILE_URL, DataBaseConfig.DOWN_STATE,
				DataBaseConfig.DOWN_FILE_SIZE,
				DataBaseConfig.DOWN_FILE_SIZE_ING };

		/** 字段值对应字段名 **/
		String[] values = new String[] { bean.id + "", bean.appName + "",
				bean.appIcon + "", bean.url + "", bean.downloadState + "",
				bean.appSize + "", bean.currentSize + "" };

		helper.insert(true, table, titles, values);
	}
	
	public synchronized static boolean confirmDownload(String id) {
		if (!TextUtils.isEmpty(id)) {
				OperationDataBase helper = TApplication.getOperationDataBase();
				Cursor cursor=helper.select(DataBaseConfig.TABLE_DOWN
						,new String[]{DataBaseConfig.DOWN_STATE
						,DataBaseConfig.DOWN_FILE_SIZE_ING}
						,DataBaseConfig.DOWN_ID + " = ? ", new String[]{id}
						, null, null, null, null);
			if (cursor.moveToNext()) {
				int state	=cursor.getInt(cursor
						.getColumnIndex(DataBaseConfig.DOWN_STATE));
				long curSize=cursor.getLong(cursor
						.getColumnIndex(DataBaseConfig.DOWN_FILE_SIZE_ING));
				if (!(state == DownLoadManager.STATE_PAUSED && curSize != 0)) {
					int resultClode =helper.delete(true, DataBaseConfig.TABLE_DOWN,
							DataBaseConfig.DOWN_ID + " =? ", new String[] { id });
				return  resultClode == 1 ? true : false;
				}
			}
			
			cursor.close();
			helper.close();
		}
		return false;
	}
	
	/**
	 * 根据id获取数据
	 */
	public synchronized static DownLoadBean getDownLoadById(String DownloadID) {
		DownLoadBean bean = null;

		OperationDataBase helper = TApplication.getOperationDataBase();

		Cursor cursor = helper.select(DataBaseConfig.TABLE_DOWN,
				new String[] { "*" }, DataBaseConfig.DOWN_ID + " = ? ",
				new String[] { DownloadID }, null, null, null, null);
		if (cursor.moveToNext()) {
			bean = new DownLoadBean();
			bean.id = cursor.getString(cursor
					.getColumnIndex(DataBaseConfig.DOWN_ID));
			bean.appName = cursor.getString(cursor
					.getColumnIndex(DataBaseConfig.DOWN_NAME));
			bean.appIcon = cursor.getString(cursor
					.getColumnIndex(DataBaseConfig.DOWN_ICON));
			bean.url = cursor.getString(cursor
					.getColumnIndex(DataBaseConfig.DOWN_FILE_URL));
			bean.downloadState = cursor.getInt(cursor
					.getColumnIndex(DataBaseConfig.DOWN_STATE));
			bean.appSize = cursor.getLong(cursor
					.getColumnIndex(DataBaseConfig.DOWN_FILE_SIZE));
			bean.currentSize = cursor.getLong(cursor
					.getColumnIndex(DataBaseConfig.DOWN_FILE_SIZE_ING));
		}
		cursor.close();
		helper.close();
		return bean;
	}

	/**
	 * 获取所有数据
	 */
	public synchronized static ArrayList<DownLoadBean> getDownLoad() {
		ArrayList<DownLoadBean> list = new ArrayList<DownLoadBean>();
		OperationDataBase helper = TApplication.getOperationDataBase();

		Cursor cursor = helper.select(DataBaseConfig.TABLE_DOWN,
				new String[] { "*" }, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			DownLoadBean bean = new DownLoadBean();
			bean.id = cursor.getString(cursor
					.getColumnIndex(DataBaseConfig.DOWN_ID));
			bean.appName = cursor.getString(cursor
					.getColumnIndex(DataBaseConfig.DOWN_NAME));
			bean.url = cursor.getString(cursor
					.getColumnIndex(DataBaseConfig.DOWN_FILE_URL));
			bean.downloadState = cursor.getInt(cursor
					.getColumnIndex(DataBaseConfig.DOWN_STATE));
			bean.appSize = cursor.getLong(cursor
					.getColumnIndex(DataBaseConfig.DOWN_FILE_SIZE));
			bean.currentSize = cursor.getLong(cursor
					.getColumnIndex(DataBaseConfig.DOWN_FILE_SIZE_ING));

			list.add(bean);
		}
		cursor.close();
		helper.close();
		return list;
	}

	/**
	 * 修改下载数据库
	 */
	public synchronized static void UpdateDownLoadById(DownLoadBean bean) {
		OperationDataBase helper = TApplication.getOperationDataBase();
		String[] titles = { DataBaseConfig.DOWN_STATE,
				DataBaseConfig.DOWN_FILE_SIZE_ING };
		String[] values = { bean.downloadState + "", bean.currentSize + "" };
		helper.update(true, DataBaseConfig.TABLE_DOWN, titles, values,
				DataBaseConfig.DOWN_ID + " =? ", new String[] { bean.id });
	}

	/**
	 * 删除下载数据库数据
	 */
	public synchronized static void DeleteDownLoadById(String id) {
		OperationDataBase helper = TApplication.getOperationDataBase();
		if (!TextUtils.isEmpty(id)) {
			helper.delete(true, DataBaseConfig.TABLE_DOWN,
					DataBaseConfig.DOWN_ID + " =? ", new String[] { id });
		} else {
			helper.delete(true, DataBaseConfig.TABLE_DOWN, null, null);
		}
	}

}
