package gd.app.mvc;

import com.google.common.collect.Maps;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.prerender.PreRender;
import gd.infrastructure.uid.UID;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class AppController {

    // SERVICES
    @Autowired
    private Environment environment;

    @Autowired
    private PreRender preRender;

    @Autowired
    private UID uid;

    // =========================================================================
    // PAGES (RECOMMENDATIONS)
    // =========================================================================
    @RequestMapping(value = "/")
    public String root() {
        return "redirect:/recommendations";
    }

    @RequestMapping(value = {"/recommendations/**"}, method = RequestMethod.GET)
    public Object recommendations(HttpServletRequest request) {
        // prerender
        if (preRender.shouldPreRender(request)) {
            Optional<String> opHtml = preRender.preRender(request);
            if (opHtml.isPresent()) {
                return new ResponseEntity(opHtml.get(), HttpStatus.OK);
            }
        }

        // render
        return "forward:/dist/index.html";
    }

    // =========================================================================
    // ASSETS / FILES
    // =========================================================================
    @RequestMapping(value = "favicon.ico", method = RequestMethod.GET)
    public String favicon() {
        return "forward:/app/files/favicon.ico";
    }

    @RequestMapping(value = "robots.txt", method = RequestMethod.GET)
    public String robots() {
        return "forward:/app/files/robots.txt";
    }

    @RequestMapping(value = "sitemap*", method = RequestMethod.GET)
    public String sitemap(HttpServletRequest request) {
        // parse sitemap file
        String sitemapPath = request.getRequestURI();
        sitemapPath = sitemapPath.substring(1);

        // safety check to prevent accessing other file
        // it might not even enter the method, but just in case
        if (sitemapPath.contains("..") || sitemapPath.contains("/") || sitemapPath.contains("\\")) {
            return "";
        }

        // download sitemap
        return String.format("forward:/app/files/%s", sitemapPath);
    }
}
