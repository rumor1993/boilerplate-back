package com.rumor.yumback.domains.posts.application;

import com.rumor.yumback.domains.posts.presentation.view.PostView;
import org.springframework.data.domain.Page;

import java.util.List;

public record CommunityView(
        Page<PostView> populars,
        List<PostView> posts
) {
}
