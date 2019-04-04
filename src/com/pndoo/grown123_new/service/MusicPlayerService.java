package com.pndoo.grown123_new.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.os.RemoteException;

import com.pndoo.grown123_new.dto.base.AudioItem;
import com.skytree.epubtest.SPUtility;

/**
 * 播放歌曲
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class MusicPlayerService extends Service {
	private StringBuffer sbf;
	private String bookName;
	/**
	 * 音频准备好了，可以获取演唱者和歌曲名称了
	 */
	public static final String PREPARED_MESSAGE = "com.pndoo.grown123_new.PREPARED_MESSAGE";
	private IMusicPlayerService.Stub iBinder = new IMusicPlayerService.Stub() {

		MusicPlayerService playerService = MusicPlayerService.this;
		@Override
		public void setPlayMode(int playmode) throws RemoteException {
			playerService.setPlayMode(playmode);

		}

		@Override
		public void seeKTo(int position) throws RemoteException {
			playerService.seeKTo(position);

		}

		@Override
		public void pre() throws RemoteException {
			// playerService.pre();

		}

		@Override
		public void play() throws RemoteException {
			playerService.play();

		}

		@Override
		public void pause() throws RemoteException {
			playerService.pause();

		}

		@Override
		public void openAudio(int position) throws RemoteException {
			playerService.openAudio(position);

		}

		@Override
		public void next() throws RemoteException {
			// playerService.next();

		}

		@Override
		public int getPlayMode() throws RemoteException {
			return playerService.getPlayMode();
		}

		@Override
		public int getDuration() throws RemoteException {
			return playerService.getDuration();
		}

		@Override
		public int getCurrentPositon() throws RemoteException {
			return playerService.getCurrentPositon();
		}

		@Override
		public String getAudioName() throws RemoteException {
			return playerService.getAudioName();
		}

		@Override
		public String getArtistName() throws RemoteException {
			return playerService.getArtistName();
		}

		@Override
		public boolean isPlaying() throws RemoteException {
			return playerService.isPlaying();
		}

		@Override
		public void notifyChange(String notify) throws RemoteException {
			playerService.notifyChange(notify);

		}

		@Override
		public String getAudioPath() throws RemoteException {
			return playerService.getAudioPath();
		}

		@Override
		public void getAllAudio(String path) throws RemoteException {
			playerService.getAllAudio(path);

		}
	};
	/**
	 * 音频列表
	 */
	// private ArrayList<AudioItem> audioLists;
	/**
	 * 当前音频列表中的某个对象
	 */
	private AudioItem currentAudio;
	/**
	 * 当前播放列表中的位置
	 */
	private int currentPosition;
	private MediaPlayer mediaPlayer;
	/**
	 * 顺序播放
	 */
	public static final int REPEATE_NORMAL = 1;

	/**
	 * 单曲播放
	 */
	public static final int REPEATE_CURRENT = 2;

	/**
	 * 全部循环
	 */
	public static final int REPEATE_ALL = 3;

	private int playMode = REPEATE_NORMAL;

	// 接口和服务进行关联
	@Override
	public IBinder onBind(Intent intent) {
		return iBinder;
	}

	protected String getAudioPath() {
		if (currentAudio != null) {
			return currentAudio.getData();
		}
		return "";
	}

	/**
	 * 判断当前是否真正播放音乐
	 * 
	 * @return
	 */
	protected boolean isPlaying() {
		if (mediaPlayer != null) {
			return mediaPlayer.isPlaying();
		}
		return false;

	}

	private SharedPreferences sp;
	@Override
	public void onCreate() {
		super.onCreate();
		initData();

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		playMode = sp.getInt("playmode", REPEATE_NORMAL);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 得到手机里面的视频 Android系统：在开启完成后或者sdcard插入可用的时候 媒体扫描器
	 */
	public void getAllAudio(String path) {
		

	}
	/**
	 * 发消息
	 * 
	 * @param nofity
	 */
	private void notifyChange(String nofity) {
		Intent intent = new Intent();
		intent.setAction(nofity);
		sendBroadcast(intent);// 发广播
	}

	/**
	 * 播放音乐
	 */
	private void play() {
		if (mediaPlayer != null) {
			mediaPlayer.start();
			SPUtility.putSPString(getApplicationContext(), "isPlay", "true");
		}
	}
	/**
	 * 暂停音乐
	 */
	private void pause() {
		SPUtility.putSPString(getApplicationContext(), "isPlay", "false");
		if (mediaPlayer != null) {
			mediaPlayer.pause();
		}
		// 消掉状态栏的通知
//		stopForeground(true);
	}
	/**
	 * 根据位置打开一个音频
	 * 
	 * @param position
	 */
	private void openAudio(int position) {
		path = SPUtility.getSPString(this, "mPath");
		bookName = SPUtility.getSPString(this, "mName");
		currentAudio = new AudioItem();
		currentAudio.setData(path);
		// 释放资源
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}

		mediaPlayer = new MediaPlayer();
		try {
			if (currentAudio.getData() == null)
				return;
			mediaPlayer.setDataSource(currentAudio.getData());
			mediaPlayer.setOnPreparedListener(mOnPreparedListener);
			mediaPlayer.setOnCompletionListener(mOnCompletionListener);
			mediaPlayer.setOnErrorListener(mOnErrorListener);
			mediaPlayer.prepareAsync();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private OnPreparedListener mOnPreparedListener = new OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mp) {
			play();
			SPUtility.putSPString(getApplicationContext(), "isPlay", "true");
			notifyChange(PREPARED_MESSAGE);
		}
	};

	private OnCompletionListener mOnCompletionListener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			SPUtility.putSPString(getApplicationContext(), "isPlay", "false");
			// next();

		}
	};
	private OnErrorListener mOnErrorListener = new OnErrorListener() {

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			return false;
		}
	};

	private String path;

	/**
	 * 拖动视频
	 * 
	 * @param position
	 */
	private void seeKTo(int position) {
		if (mediaPlayer != null) {
			mediaPlayer.seekTo(position);
		}

	}
	/**
	 * 得到音频的名称
	 * 
	 * @return
	 */
	private String getAudioName() {
		if (currentAudio != null) {
			return currentAudio.getTitle();
		}
		return "";
	}

	/**
	 * 得到艺术家名字
	 * 
	 * @return
	 */
	private String getArtistName() {
		if (currentAudio != null) {
			return currentAudio.getArtist();
		}
		return "";
	}

	/**
	 * 得到总时长
	 * 
	 * @return
	 */
	private int getDuration() {
		if (mediaPlayer != null) {
			return mediaPlayer.getDuration();
		}
		return 0;
	}

	/**
	 * 得到当前播放进度
	 * 
	 * @return
	 */
	private int getCurrentPositon() {
		if (mediaPlayer != null) {
			return mediaPlayer.getCurrentPosition();
		}
		return 0;
	}

	/**
	 * 设置模式
	 * 
	 * @param playmode
	 */
	private void setPlayMode(int playmode) {
		this.playMode = playmode;
		Editor editor = sp.edit();
		editor.putInt("playmode", playMode);
		editor.commit();
	}
	/**
	 * 得到当前播放模式
	 * 
	 * @return
	 */
	private int getPlayMode() {
		return playMode;
	}
	
