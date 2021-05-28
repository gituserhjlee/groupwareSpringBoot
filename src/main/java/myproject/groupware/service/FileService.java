package myproject.groupware.service;

import lombok.RequiredArgsConstructor;
import myproject.groupware.dto.FileDto;
import myproject.groupware.entity.File;
import myproject.groupware.entity.Member;
import myproject.groupware.repository.FileRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {
    private final FileRepository fileRepository;


    public File saveFile(File file) {
        File f = fileRepository.save(file);
        return f;
    }

    public FileDto getFile(Long id) {
        File file = fileRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}