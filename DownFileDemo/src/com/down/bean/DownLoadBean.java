package com.down.bean;


import com.down.TApplication;
import com.down.executors.DownLoadManager;

public class DownLoadBean {
	public String id;//app的id，和appInfo中的id对应
	public String appName;//app的软件名称
	public String appIcon;//app的图片
	public long appSize = 0;//app的size
	public long currentSize = 0;//当前的size
	public int downloadState = DownLoadManager.STATE_NONE;//下载的状态
	public String url;//下载地址
	private String path ;//保存路径
	
	public String getPath() {
		path = TApplication.PATH_COMPLETE + appName + ".apk";
		return path ;
	}
}
