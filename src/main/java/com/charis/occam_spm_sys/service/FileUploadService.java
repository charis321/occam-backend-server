package com.charis.occam_spm_sys.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

public interface FileUploadService {
	
//    public String uploadImage(MultipartFile file);
    public String uploadAvatar(Long userId, MultipartFile file);
}
