package com.yd.gcj.controller.page;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yd.gcj.entity.YdMangerFiles;
import com.yd.gcj.entity.YdMangerUserTeamFile;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.file.YdMangerFilesFactory;
import com.yd.gcj.service.YdMangerServiceFiles;
import com.yd.gcj.service.YdMangerServiceUser;
import com.yd.gcj.service.YdMangerServiceUserTeamFile;
import com.yd.gcj.tool.MapInitFactory;
import com.yd.gcj.util.MyStaticFactory;

@RestController
@RequestMapping(value = "/page/file", produces = { "application/json;charset=UTF-8" })
public class YdMangerControllerPageFile {

	@Autowired
	private YdMangerServiceUser serviceUser;

	@Autowired
	private YdMangerServiceUserTeamFile serviceTeamFile;

	@Autowired
	private YdMangerServiceFiles serviceFile;

	@RequestMapping(value = "/uploadheadimg/{userId}")
	public Object upLoadImg(MultipartFile m, @PathVariable Integer userId, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			MapInitFactory mapInitFactory = new MapInitFactory();
			YdMangerUserVo userVo = (YdMangerUserVo) session.getAttribute("user");
			if (userVo != null) {
				try {
					if (m != null) {
						String path = MyStaticFactory.systemPath + MyStaticFactory.headimg;
						String fileName = userId + "_" + System.currentTimeMillis()
								+ getFileType(m.getOriginalFilename());
						String imgPath = path + fileName;
						String dbpath = MyStaticFactory.headimg + fileName;
						System.out.println(imgPath);
						File file = new File(path);
						if (!file.exists()) {
							file.mkdirs();
						}
						file = new File(imgPath);
						m.transferTo(file);

						mapInitFactory.put("path", dbpath);
						userVo.setUser_avatar(dbpath);
						session.setAttribute("user", userVo);
						Integer isOk = serviceUser.$updateUserAvatar(userId, dbpath);
						if (isOk == 1) {
							mapInitFactory.setMsg("200", "上传成功！");
						} else {
							mapInitFactory.setMsg("502", "保存失败！");
						}
					} else {
						mapInitFactory.setMsg("501", "请选择图片！");
					}
				} catch (Exception e) {
					e.printStackTrace();
					mapInitFactory.setSystemError();
				}
			} else {
				mapInitFactory.setMsg("600", "对不起，您没有操作权限！");
			}
			return mapInitFactory.getMap();
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	@RequestMapping(value = "/upload/{upLoadNum}", method = RequestMethod.POST)
	public Object upLoad(HttpServletRequest request, MultipartFile[] files, @PathVariable Integer upLoadNum) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		if (userVo != null) {
			// 这里可以支持多文件上传
			BufferedOutputStream bw = null;
			try {
				if (files != null && files.length >= 1) {
					Date date = new Date();
					long time = date.getTime();
					String times = String.valueOf(time);
					Integer timesL = times.length();
					String fileName = files[0].getOriginalFilename();
					String fileTName = times.substring(3, timesL) + getFileType(fileName);
					InputStream iptS = files[0].getInputStream();
					YdMangerFilesFactory ymff = new YdMangerFilesFactory();
					boolean success = ymff.fileUpLoadToJGY(iptS, fileTName);
					if (success) {
						YdMangerFiles file = new YdMangerFiles();
						file.setFiles_name(fileName);
						file.setFiles_path(fileTName);
						file.setFiles_size(files[0].getSize());
						Integer insertFileResult = serviceFile.$insert(file);
						if (insertFileResult > 0) {
							mapInitFactory.setMsg("200", "上传成功！");
							mapInitFactory.put("fileId", file.getFiles_id());
						} else {
							mapInitFactory.setMsg("503", "上传失败！");
						}
					} else {
						mapInitFactory.setMsg("502", "上传失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "没有接收到文件");
				}
			} catch (Exception e) {
				e.printStackTrace();
				mapInitFactory.setSystemError();
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					mapInitFactory.setSystemError();
				}
			}
		} else {
			mapInitFactory.setMsg("600", "您当前没有权限操作此功能！");
		}
		return mapInitFactory.getMap();
	}
	
	

	@RequestMapping(value = "/fileUpLoad", method = RequestMethod.POST)
	public Object fileUpLoad(String path,String name,long size,HttpServletRequest request) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		if (userVo != null) {
			// 这里可以支持多文件上传
			BufferedOutputStream bw = null;
			try {
				if (path != null && path.length() >= 1) {
					YdMangerFiles file = new YdMangerFiles();
					file.setFiles_name(name);
					file.setFiles_path(path);
					file.setFiles_size(size);
					Integer insertFileResult = serviceFile.$insert(file);
					if (insertFileResult > 0) {
						mapInitFactory.setMsg("200", "上传成功！");
						mapInitFactory.put("fileId", file.getFiles_id());
					} else {
						mapInitFactory.setMsg("503", "上传失败！");
					}
				} else {
					mapInitFactory.setMsg("502", "上传失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				mapInitFactory.setSystemError();
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					mapInitFactory.setSystemError();
				}
			}
		} else {
			mapInitFactory.setMsg("600", "您当前没有权限操作此功能！");
		}
		return mapInitFactory.getMap();
	}
	
	@RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
	public void fileDownLoad(@PathVariable Integer fileId, HttpServletResponse response) {
		YdMangerUserVo userVo = new YdMangerUserVo();
		if (userVo != null) {
			try {
				YdMangerFiles files = serviceFile.$queryById(fileId);
				String fileName = files.getFiles_name();
				String filePath = files.getFiles_path();
				YdMangerFilesFactory ymff = new YdMangerFilesFactory();
				InputStream fis = ymff.fileDownLoad(filePath);

				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();

				response.reset();
				// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(fileName.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
				response.addHeader("Content-Length", "" + files.getFiles_size());
				OutputStream os = new BufferedOutputStream(response.getOutputStream());

				response.setContentType("application/octet-stream");
				os.write(buffer);// 输出文件

				os.flush();
				os.close();
			} catch (Exception e) {
				System.out.println("文件下载错误！");
				e.printStackTrace();
			}
		}

	}

	/**
	 * GET请求 上传页面，也将显示已经存在的文件
	 * 
	 * @param model
	 * @return
	 */
	/*
	 * @GetMapping(value = "/index") public String index(Model model) {
	 * //获取已存在的文件 File [] files = new File(uploadPath).listFiles();
	 * model.addAttribute("files", files); return "web/index"; }
	 */

	@RequestMapping("/uploadTeamFile")
	public Object uploadTeamFile(Integer userId,String path,String name,long size,HttpServletRequest request) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		if (userVo != null) {
			try {
				if (path != null && path.length() > 0) {
					Date date = new Date();
					
					YdMangerFiles f = new YdMangerFiles();
					f.setFiles_name(name);
					f.setFiles_path(path);
					f.setFiles_size(size);
					f.setFiles_create_time(date);
					Integer insertFileResult = serviceFile.$insert(f);
					
					YdMangerUserTeamFile teamFile = new YdMangerUserTeamFile();
					teamFile.setFile_uid(userVo.getUser_id());
					teamFile.setFile_fid(f.getFiles_id());
					teamFile.setFile_id(f.getFiles_id());
					Integer teamFileResult = serviceTeamFile.$insert(teamFile);
					
					if (insertFileResult > 0 && teamFileResult > 0) {
						mapInitFactory.setMsg("200", "上传成功！");
					} else {
						mapInitFactory.setMsg("503", "上传失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "上传失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				mapInitFactory.setSystemError();
			}
		} else {
			mapInitFactory.setMsg("600", "请登录！");
		}
		return mapInitFactory.getMap();
	}

	/**
	 * 判断文件是否为图片文件
	 * 
	 * @param fileName
	 * @return
	 */
	/*
	 * private Boolean isImageFile(String fileName) { String [] img_type = new
	 * String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"}; if(fileName==null)
	 * {return false;} fileName = fileName.toLowerCase(); for(String type :
	 * img_type) { if(fileName.endsWith(type)) {return true;} } return false; }
	 */

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	private String getFileType(String fileName) {
		if (fileName != null && fileName.indexOf(".") >= 0) {
			return fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
		return "";
	}
}
