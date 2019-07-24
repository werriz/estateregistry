package eu.werriz.estateregistry.api;

import eu.werriz.estateregistry.Application;
import eu.werriz.estateregistry.AutoImportData;
import eu.werriz.estateregistry.repository.EstatePropertyRepository;
import eu.werriz.estateregistry.service.impl.CalculateTaxesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CalculateTaxesControllerTest {

    private static final String URI_PREFIX = "/calculate/taxes/";

    @InjectMocks
    private CalculateTaxesController controller;

    @Mock
    private EstatePropertyRepository estatePropertyRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CalculateTaxesServiceImpl service = new CalculateTaxesServiceImpl(estatePropertyRepository);
        controller = new CalculateTaxesController(service);
        mockMvc = standaloneSetup(controller).build();

    }

    @Test
    public void calculateTaxesTest_success() throws Exception {

        final Long ownerId = 1L;

        Mockito.when(estatePropertyRepository.findAllByOwnerId(ownerId)).thenReturn(AutoImportData.getInitialData().subList(0,2));

        final MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(URI_PREFIX + "{ownerId}", ownerId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Status should be 200", 200, mvcResult.getResponse().getStatus());

        final String result = mvcResult.getResponse().getContentAsString();
        assertEquals("Calculated taxes value should be: 295500.0", "295500.0", result);

        verify(estatePropertyRepository, atLeastOnce()).findAllByOwnerId(ownerId);
    }
}
