package com.gez.grill.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 图片查看控制器类
 * 
 * @author Zhaowei
 * 
 */
@Controller
@RequestMapping(value = "/img")
public class ImgViewController {

	private final static Logger logger = Logger.getLogger(ImgViewController.class);

	@Autowired(required = true)
	private ServletContext servletContext;

	/**
	 * 图片输出2014-3-17
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	@ResponseBody
	public void getDishesList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "file") String file) throws IOException {
		try {
			if (file != null && !file.equals("")) {
				String tsFileContent = servletContext.getRealPath("/resources/files/");
				response.setHeader("Content-Disposition", "attachment;fileName=\"" + file + "\"");
				File stream = new File(tsFileContent + "\\" + file);
				response.setContentLength((int) stream.length());
				InputStream inputStream = new FileInputStream(stream);
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[1024];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					os.write(b, 0, length);
				}
				inputStream.close();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
}