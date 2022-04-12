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

import javax.validation.constraints.Max;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PublyGraphQLController {

  private StoryRepository storyRepository;
  private MarkdownService markdownService;
  private MemberRepository memberRepository;
  private PublyDomainService publyDomainService;

  public PublyGraphQLController(StoryRepository storyRepository, MarkdownService markdownService, MemberRepository memberRepository, PublyDomainService publyDomainService) {
    this.storyRepository = storyRepository;
    this.markdownService = markdownService;
    this.memberRepository = memberRepository;
    this.publyDomainService = publyDomainService;
  }

  @QueryMapping("story")
  public Optional<Story> getStory(@Argument("storyId") Optional<Long> id) {
    return id.map(sid -> storyRepository.findById(sid))
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

    return publyDomainService.toggleReaction(input.storyId(),
      input.memberId(), input.reactionType());
  }

  @MutationMapping
  public AddCommentPayload addComment(@Argument Long storyId,
                                      @Argument Long memberId,
                                      @Argument String content) {
    try {
      Comment comment = publyDomainService.addComment(storyId, memberId, content);
      return new AddCommentPayload(comment, null);
    } catch (Exception ex) {
      return new AddCommentPayload(null, ex.getMessage());
    }
  }

}
