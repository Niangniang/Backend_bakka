package org.bakka.userservice.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.CompteWalletRequestDto;
import org.bakka.userservice.Dtos.CompteWalletResponseDto;
import org.bakka.userservice.Entities.ClientBakka;
import org.bakka.userservice.Entities.CompteWallet;
import org.bakka.userservice.Entities.Wallet;
import org.bakka.userservice.Enums.TypeCompte;
import org.bakka.userservice.Mappers.CompteWalletMapper;
import org.bakka.userservice.Repositories.ClientBakkaRepository;
import org.bakka.userservice.Repositories.CompteWalletRepository;
import org.bakka.userservice.Repositories.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.bakka.userservice.Utils.UtilMethods;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class CompteWalletServiceImpl implements  CompteWalletService {

    private  final CompteWalletRepository compteWalletRepository;
    private final CompteWalletMapper compteWalletMapper;
    private final WalletRepository walletRepository;
    private final ClientBakkaRepository clientBakkaRepository;

    public CompteWalletServiceImpl(CompteWalletRepository compteWalletRepository, CompteWalletMapper compteWalletMapper, WalletRepository walletRepository,
                                   ClientBakkaRepository clientBakkaRepository) {
        this.compteWalletRepository = compteWalletRepository;
        this.compteWalletMapper = compteWalletMapper;
        this.walletRepository = walletRepository;
        this.clientBakkaRepository = clientBakkaRepository;
    }

    @Override
    public ResponseEntity<?> addcompteWallet(CompteWalletRequestDto compteWalletRequestDto) {
        Map<String, Object> cw = new HashMap<>();
        try {

            CompteWallet compteWallet = compteWalletMapper.compteWalletRequestDtoToCompteWallet(compteWalletRequestDto);

            ClientBakka clientBakka = clientBakkaRepository.findById(compteWalletRequestDto.getClientBakka().getId()).orElseThrow(
                    ()-> new EntityNotFoundException("Client Bakka non trouvé")
            );
            Wallet wallet = walletRepository.findById(compteWalletRequestDto.getOperateur().getId()).orElseThrow(
                    ()-> new EntityNotFoundException("Wallet non trouvé")
            );
            if(compteWalletRepository.existsByNumeroCompteAndOperateur(compteWalletRequestDto.getNumeroCompte(), compteWalletRequestDto.getOperateur())){
                Optional<Wallet> operateur = walletRepository.findById(compteWalletRequestDto.getOperateur().getId());
                cw.put("message", "Un compte " +  wallet.getIntitule() + " avec ce numéro existe deja");
                return new ResponseEntity<Object>(cw, HttpStatus.BAD_REQUEST);
            }
            compteWallet.setOperateur(walletRepository.findById(compteWalletRequestDto.getOperateur().getId()).orElseThrow());
            compteWallet.setClientBakka(clientBakka);
            //we set the generated values
            compteWallet.setSolde(UtilMethods.generateSolde());
            compteWallet.setTypeCompte(TypeCompte.COMPTEWALLET);
            CompteWallet newCompteWallet=compteWalletRepository.save(compteWallet);
            CompteWalletResponseDto compteWalletResponseDto=compteWalletMapper.compteWalletToComteWalletResponseDto(newCompteWallet);
            cw.put("message","Compte Wallet ajouté aveec succès");
            cw.put("data",compteWalletResponseDto);
            return new ResponseEntity<Object>(cw, HttpStatus.CREATED);

        }catch (Exception e){

            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getcompteWallet(UUID id) {
        Map<String, Object> cw = new HashMap<>();
        try {
            if(compteWalletRepository.existsById(id)){
                CompteWallet compteWallet=compteWalletRepository.findById(id).get();
                CompteWalletResponseDto compteWalletResponseDto= compteWalletMapper.compteWalletToComteWalletResponseDto(compteWallet);
                return ResponseEntity.ok(compteWalletResponseDto);
            }else{
                cw.put("message","Ce compte Wallet n'existe pas");
                return new ResponseEntity<Object>(cw,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<CompteWalletResponseDto> getAllCompteWallet() {
        try {
            List<CompteWallet> compteWalletList=
                    compteWalletRepository.findAll();
            return compteWalletList.stream().map(compteWalletMapper::compteWalletToComteWalletResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompteWallet(UUID id, CompteWalletRequestDto compteWalletRequestDto) {
        Map<String, Object> cw = new HashMap<>();
        try {
            if(compteWalletRepository.existsById(id)){
                CompteWallet compteWallet = compteWalletMapper.compteWalletRequestDtoToCompteWallet(compteWalletRequestDto);
                compteWallet.setId(id);
                CompteWallet updatedCompteWallet=compteWalletRepository.save(compteWallet);
                CompteWalletResponseDto compteWalletResponseDto= compteWalletMapper.compteWalletToComteWalletResponseDto(updatedCompteWallet);
                cw.put("data",compteWalletResponseDto);
                cw.put("message","compte wallet édité");
                return new ResponseEntity<Object>(cw,HttpStatus.OK);
            }else{
                cw.put("message","ce compte wallet n'existe pas");
                return new ResponseEntity<Object>(cw,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompteWalletFields(UUID id, Map<String, Object> fields) {
        Map<String, Object> cw = new HashMap<>();
        try {
            CompteWallet existCompteWalletById=compteWalletRepository.findById(id).orElseThrow(
                    ()-> new EntityNotFoundException("compte Wallet non trouvé")
            );
            fields.forEach((key,value)->{
                Field field=  ReflectionUtils.findField(CompteWallet.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existCompteWalletById,value);
            });
            CompteWallet compteWallet=compteWalletRepository.save(existCompteWalletById);
            cw.put("data", compteWalletMapper.compteWalletToComteWalletResponseDto(compteWallet));
            cw.put("message","Wallet partiellement edité");
            return new ResponseEntity<Object>(cw,HttpStatus.OK);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteCompteWallet(UUID id) {
        Map<String, Object> cw = new HashMap<>();
        try {
            if(compteWalletRepository.existsById(id)){
                compteWalletRepository.deleteById(id);
                cw.put("message","compte wallet supprimé");
                return new ResponseEntity<Object>(cw, HttpStatus.OK);
            }else{
                cw.put("message","ce com^pte wallet n'existe pas");
                return new ResponseEntity<Object>(cw, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public ResponseEntity<?> getAllClientWalletAccounts(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(clientBakkaRepository.existsById(id)){
                List<CompteWallet> compteWallets=compteWalletRepository.getAllByClientBakkaId(id);
                return ResponseEntity.ok(compteWallets);
            }else{
                res.put("message","ce client n'existe pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
