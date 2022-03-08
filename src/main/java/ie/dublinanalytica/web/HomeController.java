package ie.dublinanalytica.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HomeController.
 */
@Controller
public class HomeController {

  @RequestMapping(value = "/")
  public String index() {
    return "index";
  }

}
