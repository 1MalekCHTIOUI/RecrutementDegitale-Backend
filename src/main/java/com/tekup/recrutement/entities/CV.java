package com.tekup.recrutement.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String nom;
    private String url;
    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;


    public CV(String fileName, String uuid, String url, byte[] bytes) {
        this.nom = fileName;
        this.uuid = uuid;
        this.url = url;
        this.data = bytes;
    }
}
