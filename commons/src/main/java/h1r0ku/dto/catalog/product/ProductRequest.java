package h1r0ku.dto.catalog.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Long categoryId;
    private Float averageStar = 0.0f;
    private Integer ordersCount = 0;
    private List<MultipartFile> images = new ArrayList<>();
}
