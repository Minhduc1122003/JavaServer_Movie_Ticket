package com.example.demo.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageService {
	private final String bucketName = "movieticket-77cf5.appspot.com";
	
	public String uploadFile(MultipartFile file) throws IOException {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		
		String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
		BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).setContentType(file.getContentType()).build();
		Blob blob = storage.create(blobInfo, file.getBytes());

        // Tạo URL truy cập công khai cho ảnh
        return "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/" +
                fileName + "?alt=media";
	}
}
