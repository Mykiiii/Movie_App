package com.movieapp.moviefav.filter;

import io.jsonwebtoken.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httPreq = (HttpServletRequest) request;
        HttpServletResponse httPres = (HttpServletResponse)response;

        httPres.setHeader("Access-Control-Allow-Origin", "*");
        httPres.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
        httPres.setHeader("Access-Control-Allow-Credentials", "true");
        httPres.setHeader("Access-Control-Allow-Headers", "*");


        String authHeader=httPreq.getHeader("Authorization");

        if((authHeader==null) || (!authHeader.startsWith("Bearer")))
        {
            throw new ServletException("JWT Token is missing");
        }

        String tok=authHeader.substring(7);

        try
        {
            JwtParser jwtParserObj= Jwts.parser().setSigningKey("movieAdminkey");
            Jwt jwtObj=jwtParserObj.parse(tok);
            Claims claim=(Claims)jwtObj.getBody();
            System.out.println("user created token is " + claim.getSubject());
            System.out.println(httPreq.getHeader("Type"));

        }
        catch(SignatureException exc)
        {
            throw new ServletException("signature mismatch");
        }
        catch (MalformedJwtException exc)
        {
            throw new ServletException("token is modified by unauthorized user");
        }
        chain.doFilter(httPreq, httPres);



    }
}
