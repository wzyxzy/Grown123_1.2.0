//package com.pndoo.grown123_new;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.ContextMenu;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.pndoo.grown123_new.chat.ChatAdapter;
//import com.pndoo.grown123_new.chat.customer.ChatInput;
//import com.pndoo.grown123_new.chat.customer.VoiceSendingView;
//import com.pndoo.grown123_new.chat.login.LoginBusiness;
//import com.pndoo.grown123_new.chat.model.ImageMessage;
//import com.pndoo.grown123_new.chat.model.Message;
//import com.pndoo.grown123_new.chat.model.MessageFactory;
//import com.pndoo.grown123_new.chat.model.TextMessage;
//import com.pndoo.grown123_new.chat.model.VideoMessage;
//import com.pndoo.grown123_new.chat.model.VoiceMessage;
//import com.pndoo.grown123_new.chat.presenter.ChatPresenter;
//import com.pndoo.grown123_new.chat.presenter.ChatView;
//import com.pndoo.grown123_new.chat.util.FileUtil;
//import com.pndoo.grown123_new.chat.util.RecorderUtil;
//import com.pndoo.grown123_new.dto.base.ZhiboInfoBean;
//import com.pndoo.grown123_new.util.ActivityUtils;
//import com.pndoo.grown123_new.util.DensityUtil;
//import com.pndoo.grown123_new.util.DialogUtils;
//import com.pndoo.grown123_new.util.MyPreferences;
//import com.pndoo.grown123_new.util.SPUtility;
//import com.tencent.TIMCallBack;
//import com.tencent.TIMConversationType;
//import com.tencent.TIMGroupManager;
//import com.tencent.TIMMessage;
//import com.tencent.openqq.protocol.imsdk.log_upload_pb;
//import com.tencent.qcload.playersdk.ui.UiChangeInterface;
//import com.tencent.qcload.playersdk.ui.VideoRootFrame;
//import com.tencent.qcload.playersdk.util.PlayerListener;
//import com.tencent.qcload.playersdk.util.VideoInfo;
//import com.umeng.analytics.MobclickAgent;
//
//public class PlayActivity_old extends FragmentActivity implements ChatView {
//	private final String TAG = "PlayActivity";
//	private VideoRootFrame player;
//	private TextView title;
//	private LinearLayout layout_title, layout;
//	private ZhiboInfoBean bean;
//
//	private List<Message> messageList = new ArrayList<Message>();
//	private ChatAdapter adapter;
//	private ListView listView;
//	private ChatPresenter presenter;
//	private ChatInput input;
//	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
//	private static final int IMAGE_STORE = 200;
//	private Uri fileUri;
//	private VoiceSendingView voiceSendingView;
//	private String identify;
//	private RecorderUtil recorder = new RecorderUtil();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_play);
//		player = (VideoRootFrame) findViewById(R.id.player);
//		title = (TextView) findViewById(R.id.title);
//		layout_title = (LinearLayout) findViewById(R.id.layout_title);
//		layout = (LinearLayout) findViewById(R.id.layout);
//		// List<TitleMenu> videoTitleMenus=new ArrayList<TitleMenu>();
//		// TitleMenu icon1=new TitleMenu();
//		// icon1.iconId=R.drawable.ic_share;
//		// icon1.action=new PlayerActionInterface(){
//		// @Override
//		// public void action() {
//		// Toast.makeText(MainActivity.this,"share icon taped",Toast.LENGTH_SHORT).show();
//		// }
//		// };
//		// videoTitleMenus.add(icon1);
//		// TitleMenu icon2=new TitleMenu();
//		// icon2.iconId=R.drawable.ic_favorite;
//		// videoTitleMenus.add(icon2);
//		// TitleMenu icon3=new TitleMenu();
//		// icon3.iconId=R.drawable.ic_perm_identity;
//		// videoTitleMenus.add(icon3);
//		// player.setMenu(videoTitleMenus);
//
//		if (ActivityUtils.isNetworkAvailable(this)) {
//			if (ActivityUtils.is3rd(this) && Boolean.parseBoolean(SPUtility.getSPString(this, "switchKey"))) {
//
//			} else if (ActivityUtils.is3rd(this) && !Boolean.parseBoolean(SPUtility.getSPString(this, "switchKey"))) {
//				DialogUtils.showMyDialog(this, MyPreferences.SHOW_ERROR_DIALOG, "网络未连接", "请在wifi状态下观看，土豪请在设置中设置3G网络", new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						finish();
//					}
//				});
//			} else {
//
//			}
//		} else {
//			DialogUtils.showMyDialog(this, MyPreferences.SHOW_ERROR_DIALOG, "网络不给力", "请检查网络！", new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					finish();
//				}
//			});
//		}
//
//		bean = (ZhiboInfoBean) getIntent().getSerializableExtra("bean");
//		if (bean == null || bean.getCode().equals("FAIL") || bean.getData() == null) {
//			ActivityUtils.showToastForFail(this, "直播信息获取失败！");
//			finish();
//		}
//		title.setText(bean.getData().getChannel_name());
//		List<VideoInfo> videos = new ArrayList<VideoInfo>();
//		String path = bean.getData().getHls_downstream_address();
//		path = path.replace(".m3u8", "_550.m3u8");
//		VideoInfo v1 = new VideoInfo();
//		v1.description = "标清";
//		v1.type = VideoInfo.VideoType.HLS;
//		// v1.url="http://tidoots-10010046.video.myqcloud.com/ts/video/ios45377622470173511.mp4.f20.mp4";
//		v1.url = path;
//		Log.d(TAG, "550--------" + path);
//		videos.add(v1);
//
//		path = path.replace("_550.m3u8", "_900.m3u8");
//		VideoInfo v2 = new VideoInfo();
//		v2.description = "高清";
//		v2.type = VideoInfo.VideoType.HLS;
//		v2.url = path;
//		Log.d(TAG, "900--------" + path);
//		videos.add(v2);
//		player.setListener(new PlayerListener() {
//
//			@Override
//			public void onError(Exception arg0) {
//				arg0.printStackTrace();
//
//			}
//
//			/**
//			 * 返回码 具体含义 1 STATE_IDLE 播放器空闲，既不在准备也不在播放 2 STATE_PREPARING 播放器正在准备
//			 * 3 STATE_BUFFERING 播放器已经准备完毕，但无法立即播放。此状态
//			 * 的原因有很多，但常见的是播放器需要缓冲更多数据才能开始播放 4 STATE_PAUSE 播放器准备好并可以立即播放当前位置 5
//			 * STATE_PLAY 播放器正在播放中 6 STATE_ENDED 播放已完毕
//			 */
//			@Override
//			public void onStateChanged(int arg0) {
//				Log.d(TAG, "player states:" + arg0);
//				if (arg0 == 2) {
//					DialogUtils.showMyDialog(PlayActivity_old.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "缓存中...", null);
//				} else if (arg0 == 5) {
//					DialogUtils.dismissMyDialog();
//				} else if (arg0 == 6) {
//					player.release();
//					// List<VideoInfo> videos = new ArrayList<VideoInfo>();
//					// VideoInfo v1 = new VideoInfo();
//					// v1.description = "标清";
//					// v1.type = VideoInfo.VideoType.MP4;
//					// v1.url =
//					// "http://2527.vod.myqcloud.com/2527_a711716aa25a11e587a7251ce7af8a28.f20.mp4";
//					// videos.add(v1);
//					// player.play(videos);
//				}
//
//			}
//
//		});
//		player.play(videos);
//		player.setToggleFullScreenHandler(new UiChangeInterface() {
//			@Override
//			public void OnChange() {
//				if (player.isFullScreen()) {
//					Log.d(TAG, "-----------------player.isFullScreen()!!!!!!!!!!!!");
//					layout_title.setVisibility(View.VISIBLE);
//					layout.setPadding(dip2px(13), dip2px(7), dip2px(13), dip2px(7));
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//				} else {
//					Log.d(TAG, "---------------player.isFullScreen()");
//					layout_title.setVisibility(View.GONE);
//					layout.setPadding(0, 0, 0, 0);
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//				}
//			}
//		});
//
//		initChatRoom();
//
//	}
//
//	private void initChatRoom() {
//		// TODO Auto-generated method stub
//		identify = getIntent().getStringExtra("identify");
//		final TIMConversationType type = (TIMConversationType) getIntent().getSerializableExtra("type");
//		presenter = new ChatPresenter(this, identify, type);
//		input = (ChatInput) findViewById(R.id.input_panel);
//		input.setChatView(this);
//		adapter = new ChatAdapter(this, R.layout.item_message, messageList);
//		listView = (ListView) findViewById(R.id.list);
//		listView.setAdapter(adapter);
//		listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
//		listView.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//					case MotionEvent.ACTION_DOWN:
//						input.setInputMode(ChatInput.InputMode.NONE);
//						break;
//				}
//				return false;
//			}
//		});
//		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//			private int firstItem;
//
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && firstItem == 0) {
//					// 如果拉到顶端读取更多消息
//					presenter.getMessage(messageList.size() > 0 ? messageList.get(0).getMessage() : null);
//
//				}
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//				firstItem = firstVisibleItem;
//			}
//		});
//		registerForContextMenu(listView);
//		// TemplateTitle title = (TemplateTitle) findViewById(R.id.chat_title);
//		// switch (type) {
//		// case C2C:
//		// title.setMoreImg(R.drawable.btn_person);
//		// if (FriendshipInfo.getInstance().isFriend(identify)){
//		// title.setMoreImgAction(new View.OnClickListener() {
//		// @Override
//		// public void onClick(View v) {
//		// Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
//		// intent.putExtra("identify", identify);
//		// startActivity(intent);
//		// }
//		// });
//		// FriendProfile profile =
//		// FriendshipInfo.getInstance().getProfile(identify);
//		// title.setTitleText(profile == null ? identify : profile.getName());
//		// }else{
//		// title.setMoreImgAction(new View.OnClickListener() {
//		// @Override
//		// public void onClick(View v) {
//		// Intent person = new
//		// Intent(ChatActivity.this,AddFriendActivity.class);
//		// person.putExtra("id",identify);
//		// person.putExtra("name",identify);
//		// startActivity(person);
//		// }
//		// });
//		// title.setTitleText(identify);
//		// }
//		// break;
//		// case Group:
//		// title.setMoreImg(R.drawable.btn_group);
//		// title.setMoreImgAction(new View.OnClickListener() {
//		// @Override
//		// public void onClick(View v) {
//		// Intent intent = new Intent(ChatActivity.this,
//		// GroupProfileActivity.class);
//		// intent.putExtra("identify", identify);
//		// startActivity(intent);
//		// }
//		// });
//		// title.setTitleText(GroupInfo.getInstance().getGroupName(identify));
//		// break;
//		//
//		// }
//		voiceSendingView = (VoiceSendingView) findViewById(R.id.voice_sending);
//		presenter.start();
//	}
//
//	public void btnBack(View view) {
//		if (DialogUtils.dialog != null) {
//			DialogUtils.dismissMyDialog();
//		} else {
//			finish();
//		}
//	}
//
//	private int dip2px(int dpValue) {
//		return DensityUtil.dip2px(this, dpValue);
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if(keyCode == event.KEYCODE_BACK){
//			if (DialogUtils.dialog != null) {
//				DialogUtils.dismissMyDialog();
//			} else {
//				if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//					layout_title.setVisibility(View.VISIBLE);
//					layout.setPadding(dip2px(13), dip2px(7), dip2px(13), dip2px(7));
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//				}else {
//					finish();
//				}
//			}
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		presenter.readMessages();
//		MobclickAgent.onPageEnd(TAG);
//		MobclickAgent.onPause(this);
//	}
//
////	public void onClickSubmit(View v) {
////		String s = editText.getText().toString().trim();
////		if (TextUtils.isEmpty(s)) {
////			ActivityUtils.showToast(PlayActivity.this, "提交内容不可为空");
////		} else {
////			RequestParams params = new RequestParams();
////			params.put("userId", UserUtil.getInstance(PlayActivity.this).getUserId());
////			params.put("content", s);
////			params.put("channelId", bean.getData().getChannel_id());
////
////			Log.d(TAG, "SUBMIT_ZHIBO_CONTENT url========" + Preferences.SUBMIT_ZHIBO_CONTENT + "&userId=" + UserUtil.getInstance(PlayActivity.this).getUserId() + "&content=" + s + "&channelId=" + bean
////					.getData().getChannel_id());
////			HttpUtil.post(Preferences.SUBMIT_ZHIBO_CONTENT, params, new AsyncHttpResponseHandler() {
////
////				@Override
////				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
////					// TODO Auto-generated method stub
////					String s = new String(arg2);
////					Log.d(TAG, s);
////					Bean bean = JSON.parseObject(s, Bean.class);
////					if (bean != null && bean.getCode().equals("SUCCESS")) {
////						DialogUtils.showMyDialog(PlayActivity.this, MyPreferences.SHOW_SUCCESS_DIALOG, "提交成功", "提交成功，谢谢您的宝贵建议", null);
////						editText.setText("");
////					}
////				}
////
////				@Override
////				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
////					// TODO Auto-generated method stub
////					ActivityUtils.showToastForFail(PlayActivity.this, "提交信息失败");
////				}
////			});
////		}
////	}
//
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		DialogUtils.dismissMyDialog();
//		if (player != null)
//			player.release();
//
//		presenter.stop();
//
//		TIMGroupManager.getInstance().quitGroup(identify, new TIMCallBack() {
//
//			@Override
//			public void onSuccess() {
//				// TODO Auto-generated method stub
//				LoginBusiness.logout(new TIMCallBack() {
//
//					@Override
//					public void onSuccess() {
//						// TODO Auto-generated method stub
//					}
//
//					@Override
//					public void onError(int arg0, String arg1) {
//						// TODO Auto-generated method stub
//
//					}
//				});
//			}
//
//			@Override
//			public void onError(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	}
//
//	/**
//     * 显示消息
//     *
//     * @param message
//     */
//    @Override
//    public void showMessage(TIMMessage message) {
//        if (message == null) {
//            adapter.notifyDataSetChanged();
//        } else {
//            Message mMessage = MessageFactory.getMessage(message);
//            if (mMessage != null) {
//                if (messageList.size()==0){
//                    mMessage.setHasTime(null);
//                }else{
//                    mMessage.setHasTime(messageList.get(messageList.size()-1).getMessage());
//                }
//                messageList.add(mMessage);
//                adapter.notifyDataSetChanged();
//                listView.setSelection(adapter.getCount()-1);
//            }
//        }
//
//    }
//
//    /**
//     * 显示消息
//     *
//     * @param messages
//     */
//    @Override
//    public void showMessage(List<TIMMessage> messages) {
//        int newMsgNum = 0;
//        for (int i = 0; i < messages.size(); ++i){
//            Message mMessage = MessageFactory.getMessage(messages.get(i));
//            if (mMessage == null) continue;
//            ++newMsgNum;
//            if (i != messages.size() - 1){
//                mMessage.setHasTime(messages.get(i+1));
//                messageList.add(0, mMessage);
//            }else{
//                messageList.add(0, mMessage);
//            }
//        }
//        adapter.notifyDataSetChanged();
//        listView.setSelection(newMsgNum);
//    }
//
//    /**
//     * 发送消息成功
//     *
//     * @param message 返回的消息
//     */
//    @Override
//    public void onSendMessageSuccess(TIMMessage message) {
//        showMessage(message);
//    }
//
//    /**
//     * 发送消息失败
//     *
//     * @param code 返回码
//     * @param desc 返回描述
//     */
//    @Override
//    public void onSendMessageFail(int code, String desc) {
//
//    }
//
//    /**
//     * 发送图片消息
//     */
//    @Override
//    public void sendImage() {
//        Intent intent_album = new Intent("android.intent.action.GET_CONTENT");
//        intent_album.setType("image/*");
//        startActivityForResult(intent_album, IMAGE_STORE);
//    }
//
//    /**
//     * 发送照片消息
//     */
//    @Override
//    public void sendPhoto() {
//        Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent_photo.resolveActivity(getPackageManager()) != null) {
//            File tempFile = FileUtil.getTempFile(FileUtil.FileType.IMG);
//            if (tempFile != null) {
//                fileUri = Uri.fromFile(tempFile);
//            }
//            intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//            startActivityForResult(intent_photo, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
//    }
//
//    /**
//     * 发送文本消息
//     */
//    @Override
//    public void sendText() {
//        Message message = new TextMessage(input.getText());
//        presenter.sendMessage(message.getMessage());
//        input.setText("");
//    }
//
//
//    /**
//     * 开始发送语音消息
//     */
//    @Override
//    public void startSendVoice() {
//        voiceSendingView.setVisibility(View.VISIBLE);
//        voiceSendingView.showRecording();
//        recorder.startRecording();
//
//    }
//
//    /**
//     * 结束发送语音消息
//     */
//    @Override
//    public void endSendVoice() {
//        voiceSendingView.release();
//        voiceSendingView.setVisibility(View.GONE);
//        recorder.stopRecording();
//        if (recorder.getTimeInterval() < 1) {
//            Toast.makeText(this, getResources().getString(R.string.chat_audio_too_short), Toast.LENGTH_SHORT).show();
//        } else {
//            Message message = new VoiceMessage(recorder.getTimeInterval(), recorder.getFilePath());
//            presenter.sendMessage(message.getMessage());
//        }
//    }
//
//    /**
//     * 发送小视频消息
//     *
//     * @param fileName 文件名
//     */
//    @Override
//    public void sendVideo(String fileName) {
//        Message message = new VideoMessage(fileName);
//        presenter.sendMessage(message.getMessage());
//    }
//
//
//    /**
//     * 结束发送语音消息
//     */
//    @Override
//    public void cancelSendVoice() {
//
//    }
//
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                   ContextMenu.ContextMenuInfo menuInfo) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//        Message message = messageList.get(info.position);
//        if (message.isSendFail()){
//            menu.add(0, 1, Menu.NONE, getString(R.string.chat_resend));
//        }
//    }
//
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        Message message = messageList.get(info.position);
//        switch (item.getItemId()) {
//            case 1:
//                messageList.remove(message);
//                presenter.sendMessage(message.getMessage());
//                break;
//            default:
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                sendImage(fileUri.getPath());
//            }
//        } else if (requestCode == IMAGE_STORE) {
//            if (resultCode == RESULT_OK) {
//                sendImage(FileUtil.getImageFilePath(this, data.getData()));
//            }
//
//        }
//
//    }
//
//    private void sendImage(String path){
//        if (path == null) return;
//        File file = new File(path);
//        if (file.exists()){
//            if (file.length() > 1024 * 1024 * 10){
//                Toast.makeText(this, getString(R.string.chat_file_too_large),Toast.LENGTH_SHORT).show();
//            }else{
//                Message message = new ImageMessage(path);
//                presenter.sendMessage(message.getMessage());
//            }
//        }else{
//            Toast.makeText(this, getString(R.string.chat_file_not_exist),Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//
//    @Override
//    protected void onResume() {
//    	// TODO Auto-generated method stub
//    	super.onResume();
//    	MobclickAgent.onPageStart(TAG);
//    	MobclickAgent.onResume(this);
//    }
//
//
//}
