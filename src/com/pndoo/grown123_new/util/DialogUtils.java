package com.pndoo.grown123_new.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.GoodsDetailBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.rest.OnRefreshAdapterListener;
import com.pndoo.grown123_new.view.wheel.ChangeAddressDialog;
import com.pndoo.grown123_new.view.wheel.OnAddressCodeCListener;

import org.xutils.common.Callback;
import org.xutils.x;

public class DialogUtils {

    // 对话框数据
    public static Dialog dialog;
    public static int selected_item = -1;
    private static final String TAG = "DialogUtils";

    /**
     * 显示对话框
     *
     * @param id       对话框的类型
     * @param title    对话框里的提示信息（进度条不需要设为null即可）
     * @param listener 点击确定对应的操作（不需要任何操作设为null即可）
     */
    @SuppressLint("NewApi")
    public static void showMyDialog(final Context context, int id, String title, String message, OnClickListener listener) {
        dismissMyDialog();
        switch (id) {
            case MyPreferences.SHOW_PROGRESS_DIALOG:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.progress);
                int tv_progess = context.getResources().getIdentifier("tv_progess", "id", context.getPackageName());
                TextView textView = (TextView) dialog.findViewById(tv_progess);
                textView.setText(Html.fromHtml(message));
                dialog.setCancelable(true);
                // dialog.setOnKeyListener(new OnKeyListener() {
                //
                // @Override
                // public boolean onKey(DialogInterface dialog, int keyCode,
                // KeyEvent event) {
                // if (keyCode == KeyEvent.KEYCODE_BACK) {
                // dismissMyDialog();
                // ((MyApplication) (context.getApplicationContext()))
                // .getTaskManager().cancelAll();
                // }
                // return false;
                // }
                // });
                dialog.show();
                break;
            case MyPreferences.SHOW_ERROR_DIALOG:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.error_dialog);
                dialog.setCancelable(true);
                TextView tv_title01 = (TextView) dialog.findViewById(R.id.dialog_titile);
                tv_title01.setText(title);
                TextView textView01 = (TextView) dialog.findViewById(R.id.error_message);
                textView01.setText(message);

                Button button = (Button) dialog.findViewById(R.id.error_back);
                if (null == listener) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissMyDialog();
                        }
                    });
                } else {
                    button.setOnClickListener(listener);
                }
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            case MyPreferences.SHOW_SUCCESS_DIALOG:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.error_dialog);
                dialog.setCancelable(true);
                TextView tv_title = (TextView) dialog.findViewById(R.id.dialog_titile);
                tv_title.setText(title);
                TextView tv_message = (TextView) dialog.findViewById(R.id.error_message);
                tv_message.setText(message);
                Button button_next = (Button) dialog.findViewById(R.id.error_back);
                if (null == listener) {
                    button_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissMyDialog();
                        }
                    });
                } else {
                    button_next.setOnClickListener(listener);
                }
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            case MyPreferences.SHOW_CONFIRM_DIALOG:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.confirm_dialog);
                TextView textView02 = (TextView) dialog.findViewById(R.id.dialog_titile);
                textView02.setText(Html.fromHtml(title));
                TextView textView03 = (TextView) dialog.findViewById(R.id.error_message);
                textView03.setText(Html.fromHtml(message));
                Button button01 = (Button) dialog.findViewById(R.id.btn_right);
                Button button02 = (Button) dialog.findViewById(R.id.btn_left);
                button01.setOnClickListener(listener);
                button02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissMyDialog();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
                break;
            case MyPreferences.SHOW_DOWNLOAD_DIALOG:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.confirm_dialog);
                TextView textView05 = (TextView) dialog.findViewById(R.id.dialog_titile);
                textView05.setText(Html.fromHtml(title));
                TextView textView06 = (TextView) dialog.findViewById(R.id.error_message);
                Spannable s4 = new SpannableString(message);
                s4.setSpan(new ForegroundColorSpan(Color.BLUE), 25, 27, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                textView06.setText(s4);
                Button button05 = (Button) dialog.findViewById(R.id.btn_right);
                Button button06 = (Button) dialog.findViewById(R.id.btn_left);
                int down = context.getResources().getIdentifier("down_bg", "drawable", context.getPackageName());
                int back = context.getResources().getIdentifier("back_bg", "drawable", context.getPackageName());
                button05.setBackground(context.getResources().getDrawable(down));
                button06.setBackground(context.getResources().getDrawable(back));
                button05.setOnClickListener(listener);
                button06.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissMyDialog();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
                break;
            case MyPreferences.SHOW_BUTTON_DIALOG:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.button_dialog);
                Button button03 = (Button) dialog.findViewById(R.id.btn_right);
                button03.setText(title);
                Button button04 = (Button) dialog.findViewById(R.id.btn_left);
                button04.setText(message);
                button03.setOnClickListener(listener);
                button04.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissMyDialog();
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
                break;

            case MyPreferences.SHOW_JIESUO_DIALOG:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.dialog_login_jiesuo);
                Button button07 = (Button) dialog.findViewById(R.id.btn_right);
                Button button08 = (Button) dialog.findViewById(R.id.btn_left);
                button07.setOnClickListener(listener);
                button08.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissMyDialog();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
                break;
            case MyPreferences.SHOW_GOODS_CONTENT:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.dialog_good_detail);
                TextView textView11 = (TextView) dialog.findViewById(R.id.content);
                textView11.setText(message);
                LinearLayout button11 = (LinearLayout) dialog.findViewById(R.id.cancel);

