package org.bakka.userservice.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.WalletRequestDto;
import org.bakka.userservice.Dtos.WalletResponseDto;
import org.bakka.userservice.Entities.Wallet;
import org.bakka.userservice.Mappers.WalletMapper;
import org.bakka.userservice.Repositories.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class WalletServiceImpl  implements WalletService{

    private  final WalletRepository walletRepository;

    private final WalletMapper walletMapper;
    private final AzureStorageService azureStorageService;

    Map<String, Object> wal = new HashMap<>();

    public WalletServiceImpl(WalletRepository walletRepository, WalletMapper walletMapper, AzureStorageService azureStorageService) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
        this.azureStorageService = azureStorageService;
    }


    @Override
    public ResponseEntity<?> addWallet(WalletRequestDto walletRequestDto) {
        Map<String, Object> res = new HashMap<>();

        try {
            if(walletRepository.existsByIntitule(walletRequestDto.getIntitule())){
                res.put("message","Il existe déjas une wallet avec cet intitulé");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }else{
                String imageUrl= azureStorageService.uploadFile(
                        walletRequestDto.getImageUrl().getInputStream(),
                        walletRequestDto.getImageUrl().getOriginalFilename(),
                        walletRequestDto.getImageUrl().getSize()
                );

                Wallet wallet = walletMapper.walletRequestDtoToWallet(walletRequestDto);
                wallet.setImageUrl(imageUrl);
                Wallet newWallet=walletRepository.save(wallet);
                WalletResponseDto walletResponseDto=walletMapper.walletToWalletResponseDto(newWallet);
                wal.put("message","Wallet ajouté aveec succès");
                wal.put("data",walletResponseDto);
                return new ResponseEntity<Object>(wal,HttpStatus.CREATED);
            }


        }catch (Exception e){

            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getWallet(UUID id) {
        Map<String, Object> wal = new HashMap<>();
        try {
            if(walletRepository.existsById(id)){
                Wallet wallet=walletRepository.findById(id).get();
                WalletResponseDto walletResponseDto= walletMapper.walletToWalletResponseDto(wallet);
                return ResponseEntity.ok(walletResponseDto);
            }else{
                wal.put("message","Ce Wallet n'existe pas");
                return new ResponseEntity<Object>(wal,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<WalletResponseDto> getAllWallet() {
        try {
            List<Wallet> walletList= walletRepository.findAll();
            return walletList.stream().map(walletMapper::walletToWalletResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateWallet(UUID id, WalletRequestDto walletRequestDto) {
        try {
            if(walletRepository.existsById(id)){
                Wallet wallet = walletMapper.walletRequestDtoToWallet(walletRequestDto);
                wallet.setId(id);
                Wallet updatedWallet=walletRepository.save(wallet);
                WalletResponseDto walletResponseDto= walletMapper.walletToWalletResponseDto(updatedWallet);
                wal.put("data",walletResponseDto);
                wal.put("message","wallet édité");
                return new ResponseEntity<Object>(wal,HttpStatus.OK);
            }else{
                wal.put("message","ce wallet n'existe pas");
                return new ResponseEntity<Object>(wal,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateWalletFields(UUID id, Map<String, Object> fields) {

        Map<String, Object> wal = new HashMap<>();
        try {
            Wallet existWalletById=walletRepository.findById(id).orElseThrow(
                    ()-> new EntityNotFoundException("Wallet non trouvé")
            );

                fields.forEach((key,value)->{
                    Field field=  ReflectionUtils.findField(Wallet.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existWalletById,value);
                });
                Wallet wallet=walletRepository.save(existWalletById);
                wal.put("data", walletMapper.walletToWalletResponseDto(wallet));
                wal.put("message","Wallet partiellement edité");
                return new ResponseEntity<Object>(wal,HttpStatus.OK);

        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteWallet(UUID id) {
        Map<String, Object> wal = new HashMap<>();
        try {
            if(walletRepository.existsById(id)){
                walletRepository.deleteById(id);
                wal.put("message","wallet supprimé");
                return new ResponseEntity<Object>(wal, HttpStatus.OK);
            }else{
                wal.put("message","ce wallet n'existe pas");
                return new ResponseEntity<Object>(wal, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
