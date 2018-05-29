package ru.tsystems.controller.simple;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        int httpErrorCode = getErrorCode(httpRequest);
        final String MESSAGE = "message";

        ModelAndView errorPage = new ModelAndView("util/error");
        switch (httpErrorCode) {
            case 403: {
                errorPage.addObject(MESSAGE, "Whoops! You have no rights to watch this page." );
                break;
            }
            case 404: {
                errorPage.addObject(MESSAGE, "The page is not found. Where did you get that link?" );
                break;
            }
            //Considered to be 500 but can be any other error
            default: {
                errorPage.addObject(MESSAGE, "Whoops! Something went really wrong. Please contact me at nnikita.mikhailov@yandex.ru" );
            }
        }

        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
