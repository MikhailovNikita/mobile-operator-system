package unit;


import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.tsystems.exceptions.BusinessLogicException;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.entity.TariffOption;
import ru.tsystems.service.OptionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OptionServiceTest {
    @Mock
    private OptionDAO optionDAO;
    @Mock
    private ContractDAO contractDAO;

    @InjectMocks
    private OptionService optionService = new OptionService();

    private TariffOption optionA;
    private TariffOption optionB;
    private TariffOption optionX;



    @Before
    public void beforeTest(){
        optionA = new TariffOption("Option A", 100.0, 200.0, new HashSet<>(), new HashSet<>());
        optionB = new TariffOption("Option B", 200.0, 200.0, new HashSet<>(), new HashSet<>());
        optionX = new TariffOption("Option X", 300.0, 0.0, new HashSet<>(), new HashSet<>());
        Mockito.when(optionDAO.get(1L)).thenReturn(optionA);
        Mockito.when(optionDAO.get(2L)).thenReturn(optionB);
        Mockito.when(optionDAO.get(3L)).thenReturn(optionX);
        Mockito.when(contractDAO.getAll()).thenReturn(new ArrayList<>());
    }

    @Test
    public void testAddingForbiddingRule(){
        optionService.addForbiddingOptions(1L, 2L);
        Assert.assertTrue(setsAreEqual(optionA.getForbiddingOptions(), new HashSet<>(Arrays.asList(optionB))));
        Assert.assertTrue(setsAreEqual(optionB.getForbiddingOptions(), new HashSet<>(Arrays.asList(optionA))));
    }

    @Test
    public void testAddingRequiringRule(){
        optionService.addRequiredOption(1L, 2L);
        Assert.assertTrue(setsAreEqual(optionA.getRequiredOptions(), new HashSet<>(Arrays.asList(optionB))));
        Assert.assertTrue(optionB.getRequiredOptions().isEmpty());
    }


    @Test(expected = BusinessLogicException.class)
    /* This test is for the case when you create a chain of requiring rules
     * which, eventually, loops.
     * e.g.: A->B, B->C, C->A. In this case all the options from a loop become inaccessible.
     */
    public void loopOfRequiringRules(){
        optionService.addRequiredOption(1L, 2L);
        optionService.addRequiredOption(2L, 3L);
        optionService.addRequiredOption(3L, 1L);
    }

    @Test(expected = BusinessLogicException.class)
    /*
     * It's not possible to apply both requiring and forbidding rules to
     * the same options.
     * e.g.: Create forbidding rule A-B and then create requiring rule A->B(or vice versa)
     * Congratulations! Option A is not accessible now!
     */
    public void bothRulesForSameOptions(){
        optionService.addRequiredOption(1L, 2L);
        optionService.addForbiddingOptions(1L, 2L);
    }

    @Test(expected = BusinessLogicException.class)
    public void creatingExistingRequiringRule(){
        optionService.addRequiredOption(1L, 2L);
        optionService.addRequiredOption(1L, 2L);
    }

    @Test(expected = BusinessLogicException.class)
    public void creatingExistingForbiddingRule(){
        optionService.addForbiddingOptions(1L, 2L);
        optionService.addForbiddingOptions(1L, 2L);
    }


    public <E> boolean setsAreEqual(Set<E> set1, Set<E> set2){
        return (set1.size() == set2.size() && set1.containsAll(set2));
    }
}
