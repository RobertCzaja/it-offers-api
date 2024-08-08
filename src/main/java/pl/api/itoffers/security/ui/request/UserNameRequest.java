package pl.api.itoffers.security.ui.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserNameRequest {

    @NotBlank
    @Size(min = 1, max = 50)
    private String first;
    @NotBlank
    @Size(min = 1, max = 50)
    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
