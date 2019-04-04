package com.pndoo.grown123_new;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * ------------------------------------------------------------------
 * 创建时间：2015-10-16 下午2:10:41 项目名称：wyst
 * 
 * @author Ping Wang
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：TeacherBindLogin.java 类说明：
 * ------------------------------------------------------------------
 */
@SuppressWarnings("static-access")
public class TeacherCurriculumActivity extends BaseActivity
		implements
			OnClickListener {

//	private TextView tv_header_title;
//	private ListView listview;
//
//	private CommonAdapter<Courses> adapter;
//	private ImageButton btn_header_right;
//	private Button btn_new_class;
//	private Button btn_delete_class;
//	private Button btn_confirm_delete;
//	private LinearLayout ll_add;
//	private EditText et_text;
//	private ImageButton btn_add;
//	private int teacher_cancel;
//	boolean flag = true;
//	
//	private TaskManager tm;
//	private CommentController commentController;
//	private List<Courses> courses;
//
//
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		tm = getApplicationContext().getTaskManager();
//		commentController = IoC.getInstance(CommentController.class);
//		setContentView(R.layout.activity_teacher_curriculum);
//
//		initViews();
//	}
//
//	/**
//	 * <p>
//	 * Title
//	 * </p>
//	 * : initViews Description: 初始化view对象
//	 */
//	private void initViews() {
//		listview = (ListView) findViewById(R.id.listview);
//		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
//		btn_header_right = (ImageButton) findViewById(R.id.btn_header_right);
//
//		btn_new_class = (Button) findViewById(R.id.btn_new_class);
//		btn_delete_class = (Button) findViewById(R.id.btn_delete_class);
//		btn_confirm_delete = (Button) findViewById(R.id.btn_confirm_delete);
//		ll_add = (LinearLayout) findViewById(R.id.ll_add);
//		et_text = (EditText) findViewById(R.id.et_text);
//		btn_add = (ImageButton) findViewById(R.id.btn_add);
//		teacher_cancel = getResources().getIdentifier("teacher_cancel",
//				"drawable", getPackageName());
//		setOnclick(btn_header_right, btn_new_class, btn_delete_class,
//				btn_confirm_delete, btn_add, ll_add);
//
//		tv_header_title.setText("课程名称");
//		getCourses("1");
////		loadListViewAdapter();
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent intent = new Intent();
//				intent.putExtra("name", courses.get(position).getGroupName());
//				intent.putExtra("id", courses.get(position).getId());
//				intent.putExtra("code", courses.get(position).getGroupCode());
//				setResult(Activity.RESULT_OK, intent);
//				finish();
//
//			}
//		});
//	}
//
//	private void loadListViewAdapter() {
//		adapter = new CommonAdapter<Courses>(this, courses,
//				R.layout.teacher_curriculum_item) {
//
//			@Override
//			public void convert(ViewHolder helper, Courses item,
//					final int postion) {
//
//				helper.setText(R.id.tv_exam, item.getGroupName());
//				if (flag) {
//					helper.setHiddle(R.id.checkbox);
//				} else {
//					helper.setDisplay(R.id.checkbox);
//					helper.setOnClickListener(R.id.checkbox,
//							new OnClickListener() {
//
//								@Override
//								public void onClick(View v) {
//									if (isSelected.get(postion)) {
//										isSelected.put(postion, false);
//										setIsSelected(isSelected);
//									} else {
//										isSelected.put(postion, true);
//										setIsSelected(isSelected);
//										ActivityUtils.showToast(
//												TeacherCurriculumActivity.this,
//												courses.get(postion).getId() + courses.get(postion).getGroupCode());
//									}
//
//								}
//
//							});
//
//					helper.setIsChecked(R.id.checkbox,
//							getIsSelected().get(postion));
//				}
//			}
//		};
//		listview.setAdapter(adapter);
//		
//	}
//	/**
//	 * Title: setOnclick Description: 设置OnClickListener
//	 * 
//	 * @param v
//	 */
//	public void setOnclick(View... v) {
//		for (int i = 0; i < v.length; i++) {
//			v[i].setOnClickListener(this);
//		}
//
//	}
//
//	/**
//	 * <p>
//	 * Title
//	 * </p>
//	 * : btnBack Description: 返回按钮
//	 * 
//	 * @param v
//	 */
//	public void btnBack(View v) {
//		this.finish();
//	}
//
	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		// 取消按钮
//			case R.id.btn_header_right :
//				btn_header_right.setVisibility(View.GONE);
//				btn_new_class.setVisibility(View.VISIBLE);
//				btn_delete_class.setVisibility(View.VISIBLE);
//				ll_add.setVisibility(View.GONE);
//				btn_confirm_delete.setVisibility(View.GONE);
//				flag = true;
//				adapter.notifyDataSetChanged();
//				listview.setEnabled(true);
//				break;
//			// 新建课程
//			case R.id.btn_new_class :
//				btn_new_class.setVisibility(View.GONE);
//				btn_delete_class.setVisibility(View.GONE);
//				btn_header_right.setBackgroundResource(teacher_cancel);
//				btn_header_right.setVisibility(View.VISIBLE);
//				ll_add.setVisibility(View.VISIBLE);
//				listview.setEnabled(false);
//				break;
//			// 删除课程
//			case R.id.btn_delete_class :
//				btn_new_class.setVisibility(View.GONE);
//				btn_delete_class.setVisibility(View.GONE);
//				btn_header_right.setBackgroundResource(teacher_cancel);
//				btn_header_right.setVisibility(View.VISIBLE);
//				btn_confirm_delete.setVisibility(View.VISIBLE);
//				ll_add.setVisibility(View.GONE);
//				flag = false;
//				adapter.notifyDataSetInvalidated();
//				break;
//			// 确认删除
//			case R.id.btn_confirm_delete :
//				StringBuffer bf=new StringBuffer();
//				for (int j = 0; j < courses.size(); j++) {
//					if (adapter.getIsSelected().get(j)) {
//						bf.append(courses.get(j).getId()+",");
//						courses.get(j).setGroupName("*");
//					}
//				}
//				deleteCourses(bf.deleteCharAt(bf.length()-1).toString());
//				btn_header_right.setVisibility(View.GONE);
//				btn_new_class.setVisibility(View.VISIBLE);
//				btn_delete_class.setVisibility(View.VISIBLE);
//				btn_confirm_delete.setVisibility(View.GONE);
//				flag = true;
//				
//				// ActivityUtils.showToast(TeacherCurriculumActivity.this,
//				// list.size()+"");
//				break;
//			// 确定添加
//			case R.id.btn_add :
//				String text = et_text.getText().toString().trim();
//				if (TextUtils.isEmpty(text)) {
//					ActivityUtils.showToast(this, "请输入课程名称");
//					return;
//				}
//
//				addCourse(text, "1");
//				
//				et_text.setText("");
//				ll_add.setVisibility(View.GONE);
//				btn_header_right.setVisibility(View.GONE);
//				btn_new_class.setVisibility(View.VISIBLE);
//				btn_delete_class.setVisibility(View.VISIBLE);
//				listview.setEnabled(true);
//				
//
//				break;
//
//			default :
//				break;
//		}
	}
