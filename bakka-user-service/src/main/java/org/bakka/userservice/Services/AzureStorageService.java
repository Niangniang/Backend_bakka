package org.bakka.userservice.Services;

import com.azure.storage.blob.*;

import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.UUID;

@Service
public class AzureStorageService {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public String uploadFile(InputStream fileStream, String fileName, long fileSize) {
        String uniqueFileName = generateUniqueFileName(fileName);



        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(fileStream)) {
            if (fileSize > 0) {
                // Utilisez Apache Tika pour détecter le type de fichier en fonction de son contenu
                Tika tika = new Tika();
                String detectedContentType = tika.detect(bufferedInputStream);

                // Vérifiez si le type de contenu est un des types acceptés (images JPEG, JPG, PNG ou fichiers PDF)
                if (detectedContentType.startsWith("image/jpeg") || detectedContentType.startsWith("image/jpg") ||
                        detectedContentType.startsWith("image/png") || detectedContentType.startsWith("application/pdf")) {

                    String blobConnectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", accountName, accountKey);
                    BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(blobConnectionString).buildClient();
                    BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
                    BlobClient blobClient = containerClient.getBlobClient(uniqueFileName);

                    // Téléversez le fichier avec le type de contenu défini
                    blobClient.upload(bufferedInputStream, fileSize, true); // Le dernier paramètre true indique de remplacer les en-têtes

                    // Définir l'en-tête de type de contenu pour permettre la visualisation directe
                    BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(detectedContentType);
                    blobClient.setHttpHeaders(headers);

                    // Renvoyer l'URL du blob directement pour permettre la visualisation sans téléchargement
                    return blobClient.getBlobUrl();
                } else {
                    throw new UnsupportedFileTypeException("Le type de fichier n'est pas supporté. Seules les images JPEG, JPG, PNG et les fichiers PDF sont autorisés.");
                }
            } else {
                System.err.println("Le fichier est vide. Aucun téléversement nécessaire.");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors du téléversement du fichier : " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (UnsupportedFileTypeException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try {
                fileStream.close();
            } catch (IOException e) {
                System.err.println("Une erreur s'est produite lors de la fermeture du flux principal : " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private String generateUniqueFileName(String fileName) {
        String RequestName = "DemandeAdministrative";
        String UniqueFileName;
        String extension = "";
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDate = dateFormat.format(currentDate);
        int desiredSize = 5; // Taille souhaitée pour l'UUID
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = fileName.substring(dotIndex);
            fileName = fileName.substring(0, dotIndex);
        }
        String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, desiredSize);
        UniqueFileName = fileName + "_" + randomUUID + extension;
        return UniqueFileName;
    }

    public InputStream downloadFile(String fileName) throws IOException {
        String blobConnectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", accountName, accountKey);
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(blobConnectionString).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlockBlobClient blobClient = containerClient.getBlobClient(fileName).getBlockBlobClient();

        InputStream inputStream = null;
        inputStream = blobClient.openInputStream();
        return new BufferedInputStream(inputStream);
    }
    public class UnsupportedFileTypeException extends RuntimeException {
        public UnsupportedFileTypeException(String message) {
            super(message);
        }
    }


}
