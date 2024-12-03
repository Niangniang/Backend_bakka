package org.bakka.userservice.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.*;
import org.bakka.userservice.Entities.*;
import org.bakka.userservice.Enums.TypeCompte;
import org.bakka.userservice.Enums.TypeRole;
import org.bakka.userservice.Mappers.*;
import org.bakka.userservice.RecodDtos.AdminPasswordUpdate;
import org.bakka.userservice.RecodDtos.ClientPasswordUpdate;
import org.bakka.userservice.RecodDtos.OtpDto;
import org.bakka.userservice.RecodDtos.UserNameDto;
import org.bakka.userservice.Repositories.*;
import org.bakka.userservice.Utils.SendSMS;
import org.bakka.userservice.Utils.UtilMethods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class UserBakaService implements UserDetailsService {
    private final ClientBakkaRepository clientBakkaRepository;
    private final ClientBakkaMapper clientBakkaMapper;
    private final RoleRepository roleRepository;
    private final CompteBakkaMapper compteBakkaMapper;
    private final CompteBakkaRepository compteBakkaRepository;
    private final CarteRepository carteRepository;
    private  final CarteMapper carteMapper;
    private final OtpValidationMapper otpValidationMapper;
    private final OtpValidationRepository otpValidationRepository;
    private final ParameterRepository parameterRepository;
    private final BeneficiaireMapper beneficiaireMapper;
    private final BeneficiaireRepository beneficiaireRepository;
    private final AdministrateurRepository administrateurRepository;
    private final AdministrateurMapper administrateurMapper;
    private final ProfilRepository profilRepository;
    private final EmailService emailService;




    public UserBakaService(ClientBakkaRepository clientBakkaRepository, ClientBakkaMapper clientBakkaMapper, RoleRepository roleRepository, CompteBakkaMapper compteBakkaMapper,
                           CompteBakkaRepository compteBakkaRepository,
                           CarteRepository carteRepository, CarteMapper carteMapper, OtpValidationMapper otpValidationMapper,
                           OtpValidationRepository otpValidationRepository, ParameterRepository parameterRepository, BeneficiaireMapper beneficiaireMapper,
                           BeneficiaireRepository beneficiaireRepository, AdministrateurRepository administrateurRepository, AdministrateurMapper administrateurMapper, ProfilRepository profilRepository, EmailService emailService) {
        this.clientBakkaRepository = clientBakkaRepository;
        this.clientBakkaMapper = clientBakkaMapper;
        this.roleRepository = roleRepository;
        this.compteBakkaMapper = compteBakkaMapper;
        this.compteBakkaRepository = compteBakkaRepository;
        this.carteRepository = carteRepository;
        this.carteMapper = carteMapper;
        this.otpValidationMapper = otpValidationMapper;
        this.otpValidationRepository = otpValidationRepository;
        this.parameterRepository = parameterRepository;
        this.beneficiaireMapper = beneficiaireMapper;
        this.beneficiaireRepository = beneficiaireRepository;
        this.administrateurRepository = administrateurRepository;
        this.administrateurMapper = administrateurMapper;
        this.profilRepository = profilRepository;
        this.emailService = emailService;
    }


    public ResponseEntity<?> createadmin(AdministrateurRequestDto administrateurRequestDto) {

        Map<String, Object>  adm = new HashMap<>();
        try {
            if(administrateurRepository.existsByUsername(administrateurRequestDto.getEmail())){
                adm.put("message","Ce profil existe déja");
                return new ResponseEntity<Object>(adm, HttpStatus.BAD_REQUEST);
            }else{

                administrateurRequestDto.setPassword(new BCryptPasswordEncoder().encode(administrateurRequestDto.getPassword()));
                Role role = roleRepository.findByTypeRole(TypeRole.ADMINISTRATEUR).orElseThrow(
                        () ->   new EntityNotFoundException("Ce role n'existe pas")
                );
                Profil profil = profilRepository.findByLibelle("ADMINISTRATEUR").orElseThrow(
                        () ->   new EntityNotFoundException("Ce profil n'existe pas")
                );
                Administrateur administrateur=administrateurMapper.administrateurRequestDtoToAdministrateur(administrateurRequestDto);
                administrateur.setRole(role);
                administrateur.setProfil(profil);
                administrateur.setUsername(administrateurRequestDto.getEmail());
                Administrateur newAdmin=administrateurRepository.save(administrateur);
                //we send the email
                String emailMessage=returnEmail(newAdmin.getUsername());
                adm.put("email_message",emailMessage);
                AdministrateurResponseDto administrateurResponseDto=administrateurMapper.administrateurToAdministrateurResponseDto(newAdmin);
                adm.put("message","Enregistrement réussit");
                adm.put("admin",administrateurResponseDto);
                return new ResponseEntity<Object>(adm,HttpStatus.CREATED);
            }
        }catch (Exception e){
            adm.put("message", e.getMessage());
            return new ResponseEntity<Object>(adm,HttpStatus.NOT_FOUND);
        }
    }


    public String returnEmail(String email){
        Administrateur admin=administrateurRepository.findByUsername(email).orElseThrow(
                ()->  new EntityNotFoundException("Admin not found")
        );
        if (admin!=null){
            if (!admin.isActif()) {
                String text = "Bonjour " + admin.getPrenom() + " " + admin.getNom() + ",\n\n"
                        + "Voici votre lien pour la confirmation de votre email: http://bregitsonvm2.francecentral.cloudapp.azure.com:5661/admin/confirmEmail/" + admin.getId() + " .\n\n"
                        + "Cordialement.";
                emailService.sendSimpleEmail(email, "Confirmation Email sur ABG-IMMO", text);
                return "E-mail envoyé ";
            }
            else {
                return "le mail est confirmé merci de vous rapprocher de l'administrateur du site si vous ne pouvez pas vous connecter";
            }
        }
        else {
            return "l'email n'existe pas merci de vous inscrire avant de recevoir un email";
        }
    }


    public void confirmEmail(UUID id)    {

        Administrateur admin = administrateurRepository.findById(id).orElseThrow(
                () ->   new EntityNotFoundException("Cet admin n'existe pas")
        );
        admin.setIsActive(true);
        administrateurRepository.save(admin);

    }


    public ResponseEntity<?> updataPassword(AdminPasswordUpdate adminPasswordUpdate) {
        Map<String, Object> res = new HashMap<>();
        try {
            if (!adminPasswordUpdate.newPassword().equals(adminPasswordUpdate.confirmPassword())) {

                res.put("message", "Le nouveau mot de passe et l'ancien ne correspondent pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            } else {
                Administrateur administrateur = administrateurRepository.findByUsername(adminPasswordUpdate.userName()).orElseThrow(() -> new EntityNotFoundException("Aucun admin avec ce username"));
                administrateur.setPassword(new BCryptPasswordEncoder().encode(adminPasswordUpdate.newPassword()));
                administrateurRepository.save(administrateur);
                res.put("message", "Mot de passe réinitialisé avec succès");
                return new ResponseEntity<Object>(res, HttpStatus.OK);
            }
        }catch (Exception e){
            res.put("message", e.getMessage());
            return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> sendEmail(UserNameDto userNameDto) {
        Map<String, Object>  adm = new HashMap<>();
        try {
            Administrateur administrateur = administrateurRepository.findByUsername(userNameDto.userName()).orElseThrow(()-> new EntityNotFoundException("Admin not found"));
            String object = "Réinitialisation du mot de passe";
            String message = "Bonjour " + administrateur.getPrenom()+ " " + administrateur.getNom() + " veuillez utiliser ce lien : " +  " http://localhost:3030/updatePassword/  " + " pour réinitaliser votre mot de passe.  \n Coordialement ! ";
            emailService.sendSimpleEmail(userNameDto.userName(), object, message );
            adm.put("message", "Mail avec otp envoyé avec succès");
            return new ResponseEntity<Object>(adm,HttpStatus.OK);

        }catch (Exception e){
            adm.put("message", e.getMessage());
            return new ResponseEntity<Object>(adm,HttpStatus.NOT_FOUND);
        }
    }



    public ResponseEntity<?> registerClientBakka(ClientBakkaRequestDto clientBakkaRequestDto){
        Map<String, Object> res = new HashMap<>();
        try {
            if(clientBakkaRepository.existsClientBakkaByTelephone(clientBakkaRequestDto.getTelephone())){
                res.put("message","Il existe déja un utilisateur avec ce numéro de téléphone");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }

            else{
                // Client Bakka
                clientBakkaRequestDto.setPassword(new BCryptPasswordEncoder().encode(clientBakkaRequestDto.getPassword()));
                Role role = roleRepository.findByTypeRole(TypeRole.CLIENT).orElseThrow(
                        () ->   new EntityNotFoundException("Ce role n'existe pas")
                );
//                clientBakkaRequestDto.setRole(role);

                ClientBakka clientBakka= clientBakkaMapper.clientBakkaRequestDtoClientBakka(clientBakkaRequestDto);
                clientBakka.setRole(role);
                clientBakka.setActif(true);
                clientBakka.setUsername(clientBakkaRequestDto.getTelephone());
                ClientBakka newClient=clientBakkaRepository.save(clientBakka);
                // compteBakka
                CompteBakkaRequestDto compteBakkaRequestDto = new CompteBakkaRequestDto();
                compteBakkaRequestDto.setClientBakka(newClient);
                String identifiant;
                do {
                    identifiant = UtilMethods.generateIdentifiant();
                } while (compteBakkaRepository.existsByNumeroCompte(identifiant));

                CompteBakka compteBakka = compteBakkaMapper.compteBakkaRequestDtoToCompteBakka(compteBakkaRequestDto);


                // Carte
                CarteRequestDto carteRequestDto = new CarteRequestDto();
                String cvv;
                String numero;
                String reference;
                do {
                    cvv = UtilMethods.generateCVV();
                } while (carteRepository.existsByCvv(cvv));

                do {
                    numero = UtilMethods.generateNumero();
                } while (carteRepository.existsByNumero(numero));
                do {
                    reference = UtilMethods.generateIdentifiant();
                } while (carteRepository.existsByReference(reference));
                carteRequestDto.setCvv(cvv);
                carteRequestDto.setNumero(numero);
                carteRequestDto.setReference(reference);
                Carte carte = carteMapper.carteRequestDtoToCarte(carteRequestDto);

                Carte newCarte = carteRepository.save(carte);
                compteBakka.setCarte(carte);
                compteBakka.setNumeroCompte(identifiant);
                compteBakka.setTypeCompte(TypeCompte.COMPTEBAKKA);
                //we add the default parameters
                Set<Parameter> parametersPlafond1=parameterRepository.findParameterByLibelle("PLAFOND_1");
                compteBakka.setParameters(parametersPlafond1);
                Parameter limiteTransactionnelle=parameterRepository.findParameterByLibelleContains("LIMITE_TRANSACTIONELLE");
                compteBakka.getParameters().add(limiteTransactionnelle);
                compteBakkaRepository.save(compteBakka);
                // Create beneficiaire
                BeneficiaireRequestDto beneficiaireRequestDto = new  BeneficiaireRequestDto();
                beneficiaireRequestDto.setNom(compteBakka.getClientBakka().getNom());
                beneficiaireRequestDto.setPrenom(compteBakka.getClientBakka().getPrenom());
                beneficiaireRequestDto.setNumeroCompte(compteBakka.getNumeroCompte());
                beneficiaireRequestDto.setCompteBakkaId(compteBakka.getId());
                beneficiaireRequestDto.setTelephone(compteBakka.getClientBakka().getTelephone());

                Beneficiaire beneficiaire = beneficiaireMapper.beneficiaireRequestDtoToBeneficiaire(beneficiaireRequestDto);
                beneficiaireRepository.save((beneficiaire));
                //newCarte.setCompteBakka(newcompteBakka);
                carteRepository.save(newCarte);
                String otp;
                do {
                    otp = UtilMethods.generateOtp();
                }while (otpValidationRepository.existsByOtp(otp));

                OtpValidationRequestDto otpValidationRequestDto = new  OtpValidationRequestDto();
                otpValidationRequestDto.setOtp(otp);
                otpValidationRequestDto.setClientBakka(clientBakka);

                OtpValidation otpValidation = otpValidationMapper.otpValidationRequestDtoToOtpValidation(otpValidationRequestDto);
                otpValidation.setExpirationWithFiveMinutesAdded();
                otpValidationRepository.save(otpValidation);


                ClientBakkaResponseDto clientBakkaResponseDto= clientBakkaMapper.clientBakkaToClientBakkaResponseDto(newClient);
                res.put("message","inscription réussi");
                res.put("data",clientBakkaResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.CREATED);
            }
        }catch (Exception e){
            res.put("message", e.getMessage());
            return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<?> getClientBakka(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(clientBakkaRepository.existsById(id)){
                ClientBakka clientBakka=clientBakkaRepository.findById(id).orElseThrow();
                ClientBakkaResponseDto clientBakkaResponseDto= clientBakkaMapper.clientBakkaToClientBakkaResponseDto(clientBakka);
                return ResponseEntity.ok(clientBakkaResponseDto);
            }else{
                res.put("message","ce client n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public List<ClientBakkaResponseDto> getAllClientBakka() {
        try {
            List<ClientBakka> listClientBakka= clientBakkaRepository.findAll();
            return listClientBakka.stream().map(clientBakkaMapper::clientBakkaToClientBakkaResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<?> updateClientBakka(UUID id, ClientBakkaRequestDto clientBakkaRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(clientBakkaRepository.existsById(id)){
                ClientBakka clientBakka= clientBakkaMapper.clientBakkaRequestDtoClientBakka(clientBakkaRequestDto);
                clientBakka.setId(id);
                ClientBakka updatedClientBakka=clientBakkaRepository.save(clientBakka);
                ClientBakkaResponseDto clientBakkaResponseDto= clientBakkaMapper.clientBakkaToClientBakkaResponseDto(updatedClientBakka);
                res.put("edited_admin",clientBakkaResponseDto);
                res.put("message","client édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce client n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }


    public ResponseEntity<?> updateClientBakkaFields(UUID id, Map<String, Object> fields) {


        Map<String, Object> res = new HashMap<>();
        try {
            Optional<ClientBakka> existingClientBakka=clientBakkaRepository.findById(id);
            if(existingClientBakka.isPresent()){
                Beneficiaire beneficiaire = beneficiaireRepository.findByTelephone(existingClientBakka.get().getTelephone()).orElseThrow( () -> new EntityNotFoundException("Beneficiaire non trouvé"));
                if (fields.containsKey("nom")) {
                    String nom = (String) fields.get("nom");
                    beneficiaire.setNom(nom);
                }
                if (fields.containsKey("prenom")) {
                    String prenom = (String) fields.get("prenom");
                    beneficiaire.setPrenom(prenom);
                }

                fields.forEach((key,value)->{
                    Field field=  ReflectionUtils.findField(ClientBakka.class,key);
                    assert field != null;
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingClientBakka.get(),value);
                });
                ClientBakka clientBakka=clientBakkaRepository.save(existingClientBakka.get());
                // Sauvegardez l'objet beneficiaire après la mise à jour
                beneficiaireRepository.save(beneficiaire);
                res.put("edited_client", clientBakkaMapper.clientBakkaToClientBakkaResponseDto(clientBakka));
                res.put("message","client édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce client n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }


    public ResponseEntity<?> deleteClientBakka(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(clientBakkaRepository.existsById(id)){
                clientBakkaRepository.deleteById(id);
                res.put("message","client supprimé");
                return new ResponseEntity<Object>(res, HttpStatus.OK);
            }else{
                res.put("message","ce client n'existe pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Administrateur> adminUser = administrateurRepository.findByUsername(username);
        if (adminUser.isPresent()) {

            return this.administrateurRepository
                    .findByUsername(username)
                    .orElseThrow( () -> new UsernameNotFoundException("Aucun admin ne correspond à ce username"));
        }
        return this.clientBakkaRepository
                .findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("Aucun client ne correspond à ce username"));
    }

    public ResponseEntity<?> code_verification(OtpDto otpDto) {
        Map<String, Object> res = new HashMap<>();

        Optional<OtpValidation> otpValidation = otpValidationRepository.findByOtp(otpDto.otp());
       if(otpValidation.isPresent() && otpValidation.get().getExpiration().isAfter(Instant.now())){
           ClientBakka  clientBakka = otpValidation.get().getClientBakka();
           clientBakka.setActif(true);
           otpValidation.get().setVerify(true);
           otpValidationRepository.save(otpValidation.get());
           clientBakkaRepository.save(clientBakka);
           res.put("message", "Vérification faite avec succès");

           return new ResponseEntity<Object>(res, HttpStatus.OK);
       }else {
           res.put("message", "Le code saisi est incorrecte ou expiré");
           return new ResponseEntity<Object>(res, HttpStatus.UNAUTHORIZED);

       }

    }

    public ResponseEntity<?> send_otp(UserNameDto userNameDto) throws Exception {

        Map<String, Object> res = new HashMap<>();

        Optional<ClientBakka> clientBakka = clientBakkaRepository.findByUsername(userNameDto.userName());

        String otp;

        if (clientBakka.isPresent()) {
            do {
                otp = UtilMethods.generateOtp();
            } while (otpValidationRepository.existsByOtp(otp));
            OtpValidationRequestDto otpValidationRequestDto = new OtpValidationRequestDto();
            otpValidationRequestDto.setClientBakka(clientBakka.get());
            otpValidationRequestDto.setOtp(otp);

            OtpValidation otpValidation = otpValidationMapper.otpValidationRequestDtoToOtpValidation(otpValidationRequestDto);
            otpValidation.setExpirationWithFiveMinutesAdded();
            otpValidationRepository.save(otpValidation);

            String subject = "Réinitialisstion du mot de passe";
            String content = "Bonjour " + clientBakka.get().getPrenom() + " " + clientBakka.get().getNom() + "  pour la réinitialisation de votre mot de passe , veuillez utiliser ce code :  " + otp + ". Merci" ;
            SendSMS.send_sms(clientBakka.get().getTelephone(), subject, content );

            res.put("message", "Opt envoyé avec succès !");
            return new ResponseEntity<Object>(res, HttpStatus.OK);
        }else {
            res.put("message", "Aucun utilisateur trouvé avec les informations saisis");
            return new ResponseEntity<Object>(res, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> update_password(ClientPasswordUpdate clientPasswordUpdate) {
        Map<String, Object> res = new HashMap<>();
        if (!clientPasswordUpdate.newPassword().equals(clientPasswordUpdate.confirmPassword())) {

            res.put("message", "Le nouveau mot de passe et l'ancien ne correspondent pas");
            return  new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);

        }else {
            Optional<OtpValidation> otpValidation = otpValidationRepository.findByOtp(clientPasswordUpdate.otp());
            if(otpValidation.isPresent()) {
                ClientBakka clientBakka = otpValidation.get().getClientBakka();
                clientBakka.setPassword(new BCryptPasswordEncoder().encode(clientPasswordUpdate.newPassword()));
                clientBakkaRepository.save(clientBakka);
                res.put("message", "Mot de passe réinitialisé avec succès");
                return new ResponseEntity<Object>(res, HttpStatus.OK);
            }else {
                res.put("message", "Code invalide ");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }


        }
    }

    public List<AdministrateurResponseDto> getAllAdmins() {
        try {
            List<Administrateur> administrateurList = administrateurRepository.findAll();
            return administrateurList.stream().map(administrateurMapper::administrateurToAdministrateurResponseDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
