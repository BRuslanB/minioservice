package kz.bitlab.portal.minio.minioservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {

    private Long id;

    private String fileName;

    private String originalName;

    private String mimeType;

    private Long fileSize;

    private LocalDateTime fileDateTimeUpload;
}
