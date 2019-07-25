package com.accenture.Farm.controller;

import java.util.ArrayList;

import java.util.List;


import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.accenture.Farm.entity.Authority;
import com.accenture.Farm.entity.User;
import com.accenture.Farm.model.Chickens;
import com.accenture.Farm.model.Eggs;
import com.accenture.Farm.model.Farm;
import com.accenture.Farm.repository.daoChickens;
import com.accenture.Farm.repository.daoEggs;
import com.accenture.Farm.repository.daoFarm;
import com.accenture.Farm.repository.userRepository;

@Controller
public class FarmController {


	@Autowired
	private daoFarm daofarm;
	
	@Autowired 
	private daoChickens daochickens;
	
	@Autowired
	private daoEggs daoeggs;
	
	@Autowired
	private userRepository userRepo;
    
	@GetMapping(path="/login")
	public String login() {
		return "login";
	}
	
	
    //METODO GET PARA CREAR GRANJAS
	@GetMapping(path="/addfarm")
    public String addfarm(Model modelo, Farm farm) {
    	modelo.addAttribute("farm", new Farm());
    	modelo.addAttribute("farms", daofarm.findAll());
    	return "add-farm";
    }
    
	//METODO PARA TRAER GRANJAS
    @GetMapping({"/farms", "/"})
    public String index(Model model) {
    model.addAttribute("farms", daofarm.findAll());
  	model.addAttribute("farm", daofarm.count());
    	return "index";
    }
	
    // METODO PARA CREAR GRANJAS
    @PostMapping(path="/addfarm")
    public String addFarm(@Valid Farm farm, Model model) {    
        daofarm.save(farm);
        model.addAttribute("farms", daofarm.findAll());
//        model.addAttribute("farm", new Farm());
        return "redirect:/farms";
   }
    
    @GetMapping(path="/adduser")
    public String UserForm(User user, Authority authority, Model model) {
    	model.addAttribute("user", new User());
    	model.addAttribute("authority", new Authority());
    	return "add-user";
    }
    
    
    @PostMapping(path="/adduser")
    public String addUser(User user, Model model) {
    	System.out.println( "cantidad de users: " +userRepo.count());
    	System.out.println( user.getAuthority());
    	Authority authority1 = new Authority(); 
    	List<Authority> autho = new ArrayList <Authority>();
    	authority1.setAuthority("ROLE_USER");
    	autho.add(authority1);
//    	authority1.setId(userRepo.count()-1);
    	user.setAuthority(autho);
    	String encodedpassword = new BCryptPasswordEncoder().encode(user.getPassword());
    	user.setPassword(encodedpassword);
    	user.setEnabled(true);
    	userRepo.save(user);
    	System.out.println( "cantidad de users: " +userRepo.count());
    	model.addAttribute("users", userRepo.findAll());
    	return "login";
    }
    
  //METODO PARA TRAER GRANJAS POR ID 
    @GetMapping(path="/addchicken/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model, Chickens chicken) {
        Farm farm = daofarm.findById(id).get();
        List<Chickens> chickensfound = daochickens.findByFarm(farm);
        List<Eggs> eggsfound=daoeggs.findByChickens(chicken);
        model.addAttribute("chic", new Chickens());
        model.addAttribute("farm", farm);
        model.addAttribute("farms", daofarm.findAll());
        model.addAttribute("farmid", id);
        model.addAttribute("chickens", chickensfound);
        model.addAttribute("chickenlist", daochickens.findByFarm(farm).isEmpty());
        model.addAttribute("eggsnumber", eggsfound.size());
        return "add-chicken";
    }
    
    // METODO PARA CREAR GALLINAS POR GRANJA ID    
    @PostMapping(path="/addchicken/{id}")
    @Secured("ROLE_ADMIN")
    public String addChicken(@PathVariable("id") Long id, Chickens chic, Model model) {
    	Farm farm1 = daofarm.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    	chic.setFarm(farm1);
    	daochickens.save(chic);
    	model.addAttribute("chickens", daochickens.findAll());
    	return "redirect:/addchicken/" +id;
    }
    
    //METODO PARA EDITAR CHICKENS POR ID
    @GetMapping(path="/chicken/update/{id}")
    @Secured("ROLE_ADMIN")
    public String showUpdateChic(@PathVariable("id") long id, Model model) {
        Chickens chick = daochickens.findById(id).get();
        List<Chickens> chickensfound = daochickens.findByFarm(chick.getFarm());
        List<Eggs> eggsfound=daoeggs.findByChickens(chick);
        model.addAttribute("farm", chick.getFarm());
        model.addAttribute("farmid", chick.getFarm().getId());
        model.addAttribute("chic", chick);
        model.addAttribute("chickens", chickensfound);
        model.addAttribute("eggsnumber", eggsfound.size());
        model.addAttribute("chickenlist", daochickens.findAll());
        return "add-chicken";
    }
    
