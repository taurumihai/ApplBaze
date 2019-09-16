package tauru.springframework.WebApp.controllers;

import org.aspectj.weaver.ast.Or;
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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class HomeController {

    private static final Logger LOGGER = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AutomotiveRidesService automotiveRidesService;

    @Autowired
    private RolesService rolesService;

    @RequestMapping("/")
    public String viewHome(){

        return "index";
    }

    @RequestMapping("/login")
    public String view(String username, String password, Model model, HttpServletRequest request) {

        List<OroErrors> oroErrorsList = new ArrayList<>();
        List<AutomotiveRides> allRidesOfLoggedUser = new ArrayList<>();
        List<Roles> allRolesList = rolesService.findAllRoles();
        HttpSession session = request.getSession(true);

        // To update completed rides in profile page for each user in rela time
        List<Long> rideCompletedList = (List<Long>) session.getAttribute("ridesCompleted");
        if (rideCompletedList == null) {
            rideCompletedList = new ArrayList<>();
        }

        if (StringUtils.isNullOrEmpty(username) && StringUtils.isNullOrEmpty(password)) {
            return "login";
        }

        User loggedUser = userService.findUSerByUSernameAndPassword(username, password);
        try {

            if (userExist(loggedUser)) {

                session.setAttribute("loggedUser", loggedUser);

            } else {

                oroErrorsList.add(new OroErrors("Username-ul sau parola este incorecta."));
                model.addAttribute("logginError", "Username-ul sau parola este incorecta.");
            }

        } catch (OroErrors oroErrors) {
            oroErrors.printStackTrace();
        }

        if (loggedUser != null && loggedUser.getDriver() != null)
        {
            model.addAttribute("userIsLoggedAsDriver", loggedUser.getDriver() != null ? Boolean.TRUE : Boolean.FALSE);
        }

        if (loggedUser != null && "admin".equals(loggedUser.getUsername()) && "admin".equals(loggedUser.getPassword()))
        {
            if (loggedUser.getRolesList().isEmpty())
            {
                loggedUser.setRolesList(allRolesList);
                userService.saveUSer(loggedUser);
                model.addAttribute("adminUserLogged", Boolean.TRUE);
            }

            return "welcome";
        }

        Double totalAmmountPaid = 0.0;

        for (AutomotiveRides rides : automotiveRidesService.findAll())
        {
            for (Long id : rideCompletedList) {
                if (rides.getId().equals(id) && rides.getRideIsCompleted())
                {

                    allRidesOfLoggedUser.add(rides);
                    totalAmmountPaid += rides.getAmountOfValueUserIsPayed();
                }

                if (!allRidesOfLoggedUser.isEmpty())
                {
                    model.addAttribute("allUserRides", allRidesOfLoggedUser);
                }
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
        session.setAttribute("loggedUser", loggedUser);
        return "login";
    }

    @RequestMapping("/register")
    public String viewRegister(String username, String password, String email, Model model, HttpSession session){

        if (username == null || password == null || email == null) {

            return "register";
        }

        List<User> userList = userService.findAllUsers();
        List<OroErrors> errorList = new ArrayList<>();
        List<Roles> allRolesList = rolesService.findAllRoles();
        User userByUsername = userService.findUserByUserName(username);
        User userByEmail = userService.findUserByEmail(email);

        if (allRolesList != null && !allRolesList.isEmpty())
        {
            allRolesList.remove(1);
        }

        if (userByUsername != null && userList.contains(userByUsername)){
            OroErrors error = new OroErrors("Username existent! Va rugam schimbati username-ul");
            errorList.add(error);
        }

        if (userByEmail != null && userList.contains(userByEmail)) {
            OroErrors oroError = new OroErrors("Email existent ! Va rugam scimbati email-ul.");
            errorList.add(oroError);
        }

        if (StringUtils.isNullOrEmpty(password)) {

            OroErrors error = new OroErrors("Completati parola");
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

    private Boolean userExist(User user) throws OroErrors {

        try {
            return userService.findAllUsers().contains(user);
        } catch (Exception ex) {

            LOGGER.info("Error finding user " + user);
            throw new OroErrors("User not found");
        }

    }
}
