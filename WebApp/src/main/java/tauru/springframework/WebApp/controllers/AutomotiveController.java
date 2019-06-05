package tauru.springframework.WebApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tauru.springframework.WebApp.entities.Automotive;
import tauru.springframework.WebApp.entities.AutomotiveRides;
import tauru.springframework.WebApp.entities.Driver;
import tauru.springframework.WebApp.entities.User;
import tauru.springframework.WebApp.enums.AutomotiveTypeEnum;
import tauru.springframework.WebApp.services.AutomotiveRidesService;
import tauru.springframework.WebApp.services.AutomotiveService;
import tauru.springframework.WebApp.services.DriverService;
import tauru.springframework.WebApp.services.UserService;
import tauru.springframework.WebApp.utilitare.OroErrors;
import tauru.springframework.WebApp.utilitare.StringUtils;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Transactional
public class AutomotiveController {


    @Autowired
    private UserService userService;

    @Autowired
    private AutomotiveRidesService automotiveRidesService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private AutomotiveService automotiveService;


    @RequestMapping("/automotive")
    public String viewAutomotive(Model model, HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser != null && !loggedUser.getRolesList().isEmpty() && loggedUser.getRolesList().size() > 1)
        {
            model.addAttribute("adminLoggedIn", true);
        }

        model.addAttribute("allAutomotives", automotiveService.getAllAutomotives());

