package org.bakka.userservice.Configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.bakka.userservice.Dtos.CompteBakkaResponseDto;
import org.bakka.userservice.Enums.TypeRole;
import org.bakka.userservice.Mappers.CompteBakkaMapper;
import org.bakka.userservice.Entities.*;
import org.bakka.userservice.Repositories.AdministrateurRepository;
import org.bakka.userservice.Repositories.ClientBakkaRepository;
import org.bakka.userservice.Repositories.CompteBakkaRepository;
import org.bakka.userservice.Services.UserBakaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.http.HttpHeaders;



@Service
public class JwtService {
    private final String ENCRIPTION_KEY = "ef109828a3c0ebba5e4d5d0a048caa4581f6f0925c6b6602368da735a344a7d859660fc9aa23160f75203e380ee6fe0945e19c22a10c7802fc50c264039709d9";


    @Value("${jwt.cookieExpiry}")
    private int cookieExpiry;
    private  final UserBakaService userBakaService;
    private final ClientBakkaRepository clientBakkaRepository;
    private final CompteBakkaRepository compteBakkaRepository;

    private final CompteBakkaMapper compteBakkaMapper;
    private final AdministrateurRepository administrateurRepository;

    public JwtService( UserBakaService userBakaService,
                      ClientBakkaRepository clientBakkaRepository,
                      CompteBakkaRepository compteBakkaRepository, CompteBakkaMapper compteBakkaMapper,
                       AdministrateurRepository administrateurRepository) {
        this.userBakaService = userBakaService;
        this.clientBakkaRepository = clientBakkaRepository;
        this.compteBakkaRepository = compteBakkaRepository;
        this.compteBakkaMapper = compteBakkaMapper;
        this.administrateurRepository = administrateurRepository;
    }

    public ResponseEntity<?> admin_generate(String userName, HttpServletResponse response) {

            UserDetails userDetails = this.userBakaService.loadUserByUsername(userName);
            Administrateur utilisateur = administrateurRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                    ()-> new EntityNotFoundException("Aucun admin avec ce userName")
            );

            return this.generateJwt(utilisateur, response);

    }

    public ResponseEntity<?> client_generate(String userName, HttpServletResponse response) {
        UserDetails userDetails = this.userBakaService.loadUserByUsername(userName);
        ClientBakka clientBakka = clientBakkaRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new EntityNotFoundException("Aucun client avec ce userName"));

        return this.generateJwt(clientBakka, response);
    }
 /*   public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractuserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }*/
    public String extractuserName(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private ResponseEntity<?> generateJwt(User utilisateur, HttpServletResponse response) {

        Map<String, Object> res = new HashMap<>();
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;
        long refreshExpirationTime = currentTime + 7 * 24 * 60 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "prenom", utilisateur.getPrenom(),
                "nom", utilisateur.getNom(),
                "username", utilisateur.getUsername(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getUsername()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getUsername())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
        final String refreshToken = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(refreshExpirationTime))
                .setSubject(utilisateur.getUsername())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();

        // set accessToken to cookie header
        ResponseCookie cookie = ResponseCookie.from("accessToken", bearer)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(cookieExpiry)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        res.put("access_token", bearer);
        res.put("refresh_token", refreshToken);
        if (utilisateur.getRole().getTypeRole().equals(TypeRole.CLIENT)){
            ClientBakka clientBakka = clientBakkaRepository.findByUsername(utilisateur.getUsername()).orElseThrow(
                    ()-> new EntityNotFoundException("Aucun client Bakka correspondant")
            );
            CompteBakka compteBakka = compteBakkaRepository.findByClientBakka(clientBakka).orElseThrow(
                    ()-> new EntityNotFoundException("Aucun compte correspondant")
            );
            CompteBakkaResponseDto compteBakkaResponseDto = compteBakkaMapper.compteBakkaToCompteBakkaResponseDto(compteBakka);
            res.put("data", compteBakkaResponseDto);
        }
        return new ResponseEntity<Object>(res, HttpStatus.CREATED);
    }



    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

}
