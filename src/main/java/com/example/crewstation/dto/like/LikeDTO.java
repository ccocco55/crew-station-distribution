package com.example.crewstation.dto.like;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class LikeDTO {
    private Long id;
    private Long postId;
    private Long memberId;
}
