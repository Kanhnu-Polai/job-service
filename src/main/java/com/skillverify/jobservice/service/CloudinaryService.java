package com.skillverify.jobservice.service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.exceptions.ApiException;
import com.cloudinary.utils.ObjectUtils;
import com.skillverify.jobservice.contants.ErrorCodeEnum;
import com.skillverify.jobservice.dto.UploadResponse;
import com.skillverify.jobservice.exception.CloudinaryUploadException;
import com.skillverify.jobservice.exception.FileReadException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CloudinaryService {

	private final Cloudinary cloudinary;

	
	@SuppressWarnings("unchecked")
	public UploadResponse uploadCompanyLogo(MultipartFile file, String folder) {
		try {
			if (file == null || file.isEmpty()) {
				throw new IllegalArgumentException("❌ File must not be null or empty");
			}

			String resourceType = detectResourceType(file);

			Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
					ObjectUtils.asMap("folder", folder, "resource_type", resourceType, "access_mode", "public"));

			String url = Objects.toString(uploadResult.get("secure_url"), "");
			String publicId = Objects.toString(uploadResult.get("public_id"), "");

			log.info("✅ Upload Successful: public_id={}, url={}", publicId, url);

			return new UploadResponse(url, publicId);

		} catch (IOException e) {
			log.error("❌ File read error during Cloudinary upload: {}", e.getMessage(), e);
			throw new FileReadException(ErrorCodeEnum.FILE_READ_ERROR);
		} catch (Exception e) {
			log.error("❌ Failed to upload file to Cloudinary: {}", e.getMessage(), e);
			throw new CloudinaryUploadException(ErrorCodeEnum.CLOUDINARY_UPLOAD_FAILED);
		}
	}

	public String deleteFile(String publicId) {
		if (publicId == null || publicId.isEmpty()) {
			throw new IllegalArgumentException("Public ID must not be null or empty");
		}

		try {
			Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
			log.info("Delete result from Cloudinary: {}", deleteResult);
			return deleteResult.get("result").toString();
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete file: " + e.getMessage(), e);
		}
	}

	private String detectResourceType(MultipartFile file) {
		String contentType = file.getContentType();
		if (contentType != null && contentType.startsWith("image/")) {
			return "image";
		} else if ("application/pdf".equals(contentType)) {
			return "raw";
		}
		return "auto"; // fallback
	}

}
