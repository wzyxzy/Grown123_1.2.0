package com.pndoo.grown123_new.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.dto.base.UserVO;
import com.pndoo.grown123_new.util.SPUtility;

public class UserDAO {
	private DBOpenHelper dbOpenHelper;
	private Context context;

	public UserDAO(Context context) {
		this.context = context;
		dbOpenHelper = new DBOpenHelper(context);
	}
	// 保存用户基本数据
	public void save(UserVO userVO) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userid", userVO.getUserId());
		values.put("username", userVO.getUserName());
		values.put("password", userVO.getPassword());
		values.put("email", userVO.getEmail());
		values.put("booksdata", userVO.getBooksData());
		values.put("isrememberpd", userVO.getIsRememberPd());
		// values.put("publishName", userVO.getPublishName());
		// values.put("publishId", userVO.getPublishId());
		db.insert("user", null, values);
	}
	// 删除用户
	public void delete(Integer id) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.delete("user", "userid=?", new String[]{id.toString()});

	}
	// 修改用户信息
	public void update(UserVO userVO) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userid", userVO.getUserId());
		values.put("username", userVO.getUserName());
		values.put("password", userVO.getPassword());
		values.put("email", userVO.getEmail());
		values.put("booksdata", userVO.getBooksData());
		values.put("isrememberpd", userVO.getBooksData());
		// values.put("publishName", userVO.getPublishName());
		// values.put("publishId", userVO.getPublishId());
		db.update("user", values, "userid=?", new String[]{userVO.getUserId()
				.toString()});
	}
	// 修改登录信息
	public void updateLogin(UserVO userVO) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userid", userVO.getUserId());
		values.put("username", userVO.getUserName());
		values.put("password", userVO.getPassword());
		values.put("email", userVO.getEmail());
		values.put("isrememberpd", userVO.getIsRememberPd());
		// values.put("publishName", userVO.getPublishName());
		// values.put("publishId", userVO.getPublishId());
		db.update("user", values, "userid=?", new String[]{userVO.getUserId()
				.toString()});
	}
	// 修改密码
	public void updatePassword(UserVO userVO) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("password", userVO.getPassword());
		db.update("user", values, "userid=?", new String[]{userVO.getUserId()
				.toString()});
	}
	// 修改书架数据
	public void updateBooksData(UserVO userVO) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("booksdata", userVO.getBooksData());
		db.update("user", values, "userid=?", new String[]{userVO.getUserId()
				.toString()});
	}
	// 查找用户
	public UserVO find(String string) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("user", null, "userid=?",
				new String[]{string.toString()}, null, null, null);
		if (cursor.moveToFirst()) {
			String userId = cursor.getString(cursor.getColumnIndex("userid"));
			String userName = cursor.getString(cursor
					.getColumnIndex("username"));
			String password = cursor.getString(cursor
					.getColumnIndex("password"));
			String email = cursor.getString(cursor.getColumnIndex("email"));
			String booksData = cursor.getString(cursor
					.getColumnIndex("booksdata"));
			String isRememberPd = cursor.getString(cursor
					.getColumnIndex("isrememberpd"));
			// String publishName =
			// cursor.getString(cursor.getColumnIndex("publishName"));
			// String publishId =
			// cursor.getString(cursor.getColumnIndex("publishId"));
			cursor.close();
			return new UserVO(userName, password, email, userId, booksData,
					isRememberPd);
		}
		return null;
	}
	// 查找用户
	public UserVO findByuserName(String login_username) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("user", null, "username=?",
				new String[]{login_username.toString()}, null, null, null);
		if (cursor.moveToFirst()) {
			String userId = cursor.getString(cursor.getColumnIndex("userid"));
			String userName = cursor.getString(cursor
					.getColumnIndex("username"));
			String password = cursor.getString(cursor
					.getColumnIndex("password"));
			String email = cursor.getString(cursor.getColumnIndex("email"));
			String booksData = cursor.getString(cursor
					.getColumnIndex("booksdata"));
			String isRememberPd = cursor.getString(cursor
					.getColumnIndex("isrememberpd"));
			// String publishName =
			// cursor.getString(cursor.getColumnIndex("publishName"));
			// String publishId =
			// cursor.getString(cursor.getColumnIndex("publishId"));
			cursor.close();
			return new UserVO(userName, password, email, userId, booksData,
					isRememberPd);
		}
		return null;
	}
	// 查找用户
	public UserVO findByEmail(String login_email) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("user", null, "email=?",
				new String[]{login_email.toString()}, null, null, null);
		if (cursor.moveToFirst()) {
			String userId = cursor.getString(cursor.getColumnIndex("userid"));
			String userName = cursor.getString(cursor
					.getColumnIndex("username"));
			String password = cursor.getString(cursor
					.getColumnIndex("password"));
			String email = cursor.getString(cursor.getColumnIndex("email"));
			String booksData = cursor.getString(cursor
					.getColumnIndex("booksdata"));
			String isRememberPd = cursor.getString(cursor
					.getColumnIndex("isrememberpd"));
			// String publishName =
			// cursor.getString(cursor.getColumnIndex("publishName"));
			// String publishId =
			// cursor.getString(cursor.getColumnIndex("publishId"));
			cursor.close();
			return new UserVO(userName, password, email, userId, booksData,
					isRememberPd);
		}
		return null;
	}
	// 分页查找用户
	public List<UserVO> getScrollData(int offset, int maxResult) {
		List<UserVO> userList = new ArrayList<UserVO>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("user", null, null, null, null, null,
				"userid asc", offset + "," + maxResult);
		while (cursor.moveToNext()) {
			String userId = cursor.getString(cursor.getColumnIndex("userid"));
			String userName = cursor.getString(cursor
					.getColumnIndex("username"));
			String password = cursor.getString(cursor
					.getColumnIndex("password"));
			String email = cursor.getString(cursor.getColumnIndex("email"));
			String booksData = cursor.getString(cursor
					.getColumnIndex("booksdata"));
			String isRememberPd = cursor.getString(cursor
					.getColumnIndex("isrememberpd"));
			// String publishName =
			// cursor.getString(cursor.getColumnIndex("publishName"));
			// String publishId =
			// cursor.getString(cursor.getColumnIndex("publishId"));
			userList.add(new UserVO(userName, password, email, userId,
					booksData, isRememberPd));
		}
		cursor.close();
		return userList;
	}
	// 用户总数
	public long getCount() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("user", new String[]{"count(*)"}, null, null,
				null, null, null);
		cursor.moveToFirst();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}

	/**
	 * 把登录名增加到xml中
	 * 
	 * @param loginUser
	 */
	public void addUserToXML(String loginUser) {
		String loginUsers = SPUtility.getSPString(context, "loginUsers");
		Log.i("userXML", loginUsers);
		if (loginUsers.equals("")) {
			SPUtility.putSPString(context, "loginUsers", loginUser);
		} else {
			if (!isExitForXML(loginUser)) {
				loginUsers = loginUsers + ":" + loginUser;
				SPUtility.putSPString(context, "loginUsers", loginUsers);
				Log.i("userXML", loginUsers);
			}
		}
	}

	/**
	 * 判断登录明是否存在xml中
	 * 
	 * @param loginUser
	 * @return
	 */
	public boolean isExitForXML(String loginUser) {
		String loginUsers = SPUtility.getSPString(context, "loginUsers");
		if (loginUsers.equals("")) {
			return false;
		} else {
			String[] strs = loginUsers.split(":");
			for (String str : strs) {
				if (str.equals(loginUser)) {
					return true;
				}
			}
			return false;
		}

	}
	/**
	 * 获取登录名列表
	 * 
	 * @return
	 */
	public String[] getUserListFromXML() {
		String loginUsers = SPUtility.getSPString(context, "loginUsers");
		if (loginUsers.equals("")) {
			return null;
		} else {
			return loginUsers.split(":");
		}
	}
	/**
	 * 获取输入登录名列表
	 * 
	 * @return
	 */
	public String[] getSameUserFromXML(String text) {
		String loginUsers = SPUtility.getSPString(context, "loginUsers");
		if (loginUsers.equals("")) {
			return null;
		} else {
			String[] users = loginUsers.split(":");
			String result = "";
			for (String user : users) {
				if (user.contains(text)) {
					result = result + user + ":";
				}
			}
			if (result.equals("")) {
				return null;
			} else {
				return result.split(":");
			}
		}
	}

	/**
	 * 删除登录名
	 * 
	 * @param loginUser
	 */
	public void deleteUserFromXml(String loginUser) {
		if (isExitForXML(loginUser)) {
			String[] strs = SPUtility.getSPString(context, "loginUsers").split(
					":");
			String loginUsers = "";
			for (String str : strs) {
				if (!str.equals(loginUser)) {
					loginUsers = loginUsers + ":" + str;
				}
			}
			if (!loginUsers.equals("")) {
				loginUsers = loginUsers.substring(0, loginUsers.length() - 2);
			}
			SPUtility.putSPString(context, "loginUsers", loginUsers);
		}

	}
	/**
	 * 保存登录信息
	 * 
	 * @param userVO
	 * @param loginUser
	 */
	public void saveUserForLogin(UserVO userVO, String loginUser) {
		SPUtility.putSPBoolean(context, R.string.islogin, true);
		SPUtility.putSPString(context, "userId", userVO.getUserId());
		if (null == find(userVO.getUserId())) {
			save(userVO);
		} else {
			updateLogin(userVO);
		}
		addUserToXML(loginUser);
	}

	public String isRememberPd(String loginName) {
		// TODO Auto-generated method stub
		UserVO userVO = findByuserName(loginName);
		if (null != userVO && userVO.getIsRememberPd().equals("1")) {
			return userVO.getPassword();
		}
		userVO = findByEmail(loginName);
		if (null != userVO && userVO.getIsRememberPd().equals("1")) {
			return userVO.getPassword();
		}
		return null;
	}
}
