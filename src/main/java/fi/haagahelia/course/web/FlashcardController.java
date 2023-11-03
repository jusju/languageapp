package fi.haagahelia.course.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.course.domain.AppUser;
import fi.haagahelia.course.domain.AppUserRepository;
import fi.haagahelia.course.domain.Flashcard;
import fi.haagahelia.course.domain.FlashcardReposirory;




@Controller
public class FlashcardController {
	
	@Autowired 
	private FlashcardReposirory frepository;
	
	@Autowired 
	private AppUserRepository urepository;
	
	
	@RequestMapping("/flashcards")
	public String listFlashcards(Model model) {
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
	
	@RequestMapping("/registration")
	public String register(Model model) {
		AppUser user = new AppUser();
		model.addAttribute("user", user);
		return "registration";
	}
	
	@PostMapping("/saveuser")
	public String saveUser(AppUser user) {
		urepository.save(user);
		return "redirect:/login";
	}
	
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}    

}
