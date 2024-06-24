package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.CategoryDTO;
import ru.tinkoff.rentall.entity.Category;
import ru.tinkoff.rentall.mapper.CategoryMapper;
import ru.tinkoff.rentall.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
        return categoryRepository.save(category);
    }

    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categories) {
            categoryDTOList.add(CategoryMapper.INSTANCE.toCategoryDTO(category));
        }
        return categoryDTOList;
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }
}
