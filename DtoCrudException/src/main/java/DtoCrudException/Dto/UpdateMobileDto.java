package DtoCrudException.Dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMobileDto {

    @Pattern(regexp = "\\d{10}", message = "Mobile must be exactly 10 digits")
    private String mobileNo;

}
