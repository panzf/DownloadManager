package com.down;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.down.bean.DownLoadBean;
import com.down.executors.DownLoadManager;
import com.down.executors.DownLoadObserver;
import com.down.uitl.DataBaseUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	/** 列表控件 */
	public ListView listview;
	/** 列表数据bean */
	public ArrayList<DownLoadBean> list;

	public DownAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		list = new ArrayList<DownLoadBean>();
		Test();
		listview = (ListView) findViewById(R.id.listview);
		adapter = new DownAdapter(MainActivity.this, list);
		listview.setAdapter(adapter);
	}

	// 测试数据
	private void Test() {
		DownLoadBean bean1 = new DownLoadBean();
		bean1.id = "3210158";
		bean1.appName = "剑与魔法";
		bean1.appSize = 252821785;
		bean1.appIcon = "http://p1.qhimg.com/dr/160_160_/t0170f68197b3c8efe9.png";
		bean1.url = "http://m.shouji.360tpcdn.com/160315/168f6b5f7e38b95f8d7dcce94076acc4/com.longtugame.jymf.qihoo_22.apk";

		DownLoadBean bean2 = new DownLoadBean();
		bean2.id = "2981222";
		bean2.appName = "花椒-直播App";
		bean2.appSize = 17699443;
		bean2.appIcon = "http://p1.qhimg.com/dr/160_160_/t01c52b0ee594a7f507.png";
		bean2.url = "http://m.shouji.360tpcdn.com/160318/a043152dd8789131a12c5beeb7e42e34/com.huajiao_4071059.apk";

		DownLoadBean bean3 = new DownLoadBean();
		bean3.id = "21972";
		bean3.appName = "唯品会";
		bean3.appSize = 33411097;
		bean3.appIcon = "http://p1.qhimg.com/dr/160_160_/t016c539aa97fdef5bf.png";
		bean3.url = "http://m.shouji.360tpcdn.com/160310/5aae1072a87bf4cef0ccec0e17999d27/com.achievo.vipshop_436.apk";

		DownLoadBean bean4 = new DownLoadBean();
		bean4.id = "1625930";
		bean4.appName = "开心消消乐";
		bean4.appSize = 67771094;
		bean4.appIcon = "http://p1.qhimg.com/dr/160_160_/t01fbaee14a2b65be0f.png";
		bean4.url = "http://m.shouji.360tpcdn.com/160310/ca3b2c5ab347fc988dde0325e6f7c658/com.happyelements.AndroidAnimal_31.apk";

		DownLoadBean bean5 = new DownLoadBean();
		bean5.id = "8043";
		bean5.appName = "阿里旅行";
		bean5.appSize = 33840292;
		bean5.appIcon = "http://p1.qhimg.com/dr/160_160_/t01c513232212e2d915.png";
		bean5.url = "http://m.shouji.360tpcdn.com/160317/0a2c6811b5fc9bada8e7e082fb5a9324/com.taobao.trip_3001049.apk";

		DownLoadBean bean6 = new DownLoadBean();
		bean6.id = "65533";
		bean6.appName = "苏宁易购";
		bean6.appSize = 27854306;
		bean6.appIcon = "http://p1.qhimg.com/dr/160_160_/t01f9b42c0addddd698.png";
		bean6.url = "http://m.shouji.360tpcdn.com/160316/deab26b43b55089736817040f921c1e7/com.suning.mobile.ebuy_120.apk";

		list.add(bean1);
		list.add(bean2);
		list.add(bean3);
		list.add(bean4);
		list.add(bean5);
		list.add(bean6);

		ArrayList<DownLoadBean> list_lin = DataBaseUtil.getDownLoad();
		for (int i = 0; i < list_lin.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list_lin.get(i).id.equals(list.get(j).id)) {
					list.remove(j);
					File file = new File(list_lin.get(i).getPath());
					if (file.exists()) {
						list_lin.get(i).currentSize = file.length();
					}
					list.add(j, list_lin.get(i));
					break;
				}
			}
		}
	}
	@Override
	protected void onResume() {
		Log.i("TAG", "onResume");
		super.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		DownLoadManager.getInstance().destory();
		super.onDestroy();
	}

	/**
	 * 换算文件的大小
	 */
	public String FormetFileSize(long fileSize) {// 转换文件大小
		if (fileSize <= 0) {
			return "0M";
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileSize < 1024) {
			fileSizeString = df.format((double) fileSize) + "B";
		} else if (fileSize < 1048576) {
			fileSizeString = df.format((double) fileSize / 1024) + "K";
		} else if (fileSize < 1073741824) {
			fileSizeString = df.format((double) fileSize / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileSize / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public class DownAdapter extends BaseAdapter {

		public ArrayList<DownLoadBean> list;
		public Context context;

		public DownAdapter(Context context, ArrayList<DownLoadBean> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHolder;
			final DownLoadBean bean = list.get(position);
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_down, null);
				viewHolder.txt_name = (TextView) convertView
						.findViewById(R.id.txt_name);
				viewHolder.txt_state = (TextView) convertView
						.findViewById(R.id.txt_state);
				viewHolder.btn_delete = (Button) convertView
						.findViewById(R.id.btn_delete);
				viewHolder.txt_file_size = (TextView) convertView
						.findViewById(R.id.txt_file_size);
				viewHolder.seekbar = (SeekBar) convertView
						.findViewById(R.id.seekbar);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.txt_name.setText(bean.appName);
			viewHolder.txt_file_size.setText(FormetFileSize(bean.currentSize)
					+ " / " + FormetFileSize(bean.appSize));
			
			if(bean.downloadState == DownLoadManager.STATE_NONE){
				viewHolder.txt_state.setText("添加下载");
			}else if (bean.downloadState == DownLoadManager.STATE_START) {
				viewHolder.txt_state.setText("开始下载");
			} else if (bean.downloadState == DownLoadManager.STATE_WAITING) {
				viewHolder.txt_state.setText("排队等待中");
			} else if (bean.downloadState == DownLoadManager.STATE_DOWNLOADING) {
				viewHolder.txt_state.setText("下载中");
			} else if (bean.downloadState == DownLoadManager.STATE_PAUSED) {
				viewHolder.txt_state.setText("暂停中");
			} else if (bean.downloadState == DownLoadManager.STATE_DOWNLOADED) {
				viewHolder.txt_state.setText("下载完成");
			} else if (bean.downloadState == DownLoadManager.STATE_ERROR) {
				viewHolder.txt_state.setText("下载失败，请重新下载");
			}

			viewHolder.seekbar.setProgress((int) ((float) bean.currentSize
					/ (float) bean.appSize * 100f));
			viewHolder.seekbar.setMax(100);

			DownLoadManager.getInstance().registerObserver(bean.id,
					new DownLoadObserver() {
						@Override
						public void onStop(DownLoadBean bean) {
							viewHolder.txt_state.setText("暂停中");
						}

						@Override
						public void onStart(DownLoadBean bean) {
							viewHolder.txt_state.setText("开始下载");
						}

						@Override
						public void onProgress(DownLoadBean bean) {
							viewHolder.txt_file_size
									.setText(FormetFileSize(bean.currentSize)
											+ " / "
											+ FormetFileSize(bean.appSize));
							viewHolder.txt_state.setText("下载中");
							viewHolder.seekbar
									.setProgress((int) ((float) bean.currentSize
											/ (float) bean.appSize * 100f));
						}

						@Override
						public void onPrepare(DownLoadBean bean) {
							viewHolder.txt_state.setText("排队等待中");
						}

						@Override
						public void onFinish(DownLoadBean bean) {
							viewHolder.txt_state.setText("下载完成");
						}

						@Override
						public void onError(DownLoadBean bean) {
							viewHolder.txt_state.setText("下载失败，请重新下载");
						}

						@Override
						public void onDelete(DownLoadBean bean) {
							// 删除成功之后的接口
							viewHolder.txt_state.setText("点击下载");
							viewHolder.txt_file_size.setText(FormetFileSize(0)
									+ " / " + FormetFileSize(bean.appSize));
							viewHolder.seekbar.setProgress(0);
						}

						@Override
						public void onEuqueue(DownLoadBean bean) {
							viewHolder.txt_state.setText("添加下载");		
						}
					});

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 开启下载
					DownLoadManager.getInstance().download(bean);
				}
			});

			viewHolder.btn_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 删除当前任务
					DownLoadManager.getInstance().DeleteDownTask(bean);
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private TextView txt_name;
			private Button btn_delete;
			private TextView txt_state;
			private TextView txt_file_size;
			private SeekBar seekbar;
		}
	}
}
