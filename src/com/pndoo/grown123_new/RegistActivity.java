package com.pndoo.grown123_new;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.springframework.util.MultiValueMap;
import org.xutils.common.Callback;
import org.xutils.x;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.LoginController;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.CheckBean;
import com.pndoo.grown123_new.dto.base.GoodsDetailBean;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.soap.LoginJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.GoodsDetailUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;

import static com.pndoo.grown123_new.R.id.textView1;
import static com.pndoo.grown123_new.util.DialogUtils.dismissMyDialog;
import static com.tencent.qalsdk.service.QalService.context;

public class RegistActivity extends BaseActivity {
    private TextView tv_header_title;
    EditText et_code;
    LinearLayout ll_code;
    // ImageView iv_clear_code;
    EditText et_phone;
    LinearLayout ll_phone;
    ImageView iv_clear_phone;
    EditText et_surname;
    EditText et_password01;
    LinearLayout ll_password01;
    EditText et_password02;
    LinearLayout ll_password02;
    TaskManager tm;
    LoginController loginController;
    Button btn_send;
    LinearLayout ll_jycode;
    TextView second;
    String verifCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext().addActivity(this);
        tm = getApplicationContext().getTaskManager();
        loginController = IoC.getInstance(LoginController.class);
        setContentView(R.layout.regist);
        initView();
    }

    /**
     * 初始化修改密码界面
     */
    public void initView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText("用户注册");
        btn_send = (Button) findViewById(R.id.btn_send);
        et_code = (EditText) findViewById(R.id.et_code);
        ll_code = (LinearLayout) findViewById(R.id.ll_code);
        et_phone = (EditText) findViewById(R.id.et_phone);
        ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
        et_password01 = (EditText) findViewById(R.id.et_password01);
        ll_password01 = (LinearLayout) findViewById(R.id.ll_password01);
        et_password02 = (EditText) findViewById(R.id.et_password02);
        ll_password02 = (LinearLayout) findViewById(R.id.ll_password02);
        et_surname = (EditText) findViewById(R.id.et_surname);
    }

    public void btnBack(View v) {
        this.finish();
    }

    public void btnConfirm(View v) {
        String phone = et_phone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (!TextUtils.isEmpty(phone) && phone.matches("^[1][34578][0-9]{9}$")) {
            et_phone.setText(phone);
        } else if (phone.matches("[\u4E00-\u9FA5a]+")) {
            ActivityUtils.showToast(RegistActivity.this, "请不要输入中文!");
            return;
        } else if (TextUtils.isEmpty(phone)) {
            ActivityUtils.showToastForFail(getApplication(), "请输入手机号码！");
            return;
        } else {
            ActivityUtils.showToastForFail(getApplication(), "手机号码格式不正确！");
            return;
        }
        String password1 = et_password01.getText().toString().trim();
        if (!TextUtils.isEmpty(password1) && password1.length() >= 6 || password1.length() <= 16) {
            et_password01.setText(password1);

        } else if (password1.matches("[\u4E00-\u9FA5a]+")) {
            ActivityUtils.showToast(RegistActivity.this, "请不要输入中文!");
            return;
        } else if (TextUtils.isEmpty(password1)) {
            ActivityUtils.showToastForFail(getApplication(), "请输入密码！");
            return;
        } else {
            ActivityUtils.showToastForFail(RegistActivity.this, "密码请输入6-16位");
            return;
        }
        String password2 = et_password02.getText().toString().trim();
        if (!TextUtils.isEmpty(password1) && password2.length() >= 6 || password2.length() <= 16) {
            et_password01.setText(password1);
        } else if (password2.matches("[\u4E00-\u9FA5a]+")) {
            ActivityUtils.showToast(RegistActivity.this, "请不要输入中文!");
            return;
        } else if (TextUtils.isEmpty(password2)) {
            ActivityUtils.showToastForFail(getApplication(), "请输入确认密码！");
            return;
        } else {
            ActivityUtils.showToastForFail(RegistActivity.this, "密码请输入6-16位");
            return;
        }
        if (!password1.equals(password2)) {
            ActivityUtils.showToastForFail(getApplication(), "密码与确认密码不一致，请重新输入");
            return;
        }
        String surname = et_surname.getText().toString().trim();
        if (TextUtils.isEmpty(surname)) {
            ActivityUtils.showToastForFail(getApplication(), "昵称选项不能为空，请输入");
            return;
        }
        if (!ActivityUtils.isNetworkAvailable(this)) {
            ActivityUtils.showToastForFail(this, "无法连接网络");
            return;
        }
        if (code.equals(verifCode)) {
            if (!TextUtils.isEmpty(phone) && phone.matches("^[1][34578][0-9]{9}$")) {
                System.out.println("phone=>>" + phone);
//				reginst();

                Intent intent = new Intent(RegistActivity.this, RegisterTwoActivity.class);
                userName = et_phone.getText().toString().trim();
                userPwd = et_password01.getText().toString().trim();
                TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();
                intent.putExtra("userName", userName);
                intent.putExtra("userPwd", userPwd);
                intent.putExtra("userEmail", et_code.getText().toString().trim());
                intent.putExtra("phoneId", imei);
                intent.putExtra("surname", surname);
                startActivity(intent);
                finish();
            }
        } else {
            ActivityUtils.showToast(RegistActivity.this, "验证码错误");
        }

    }

    private void reginst() {

        Map<String, String> params = new HashMap<String, String>();
        userName = et_phone.getText().toString().trim();
        userPwd = et_password01.getText().toString().trim();
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        params.put("userName", userName);
        params.put("userPwd", userPwd);
        params.put("userEmail", et_code.getText().toString().trim());
        params.put("phoneId", imei);
        tm.createNewTask(new TaskListener() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public void onPreExecute(BaseTask task) {
                DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...", null);
            }

            @Override
            public void onPostExecute(BaseTask task, String errorMsg) {
                dismissMyDialog();
                if (errorMsg != null) {// 获取数据出现异常
                    DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", errorMsg, null);
                } else {
                    LoginJson loginJson = loginController.getModel();
                    if (null == loginJson) {
                        DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "获取信息失败", null);
                        return;
                    } else {
                        if (loginJson.getCode().equals("SUCCESS")) {
                            ActivityUtils.showToastForSuccess(RegistActivity.this, loginJson.getCodeInfo());
                            SPUtility.putSPString(RegistActivity.this, "username", userName);
                            SPUtility.putSPString(RegistActivity.this, "userPwd", userPwd);
                            Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                            startActivity(intent);
                            return;
                        } else if (loginJson.getCode().equals("FAIL")) {
                            DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", loginJson.getCodeInfo(), null);
                            return;
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
                    errorMsg = loginController.reginst(param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return errorMsg;
            }
        }).execute(params);

    }

    TimeCount time = new TimeCount(60000, 1000);
    private String userName;
    private String userPwd;
    private String phone;

    public void btn_login(View v) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void btn_send(View v) {
        phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || !phone.matches("^[1][34578][0-9]{9}$")) {
            DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "手机号码格式错误", null);
            return;
        }
        DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_CONFIRM_DIALOG, "发送短信", "<center>您确认要给" + phone + "发送短信吗？</center>", new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phone) && phone.matches("^[1][34578][0-9]{9}$")) {
                    System.out.println("phone=>>" + phone);
                    org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.CHECKNAME);
                    params.addQueryStringParameter("userName", phone);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<CheckBean>() {
                            }.getType();
                            CheckBean bean = gson.fromJson(s, type);
                            if (bean != null && bean.getCode().equals("SUCCESS")) {
                                if(bean.getData().getCode()==0){
                                    send();
                                }else{
                                    DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", bean.getData().getMessage(), null);
                                }
                            } else{
                                DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", bean.getCodeInfo(), null);
                                return;
                            }
                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "请检查网络", null);
                        }

                        @Override
                        public void onCancelled(CancelledException e) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
            }
        });

    }

    private void send() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        tm.createNewTask(new TaskListener() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public void onPreExecute(BaseTask task) {
                DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...", null);
            }

            @Override
            public void onPostExecute(BaseTask task, String errorMsg) {
                dismissMyDialog();
                if (errorMsg != null) {// 获取数据出现异常
                    DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", errorMsg, null);
                } else {
                    LoginJson loginJson = loginController.getModel();
                    if (null == loginJson) {
                        DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "获取信息失败", null);
                        return;
                    } else {
                        if (loginJson.getCode() != null) {
                            if (loginJson.getCode().equals("SUCCESS")) {
                                verifCode = loginJson.getSms().getVerifCode();
                                Log.i("verifCode", verifCode);
                                return;
                            } else if (loginJson.getCode().equals("FAIL")) {
                                DialogUtils.showMyDialog(RegistActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", loginJson.getCodeInfo(), null);
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
     * 清除用户名输入框
     *
     * @param v
     */
    // public void clearClick(View v) {
    // int id = v.getId();
    // if (id == R.id.iv_clear_code) {
    // et_code.setText("");
    // v.setVisibility(View.GONE);
    // } else if (id == R.id.iv_clear_phone) {
    // et_phone.setText("");
    // v.setVisibility(View.GONE);
    // } else if (id == R.id.iv_delete01) {
    // et_password01.setText("");
    // v.setVisibility(View.GONE);
    // } else if (id == R.id.iv_delete02) {
    // et_password02.setText("");
    // v.setVisibility(View.GONE);
    // } else {
    // }
    //
    // }

    // /**
    // * 验证输入的邮箱格式是否符合
    // *
    // * @param email
    // * @return 是否合法
    // */
    // public static boolean emailFormat(String email) {
    // boolean tag = true;
    // final String pattern1 =
    // "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$";
    // final Pattern pattern = Pattern.compile(pattern1);
    // final Matcher mat = pattern.matcher(email);
    // if (!mat.find()) {
    // tag = false;
    // }
    // return tag;
    // }
    @Override
    protected void onDestroy() {
        getApplicationContext().removeActivity(this);
        super.onDestroy();
    }

}

