package edu.escw.server;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("device")
public class Dev {
    @Id
    private Long id;


}
