//package com.maen.vlogwebserviceserver.web;
//
//import com.maen.vlogwebserviceserver.config.auth.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@RequiredArgsConstructor
//@RestController
//public class TokenApiController {
//    private final JwtService tokenService;
//
//    @GetMapping("/api/v1/token/expired")
//    public String auth() {
//        throw new RuntimeException();
//    }
//
//    @GetMapping("/api/v1/token/refresh")
//    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
//        String token = request.getHeader("Refresh");
//
//        if (token != null && tokenService.verifyToken(token)) {
//            String email = tokenService.getUid(token);
//    //        Token newToken = tokenService.generateToken(email, "USER");
//
// //           response.addHeader("Auth", newToken.getToken());
//  //          response.addHeader("Refresh", newToken.getRefreshToken());
//            response.setContentType("application/json;charset=UTF-8");
//
//            return "REFRESH COMPLETE";
//        }
//
//        throw new RuntimeException();
//    }
//
//}
