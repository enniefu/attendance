package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import bean.Employee;
import bean.Manager;
import bean.User;
import dao.EmployeeDao;
import dao.ManagerDao;
import dao.UserDao;


@CrossOrigin(origins = "http://39.105.38.34", maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping("/login")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ManagerDao managerDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private UserDao userDao;


	
	@PostMapping("/login")
    public String login(@RequestBody User user ) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();

		JSONObject ans=new JSONObject();
		ans.put("status", 0);
    	if(userDao.searchUserByIdAndPasswd(user).isEmpty()) return ans.toString();
    	else {
    		User loginuser = userDao.searchUserByIdAndPasswd(user).get(0);
    		
    		session.setAttribute("user", loginuser);
        	
       
        	if(employeeDao.findEmployeeByUserId(loginuser.getId()).isEmpty()) {
        		Manager manager = managerDao.findManagerByUserId(loginuser.getId()).get(0);
        		session.setAttribute("manager", manager);
        	}else {
        		Employee employee = employeeDao.findEmployeeByUserId(loginuser.getId()).get(0);
        		if(employee.isManager()!=false){
            		session.setAttribute("master", employee);
            	}else{
            		session.setAttribute("employee", employee);
            	}
        	}
        		
        }	
    	ans.put("status", 1);
        return ans.toString();
    }
	
	
	@RequestMapping("/error")
	public String error() {
		return "login error";
	}
	

}
