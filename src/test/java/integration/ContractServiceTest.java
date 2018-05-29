package integration;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.tsystems.service.ContractService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
@WebAppConfiguration
@Ignore("Travis ne smog")
public class ContractServiceTest {

    @Autowired
    private ContractService contractService;

    @Test
    public void findTariffByName(){
        Assert.assertEquals( "+7(981)7868685",contractService.findContractByNumber("+7(981)7868685").getNumber());
    }

    @Test
    public void findTariffByNumberFail(){
        //just checking that looking for a wrong number doesn't crash everything
        Assert.assertFalse(contractService.isContractWithSuchNumberExist("322"));
    }



}