//	private void play() {
//	if (mediaPlayer != null) {
//		mediaPlayer.start();
//	}
//// 当播放音乐的时候，在状态栏显示播放音乐
//int icon = R.drawable.notification_music_playing;
//bookName = SPUtility.getSPString(this, "mName");
//CharSequence tickerText = "正在播放:" + bookName;
//long when = System.currentTimeMillis();	
//NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE); 
// Notification notification = new Notification(icon, tickerText , when
// );
//// 设置通知栏的属性-点击后还在，并没有消掉
// notification.flags = Notification.FLAG_ONGOING_EVENT;
// CharSequence contentText = "正在播放:"+bookName;
//	Intent intent = new Intent(MusicPlayerService.this, MediaPlayerActivity.class);
//	intent.putExtra("from_notification", true);
//	PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//	intent, 0);
// notification.setLatestEventInfo(this, "外研视听", contentText ,
// contentIntent );
// startForeground(1, notification);
//}	
//	public void getAllAudio(String path) {
//	try {
//		currentAudio = new AudioItem();
//		currentAudio.setData(path);
//
//		MP3File mp3File = (MP3File) AudioFileIO.read(new File(path));
//
//		Tag tag = mp3File.getTag();
//		sbf = new StringBuffer();
//		// sbf.append("歌手："+tag.getFirst(FieldKey.ARTIST) + "\n");
//		// currentAudio.setArtist(tag.getFirst(FieldKey.ARTIST));
//		// sbf.append("专辑名："+tag.getFirst(FieldKey.ALBUM) + "\n");
//		// sbf.append("歌名："+tag.getFirst(FieldKey.TITLE) + "\n");
//		currentAudio.setTitle(tag.getFirst(FieldKey.TITLE));
//		// sbf.append("年份："+tag.getFirst(FieldKey.YEAR));
//
//		MP3AudioHeader header = mp3File.getMP3AudioHeader();
//
//		sbf.append("长度: " + header.getTrackLength() + "\n");
//		sbf.append("精确的长度: " + header.getPreciseTrackLength() + "\n");
//		// sbf.append("比特率: " + header.getBitRate() + "\n");
//		// sbf.append("编码器: " + header.getEncoder()+"\n");
//		// sbf.append("格式: " + header.getFormat() + "\n");
//		// sbf.append("声道: " + header.getChannels() + "\n");
//		// sbf.append("采样率: " +header.getSampleRate() + "\n");
//		// sbf.append("MPEG: " + header.getMpegLayer() + "\n");
//		// sbf.append("MP3起始字节: "+header.getMp3StartByte() + "\n");
//
//		currentAudio.setDuration(header.getTrackLengthAsString());
//		currentAudio.setSize(header.getPreciseTrackLength() + "");
//
//		// sbf.append("帧数："+header.getNumberOfFrames()+ "\n");
//		// sbf.append("编码类型："+header.getEncodingType()+ "\n");
//		// sbf.append("MPEG版本:"+header.getMpegVersion()+ "\n");
//		Log.i("Mp3info", sbf.toString());
//		Log.e("audio", currentAudio.toString());

