package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.dto.CategoryDTO;
import ru.tinkoff.rentall.entity.Category;
import ru.tinkoff.rentall.mapper.CategoryMapper;
import ru.tinkoff.rentall.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return categories
                .stream()
                .map(CategoryMapper.INSTANCE::toCategoryDTO)
                .collect(Collectors.toList());
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }
}