//	
//	
//	/**
//	* <p>Title</p>: getCourses
//	* Description:获取课程列表 
//	* @param teacherId
//	*/
//	protected void getCourses(String teacherId) {
//		if (teacherId==null) {
//			ActivityUtils.showToast(this, "请选择试卷");
//			return;
//		}
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("teacherId", teacherId);
//		tm.createNewTask(new TaskListener() {
//
//			
//
//			@Override
//			public String getName() {
//				return null;
//			}
//			
//			@Override
//			public void onPreExecute(BaseTask task) {
//				DialogUtils.showMyDialog(TeacherCurriculumActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//			
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherCurriculumActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherCurriculumActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							 courses = commentJson.getCourses();
//							Log.i("msg", courses.toString());
//							if (courses!=null) {
//								loadListViewAdapter();
//							}
//							
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherCurriculumActivity.this,
//									MyPreferences.SHOW_ERROR_DIALOG, "获取失败",
//									commentJson.getCodeInfo(), null);
//							return;
//						}
//					}
//				}
//			}
//			
//			@Override
//			public void onProgressUpdate(BaseTask task, Object param) {
//			}
//			
//			@Override
//			public void onCancelled(BaseTask task) {
//			}
//			
//			@Override
//			public String onDoInBackground(BaseTask task,
//					MultiValueMap<String, String> param) {
//				String errorMsg = null;
//				try {
//					errorMsg = commentController.getCourses(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//	}
//	/**
//	 * <p>Title</p>: deleteCourses
//	 * Description:删除课程 
//	 * @param teacherId
//	 */
//	protected void deleteCourses(String courseIds) {
//		if (courseIds==null) {
//			ActivityUtils.showToast(this, "请选择试卷");
//			return;
//		}
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("courseIds", courseIds);
//		tm.createNewTask(new TaskListener() {
//			
//			
//			
//			@Override
//			public String getName() {
//				return null;
//			}
//			
//			@Override
//			public void onPreExecute(BaseTask task) {
//				DialogUtils.showMyDialog(TeacherCurriculumActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//			
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherCurriculumActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherCurriculumActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
////							courses = commentJson.getCourses();
////							Log.i("msg", courses.toString());
////							if (courses!=null) {
////								loadListViewAdapter();
////							}
//							Iterator<Courses> it = courses.iterator();
//							while (it.hasNext()) {
//								Courses value = (Courses) it.next();
//								if (value.getGroupName().equals("*")) {
//									it.remove();
//								}
//							}
//							adapter.dataChange(courses);
//							adapter.notifyDataSetChanged();
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherCurriculumActivity.this,
//									MyPreferences.SHOW_ERROR_DIALOG, "获取失败",
//									commentJson.getCodeInfo(), null);
//							return;
//						}
//					}
//				}
//			}
//			
//			@Override
//			public void onProgressUpdate(BaseTask task, Object param) {
//			}
//			
//			@Override
//			public void onCancelled(BaseTask task) {
//			}
//			
//			@Override
//			public String onDoInBackground(BaseTask task,
//					MultiValueMap<String, String> param) {
//				String errorMsg = null;
//				try {
//					errorMsg = commentController.deleteCourses(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//	}
//	/**
//	 * <p>Title</p>: addCourse
//	 * Description:添加课程 
//	 * @param teacherId
//	 */
//	protected void addCourse(String courseName,String teacherId) {
//		if (courseName==null&&courseName==null) {
//			ActivityUtils.showToast(this, "请选择试卷");
//			return;
//		}
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("courseName", courseName);
//		params.put("teacherId", teacherId);
//		tm.createNewTask(new TaskListener() {
//			
//			
//			
//			@Override
//			public String getName() {
//				return null;
//			}
//			
//			@Override
//			public void onPreExecute(BaseTask task) {
//				DialogUtils.showMyDialog(TeacherCurriculumActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//			
//			
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherCurriculumActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherCurriculumActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							courses = commentJson.getCourses();
//							adapter.dataChange(courses);
//							loadListViewAdapter();
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherCurriculumActivity.this,
//									MyPreferences.SHOW_ERROR_DIALOG, "获取失败",
//									commentJson.getCodeInfo(), null);
//							return;
//						}
//					}
//				}
//			}
//			
//			@Override
//			public void onProgressUpdate(BaseTask task, Object param) {
//			}
//			
//			@Override
//			public void onCancelled(BaseTask task) {
//			}
//			
//			@Override
//			public String onDoInBackground(BaseTask task,
//					MultiValueMap<String, String> param) {
//				String errorMsg = null;
//				try {
//					errorMsg = commentController.AddCourses(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//	}
}
