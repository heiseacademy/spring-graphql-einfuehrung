package nh.publy.backend.graphql;

import nh.publy.backend.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PublyGraphQLController {

  private StoryRepository storyRepository;
  private MarkdownService markdownService;
  private MemberRepository memberRepository;
  private PublyDomainService publyDomainService;
  private CommentRepository commentRepository;

  public PublyGraphQLController(StoryRepository storyRepository, MarkdownService markdownService, MemberRepository memberRepository, PublyDomainService publyDomainService, CommentRepository commentRepository) {
    this.storyRepository = storyRepository;
    this.markdownService = markdownService;
    this.memberRepository = memberRepository;
    this.publyDomainService = publyDomainService;
    this.commentRepository = commentRepository;
  }

  @QueryMapping("story")
  public Optional<Story> getStory(@Argument("storyId") Optional<NodeId> id) {
    return id.map(sid -> storyRepository.findById(sid.getStoryId()))
      .orElseGet( () -> storyRepository.findFirstByOrderByCreatedAtDesc());
  }

  @SchemaMapping(typeName="Story", field="body")
  public String body(Story story) {
    return story.getBodyMarkdown();
  }

  @SchemaMapping
  public String excerpt(Story story, @Argument int maxLength) {
    String plainBody = markdownService.toPlainText(story.getBodyMarkdown());
    String excerpt = plainBody.length() > maxLength ? plainBody.substring(0, maxLength) + "..." : plainBody;
    return excerpt;
  }

  @QueryMapping
  public List<Member> members(@Valid MemberPagination memberPagination) {

    Pageable pageable = PageRequest.of(
      memberPagination.getPage().orElse(0),
      memberPagination.getSize().orElse(10)
    );

    Page<Member> result = memberRepository.findAll(pageable);
    return result.getContent();
  }

  @MutationMapping
  public Story toggleReaction(@Argument ToggleReactionInput input) {

    return publyDomainService.toggleReaction(input.storyId().getStoryId(),
      input.memberId().getMemberId(), input.reactionType());
  }

  @MutationMapping
  public Object addComment(@Argument NodeId storyId,
                                      @Argument NodeId memberId,
                                      @Argument String content) {
    try {
      Comment comment = publyDomainService.addComment(
        storyId.getStoryId(),
        memberId.getMemberId(),
        content);

      return new AddCommentSuccessResult(comment);
    } catch (Exception ex) {
      return new AddCommentFailedPayload(ex.getMessage());
    }
  }

 @SchemaMapping
  public NodeId id(Story story) {
    return new NodeId(story.getId(), NodeType.story);
  }

  @SchemaMapping
  public NodeId id(Member member) {
    return new NodeId(member.getId(), NodeType.member);
  }

  @SchemaMapping
  public NodeId id(Comment comment) {
    return new NodeId(comment.getId(), NodeType.comment);
  }

  @QueryMapping
  public Object node(@Argument NodeId id) {
    NodeType nodeType = id.getNodeType();

    return switch (nodeType) {
      case story -> getStory(Optional.of(id));
      case member -> memberRepository.findById(id.getMemberId());
      case comment -> commentRepository.findById(id.getCommentId());
    };

  }

}
