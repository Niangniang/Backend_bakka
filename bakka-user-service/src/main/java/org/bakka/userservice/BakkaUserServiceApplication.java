package org.bakka.userservice;


import org.bakka.userservice.Entities.Beneficiaire;
import org.bakka.userservice.Entities.Profil;
import org.bakka.userservice.Entities.Role;
import org.bakka.userservice.Enums.TypeRole;
import org.bakka.userservice.Repositories.BeneficiaireRepository;
import org.bakka.userservice.Repositories.ProfilRepository;
import org.bakka.userservice.Repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BakkaUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakkaUserServiceApplication.class, args);
    }
/*   @Bean
    CommandLineRunner start(ProfilRepository profilRepository, BeneficiaireRepository beneficiaireRepository, RoleRepository roleRepository) {

        return args -> {

            beneficiaireRepository.save(new Beneficiaire(null, "Niang", "Mountagha", "15KJGB"));
            beneficiaireRepository.save(new Beneficiaire(null, "Diao", "Abdoul Aziz", "82NCJY"));


            beneficiaireRepository.findAll().forEach(cpt->{
                System.out.println(cpt.getId());
            });


        };
    }*/



}
