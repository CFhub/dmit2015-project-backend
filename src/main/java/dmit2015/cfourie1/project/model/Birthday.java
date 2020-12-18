package dmit2015.cfourie1.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table

public class Birthday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    @NotBlank(message = "The Title field is required.")
    @Size(min = 3, max = 60, message = "The field Title must be a string with a minimum length of {min} and a maximum length of {max}.")
    private String personName;

    @com.fasterxml.jackson.databind.annotation.JsonSerialize(using = com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer.class)
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer.class)
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @NotNull(message = "The Birthday Date field is required")
    @Future(message = "The Birthday can't be in the past")
    private LocalDate birthdayDate;

    //@Column(nullable = true, length = 30)
    //@NotBlank(message = "The field Gift is required.")
    private String gift;

    //@Column(nullable = true, length = 30)
    //@NotBlank(message = "The field Gift is required.")
    private String whereToGet;

    @DecimalMin(value = "1.00", message = "The price must be greater than 1.00.")
    private BigDecimal price;

    private boolean bought;
}
