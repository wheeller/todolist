package todolist.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    final Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.error("Catch exception " + e.getClass() + ": " + e.getMessage());
            ExceptionResponseBody exceptionResponseBody = new ExceptionResponseBody("INTERNAL_ERROR", e.getMessage());

            if (e.getClass().equals(ExpiredJwtException.class)){
                httpStatus = HttpStatus.UNAUTHORIZED;
                exceptionResponseBody = new ExceptionResponseBody("TOKEN_ERROR", e.getMessage());
            }
           response.setStatus(httpStatus.value());
//           response.getWriter().write(exceptionResponseBody.toString());
//           response.getWriter().write(e.getMessage());

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(exceptionResponseBody);
            out.flush();
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}