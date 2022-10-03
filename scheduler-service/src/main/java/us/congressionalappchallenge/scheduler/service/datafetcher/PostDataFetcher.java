package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import us.congressionalappchallenge.scheduler.service.graphql.types.*;
import us.congressionalappchallenge.scheduler.service.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
@AllArgsConstructor
@Slf4j
public class PostDataFetcher {
  private final PostService postService;
  private final ModelMapper modelMapper;

  @DgsQuery
  @PreAuthorize("isAuthenticated() and #queryFilter.getBusinessId() == authentication.principal.getUid()")
  public List<FacebookPost> facebookPosts(@InputArgument QueryFilter queryFilter) {
    return postService.getFacebookPosts(queryFilter).stream()
        .map(post -> modelMapper.map(post, FacebookPost.class))
        .collect(Collectors.toList());
  }

  @DgsMutation
  @PreAuthorize("isAuthenticated() and #createFacebookPostInput.getBusinessId() == authentication.principal.getUid()")
  public FacebookPost createFacebookPost(@InputArgument CreateFacebookPostInput createFacebookPostInput) {
    return modelMapper.map(
        postService.createFacebookPost(createFacebookPostInput), FacebookPost.class);
  }

  @DgsMutation
  @PreAuthorize("isAuthenticated() and #createInstagramPostInput.getBusinessId() == authentication.principal.getUid()")
  public InstagramPost createInstagramPost(@InputArgument CreateInstagramPostInput createInstagramPostInput) {
    return modelMapper.map(
        postService.createInstagramPost(createInstagramPostInput), InstagramPost.class);
  }
}
