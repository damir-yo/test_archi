package com.peachub.app.dto.reviewLike;

public record LikeResponse(
        boolean liked,
        int likesCount
) {
}