package sem3chatbot.backend.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse res, FilterChain chain)
    throws ServletException, IOException {
        res.setStatus(HttpServletResponse.SC_OK);
       // res.addHeader("Access-Control-Allow-Origin","http://localhost:3000");
        chain.doFilter(request, res);

    }
}
