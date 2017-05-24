package gd.app.mvc;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class AppController {

    // =========================================================================
    // PAGES (RECOMMENDATIONS)
    // =========================================================================
    @RequestMapping(value = "/")
    public String root() {
        return "redirect:/recommendations";
    }

    @RequestMapping(value = {"/recommendations/**"}, method = RequestMethod.GET)
    public Object recommendations(HttpServletRequest request) {
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
