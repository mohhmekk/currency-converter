package org.sample.currency.app.controller.user;


import org.sample.currency.app.controller.user.dto.NewUserDTO;
import org.sample.currency.app.controller.user.dto.UserInfoDTO;
import org.sample.currency.app.model.User;
import org.sample.currency.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * REST service for users.
 *
 * Created by Mohamed Mekkawy.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public UserInfoDTO getUserInfo(Principal principal) {

        User user = userService.findUserByUsername(principal.getName());

        return user != null ? new UserInfoDTO(user.getUserName()) : null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody NewUserDTO user) {
        userService.createUser(user.getUsername(), user.getEmail(), user.getPlainTextPassword());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        logger.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
