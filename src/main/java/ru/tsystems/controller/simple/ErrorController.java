package ru.tsystems.controller.simple;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        String errorMsg;
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 403: {
                return new ModelAndView();
            }
            case 404: {
                return new ModelAndView("error/page404");
            }
            //Considered to be 500 but can be any other error
            default: {
                return new ModelAndView("error/page500");
            }
        }

    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