//				button01.setOnClickListener(listener);
//				Button button02 = (Button) dialog.findViewById(R.id.btn_left);
                button11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissMyDialog();
                    }
                });

                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                lp.y = DensityUtil.dip2px(context, 100);
                dialogWindow.setAttributes(lp);

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
        }

    }

    private final static String split = " ";
    public static void showGoodsDialog(final Context context, int id, String message, final OnRefreshAdapterListener listener) {
        dismissMyDialog();
        switch (id) {
            case MyPreferences.SHOW_VIP_INPUT:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.dialog_vipinput);
                final EditText code = (EditText) dialog.findViewById(R.id.editText1);

                ((ImageButton)dialog.findViewById(R.id.imageButton1)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissMyDialog();
                    }
                });

                ((Button)dialog.findViewById(R.id.btn_tijiao)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = code.getText().toString();
                        if(TextUtils.isEmpty(s)){
                            ActivityUtils.showToastForFail(context,"验证码不可为空");
                            return;
                        }else if(s.length()!=8){
                            ActivityUtils.showToastForFail(context,"验证码长度不正确");
                        }

                        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.YANZHENG_VIP);
                        params.addQueryStringParameter("userid", UserUtil.getInstance(context).getUserId());
                        params.addQueryStringParameter("memeberCode", s);
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String s) {
                                Gson gson = new Gson();
                                java.lang.reflect.Type type = new TypeToken<Bean>() {
                                }.getType();
                                Bean bean = gson.fromJson(s, type);
                                if (bean != null && bean.getCode().equals("SUCCESS")) {
                                    ActivityUtils.showToastForSuccess(context,"恭喜您，已验证成功");
                                    listener.onRefreshAdapter();
                                    dismissMyDialog();
                                }else{
                                    ActivityUtils.showToastForFail(context,bean.getCodeInfo());
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
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
            case MyPreferences.SHOW_GOODS_ADDRESS:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.dialog_good_address);
                Log.d(TAG, "ssss==" + message);
                String[] s = message.split("@@");
                Log.d("dialog", s.length + "-------s[0]=" + s[0]);
                final EditText textView12 = (EditText) dialog.findViewById(R.id.address);
                final EditText textView13 = (EditText) dialog.findViewById(R.id.name);
                final EditText textView14 = (EditText) dialog.findViewById(R.id.phone);
                final TextView tv_city = (TextView) dialog.findViewById(R.id.city);
                String[] address = s[0].split(split,2);
                if(address.length == 1){
                    tv_city.setText(address[0]);
                    textView12.setText("");
                } else if(address.length == 2){
                    tv_city.setText(address[0]);
                    textView12.setText(address[1]);
                }
                textView13.setText(s[1]);
                textView14.setText(s[2]);
                LinearLayout button12 = (LinearLayout) dialog.findViewById(R.id.cancel);
                button12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissMyDialog();
                    }
                });
                Button button1 = (Button) dialog.findViewById(R.id.btn_defaul);
                Button button2 = (Button) dialog.findViewById(R.id.btn_xiugai);


                tv_city.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(context);
                        mChangeAddressDialog.setAddress("北京市", "北京市", "东城区");
                        mChangeAddressDialog.show();
                        mChangeAddressDialog.setAddresskListener(new OnAddressCodeCListener() {
                            @Override
                            public void onClick(String province, String city, String area, String code) {
                                if (city.endsWith("市") || city.endsWith("区") || city.endsWith("县")) {
                                    if (area == null || area.equals("")) {
                                        tv_city.setText(province + "-" + city);
                                    } else {
                                        tv_city.setText(province + "-" + city + "-" + area);
                                    }
                                } else {
                                    if (area == null || area.equals("")) {
                                        tv_city.setText(province + "-" + city);
                                    } else {
                                        tv_city.setText(province + "-" + city + "-" + area);
                                    }
                                }
                            }
                        });
                    }
                });

                button1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(textView12.getText().toString()) || TextUtils.isEmpty(textView13.getText().toString())) {
                            ActivityUtils.showToastForFail(context, "填写信息不可为空");
                            return;
                        }
                        if (!TextUtils.isEmpty(textView14.getText().toString()) && textView14.getText().toString().matches("^[1][34578][0-9]{9}$")) {

                        } else if (textView14.getText().toString().matches("[\u4E00-\u9FA5a]+")) {
                            ActivityUtils.showToastForFail(context, "请不要输入中文!");
                            return;
                        } else if (TextUtils.isEmpty(textView14.getText().toString())) {
                            ActivityUtils.showToastForFail(context, "请输入手机号码！");
                            return;
                        } else {
                            ActivityUtils.showToastForFail(context, "手机号码格式不正确！");
                            return;
                        }

                        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.ZHIBO_GOODS_SETADDRESS);
                        params.addQueryStringParameter("user.userId", UserUtil.getInstance(context).getUserId());
                        params.addQueryStringParameter("name", textView13.getText().toString());
                        params.addQueryStringParameter("address", tv_city.getText().toString()+" "+textView12.getText().toString());
                        params.addQueryStringParameter("telephone", textView14.getText().toString());
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String s) {
                                Gson gson = new Gson();
                                java.lang.reflect.Type type = new TypeToken<Bean>() {
                                }.getType();
                                Bean bean = gson.fromJson(s, type);
                                if (bean != null && bean.getCode().equals("SUCCESS")) {
                                    GoodsDetailBean detail = GoodsDetailUtils.getInstance(context).getBean();
                                    detail.setAddress(tv_city.getText().toString()+" "+textView12.getText().toString());
                                    detail.setName(textView13.getText().toString());
                                    detail.setTelephone(textView14.getText().toString());
                                    GoodsDetailUtils.getInstance(context).setBean(detail);

                                    listener.onRefreshAdapter();

                                    ActivityUtils.showToast(context, "信息修改成功");
                                    dismissMyDialog();
                                } else {
                                    ActivityUtils.showToastForFail(context, "信息设置失败");
                                }
                            }

                            @Override
                            public void onError(Throwable throwable, boolean b) {
                                ActivityUtils.showToastForFail(context, "信息设置失败");
                            }

                            @Override
                            public void onCancelled(CancelledException e) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });

                    }
                });
                button2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(textView12.getText().toString()) || TextUtils.isEmpty(textView13.getText().toString())) {
                            ActivityUtils.showToastForFail(context, "填写信息不可为空");
                            return;
                        }
                        if (!TextUtils.isEmpty(textView14.getText().toString()) && textView14.getText().toString().matches("^[1][34578][0-9]{9}$")) {

                        } else if (textView14.getText().toString().matches("[\u4E00-\u9FA5a]+")) {
                            ActivityUtils.showToastForFail(context, "请不要输入中文!");
                            return;
                        } else if (TextUtils.isEmpty(textView14.getText().toString())) {
                            ActivityUtils.showToastForFail(context, "请输入手机号码！");
                            return;
                        } else {
                            ActivityUtils.showToastForFail(context, "手机号码格式不正确！");
                            return;
                        }
                        GoodsDetailBean detail = GoodsDetailUtils.getInstance(context).getBean();
                        detail.setAddress(tv_city.getText().toString()+" "+textView12.getText().toString());
                        detail.setName(textView13.getText().toString());
                        detail.setTelephone(textView14.getText().toString());
                        GoodsDetailUtils.getInstance(context).setBean(detail);

                        listener.onRefreshAdapter();

                        ActivityUtils.showToast(context, "信息修改成功");
                        dismissMyDialog();
                    }
                });

                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                lp.y = DensityUtil.dip2px(context, 100);
                dialogWindow.setAttributes(lp);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;


            case MyPreferences.SHOW_GOODS_FAPIAO:
                dialog = new Dialog(context, R.style.my_dialog);
                dialog.setContentView(R.layout.dialog_good_fapiao);
                final EditText textView1 = (EditText) dialog.findViewById(R.id.taitou);
                TextView textView2 = (TextView) dialog.findViewById(R.id.price);
                Log.d(TAG, "ssss==" + message);
                String[] s1 = message.split("@@");
                if (s1.length == 2) {
                    if (s1[0].equals("null"))
                        textView1.setText("");
                    else
                        textView1.setText(s1[0]);
                    textView2.setText(s1[1]);
                } else {
                    textView1.setText("");
                    textView2.setText(s1[0]);
                }
                LinearLayout button13 = (LinearLayout) dialog.findViewById(R.id.cancel);

                button13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissMyDialog();
                    }
                });
                Button button3 = (Button) dialog.findViewById(R.id.btn_defaul);
                Button button4 = (Button) dialog.findViewById(R.id.btn_xiugai);
                button3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(textView1.getText().toString())) {
                            ActivityUtils.showToastForFail(context, "填写信息不可为空");
                            return;
                        }

                        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.ZHIBO_GOODS_SETFAPIAO);
                        params.addQueryStringParameter("user.userId", UserUtil.getInstance(context).getUserId());
                        params.addQueryStringParameter("invoiceTitle", textView1.getText().toString());
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String s) {
                                Gson gson = new Gson();
                                java.lang.reflect.Type type = new TypeToken<Bean>() {
                                }.getType();
                                Bean bean = gson.fromJson(s, type);
                                if (bean != null && bean.getCode().equals("SUCCESS")) {
                                    GoodsDetailBean detail = GoodsDetailUtils.getInstance(context).getBean();
                                    detail.setFapiao(textView1.getText().toString());
                                    GoodsDetailUtils.getInstance(context).setBean(detail);
                                    ActivityUtils.showToast(context, "信息修改成功");
                                    listener.onRefreshAdapter();
                                    dismissMyDialog();
                                } else {
                                    ActivityUtils.showToastForFail(context, "信息设置失败");
                                }
                            }

                            @Override
                            public void onError(Throwable throwable, boolean b) {
                                ActivityUtils.showToastForFail(context, "信息设置失败");
                            }

                            @Override
                            public void onCancelled(CancelledException e) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }
                });
                button4.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(textView1.getText().toString())) {
                            ActivityUtils.showToastForFail(context, "填写信息不可为空");
                            return;
                        }
                        GoodsDetailBean detail = GoodsDetailUtils.getInstance(context).getBean();
                        detail.setFapiao(textView1.getText().toString());
                        GoodsDetailUtils.getInstance(context).setBean(detail);
                        ActivityUtils.showToast(context, "信息修改成功");
                        dismissMyDialog();
                        listener.onRefreshAdapter();
                    }
                });
                Window dialogWindow1 = dialog.getWindow();
                WindowManager.LayoutParams lp1 = dialogWindow1.getAttributes();
                dialogWindow1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                lp1.y = DensityUtil.dip2px(context, 100);
                dialogWindow1.setAttributes(lp1);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
        }
    }


    // TODO 取消对话框
    public static void dismissMyDialog() {
        try {
            if (null != dialog) {
                dialog.cancel();
                dialog = null;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
