package nh.publy.backend.graphql;

import nh.publy.backend.domain.Story;
import nh.publy.backend.domain.StoryRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class PublyGraphQLController {


  private StoryRepository storyRepository;
  private MarkdownService markdownService;

  public PublyGraphQLController(StoryRepository storyRepository, MarkdownService markdownService) {
    this.storyRepository = storyRepository;
    this.markdownService = markdownService;
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

}
