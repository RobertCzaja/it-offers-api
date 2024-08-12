package pl.api.itoffers.security.ui.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserNameRequest {

    @NotBlank
    @Size(min = 1, max = 50)
    private String first;
    @NotBlank
    @Size(min = 1, max = 50)
    private String last;
}
