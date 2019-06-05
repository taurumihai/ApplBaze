package tauru.springframework.WebApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tauru.springframework.WebApp.entities.Address;
import tauru.springframework.WebApp.entities.User;
import tauru.springframework.WebApp.repositories.AddressRepository;
import tauru.springframework.WebApp.services.AddressService;
import tauru.springframework.WebApp.services.UserService;
import tauru.springframework.WebApp.utilitare.OroErrors;
import tauru.springframework.WebApp.utilitare.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @RequestMapping("/profile")
    public String viewProfile() {

        return "profile";
    }

    @RequestMapping("/changePassword")
    public String changePasswordView(String username, String password, String newPassword, Model model) {

        User currentUser = null;
        Boolean success;
        List<OroErrors> errorsList = new ArrayList<>();

        if (username != null && password != null) {

            currentUser  = userService.findUSerByUSernameAndPassword(username, password);
        }

        if (currentUser != null && newPassword != null) {

            if (newPassword.equals(password)) {

                errorsList.add(new OroErrors("Noua parola nu poate coincide cu cea veche"));

            } else {

                success = Boolean.TRUE;
                currentUser.setPassword(newPassword);
                model.addAttribute("passwordChanged", success);
                userService.saveUSer(currentUser);
            }
        }
        model.addAttribute("errorList", errorsList);

        return "changePassword";
    }

    @RequestMapping("/userAddresses")
    public String viewAddressForm(String county, String street, String streetNumber, String zipCode,
                                  HttpSession session, Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");

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
        if (errors.isEmpty() && !loggedUser.getHasChangedAddressInfos()) {

            if (loggedUser.getAddressList() != null && loggedUser.getAddressList().isEmpty()) {

                loggedUser.setHasChangedAddressInfos(Boolean.TRUE);
                Address userAddress = new Address();

                userAddress.setCounty(county);
                userAddress.setStreetNumber(streetNumber);
                userAddress.setStreet(street);
                userAddress.setZipCode(zipCode);
                userAddress.setUser(loggedUser);
                loggedUser.setHasChangedAddressInfos(Boolean.TRUE);

                loggedUser.getAddressList().add(userAddress);
                addressService.saveAddress(userAddress);
                userService.saveUSer(loggedUser);
            }

        } else if (errors.isEmpty() && loggedUser.getHasChangedAddressInfos()) {

            if (!StringUtils.isNullOrEmpty(county)  && !StringUtils.isNullOrEmpty(street) && !StringUtils.isNullOrEmpty(streetNumber)
                 && !StringUtils.isNullOrEmpty(zipCode))
            {
                Address userAddress = new Address();

                userAddress.setCounty(county);
                userAddress.setStreetNumber(streetNumber);
                userAddress.setStreet(street);
                userAddress.setZipCode(zipCode);
                userAddress.setUser(loggedUser);
                loggedUser.getAddressList().add(userAddress);
                loggedUser.setHasChangedAddressInfos(Boolean.TRUE);

                addressService.saveAddress(userAddress);
                userService.saveUSer(loggedUser);
            }
        }

        if (loggedUser.getAddressList() != null && !loggedUser.getAddressList().isEmpty()) {

            model.addAttribute("addressesList", loggedUser.getAddressList());
        }

        if (loggedUser != null) {

            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("changedAddresses", loggedUser.getHasChangedAddressInfos());
        }

        return "userAddresses";
    }
}
