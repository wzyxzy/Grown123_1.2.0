package com.pndoo.grown123_new.util;

import java.io.File;

import android.util.Log;

public final class OperationFileHelper {
	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public static void RecursionDeleteFile(File file) {
		if (file.isFile()) {
			boolean delete = file.delete();
			if (delete) {
				Log.i("OperationFileHelper", file.getName() + "删除成功！");
			} else {
				Log.i("OperationFileHelper", file.getName() + "删除失败！");
			}

			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				boolean dirDel = file.delete();
				if (dirDel) {
					Log.i("OperationFileHelper", file.getName() + "目录删除成功！");
				} else {
					Log.i("OperationFileHelper", file.getName() + "目录删除失败！");
				}
				return;
			}
			for (File f : childFile) {
				RecursionDeleteFile(f);
			}
			boolean del = file.delete();
			if (del) {
				Log.i("OperationFileHelper", file.getName() + "删除成功！");
			} else {
				Log.i("OperationFileHelper", file.getName() + "删除失败！");
			}
		}
	}
}