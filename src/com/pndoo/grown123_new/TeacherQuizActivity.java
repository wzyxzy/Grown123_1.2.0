package com.pndoo.grown123_new;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * ------------------------------------------------------------------
 * 创建时间：2015-10-16 下午2:10:41 项目名称：jiayue
 * 
 * @author Ping Wang
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：TeacherBindLogin.java 类说明：
 *        ------------------------------------------------------------------
 */
/**
* Title: TeacherQuizActivity
* Description: 
* Company: btpd 
* @author Ping Wang
* @date 2015-10-27
*/
@SuppressWarnings("static-access")
@SuppressLint("InflateParams")
public class TeacherQuizActivity extends BaseActivity
		implements
			OnClickListener {
//	private LinearLayout btn_quiz;
//	private LinearLayout quiz_list;
//	private TextView tv_header_title;
//	private View left_line;
//	private View right_line;
//	private ViewPager viewPager;
//	private ArrayList<View> pageview;
//
//	private Button book_select;
//	private Button exam_select;
//	private Button one_level;
//	private Button two_level;
//
////	private Button exam_code;
//	private EditText exam_name;
//	private ListView listview;
//	private Button send_exam;
//
//
//	private TextView exam_number;
//	private TextView exam_designation;
//	private Button curriculum_name;
//	private CheckBox checkbox_slelect;
//	private EditText et_time;
//	private TextView tv_suggest;
//	private CommonAdapter<SelectQuesitons> adapter;
//
//	private static final int STEP1 = 123;
//	private static final int STEP2 = 124;
//	private static final int STEP3 = 125;
//	private static final int STEP4 = 126;
//	private static final int STEP5 = 127;
//	private View view1;
//	private View view2;
//	private View view3;
//	private LayoutInflater inflater;
//	private ListView listview1;
//	
//	private TaskManager tm;
//	private CommentController commentController;
//	private List<BookSeletct> bookSeletcts;
//	private List<TextOneBookDirs> oneBookDirs;
//	private List<TextTwoBookDirs> twoBookDirs;
//	private List<SelectQuesitons> selectQuesitons;
//	private List<TestPaper> papers;
//	private TestQuestions questions;
//	private String bookId;
//	private String dirId1;
//	private String dirId2;
//	private String paperId;
//	private String testName;
//	private String claTesPapId;
//	private String paperCode;
//	private String courseId;
//	private int count;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_teacher_quiz);
//		tm = getApplicationContext().getTaskManager();
//		commentController = IoC.getInstance(CommentController.class);
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
//		viewPager = (ViewPager) findViewById(R.id.viewPager);
//		left_line = (View) findViewById(R.id.left_line);
//		right_line = (View) findViewById(R.id.right_line);
//		btn_quiz = (LinearLayout) findViewById(R.id.btn_quiz);
//		quiz_list = (LinearLayout) findViewById(R.id.quiz_list);
//		listview1 = (ListView) findViewById(R.id.listview);
//		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
//		tv_header_title.setText("随堂测试");
//		loadViewPager();
//		setOnclick(btn_quiz, quiz_list, book_select, exam_select, one_level,
//				two_level, /*save_exam,*/ send_exam, curriculum_name);
//	}
//	private void loadViewPager() {
//
//		inflater = getLayoutInflater();
//
//		view1 = inflater.inflate(R.layout.teacher_quiz_item01, null);
//		book_select = (Button) view1.findViewById(R.id.book_select);
//		exam_select = (Button) view1.findViewById(R.id.exam_select);
//		one_level = (Button) view1.findViewById(R.id.one_level);
//		two_level = (Button) view1.findViewById(R.id.two_level);
//
//		view2 = inflater.inflate(R.layout.teacher_quiz_item02, null);
////		exam_code = (Button) view2.findViewById(R.id.exam_code);
//		exam_name = (EditText) view2.findViewById(R.id.exam_name);
//		listview = (ListView) view2.findViewById(R.id.listview);
//	
////		save_exam = (Button) view2.findViewById(R.id.save_exam);
//		send_exam = (Button) view2.findViewById(R.id.send_exam);
//
//		view3 = inflater.inflate(R.layout.teacher_quiz_item03, null);
//		exam_number = (TextView) view3.findViewById(R.id.exam_number);
//		exam_designation = (TextView) view3.findViewById(R.id.exam_designation);
//		curriculum_name = (Button) view3.findViewById(R.id.curriculum_name);
//		checkbox_slelect = (CheckBox) view3.findViewById(R.id.checkbox_slelect);
//		et_time = (EditText) view3.findViewById(R.id.et_time);
//		tv_suggest = (TextView) view3.findViewById(R.id.tv_suggest);
//		pageview = new ArrayList<View>();
//		pageview.add(view1);
//		pageview.add(view2);
//		pageview.add(view3);
//		loadViewPagerAdapter();
//
//	}
//
//	private void loadViewPagerAdapter() {
//		PagerAdapter mPagerAdapter = new PagerAdapter() {
//
//			@Override
//			// 获取当前窗体界面数
//			public int getCount() {
//				return pageview.size();
//			}
//
//			@Override
//			// 断是否由对象生成界面
//			public boolean isViewFromObject(View arg0, Object arg1) {
//				return arg0 == arg1;
//			}
//
//			// 是从ViewGroup中移出当前View
//			public void destroyItem(View arg0, int arg1, Object arg2) {
//				((ViewPager) arg0).removeView(pageview.get(arg1));
//			}
//
//			// 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
//			public Object instantiateItem(View arg0, int arg1) {
//				((ViewPager) arg0).addView(pageview.get(arg1));
//
//				return pageview.get(arg1);
//			}
//
//		};
//		viewPager.setAdapter(mPagerAdapter);
//	}
//
//	private void loadListViewAdapter() {
//		selectQuesitons = questions.getSelectQuesitons();
//		adapter = new CommonAdapter<SelectQuesitons>(this,selectQuesitons, R.layout.teacher_exam_item) {
//
//			@Override
//			public void convert(ViewHolder helper, SelectQuesitons item,
//					final int postion) {
//
//				helper.setText(R.id.tv_exam_question,item.getName());
//				List<SelectOptions> selectOptions = item.getSelectOptions();
//				StringBuffer bf = new StringBuffer();
//				for (int i = 0; i < selectOptions.size(); i++) {
//					bf.append(selectOptions.get(i).getOptionCode()+"."+selectOptions.get(i).getName()+"\n");
//				}
//				helper.setText(R.id.tv_exam_select, bf.toString());
//
//				helper.setOnClickListener(R.id.checkbox, new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						if (isSelected.get(postion)) {
//							isSelected.put(postion, false);
//							setIsSelected(isSelected);
//						} else {
//							isSelected.put(postion, true);
//							setIsSelected(isSelected);
//						}
////						ActivityUtils.showToast(TeacherQuizActivity.this,
////								selectQuesitons.get(postion).getName() + postion);
//
//					}
//
//				});
//
//				helper.setIsChecked(R.id.checkbox, getIsSelected().get(postion));
//			}
//		};
//		listview.setAdapter(adapter);
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
//	/**
//	 * <p>
//	 * Title
//	 * </p>
//	 * : btnLogin Description:确认绑定按钮
//	 * 
//	 * @param v
//	 */
//	public void btnLogin(View v) {
//		String book = book_select.getText().toString().trim();
//		String exam = exam_select.getText().toString().trim();
//		String oneLevel = one_level.getText().toString().trim();
//		String twoLevel = two_level.getText().toString().trim();
//		if (TextUtils.isEmpty(book)) {
//			ActivityUtils.showToastForFail(this, "书籍选择不能为空");
//			return;
//		}
//		if (TextUtils.isEmpty(exam)) {
//			ActivityUtils.showToastForFail(this, "试卷选择不能为空");
//			return;
//		}
//		if (TextUtils.isEmpty(oneLevel)) {
//			ActivityUtils.showToastForFail(this, "一级列表不能为空");
//			return;
//		}
//		if (TextUtils.isEmpty(twoLevel)) {
//			ActivityUtils.showToastForFail(this, "二级列表不能为空");
//			return;
//		}
//		if (paperId!=null) {
//			ActivityUtils.showToast(this, paperId);
//			getPaperInfo(paperId);
//		}
//
//		viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//	}
//	public void btnSend(View v) {
//		if (checkbox_slelect.isChecked()) {
////			String time = et_time.getText().toString().trim();
//		}
//		confirmSend(claTesPapId, courseId);
//	}
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
//	
	@Override
	public void onClick(View v) {
//		Intent intent;
//		switch (v.getId()) {
//			case R.id.btn_quiz :
//				left_line.setBackgroundColor(getResources().getColor(
//						R.color.background));
//				right_line.setBackgroundColor(getResources().getColor(
//						R.color.login_hint_color));
//				viewPager.setVisibility(View.VISIBLE);
//
//				break;
//			case R.id.quiz_list :
//				viewPager.setVisibility(View.GONE);
//				left_line.setBackgroundColor(getResources().getColor(
//						R.color.login_hint_color));
//				right_line.setBackgroundColor(getResources().getColor(
//						R.color.background));
//				ArrayList<String> list = new ArrayList<>();
//				list.add("hh");
//				list.add("hh");
//				list.add("hh");
//				listview1.setAdapter(new CommonAdapter<String>(this, list,
//						R.layout.quiz_list_item) {
//
//					@Override
//					public void convert(ViewHolder helper, String item,
//							int postion) {
//						
//
//					}
//				});
//				listview1.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view,
//							int position, long id) {
//						Intent intent=new Intent(TeacherQuizActivity.this,TeacherTestResultActivity.class);
//						startActivity(intent);
//					}
//				});
//
//				break;
//			case R.id.book_select :
//				getTeaClaTesTextbook();
//				reset(one_level,two_level,exam_select);
//				break;
//			case R.id.one_level :
//				getTextBookOneDirs(bookId);
//				reset(two_level,exam_select);
//				break;
//			case R.id.two_level :
//				getTextBookTwoDirs(dirId1);
//				reset(exam_select);
//				break;
//			case R.id.exam_select :
//				getTwoDirPapers(dirId2);
//				
//				break;
////			case R.id.save_exam :
////				ActivityUtils.showToast(this, "1");
////				break;
//			case R.id.send_exam :
//		
//			
//				HashMap<Integer, Boolean> hashMap = adapter.getIsSelected();
//				StringBuffer sb = new StringBuffer();
//				
//				for (int i = 0; i < hashMap.size(); i++) {
//					if (hashMap.get(i)) {
//						String test_id = selectQuesitons.get(i).getId();
//						sb.append(test_id+",");
//						count++;
//					}
//				}
//				testName = exam_name.getText().toString().trim();
//				if (!TextUtils.isEmpty(testName)) {
//					exam_name.setText(testName);
//					exam_designation.setText("试卷名称："+testName);
//				}else {
//					ActivityUtils.showToast(TeacherQuizActivity.this, "请输入试题名称");
//					return;
//				}
//				if (sb.length()==0) {
//					ActivityUtils.showToast(TeacherQuizActivity.this, "您还没有选择试题");
//				}else {
//					String queIds = sb.deleteCharAt(sb.length()-1).toString();
//					tv_suggest.setText("(共"+count+"道题，建议答题时间为"+questions.getUsedTime()+"分钟)");
//					ActivityUtils.showToast(TeacherQuizActivity.this, queIds);
//					insertPaperInfo(paperId, "1", queIds, testName);
//					
//				}
//			
//				break;
//
//			case R.id.curriculum_name :
//				intent = new Intent(this, TeacherCurriculumActivity.class);
//				
//				startActivityForResult(intent, STEP1);
//				
//				break;
//
//			default :
//				break;
//		}

	}
//	private void reset(TextView ...v){
//		for (int i = 0; i < v.length; i++) {
//			v[i].setText(null);
//		}
//		
//	}
//	/**
//	* <p>Title</p>: getTeaClaTesTextbook
//	* Description: 获取书籍列表
//	*/
//	protected void getTeaClaTesTextbook() {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("teacherId", "1");	
//	
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
//				DialogUtils.showMyDialog(TeacherQuizActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherQuizActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherQuizActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							bookSeletcts = commentJson.getBookSeletcts();
//							Log.i("msg", bookSeletcts.toString());
//							if (bookSeletcts!=null) {
//								Intent intent = new Intent(TeacherQuizActivity.this, TeacherSelectActivity.class);
//								intent.putExtra("bookSelect", (Serializable)bookSeletcts);
//								intent.putExtra("select", "1");
//								startActivityForResult(intent, STEP2);
//							}
//
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherQuizActivity.this,
//									MyPreferences.SHOW_ERROR_DIALOG, "获取失败",
//									commentJson.getCodeInfo(), null);
//							return;
//						}
//					}
//				}
//
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
//					errorMsg = commentController.getTeaClaTesTextbook(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//
//
//	}
//	
//	/**
//	* <p>Title</p>: getTextBookOneDirs
//	* Description: 获取一级目录
//	* @param bookId
//	*/
//	protected void getTextBookOneDirs(String bookId) {
//		if (bookId==null) {
//			ActivityUtils.showToast(this, "请选择书籍");
//			return;
//		}
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("id", bookId);
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
//				DialogUtils.showMyDialog(TeacherQuizActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherQuizActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherQuizActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							   oneBookDirs = commentJson.getOneBookDirs();
//							Log.i("msg", oneBookDirs.toString());
//							if (oneBookDirs!=null) {
//								
//								Intent intent = new Intent(TeacherQuizActivity.this, TeacherSelectActivity.class);
//								intent.putExtra("oneBookDirs", (Serializable)oneBookDirs);
//								intent.putExtra("select", "2");
//								startActivityForResult(intent, STEP3);
//								
//							}
//
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherQuizActivity.this,
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
//					errorMsg = commentController.getTextBookOneDirs(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//
//
//	}
//	
//	/**
//	* <p>Title</p>: getTextBookTwoDirs
//	* Description: 获取二级目录
//	* @param dirId
//	*/
//	protected void getTextBookTwoDirs(String dirId) {
//		if (dirId==null) {
//			ActivityUtils.showToast(this, "请选择一级目录");
//			return;
//		}
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("dirId", dirId);
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
//				DialogUtils.showMyDialog(TeacherQuizActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//			
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherQuizActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherQuizActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							twoBookDirs = commentJson.getTwoBookDirs();
//							Log.i("msg", twoBookDirs.toString());
//							if (twoBookDirs!=null) {
//								
//								Intent intent = new Intent(TeacherQuizActivity.this, TeacherSelectActivity.class);
//								intent.putExtra("twoBookDirs", (Serializable)twoBookDirs);
//								intent.putExtra("select", "3");
//								startActivityForResult(intent, STEP4);
//								
//							}
//							
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherQuizActivity.this,
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
//					errorMsg = commentController.getTextBookTwoDirs(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//		
//		
//	}
//	/**
//	* <p>Title</p>: getTwoDirPapers
//	* Description: 获取试卷列表
//	* @param dirId
//	*/
//	protected void getTwoDirPapers(String dirId) {
//		if (dirId==null) {
//			ActivityUtils.showToast(this, "请选择二级目录");
//			return;
//		}
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("dirId", dirId);
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
//				DialogUtils.showMyDialog(TeacherQuizActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//			
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherQuizActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherQuizActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							papers = commentJson.getPapers();
//							Log.i("msg", papers.toString());
//							if (papers!=null) {
//								
//								Intent intent = new Intent(TeacherQuizActivity.this, TeacherSelectActivity.class);
//								intent.putExtra("papers", (Serializable)papers);
//								intent.putExtra("select", "4");
//								startActivityForResult(intent, STEP5);
//								
//							}
//							
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherQuizActivity.this,
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
//					errorMsg = commentController.getTwoDirPapers(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//	}
//	/**
//	 * <p>Title</p>: getPaperInfo
//	 * Description: 获取试卷内容
//	 * @param dirId
//	 */
//	protected void getPaperInfo(String paperId) {
//		if (paperId==null) {
//			ActivityUtils.showToast(this, "请选择试卷");
//			return;
//		}
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("paperId", paperId);
//		tm.createNewTask(new TaskListener() {
//
//			@Override
//			public String getName() {
//				return null;
//			}
//			
//			@Override
//			public void onPreExecute(BaseTask task) {
//				DialogUtils.showMyDialog(TeacherQuizActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//			
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherQuizActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherQuizActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							questions = commentJson.getQuestions();
//							Log.i("msg", questions.toString());
//							if (questions!=null) {
//								loadListViewAdapter();
//								et_time.setText(questions.getUsedTime());
//								et_time.setSelection(et_time.getSelectionStart()+1);
//								
//							}
//							
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherQuizActivity.this,
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
//					errorMsg = commentController.getPaperInfo(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//	}
//	/**
//	 * <p>Title</p>: getPaperInfo
//	 * Description: 发送试卷
//	 * @param dirId
//	 */
//	protected void insertPaperInfo(String paperId,String teacherId,String queIds,String paperName) {
//
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("paperId", paperId);
//		params.put("teacherId", teacherId);
//		params.put("queIds", queIds);
//		params.put("paperName", paperName);
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
//				DialogUtils.showMyDialog(TeacherQuizActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//			
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherQuizActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherQuizActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							PaperInfo paperInfo= commentJson.getPaperInfo();
//							Log.i("msg", paperInfo.toString());
//							if (paperInfo!=null) {
//								claTesPapId = paperInfo.getClaTesPapId();
//								paperCode = paperInfo.getPaperCode();
//								viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//							}
//							
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherQuizActivity.this,
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
//					errorMsg = commentController.insertPaperInfo(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//	}
//	/**
//	 * <p>Title</p>: getPaperInfo
//	 * Description: 发送试卷
//	 * @param dirId
//	 */
//	protected void confirmSend(String claTesPapId,String courseIds) {
//		
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("claTesPapId", claTesPapId);
//		params.put("courseIds", courseIds);
//
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
//				DialogUtils.showMyDialog(TeacherQuizActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
//			}
//			
//			@Override
//			public void onPostExecute(BaseTask task, String errorMsg) {
//				DialogUtils.dismissMyDialog();
//				if (errorMsg != null) {// 获取数据出现异常
//					DialogUtils.showMyDialog(TeacherQuizActivity.this,
//							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
//							null);
//				} else {
//					CommentJson commentJson = commentController.getModel();
//					if (null == commentJson || null == commentJson.getCode()) {
//						DialogUtils.showMyDialog(
//								TeacherQuizActivity.this,
//								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
//								"获取信息失败", null);
//						return;
//					} else {
//						if (commentJson.getCode().equals("SUCCESS")) {
//							ActivityUtils.showToastForSuccess(TeacherQuizActivity.this, "发送成功");
//							
//							return;
//						} else if (commentJson.getCode().equals("FAIL")) {
//							DialogUtils.showMyDialog(
//									TeacherQuizActivity.this,
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
//					errorMsg = commentController.confirmSend(param);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return errorMsg;
//			}
//		}).execute(params);
//	}
//	
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (data == null) {
//			return;
//		}
//		switch (requestCode) {
//			case STEP1 :
//				curriculum_name.setText(data.getStringExtra("name"));
//				courseId = data.getStringExtra("id");
//				String courseCode = data.getStringExtra("code");
//				break;
//			case STEP2 :
//				book_select.setText(data.getStringExtra("book_select"));
//				bookId = data.getStringExtra("book_id");
//				
//				break;
//			case STEP3 :
//				one_level.setText(data.getStringExtra("book_select"));
//				dirId1 = data.getStringExtra("dirId1");
//
//				break;
//			case STEP4 :
//				two_level.setText(data.getStringExtra("book_select"));
//				dirId2 = data.getStringExtra("dirId2");
//			
//				break;
//			case STEP5 :
//				paperId = data.getStringExtra("id");
//				String name = data.getStringExtra("book_select");
//				String code = data.getStringExtra("code");
//				exam_select.setText(name);
////				exam_code.setText(code);
////				exam_name.setText(name);
//				exam_number.setText("试卷编号："+code);
//				
//				break;
//
//			default :
//				break;
//		}
//
//	}
}
