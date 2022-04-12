package nh.publy.backend.graphql;

import nh.publy.backend.domain.Story;
import nh.publy.backend.domain.StoryRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class PublyGraphQLController {

  private StoryRepository storyRepository;

  public PublyGraphQLController(StoryRepository storyRepository) {
    this.storyRepository = storyRepository;
  }

  @QueryMapping
  public Optional<Story> story(@Argument Optional<Long> storyId) {
    return storyId.map(sid -> storyRepository.findById(sid))
      .orElseGet( () -> storyRepository.findFirstByOrderByCreatedAtDesc());
  }

}
