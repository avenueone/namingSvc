package org.avenue1.naming.web.rest;

import org.avenue1.naming.NamingSvcApp;

import org.avenue1.naming.domain.Names;
import org.avenue1.naming.repository.NamesRepository;
import org.avenue1.naming.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static org.avenue1.naming.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.avenue1.naming.domain.enumeration.EntityTypeEnum;
/**
 * Test class for the NamesResource REST controller.
 *
 * @see NamesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NamingSvcApp.class)
public class NamesResourceIntTest {

    private static final EntityTypeEnum DEFAULT_ENTITY_TYPE = EntityTypeEnum.DESIGN;
    private static final EntityTypeEnum UPDATED_ENTITY_TYPE = EntityTypeEnum.CONTAINER;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_SUFFIX = "BBBBBBBBBB";

    @Autowired
    private NamesRepository namesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restNamesMockMvc;

    private Names names;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NamesResource namesResource = new NamesResource(namesRepository);
        this.restNamesMockMvc = MockMvcBuilders.standaloneSetup(namesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Names createEntity() {
        Names names = new Names()
            .entityType(DEFAULT_ENTITY_TYPE)
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .format(DEFAULT_FORMAT)
            .prefix(DEFAULT_PREFIX)
            .suffix(DEFAULT_SUFFIX);
        return names;
    }

    @Before
    public void initTest() {
        namesRepository.deleteAll();
        names = createEntity();
    }

    @Test
    public void createNames() throws Exception {
        int databaseSizeBeforeCreate = namesRepository.findAll().size();

        // Create the Names
        restNamesMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(names)))
            .andExpect(status().isCreated());

        // Validate the Names in the database
        List<Names> namesList = namesRepository.findAll();
        assertThat(namesList).hasSize(databaseSizeBeforeCreate + 1);
        Names testNames = namesList.get(namesList.size() - 1);
        assertThat(testNames.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testNames.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNames.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testNames.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testNames.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testNames.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
    }

    @Test
    public void createNamesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = namesRepository.findAll().size();

        // Create the Names with an existing ID
        names.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restNamesMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(names)))
            .andExpect(status().isBadRequest());

        // Validate the Names in the database
        List<Names> namesList = namesRepository.findAll();
        assertThat(namesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkEntityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = namesRepository.findAll().size();
        // set the field null
        names.setEntityType(null);

        // Create the Names, which fails.

        restNamesMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(names)))
            .andExpect(status().isBadRequest());

        List<Names> namesList = namesRepository.findAll();
        assertThat(namesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = namesRepository.findAll().size();
        // set the field null
        names.setName(null);

        // Create the Names, which fails.

        restNamesMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(names)))
            .andExpect(status().isBadRequest());

        List<Names> namesList = namesRepository.findAll();
        assertThat(namesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFormatIsRequired() throws Exception {
        int databaseSizeBeforeTest = namesRepository.findAll().size();
        // set the field null
        names.setFormat(null);

        // Create the Names, which fails.

        restNamesMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(names)))
            .andExpect(status().isBadRequest());

        List<Names> namesList = namesRepository.findAll();
        assertThat(namesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllNames() throws Exception {
        // Initialize the database
        namesRepository.save(names);

        // Get all the namesList
        restNamesMockMvc.perform(get("/api/names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(names.getId())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX.toString())))
            .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX.toString())));
    }
    
    @Test
    public void getNames() throws Exception {
        // Initialize the database
        namesRepository.save(names);

        // Get the names
        restNamesMockMvc.perform(get("/api/names/{id}", names.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(names.getId()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX.toString()))
            .andExpect(jsonPath("$.suffix").value(DEFAULT_SUFFIX.toString()));
    }

    @Test
    public void getNonExistingNames() throws Exception {
        // Get the names
        restNamesMockMvc.perform(get("/api/names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateNames() throws Exception {
        // Initialize the database
        namesRepository.save(names);

        int databaseSizeBeforeUpdate = namesRepository.findAll().size();

        // Update the names
        Names updatedNames = namesRepository.findById(names.getId()).get();
        updatedNames
            .entityType(UPDATED_ENTITY_TYPE)
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .format(UPDATED_FORMAT)
            .prefix(UPDATED_PREFIX)
            .suffix(UPDATED_SUFFIX);

        restNamesMockMvc.perform(put("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNames)))
            .andExpect(status().isOk());

        // Validate the Names in the database
        List<Names> namesList = namesRepository.findAll();
        assertThat(namesList).hasSize(databaseSizeBeforeUpdate);
        Names testNames = namesList.get(namesList.size() - 1);
        assertThat(testNames.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testNames.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNames.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testNames.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testNames.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testNames.getSuffix()).isEqualTo(UPDATED_SUFFIX);
    }

    @Test
    public void updateNonExistingNames() throws Exception {
        int databaseSizeBeforeUpdate = namesRepository.findAll().size();

        // Create the Names

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNamesMockMvc.perform(put("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(names)))
            .andExpect(status().isBadRequest());

        // Validate the Names in the database
        List<Names> namesList = namesRepository.findAll();
        assertThat(namesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteNames() throws Exception {
        // Initialize the database
        namesRepository.save(names);

        int databaseSizeBeforeDelete = namesRepository.findAll().size();

        // Get the names
        restNamesMockMvc.perform(delete("/api/names/{id}", names.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Names> namesList = namesRepository.findAll();
        assertThat(namesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Names.class);
        Names names1 = new Names();
        names1.setId("id1");
        Names names2 = new Names();
        names2.setId(names1.getId());
        assertThat(names1).isEqualTo(names2);
        names2.setId("id2");
        assertThat(names1).isNotEqualTo(names2);
        names1.setId(null);
        assertThat(names1).isNotEqualTo(names2);
    }
}
