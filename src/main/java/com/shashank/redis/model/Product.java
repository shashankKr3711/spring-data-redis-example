package com.shashank.redis.model;

import com.shashank.redis.repository.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("product")
public class Product extends AbstractEntity<UUID> {

    @Id
    private UUID id;
    private String name;
    private String category;
    private Double price;

}
