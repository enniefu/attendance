package controller;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bean.ApplicationForEW;
import bean.ApplicationForLeave;
import bean.AttendanceRecord;
import bean.Employee;
import face.search.FaceInteraction;
import face.search.FaceSearch;
import service.EmployeeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/add")
	public int addAppicateForEW(@RequestBody ApplicationForEW e) {
		return employeeService.applicateEW(e);
		
	}
	
	@RequestMapping("/appEWfind/{applicatedPerson}")
	public List<ApplicationForEW> findByIdApplicationForEW(@PathVariable  int applicatedPerson) {
		return employeeService.FindById(applicatedPerson);
		
	}
	
	@RequestMapping("/findall")
	public List<ApplicationForEW> findAllApplicationForEW(){
		logger.info("从数据库读取ApplicationForEW信息");
		return employeeService.findAll();
	}
	
	@RequestMapping("/delete")
	public int deleteApplicationForEW(HttpServletRequest request,@RequestParam("applicatedId") int applicatedId) {
		logger.info("删除数据");
		return employeeService.deleteApplication(applicatedId);
	}
	
	
	@RequestMapping("/employeeinfo/{employeeId}")
    public List<Employee> getEmployeeById(@PathVariable int employeeId){
		logger.info("从数据库中根据员工id="+employeeId+",读取Employee信息");
    	return employeeService.employeeFindById(employeeId);
    }
	
	@RequestMapping("/attendanceinfo/{userId}")
    public List<AttendanceRecord> getAttendanceById(@PathVariable int userId){
		logger.info("从数据库中根据员工id="+userId+",读取attendance_record信息");
    	return employeeService.attendanceFindById(userId);
    }
	
	@RequestMapping("/applicationForLeaveinfo/{applicaedPerson}")
    public List<ApplicationForLeave> getApplicationForLeaveById(@PathVariable int applicaedPerson){
		logger.info("从数据库中根据员工id="+applicaedPerson+",读取applicationForLeave信息");
    	return employeeService.applicationForLeaveFindById(applicaedPerson);
    }
	
	@RequestMapping("/employeeAdd")
    public int addEmployee(@RequestBody Employee employee){
		logger.info("向数据库添加员工");
    	return employeeService.addEmployee(employee);
    }
	
	@RequestMapping("/deleteemployee/{employeeId}")
    public int deleteEmployeeById(@PathVariable int employeeId){
		logger.info("从数据库中根据员工id="+employeeId+",删除相应Employee信息");
    	return employeeService.deleteEmployee(employeeId);
    }
	
	@RequestMapping("/applicationForLeave")
	public int addApplicationForLeave(@RequestBody ApplicationForLeave applicationForLeave){
		logger.info("向数据库添加请假表");
    	return employeeService.addApplicationForLeave(applicationForLeave);
    }
	/**
	 *@param     一张图片
	 *@return   成功：user_id,失败：0
	 *
	 */
	//上班打卡识别
	@PostMapping(value="/ai/photo/identify",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public int identifyP(@RequestParam("image") MultipartFile image) throws Exception{
		
			logger.info("接受图片");
			logger.info("开始识别");
			//return FaceSearch.search(path);
			String user_id = FaceSearch.search(image);
			
			if(!user_id.equals("false")) {
				int userId = Integer.parseInt(user_id);
				return employeeService.attendance(userId);
			}
			
	   return 0;
	}
	
//	/**
//	 *@param    一张图片
//	 *@return
//	 *
//	 */
//	//下班打卡识别
//	@PostMapping(value="/ai/photo/identifydown",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
//	public int identifyPDown(@RequestParam("image") MultipartFile image) throws Exception{
//		try {
//			logger.info("接受图片");
//			//保存文件
//			
//			logger.info("开始识别");
//			//return FaceSearch.search(path);
//			String user_id = FaceSearch.search(image);
//			
//			if(!user_id.equals("false")) {
//				int userId = Integer.parseInt(user_id);
//				return employeeService.attendanceDown(userId);
//			}
//		}
//		catch (Exception e){
//			e.printStackTrace();
//			return 0;
//		}
//		return 0;
//	}
	
	//录入照片入库
	@PostMapping(value="/ai/{id}/photo/add" , consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public boolean add(@RequestParam("image") MultipartFile image,@PathVariable int id) throws Exception {
		try {
			logger.info("接受图片");
			//保存文件
			FileOutputStream fos=new FileOutputStream("target/"+image.getOriginalFilename());
			IOUtils.copy(image.getInputStream(), fos);
			fos.close();
			String path="target/"+image.getOriginalFilename();
			//录入图片
			logger.info("录入图片");
			return FaceInteraction.add(path, id);
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	//人脸更新
	@PostMapping(value="/ai/{id}/photo/update" , consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public boolean update(@RequestParam("image") MultipartFile image,@PathVariable int id) throws Exception {
		try {
			logger.info("接受图片");
			//保存文件
			FileOutputStream fos=new FileOutputStream("target/"+image.getOriginalFilename());
			IOUtils.copy(image.getInputStream(), fos);
			fos.close();
			String path="target/"+image.getOriginalFilename();
			//更新图片
			logger.info("更新图片");
			return FaceInteraction.update(path, id);
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//这应该是经理有的权限 暂时先放在这了
	//获取所有库中用户id
	@GetMapping(value="/ai/getAllUsers")
	public List<String> getAll(){
		List<String> ans=new ArrayList<String>();
		ans=FaceInteraction.getUsers();
		return ans;
	}
	
	//删除库中某一用户
	@GetMapping(value="/ai/{id}/photo/delete")
	public boolean delete(@PathVariable int id) {
		return FaceInteraction.delete(id);
	}
	
	
}