//	} catch (Exception e) {
//		e.printStackTrace();
//	}
	// 此处存在一个问题。至于是什么呢？
	// new Thread() {
	// public void run() {
	// // audioLists = new ArrayList<AudioItem>();
	// // 加载视频
	// ContentResolver resolver = getContentResolver();
	// Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	// String[] projection = {
	// MediaStore.Audio.Media.DISPLAY_NAME,
	// MediaStore.Audio.Media.DURATION,
	// MediaStore.Audio.Media.SIZE,
	// MediaStore.Audio.Media.DATA ,
	// MediaStore.Audio.Media.ARTIST
	// };
	// Cursor cursor = resolver.query(uri, projection, null,
	// null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	//
	// while(cursor.moveToNext()){
	// String data = cursor.getString(3);
	// if
	// (data.equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"wyst"+File.separator+"夏洛特烦恼.mp3"))
	// {
	// String size = cursor.getString(2);
	// String title = cursor.getString(0);
	// String duration = cursor.getString(1);
	// String artist = cursor.getString(4);
	// currentAudio.setData(data);
	// currentAudio.setTitle(title);
	// currentAudio.setDuration(duration);
	// currentAudio.setSize(size);
	// currentAudio.setArtist(artist);
	// }
	//
	//
	// // audioLists.add(audioItem);
	//
	//
	// }
	// cursor.close();
	// // handler.sendEmptyMessage(0);
	//
	// };
	// }.start();

