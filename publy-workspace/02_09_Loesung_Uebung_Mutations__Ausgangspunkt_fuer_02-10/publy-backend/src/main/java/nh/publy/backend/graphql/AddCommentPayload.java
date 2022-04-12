package nh.publy.backend.graphql;

import nh.publy.backend.domain.Comment;

public record AddCommentPayload(
  Comment newComment,
  String errorMessage) {
}
