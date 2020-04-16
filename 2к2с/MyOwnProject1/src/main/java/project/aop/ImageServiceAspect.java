package project.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.security.details.UserDetailsImpl;
import project.services.users.email.EmailService;


@Aspect
@Component
public class ImageServiceAspect {

    @Autowired
    private EmailService emailService;

    @Pointcut("execution(* project.services.users.StorageServiceImpl.store(..))")
    private String store(){
        return null;
    }

    @AfterReturning(pointcut = "store() && args(multipartFile, authentication)", returning = "name")
    public void afterReturningAdvice(String name, MultipartFile multipartFile, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("before sending");
        emailService.sendImage(userDetails.getUser().getEmail(), name);
        System.out.println("after sending");
        System.out.println("В аспекте я поймал " + name);
    }

}