    //METODO PARA EDITAR CHICKENS POR ID
    @GetMapping(path="/chicken/changefarm/{id}")
    @Secured("ROLE_ADMIN")
    public String changeFarm(@PathVariable("id") long id, Model model, Farm farm) {
    	
        Chickens chick = daochickens.findById(id).get(); 
//        chick.setFarm(farm);
//        model.addAttribute("farm", chick.getFarm());
        model.addAttribute("farmid", chick.getFarm().getId());
        model.addAttribute("chickenid", chick.getId());
        model.addAttribute("chic", chick);
        model.addAttribute("farms", daofarm.findAll());
        model.addAttribute("chickenlist", daochickens.findAll());
        return "change-farm";
    }
    
  //METODO PARA EDITAR CHICKENS POR ID
    @GetMapping(path="/chicken/{idc}/farm/{id}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_ACTUATOR"})
    public String changeFarmChic(@PathVariable("id") long id, @PathVariable("idc") long idc, Model model) {
    	
    	Farm farm1 = daofarm.findById(id).get();
    	
        Chickens chick = daochickens.findById(idc).get();
        chick.setFarm(farm1);
        daochickens.save(chick);
        model.addAttribute("chic", chick);
        model.addAttribute("chickenid", chick.getId());
        model.addAttribute("farms", daofarm.findAll());
       
        return "index";
    }
    
    //METODO PARA TRAER HUEVOS POR CHICKENS ID 
    @GetMapping(path="/chicken/edit/{id}")
    public String showUpdateChicken(@PathVariable("id") long id, Model model, Eggs egg) {
        Chickens chick = daochickens.findById(id)
       .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<Eggs> eggsfound = daoeggs.findByChickens(chick);		
        model.addAttribute("chickenid", chick.getId());
        model.addAttribute("chicken", chick);
        model.addAttribute("eggs", eggsfound);
        model.addAttribute("egg", new Eggs());
        model.addAttribute("eggslist", daoeggs.findByChickens(chick).isEmpty());
        model.addAttribute("farmid", chick.getFarm().getId());
        return "add-eggs";
    }
    
    //METODO PARA CREAR HUEVOS POR CHICKEN ID
    @PostMapping(path="/chicken/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String addEgg(@PathVariable("id") long id, Model model, Eggs egg) {
    	Chickens chik = daochickens.findById(id).orElse(null);
    	egg.setChickens(chik);
    	daoeggs.save(egg);
    	model.addAttribute("egg", new Eggs());
    	model.addAttribute("eggs", daoeggs.findAll());
	return "redirect:/chicken/edit/{id}";	
    }
   
    //METODO PARA EDITAR EGGS POR ID 
    @GetMapping(path="/egg/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String showUpdateEgg(@PathVariable("id") long id, Model model) {
        Eggs egg = daoeggs.findById(id).get();
        List<Eggs> eggsfound = daoeggs.findByChickens(egg.getChickens());		
        model.addAttribute("chickenid", egg.getChickens().getId());
        model.addAttribute("chicken", egg.getChickens());
        model.addAttribute("eggs", eggsfound);
        model.addAttribute("egg", egg);
        model.addAttribute("farmid", egg.getChickens().getFarm().getId());
        return "add-eggs";
    }
    
    //METODO PARA CONVERTIR EGGS EN CHICKENS 
    @GetMapping(path="/egg/born/{id}")
    public String showBornEgg(@PathVariable("id") long id, Model model) {
        Eggs egg = daoeggs.findById(id).get();
        Chickens chick = new Chickens();
        chick.setNombre(egg.getNombre());
        chick.setFarm(egg.getChickens().getFarm());
        daochickens.save(chick);
        daoeggs.delete(egg);
        List<Eggs> eggsfound = daoeggs.findByChickens(egg.getChickens());		
        model.addAttribute("chickenid", egg.getChickens().getId());
        model.addAttribute("chicken", egg.getChickens());
        model.addAttribute("eggs", eggsfound);
        model.addAttribute("egg", egg);
        model.addAttribute("farmid", egg.getChickens().getFarm().getId());
        return "redirect:/addchicken/" +chick.getFarm().getId();
    }
    
    
    //METODO PARA ELIMINAR GRANJAS POR ID
    @GetMapping(path="/delete/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteFarm(@PathVariable("id") long id, Model model) {
        Farm farm = daofarm.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        daofarm.delete(farm);
        model.addAttribute("farms", daofarm.findAll());
        return "redirect:/farms";
    }
    
    //METODO PARA ELIMINAR GALLINAS POR ID
    @GetMapping(path="/chicken/delete/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteChicken(@PathVariable("id") long id, Model model) {
        Chickens chicken = daochickens.findById(id).get();
        daochickens.delete(chicken);
        
    	model.addAttribute("chickens", daochickens.findAll());
//    	model.addAttribute("farm", farm);
    	return "redirect:/addchicken/" +chicken.getFarm().getId();
    }
    
  //METODO PARA ELIMINAR HUEVOS POR ID
    @GetMapping(path="/egg/delete/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteEgg(@PathVariable("id") long id, Model model) {
        Eggs egg = daoeggs.findById(id).get();
        daoeggs.delete(egg);
    	model.addAttribute("chickens", daochickens.findAll());
    	return "redirect:/chicken/edit/" +egg.getChickens().getId();
    }
    
}	