        return "automotive";
    }

    @RequestMapping(value = "/automotivesOffers/{id}")
    public String viewOffersOfAutomotives(@PathVariable(name = "id") String automotiveId, HttpSession session, Model model) {

        Automotive currentAutomotive = automotiveService.findAutomotiveById(Long.valueOf(automotiveId));
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser != null && loggedUser.getDriver() != null)
        {
            model.addAttribute("userIsRegistredAsDriver", Boolean.TRUE);
        }

        // iau toate cursele si in functie de tipul masinii stabilesc care cum preia
        List<AutomotiveRides> automotiveRidesList = automotiveRidesService.findAll();
        List<AutomotiveRides> rideForCurrentAutomotive = new ArrayList<>();

        if (currentAutomotive.getAutomotiveType().equals(AutomotiveTypeEnum.BERLIN))
        {
            for (AutomotiveRides rides : automotiveRidesList)
            {
                if (rides != null && rides.getDuration() != null && rides.getDuration() < 120)
                {
                    rideForCurrentAutomotive.add(rides);
                }
            }

            if (!rideForCurrentAutomotive.isEmpty())
            {
                currentAutomotive.setAutomotiveRidesList(rideForCurrentAutomotive);
                automotiveService.saveAutomotive(currentAutomotive);
            }
        }

        if (currentAutomotive.getAutomotiveType().equals(AutomotiveTypeEnum.BREAK))
        {
            for (AutomotiveRides rides : automotiveRidesList)
            {
                if (rides != null && rides.getDuration() != null && rides.getDuration() > 120 && rides.getDuration() < 240)
                {
                    rideForCurrentAutomotive.add(rides);
                }
            }

            if (!rideForCurrentAutomotive.isEmpty())
            {
                currentAutomotive.setAutomotiveRidesList(rideForCurrentAutomotive);
                automotiveService.saveAutomotive(currentAutomotive);
            }
        }

        if (currentAutomotive.getAutomotiveType().equals(AutomotiveTypeEnum.COUPE))
        {
            for (AutomotiveRides rides : automotiveRidesList)
            {
                if (rides != null && rides.getDuration() != null && rides.getDuration() < 60)
                {
                    rideForCurrentAutomotive.add(rides);
                }
            }

            if (!rideForCurrentAutomotive.isEmpty())
            {
                currentAutomotive.setAutomotiveRidesList(rideForCurrentAutomotive);
                automotiveService.saveAutomotive(currentAutomotive);
            }
        }

        model.addAttribute("automotiveRides", rideForCurrentAutomotive);

        return "automotivesOffers";
    }

    @RequestMapping(value = "automotivesOffers/takeRide/{id}")
    public String takeRideView(@PathVariable(name = "id") String automotiveRideId, HttpSession session, Model model)
    {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser.getUsername().equals("admin"))
        {
            model.addAttribute("adminIsLogged", Boolean.TRUE);
        }

        AutomotiveRides automotiveRides = automotiveRidesService.findAutomotiveRideById(Long.valueOf(automotiveRideId));

        if (loggedUser != null)
        {
            if (automotiveRides != null && automotiveRides.getRideIsTaken() != null && !automotiveRides.getRideIsTaken())
            {
                automotiveRides.setUser(loggedUser);
                automotiveRides.setRideIsTaken(Boolean.TRUE);
                automotiveRidesService.saveAutomotiveRides(automotiveRides);
                model.addAttribute("enjoyRide", "Penbtru ridicarea autovehiculului va rugam sa va prezentati la sediu cu cel putin 30 de minute inainte de inceperea cursei. Va multumim !");
            }

            if (automotiveRides != null && !loggedUser.equals(automotiveRides.getUser()))
            {
                OroErrors error = new OroErrors("Cursa a fost acceptata de alt utilizator ! Va rugam sa selectati alta cursa.");
                model.addAttribute("rideTaken", error.getError());
            }
        }
        else
        {
            OroErrors error = new OroErrors("Va rugam sa va logati sau daca nu aveti cont, sa va inregistrati in sistem. Va multumim !");
            model.addAttribute("notLoggedUser", error.getError());
        }

        return "takeRide";
    }

    @RequestMapping("/drivers")
    public String viewDriversView(String firstName,
                                  String lastName,
                                  String age,
                                  String experienceAge, Model model, HttpSession session)
    {

        User loggedUser = (User) session.getAttribute("loggedUser");
        Boolean driverIsRegistered = Boolean.FALSE;

        if (loggedUser != null && loggedUser.getDriver() != null ) {

            driverIsRegistered = Boolean.TRUE;

        }

        List<OroErrors> errorsList = new ArrayList<>();

        Boolean success = Boolean.FALSE;

        if (loggedUser != null)
        {
            model.addAttribute("loggedUser", loggedUser);
        }

        if (StringUtils.isNullOrEmpty(firstName))
        {
            errorsList.add(new OroErrors("Completati numele dvs."));
        }
        if (StringUtils.isNullOrEmpty(lastName))
        {
            errorsList.add(new OroErrors("Completati prenumele"));
        }
        if (StringUtils.isNullOrEmpty(age))
        {
            errorsList.add(new OroErrors("Completati varsta dvs"));
        }

        if (StringUtils.isNullOrEmpty(age) || Integer.valueOf(age) < 18)
        {
            errorsList.add(new OroErrors("Varsta minima este de 18 ani"));
        }
        if (StringUtils.isNullOrEmpty(experienceAge))
        {
            errorsList.add(new OroErrors("Completati numarul anilor de experienta"));
        }

        model.addAttribute("errorList", errorsList);

        if (errorsList.isEmpty())
        {
            loggedUser.setFirstName(firstName);
            loggedUser.setLastName(lastName);

            Driver newDrivre = new Driver();

            newDrivre.setAge(Integer.valueOf(age));
            newDrivre.setExperienceYears(Integer.valueOf(experienceAge));
            newDrivre.setIsRegistered(Boolean.TRUE);

            loggedUser.setDriver(newDrivre);
            driverIsRegistered =  Boolean.TRUE;

            driverService.saveDriver(newDrivre);
            userService.saveUSer(loggedUser);
            success = Boolean.TRUE;
        }

        model.addAttribute("driverIsRegistered", driverIsRegistered);
        model.addAttribute("success", success);

        return "drivers";
    }

    @RequestMapping("/addRides")
    public String addRidesView(Double userIsPayd, String description, String duration, String rideStartAt) throws ParseException {

        if (StringUtils.isNullOrEmpty(description) && StringUtils.isNullOrEmpty(duration) && StringUtils.isNullOrEmpty(rideStartAt) && userIsPayd == null)
        {
            return "addRides";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm dd/mm/yyyy");
        Date dateWhenRideStart = simpleDateFormat.parse(rideStartAt);

        AutomotiveRides ride = new AutomotiveRides();
        ride.setRideIsTaken(Boolean.FALSE);
        ride.setDescription(description);
        ride.setDuration(Integer.parseInt(duration));
        ride.setRideStartAt(dateWhenRideStart);
        ride.setAmountOfValueUserIsPayed(userIsPayd);
        automotiveRidesService.saveAutomotiveRides(ride);


        return "addRides";
    }
}
