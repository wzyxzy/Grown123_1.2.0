package com.pndoo.grown123_new;

import android.app.Activity;

public class MediaPlayerActivity extends Activity {

//	private String path = "";
//	private String bookName;
//	private TextView start_time;
//	private SeekBar seekBar_audio;
//	private TextView end_time;
//	private ImageButton btn_pre;
//	private ImageButton btn_play;
//	private ImageButton btn_next;
//	private TextView tv_header_title;
//	private ImageButton btn_header_right;
//	private int mediaplayer_pause;
//	private int mediaplayer_play;
//	protected static final int PROGRESS = 1;
//	protected static final int LYRIC_SHOW = 2;
//
//	private LyricUtils lyricUtils;
//
//	private LrcView mLrcView;
//	/**
//	 * 服务代理对象
//	 */
//	private com.jiayue.service.IMusicPlayerService service;
//	/**
//	 * 在音频列表中播放那一个
//	 */
//	private int currentPosition;
//
//	private MyBroadcastReceiver receiver;
//	private boolean isStoped = false;
//	/**
//	 * true:表示进入这个Activity状态栏进来 flase:播放列表进来
//	 */
//	private boolean from_notification;
//	private MusicUtils utils;
//	private Handler handler = new Handler(new Handler.Callback() {
//
//		@Override
//		public boolean handleMessage(Message msg) {
//			switch (msg.what) {
//				case PROGRESS :
//					// 得到当前播放进度
//					try {
//						int currentPosition = service.getCurrentPositon();
//						mLrcView.seekTo(currentPosition, true, true);
//						seekBar_audio.setProgress(currentPosition);
//						start_time.setText(utils.stringForTime(service
//								.getCurrentPositon()));
//						end_time.setText(utils.stringForTime(service
//								.getDuration()));
//
//					} catch (RemoteException e) {
//						e.printStackTrace();
//					}
//					if (!isStoped) {
//						handler.sendEmptyMessageDelayed(PROGRESS, 1000);
//					}
//
//					break;
//				case LYRIC_SHOW :
////					int currentTime;
////					try {
////						currentTime = service.getCurrentPositon();
////						// mLrcView.setShowNextLyric(currentTime);
////					} catch (RemoteException e) {
////						e.printStackTrace();
////					}
//
//					if (!isStoped) {
//						handler.removeMessages(LYRIC_SHOW);
//						// showLyric();
//					}
//
//					break;
//
//				default :
//					break;
//
//			}
//			return false;
//		}
//	});
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		// 加载解码器
//		if (!Vitamio.isInitialized(this))
//			return;
//
//		setContentView(R.layout.activity_music_player);
//		if (getIntent().getStringExtra("filePath") != null
//				&& (!getIntent().getStringExtra("filePath").equals(""))) {
//			path = getIntent().getStringExtra("filePath");
//			SPUtility.putSPString(this, "mPath", path);
//		}
//
//		if (getIntent().getStringExtra("bookName") != null) {
//			bookName = getIntent().getStringExtra("bookName");
//			SPUtility.putSPString(this, "mName", bookName);
//		}
//		initView();
//		startService();
//		getData();
//		setOnlistener();
//	}
//	private OnClickListener mOnClickListener = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			// int getmCurRow = mLrcView.getmCurRow();
//			switch (v.getId()) {
//				case R.id.btn_pre :
//					// mLrcView.setmCurRow(getmCurRow-1);
//					// try {
//					// service.pre();
//					// } catch (RemoteException e1) {
//					// e1.printStackTrace();
//					// }
//					break;
//				case R.id.btn_next :
//
//					// mLrcView.setmCurRow(getmCurRow+1);
//					// try {
//					// service.next();
//					// } catch (RemoteException e1) {
//					// e1.printStackTrace();
//					// }
//					break;
//				case R.id.btn_play :// 播放和暂停
//
//					if (service != null) {
//						try {
//							if (service.isPlaying()) {
//								service.pause();
//							} else {
//								service.play();
//							}
//							setPlayOrPauseStatus();
//						} catch (RemoteException e) {
//							e.printStackTrace();
//						}
//					}
//
//					break;
//
//				default :
//					break;
//			}
//
//		}
//	};
//	private void getData() {
//		utils = new MusicUtils();
//		lyricUtils = new LyricUtils();
//		currentPosition = getIntent().getIntExtra("currentPosition", 0);
//		from_notification = getIntent().getBooleanExtra("from_notification",
//				false);
//
//		// 注册广播
//		IntentFilter filter = new IntentFilter();
//		receiver = new MyBroadcastReceiver();
//		filter.addAction(MusicPlayerService.PREPARED_MESSAGE);
//		registerReceiver(receiver, filter);
//
//	}
//	/**
//	 * 绑定服务
//	 */
//
//	private void startService() {
//
//		Bundle extras = new Bundle();
//		Intent intent = new Intent();
//		intent.setAction("com.jiayue.startservice");
//		intent.putExtras(extras);
//		bindService(intent, conn, Context.BIND_AUTO_CREATE);
//		startService(intent);
//
//	}
//	/**
//	 * 设置按钮监听
//	 */
//	private void setOnlistener() {
//		btn_play.setOnClickListener(mOnClickListener);
//		btn_next.setOnClickListener(mOnClickListener);
//		btn_pre.setOnClickListener(mOnClickListener);
//
//		seekBar_audio.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//
//			@Override
//			public void onStopTrackingTouch(SeekBar seekBar) {
//			}
//			@Override
//			public void onStartTrackingTouch(SeekBar seekBar) {
//			}
//			@Override
//			public void onProgressChanged(SeekBar seekBar, int progress,
//					boolean fromUser) {
//				if (fromUser) {
//					try {
//						service.seeKTo(progress);
//					} catch (RemoteException e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//		});
//
//	}
//
//	/**
//	 * 设置播放和暂停按钮状态
//	 */
//	protected void setPlayOrPauseStatus() {
//		if (service != null) {
//			try {
//				if (service.isPlaying()) {
//					// 暂停
//					
//					btn_play.setBackgroundResource(mediaplayer_pause);
//				} else {
//					// 播放
//					btn_play.setBackgroundResource(mediaplayer_play);
//				}
//
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//	private class MyBroadcastReceiver extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			if (intent.getAction().equals(MusicPlayerService.PREPARED_MESSAGE)) {
//				// 音频是播起来了
//				setViewStatus();
//			}
//		}
//	}
//	private void initView() {
//
//		start_time = (TextView) findViewById(R.id.start_time);
//		seekBar_audio = (SeekBar) findViewById(R.id.seekBar_audio);
//		end_time = (TextView) findViewById(R.id.end_time);
//		btn_pre = (ImageButton) findViewById(R.id.btn_pre);
//		btn_play = (ImageButton) findViewById(R.id.btn_play);
//		mediaplayer_pause = getResources().getIdentifier("mediaplayer_pause",
//				"drawable", getPackageName());
//		mediaplayer_play = getResources().getIdentifier("mediaplayer_play",
//				"drawable", getPackageName());
//		btn_next = (ImageButton) findViewById(R.id.btn_next);
//		mLrcView = (LrcView) findViewById(R.id.show_lyric_view);
//		mLrcView.setOnSeekToListener(new com.jiayue.lrcview.LrcView.OnSeekToListener() {
//
//			@Override
//			public void onSeekTo(int progress) {
//				try {
//					service.seeKTo(progress);
//				} catch (RemoteException e) {
//					e.printStackTrace();
//				}
//
//			}
//		});
//		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
//		tv_header_title.setText(bookName);
//		btn_header_right = (ImageButton) findViewById(R.id.btn_header_right);
//		btn_header_right.setVisibility(View.VISIBLE);
//
//		btn_header_right.setBackgroundResource(getResources().getIdentifier(
//				"mediaplayer_hide", "drawable", getPackageName()));
//		btn_header_right.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				moveTaskToBack(true);
//			}
//		});
//	}
//	private void showLyric() {
//
//		try {
//			String lrcpath = service.getAudioPath();// mnt/sdcard/video/beijingbeijing.mp3
//
//			if (!TextUtils.isEmpty(lrcpath)) {
//				lrcpath = lrcpath.substring(0, lrcpath.lastIndexOf("."));// mnt/sdcard/video/beijingbeijing
//			}
//			File file = new File(lrcpath + ".lrc");
//			if (!file.exists()) {
//				file = new File(lrcpath + ".txt");
//			}
//			// lyricUtils.readLyricFile(file);
//			// mLrcView.setLyrics(lyricUtils.getLyrics());
//			mLrcView.setLrcRows((List<LrcRow>) getLrcRows(file));
//			if (lyricUtils.isExistLyric()) {
//				handler.sendEmptyMessage(LYRIC_SHOW);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 获取歌词List集合
//	 * 
//	 * @return
//	 */
//	private List<LrcRow> getLrcRows(File file) {
//		List<LrcRow> rows = new ArrayList<LrcRow>();
//		String line = "";
//		StringBuffer sb = new StringBuffer();
//		BufferedReader reader = null;
//		try {
//			reader = new BufferedReader(new InputStreamReader(
//					new BufferedInputStream(new FileInputStream(file)),
//					getCharset(file)));
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + "\n");
//			}
//			System.out.println(sb.toString());
//			rows = DefaultLrcParser.getIstance().getLrcRows(sb.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (reader != null) {
//					reader.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return rows;
//	}
//
//	/**
//	 * 判断文件编码
//	 * 
//	 * @param file
//	 *            文件
//	 * @return 编码：GBK,UTF-8,UTF-16LE
//	 */
//	public String getCharset(File file) {
//		String charset = "GBK";
//		byte[] first3Bytes = new byte[3];
//		BufferedInputStream bis = null;
//		try {
//			boolean checked = false;
//			bis = new BufferedInputStream(new FileInputStream(file));
//			bis.mark(0);
//			int read = bis.read(first3Bytes, 0, 3);
//			if (read == -1)
//				return charset;
//			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
//				charset = "UTF-16LE";
//				checked = true;
//			} else if (first3Bytes[0] == (byte) 0xFE
//					&& first3Bytes[1] == (byte) 0xFF) {
//				charset = "UTF-16BE";
//				checked = true;
//			} else if (first3Bytes[0] == (byte) 0xEF
//					&& first3Bytes[1] == (byte) 0xBB
//					&& first3Bytes[2] == (byte) 0xBF) {
//				charset = "UTF-8";
//				checked = true;
//			}
//			bis.reset();
//			if (!checked) {
//				// int loc = 0;
//				while ((read = bis.read()) != -1) {
//					// loc++;
//					if (read >= 0xF0)
//						break;
//					if (0x80 <= read && read <= 0xBF)
//						break;
//					if (0xC0 <= read && read <= 0xDF) {
//						read = bis.read();
//						if (0x80 <= read && read <= 0xBF)
//							continue;
//						else
//							break;
//					} else if (0xE0 <= read && read <= 0xEF) {
//						read = bis.read();
//						if (0x80 <= read && read <= 0xBF) {
//							read = bis.read();
//							if (0x80 <= read && read <= 0xBF) {
//								charset = "UTF-8";
//								break;
//							} else
//								break;
//						} else
//							break;
//					}
//				}
//			}
//			bis.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (bis != null) {
//					bis.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return charset;
//	}
//
//	public void setViewStatus() {
//		try {
//			// tv_artist.setText(service.getArtistName());
//			// tv_name.setText(service.getAudioName());
//			// 设置seeKBar的总长度
//			seekBar_audio.setMax(service.getDuration());
//
//			start_time
//					.setText(utils.stringForTime(service.getCurrentPositon()));
//			end_time.setText(utils.stringForTime(service.getDuration()));
//			handler.sendEmptyMessage(PROGRESS);
//			showLyric();
//
//			// setPlayModeButtonStatus();
//
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private ServiceConnection conn = new ServiceConnection() {
//
//		// 当取消绑定服务成功后，会回调这个方法
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			service = null;
//
//		}
//		// 当绑定服务成功后，会回调--
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder ibinder) {
//			// 代表服务
//			service = IMusicPlayerService.Stub.asInterface(ibinder);
//			try {
//				if (!from_notification) {
//					service.openAudio(currentPosition);
//
//				} else {
//					service.notifyChange(MusicPlayerService.PREPARED_MESSAGE);
//				}
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//
//		}
//	};
//
//
//	public void btnBack(View v) {
//		if (service != null) {
//			try {
//				service.pause();
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		finish();
//	}
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		if (service != null) {
//			try {
//				service.pause();
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public void onBackPressed() {
//		if (service != null) {
//			try {
//				service.pause();
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		super.onBackPressed();
//	}
//	@Override
//	protected void onDestroy() {
//
//		if (service != null) {
//			try {
//				service.pause();
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		if (receiver != null) {
//			unregisterReceiver(receiver);
//			receiver = null;
//		}
//		isStoped = true;
//		ActivityUtils.deleteBookFormSD(path);
//		super.onDestroy();
//	}
}
