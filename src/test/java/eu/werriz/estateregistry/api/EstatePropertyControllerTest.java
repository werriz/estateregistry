package eu.werriz.estateregistry.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.werriz.estateregistry.Application;
import eu.werriz.estateregistry.AutoImportData;
import eu.werriz.estateregistry.converter.AddressToEntityConverter;
import eu.werriz.estateregistry.converter.AddressToFormConverter;
import eu.werriz.estateregistry.converter.EstatePropertyToEntityConverter;
import eu.werriz.estateregistry.converter.EstatePropertyToFormConverter;
import eu.werriz.estateregistry.form.AddressForm;
import eu.werriz.estateregistry.form.EstatePropertyForm;
import eu.werriz.estateregistry.form.OwnerForm;
import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.model.EstatePropertyType;
import eu.werriz.estateregistry.repository.EstatePropertyRepository;
import eu.werriz.estateregistry.service.impl.EstatePropertyServiceImpl;
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

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class EstatePropertyControllerTest {

    private static final String URI_PREFIX = "/estates/";

    @InjectMocks
    private EstatePropertyController controller;

    @Mock
    private EstatePropertyRepository repository;

    private MockMvc mockMvc;
    private EstatePropertyToEntityConverter converter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        converter = new EstatePropertyToEntityConverter(new AddressToEntityConverter());
        EstatePropertyServiceImpl service = new EstatePropertyServiceImpl(
                repository, new EstatePropertyToFormConverter(new AddressToFormConverter()), converter);
        controller = new EstatePropertyController(service);
        mockMvc = standaloneSetup(controller).build();

    }

    @Test
    public void getAllTest_success() throws Exception {

        Mockito.when(repository.findAll()).thenReturn(AutoImportData.getInitialData());

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI_PREFIX)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Status should be 200", 200, mvcResult.getResponse().getStatus());

        String[] properties = mvcResult.getResponse().getContentAsString().split("}}},");
        assertEquals("Estate properties array length should be: 10", 10, properties.length);

        verify(repository, atLeastOnce()).findAll();
    }

    @Test
    public void postTest_success() throws Exception{

        final EstatePropertyForm form = new EstatePropertyForm(
                1L, new BigDecimal(1), new BigDecimal(2),
                EstatePropertyType.HOUSE.toString());

        final AddressForm addressForm = new AddressForm(null, "street",
                1, "city");
        form.setAddress(addressForm);
        final OwnerForm ownerForm = new OwnerForm(null, "name", "surname",
                "personalCode", addressForm);
        form.setOwner(ownerForm);

        final EstateProperty property = converter.convert(form);
        Mockito.when(repository.save(property)).thenReturn(property);

        mockMvc.perform(MockMvcRequestBuilders
                .post(URI_PREFIX)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(form)))
                .andExpect(status().isOk());

        verify(repository, atLeastOnce()).save(property);
    }

    @Test
    public void deleteTest_success() throws Exception {

        final Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                .delete(URI_PREFIX +"{id}", id))
                .andExpect(status().isOk());

        verify(repository, atLeastOnce()).deleteById(id);
    }
}
