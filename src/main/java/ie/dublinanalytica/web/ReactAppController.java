package ie.dublinanalytica.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to direct all requests to frontend routes to index.html so that they
 * are handled by react-router.
 */
@Controller
public class ReactAppController {
  @GetMapping({ "/", "/login", "/register", "/dashboard", "/dataset", "/marketplace" })
  public String getIndex() {
    return "/index.html";
  }
}