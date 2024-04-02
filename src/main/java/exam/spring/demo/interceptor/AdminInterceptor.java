package exam.spring.demo.interceptor;

import java.util.logging.Logger;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object acc = request.getSession().getAttribute("myaccAD");
		if (acc != null) {
			Logger log = Logger.getGlobal();
			log.info("exit account");
			return true;
		} else {
			Logger log = Logger.getGlobal();
			log.info("not exit account");
			response.sendRedirect("/admin/login");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, exception);
	}
}
