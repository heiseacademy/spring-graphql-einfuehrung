package nh.publy.backend.graphql;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

@Component
public class PublyRuntimeWiringConfigurer implements RuntimeWiringConfigurer {

  @Override
  public void configure(RuntimeWiring.Builder builder) {
    builder.type("AddCommentPayload", typeBuilder -> {
      return typeBuilder.typeResolver(new TypeResolver() {
        @Override
        public GraphQLObjectType getType(TypeResolutionEnvironment env) {
          Object object = env.getObject();
          
          if (object instanceof AddCommentSuccessResult) {
            return env.getSchema().getObjectType("AddCommentSuccessPayload");
          }

          return env.getSchema().getObjectType("AddCommentFailedPayload");
        }
      });
    });
  }
}
