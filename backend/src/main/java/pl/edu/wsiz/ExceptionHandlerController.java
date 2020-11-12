package pl.edu.wsiz;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.edu.wsiz.core.ApplicationException;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ApplicationException.class)
	public void applicationExceptionHandler(ApplicationException e, HttpServletResponse response) throws IOException {
		
		JsonResponse jsonReponse = new JsonResponse(e.getMessage());
		response.getWriter().write(new ObjectMapper().writeValueAsString(jsonReponse));
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		response.setStatus(httpStatus.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.flushBuffer();
	}
	

}
