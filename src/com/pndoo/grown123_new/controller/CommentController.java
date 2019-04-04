package com.pndoo.grown123_new.controller;

import java.util.List;

import org.springframework.util.MultiValueMap;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.google.inject.Inject;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.base.AbstractController;
import com.pndoo.grown123_new.dto.base.AuthorComment;
import com.pndoo.grown123_new.dto.base.AuthorReply;
import com.pndoo.grown123_new.dto.base.BookSeletct;
import com.pndoo.grown123_new.dto.base.Courses;
import com.pndoo.grown123_new.dto.base.PaperInfo;
import com.pndoo.grown123_new.dto.base.ReaderComment;
import com.pndoo.grown123_new.dto.base.ReaderJoin;
import com.pndoo.grown123_new.dto.base.TestPaper;
import com.pndoo.grown123_new.dto.base.TestQuestions;
import com.pndoo.grown123_new.dto.base.TextOneBookDirs;
import com.pndoo.grown123_new.dto.base.TextTwoBookDirs;
import com.pndoo.grown123_new.rest.IAsyncCallback;
import com.pndoo.grown123_new.rest.IRestService;
import com.pndoo.grown123_new.rest.Response;
import com.pndoo.grown123_new.rest.RestException;
import com.pndoo.grown123_new.rest.RestFault;
import com.pndoo.grown123_new.soap.CommentJson;
import com.pndoo.grown123_new.util.ObjectHelper;
@SuppressWarnings("unchecked")
public class CommentController extends AbstractController<CommentJson> {

	private IRestService restService;
	private ObjectHelper objectHelper;// 同步对象，把第二个参数对象同步到第一个参数对象中
	private CommentJson commentJson = new CommentJson();
	private List<Courses> courses;
	String mErrorMsg = null;

