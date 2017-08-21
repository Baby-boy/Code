package com.glanway.iclock.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * 文件处理工具类.
 *
 * @author FUQIHAO
 * @version 1.0.0
 * @dateTime 2017年8月18日 上午9:23:59
 */
public final class FileUtil {

	private static final String separator = File.separator;
	private String dateDirectory;// 时间字符串(用于根据时间创建目录)
	private String rootFilePath;// 根文件目录路径
	private String targetFile;// 目标文件
	private String targetFilePath;// 目标文件路径

	/**
	 * 错误信息记录对象初始化.
	 */
	public FileUtil(String rootFilePath) {
		this.rootFilePath = rootFilePath;
		this.targetFile = "error";
		this.dateDirectory = TimeUtil.format(new Date(), TimeUtil.FORMAT_YYYY_MM_DD);
		this.targetFilePath = this.rootFilePath + separator + dateDirectory + separator + this.targetFile + ".log";
	}

	/**
	 * 信息记录对象初始化.
	 */
	public FileUtil(String rootFilePath, String targetFile) {
		this.rootFilePath = rootFilePath;
		this.targetFile = targetFile;
		this.dateDirectory = TimeUtil.format(new Date(), TimeUtil.FORMAT_YYYY_MM_DD);
		this.targetFilePath = this.rootFilePath + separator + dateDirectory + separator + this.targetFile + ".log";
	}

	/**
	 * 创建目标文件夹和文件.
	 *
	 * @param mark
	 * @return
	 * @author FUQIHAO
	 * @dateTime 2017年8月16日 下午2:36:30
	 */
	public boolean init() {
		boolean flag = true;
		// 根据当天时间创建一个文件夹
		File file = new File(rootFilePath + separator + dateDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 拼接文件
		if (file.isDirectory()) {
			File deviceFile = new File(targetFilePath);
			if (!deviceFile.exists()) {
				try {
					flag = deviceFile.createNewFile();
				} catch (IOException e) {
					flag = false;
					e.printStackTrace();
				}
			}
		}

		return flag;
	}

	/**
	 * 记录信息.
	 *
	 * @param msg
	 * @param object
	 * @author FUQIHAO
	 * @dateTime 2017年8月16日 下午2:37:00
	 */
	private void writeFileContent(String msg, Object object) {
		boolean bool = init();
		if (bool) {
			write(msg);
		} else {
			throw new RuntimeException("An unknown error occurred initializing the folder and file.");
		}
	}

	private void write(String msg) {
		try {
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(targetFilePath), true), "UTF-8"));
			writer.write(TimeUtil.format(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
			writer.write(" ");
			writer.write(msg);
			writer.newLine();
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void log(String msg) {
		writeFileContent(msg, null);
	}

	public void log(String format, Object arg) {
		FormattingTuple ft = MessageFormatter.format(format, arg);
		writeFileContent(ft.getMessage(), ft.getThrowable());
	}

	public void log(String format, Object arg1, Object arg2) {
		FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
		writeFileContent(ft.getMessage(), ft.getThrowable());
	}

	public void log(String format, Object... arguments) {
		FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		writeFileContent(ft.getMessage(), ft.getThrowable());
	}

	public static void main(String[] args) {
		FileUtil fileUtil = new FileUtil("e:");
		fileUtil.log("错误信息为", "1");

	}
}
