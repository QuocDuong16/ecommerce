package com.example.ecommerce.data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.web.multipart.MultipartFile;

public class ImageUtils {

    public static String encodeImage(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);
    }

    public static byte[] resizeImage(MultipartFile originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(originalImage.getInputStream());
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, bufferedImage.getType());

        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        return baos.toByteArray();
    }
    
    public static Blob convertToBlob(byte[] bytes) throws SQLException {
        if (bytes == null) {
            return null;
        }
        return new SerialBlob(bytes);
    }
}
