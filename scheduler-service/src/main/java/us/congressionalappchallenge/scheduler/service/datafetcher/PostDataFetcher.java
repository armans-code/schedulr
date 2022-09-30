package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.google.firebase.auth.FirebaseToken;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateSocialPostInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.Post;
import us.congressionalappchallenge.scheduler.service.helper.AuthHelper;
import us.congressionalappchallenge.scheduler.service.service.PostService;

@DgsComponent
public class PostDataFetcher {
  private final PostService postService;
  private final AuthHelper authHelper;

  public PostDataFetcher(PostService postService, AuthHelper authHelper) {
    this.postService = postService;
    this.authHelper = authHelper;
  }

  @DgsMutation
  public Post createSocialPost(
      @InputArgument CreateSocialPostInput createSocialPostInput,
      @RequestHeader("Authorization") String token) {
    FirebaseToken user = authHelper.verifyUser(token.split("Bearer ")[1]);
    return postService.createSocialPost(user.getUid(), createSocialPostInput);
  }
}
