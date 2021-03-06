package com.devils.pics.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devils.pics.domain.Category;
import com.devils.pics.domain.RepeatDate;
import com.devils.pics.domain.Schedule;
import com.devils.pics.domain.Studio;
import com.devils.pics.domain.StudioFilter;
import com.devils.pics.domain.Tag;
import com.devils.pics.service.ScheduleService;
import com.devils.pics.service.StudioFilterService;
import com.devils.pics.service.StudioInfoService;
import com.devils.pics.service.StudioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins={"*"}, maxAge=6000)
@Api(tags= {"Pics Studio"})
public class StudioController {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private StudioFilterService studioFilterService;

	@Autowired
	private StudioInfoService studioInfoService;
	
	@Autowired
	private StudioService studioService;

	
	@ApiOperation(value="스튜디오 카테고리 반환", response = List.class)
	@GetMapping("/category")
	public ResponseEntity getCategory() {
		List<Category> category = studioInfoService.getCategory();
		if(category.size()<1) return new ResponseEntity(HttpStatus.NO_CONTENT);
		else return new ResponseEntity(category, HttpStatus.OK);
	}

	/* 스튜디오를 등록하고 숫자를 응답함. 화면단에서 받은 응답값을 통해 alert를 다르게 띄움
	 * 화면으로부터 받은 studio가 null값이라서 응답할 내용이 없음. => 응답값 없음
	 * 성공 => 1 응답
	 * 이미 등록된 스튜디오임(중복 등록 방지) => -1 응답 */
	
	@ApiOperation(value="스튜디오 등록", response = Integer.class)
	@PostMapping("/studio")
	public ResponseEntity registerStudio(@RequestBody Studio studio) {
		System.out.println("받아온 폼값 : "+studio);

		if(studio == null) { /* 받아온 studio가 비었을 경우  */
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}else {
			try {
				/* 받아온 폼값에서 studioFilter, tags, repeatDate를 각각 뽑아옴 */
				StudioFilter studioFilter = studio.getStudioFilter();
				ArrayList<Tag> tags = studio.getTag();
				ArrayList<RepeatDate> repeatDates = studio.getSchedule().getRepeatDate();

				String comId = studio.getComId();
				
				/* 이미 존재하는 studio인지 검사 
				 * comId, name이 같거나 comId, address가 같은 스튜디오이면 등록 방지 */
				Map map = new HashMap<>();
				map.put("comId", comId);
				map.put("address", studioFilter.getAddress());
				boolean isExist = studioInfoService.isExistStudio(studio, map);
				if(isExist == true) { //이미 존재하는 스튜디오일 경우
					return new ResponseEntity(-1, HttpStatus.OK);
				}
				else { //존재하지 않은 스튜디오일 경우
					/* studio를 등록  */
					int result = studioInfoService.registerStudioInfo(studio);

					/* autoIncrement로 생긴 Studio Id를 가져옴
					 * (SQL문으로 name, comId가 모두 동일할 경우에만 가져옴. 
					 * 다른 업체의 같은 이름 스튜디오 stuId를 가져오는 것을 방지함.)  */
					int stuId = studioInfoService.getStudioId(studio);

					/* studioFilter에 Studio Id를 set하고, studioFilter를 등록 */
					studioFilter.setStuId(stuId);
					result = studioFilterService.registerStudioFilter(studioFilter);

					/* repeatDates에 Studio Id를 set하고, repeatDate를 등록 */
					for(RepeatDate repeatDate : repeatDates) {
						repeatDate.setStuId(stuId);
						result = scheduleService.registerRepeatDate(repeatDate);
					}

					/* tags에  Studio Id를 set하고, tags를 등록 */
					for(Tag tag : tags) {
						tag.setStuId(stuId);
						result = studioInfoService.registerTag(tag);
					}

				}

			}catch(RuntimeException e) {
				//e.printStackTrace();
			}
			return new ResponseEntity(1, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value="스튜디오 정보 반환", response = List.class)
	@GetMapping("/studio/{comId}")
	public ResponseEntity getStudiosBycomId(@PathVariable String comId) {
		try {
			List<Studio> studios = studioService.getStudiosBycomId(comId);
			return new ResponseEntity(studios,HttpStatus.OK);
		} catch (Exception e) {
			//e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value="스튜디오 수정을 위한 정보 반환", response = Studio.class)
	@GetMapping("/studio/edit/{stuId}")
	public ResponseEntity getStudio(@PathVariable int stuId) {
		Schedule schedule = new Schedule();
		
		try {
			schedule.setRepeatDate((ArrayList<RepeatDate>) scheduleService.getRepeatDateByStuId(stuId));
			Studio studio = studioService.getStudio(stuId);
			studio.setSchedule(schedule);
			System.out.println(studio);
			
			return new ResponseEntity(studio,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/studio")
	public ResponseEntity editSutdio(@RequestBody Studio studio) {
		
		if(studio == null) { /* 받아온 studio가 비었을 경우  */
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}else {
			try {
				/* 받아온 폼값에서 studioFilter, tags, repeatDate를 각각 뽑아옴 */
				StudioFilter studioFilter = studio.getStudioFilter();
				ArrayList<Tag> tags = studio.getTag();
				ArrayList<RepeatDate> repeatDates = studio.getSchedule().getRepeatDate();
				int stuId = studio.getStuId();
				
				studioService.updateStudio(studio);
				/* repeatDates에 Studio Id를 set하고, repeatDate를 등록 */
				for(RepeatDate repeatDate : repeatDates) {
					repeatDate.setStuId(stuId);
					scheduleService.updateRepeatDate(repeatDate);
				}
				
				for(Tag tag : tags) {
					tag.setStuId(stuId);
					studioInfoService.registerTag(tag);
				}
				return new ResponseEntity(HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@ApiOperation(value="스튜디오 삭제")
	@DeleteMapping("/studio/delete/{stuId}")
	public ResponseEntity deleteStudio(@PathVariable int stuId) {
		try {
			int n = studioService.deleteStudio(stuId);
			return new ResponseEntity(n,HttpStatus.OK);
		} catch (Exception e) {
			//e.printStackTrace();
			return new ResponseEntity(HttpStatus.NOT_MODIFIED);
		}
		
	}

}