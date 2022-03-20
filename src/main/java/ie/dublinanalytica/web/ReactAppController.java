package ie.dublinanalytica.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to direct all requests to frontend routes to index.html so that they
 * are handled by react-router.
 */
@Controller
public class ReactAppController implements ErrorController {
  @GetMapping({
    "/",
    "/login",
    "/register",
    "/account",
    "/dashboard",
    "/dashboard/orders",
    "/dashboard/datasets",
    "/dataset/{id:.{36}}",
    "/dataset/add",
    "/dataset/edit/{id:.{36}}",
    "/marketplace",
    "/404",
    "/orders",
    "/basket"
  })
  public String getIndex() {
    return "/index.html";
  }

  // @RequestMapping("/error")
  // public String handleError() {
  //   // return "redirect:/404";
  // }

}
