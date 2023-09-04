package com.coding.cho.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.annotation.Beta;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Component
public class FileUploadUtil {

	/**
	 * 
	 * @param s3Client AmazonS3Client 객체
	 * @param bucket aws 버킷이름
	 * @param path 버킷 내부 경로
	 * @param img  MultipartFile 객체로 이미지정보
	 * @return
	 * @throws IOException 
	 */
	public static Map<String, String> s3Upload(AmazonS3Client s3Client, String bucket, String path, MultipartFile img)  {
		
		//버킷 내부의 이미지 경로
		String newName=newFileNameByNanotime(img.getOriginalFilename());
		String bucketKey=path+newName;
		
		try {
			PutObjectRequest or=new PutObjectRequest(bucket, bucketKey, img.getInputStream(), objectMetadata(img));
			s3Client.putObject(or.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, String> result=new HashMap<>();
		result.put("url", s3Client.getUrl(bucket, bucketKey).toString().substring(6));
		result.put("bucketKey", bucketKey);//
		result.put("orgName", img.getOriginalFilename());
		result.put("newName", newName);
		return result;
	}
	
	public static String s3TempToSrc(AmazonS3Client s3Client, String bucket, String tempKey, String uploadKey) {
		CopyObjectRequest cor=new CopyObjectRequest(bucket, tempKey, bucket, uploadKey);
		s3Client.copyObject(cor.withCannedAccessControlList(CannedAccessControlList.PublicRead));
		
		s3Client.deleteObject(bucket, tempKey);//temp 경로 이미지 삭제
		
		return s3Client.getUrl(bucket, uploadKey).toString().substring(6);//src 폴더의 url
	}
	public static String s3TempToSrcNoDelete(AmazonS3Client s3Client, String bucket, String tempKey, String uploadKey) {
		CopyObjectRequest cor=new CopyObjectRequest(bucket, tempKey, bucket, uploadKey);
		s3Client.copyObject(cor.withCannedAccessControlList(CannedAccessControlList.PublicRead));
		
		
		
		return s3Client.getUrl(bucket, uploadKey).toString().substring(6);//src 폴더의 url
	}
	
	public static void clearTemp(AmazonS3Client s3Client, String bucketName, String tempPath) {
		// 템프 경로의 목록을 갖고와서 제거
		// 폴더 내의 모든 객체를 삭제
		ObjectListing objectListing = s3Client.listObjects(bucketName, tempPath);
		while (true) {
			for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
				s3Client.deleteObject(bucketName, objectSummary.getKey());
			}

			if (objectListing.isTruncated()) {
				objectListing = s3Client.listNextBatchOfObjects(objectListing);
			} else {
				break;
			}
		}

		// 폴더를 삭제
		s3Client.deleteObject(bucketName, tempPath);

	}
	
	public static void delete(AmazonS3Client s3Client, String bucketName, String bucketKey) {
		s3Client.deleteObject(bucketName, bucketKey);
	}
	
	/**
	 * 
	 * @param path : static 하위 경로 예) "/images/upload/temp"
	 * @param tempImg : MultipartFile 객체
	 * @return
	 */
	public static Map<String, String> classPathUpload(String path, MultipartFile tempImg) {
		ClassPathResource cpr=new ClassPathResource("static"+path);
		String newFileName=newFileName(tempImg.getOriginalFilename());
		try {
			File folder=cpr.getFile();
			if(!folder.exists())folder.mkdirs();
			
			tempImg.transferTo(new File(folder, newFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, String> result=new HashMap<>();
		result.put("url", path+newFileName);
		result.put("orgName", tempImg.getOriginalFilename());
		
		return result;
	}
	
	
	//파일 설정 정보
	private static ObjectMetadata objectMetadata(MultipartFile mf) {
		ObjectMetadata objectMetadata=new ObjectMetadata();
		objectMetadata.setContentType(mf.getContentType());//파일타입
		objectMetadata.setContentLength(mf.getSize());//파일사이즈
		return objectMetadata;
	}
	
	//파일이름 UUID를 이용하여 변경
	private static String newFileName(String orgName) {
		int idx=orgName.lastIndexOf(".");
		return UUID.randomUUID().toString() //새로운이름을 UUID로 생성
				+ orgName.substring(idx); //.확장자
	}
	
	//파일이름 nanoTime()을 이용하여 변경
	private static String newFileNameByNanotime(String orgName) {
		int idx=orgName.lastIndexOf(".");
		return orgName.substring(0, idx)+"-"+(System.nanoTime()/1000000)
				+ orgName.substring(idx); //.확장자
	}

	

	
	
	

}
