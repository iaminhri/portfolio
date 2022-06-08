package shadow.practice.portfolio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shadow.practice.portfolio.Model.Services;
import shadow.practice.portfolio.Repository.ServicesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ServicesController {

    @Autowired
    private ServicesRepository servicesRepository;

//    @GetMapping("/services")
//    public String displayServices(@RequestParam(required = false)boolean gamedev,
//                                  @RequestParam(required = false)boolean webdev,
//                                  Model model){
//        model.addAttribute("gamedev", gamedev);
//        model.addAttribute("webdev", webdev);
//        List<Services> services = Arrays.asList(
//                new Services("Web Developer", "Spring Full Stack, HTML, CSS, Bootstrap,", Services.Type.WEBDEV),
//                new Services("Game Developer ", "Unreal Engine4, Unreal Engine5, Visual Studio, C++", Services.Type.GAMEDEV),
//                new Services("3D Modeller", "Blender, Quixel Mixer, Substance Painter, Photoshop", Services.Type.GAMEDEV),
//                new Services("Java Programmer", "JAVA, SPRING Framework, Problem Solving", Services.Type.WEBDEV),
//                new Services("Photographer", "Wildlife Photography", Services.Type.GAMEDEV),
//                new Services("Networker", "CCNA Certification", Services.Type.WEBDEV)
//        );

        @GetMapping("/services/{display}")
        public String displayServices(@PathVariable String display,
                                  Model model){
        if(display != null && display.equals("all")){
            model.addAttribute("gamedev", true);
            model.addAttribute("webdev", true);
        }
        else if(display != null && display.equals("gamedev"))
            model.addAttribute("gamedev", true);
        else
            model.addAttribute("webdev", true);

//        List<Services> services = Arrays.asList(
//                new Services("Web Developer", "Spring Full Stack, HTML, CSS, Bootstrap", Services.Type.WEBDEV),
//                new Services("Game Developer ", "Unreal Engine4, Unreal Engine5, Visual Studio, C++", Services.Type.GAMEDEV),
//                new Services("3D Modeller", "Blender, Quixel Mixer, Substance Painter, Photoshop", Services.Type.GAMEDEV),
//                new Services("Java Programmer", "JAVA, SPRING Framework, Problem Solving", Services.Type.WEBDEV),
//                new Services("Photographer", "Wildlife Photography", Services.Type.GAMEDEV)
//        );

        List<Services> services = servicesRepository.findAllServices();

        Services.Type[] types = Services.Type.values();

        for(Services.Type type : types){
            model.addAttribute(type.toString(),
                    services.stream().filter(Services -> Services.getType().equals(type)).collect(Collectors.toList())
                    );
        }
        return "services.html";
    }
}
