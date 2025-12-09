package assum.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping(value = "/dashboard")
	public String userDashboard() {
		return "Usuário autenticado (USER ou ADMIN) acessa!";
	}
}
