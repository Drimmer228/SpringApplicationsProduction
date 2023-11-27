package com.example.springmodels.controllers;

import javax.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public abstract class CrudController<T, ID> {

    protected final JpaRepository<T, ID> repository;
    private final String entityName;

    protected CrudController(JpaRepository<T, ID> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute(entityName + "s", repository.findAll());
        return entityName + "s/index";
    }

    @GetMapping("/index")
    public String list(Model model) {
        model.addAttribute(entityName + "s", repository.findAll());
        return entityName + "s/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable ID id, Model model) {
        Optional<T> entity = repository.findById(id);
        model.addAttribute(entityName, entity.orElse(null));
        return entityName + "s/show";
    }

    @GetMapping("/new")
    public String newEntity(@ModelAttribute T entity, Model model) {
        model.addAttribute(entityName, entity);
        return entityName + "s/new";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute T entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return entityName + "s/new";
        }
        repository.save(entity);
        return "redirect:/" + entityName + "s/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable ID id, Model model) {
        Optional<T> entity = repository.findById(id);
        model.addAttribute(entityName, entity.orElse(null));
        return entityName + "s/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable ID id, @Valid @ModelAttribute T entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return entityName + "s/edit";
        }
        repository.save(entity);
        return "redirect:/" + entityName + "s/index";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable ID id) {
        repository.deleteById(id);
        return "redirect:/" + entityName + "s/index";
    }
}
