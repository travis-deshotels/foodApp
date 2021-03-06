package tk.codedojo.food.beans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Customer {
    @Id
    private String id;
    private String lastName;
    private String firstName;
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String email;
}
