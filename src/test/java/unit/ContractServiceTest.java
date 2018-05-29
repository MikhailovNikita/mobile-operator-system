package unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.exceptions.BusinessLogicException;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.dao.api.TariffDAO;
import ru.tsystems.persistence.dao.api.UserDAO;
import ru.tsystems.persistence.entity.*;
import ru.tsystems.service.ContractService;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(MockitoJUnitRunner.Silent.class)
public class ContractServiceTest {
    @Mock
    private ContractDAO contractDAO;
    @Mock
    private TariffDAO tariffDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private OptionDAO optionDAO;

    @InjectMocks
    private ContractService contractService = new ContractService();

    private User user1;
    private User user2;
    private TariffOption optionA;
    private TariffOption optionB;
    private TariffOption optionX;
    private Tariff tariffA;
    private Tariff tariffB;
    private Contract contract1;
    private Contract contract2;
    private HashSet<TariffOption> optionSet;
    private HashSet<TariffOption> optionSet2;

    @Before
    public void beforeTest() {
        user1 = new User("Petya", "Petrov", "petya@gmail.com", "Moscow", new Date(),
                "100", "qwerty", false, new HashSet<>(), UserRole.ROLE_USER);

        user2 = new User("Ivan", "Ivanov", "vanya@gmail.com", "Moscow", new Date(),
                "101", "123456", false, new HashSet<>(), UserRole.ROLE_USER);

        optionA = new TariffOption("Option A", 100.0, 200.0, new HashSet<>(), new HashSet<>());
        optionB = new TariffOption("Option B", 200.0, 200.0, new HashSet<>(), new HashSet<>());
        optionX = new TariffOption("Option X", 300.0, 0.0, new HashSet<>(), new HashSet<>());
        optionSet = new HashSet<>();
        optionSet2 = new HashSet<>();
        optionSet.add(optionA);
        optionSet.add(optionB);
        optionSet2.add(optionA);

        tariffA = new Tariff("Tariff A", 100.0, false, optionSet, new HashSet<>());
        tariffB = new Tariff("Tariff B", 200.0, false, optionSet2, new HashSet<>());

        contract1 = new Contract("88005553535", tariffA, optionSet, user1, false, false, new Date());
        contract2 = new Contract("number", tariffB, optionSet2, user2, false, false, new Date());
        when(contractDAO.get(1L)).thenReturn(contract1);
        when(contractDAO.get(2L)).thenReturn(contract2);
        when(tariffDAO.get(1L)).thenReturn(tariffA);
        when(tariffDAO.get(2L)).thenReturn(tariffB);
        when(optionDAO.get(1L)).thenReturn(optionA);
        when(optionDAO.get(2L)).thenReturn(optionB);
    }

    @Test
    public void testTariffChange(){
        contractService.changeTariff(1L, 2L);
        assertEquals(tariffB, contract1.getTariff());
    }

    @Test
    public void optionDisable(){
        contractService.disableOption(1L, 2L);
        assertTrue(setsAreEqual(optionSet2, contract1.getEnabledOptions()));
    }

    @Test
    public void testDeletingNotSupportedOptionsAfterTariffChange() {
        contractService.changeTariff(1L, 2L);

        assertTrue(setsAreEqual(contract1.getEnabledOptions(), optionSet2));
    }

    @Test
    public void testBlockingContractByUser(){
        contractService.userBlocksContract(1L);
        assertTrue(contract1.isBlockedByUser());
        contractService.userUnblocksContract(1L);
        assertFalse(contract1.isBlockedByUser());
    }


    @Test
    public void testBlockingContractByAdmin(){
        contractService.adminBlocksContract(1L);
        assertTrue(contract1.isBlockedByAdmin());
        contractService.adminUnblocksContract(1L);
        assertFalse(contract1.isBlockedByAdmin());
    }


    @Test(expected = BusinessLogicException.class)
    public void testIfContractBlockedByAdminIsImmutable(){
        contractService.adminBlocksContract(1L);
        contractService.adminBlocksContract(2L);
        contractService.changeTariff(1L, 2L);

        //can't change tariff
        assertEquals(tariffA, contract1.getTariff());

        //can't disable option
        contractService.disableOption(1L, 1L);
        assertTrue(setsAreEqual(optionSet, contract1.getEnabledOptions()));

        //can't enable option
        contractService.enableOption(2L, 2L);
    }

    @Test(expected = BusinessLogicException.class)
    public void testIfContractBlockedByUserIsImmutable(){
        contractService.userBlocksContract(1L);
        contractService.userBlocksContract(2L);
        contractService.changeTariff(1L, 2L);

        //can't change tariff
        assertEquals(tariffA, contract1.getTariff());

        //can't disable option
        contractService.disableOption(1L, 1L);

        //can't enable option
        contractService.enableOption(2L, 2L);

    }

    @Test(expected = BusinessLogicException.class)
    public void createInvalidContract(){
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setNumber("322");
        contractService.addNewContract(contractDTO);
    }

    public <E> boolean setsAreEqual(Set<E> set1, Set<E> set2){
        return (set1.size() == set2.size() && set1.containsAll(set2));
    }
}