package com.example.crewstation.repository.file;

import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.mapper.file.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FileDAO {
    private final FileMapper fileMapper;
    //    회원가입 시 프로필
    public void save(FileVO FileVO) {
        fileMapper.insertFile(FileVO);
    }

    public void saveFile(FileDTO fileDTO) {
        fileMapper.insert(fileDTO);
    }

    //  파일 삭제
    public void delete(Long id){
        fileMapper.delete(id);
    }

//    파일 링크 삭제
    public FileDTO findById(Long id) {
        return fileMapper.selectOne(id);
    }

}
