package ru.tsystems.controller.rest;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.service.OptionService;
import ru.tsystems.utils.JMSProducer;

import javax.jms.JMSException;
import java.util.List;

/**
 * Only for test
 */
@RestController()
public class TestController {
    @Autowired
    private OptionService optionService;
    @Autowired
    private JMSProducer jmsProducer;

    private static final Logger logger = Logger.getLogger(TestController.class);

    @GetMapping("/api/options")
    public List<OptionDTO> get(){
        return optionService.getAllOptions();
    }

    @PostMapping("/api/options")
    public String post(){
        jmsProducer.sendMessage();
        return "Done!";
    }

    @GetMapping("/api/options/{id}")
    public OptionDTO getOption(@PathVariable long id){
        return optionService.getOptionById(id);
    }

    @DeleteMapping("/api/options/{id}")
    public String deleteOption(@PathVariable long id){
        optionService.deleteOptionById(id);
        try{
            JMSProducer jmsProducer = new JMSProducer();
            jmsProducer.sendMessage();
        }catch (JMSException e){
            logger.debug(e);
        }

        return "Done!";
    }
}
