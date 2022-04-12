package nh.publy.backend.graphql;

import nh.publy.backend.domain.ReactionType;

public record ToggleReactionInput(NodeId storyId, NodeId memberId, ReactionType reactionType) {
}
