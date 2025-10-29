package com.example.crewstation.controller.admin;

import com.example.crewstation.dto.report.post.ReportPostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Admin", description = "관리자 API")
public interface AdminRestControllerDocs {

//    다이어리 신고 목록
    @Operation(summary = "다이어리 신고 목록", description = "다이어리에 대한 신고 목록을 페이지 단위로 조회합니다.",
            parameters = {
                    @Parameter(name = "page", description = "조회할 페이지 번호")
            }
    )
    public ResponseEntity<List<ReportPostDTO>> getReportDiaryList(@RequestParam(defaultValue = "1") int page);

//    다이어리 신고 처리
    @Operation(summary = "다이어리 신고 처리", description = "특정 다이어리 신고를 처리합니다. 필요시 해당 게시글(다이어리)을 숨길 수 있습니다.",
            parameters = {
                    @Parameter(name = "reportId", description = "신고 ID"),
                    @Parameter(name = "postId", description = "숨길 게시글(다이어리)의 ID"),
                    @Parameter(name = "hidePost", description = "게시글 숨김 여부")
            }
    )
    public ResponseEntity<ReportPostDTO> processDiaryReport(@PathVariable Long reportId,
                                                            @RequestParam(required = false) Long postId,
                                                            @RequestParam(defaultValue = "false") boolean hidePost);

//    기프트 신고 목록
    @Operation(summary = "기프트 신고 목록", description = "기프트에 대한 신고 목록을 페이지 단위로 조회합니다.",
            parameters = {
                    @Parameter(name = "page", description = "조회할 페이지 번호")
            }
    )
    public ResponseEntity<List<ReportPostDTO>> getReportGiftList(@RequestParam(defaultValue = "1") int page);

//    기프트 신고 처리
    @Operation(summary = "기프트 신고 처리", description = "특정 기프트 신고를 처리합니다. 필요시 해당 게시글(기프트)을 숨길 수 있습니다.",
            parameters = {
                    @Parameter(name = "reportId", description = "신고 ID"),
                    @Parameter(name = "postId", description = "숨길 게시글(기프트)의 ID"),
                    @Parameter(name = "hidePost", description = "게시글 숨김 여부")
            }
    )
    public ResponseEntity<ReportPostDTO> processGiftReport(@PathVariable Long reportId,
                                                           @RequestParam(required = false) Long postId,
                                                           @RequestParam(defaultValue = "false") boolean hidePost);
}
