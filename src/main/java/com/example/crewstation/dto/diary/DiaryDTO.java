package com.example.crewstation.dto.diary;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class DiaryDTO {
    private Long id;
    private Long postId;
    private String diarySecret;
    private String diaryLikeCount;
    private int diaryReplyCount;
    private int fileCount;
    private String diaryCountryPathId;
    private String postTitle;
    private String postReadCount;
    private Long diaryId;
    private Long crewId;
    private String crewName;
    private String memberFilePath;
    private String diaryFilePath;
    private Long memberId;
    private Long diaryPathId;
    private Long userId;
    private String socialImgUrl;
    private String memberName;
    private String memberDescription;
    private String postContent;
    private String createdDatetime;
    private Long likeId;
    private String updatedDatetime;
    private String relativeDate;
    private boolean check;
}
