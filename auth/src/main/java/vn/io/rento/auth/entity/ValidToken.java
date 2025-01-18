package vn.io.rento.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ValidToken {
    @Id
    private String accessTokenId;
    private String refreshTokenId;
    private String prevTokenId;
    private Date refreshTime;
}
