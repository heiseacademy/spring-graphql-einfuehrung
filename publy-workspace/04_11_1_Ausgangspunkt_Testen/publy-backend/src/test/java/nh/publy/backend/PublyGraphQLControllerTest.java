package nh.publy.backend;


import nh.publy.backend.domain.*;
import nh.publy.backend.domain.user.UserService;
import nh.publy.backend.graphql.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class PublyGraphQLControllerTest {
  @MockBean StoryRepository storyRepository;
  @MockBean MarkdownService markdownService;
  @MockBean MemberRepository memberRepository;
  @MockBean PublyDomainService publyDomainService;
  @MockBean CommentRepository commentRepository;
  @MockBean CommentEventPublisher commentEventPublisher;
  @MockBean ReactionRepository reactionRepository;
  @MockBean FeedbackPublisher feedbackPublisher;
  @MockBean UserService userService;

}
