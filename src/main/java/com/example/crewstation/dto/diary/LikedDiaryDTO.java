package com.example.crewstation.dto.diary;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class LikedDiaryDTO {
    private Long postId;
    private String postTitle;
    private String content;
    private Long memberId;
    private String memberName;
    private String memberProfileImage;
    private String mainImage;
    private int diaryLikeCount;
    private int diaryReplyCount;
    private boolean liked;
}
