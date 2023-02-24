package h1r0ku.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private List<MultipartFile> images;
}
