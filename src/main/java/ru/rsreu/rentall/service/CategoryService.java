package ru.rsreu.rentall.service;

import org.springframework.stereotype.Service;
import ru.rsreu.rentall.dto.AdvertisementDTO;
import ru.rsreu.rentall.dto.CategoryDTO;
import ru.rsreu.rentall.entity.Advertisement;
import ru.rsreu.rentall.entity.Category;
import ru.rsreu.rentall.mapper.AdvertisementMapper;
import ru.rsreu.rentall.mapper.CategoryMapper;
import ru.rsreu.rentall.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
}
