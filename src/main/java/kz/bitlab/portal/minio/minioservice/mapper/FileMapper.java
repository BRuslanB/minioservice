package kz.bitlab.portal.minio.minioservice.mapper;

import kz.bitlab.portal.minio.minioservice.dto.FileDto;
import kz.bitlab.portal.minio.minioservice.model.UploadFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileDto toDto(UploadFile uploadFile);
    UploadFile toEntity (FileDto fileDto);

    List<FileDto> toDtoList (List<UploadFile> fileList);
}