	/**
	 * 作者沟通
	 * 
	 * @param request
	 */
	public String authorComment(MultiValueMap<String, String> request)
			throws RestFault, RestException {

		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.AUTHOR_COMMENT, request,
				new IAsyncCallback() {
					public void onSuccess(Response result) {
						try {
							if (null != result) {
								commentJson.setCode(result.getCode());
								commentJson.setCodeInfo(result.getCodeInfo());
								if (result.getData() != null) {
									AuthorComment comment = new AuthorComment();
									Log.i("other", result.toString());
									objectHelper.syncObjectGraph(comment,
											result.getData());
									Log.e("other", comment.toString());
									commentJson.setAuthorComment(comment);
									setCommentJson(commentJson);
								}
							} else {
								exceptionInfo[0] = "返回的数据为空！";
							}
						} catch (Exception e) {
							setCommentJson(commentJson);
							e.printStackTrace();
							exceptionInfo[0] = e.getMessage();
						}
						notifyStopProgress();
					}

					public void onFailure(Throwable throwable) {
						setCommentJson(commentJson);
						Log.e(CommentController.class.getCanonicalName(),
								throwable.getMessage());
						notifyStopProgress();
						exceptionInfo[0] = throwable.getMessage();
					}
				});
		return exceptionInfo[0];
	}
	/**
	 * 读者沟通
	 * 
	 * @param request
	 */
	public String readerComment(MultiValueMap<String, String> request)
			throws RestFault, RestException {

		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.AUTHOR_COMMENT, request,
				new IAsyncCallback() {
					public void onSuccess(Response result) {
						try {
							if (null != result) {
								commentJson.setCode(result.getCode());
								commentJson.setCodeInfo(result.getCodeInfo());
								if (result.getData() != null) {
									ReaderComment comment = new ReaderComment();
									Log.i("other", result.toString());
									objectHelper.syncObjectGraph(comment,
											result.getData());
									Log.e("other", comment.toString());
									commentJson.setReaderComment(comment);
									setCommentJson(commentJson);
								}
							} else {
								exceptionInfo[0] = "返回的数据为空！";
							}
						} catch (Exception e) {
							setCommentJson(commentJson);
							e.printStackTrace();
							exceptionInfo[0] = e.getMessage();
						}
						notifyStopProgress();
					}

					public void onFailure(Throwable throwable) {
						setCommentJson(commentJson);
						Log.e(CommentController.class.getCanonicalName(),
								throwable.getMessage());
						notifyStopProgress();
						exceptionInfo[0] = throwable.getMessage();
					}
				});
		return exceptionInfo[0];
	}
	/**
	 * 作者发表问题
	 * 
	 * @param request
	 */
	public String authorPostQuest(MultiValueMap<String, String> request)
			throws RestFault, RestException {

		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.AUTHOR_POST_QUESTION, request,
				new IAsyncCallback() {
					public void onSuccess(Response result) {
						try {
							if (null != result) {
								commentJson.setCode(result.getCode());
								commentJson.setCodeInfo(result.getCodeInfo());
								setCommentJson(commentJson);

							} else {
								exceptionInfo[0] = "返回的数据为空！";
							}
						} catch (Exception e) {
							setCommentJson(commentJson);
							e.printStackTrace();
							exceptionInfo[0] = e.getMessage();
						}
						notifyStopProgress();
					}

					public void onFailure(Throwable throwable) {
						setCommentJson(commentJson);
						Log.e(CommentController.class.getCanonicalName(),
								throwable.getMessage());
						notifyStopProgress();
						exceptionInfo[0] = throwable.getMessage();
					}
				});
		return exceptionInfo[0];
	}
	/**
	 * 作者回复获取列表
	 * 
	 * @param request
	 */
	public String authorPostReply(MultiValueMap<String, String> request)
			throws RestFault, RestException {

		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.AUTHOR_POST_LIST, request,
				new IAsyncCallback() {
					public void onSuccess(Response result) {
						try {
							if (null != result) {
								commentJson.setCode(result.getCode());
								commentJson.setCodeInfo(result.getCodeInfo());
								List<AuthorReply> authorReplyLists = objectHelper
										.syncListObject(result.getData(),
												AuthorReply.class);
								commentJson
										.setAuthorReplyLists(authorReplyLists);
								setCommentJson(commentJson);

							} else {
								exceptionInfo[0] = "返回的数据为空！";
							}
						} catch (Exception e) {
							setCommentJson(commentJson);
							e.printStackTrace();
							exceptionInfo[0] = e.getMessage();
						}
						notifyStopProgress();
					}

					public void onFailure(Throwable throwable) {
						setCommentJson(commentJson);
						Log.e(CommentController.class.getCanonicalName(),
								throwable.getMessage());
						notifyStopProgress();
						exceptionInfo[0] = throwable.getMessage();
					}
				});
		return exceptionInfo[0];
	}
	/**
	 * 读者回复获取列表
	 * 
	 * @param request
	 */
	public String readerPostReply(MultiValueMap<String, String> request)
			throws RestFault, RestException {

		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.AUTHOR_POST_LIST, request,
				new IAsyncCallback() {
					public void onSuccess(Response result) {
						try {
							if (null != result) {
								commentJson.setCode(result.getCode());
								commentJson.setCodeInfo(result.getCodeInfo());
								List<ReaderJoin> readerJoins = objectHelper
										.syncListObject(result.getData(),
												ReaderJoin.class);
								commentJson.setReaderJoins(readerJoins);
								setCommentJson(commentJson);

							} else {
								exceptionInfo[0] = "返回的数据为空！";
							}
						} catch (Exception e) {
							setCommentJson(commentJson);
							e.printStackTrace();
							exceptionInfo[0] = e.getMessage();
						}
						notifyStopProgress();
					}

					public void onFailure(Throwable throwable) {
						setCommentJson(commentJson);
						Log.e(CommentController.class.getCanonicalName(),
								throwable.getMessage());
						notifyStopProgress();
						exceptionInfo[0] = throwable.getMessage();
					}
				});
		return exceptionInfo[0];
	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : answerQuestion Description: 回复接口
	 * 
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String answerQuestion(MultiValueMap<String, String> request)
			throws RestFault, RestException {

		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.AUTHOR_ANSWER_QUESTION, request,
				new IAsyncCallback() {
					public void onSuccess(Response result) {
						try {
							if (null != result) {
								commentJson.setCode(result.getCode());
								commentJson.setCodeInfo(result.getCodeInfo());
								setCommentJson(commentJson);

							} else {
								exceptionInfo[0] = "返回的数据为空！";
							}
						} catch (Exception e) {
							setCommentJson(commentJson);
							e.printStackTrace();
							exceptionInfo[0] = e.getMessage();
						}
						notifyStopProgress();
					}

					public void onFailure(Throwable throwable) {
						setCommentJson(commentJson);
						Log.e(CommentController.class.getCanonicalName(),
								throwable.getMessage());
						notifyStopProgress();
						exceptionInfo[0] = throwable.getMessage();
					}
				});
		return exceptionInfo[0];
	}

