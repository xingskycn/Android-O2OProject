package com.gez.grill.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gez.grill.controller.ShouyController;
import com.gez.grill.entity.CaipBasic;
import com.gez.grill.entity.Caipsc;
import com.gez.grill.entity.Caipxq;
import com.gez.grill.entity.PagenateArgs;
import com.gez.grill.entity.Pingl;
import com.gez.grill.entity.UploadFileResult;
import com.gez.grill.mapper.CaipMapper;
import com.gez.grill.mapper.CaipplMapper;

@Service
public class UploadService {

	private final static Logger logger = Logger
			.getLogger(ShouyController.class);

	@Autowired(required = true)
	private ServletContext servletContext;

	public UploadFileResult uploadFile(MultipartHttpServletRequest icMultipartRequest) {
		UploadFileResult tuResult = new UploadFileResult();
		tuResult.setSuccess(false);
		tuResult.setFileName("");

		MultipartFile tcFile = icMultipartRequest.getFile("zhaop");

		//String tsFileContent = "C:\\tomcat\\webapps\\ROOT\\resources\\files";
		
		String tsFileContent = "D:\\alpha\\stew\\01\\woodware\\ROOT\\resources\\files";
		
		File tcFileDir = new File(tsFileContent);
    	if(!tcFileDir.isDirectory()){
    		tcFileDir.mkdirs();
        }
		try {
			if (tcFile != null && tcFile.getName().length() > 0) {
				String tsFileName = tcFile.getOriginalFilename();
                String tsFileNameServer = UUID.randomUUID().toString();
                String tsReturnName = tsFileNameServer + tsFileName;

                File targetFile = new File(tsFileContent + File.separator + tsFileNameServer + ".image");
                tcFile.transferTo(targetFile);
				tuResult.setSrc("/resources/files/" + tsFileNameServer + ".image");
				
				tuResult.setSuccess(true);
                tuResult.setFileName(tsReturnName);
            }
		}
		catch (Exception e) {
			logger.error(e);
        }
		
		
		return tuResult;
	}
}
