package project.controllers.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.services.users.StorageService;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Controller
public class FilesController {

	@Autowired
	private StorageService storageService;

	@PostMapping(value = "/storage")
	@PreAuthorize("permitAll()")
	public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
		System.out.println("FC.uF()");
		String name = storageService.store(multipartFile, authentication);
		ModelAndView mv = new ModelAndView("redirect:/files/" + name);
		return mv;
	}
	// localhost:8080/files/123809183093qsdas09df8af.jpeg

	@GetMapping(value = "/files/{file-name:.+}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Resource> getFile(@PathVariable("file-name") String fileName) {
		System.out.println("FC.gF() file-name:" + fileName);
		Optional<File> fileCandidate = storageService.load(fileName);
		if (fileCandidate.isPresent()) {
			Resource fileSystemResource = new FileSystemResource(fileCandidate.get());
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileSystemResource);
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new FileSystemResource(storageService.get404()));
	}

	@GetMapping(value = "/storage")
	@PreAuthorize("permitAll()")
	public ModelAndView getStoragePage(Authentication authentication) {
		if (authentication != null) {
			ModelAndView mv = new ModelAndView("file_upload");
			List<String> images = storageService.getAllImages();
			mv.addObject("images", images);
			return mv;
		}
		return new ModelAndView("redirect:/signin");
	}

//    @GetMapping(value = "/storage")
//    @PreAuthorize("permitAll()")
//    public ModelAndView getStoragePage() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(!authentication.getAuthorities().contains("ROLE_ANONYMOUS")) {
//            ModelAndView mv = new ModelAndView("file_upload");
//            List<String> images = storageService.getAllImages();
//            mv.addObject("images", images);
//            return mv;
//        }
//        return new ModelAndView("redirect:/signin");
//    }
}
