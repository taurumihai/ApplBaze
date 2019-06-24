package tauru.springframework.WebApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tauru.springframework.WebApp.entities.AutomotiveRides;
import tauru.springframework.WebApp.entities.Roles;
import tauru.springframework.WebApp.entities.User;
import tauru.springframework.WebApp.services.AutomotiveRidesService;
import tauru.springframework.WebApp.services.RolesService;
import tauru.springframework.WebApp.services.UserService;
import tauru.springframework.WebApp.utilitare.OroErrors;
import tauru.springframework.WebApp.utilitare.StringUtils;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private AutomotiveRidesService automotiveRidesService;

    @Autowired
    private RolesService rolesService;

    @RequestMapping("/")
    public String viewHome(Model model){


        return "index";
    }

    @RequestMapping("/login")
    public String view (String username, String password, Model model, HttpSession session) {

        List<OroErrors> oroErrorsList = new ArrayList<>();
        List<AutomotiveRides> allRidesFromDB = automotiveRidesService.findAll();
        List<AutomotiveRides> allRidesOfLoggedUser = new ArrayList<>();
        List<Roles> allRolesList = rolesService.findAllRoles();

        if (StringUtils.isNullOrEmpty(username) && StringUtils.isNullOrEmpty(password)) {

            return "login";
        }

        User loggedUser = userService.findUSerByUSernameAndPassword(username, password);

        if ("admin".equals(loggedUser.getUsername()) && "admin".equals(loggedUser.getPassword()))
        {
            if (loggedUser.getRolesList().isEmpty())
            {
                loggedUser.setRolesList(allRolesList);
                userService.saveUSer(loggedUser);
                model.addAttribute("adminUserLogged", Boolean.TRUE);
                session.setAttribute("loggedUser", loggedUser);
            }

            return "welcome";
        }

        Double totalAmmountPaid = 0.0;

        for (AutomotiveRides rides : allRidesFromDB)
        {

            if (rides.getUser() != null && rides.getUser().equals(loggedUser))
            {
                allRidesOfLoggedUser.add(rides);
                totalAmmountPaid += rides.getAmountOfValueUserIsPayed();
            }

            if (!allRidesOfLoggedUser.isEmpty())
            {
                model.addAttribute("allUserRides", allRidesOfLoggedUser);
            }

            model.addAttribute("totalPaid", totalAmmountPaid);
        }

        if (loggedUser != null)
        {

            if (allRolesList != null && !allRolesList.isEmpty())
            {
                allRolesList.remove(1);
            }

            loggedUser.setUserIsLoggedIn(Boolean.TRUE);
            loggedUser.setRolesList(allRolesList);
            userService.saveUSer(loggedUser);
            session.setAttribute("loggedUser", loggedUser);

            return "welcome";

        }
        else
        {

            oroErrorsList.add(new OroErrors("Nu exista contul"));
            model.addAttribute("errorList", oroErrorsList);
            return "login";

        }
    }

    @RequestMapping("/register")
    public String viewRegister(String username, String password, String email, Model model, HttpSession session){

        if (username == null || password == null || email == null) {

            return "register";
        }

        List<User> userList = userService.findAllUsers();
        List<String> errorList = new ArrayList<String>();
        List<Roles> allRolesList = rolesService.findAllRoles();

        if (allRolesList != null && !allRolesList.isEmpty())
        {
            allRolesList.remove(1);
        }


        for (User user : userList) {

            if (user.getUsername().equals(username)) {

                String error = "Please chose another username";
                errorList.add(error);

            }

            if (user.getEmail().equals(email)) {

                String error = "Please chose another email";
                errorList.add(error);

            }
        }

        if (StringUtils.isNullOrEmpty(password)) {

            String error = "Completati parola";
            errorList.add(error);
        }

        model.addAttribute("errorList", errorList);

        if (errorList.isEmpty()) {

            User user = new User(username, password, email);
            user.setUserIsLoggedIn(Boolean.TRUE);
            user.setRolesList(allRolesList);
            userService.saveUSer(user);
            session.setAttribute("loggedUser", user);
            return "welcome";
        }

        return "register";
    }
}
