package nh.publy.backend.graphql;

import nh.publy.backend.domain.ReactionType;

public record ToggleReactionInput(Long storyId, Long memberId, ReactionType reactionType) {
}
