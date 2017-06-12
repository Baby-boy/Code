package com.yd.gcj.file;

import java.io.InputStream;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

public class YdMangerFilesFactory {
	
	private final String filepath = "https://dav.jianguoyun.com/dav/我的坚果云/gcj/";
	private final String username = "117182798@qq.com";
	private final String userpwd = "av8zvxxvpthag9u2";
	
	
	/**
	 * 上传文件
	 * @param fis
	 * @param fileName
	 * @return
	 */
	public boolean fileUpLoadToJGY(InputStream fis,String fileName){
		boolean success = true;
		try {
			String allPathName = filepath + fileName;
			Sardine sardine = SardineFactory.begin(username,userpwd);
			System.out.println(allPathName);
			if(sardine.exists(allPathName)){
				sardine.delete(allPathName);
			}
			sardine.put(allPathName, fis);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	/**
	 * 删除文件
	 * @param fileName
	 * @return
	 */
	public boolean fileDel(String fileName){
		boolean success = true;
		try {
			String allPathName = filepath + fileName;
			Sardine sardine = SardineFactory.begin(username,userpwd);
			sardine.delete(allPathName);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	/***
	 * 下载文件
	 * @param fileName
	 * @return
	 */
	public InputStream fileDownLoad(String fileName){
		InputStream fis = null;
		try {
			Sardine sardine = SardineFactory.begin(username,userpwd);
			fis = sardine.get(filepath+fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fis;
	}
	
//	public void test(){
//		try {
//			Sardine sardine = SardineFactory.begin(username,userpwd);
//			
//			/*InputStream fis = new FileInputStream(new File("img12.jpg"));  
//			
//	        sardine.put(filepath+"", fis);*/
//			
//			@SuppressWarnings("deprecation")
//			List<DavResource> resources = sardine.getResources(filepath);
//			for (DavResource res : resources)
//			{	
//				ObjectMapperFactory.doIt(res);
//			     System.out.println(res);
//			}
//			
//			/*InputStream cls = sardine.get(filepath+"233.txt");
//			
//			ObjectMapperFactory.doIt("输入流位置："+cls);
//			ObjectMapperFactory.doIt(resources);*/
//			
//			InputStream iptS = sardine.get(filepath+"233.txt");
//			
//            FileOutputStream foptS = new FileOutputStream("D:/功诚记下载文件测试.txt");
//            
//            OutputStream optS = (OutputStream) foptS;
//
//            int c;
//            while((c=iptS.read())!=-1)
//            {
//                optS.write(c);
//            }
//            optS.flush();
//            
//            
//            
//            /*File file = new File(path);// path是根据日志路径和文件名拼接出来的
//    	    String filename = file.getName();// 获取日志文件名称
//    	    InputStream fis = new BufferedInputStream(new FileInputStream(path));
//    	    byte[] buffer = new byte[fis.available()];
//    	    fis.read(buffer);
//    	    fis.close();
//    	    response.reset();
//    	    // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
//    	    response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
//    	    response.addHeader("Content-Length", "" + file.length());
//    	    OutputStream os = new BufferedOutputStream(response.getOutputStream());
//    	    response.setContentType("application/octet-stream");
//    	    os.write(buffer);// 输出文件
//    	    os.flush();
//    	    os.close();*/
//            
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
}