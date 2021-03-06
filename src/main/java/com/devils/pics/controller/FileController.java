package com.devils.pics.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devils.pics.domain.Bookmark;
import com.devils.pics.domain.Company;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins={"*"}, maxAge=6000)
@Api(tags= {"Pics FileControl"})
public class FileController {
	
	private String fileSeparator = File.separator;
	private String root;
	private String path;

	/* 파일 업로드 반응값
	 * 성공했을 경우 => 파일 이름 리턴
	 * 파일이 존재하지 않을 경우 => 응답하지 않음 */

	@ApiOperation(value="하나의 파일을 스프링부트에 저장하고 변환한 파일명 반환",response =String.class)
	@PostMapping("/fileUpload/{subPath}/{id}")
	public ResponseEntity uploadImage(@RequestBody MultipartFile file, 
			@PathVariable String subPath, @PathVariable String id,
			HttpServletRequest request) {		
		/* 업체 아이디 받아오기 */
//		System.out.println("업체 아이디 : " + id);

		String fileName = ""; //화면으로 보낼 파일의 이름들

		/* 파일 경로 설정하기 */
		root = request.getSession().getServletContext().getRealPath("/");
		path = root + "upload" + fileSeparator + subPath + fileSeparator; //공통 파일 경로
//		System.out.println(path);

		if(file==null) return new ResponseEntity(HttpStatus.NO_CONTENT);

		else {
			fileName = file.getOriginalFilename(); //업로드된 파일명

			/* 파일 정보 확인 */
//			System.out.println("파일의 사이즈 :: "+file.getSize());
//			System.out.println("업로드된 파일명 :: "+fileName);
//			System.out.println("파일의 파라미터명 :: "+file.getName());

			/* 파일 이름 설정하기(현재시간+_+comId+확장자) */
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")); //현재 시간
			int i = fileName.lastIndexOf("."); //파일 확장자 위치
			fileName = now + "_" + id + fileName.substring(i, fileName.length());
//			System.out.println("새롭게 설정한 파일 이름 :: " + fileName);

			try {
				file.transferTo(new File(path+fileName)); //파일 생성
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			return new ResponseEntity(fileName, HttpStatus.OK);

		}
	}

	@ApiOperation(value="여러개의 파일을 스프링부트에 저장하고 변환한 파일명 반환",response =String.class)
	@PostMapping("/filesUpload/{subPath}/{comId}")
	public ResponseEntity uploadImages(@RequestBody List<MultipartFile> files, 
			@PathVariable String subPath, @PathVariable String comId,
			HttpServletRequest request) {
		/* 업체 아이디 받아오기 */
//		System.out.println("업체 아이디 : " + comId);


		String fileNames = ""; //화면으로 보낼 파일의 이름들

		/* 파일 경로 설정하기 */
		root = request.getSession().getServletContext().getRealPath("/");
		path = root + "upload"+fileSeparator+ subPath + fileSeparator; //공통 파일 경로
//		System.out.println(path);

		if(files.size()==0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		else {
			int count = 0;
			for(MultipartFile file : files) {
				String fileName = file.getOriginalFilename(); //업로드된 파일명

				/* 파일 정보 확인 */
//				System.out.println("파일의 사이즈 :: "+file.getSize());
//				System.out.println("업로드된 파일명 :: "+file.getOriginalFilename());
//				System.out.println("파일의 파라미터명 :: "+file.getName());

				/* 파일 이름 설정하기(현재시간+_+comId+확장자) */
				String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")); //현재 시간
				int i = fileName.lastIndexOf("."); //파일 확장자 위치
				fileName = now + "_" + comId + fileName.substring(i, fileName.length());
//				System.out.println("새롭게 설정한 파일 이름 :: " + fileName);

				fileNames += fileName + ",";

				try {
					file.transferTo(new File(path+fileName)); //파일 생성
				} catch (IllegalStateException | IOException e) {
					//e.printStackTrace();
				}
			}
			fileNames = fileNames.substring(0, fileNames.length()-1);
			return new ResponseEntity(fileNames, HttpStatus.OK);

		}
	}
	
	@ApiOperation(value="사용하지 않는 파일 삭제")
	@DeleteMapping("/filedelte/{subPath}/{imgSrc}")
	public ResponseEntity deleteFile(@PathVariable String subPath,@PathVariable String imgSrc, HttpServletRequest request) {
		
		/* 파일 경로 설정하기 */
		root = request.getSession().getServletContext().getRealPath("/");
		path = root + "upload"+fileSeparator+ subPath + fileSeparator; //공통 파일 경로
		//System.out.println(path);
		
		File file = new File(path+imgSrc);
		//System.out.println(path+imgSrc);
		
		if(file.exists()) {
			if(file.delete()) return new ResponseEntity("OK",HttpStatus.OK);
			else return new ResponseEntity("FAIL",HttpStatus.NOT_MODIFIED);
		}
		else return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
