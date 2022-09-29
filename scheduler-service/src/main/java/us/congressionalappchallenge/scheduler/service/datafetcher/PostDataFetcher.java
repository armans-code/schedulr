package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreatePostInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.Post;
import us.congressionalappchallenge.scheduler.service.service.PostService;

@DgsComponent
public class PostDataFetcher {
  private final PostService postService;

  public PostDataFetcher(PostService postService) {
    this.postService = postService;
  }

  @DgsMutation
  public Post createPost(@InputArgument CreatePostInput createPostInput) {
    return postService.createPost("auth-id", createPostInput);
  }
}
