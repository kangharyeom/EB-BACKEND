package com.java.everyboard.content.mapper;

import com.java.everyboard.comment.dto.CommentResponseDto;
import com.java.everyboard.comment.entity.Comment;
import com.java.everyboard.comment.repository.CommentRepository;
import com.java.everyboard.constant.Category;
import com.java.everyboard.content.dto.*;
import com.java.everyboard.content.entity.Content;
import com.java.everyboard.content.entity.ContentImage;
import com.java.everyboard.content.repository.ContentRepository;
import com.java.everyboard.user.entity.User;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    // 컨텐츠 포스트 to 컨텐츠 //
    Content contentPostDtoToContent(ContentPostDto requestBody);

    // 컨텐츠 패치 to 컨텐츠 //
    Content contentPatchDtoToContent(ContentPatchDto requestBody);

    // 컨텐츠 to 컨텐츠 리스폰스 (단건) //
    default ContentResponseDto contentToContentResponse(Content content){
        User user = content.getUser();

        return ContentResponseDto.builder()
                .contentId(content.getContentId())
                .userId(user.getUserId())
                .viewCount(content.getViewCount())
                .contentHeartCount(content.getContentHeartCount())
                .title(content.getTitle())
                .content(content.getContent())
                .contentImages(content.getContentImages())
                .category(content.getCategory())
                .tag(content.getTag())
                .createdAt(content.getCreatedAt())
                .modifiedAt(content.getModifiedAt())
                .build();
    }

    // 컨텐츠(다중) to 컨텐츠 리스폰스 (전체) //
    List<ContentResponseDto> contentsToContentResponse(List<Content> contents);

    // 카테고리 list 선언 //
    default CategoryContentsResponseDto categoryContentsResponseDto(Category category, ContentRepository contentRepository){
        List<Content> contents = contentRepository.findAllByCategory(category);

        return CategoryContentsResponseDto.builder()
                .category(category)
                .contents(contentsToCategoryContentsResponseDtos(contents))
                .build();
    }

    // 컨텐츠 to 카테고리 리스폰스 //
    default List<CategoryResponseDto> contentsToCategoryContentsResponseDtos(List<Content> contents){
        return contents.stream()
                .map(content -> CategoryResponseDto.builder()
                        .contentId(content.getContentId())
                        .userId(content.getUser().getUserId())
                        .nickname(content.getUser().getNickname())
                        .title(content.getTitle())
                        .content(content.getContent())
                        .viewCount(content.getViewCount())
                        .contentHeartCount(content.getContentHeartCount())
                        .category(content.getCategory())
                        .contentImages(content.getContentImages())
                        .createdAt(content.getCreatedAt())
                        .modifiedAt(content.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 컨텐츠 to 컨텐트 전체 리스폰스 //
    default ContentAllResponseDto contentToContentAllResponse(Content content, CommentRepository commentRepository){
        User user = content.getUser();
        List<Comment> comments = commentRepository.findAllByContentId(content.getContentId());
        Collections.reverse(comments);

        return ContentAllResponseDto.builder()
                .contentId(content.getContentId())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .title(content.getTitle())
                .content(content.getContent())
                .contentHeartCount(content.getContentHeartCount())
                .category(content.getCategory())
                .comments(commentsToCommentResponseDtos(comments))
                .createdAt(content.getCreatedAt())
                .modifiedAt(content.getModifiedAt())
                .contentImages(content.getContentImages())
                .tag(content.getTag())
                .viewCount(content.getViewCount())
                .build();
    }
    // 컨텐츠 to 홈페이지 컨텐츠 리스폰스 //
    default List<HomepageContentResponseDto> contentsToHomepageContentResponseDto(List<Content> contents){
        return contents.stream()
                .map(content -> HomepageContentResponseDto.builder()
                        .contentId(content.getContentId())
                        .title(content.getTitle())
                        .build())
                .collect(Collectors.toList());
    }
    // 컨텐츠 to 홈페이지 컨텐츠 이미지 리스폰스 //
    default List<HomepageContentImageResponseDto> contentsToHomepageContentImageResponseDto(List<Content> contents){
        return contents.stream()
                .map(content -> HomepageContentImageResponseDto.builder()
                        .contentId(content.getContentId())
                        .contentImages(content.getContentImages())
                        .build())
                .collect(Collectors.toList());
    }

    // 컨텐츠 to 유저 컨텐츠 리스폰스 //
    default UserContentResponseDto contentToUserContentResponse(Content content){
        User user = content.getUser();

        return UserContentResponseDto.builder()
                .contentId(content.getContentId())
                .userId(user.getUserId())
                .title(content.getTitle())
                .content(content.getContent())
                .createdAt(content.getCreatedAt())
                .modifiedAt(content.getModifiedAt())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();
    }

    // 커멘츠 to 커멘트 리스폰스 (전체) //
    default List<CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comments){
        return comments.stream()
                .map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .contentId(comment.getContent().getContentId())
                        .userId(comment.getUser().getUserId())
                        .comment(comment.getComment())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .title(comment.getContent().getTitle())
                        .nickName(comment.getUser().getNickname())
                        .build())
                .collect(Collectors.toList());
    }
}