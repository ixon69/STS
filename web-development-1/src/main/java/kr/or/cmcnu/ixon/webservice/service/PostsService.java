package kr.or.cmcnu.ixon.webservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.cmcnu.ixon.webservice.domain.PostsRepository;
import kr.or.cmcnu.ixon.webservice.dto.PostsMainResponseDto;
import kr.or.cmcnu.ixon.webservice.dto.PostsSaveRequestDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostsService {
    private PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto dto){
        return postsRepository.save(dto.toEntity()).getId();
    }
    
    @Transactional(readOnly = true)
    public List<PostsMainResponseDto> findAllDesc() {
        return postsRepository.findAllDesc()
                .map(PostsMainResponseDto::new)
                .collect(Collectors.toList());
    }
    
}
