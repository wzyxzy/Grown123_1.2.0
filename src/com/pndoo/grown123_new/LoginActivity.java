package com.pndoo.grown123_new;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.BookController;
import com.pndoo.grown123_new.controller.LoginController;
import com.pndoo.grown123_new.download.DownloadProgressListener;
import com.pndoo.grown123_new.download.FileDownloader;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.LoginBean;
import com.pndoo.grown123_new.dto.base.Update;
import com.pndoo.grown123_new.dto.base.UserVO;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.service.UserDAO;
import com.pndoo.grown123_new.soap.BookJson;
import com.pndoo.grown123_new.soap.LoginJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;
import com.pndoo.grown123_new.util.UpdateInfo;
import com.pndoo.grown123_new.util.UpdateInfoParser;
import com.umeng.analytics.MobclickAgent;

import org.springframework.util.MultiValueMap;
import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoginActivity extends BaseActivity {

    UpdateInfo info;
    LinearLayout ll_username;
    LinearLayout ll_password;
    EditText et_username;
    EditText et_password;
    Update update;
    TaskManager tm;
    LoginController loginController;
    UserDAO dao;
    String[] loginUsers;
    String client_version;
    BookController bookController;
    private ProgressBar pb_syn;
    private String file;
    private int inputbox_click;
    private int inputbox_normal;
    private TextView publishName;
    private final String TAG = getClass().getSimpleName();
    // public final static String IS_REGISTER = "IS_REGISTER";
    // private boolean mIs_Register = false;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(LoginActivity.this, CastUpdateActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    BookJson bookJson = bookController.getModel();
                    update = bookJson.getUpdate();
                    if (update != null) {
                        Log.i("update", "update=" + update.toString() + "LoginActivity");
                        if ("2".equals(update.getIsUpdate())) {
                            if (ActivityUtils.isWifiEnabled(LoginActivity.this)) {
                                intent = new Intent(LoginActivity.this, CastUpdateActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("update", update);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            } else {
                                ActivityUtils.showToastForFail(LoginActivity.this, "请连接WIFI后进行更新~");
                            }
                        } else if ("1".equals(update.getIsUpdate())) {
                            if (ActivityUtils.isWifiEnabled(LoginActivity.this)) {
                                intent = new Intent(LoginActivity.this, CastUpdateActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("update", update);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            } else {
                                ActivityUtils.showToastForFail(LoginActivity.this, "请连接WIFI后进行更新~");
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private String imei;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext().addActivity(this);
        // if (this.getResources().getConfiguration().orientation ==
        // Configuration.ORIENTATION_LANDSCAPE) {
        // setContentView(R.layout.login_land);
        // } else if (this.getResources().getConfiguration().orientation ==
        // Configuration.ORIENTATION_PORTRAIT) {
        setContentView(R.layout.login);
        // }
        tm = getApplicationContext().getTaskManager();
        loginController = IoC.getInstance(LoginController.class);
        bookController = IoC.getInstance(BookController.class);
        dao = new UserDAO(this);
        loginUsers = dao.getUserListFromXML();
        DialogUtils.dismissMyDialog();
        initView();

        pb_syn = (ProgressBar) findViewById(R.id.pb_syn);
        client_version = getClientVersion();
        checkUpdateVerson();
        String userName = SPUtility.getSPString(this, "username");
        String userPwd = SPUtility.getSPString(this, "userPwd");
        Log.d(TAG, "login---------userName=" + userName + "--------userPwd=" + userPwd);
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPwd)) {
            SPUtility.putSPString(this, "loginUsers", userName);
            SPUtility.putSPString(this, "password", userPwd);
            login(userName, userPwd);
        }


//        JPushInterface.setAlias(this, "18522372611", new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                Log.d(TAG, "ssssssss==" + s + "----------i=" + i);
//            }
//        });
//
//        Set<String> tags = new HashSet<>();
//        tags.add("18522372612");
//
//        JPushInterface.setTags(this, tags, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//
//                Log.d(TAG, "ssssssss==" + s + "----------i=" + i + "------set=" + set.contains("18522372611"));
//            }
//        });
    }

    // 完成文件的下载
    private void download(File saveDir, String path) {
        new Thread(new DownloadFileTask(saveDir, path)).start();
    }

    private final class DownloadFileTask implements Runnable {
        private File saveDir;
        private String path;

        public DownloadFileTask(File saveDir, String path) {
            this.saveDir = saveDir;
            this.path = path;
        }

        public void run() {
            try {
                FileDownloader loader = new FileDownloader(getApplicationContext(), path, saveDir, 1, "jiayue.apk");
                pb_syn.setMax(loader.getFileSize());// 设置进度条的最大刻度为文件长度
                loader.download(new DownloadProgressListener() {
                    public void onDownloadSize(int size) {
                        Message msg = new Message();
                        msg.what = 3;
                        msg.getData().putInt("size", size);
                        handler.sendMessage(msg);
                    }
                });
            } catch (Exception e) {
                handler.sendMessage(handler.obtainMessage(-1));
            }
        }

    }

    /**
     * 安装下载成功的apk
     *
     * @param file apk的文件对象
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 查看的意图 (动作)
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        finish();
        startActivity(intent);
    }

    /**
     * 忘记密码按钮
     */

    public void btnForgot(View v) {
        Intent intent = new Intent(LoginActivity.this, UserResetActivity.class);
        startActivity(intent);
    }

    private void checkUpdateVerson() {
        // new Thread(new CheckVersionTask()).start();
        getVerisonUpdate();

    }

    private void getVerisonUpdate() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("version", getClientVersion());
        params.put("systemType", "android");
        Log.i("BookSynActivity", "version" + getClientVersion());

        tm.createNewTask(new TaskListener() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public void onPreExecute(BaseTask task) {
            }

            @Override
            public void onPostExecute(BaseTask task, String errorMsg) {

                // if (errorMsg != null) {// 获取数据出现异常
                // // addTextViewForError("加载失败," + errorMsg);
                //
                // } else {
                // BookJson bookJson = bookController.getModel();
                // if (null == bookJson) {
                // // addTextViewForError("加载失败," + "返回空数据");
                // return;
                // } else {
                // if (bookJson.getCode().equals(MyPreferences.SUCCESS)&& null
                // != bookJson.getUpdate()) {
                //
                // return;
                // } else if (bookJson.getCode()
                // .equals(MyPreferences.FAIL)) {
                // // addTextViewForError("加载失败,"
                // // + bookJson.getCodeInfo());
                // return;
                // }
                // }
                // }
                //
            }

            @Override
            public void onProgressUpdate(BaseTask task, Object param) {
            }

            @Override
            public void onCancelled(BaseTask task) {
            }

            @Override
            public String onDoInBackground(BaseTask task, MultiValueMap<String, String> param) {
                try {
                    String errorMsg = bookController.getUpdate(param);
                    Message msg = Message.obtain();
                    msg.what = 2;
                    handler.sendMessage(msg);
                    return errorMsg;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).execute(params);

    }

    private String getClientVersion() {
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            return "100";
        }
    }

    private class CheckVersionTask implements Runnable {

        public void run() {
            try {
                String path = Preferences.UPDATE_URL;
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                InputStream is = conn.getInputStream();
                info = UpdateInfoParser.getUpdateInfo(is);
                // 如果服务上的版本信息 比客户端的信息大 提示用户升级
                String serverversion = info.getVersion();
                float serverV = (new Float(serverversion)).floatValue();

                float localV = (new Float(client_version)).floatValue();
                if (localV >= serverV) {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else {
                    // 弹出对话框的操作必须在主线程里面
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }

        }
    }

    public void initView() {
        inputbox_click = getResources().getIdentifier("inputbox_click", "drawable", getPackageName());
        inputbox_normal = getResources().getIdentifier("inputbox_normal", "drawable", getPackageName());
        publishName = (TextView) findViewById(R.id.tv_publishName);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        ll_username = (LinearLayout) findViewById(R.id.ll_username);
        ll_password = (LinearLayout) findViewById(R.id.ll_password);
        publishName.setText("用户登录");
        String username = SPUtility.getSPString(this, "username");
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();

        // 手机型号
        String model = android.os.Build.MODEL;

        // mIs_Register = getIntent().getBooleanExtra(IS_REGISTER, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * 注册
     */
    public void btnRegist(View v) {
        if (!ActivityUtils.isNetworkAvailable(getBaseContext())) {
            ActivityUtils.showToastForFail(getBaseContext(), "对不起,不能进行离线注册");
        } else {

//          Intent intent = new Intent(this, WebChatActivity.class);
//            Intent intent = new Intent(this, RegisterTwoActivity.class);
            Intent intent = new Intent(this, RegistActivity.class);
            startActivity(intent);
        }
    }


    /**
     * 忘记密码
     */
    public void btnforget(View v) {
        DialogUtils.showMyDialog(this, MyPreferences.SHOW_BUTTON_DIALOG, "通过Email找回密码", "取        消", new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UpdateUserInfoActivity.class);
                intent.putExtra("updateFlag", MyPreferences.FORGET_PASSWORD);
                startActivityForResult(intent, MyPreferences.FORGET_PASSWORD);
                DialogUtils.dismissMyDialog();
            }

        });
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = DialogUtils.dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        lp.height = (int) (display.getHeight()); // 设置高度
        DialogUtils.dialog.getWindow().setAttributes(lp);

    }

    /**
     * 登录按钮
     */
    public void btnLogin(View v) {
        String username = et_username.getText().toString().trim();
        String password1 = SPUtility.getSPString(LoginActivity.this, username);
        if (!TextUtils.isEmpty(password1) && !ActivityUtils.isNetworkAvailable(this)) {
            et_password.setText(password1);
        }
        if (!TextUtils.isEmpty(username) && username.matches("^[a-zA-Z0-9][a-zA-Z0-9_]{5,16}$")) {
            et_username.setText(username);

        } else if (username.matches("[\u4E00-\u9FA5a]+")) {
            ActivityUtils.showToast(LoginActivity.this, "请不要输入中文!");
            return;
        } else if (TextUtils.isEmpty(username)) {
            ActivityUtils.showToastForFail(getApplication(), "请输入用户名");
            return;
        } else {
            ActivityUtils.showToastForFail(LoginActivity.this, "账号请输入6-16位字母或字母+数字!");
            return;
        }
        String password = et_password.getText().toString().trim();

        if (!TextUtils.isEmpty(password) && password.length() >= 6 || password.length() <= 16) {
            et_password.setText(password);
        } else if (password.matches("[\u4E00-\u9FA5a]+")) {
            ActivityUtils.showToast(LoginActivity.this, "请不要输入中文!");
            return;
        } else if (TextUtils.isEmpty(password)) {
            ActivityUtils.showToastForFail(getApplication(), "请输入密码！");
            return;
        } else {
            ActivityUtils.showToastForFail(LoginActivity.this, "您输入的密码不正确!");
            return;
        }

        login(username, password);
    }

    private void login(String username, final String password) {

        String name = SPUtility.getSPString(getBaseContext(), "loginUsers");
        String pwd = SPUtility.getSPString(getBaseContext(), "password");

        if (!ActivityUtils.isNetworkAvailable(this)) {

            if (name.equalsIgnoreCase(username) && pwd.equalsIgnoreCase(password)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("update_flag", true);
                Bundle b = getIntent().getBundleExtra("extra");
                if (b != null)
                    intent.putExtra("extra", b);
                startActivity(intent);
                LoginActivity.this.finish();
            } else if (TextUtils.isEmpty(name) && TextUtils.isEmpty(pwd)) {
                DialogUtils.showMyDialog(this, MyPreferences.SHOW_CONFIRM_DIALOG, "网络设置提示", "网络连接不可用,是否进行设置?", new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                });
            } else {
                ActivityUtils.showToastForFail(getBaseContext(), "账号密码不符");
            }

            return;
        }
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.LOGIN_URL);
        params.addQueryStringParameter("userName", username);
        params.addQueryStringParameter("userPwd", password);
        params.addQueryStringParameter("phoneId", imei);
        if (!LoginActivity.this.isFinishing())
            DialogUtils.showMyDialog(LoginActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在登录中...", null);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<LoginBean>() {
                }.getType();
                LoginBean bean = gson.fromJson(s, type);

                DialogUtils.dismissMyDialog();
                if (bean.getData() != null && bean.getCode().equals("SUCCESS")) {
                    LoginBean.Data data = bean.getData();
                    UserVO bj = new UserVO();
                    bj.setPassword(password);
                    bj.setUserId(data.getUserId());
                    bj.setUserName(data.getUserName());
                    bj.setEmail(data.getUserEmail());
                    bj.setIsHaveGroup(data.getIsHaveGroup());
                    bj.setUserPortraits(data.getUserPortraits());
                    bj.setUserStatus(data.getUserStatus());
                    LoginBean.Data.UserDetail detail = data.getUserDetail();
                    bj.setAddress1(detail.getAddress1());
                    bj.setBirthdayShow(detail.getBirthdayShow());
                    bj.setRealName(detail.getRealName());
                    bj.setKindergarten(detail.getKindergarten());
                    bj.setSex(detail.getSex());
                    bj.setSurname(detail.getSurname());
                    bj.setLevel(detail.getLevel());
                    bj.setParents(detail.getParents());
                    bj.setSubscibed(detail.getSubscibed());
                    setSpUserVo(bj);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("update_flag", true);
                    Bundle b = getIntent().getBundleExtra("extra");
                    if (b != null)
                        intent.putExtra("extra", b);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else if(bean.getCodeInfo().contains("解绑")){
                    DialogUtils.showMyDialog(LoginActivity.this, MyPreferences.SHOW_JIESUO_DIALOG, "", "", new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            DialogUtils.dismissMyDialog();
                            showYanzhengDialog();
                        }

                    });
                }else{
                    DialogUtils.showMyDialog(LoginActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "登录失败", bean.getCodeInfo(), null);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void deleteUsername(View v) {
        et_username.setText("");
        v.setVisibility(View.GONE);
    }

    Button btn_send;
    String verifCode;

    private void showYanzhengDialog() {
        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(this, R.style.my_dialog);
        dialog.setContentView(R.layout.dialog_login_jiesuorenzheng);
        final EditText textView = (EditText) dialog.findViewById(R.id.dialog_titile);
        textView.setText(et_username.getText().toString().trim());
        final EditText editText = (EditText) dialog.findViewById(R.id.editText1);
        btn_send = (Button) dialog.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                btn_send();
            }
        });
        Button button07 = (Button) dialog.findViewById(R.id.btn_right);
        ImageButton button = (ImageButton) dialog.findViewById(R.id.imageButton1);
        button07.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (editText.getText().toString().trim().equals(verifCode)) {
                    org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JIESUO);
                    params.addQueryStringParameter("userName", textView.getText().toString().trim());

                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<Bean>() {
                            }.getType();
                            Bean bean = gson.fromJson(s, type);

                            if (bean.getCode().equals("SUCCESS")) {
                                ActivityUtils.showToast(LoginActivity.this, "已成功解绑，请登录");
                                dialog.dismiss();
                            } else {
                                ActivityUtils.showToastForFail(LoginActivity.this, "解绑失败");
                            }
                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            ActivityUtils.showToastForFail(LoginActivity.this, "解绑失败");
                        }

                        @Override
                        public void onCancelled(CancelledException e) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                } else {
                    ActivityUtils.showToast(LoginActivity.this, "验证码错误");
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void btn_send() {
        String phone = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(phone) && !phone.matches("^[1][34578][0-9]{9}$")) {
            return;
        }
        if (!TextUtils.isEmpty(phone) && phone.matches("^[1][34578][0-9]{9}$")) {
            System.out.println("phone=>>" + phone);
            Map<String, String> params = new HashMap<String, String>();
            params.put("phone", phone);
            tm.createNewTask(new TaskListener() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public void onPreExecute(BaseTask task) {
                    ActivityUtils.showToast(LoginActivity.this, "正在发送中...");
                }

                @Override
                public void onPostExecute(BaseTask task, String errorMsg) {
                    DialogUtils.dismissMyDialog();
                    if (errorMsg != null) {// 获取数据出现异常
                        ActivityUtils.showToast(LoginActivity.this, "验证码获取失败");
                    } else {
                        LoginJson loginJson = loginController.getModel();
                        if (null == loginJson) {
                            ActivityUtils.showToast(LoginActivity.this, "验证码获取失败");
                            return;
                        } else {
                            if (loginJson.getCode() != null) {
                                if (loginJson.getCode().equals("SUCCESS")) {
                                    verifCode = loginJson.getSms().getVerifCode();
                                    return;
                                } else if (loginJson.getCode().equals("FAIL")) {
                                    ActivityUtils.showToast(LoginActivity.this, "验证码获取失败");
                                    return;
                                }
                            }
                        }
                    }

                }

                @Override
                public void onProgressUpdate(BaseTask task, Object param) {
                }

                @Override
                public void onCancelled(BaseTask task) {
                }

                @Override
                public String onDoInBackground(BaseTask task, MultiValueMap<String, String> param) {
                    String errorMsg = null;
                    try {
                        errorMsg = loginController.sendSMS(param);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return errorMsg;
                }
            }).execute(params);
            time.start();
        }

    }

    TimeCount time = new TimeCount(60000, 1000);

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btn_send.setText("重新验证");
            btn_send.setTextColor(getResources().getColor(R.color.background));
            btn_send.setClickable(true);
            int regist_sms_button1 = getResources().getIdentifier("regist_sms_button1", "drawable", getPackageName());
            btn_send.setBackgroundResource(regist_sms_button1);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            int regist_sms_button2 = getResources().getIdentifier("regist_sms_button2", "drawable", getPackageName());
            btn_send.setBackgroundResource(regist_sms_button2);
            btn_send.setClickable(false);
            btn_send.setTextColor(getResources().getColor(R.color.login_hint_color));
            btn_send.setText("(" + millisUntilFinished / 1000 + "秒)");
        }


    }

    /**
     * 将User写入xml
     */
    private void setSpUserVo(UserVO user) {
        SPUtility.putSPBoolean(this, R.string.islogin, true);
        SPUtility.putSPString(this, "userid", user.getUserId());
        SPUtility.putSPString(this, "username", user.getUserName());
        // 用于账号统计
        MobclickAgent.onProfileSignIn(user.getUserId());
        SPUtility.putSPString(this, "password", user.getPassword());
        SPUtility.putSPString(this, "userPwd", user.getPassword());
        SPUtility.putSPString(this, user.getUserName().toLowerCase(), user.getPassword());
        Properties prop = new Properties();
        prop.put(user.getUserName(), user.getPassword());
        SPUtility.putSPString(this, "email", user.getEmail());

        SPUtility.putSPInteger(this, "userDetail.sex", user.getSex());
        SPUtility.putSPString(this, "userDetail.kindergarten", user.getKindergarten());
        SPUtility.putSPString(this, "userDetail.realName", user.getRealName());
        SPUtility.putSPString(this, "userDetail.birthdayShow", user.getBirthdayShow());
        SPUtility.putSPString(this, "userDetail.level", user.getLevel());
        SPUtility.putSPString(this, "userDetail.parents", user.getParents());
        SPUtility.putSPString(this, "userDetail.address1", user.getAddress1());
        SPUtility.putSPString(this, "userDetail.surname", user.getSurname());
        SPUtility.putSPInteger(this, "userDetail.subscibed", user.getSubscibed());
        SPUtility.putSPInteger(this, "userStatus", user.getUserStatus());
        SPUtility.putSPInteger(this, "isHaveGroup", user.getIsHaveGroup());
        SPUtility.putSPString(this, "userPortraits", user.getUserPortraits());


        Log.d(TAG, "userStatus===" + user.getUserStatus() + "user.getSubscibed()=" + user.getSubscibed() + "----user.getParents()=" + user.getParents() + "----------user.getLevel()=" + user.getLevel() + "------user.getAddress1()=" + user.getAddress1());

        dao.saveUserForLogin(user, et_username.getText().toString().trim());
        UserVO userVO = dao.find(user.getUserId());
        if (userVO.getBooksData() != null) {
            SPUtility.putSPString(this, "booksData", userVO.getBooksData());
        } else {
            SPUtility.putSPString(this, "booksData", "");
        }

        savaPic(user.getUserPortraits());
    }

    private void savaPic(String path){
        String url = Preferences.IMAGE_HTTP_LOCATION + path;

        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(url);
        // 自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
        params.setSaveFilePath(ActivityUtils.getSDPath().getAbsolutePath());
        // 自动为文件命名
        params.setAutoRename(true);
        params.setAutoResume(true);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "imageDownload onFailure------" + ex.getMessage());
                File file = new File(ActivityUtils.getSDPath().getAbsolutePath()+"/photo.png");
                if(file.exists())
                    file.delete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }

            // 网络请求之前回调
            @Override
            public void onWaiting() {
            }

            // 网络请求开始的时候回调
            @Override
            public void onStarted() {
            }

            // 下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                // 当前进度和文件总大小
                Log.i("JAVA", "current：" + current + "，total：" + total);
            }

            @Override
            public void onSuccess(File file) {
                // TODO Auto-generated method stub
                Log.d(TAG, "imageDownload onSuccess");
                file.renameTo(new File(ActivityUtils.getSDPath().getAbsolutePath()+"/photo.png"));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            getApplicationContext().finishAllActivity();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        getApplicationContext().removeActivity(this);
        super.onDestroy();
    }

    /**
     * 更新记住密码单选按钮状态
     *
     * @param loginName
     */
    public void updateRememberPd(String loginName) {
        String password = dao.isRememberPd(loginName);
        if (null != password) {
            if (!"".equals(password)) {
                et_password.setText(password);
            }
        } else {
            et_password.setText("");
        }
    }
}
