package net.xeill.elpuig.apipatitasconectadas.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileStorageService {

    private final Cloudinary cloudinary;

    public FileStorageService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }

    public String storeFile(MultipartFile file, String type) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen");
        }

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", "patitas/" + type));

        return (String) uploadResult.get("secure_url");
    }

    public String storeFile(MultipartFile file) throws IOException {
        return storeFile(file, "posts");
    }

    public void deleteFile(String fileUrl) throws IOException {
        if (fileUrl == null || fileUrl.isEmpty()) return;

        // Extraer el public_id de la URL de Cloudinary
        // URL formato: https://res.cloudinary.com/cloud/image/upload/v123/patitas/posts/filename.jpg
        if (fileUrl.contains("cloudinary.com")) {
            String publicId = fileUrl
                    .replaceAll(".*upload/v\\d+/", "")
                    .replaceAll("\\.[^.]+$", "");
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }
    }
}
