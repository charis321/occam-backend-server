package com.charis.occam_spm_sys.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.charis.occam_spm_sys.exception.BusinessException;
import com.charis.occam_spm_sys.service.FileUploadService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

	
	@Autowired
	private Cloudinary cloudinary;

	@Override
	public String uploadAvatar(Long userId ,MultipartFile file){
		try {
			Map uploadResult = cloudinary.uploader()
										 .upload(file.getBytes(), Map.of("folder", "occam_avatar","public_id", userId.toString()));
			String publicId = uploadResult.get("public_id").toString();
			return  cloudinary.url()
				    .transformation(new Transformation().width(150).height(150).crop("fill"))
				    .secure(true)
				    .generate(publicId);
		}catch(Exception e){
			log.warn("cloudinary上傳服務發生錯誤 | msg: {}",e.getMessage());
			throw new BusinessException(500,"cloudinary上傳服務發生錯誤");
		}
	}
	
	
//	String url = cloudinary.url()
//		    .transformation(new Transformation().width(400).height(225).crop("fill"))
//		    .generate("occam_course_covers/sample_id");

//	@Override
//	public String uploadAvatar(MultipartFile file) {
//		if(ObjectUtils.isEmpty(file)) return null;
//		
//		if (file.getSize() > 102400) {
//		    throw new BusinessException(400, "頭像檔案過大，請限制在 100KB 以內");
//		}
//		
//		String fileType = file.getContentType();
//		if(!("image/png".equals(fileType)||"image/jpeg".equals(fileType))) {
//			log.warn("檔案格式不支援 | file type: {}", fileType);
//			throw new BusinessException(400, "檔案格式不支援");
//		}
//		try {
//			return convertToBase64(file);
//		}catch(IOException e) {
//			log.error("檔案轉換發生錯誤: ", e);
//			throw new BusinessException(500, "檔案轉換發生錯誤");
//		}
//	}

	public String convertToBase64(MultipartFile file) throws IOException {
	    byte[] bytes = file.getBytes();
	    String encodedString = Base64.getEncoder().encodeToString(bytes);
	    String contentType = file.getContentType();
	    return "data:" + contentType + ";base64," + encodedString;
	}
}