//};
	// /**
	// * 播放下一个音频
	// */
	// private void next(){
	// setNextPosition();
	// setNextOpen();
	//
	// }
	// /**
	// * 更加位置，打开对应的音频并且播放
	// */
	// private void setNextOpen() {
	//
	// if(playMode == MusicPlayerService.REPEATE_NORMAL){
	// //顺序播放
	// if(audioLists != null && audioLists.size() >0){
	// if(currentPosition != audioLists.size()){
	// openAudio(currentPosition);
	// }
	// }
	// }else if(playMode == MusicPlayerService.REPEATE_CURRENT){
	// //单曲播放
	// openAudio(currentPosition);
	// }else if(playMode == MusicPlayerService.REPEATE_ALL){
	// //全部循环
	// if(audioLists != null && audioLists.size() >0){
	// openAudio(currentPosition);
	// }
	// }else{
	// //顺序循环
	// //顺序播放
	// if(audioLists != null && audioLists.size() >0){
	// openAudio(currentPosition);
	// }
	// }
	//
	//
	//
	//
	// }

	// /**
	// * 根据不同的模式，得到下一个播放的位置
	// */
	// private void setNextPosition() {
	// if(playMode == MusicPlayerService.REPEATE_NORMAL){
	// //顺序播放
	// if(audioLists != null && audioLists.size() >0){
	// currentPosition ++;
	// //屏蔽非法值
	// if(currentPosition >audioLists.size()-1){
	// currentPosition = audioLists.size()-1;
	// }
	//
	// }
	// }else if(playMode == MusicPlayerService.REPEATE_CURRENT){
	// //单曲播放
	// }else if(playMode == MusicPlayerService.REPEATE_ALL){
	// //全部循环
	// if(audioLists != null && audioLists.size() >0){
	// currentPosition ++;
	// //屏蔽非法值
	// if(currentPosition >audioLists.size()-1){
	// currentPosition = 0;
	// }
	//
	// }
	// }else{
	// //顺序循环
	// //顺序播放
	// if(audioLists != null && audioLists.size() >0){
	// currentPosition ++;
	// //屏蔽非法值
	// if(currentPosition >audioLists.size()-1){
	// currentPosition = audioLists.size()-1;
	// }
	//
	// }
	// }
	//
	//
	// }

	// /**
	// * 播放上一个音频
	// */
	// private void pre(){
	// setPrePosition();
	// setPreOpen();
	// }

	// private void setPreOpen() {
	//
	//
	// if(playMode == MusicPlayerService.REPEATE_NORMAL){
	// //顺序播放
	// if(audioLists != null && audioLists.size() >0){
	// if(currentPosition != audioLists.size()){
	// openAudio(currentPosition);
	// }
	// }
	// }else if(playMode == MusicPlayerService.REPEATE_CURRENT){
	// //单曲播放
	// openAudio(currentPosition);
	// }else if(playMode == MusicPlayerService.REPEATE_ALL){
	// //全部循环
	// if(audioLists != null && audioLists.size() >0){
	// openAudio(currentPosition);
	// }
	// }else{
	// //顺序循环
	// //顺序播放
	// if(audioLists != null && audioLists.size() >0){
	// openAudio(currentPosition);
	// }
	// }
	//
	//
	//
	//
	//
	//
	// }

	// private void setPrePosition() {
	//
	// if(playMode == MusicPlayerService.REPEATE_NORMAL){
	// //顺序播放
	// if(audioLists != null && audioLists.size() >0){
	// currentPosition --;
	// //屏蔽非法值
	// if(currentPosition < 0){
	// currentPosition = 0;
	// }
	//
	// }
	// }else if(playMode == MusicPlayerService.REPEATE_CURRENT){
	// //单曲播放
	// }else if(playMode == MusicPlayerService.REPEATE_ALL){
	// //全部循环
	// if(audioLists != null && audioLists.size() >0){
	// currentPosition --;
	// //屏蔽非法值
	// if(currentPosition < 0){
	// currentPosition = audioLists.size()-1;
	// }
	//
	// }
	// }else{
	// //顺序循环
	// //顺序播放
	// if(audioLists != null && audioLists.size() >0){
	// currentPosition --;
	// //屏蔽非法值
	// if(currentPosition < 0){
	// currentPosition = 0;
	// }
	//
	// }
	// }
	//
	//
	//
	//
	// }

}
