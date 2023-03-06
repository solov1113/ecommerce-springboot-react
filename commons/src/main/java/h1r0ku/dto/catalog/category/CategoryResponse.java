package h1r0ku.dto.catalog.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private Integer level;
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private List<CategoryResponse> childCategories = new ArrayList<>();
    @JsonBackReference
    private CategoryResponse parentCategory;
    private List<BannerResponse> banners;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
