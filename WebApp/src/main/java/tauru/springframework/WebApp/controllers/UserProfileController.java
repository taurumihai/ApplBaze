package tauru.springframework.WebApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tauru.springframework.WebApp.entities.Address;
import tauru.springframework.WebApp.entities.AutomotiveRides;
import tauru.springframework.WebApp.entities.Driver;
import tauru.springframework.WebApp.entities.User;
import tauru.springframework.WebApp.repositories.AddressRepository;
import tauru.springframework.WebApp.services.AddressService;
import tauru.springframework.WebApp.services.AutomotiveRidesService;
import tauru.springframework.WebApp.services.DriverService;
import tauru.springframework.WebApp.services.UserService;
import tauru.springframework.WebApp.utilitare.OroErrors;
import tauru.springframework.WebApp.utilitare.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserProfileController {

    private static final Logger LOGGER = Logger.getLogger(UserProfileController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AutomotiveRidesService automotiveRidesService;

    @Autowired
    private DriverService driverService;

    @RequestMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser != null &&  loggedUser.getAddressList() != null && !loggedUser.getAddressList().isEmpty()) {
            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("userAddressesList", loggedUser.getAddressList());
            model.addAttribute("loggedUserEmail", loggedUser.getEmail());
            model.addAttribute("loggedUserFirstName", StringUtils.getUserName(loggedUser.getFirstName()));
            model.addAttribute("loggedUserLastName", StringUtils.getUserName(loggedUser.getLastName()));
        }
        session.setAttribute("loggedUser", loggedUser);
        return "profile";
    }

    @RequestMapping("/personalData")
    public String viewPersonalData(HttpSession session, Model model, String email, String firstName, String lastName) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser != null && !StringUtils.isNullOrEmpty(firstName) && !StringUtils.isNullOrEmpty(lastName)) {
            loggedUser.setFirstName(firstName);
            loggedUser.setLastName(lastName);
            userService.saveUSer(loggedUser);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
        }
        session.setAttribute("loggedUser", loggedUser);
        return "personalData";
    }

    @RequestMapping("/changePassword")
    public String changePasswordView(HttpSession session,  String password, String newPassword, Model model) {

        Boolean success;
        List<OroErrors> errorsList = new ArrayList<>();
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser != null && newPassword != null) {

            if (newPassword.equals(loggedUser.getPassword())) {

                errorsList.add(new OroErrors("Noua parola nu poate coincide cu cea veche"));

            } else {

                success = Boolean.TRUE;
                loggedUser.setPassword(newPassword);
                model.addAttribute("passwordChanged", success);
                userService.saveUSer(loggedUser);
            }
        }
        model.addAttribute("errorList", errorsList);

        return "changePassword";
    }

    @RequestMapping("/userAddresses")
    public String viewAddressForm(String county, String street, String streetNumber, String zipCode,
                                  HttpSession session, Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {

            return "login";
        }

        List<OroErrors> errors = new ArrayList<>();

        if (StringUtils.isNullOrEmpty(county)) {

            String error = "Completati campul Judet";
            errors.add(new OroErrors(error));

        }

        if (StringUtils.isNullOrEmpty(street)) {

            String error = "Completati campul Strada";
            errors.add(new OroErrors(error));

        }

        if (StringUtils.isNullOrEmpty(streetNumber)) {

            String error = "Completati campul Numar Strada";
            errors.add(new OroErrors(error));

        }

        if (StringUtils.isNullOrEmpty(zipCode)) {

            String error = "Completati campul Cod Postal";
            errors.add(new OroErrors(error));

        }

        model.addAttribute("errorList", errors);
        if (errors.isEmpty()) {
            List<Address> localAddresses = new ArrayList<>();
            loggedUser.setHasChangedAddressInfos(Boolean.TRUE);
            Address userAddress = new Address();
            userAddress.setCounty(county);
            userAddress.setStreetNumber(streetNumber);
            userAddress.setStreet(street);
            userAddress.setZipCode(zipCode);
            userAddress.setUser(loggedUser);
            loggedUser.setHasChangedAddressInfos(Boolean.TRUE);
            addressService.saveAddress(userAddress);
            localAddresses.add(userAddress);
            loggedUser.setAddressList(localAddresses);
            userService.saveUSer(loggedUser);

        }

        if (loggedUser.getAddressList() != null && !loggedUser.getAddressList().isEmpty()) {

            model.addAttribute("addressesList", loggedUser.getAddressList());
        }

        if (loggedUser != null) {

            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("changedAddresses", loggedUser.getHasChangedAddressInfos());
        }

        session.setAttribute("loggedUser", loggedUser);

        return "userAddresses";
    }

    @RequestMapping("/endRides")
    public String endrideView(HttpSession session, Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "login";
        }

        List<AutomotiveRides> list = new ArrayList<>();
        List<Long> ridesIds = (List<Long>) session.getAttribute("ridesIds");

        if (ridesIds != null){
            for (AutomotiveRides rides : automotiveRidesService.findAll()) {

                for (Long rideId : ridesIds) {
                    if (rides.getId().equals(rideId)) {
                        list.add(rides);
                    }
                }
            }
        }

        model.addAttribute("allUserRides", list);

        return "endRides";
    }
    @RequestMapping(value = "endRides/{id}")
    public String completeRides(@PathVariable(name = "id") String automotiveRideId, HttpSession session) {

        List<Long> endRideId = (List<Long>) session.getAttribute("ridesCompleted");

        if (endRideId == null) {
            endRideId = new ArrayList<>();
        }

        AutomotiveRides automotiveRides = automotiveRidesService.findAutomotiveRideById(Long.valueOf(automotiveRideId));

        automotiveRides.setRideIsCompleted(Boolean.TRUE);
        automotiveRidesService.saveAutomotiveRides(automotiveRides);
        endRideId.add(automotiveRides.getId());

        session.setAttribute("ridesCompleted", endRideId);

        return "endRides";
    }
}
