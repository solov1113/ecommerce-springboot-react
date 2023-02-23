package h1r0ku.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductRequest {
    private Double price;
    private Long categoryId;
    private List<MultipartFile> images;
}
