package org.launchcode.controllers;

import org.launchcode.CheeseMvcApplication;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by there on 7/21/2017.
 */

@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());

        return "menu/add";

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu menu, Errors errors) {

        if(errors.hasErrors()){
            model.addAttribute("title", "Add Cheese");
            return "menu/add";
        }

        menuDao.save(menu);
        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(@PathVariable int id, Model model) {

        Menu menu = menuDao.findOne(id);
        model.addAttribute("menu", menu);
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItem(@PathVariable int id, Model model) {

        Menu menu = menuDao.findOne(id);
        model.addAttribute("menu", menu);
        AddMenuItemForm form = new AddMenuItemForm(menu, cheeseDao.findAll());
        model.addAttribute("form", form);
        model.addAttribute("title", "Add Item to Menu: " + menu.getName());
        return "menu/add-item";
    }

    @RequestMapping(value = "add-item/{id}", method=RequestMethod.POST)
    public String addItem(@PathVariable int id, Model model,
                          @ModelAttribute @Valid AddMenuItemForm form, Errors errors, int cheeseId) {

        if (errors.hasErrors()) {
           return "menu/add-item/" + menuDao.findOne(id);
        }

        Cheese addedCheese = cheeseDao.findOne(cheeseId);
        Menu editedMenu = menuDao.findOne(id);
        editedMenu.addItem(addedCheese);
        menuDao.save(editedMenu);

        return "redirect:/menu/view/" + id;

    }

}
