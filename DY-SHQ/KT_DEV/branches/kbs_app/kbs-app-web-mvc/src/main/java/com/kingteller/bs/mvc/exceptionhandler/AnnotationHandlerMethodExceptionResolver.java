package com.kingteller.bs.mvc.exceptionhandler;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

public class AnnotationHandlerMethodExceptionResolver extends
		ExceptionHandlerExceptionResolver {
	private String defaultErrorView;

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	protected ModelAndView doResolveHandlerMethodException(
			HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {

		if (handlerMethod == null) {
			return null;
		}

		Method method = handlerMethod.getMethod();

		if (method == null) {
			return null;
		}

		ModelAndView returnValue = super.doResolveHandlerMethodException(
				request, response, handlerMethod, exception);

		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method,
				ResponseBody.class);
		if (responseBodyAnn != null) {
			try {
				ResponseStatus responseStatusAnn = AnnotationUtils
						.findAnnotation(method, ResponseStatus.class);
				if (responseStatusAnn != null) {
					HttpStatus responseStatus = responseStatusAnn.value();
					String reason = responseStatusAnn.reason();
					if (!StringUtils.hasText(reason)) {
						response.setStatus(responseStatus.value());
					} else {
						try {
							response.sendError(responseStatus.value(), reason);
						} catch (IOException e) {
						}
					}
				}
				ModelAndView jsonView = new ModelAndView(
						new NetbankMappingJacksonJsonView());
				jsonView.addObject(returnValue.getModelMap());
				return jsonView;
			} catch (Exception e) {
				return null;
			}
		}

		if (returnValue == null) {
			returnValue = new ModelAndView(defaultErrorView);
		} else if (returnValue.getViewName() == null) {
			returnValue.setViewName(defaultErrorView);
		}
		return returnValue;

	}

}
