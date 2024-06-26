package ru.rsreu.springhelloworld.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tinkoff.rentall.controller.CategoryController;
import ru.tinkoff.rentall.dto.CategoryDTO;
import ru.tinkoff.rentall.dto.UserReviewDTO;
import ru.tinkoff.rentall.entity.Category;
import ru.tinkoff.rentall.entity.UserReview;
import ru.tinkoff.rentall.mapper.CategoryMapper;
import ru.tinkoff.rentall.mapper.UserReviewMapper;
import ru.tinkoff.rentall.repository.CategoryRepository;
import ru.tinkoff.rentall.service.CategoryService;


import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({CategoryController.class, CategoryService.class})
@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {


    @Autowired // сюда подставляется компонент
    private CategoryController  categoryController;

    @MockBean
    private CategoryRepository categoryRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private CategoryDTO categoryDTO = new CategoryDTO();

    private EasyRandom esr;

    private EasyRandomParameters parameters;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        this.parameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(1, 10);
        this.esr = new EasyRandom(this.parameters);
    }

    @Test
    void createCategory_successful() throws Exception {
        categoryDTO = esr.nextObject(CategoryDTO.class);
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
        String categoryJson = objectMapper.writeValueAsString(categoryDTO);
        Mockito.doReturn(category).when(categoryRepository).save(ArgumentMatchers.any());
        mockMvc.perform(post("/create_category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isCreated());
        verify(categoryRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void getCategories_successful() throws Exception {
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            categories.add(this.esr.nextObject(Category.class));
        }
        Mockito.doReturn(categories).when(categoryRepository).findAll();

        mockMvc.perform(get("/category"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[*].catName",
                        containsInAnyOrder(categories.get(0).getCatName(), categories.get(1).getCatName(),
                                categories.get(2).getCatName())));
        verify(categoryRepository, times(1)).findAll();
    }



}
