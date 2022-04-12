package nh.publy;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import nh.publy.domain.Story;
import nh.publy.domain.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class GraphQLApi {

  private static final Logger log = LoggerFactory.getLogger(GraphQLApi.class);

  StoryRepository storyRepository = new StoryRepository();

  @QueryMapping
  public List<Story> stories() {
    return storyRepository.findAllStories();
  }

  @QueryMapping
  public Optional<Story> story(@Argument Long storyId) {
    Optional<Story> story = storyRepository.findStoryById(storyId);
    return story;
  }

  @SchemaMapping
  public String excerpt(Story story) {
    String excerpt = story.getBody().substring(0, 3);
    return excerpt;
  }

  @Component
  class ExecuteGraphQLQuery implements CommandLineRunner {

    @Autowired
    private GraphQlSource graphQlSource;

    @Override
    public void run(String... args) throws Exception {
      GraphQL graphQL = graphQlSource.graphQl();

      ExecutionResult executionResult = graphQL.execute("""
      query { 
        stories { id title excerpt writtenBy { id } }
      }
    """);

      Object data = executionResult.getData();
      log.info("GraphQL Result {}", data);
    }
  }
}
