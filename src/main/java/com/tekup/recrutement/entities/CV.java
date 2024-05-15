package com.tekup.recrutement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    private Date uploadDate;
    private Date deletionDate;
    private int score;
    private List<String> skillsFound;
    private boolean isAcceptedBySystem;
    private String specialite;
    private boolean archived;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "offre_id")
    private Offre offre;

    public CV(String fileName, String uuid, String url, byte[] bytes, Date uploadDate, Date deletionDate, int score,
            List<String> skillsFound, String specialite, boolean isAcceptedBySystem, boolean archived, User user,
            Offre offre) {
        this.nom = fileName;
        this.uuid = uuid;
        this.url = url;
        this.data = bytes;
        this.uploadDate = uploadDate;
        this.deletionDate = deletionDate;
        this.score = score;
        this.skillsFound = skillsFound;
        this.specialite = specialite;
        this.isAcceptedBySystem = isAcceptedBySystem;
        this.user = user;
        this.archived = archived;
        this.offre = offre;
    }

}