/**
 * --------------------------------------考试系统------------------------------------------
 */

	/**
	* <p>Title</p>: getTeaClaTesTextbook
	* Description: 获取书籍列表
	* @param request
	* @return
	* @throws RestFault
	* @throws RestException
	*/
	public String getTeaClaTesTextbook(MultiValueMap<String, String> request)
			throws RestFault, RestException {

		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.GET_TEA_CLA_TES_TEXTBOOK, request,
				new IAsyncCallback() {
					public void onSuccess(Response result) {
						try {
							if (null != result) {
								commentJson.setCode(result.getCode());
								commentJson.setCodeInfo(result.getCodeInfo());
								List<BookSeletct> bookSeletcts = objectHelper
										.syncListObject(result.getData(),
												BookSeletct.class);
								commentJson.setBookSeletcts(bookSeletcts);
								setCommentJson(commentJson);

							} else {
								exceptionInfo[0] = "返回的数据为空！";
							}
						} catch (Exception e) {
							setCommentJson(commentJson);
							e.printStackTrace();
							exceptionInfo[0] = e.getMessage();
						}
						notifyStopProgress();
					}

					public void onFailure(Throwable throwable) {
						setCommentJson(commentJson);
						Log.e(CommentController.class.getCanonicalName(),
								throwable.getMessage());
						notifyStopProgress();
						exceptionInfo[0] = throwable.getMessage();
					}
				});
		return exceptionInfo[0];
	}
	
	
	/**
	* <p>Title</p>: getTextBookOneDirs
	* Description: 获取一级目录列表
	* @param request
	* @return
	* @throws RestFault
	* @throws RestException
	*/
	public String getTextBookOneDirs(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.GET_TEXT_ONE_DIRS, request,
				new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());
						
								List<TextOneBookDirs> oneBookDirs = objectHelper
							.syncListObject(result.getData(),
									TextOneBookDirs.class);
					commentJson.setOneBookDirs(oneBookDirs);
					setCommentJson(commentJson);
						
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	/**
	 * <p>Title</p>: getTextBookOneDirs
	 * Description: 获取二级目录列表
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String getTextBookTwoDirs(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.GET_TEXT_TWO_DIRS, request,
				new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());
						
						List<TextTwoBookDirs> twoBookDirs = objectHelper
								.syncListObject(result.getData(),
										TextTwoBookDirs.class);
						commentJson.setTwoBookDirs(twoBookDirs);
						setCommentJson(commentJson);
						
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	/**
	 * <p>Title</p>: getTwoDirPapers
	 * Description: 获取试卷名称
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String getTwoDirPapers(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.GET_TWO_DIR_PAPERS, request,
				new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());
						List<TestPaper> papers = objectHelper
								.syncListObject(result.getData(),
										TestPaper.class);
							commentJson.setPapers(papers);
							setCommentJson(commentJson);
						
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	/**
	 * <p>Title</p>: getTwoDirPapers
	 * Description: 获取试卷内容
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String getPaperInfo(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.GET_PAPER_INFO, request,
				new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							TestQuestions question=new TestQuestions();
							Log.i("other", result.toString());
							objectHelper.syncObjectGraph(question,
									result.getData());
							Log.e("other", question.toString());
							commentJson.setQuestions(question);
							setCommentJson(commentJson);
						}
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	/**
	 * <p>Title</p>: getTwoDirPapers
	 * Description: 发送试卷
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String insertPaperInfo(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.INSERT_PAPER_INFO, request,
				new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							PaperInfo paperInfo=new PaperInfo();
							Log.i("other", result.toString());
							objectHelper.syncObjectGraph(paperInfo,
									result.getData());
							Log.e("other", paperInfo.toString());
							commentJson.setPaperInfo(paperInfo);
							setCommentJson(commentJson);
						}
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	
	/**
	 * <p>Title</p>: getTwoDirPapers
	 * Description: 获取课程列表
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String getCourses(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.GET_COURSES, request,
				new IAsyncCallback() {
		
			

			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());

							courses = objectHelper.syncListObject(result.getData(), Courses.class);
							Log.e("other", courses.toString());
							commentJson.setCourses(courses);
							setCommentJson(commentJson);
						
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	/**
	 * <p>Title</p>: getTwoDirPapers
	 * Description: 删除课程
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String deleteCourses(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.DELETE_COURSES, request,
				new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());
//						if (result.getData() != null) {
//							List<Courses> courses=new ArrayList<Courses>();
//							Log.i("other", result.toString());
//							objectHelper.syncObjectGraph(courses,
//									result.getData());
//							Log.e("other", courses.toString());
//							commentJson.setCourses(courses);
//							setCommentJson(commentJson);
//						}
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	/**
	 * <p>Title</p>: getTwoDirPapers
	 * Description: 添加课程
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String AddCourses(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.ADD_COURSE, request,
				new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());
						courses=objectHelper.syncListObject(result.getData(), Courses.class);
						Log.e("other", courses.toString());
						commentJson.setCourses(courses);
						setCommentJson(commentJson);
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	/**
	 * <p>Title</p>: getTwoDirPapers
	 * Description: 添加课程
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String confirmSend(MultiValueMap<String, String> request)
			throws RestFault, RestException {
		
		final String[] exceptionInfo = {null};
		restService.sendRequest(Preferences.CONFIRM_SEND, request,
				new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						commentJson.setCode(result.getCode());
						commentJson.setCodeInfo(result.getCodeInfo());
//						if (result.getData() != null) {
//							List<Courses> courses=new ArrayList<Courses>();
//							Log.i("other", result.toString());
//							objectHelper.syncObjectGraph(courses,
//									result.getData());
//							Log.e("other", courses.toString());
//							commentJson.setCourses(courses);
//							setCommentJson(commentJson);
//						}
						
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setCommentJson(commentJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}
				notifyStopProgress();
			}
			
			public void onFailure(Throwable throwable) {
				setCommentJson(commentJson);
				Log.e(CommentController.class.getCanonicalName(),
						throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}
		});
		return exceptionInfo[0];
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public BaseAdapter getAdapter(Context context) {
		return null;
	}
	@Inject
	public void setRestService(IRestService restService) {
		this.restService = restService;
	}

	@Inject
	public void setObjectHelper(ObjectHelper objectHelper) {
		this.objectHelper = objectHelper;
	}
	@Inject
	public void setCommentJson(CommentJson commentJson) {
		this.commentJson = commentJson;
		this.registerModel(this.commentJson);
	}

}
