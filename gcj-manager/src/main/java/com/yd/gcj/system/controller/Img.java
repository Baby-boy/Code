package com.yd.gcj.system.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/system")
public class Img {

	@RequestMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) {
		// String path = request.getSession().getServletContext().getRealPath("upload/file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		String imgFormt = "";
		if (fileName.contains(".png")) {
			imgFormt += ".png";
		}
		if (fileName.contains(".jpg")) {
			imgFormt += ".jpg";
		}
		if (fileName.contains(".jpeg")) {
			imgFormt += ".jpeg";
		}
		if (fileName.contains(".bmp")) {
			imgFormt += ".bmp";
		}
		if (!file.isEmpty()) {

			try {

				/*
				 * 1、文件路径； 2、文件名； 3、文件格式; 4、文件大小的限制;
				 */

				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(
						new File("upload/", String.valueOf(System.currentTimeMillis()).substring(0, 10) + imgFormt)));
				out.write(file.getBytes());

				out.flush();

				out.close();

			} catch (FileNotFoundException e) {

				e.printStackTrace();

				return "上传失败," + e.getMessage();

			} catch (IOException e) {

				e.printStackTrace();

				return "上传失败," + e.getMessage();

			}

			model.addAttribute("msg", true);
			model.addAttribute("fileName", fileName);
			model.addAttribute("path", String.valueOf(System.currentTimeMillis()).substring(0, 10) + imgFormt);
			return "system/tpgl/pictureadd";

		} else {

			return "上传失败，因为文件是空的.";

		}

	}

}