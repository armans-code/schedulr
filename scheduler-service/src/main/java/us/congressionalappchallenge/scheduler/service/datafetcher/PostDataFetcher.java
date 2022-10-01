package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.security.access.prepost.PreAuthorize;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateFacebookPostInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.FacebookPost;
import us.congressionalappchallenge.scheduler.service.service.PostService;

@DgsComponent
public class PostDataFetcher {
  private final PostService postService;

  public PostDataFetcher(PostService postService) {
    this.postService = postService;
  }

  @DgsMutation
  @PreAuthorize("isAuthenticated() and #createFacebookPostInput.getBusinessId() == authentication.principal.getUid()")
  public FacebookPost createFacebookPost(@InputArgument CreateFacebookPostInput createFacebookPostInput) {
    return postService.createFacebookPost(createFacebookPostInput.getBusinessId(), createFacebookPostInput);
  }
}
