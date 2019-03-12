
package controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdminService;
import services.MemberService;
import domain.Actor;
import forms.ActorForm;
import forms.RegisterForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private MemberService	memberService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private AdminService	adminService;


	//REGISTER
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;
		final RegisterForm registerForm = new RegisterForm();
		res = this.createEditModelAndView(registerForm);
		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("regForm") @Valid final RegisterForm registerForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(registerForm);
		else
			try {
				this.actorService.register(registerForm);
				res = new ModelAndView("redirect:../security/login.do");
			} catch (final Exception e) {
				res = this.createEditModelAndView(registerForm, "actor.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView userProfile(@RequestParam(required = false) final Integer id) {
		ModelAndView res;
		res = new ModelAndView("actor/profile");
		Actor actor;
		if (id == null || id == 0) {
			actor = this.actorService.findPrincipal();
			res.addObject("owner", true);
		} else {
			actor = this.actorService.findOne(id);
			Assert.notNull(actor);
		}

		res.addObject("actor", actor);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;
		final ActorForm actorForm = this.actorService.formatForm(this.actorService.findPrincipal());
		res = this.createActorEditModelAndView(actorForm);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView commit(@ModelAttribute("actorForm") @Valid final ActorForm actorForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createActorEditModelAndView(actorForm);
		else
			try {
				this.actorService.save(this.actorService.parseForm(actorForm));
				res = new ModelAndView("redirect:profile.do");
			} catch (final Exception e) {
				res = this.createActorEditModelAndView(actorForm, "actor.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/preferences", method = RequestMethod.POST)
	public ModelAndView setPreferences() {
		final ModelAndView res = null;
		return res;
	}

	@RequestMapping(value = "/social", method = RequestMethod.POST)
	public ModelAndView setSocialProfile() {
		final ModelAndView res = null;
		return res;
	}

	//AUX
	protected ModelAndView createEditModelAndView(final RegisterForm registerForm) {
		return this.createEditModelAndView(registerForm, null);
	}

	protected ModelAndView createEditModelAndView(final RegisterForm registerForm, final String messageCode) {
		ModelAndView result;
		final ArrayList<String> roles = new ArrayList<>();
		roles.add("MEMBER");
		roles.add("ADMIN");

		result = new ModelAndView("security/register");
		result.addObject("regForm", registerForm);
		result.addObject("roles", roles);
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView createActorEditModelAndView(final ActorForm actorForm) {
		return this.createActorEditModelAndView(actorForm, null);
	}

	protected ModelAndView createActorEditModelAndView(final ActorForm actorForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", messageCode);
		return result;
	}
}
