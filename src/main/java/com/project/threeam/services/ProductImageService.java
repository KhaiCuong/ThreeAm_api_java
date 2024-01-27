package com.project.threeam.services;

import com.project.threeam.dtos.CategoryDTO;
import com.project.threeam.dtos.ProductDTO;
import com.project.threeam.entities.CategoryEntity;
import com.project.threeam.entities.ProductEntity;
import com.project.threeam.entities.ProductImageEntity;
import com.project.threeam.repositories.ProductImageRepository;
import com.project.threeam.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductImageService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    private String uploadDirectory = "src/main/resources/static/uploads/";
    private String imageDirectory = "src/main/resources/static/";


    public List<String> GetProductImageByProductId(String productId) {
        Optional<ProductEntity> productOptional = productRepository.findByProductId(productId);

        if(productOptional.isPresent()) {
            ProductEntity pd = productOptional.get();
            Optional<List<ProductImageEntity>> productImage = productImageRepository.findByProductEntity(pd);
            List<ProductImageEntity> listImage = productImage.get();
            List<String> listUrl = new ArrayList<>();
            for (ProductImageEntity img : listImage) {
                try {
                    listUrl.add(img.getImage_url());
                } catch (Exception e) {
                }
            }
            return listUrl;
        } else { return null;}

    }




    public Set<String> uploadMultiImage(MultipartFile[] files,String productID) throws Exception {
        Set<String> results = new HashSet<>();
        Optional<ProductEntity> productOptional = productRepository.findByProductId(productID);

        if(productOptional.isPresent()) {
            ProductEntity pd = productOptional.get();

            int index = 0;

            for (MultipartFile file : files) {
                String fileName = uploadImage(file);
                if(index == 0) {
                    pd.setImage(fileName);
                    ProductEntity savedEntity = productRepository.save(pd);
                }
                ProductImageEntity productImage = new ProductImageEntity();
                productImage.setImage_url(fileName);
                productImage.setProductEntity(pd);
                ProductImageEntity savedEntity = productImageRepository.save(productImage);
                results.add(fileName);
                index++;
            }
            return results;

        } else { return null;}

    }

    public String uploadImage(MultipartFile image) throws Exception {
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }
        String fileName = UUID.randomUUID().toString() + image.getOriginalFilename();
        /// Lấy dữ liệu đầu vào từ InputStream của hình ảnh và sao chép vào tệp đích
        Path destination = Path.of(uploadDirectory, fileName);
        Files.copy(image.getInputStream(), destination);
        return  "uploads/" + fileName;
    }



    public Boolean deleteMultiImage(String productID) throws Exception {
        Optional<ProductEntity> productOptional = productRepository.findByProductId(productID);
        if(productOptional.isPresent()) {
            ProductEntity pd = productOptional.get();
            Optional<List<ProductImageEntity>> productImage = productImageRepository.findByProductEntity(pd);
            List<ProductImageEntity> listImage = productImage.get();
            for (ProductImageEntity img : listImage) {
                try {
                    // xóa hình trong thư mục uploads
                    deleteImage(img.getImage_url());
                    // xóa dữ liệu trong bảng Product Image
                    productImageRepository.delete(img);
                } catch (Exception e) {
                    return false;
                }
            }
        } else {return false;}
        return true;
    }

    public void deleteImage(String fileName) throws Exception {
        Path imagePath = Paths.get(imageDirectory, fileName);
        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }

}
