package h1r0ku.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {
    @Size(min = 3, max = 25, message = "Name must be between 3 and 30 characters")
    @NotBlank(message = "Name value mustn't be null or whitespace")
    private String name;

    private Long parentCategoryId;
}