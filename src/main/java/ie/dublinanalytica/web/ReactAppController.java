package ie.dublinanalytica.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to direct all requests to frontend routes to index.html so that they
 * are handled by react-router.
 */
@Controller
public class ReactAppController {
  @GetMapping({
    "/", "/login", "/register", "/account", "/dashboard", "/dataset", "/marketplace", "/404"
  })
  public String getIndex() {
    return "/index.html";
  }

  @RequestMapping("/error")
  public String handleError() {
    return "redirect:/404";
  }

}
