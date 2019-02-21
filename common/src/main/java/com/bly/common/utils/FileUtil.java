package com.bly.common.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;

/**
 * <p>
 * 文件处理类
 * </p>
 * .
 */
public class FileUtil {
	
	static BASE64Decoder decoder = new sun.misc.BASE64Decoder();   
	/**
	 * 保存读取的Excel
	 * 
	 * @param file
	 * @param excelXmlPath
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static List<String> readExcelToDb(HttpServletRequest request, String rPath)throws Exception {
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件需要上传到的路径
		String path = request.getSession().getServletContext().getRealPath(rPath);
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 返回的文件List
		List<String> filelist = new ArrayList<String>();
		factory.setRepository(file);
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String value = item.getString();
					request.setAttribute(name, value);
				}
				// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				else {
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start + 1);
					request.setAttribute(name, filename);
					// 手动写的
					OutputStream out = new FileOutputStream(new File(path, filename));
					InputStream in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];
					// in.read(buf) 每次读到的数据存放在 buf 数组中
					while ((length = in.read(buf)) != -1) {
						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
						out.write(buf, 0, length);
					}
					in.close();
					out.close();
					System.out.println("获取上传文件的总共的容量：" + item.getSize());
					String pathAndFileName = path + "/" + filename;
					System.out.println("文件上传的路径是：" + pathAndFileName);
					// 返回结果
					filelist.add(pathAndFileName);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filelist;
	}

	private static String saveFile(MultipartFile file,HttpServletRequest request) {
		if (!file.isEmpty()) {
			try {
				// 获取文件需要上传到的路径
				String path = request.getRealPath("\\upload");
				// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
				// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
				File f = new File(path);
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath = path + "/" + file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
				return filePath;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Excel数据与系统实体绑定
	 *
	 * @param excelPath
	 *            表格路径
	 * @param excelXmlPath
	 *            表格对应的XML路径
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> readExcelData(List<String> excelPathList, String excelXmlPath)throws Exception {
			List<Object> resultList = new ArrayList<Object>();
			for(String excelPath : excelPathList){
				InputStream inputExcelXML = new FileInputStream(new File(excelXmlPath));
				XLSReader mainReader = ReaderBuilder.buildFromXML(inputExcelXML);
				InputStream inputXLS = new BufferedInputStream(new FileInputStream(excelPath));
				List<Object> dataList = new ArrayList<Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dataList", dataList);
				mainReader.read(inputXLS, map);
				dataList = (List<Object>) map.get("dataList");
				resultList.add(dataList);
				inputXLS.close();
		}
		return resultList;
	}
	
	/**
	 * 文件、图片上传
	 * @param path
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> fileUpload(String path, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		Map<String,Object> returnMap = new HashMap<String, Object>();
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件需要上传到的路径
		
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		factory.setRepository(file);
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String value = item.getString();
					returnMap.put(name, value);
				}
				// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				else {
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start + 1);
					request.setAttribute(name, filename);
					// 手动写的
					OutputStream out = new FileOutputStream(new File(path, filename));
					InputStream in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];
					// in.read(buf) 每次读到的数据存放在 buf 数组中
					while ((length = in.read(buf)) != -1) {
						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
						out.write(buf, 0, length);
					}
					in.close();
					out.close();
					System.out.println("获取上传文件的总共的容量：" + item.getSize());
					String pathAndFileName = path + "\\" + filename;
					System.out.println("文件上传的路径是：" + pathAndFileName);
					// 返回结果
					JSONObject obj = new JSONObject();
					obj.put("fileName", filename);
					System.out.println(obj.toString());
					//response.getWriter().print(obj.toString());
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
	
	public static void base64StringToImage(String base64String){   
        try {   
            byte[] bytes1 = decoder.decodeBuffer(base64String);   
               
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);   
            BufferedImage bi1 =ImageIO.read(bais);   
            File w2 = new File("C:/Users/Public/Pictures/Sample Pictures/1.jpg");//可以是jpg,png,gif格式   
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    }   
	
	/**
	 * 文件下载
	 */
	public static void FileDownload(Map<String, Object> beans,String destFileName,String excelName){
		
	}
	
	/**
	 * 模板下载
	 * @param request
	 * @param response
	 * @param templateName	要下载的存放在服务器的模板名
	 * @param exportName	导出后的文件命名
	 * @throws IOException
	 */
    public static void templateDownload(HttpServletRequest request, HttpServletResponse response, String templateName, String exportName) 
    		throws IOException {
    	String filePath = request.getServletContext().getRealPath("/") + "plugins/template/" + templateName + ".xlsx";
    	String fileName = exportName + DateFormatUtils.formatUTC(new Date(), "yyyyMMddhhmmss") + ".xlsx";
    	response.reset();
    	response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    	response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
    	OutputStream outputStream = response.getOutputStream();
    	InputStream inputStream = new FileInputStream(filePath);
    	byte[] buffer = new byte[1024];
    	int i = -1;
    	while ((i = inputStream.read(buffer)) != -1) {
    		outputStream.write(buffer, 0, i);
    	}
    	outputStream.flush();
    	outputStream.close();
    	inputStream.close();
    }
}
