package org.bakka.userservice.Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.bakka.userservice.Configs.JwtService;
import org.bakka.userservice.Dtos.*;
import org.bakka.userservice.RecodDtos.AuthenticationDto;
import org.bakka.userservice.RecodDtos.ClientPasswordUpdate;
import org.bakka.userservice.RecodDtos.OtpDto;
import org.bakka.userservice.RecodDtos.UserNameDto;
import org.bakka.userservice.Services.UserBakaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientBakkaController {
    private final UserBakaService userBakaService;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ClientBakkaController(UserBakaService userBakaService, AuthenticationManager authenticationManager, JwtService jwtService ) {
        this.userBakaService = userBakaService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }

 @PostMapping("/register")
 public ResponseEntity<?> registerClientBakka(@RequestBody ClientBakkaRequestDto clientBakkaRequestDto){
     return userBakaService.registerClientBakka(clientBakkaRequestDto);
 }

    @PostMapping("/code_verification")
    public ResponseEntity<?> code_verification(@RequestBody OtpDto otpDto){
        return userBakaService.code_verification(otpDto);
    }
    @PostMapping("/forgot_password/send_otp")
    public ResponseEntity<?> send_otp(@RequestBody UserNameDto userNameDto) throws Exception {
        return userBakaService.send_otp(userNameDto);
    }
    @PostMapping("/forgot_password/update_password")
    public ResponseEntity<?> update_password(@RequestBody ClientPasswordUpdate clientPasswordUpdate) throws Exception {
        return userBakaService.update_password(clientPasswordUpdate);
    }

    @PostMapping( path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDto authenticationDto, HttpServletResponse response){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.userName(), authenticationDto.password())

        );
        if(authentication.isAuthenticated()) {
            return this.jwtService.client_generate(authenticationDto.userName(), response);
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientBakka(@PathVariable("id") UUID id){
        return userBakaService.getClientBakka(id);
    }

    @GetMapping("/all")
    public List<ClientBakkaResponseDto> getAllClientBakka(){
        return userBakaService.getAllClientBakka();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateClientBakka(@PathVariable("id") UUID id,@RequestBody ClientBakkaRequestDto clientBakkaRequestDto){
        return userBakaService.updateClientBakka(id,clientBakkaRequestDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?>updateClientBakkaFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return userBakaService.updateClientBakkaFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteClientBakka(@PathVariable("id") UUID id){
        return userBakaService.deleteClientBakka(id);
    }

}
