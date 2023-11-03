package fi.haagahelia.course.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.haagahelia.course.domain.AppUser;
import fi.haagahelia.course.domain.AppUserRepository;
import fi.haagahelia.course.domain.Flashcard;
import fi.haagahelia.course.domain.FlashcardReposirory;
import fi.haagahelia.course.domain.SignupForm;
import jakarta.validation.Valid;




@Controller
public class FlashcardController {
	
	@Autowired 
	private FlashcardReposirory frepository;
	
	@Autowired 
	private AppUserRepository urepository;
	
	
	@RequestMapping("/flashcards")
	public String listFlashcards(Model model) {
		System.out.println("JUKKA AT FLASH LIST");
		model.addAttribute("flashcards", frepository.findAll());
		
		return "flascardlist";
	}
	
	@RequestMapping("/showback/{id}")
	public String showBack(@PathVariable("id") Long id, Model model) {
		Flashcard flashcard = frepository.findById(id).get();
		flashcard.setShowBack(true);
		frepository.save(flashcard);
		return "redirect:/flashcards";
		
	}
	
	
	@RequestMapping("/showfront/{id}")
	public String showFront(@PathVariable("id") Long id, Model model) {
		Flashcard flashcard = frepository.findById(id).get();
		flashcard.setShowBack(false);
		frepository.save(flashcard);
		return "redirect:/flashcards";
		
	}
	
    @RequestMapping(value = "signup")
    public String addStudent(Model model){
    	System.out.println("JUKKA: we are at signup");
    	model.addAttribute("signupform", new SignupForm());
        return "signup";
    }	
	/*
	@RequestMapping("/registration")
	public String register(Model model) {
		AppUser user = new AppUser();
		model.addAttribute("user", user);
		return "signup";
	}
	*/
	@PostMapping("/storeuser")
	public String saveUser(AppUser user) {
		urepository.save(user);
		return "redirect:/login";
	}
	
	   @RequestMapping(value = "saveuser", method = RequestMethod.POST)
	    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
	    	if (!bindingResult.hasErrors()) { // validation errors
	    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check password match		
		    		String pwd = signupForm.getPassword();
			    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			    	String hashPwd = bc.encode(pwd);
		
			    	AppUser newUser = new AppUser();
			    	newUser.setPasswordHash(hashPwd);
			    	newUser.setUsername(signupForm.getUsername());
			    	newUser.setRole("USER");
			    	if (urepository.findByUsername(signupForm.getUsername()) == null) { // Check if user exists
			    		urepository.save(newUser);
			    	}
			    	else {
		    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
		    			return "signup";		    		
			    	}
	    		}
	    		else {
	    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
	    			return "signup";
	    		}
	    	}
	    	else {
	    		return "signup";
	    	}
	    	return "redirect:/login";    	
	    }    
	
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}    

}
