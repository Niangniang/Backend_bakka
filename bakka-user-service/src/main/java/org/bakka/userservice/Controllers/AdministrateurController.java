package org.bakka.userservice.Controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.bakka.userservice.Configs.JwtService;
import org.bakka.userservice.Dtos.ActivitiesResponseDto;
import org.bakka.userservice.Dtos.AdministrateurRequestDto;
import org.bakka.userservice.Dtos.AdministrateurResponseDto;
import org.bakka.userservice.Dtos.ClientBakkaResponseDto;
import org.bakka.userservice.RecodDtos.AdminPasswordUpdate;
import org.bakka.userservice.RecodDtos.AuthenticationDto;
import org.bakka.userservice.RecodDtos.UserNameDto;
import org.bakka.userservice.Services.UserBakaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdministrateurController {

    private final UserBakaService userBakaService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AdministrateurController(UserBakaService userBakaService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userBakaService = userBakaService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signupadmin(@RequestBody AdministrateurRequestDto administrateurRequestDto){
        return userBakaService.createadmin(administrateurRequestDto);
    }

    @PostMapping( path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDto authenticationDto, HttpServletResponse response){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.userName(), authenticationDto.password())

        );
        if(authentication.isAuthenticated()) {

            return this.jwtService.admin_generate(authenticationDto.userName(), response);
        }
        return null;
    }

    @PostMapping("/forgetPassword/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody UserNameDto userNameDto){
        return userBakaService.sendEmail(userNameDto);
    }

    @PostMapping("/forgetPassword/update_password")
    public ResponseEntity<?> updataPassword(@RequestBody AdminPasswordUpdate adminPasswordUpdate){
        return userBakaService.updataPassword(adminPasswordUpdate);
    }

    @GetMapping("/confirmEmail/{id}")
    public void confirmEmail(@PathVariable("id") UUID id){
        userBakaService.confirmEmail(id);
    }

    @GetMapping("/all")
    List<AdministrateurResponseDto> getAllAdmins(){
        return  userBakaService.getAllAdmins();
    }
}
