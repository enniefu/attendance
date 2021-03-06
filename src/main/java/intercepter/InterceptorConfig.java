package intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter  {
	
	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/**").excludePathPatterns("/login/**", "/register","/hello");
	        registry.addInterceptor(new ManagerIntercepter()).addPathPatterns("/manager/**");
	        registry.addInterceptor(new MasterIntercepter()).addPathPatterns("/master/**");
	        
	        super.addInterceptors(registry);
	    }		

}
