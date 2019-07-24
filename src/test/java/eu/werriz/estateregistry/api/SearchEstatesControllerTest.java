package eu.werriz.estateregistry.api;

import eu.werriz.estateregistry.Application;
import eu.werriz.estateregistry.AutoImportData;
import eu.werriz.estateregistry.converter.AddressToFormConverter;
import eu.werriz.estateregistry.converter.EstatePropertyToFormConverter;
import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.repository.EstatePropertyRepository;
import eu.werriz.estateregistry.service.impl.SearchEstatesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SearchEstatesControllerTest {

    private static final String URI_PREFIX = "/estates/search/";

    @InjectMocks
    private SearchEstatesController controller;

    @Mock
    private EstatePropertyRepository estatePropertyRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        final EstatePropertyToFormConverter converter = new EstatePropertyToFormConverter(new AddressToFormConverter());
        final SearchEstatesServiceImpl service = new SearchEstatesServiceImpl(estatePropertyRepository, converter);
        controller = new SearchEstatesController(service);
        mockMvc = standaloneSetup(controller).build();

    }

    @Test
    public void getSimilarEstatesTest_success() throws Exception {
        final Long estateId = 1L;

        final EstateProperty estateProperty = AutoImportData.getInitialData().get(0);
        Mockito.when(estatePropertyRepository.findById(estateId)).thenReturn(Optional.of(estateProperty));

        Mockito.when(estatePropertyRepository.findSimilarProperties(estateProperty.getId(), estateProperty.getAddress().getCity(),
                estateProperty.getAddress().getStreet(), estateProperty.getType(), estateProperty.getSize(),
                PageRequest.of(0, 3, Sort.Direction.DESC, "marketValue")))
        .thenReturn(AutoImportData.getInitialData().subList(0, 3));

        final MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(URI_PREFIX + "similar/{estateId}", estateId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Status should be 200", 200, mvcResult.getResponse().getStatus());

        final String[] resultArr = mvcResult.getResponse().getContentAsString().split("}}},");
        assertEquals("Searched properties array size should be: 3", 3, resultArr.length);

        verify(estatePropertyRepository, atLeastOnce()).findById(estateId);
        verify(estatePropertyRepository, atLeastOnce()).findSimilarProperties(estateProperty.getId(),
                estateProperty.getAddress().getCity(), estateProperty.getAddress().getStreet(),
                estateProperty.getType(), estateProperty.getSize(), PageRequest.of(0, 3,
                        Sort.Direction.DESC, "marketValue"));
    }
}
