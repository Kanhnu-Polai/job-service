package com.skillverify.jobservice.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CloudinaryService {

	private final Cloudinary cloudinary;

	public Map<String, String> uploadCompanyLogo(MultipartFile file, String folder) {
		try {
			String resourceType = detectResourceType(file);

			Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
					ObjectUtils.asMap("folder", folder, "resource_type", resourceType, "access_mode", "public"));

			log.info("Upload result from Cloudinary: {}", uploadResult);

			return Map.of("url", uploadResult.get("secure_url").toString(), // ✅ HTTPS secure URL
					"public_id", uploadResult.get("public_id").toString());
		} catch (Exception e) {
			throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
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
