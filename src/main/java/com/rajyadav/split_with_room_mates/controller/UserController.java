package com.rajyadav.split_with_room_mates.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rajyadav.split_with_room_mates.dao.UserDao;
import com.rajyadav.split_with_room_mates.dto.Items;
import com.rajyadav.split_with_room_mates.dto.LoginRequestDTO;
import com.rajyadav.split_with_room_mates.dto.User;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(
	    origins = {
	        "http://localhost:5173",
	        "https://bill-buddy-react-fronted-project.vercel.app"
	    },
	    allowCredentials = "true"
	)
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

    private UserDao userDao;
    private HttpSession httpSession;

    @PostMapping(value = "/saveUser")
    public User saveUserController(@RequestBody User user) {
        return userDao.saveUserDao(user);
    }

    // ✅ FINAL LOGIN API (POST + JSON)
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginWithUserController(@RequestBody LoginRequestDTO request) {

        System.out.println("login user !!!");

        User user = userDao.findByEmailDao(request.getEmail());

        if (user != null && user.getPassword().equals(request.getPassword())) {
            httpSession.setAttribute("userSession", user.getEmail());

            return ResponseEntity.ok(Map.of(
                    "message", "Login Success",
                    "userEmail", user.getEmail()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }

    @GetMapping(value = "/userLogout")
    public ResponseEntity<?> userLogout() {
        System.out.println("logout user !!!");

        String email = (String) httpSession.getAttribute("userSession");

        if (email != null) {
            httpSession.invalidate();
            return ResponseEntity.ok(Map.of("message", "Logout Success", "userEmail", email));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user already logout");
    }

    @GetMapping(value = "/getUserLoggedInAddedItemsSummation")
    public ResponseEntity<?> getUserLoggedInAddedItemsSummation() {
        String email = (String) httpSession.getAttribute("userSession");
        if (email != null) {
            User user = userDao.findByEmailDao(email);
            if (user != null) {
                Double totalSum = user.getItems().stream().mapToDouble(Items::getPrice).sum();
                return ResponseEntity.ok(Map.of("totalSum", totalSum));
            }
        }
        return ResponseEntity.ok(Map.of("message", "user not found because session is not working, please login then try"));
    }

    @GetMapping(value = "/getUserName")
    public User getUserNameController() {
        String email = (String) httpSession.getAttribute("userSession");
        if (email != null) {
            return userDao.findByEmailDao(email);
        }
        return null;
    }
}